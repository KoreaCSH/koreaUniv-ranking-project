package koreaUniv.koreaUnivRankSys.batch.job.building.history;

import koreaUniv.koreaUnivRankSys.batch.common.JobLoggerListener;
import koreaUniv.koreaUnivRankSys.batch.domain.building.history.HanaSquareRecordHistory;
import koreaUniv.koreaUnivRankSys.batch.domain.building.history.ScienceLibraryRecordHistory;
import koreaUniv.koreaUnivRankSys.batch.repository.building.history.ScienceLibraryRecordHistoryRepository;
import koreaUniv.koreaUnivRankSys.domain.building.domain.HanaSquareRecord;
import koreaUniv.koreaUnivRankSys.domain.building.domain.ScienceLibraryRecord;
import koreaUniv.koreaUnivRankSys.domain.building.repository.ScienceLibraryRecordRepository;
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

@Slf4j
@Configuration
@RequiredArgsConstructor
public class ScienceLibraryRecordMigrationJobConfig {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    private final ScienceLibraryRecordHistoryRepository scienceLibraryRecordHistoryRepository;
    private final ScienceLibraryRecordRepository scienceLibraryRecordRepository;

    private final MemberService memberService;
    private final DataSource dataSource;

    @Bean
    public Job scienceLibraryRecordMigrationJob(Step scienceLibraryRecordMigrationStep,
                                            Step scienceLibraryDailyStudyTimeUpdateStep) {
        return jobBuilderFactory.get("scienceLibraryRecordMigrationJob")
                .incrementer(new RunIdIncrementer())
                .listener(new JobLoggerListener())
                .start(scienceLibraryRecordMigrationStep)
                .on("COMPLETED").to(scienceLibraryDailyStudyTimeUpdateStep)
                .from(scienceLibraryRecordMigrationStep)
                .end()
                .build();
    }

    @JobScope
    @Bean
    public Step scienceLibraryRecordMigrationStep(ItemReader scienceLibraryRecordRankingReader,
                                              ItemProcessor scienceLibraryRecordProcessor,
                                              ItemWriter scienceLibraryRecordHistoryWriter) {

        return stepBuilderFactory.get("scienceLibraryRecordMigrationStep")
                .<RankingDto, ScienceLibraryRecordHistory>chunk(10)
                .reader(scienceLibraryRecordRankingReader)
                .processor(scienceLibraryRecordProcessor)
                .writer(scienceLibraryRecordHistoryWriter)
                .build();
    }

    @StepScope
    @Bean
    public JdbcCursorItemReader<RankingDto> scienceLibraryRecordRankingReader() {

        return new JdbcCursorItemReaderBuilder<RankingDto>()
                .dataSource(dataSource)
                .name("scienceLibraryRecordReader")
                .sql("select member_id, path, nick_name, daily_study_time, " +
                        "row_number() over (order by daily_study_time desc) as \'ranking\' " +
                        "from (member natural left outer join member_image) join science_library_record " +
                        "where member.science_library_record_id = science_library_record.science_library_record_id")
                .rowMapper(new DailyRankingDtoRowMapper())
                .build();
    }

    @StepScope
    @Bean
    public ItemProcessor<RankingDto, ScienceLibraryRecordHistory> scienceLibraryRecordProcessor() {
        return new ItemProcessor<RankingDto, ScienceLibraryRecordHistory>() {
            @Override
            public ScienceLibraryRecordHistory process(RankingDto item) throws Exception {

                Member findMember = memberService.findById(item.getMemberId());
                return new ScienceLibraryRecordHistory(findMember, item.getRanking(), item.getStudyTime());
            }
        };
    }

    @StepScope
    @Bean
    public RepositoryItemWriter<ScienceLibraryRecordHistory> scienceLibraryRecordHistoryWriter() {

        return new RepositoryItemWriterBuilder<ScienceLibraryRecordHistory>()
                .repository(scienceLibraryRecordHistoryRepository)
                .methodName("save")
                .build();
    }

    @JobScope
    @Bean
    public Step scienceLibraryDailyStudyTimeUpdateStep(ItemReader scienceLibraryRecordDailyStudyTimeReader,
                                                   ItemProcessor scienceLibraryRecordDailyStudyTimeUpdateProcessor,
                                                   ItemWriter scienceLibraryRecordDailyStudyTimeWriter) {

        return stepBuilderFactory.get("scienceLibraryDailyStudyTimeUpdateStep")
                .<ScienceLibraryRecord, ScienceLibraryRecord>chunk(10)
                .reader(scienceLibraryRecordDailyStudyTimeReader)
                .processor(scienceLibraryRecordDailyStudyTimeUpdateProcessor)
                .writer(scienceLibraryRecordDailyStudyTimeWriter)
                .build();
    }

    @StepScope
    @Bean
    public RepositoryItemReader<ScienceLibraryRecord> scienceLibraryRecordDailyStudyTimeReader() {

        return new RepositoryItemReaderBuilder<ScienceLibraryRecord>()
                .name("scienceLibraryRecordDailyStudyTimeReader")
                .repository(scienceLibraryRecordRepository)
                .methodName("findAll")
                .pageSize(10)
                .arguments(Arrays.asList())
                .sorts(Collections.singletonMap("id", Sort.Direction.ASC))
                .build();
    }

    @StepScope
    @Bean
    public ItemProcessor<ScienceLibraryRecord, ScienceLibraryRecord> scienceLibraryRecordDailyStudyTimeUpdateProcessor() {

        return new ItemProcessor<ScienceLibraryRecord, ScienceLibraryRecord>() {
            @Override
            public ScienceLibraryRecord process(ScienceLibraryRecord item) throws Exception {
                item.resetDailyStudyTime();
                return item;
            }
        };
    }

    @StepScope
    @Bean
    public RepositoryItemWriter<ScienceLibraryRecord> scienceLibraryRecordDailyStudyTimeWriter() {

        return new RepositoryItemWriterBuilder<ScienceLibraryRecord>()
                .repository(scienceLibraryRecordRepository)
                .methodName("save")
                .build();
    }

}
