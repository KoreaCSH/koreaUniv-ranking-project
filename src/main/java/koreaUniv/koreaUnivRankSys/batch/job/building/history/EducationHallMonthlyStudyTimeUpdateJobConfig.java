package koreaUniv.koreaUnivRankSys.batch.job.building.history;

import koreaUniv.koreaUnivRankSys.batch.common.JobLoggerListener;
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
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Sort;

import java.util.Arrays;
import java.util.Collections;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class EducationHallMonthlyStudyTimeUpdateJobConfig {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final EducationHallRecordRepository educationHallRecordRepository;

    @Bean
    public Job educationHallRecordMonthlyStudyTimeUpdateJob(Step educationHallRecordMonthlyStudyTimeUpdateStep) {
        return jobBuilderFactory.get("educationHallRecordMonthlyStudyTimeUpdateJob")
                .incrementer(new RunIdIncrementer())
                .listener(new JobLoggerListener())
                .start(educationHallRecordMonthlyStudyTimeUpdateStep)
                .build();
    }

    @JobScope
    @Bean
    public Step educationHallRecordMonthlyStudyTimeUpdateStep(ItemReader educationHallRecordMonthlyStudyTimeReader,
                                                              ItemProcessor educationHallRecordMonthlyStudyTimeUpdateProcessor,
                                                              ItemWriter educationHallRecordMonthlyStudyTimeWriter) {

        return stepBuilderFactory.get("educationHallRecordMonthlyStudyTimeUpdateStep")
                .<EducationHallRecord, EducationHallRecord>chunk(10)
                .reader(educationHallRecordMonthlyStudyTimeReader)
                .processor(educationHallRecordMonthlyStudyTimeUpdateProcessor)
                .writer(educationHallRecordMonthlyStudyTimeWriter)
                .build();
    }

    @StepScope
    @Bean
    public RepositoryItemReader<EducationHallRecord> educationHallRecordMonthlyStudyTimeReader() {

        return new RepositoryItemReaderBuilder<EducationHallRecord>()
                .name("educationHallRecordMonthlyStudyTimeReader")
                .repository(educationHallRecordRepository)
                .methodName("findAll")
                .pageSize(10)
                .arguments(Arrays.asList())
                .sorts(Collections.singletonMap("id", Sort.Direction.ASC))
                .build();
    }

    @StepScope
    @Bean
    public ItemProcessor<EducationHallRecord, EducationHallRecord> educationHallRecordMonthlyStudyTimeUpdateProcessor() {

        return new ItemProcessor<EducationHallRecord, EducationHallRecord>() {
            @Override
            public EducationHallRecord process(EducationHallRecord item) throws Exception {
                item.resetMonthlyStudyTime();
                return item;
            }
        };
    }

    @StepScope
    @Bean
    public RepositoryItemWriter<EducationHallRecord> educationHallRecordMonthlyStudyTimeWriter() {

        return new RepositoryItemWriterBuilder<EducationHallRecord>()
                .repository(educationHallRecordRepository)
                .methodName("save")
                .build();
    }

}
