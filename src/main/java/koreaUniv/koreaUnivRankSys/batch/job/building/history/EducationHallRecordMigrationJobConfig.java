package koreaUniv.koreaUnivRankSys.batch.job.building.history;

import koreaUniv.koreaUnivRankSys.batch.common.JobLoggerListener;
import koreaUniv.koreaUnivRankSys.batch.domain.building.history.EducationHallRecordHistory;
import koreaUniv.koreaUnivRankSys.batch.repository.building.history.EducationHallRecordHistoryRepository;
import koreaUniv.koreaUnivRankSys.domain.building.domain.EducationHallRecord;
import koreaUniv.koreaUnivRankSys.domain.building.repository.EducationHallRecordRepository;
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

// --spring.batch.job.names=educationHallRecordMigrationJob

@Slf4j
@Configuration
@RequiredArgsConstructor
public class EducationHallRecordMigrationJobConfig {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    private final EducationHallRecordHistoryRepository educationHallRecordHistoryRepository;
    private final EducationHallRecordRepository educationHallRecordRepository;

    private final MemberService memberService;
    private final DataSource dataSource;

    @Bean
    public Job educationHallRecordMigrationJob(Step educationHallRecordMigrationStep,
                                                Step educationHallDailyStudyTimeUpdateStep) {
        return jobBuilderFactory.get("educationHallRecordMigrationJob")
                .incrementer(new RunIdIncrementer())
                .listener(new JobLoggerListener())
                .start(educationHallRecordMigrationStep)
                .on("COMPLETED").to(educationHallDailyStudyTimeUpdateStep)
                .from(educationHallRecordMigrationStep)
                .end()
                .build();
    }

    @JobScope
    @Bean
    public Step educationHallRecordMigrationStep(ItemReader educationHallRecordRankingReader,
                                                  ItemProcessor educationHallRecordProcessor,
                                                  ItemWriter educationHallRecordHistoryWriter) {

        return stepBuilderFactory.get("educationHallRecordMigrationStep")
                .<RankingDto, EducationHallRecordHistory>chunk(10)
                .reader(educationHallRecordRankingReader)
                .processor(educationHallRecordProcessor)
                .writer(educationHallRecordHistoryWriter)
                .build();
    }

    @StepScope
    @Bean
    public JdbcCursorItemReader<RankingDto> educationHallRecordRankingReader() {

        return new JdbcCursorItemReaderBuilder<RankingDto>()
                .dataSource(dataSource)
                .name("educationHallRecordReader")
                .sql("select member_id, path, nick_name, daily_study_time, " +
                        "row_number() over (order by daily_study_time desc) as \'ranking\' " +
                        "from (member natural left outer join member_image) join education_hall_record " +
                        "where member.education_hall_record_id = education_hall_record.education_hall_record_id")
                .rowMapper(new DailyRankingDtoRowMapper())
                .build();
    }

    @StepScope
    @Bean
    public ItemProcessor<RankingDto, EducationHallRecordHistory> educationHallRecordProcessor() {
        return new ItemProcessor<RankingDto, EducationHallRecordHistory>() {
            @Override
            public EducationHallRecordHistory process(RankingDto item) throws Exception {

                Member findMember = memberService.findById(item.getMemberId());
                return new EducationHallRecordHistory(findMember, item.getRanking(), item.getStudyTime());
            }
        };
    }

    @StepScope
    @Bean
    public RepositoryItemWriter<EducationHallRecordHistory> educationHallRecordHistoryWriter() {

        return new RepositoryItemWriterBuilder<EducationHallRecordHistory>()
                .repository(educationHallRecordHistoryRepository)
                .methodName("save")
                .build();
    }

    @JobScope
    @Bean
    public Step educationHallDailyStudyTimeUpdateStep(ItemReader educationHallRecordDailyStudyTimeReader,
                                                       ItemProcessor educationHallRecordDailyStudyTimeUpdateProcessor,
                                                       ItemWriter educationHallRecordDailyStudyTimeWriter) {

        return stepBuilderFactory.get("educationHallDailyStudyTimeUpdateStep")
                .<EducationHallRecord, EducationHallRecord>chunk(10)
                .reader(educationHallRecordDailyStudyTimeReader)
                .processor(educationHallRecordDailyStudyTimeUpdateProcessor)
                .writer(educationHallRecordDailyStudyTimeWriter)
                .build();
    }

    @StepScope
    @Bean
    public RepositoryItemReader<EducationHallRecord> educationHallRecordDailyStudyTimeReader() {

        return new RepositoryItemReaderBuilder<EducationHallRecord>()
                .name("educationHallRecordDailyStudyTimeReader")
                .repository(educationHallRecordRepository)
                .methodName("findAll")
                .pageSize(10)
                .arguments(Arrays.asList())
                .sorts(Collections.singletonMap("id", Sort.Direction.ASC))
                .build();
    }

    @StepScope
    @Bean
    public ItemProcessor<EducationHallRecord, EducationHallRecord> educationHallRecordDailyStudyTimeUpdateProcessor() {

        return new ItemProcessor<EducationHallRecord, EducationHallRecord>() {
            @Override
            public EducationHallRecord process(EducationHallRecord item) throws Exception {
                item.resetDailyStudyTime();
                return item;
            }
        };
    }

    @StepScope
    @Bean
    public RepositoryItemWriter<EducationHallRecord> educationHallRecordDailyStudyTimeWriter() {

        return new RepositoryItemWriterBuilder<EducationHallRecord>()
                .repository(educationHallRecordRepository)
                .methodName("save")
                .build();
    }

}
