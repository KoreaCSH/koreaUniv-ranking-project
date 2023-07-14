package koreaUniv.koreaUnivRankSys.global.batch.job.building;

import koreaUniv.koreaUnivRankSys.domain.building.domain.CentralLibraryRecord;
import koreaUniv.koreaUnivRankSys.domain.building.dto.RankingDto;
import koreaUniv.koreaUnivRankSys.domain.building.dto.mapper.DailyRankingDtoRowMapper;
import koreaUniv.koreaUnivRankSys.domain.building.repository.CentralLibraryRecordRepository;
import koreaUniv.koreaUnivRankSys.domain.building.repository.ranking.CentralLibraryRankingQueryRepository;
import koreaUniv.koreaUnivRankSys.domain.member.domain.Member;
import koreaUniv.koreaUnivRankSys.domain.member.service.MemberService;
import koreaUniv.koreaUnivRankSys.global.batch.domain.building.CentralLibraryRecordHistory;
import koreaUniv.koreaUnivRankSys.global.batch.domain.building.CentralLibraryRecordHistoryRepository;
import koreaUniv.koreaUnivRankSys.global.batch.job.JobLoggerListener;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.RepositoryItemReader;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.data.builder.RepositoryItemReaderBuilder;
import org.springframework.batch.item.data.builder.RepositoryItemWriterBuilder;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Sort;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.Collections;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class CentralLibraryRecordMigrationJobConfig {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    private final CentralLibraryRecordHistoryRepository centralLibraryRecordHistoryRepository;
    private final CentralLibraryRecordRepository centralLibraryRecordRepository;

    private final MemberService memberService;
    private final DataSource dataSource;

    @Bean
    public Job centralLibraryRecordMigrationJob(Step centralLibraryRecordMigrationStep,
                                                Step centralLibraryDailyStudyTimeUpdateStep) {
        return jobBuilderFactory.get("centralLibraryRecordMigrationJob")
                .incrementer(new RunIdIncrementer())
                .listener(new JobLoggerListener())
                .start(centralLibraryRecordMigrationStep)
                .on("COMPLETED").to(centralLibraryDailyStudyTimeUpdateStep)
                .from(centralLibraryRecordMigrationStep)
                .end()
                .build();
    }

    @JobScope
    @Bean
    public Step centralLibraryRecordMigrationStep(ItemReader centralLibraryRecordRankingReader,
                                                  ItemProcessor centralLibraryRecordProcessor,
                                                  ItemWriter centralLibraryRecordHistoryWriter) {

        return stepBuilderFactory.get("centralLibraryRecordMigrationStep")
                .<RankingDto, CentralLibraryRecordHistory>chunk(10)
                .reader(centralLibraryRecordRankingReader)
                .processor(centralLibraryRecordProcessor)
                .writer(centralLibraryRecordHistoryWriter)
                .build();
    }

    @StepScope
    @Bean
    public JdbcCursorItemReader<RankingDto> centralLibraryRecordRankingReader() {

        return new JdbcCursorItemReaderBuilder<RankingDto>()
                .dataSource(dataSource)
                .name("centralLibraryRecordReader")
                .sql("select member_id, path, nick_name, daily_study_time, " +
                        "row_number() over (order by daily_study_time desc) as \'ranking\' " +
                        "from (member natural left outer join member_image) join central_library_record " +
                        "where member.central_library_record_id = central_library_record.central_library_record_id")
                .rowMapper(new DailyRankingDtoRowMapper())
                .build();
    }

    @StepScope
    @Bean
    public ItemProcessor<RankingDto, CentralLibraryRecordHistory> centralLibraryRecordProcessor() {
        return new ItemProcessor<RankingDto, CentralLibraryRecordHistory>() {
            @Override
            public CentralLibraryRecordHistory process(RankingDto item) throws Exception {

                Member findMember = memberService.findById(item.getMemberId());
                return new CentralLibraryRecordHistory(findMember, item.getRanking(), item.getStudyTime());
            }
        };
    }

    @StepScope
    @Bean
    public RepositoryItemWriter<CentralLibraryRecordHistory> centralLibraryRecordHistoryWriter() {

        return new RepositoryItemWriterBuilder<CentralLibraryRecordHistory>()
                .repository(centralLibraryRecordHistoryRepository)
                .methodName("save")
                .build();
    }

    @JobScope
    @Bean
    public Step centralLibraryDailyStudyTimeUpdateStep(ItemReader centralLibraryRecordDailyStudyTimeReader,
                                                       ItemProcessor centralLibraryRecordDailyStudyTimeUpdateProcessor,
                                                       ItemWriter centralLibraryRecordDailyStudyTimeWriter) {

        return stepBuilderFactory.get("centralLibraryDailyStudyTimeUpdateStep")
                .<CentralLibraryRecord, CentralLibraryRecord>chunk(10)
                .reader(centralLibraryRecordDailyStudyTimeReader)
                .processor(centralLibraryRecordDailyStudyTimeUpdateProcessor)
                .writer(centralLibraryRecordDailyStudyTimeWriter)
                .build();
    }

    @StepScope
    @Bean
    public RepositoryItemReader<CentralLibraryRecord> centralLibraryRecordDailyStudyTimeReader() {

        return new RepositoryItemReaderBuilder<CentralLibraryRecord>()
                .name("centralLibraryRecordDailyStudyTimeReader")
                .repository(centralLibraryRecordRepository)
                .methodName("findAll")
                .pageSize(10)
                .arguments(Arrays.asList())
                .sorts(Collections.singletonMap("id", Sort.Direction.ASC))
                .build();
    }

    @StepScope
    @Bean
    public ItemProcessor<CentralLibraryRecord, CentralLibraryRecord> centralLibraryRecordDailyStudyTimeUpdateProcessor() {

        return new ItemProcessor<CentralLibraryRecord, CentralLibraryRecord>() {
            @Override
            public CentralLibraryRecord process(CentralLibraryRecord item) throws Exception {
                item.resetDailyStudyTime();
                return item;
            }
        };
    }

    @StepScope
    @Bean
    public RepositoryItemWriter<CentralLibraryRecord> centralLibraryRecordDailyStudyTimeWriter() {

        return new RepositoryItemWriterBuilder<CentralLibraryRecord>()
                .repository(centralLibraryRecordRepository)
                .methodName("save")
                .build();
    }

}
