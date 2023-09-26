package koreaUniv.koreaUnivRankSys.batch.job.building.history;

import koreaUniv.koreaUnivRankSys.batch.common.JobLoggerListener;
import koreaUniv.koreaUnivRankSys.domain.building.domain.HanaSquareRecord;
import koreaUniv.koreaUnivRankSys.domain.building.repository.HanaSquareRecordRepository;
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
public class HanaSquareMonthlyStudyTimeUpdateJobConfig {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final HanaSquareRecordRepository hanaSquareRecordRepository;

    @Bean
    public Job hanaSquareRecordMonthlyStudyTimeUpdateJob(Step hanaSquareRecordMonthlyStudyTimeUpdateStep) {
        return jobBuilderFactory.get("hanaSquareRecordMonthlyStudyTimeUpdateJob")
                .incrementer(new RunIdIncrementer())
                .listener(new JobLoggerListener())
                .start(hanaSquareRecordMonthlyStudyTimeUpdateStep)
                .build();
    }

    @JobScope
    @Bean
    public Step hanaSquareRecordMonthlyStudyTimeUpdateStep(ItemReader hanaSquareRecordMonthlyStudyTimeReader,
                                                              ItemProcessor hanaSquareRecordMonthlyStudyTimeUpdateProcessor,
                                                              ItemWriter hanaSquareRecordMonthlyStudyTimeWriter) {

        return stepBuilderFactory.get("hanaSquareRecordMonthlyStudyTimeUpdateStep")
                .<HanaSquareRecord, HanaSquareRecord>chunk(10)
                .reader(hanaSquareRecordMonthlyStudyTimeReader)
                .processor(hanaSquareRecordMonthlyStudyTimeUpdateProcessor)
                .writer(hanaSquareRecordMonthlyStudyTimeWriter)
                .build();
    }

    @StepScope
    @Bean
    public RepositoryItemReader<HanaSquareRecord> hanaSquareRecordMonthlyStudyTimeReader() {

        return new RepositoryItemReaderBuilder<HanaSquareRecord>()
                .name("hanaSquareRecordMonthlyStudyTimeReader")
                .repository(hanaSquareRecordRepository)
                .methodName("findAll")
                .pageSize(10)
                .arguments(Arrays.asList())
                .sorts(Collections.singletonMap("id", Sort.Direction.ASC))
                .build();
    }

    @StepScope
    @Bean
    public ItemProcessor<HanaSquareRecord, HanaSquareRecord> hanaSquareRecordMonthlyStudyTimeUpdateProcessor() {

        return new ItemProcessor<HanaSquareRecord, HanaSquareRecord>() {
            @Override
            public HanaSquareRecord process(HanaSquareRecord item) throws Exception {
                item.resetMonthlyStudyTime();
                return item;
            }
        };
    }

    @StepScope
    @Bean
    public RepositoryItemWriter<HanaSquareRecord> hanaSquareRecordMonthlyStudyTimeWriter() {

        return new RepositoryItemWriterBuilder<HanaSquareRecord>()
                .repository(hanaSquareRecordRepository)
                .methodName("save")
                .build();
    }

}
