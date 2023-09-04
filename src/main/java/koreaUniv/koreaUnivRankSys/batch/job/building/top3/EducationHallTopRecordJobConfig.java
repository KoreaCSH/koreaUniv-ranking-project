package koreaUniv.koreaUnivRankSys.batch.job.building.top3;

import koreaUniv.koreaUnivRankSys.batch.common.JobLoggerListener;
import koreaUniv.koreaUnivRankSys.batch.domain.building.top3.EducationHallTop3;
import koreaUniv.koreaUnivRankSys.batch.domain.building.top3.mapper.EducationHallTop3RowMapper;
import koreaUniv.koreaUnivRankSys.batch.repository.building.top3.EducationHallTop3Repository;
import koreaUniv.koreaUnivRankSys.domain.building.domain.EducationHallRecord;
import koreaUniv.koreaUnivRankSys.domain.building.repository.EducationHallRecordRepository;
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
public class EducationHallTopRecordJobConfig {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    private final EducationHallTop3Repository educationHallTop3Repository;
    private final EducationHallRecordRepository educationHallRecordRepository;

    private final DataSource dataSource;

    @Bean
    public Job educationHallTopRecordJob(Step educationHallGetTop3RecordStep,
                                         Step educationHallStudyTimeUpdateStep) {

        return jobBuilderFactory.get("educationHallTopRecordJob")
                .incrementer(new RunIdIncrementer())
                .listener(new JobLoggerListener())
                .start(educationHallGetTop3RecordStep)
                .on("COMPLETED").to(educationHallStudyTimeUpdateStep)
                .from(educationHallGetTop3RecordStep)
                .end()
                .build();
    }

    @JobScope
    @Bean
    public Step educationHallGetTop3RecordStep(ItemReader<EducationHallTop3> educationHallGetTop3Reader,
                                               ItemWriter educationHallTop3Writer) {

        return stepBuilderFactory.get("educationHallGetTop3RecordStep")
                .<EducationHallTop3, EducationHallTop3>chunk(3)
                .reader(educationHallGetTop3Reader)
                .writer(educationHallTop3Writer)
                .build();
    }

    @JobScope
    @Bean
    public Step educationHallStudyTimeUpdateStep(ItemReader educationHallRecordReader,
                                                 ItemProcessor educationHallRecordUpdateProcessor,
                                                 ItemWriter educationHallRecordWriter) {

        return stepBuilderFactory.get("educationHallStudyTimeUpdateStep")
                .<EducationHallRecord, EducationHallRecord>chunk(10)
                .reader(educationHallRecordReader)
                .processor(educationHallRecordUpdateProcessor)
                .writer(educationHallRecordWriter)
                .build();
    }

    @StepScope
    @Bean
    public JdbcCursorItemReader<EducationHallTop3> educationHallGetTop3Reader() {

        return new JdbcCursorItemReaderBuilder<EducationHallTop3>()
                .dataSource(dataSource)
                .name("educationHallGetTop3Reader")
                .sql("select path, nick_name, weekly_study_time, row_number() over (order by weekly_study_time desc) as \'ranking\' " +
                        "from (member natural left outer join member_image) join education_hall_record " +
                        "where member.education_hall_record_id = education_hall_record.education_hall_record_id limit 3")
                .rowMapper(new EducationHallTop3RowMapper())
                .build();
    }

    @StepScope
    @Bean
    public RepositoryItemWriter<EducationHallTop3> educationHallTop3Writer() {

        return new RepositoryItemWriterBuilder<EducationHallTop3>()
                .repository(educationHallTop3Repository)
                .methodName("save")
                .build();
    }

    @StepScope
    @Bean
    public RepositoryItemReader<EducationHallRecord> educationHallRecordReader() {

        return new RepositoryItemReaderBuilder<EducationHallRecord>()
                .name("educationHallRecordReader")
                .repository(educationHallRecordRepository)
                .methodName("findAll")
                .pageSize(10)
                .arguments(Arrays.asList())
                .sorts(Collections.singletonMap("id", Sort.Direction.ASC))
                .build();
    }

    @StepScope
    @Bean
    public ItemProcessor<EducationHallRecord, EducationHallRecord> educationHallRecordUpdateProcessor() {
        return new ItemProcessor<EducationHallRecord, EducationHallRecord>() {
            @Override
            public EducationHallRecord process(EducationHallRecord item) throws Exception {
                item.resetWeeklyStudyTime();
                return item;
            }
        };
    }

    @StepScope
    @Bean
    public RepositoryItemWriter<EducationHallRecord> educationHallRecordWriter() {

        return new RepositoryItemWriterBuilder<EducationHallRecord>()
                .repository(educationHallRecordRepository)
                .methodName("save")
                .build();
    }

}
