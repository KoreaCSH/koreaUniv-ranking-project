package koreaUniv.koreaUnivRankSys.batch.job.building.history;

import koreaUniv.koreaUnivRankSys.batch.common.JobLoggerListener;
import koreaUniv.koreaUnivRankSys.batch.domain.building.history.CentralSquareRecordHistory;
import koreaUniv.koreaUnivRankSys.batch.repository.building.history.CentralSquareRecordHistoryRepository;
import koreaUniv.koreaUnivRankSys.domain.building.domain.CentralSquareRecord;
import koreaUniv.koreaUnivRankSys.domain.building.repository.CentralSquareRecordRepository;
import koreaUniv.koreaUnivRankSys.domain.member.domain.Member;
import koreaUniv.koreaUnivRankSys.domain.member.service.MemberService;
import koreaUniv.koreaUnivRankSys.web.building.dto.RankingDto;
import koreaUniv.koreaUnivRankSys.web.building.dto.mapper.DailyRankingDtoRowMapper;
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

// --spring.batch.job.names=centralSquareRecordMigrationJob

@Slf4j
@Configuration
@RequiredArgsConstructor
public class CentralSquareRecordMigrationJobConfig {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    private final CentralSquareRecordHistoryRepository centralSquareRecordHistoryRepository;
    private final CentralSquareRecordRepository centralSquareRecordRepository;

    private final MemberService memberService;
    private final DataSource dataSource;

    @Bean
    public Job centralSquareRecordMigrationJob(Step centralSquareRecordMigrationStep,
                                               Step centralSquareDailyStudyTimeUpdateStep) {
        return jobBuilderFactory.get("centralSquareRecordMigrationJob")
                .incrementer(new RunIdIncrementer())
                .listener(new JobLoggerListener())
                .start(centralSquareRecordMigrationStep)
                .on("COMPLETED").to(centralSquareDailyStudyTimeUpdateStep)
                .from(centralSquareRecordMigrationStep)
                .end()
                .build();
    }

    @JobScope
    @Bean
    public Step centralSquareRecordMigrationStep(ItemReader centralSquareRecordRankingReader,
                                                 ItemProcessor centralSquareRecordProcessor,
                                                 ItemWriter centralSquareRecordHistoryWriter) {
        return stepBuilderFactory.get("centralSquareRecordMigrationStep")
                .<RankingDto, CentralSquareRecordHistory>chunk(10)
                .reader(centralSquareRecordRankingReader)
                .processor(centralSquareRecordProcessor)
                .writer(centralSquareRecordHistoryWriter)
                .build();
    }

    @StepScope
    @Bean
    public JdbcCursorItemReader<RankingDto> centralSquareRecordRankingReader() {

        return new JdbcCursorItemReaderBuilder<RankingDto>()
                .dataSource(dataSource)
                .name("centralSquareRecordReader")
                .sql("select member_id, path, nick_name, daily_study_time, " +
                        "row_number() over (order by daily_study_time desc) as \'ranking\' " +
                        "from (member natural left outer join member_image) join central_square_record " +
                        "where member.central_square_record_id = central_square_record.central_square_record_id")
                .rowMapper(new DailyRankingDtoRowMapper())
                .build();
    }

    @StepScope
    @Bean
    public ItemProcessor<RankingDto, CentralSquareRecordHistory> centralSquareRecordProcessor() {
        return new ItemProcessor<RankingDto, CentralSquareRecordHistory>() {
            @Override
            public CentralSquareRecordHistory process(RankingDto item) throws Exception {

                Member findMember = memberService.findById(item.getMemberId());
                return new CentralSquareRecordHistory(findMember, item.getRanking(), item.getStudyTime());
            }
        };
    }

    @StepScope
    @Bean
    public RepositoryItemWriter<CentralSquareRecordHistory> centralSquareRecordHistoryWriter() {

        return new RepositoryItemWriterBuilder<CentralSquareRecordHistory>()
                .repository(centralSquareRecordHistoryRepository)
                .methodName("save")
                .build();
    }

    @JobScope
    @Bean
    public Step centralSquareDailyStudyTimeUpdateStep(ItemReader centralSquareRecordDailyStudyTimeReader,
                                                      ItemProcessor centralSquareRecordDailyStudyTimeUpdateProcessor,
                                                      ItemWriter centralSquareRecordDailyStudyTimeWriter) {

        return stepBuilderFactory.get("centralSquareDailyStudyTimeUpdateStep")
                .<CentralSquareRecord, CentralSquareRecord>chunk(10)
                .reader(centralSquareRecordDailyStudyTimeReader)
                .processor(centralSquareRecordDailyStudyTimeUpdateProcessor)
                .writer(centralSquareRecordDailyStudyTimeWriter)
                .build();
    }

    @StepScope
    @Bean
    public RepositoryItemReader<CentralSquareRecord> centralSquareRecordDailyStudyTimeReader() {

        return new RepositoryItemReaderBuilder<CentralSquareRecord>()
                .name("centralSquareRecordDailyStudyTimeReader")
                .repository(centralSquareRecordRepository)
                .methodName("findAll")
                .pageSize(10)
                .arguments(Arrays.asList())
                .sorts(Collections.singletonMap("id", Sort.Direction.ASC))
                .build();
    }

    @StepScope
    @Bean
    public ItemProcessor<CentralSquareRecord, CentralSquareRecord> centralSquareRecordDailyStudyTimeUpdateProcessor() {

        return new ItemProcessor<CentralSquareRecord, CentralSquareRecord>() {
            @Override
            public CentralSquareRecord process(CentralSquareRecord item) throws Exception {
                item.resetDailyStudyTime();
                return item;
            }
        };
    }

    @StepScope
    @Bean
    public RepositoryItemWriter<CentralSquareRecord> centralSquareRecordDailyStudyTimeWriter() {

        return new RepositoryItemWriterBuilder<CentralSquareRecord>()
                .repository(centralSquareRecordRepository)
                .methodName("save")
                .build();
    }

}
