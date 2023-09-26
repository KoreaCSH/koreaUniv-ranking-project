package koreaUniv.koreaUnivRankSys.batch.job.building.history;

import koreaUniv.koreaUnivRankSys.batch.common.JobLoggerListener;
import koreaUniv.koreaUnivRankSys.domain.building.domain.CentralSquareRecord;
import koreaUniv.koreaUnivRankSys.domain.building.repository.CentralSquareRecordRepository;
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
public class CentralSquareMonthlyStudyTimeUpdateJobConfig {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final CentralSquareRecordRepository centralSquareRecordRepository;

    @Bean
    public Job centralSquareRecordMonthlyStudyTimeUpdateJob(Step centralSquareRecordMonthlyStudyTimeUpdateStep) {
        return jobBuilderFactory.get("centralSquareRecordMonthlyStudyTimeUpdateJob")
                .incrementer(new RunIdIncrementer())
                .listener(new JobLoggerListener())
                .start(centralSquareRecordMonthlyStudyTimeUpdateStep)
                .build();
    }

    @JobScope
    @Bean
    public Step centralSquareRecordMonthlyStudyTimeUpdateStep(ItemReader centralSquareRecordMonthlyStudyTimeReader,
                                                               ItemProcessor centralSquareRecordMonthlyStudyTimeUpdateProcessor,
                                                               ItemWriter centralSquareRecordMonthlyStudyTimeWriter) {

        return stepBuilderFactory.get("centralSquareRecordMonthlyStudyTimeUpdateStep")
                .<CentralSquareRecord, CentralSquareRecord>chunk(10)
                .reader(centralSquareRecordMonthlyStudyTimeReader)
                .processor(centralSquareRecordMonthlyStudyTimeUpdateProcessor)
                .writer(centralSquareRecordMonthlyStudyTimeWriter)
                .build();
    }

    @StepScope
    @Bean
    public RepositoryItemReader<CentralSquareRecord> centralSquareRecordMonthlyStudyTimeReader() {

        return new RepositoryItemReaderBuilder<CentralSquareRecord>()
                .name("centralSquareRecordMonthlyStudyTimeReader")
                .repository(centralSquareRecordRepository)
                .methodName("findAll")
                .pageSize(10)
                .arguments(Arrays.asList())
                .sorts(Collections.singletonMap("id", Sort.Direction.ASC))
                .build();
    }

    @StepScope
    @Bean
    public ItemProcessor<CentralSquareRecord, CentralSquareRecord> centralSquareRecordMonthlyStudyTimeUpdateProcessor() {

        return new ItemProcessor<CentralSquareRecord, CentralSquareRecord>() {
            @Override
            public CentralSquareRecord process(CentralSquareRecord item) throws Exception {
                item.resetMonthlyStudyTime();
                return item;
            }
        };
    }

    @StepScope
    @Bean
    public RepositoryItemWriter<CentralSquareRecord> centralSquareRecordMonthlyStudyTimeWriter() {

        return new RepositoryItemWriterBuilder<CentralSquareRecord>()
                .repository(centralSquareRecordRepository)
                .methodName("save")
                .build();
    }

}
