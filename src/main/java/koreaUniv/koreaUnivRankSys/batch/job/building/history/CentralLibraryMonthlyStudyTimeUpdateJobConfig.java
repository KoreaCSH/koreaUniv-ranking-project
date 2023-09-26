package koreaUniv.koreaUnivRankSys.batch.job.building.history;

import koreaUniv.koreaUnivRankSys.batch.common.JobLoggerListener;
import koreaUniv.koreaUnivRankSys.domain.building.domain.CentralLibraryRecord;
import koreaUniv.koreaUnivRankSys.domain.building.repository.CentralLibraryRecordRepository;
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
public class CentralLibraryMonthlyStudyTimeUpdateJobConfig {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final CentralLibraryRecordRepository centralLibraryRecordRepository;

    @Bean
    public Job centralLibraryRecordMonthlyStudyTimeUpdateJob(Step centralLibraryRecordMonthlyStudyTimeUpdateStep) {
        return jobBuilderFactory.get("centralLibraryRecordMonthlyStudyTimeUpdateJob")
                .incrementer(new RunIdIncrementer())
                .listener(new JobLoggerListener())
                .start(centralLibraryRecordMonthlyStudyTimeUpdateStep)
                .build();
    }

    @JobScope
    @Bean
    public Step centralLibraryRecordMonthlyStudyTimeUpdateStep(ItemReader centralLibraryRecordMonthlyStudyTimeReader,
                                                               ItemProcessor centralLibraryRecordMonthlyStudyTimeUpdateProcessor,
                                                               ItemWriter centralLibraryRecordMonthlyStudyTimeWriter) {

        return stepBuilderFactory.get("centralLibraryRecordMonthlyStudyTimeUpdateStep")
                .<CentralLibraryRecord, CentralLibraryRecord>chunk(10)
                .reader(centralLibraryRecordMonthlyStudyTimeReader)
                .processor(centralLibraryRecordMonthlyStudyTimeUpdateProcessor)
                .writer(centralLibraryRecordMonthlyStudyTimeWriter)
                .build();
    }

    @StepScope
    @Bean
    public RepositoryItemReader<CentralLibraryRecord> centralLibraryRecordMonthlyStudyTimeReader() {

        return new RepositoryItemReaderBuilder<CentralLibraryRecord>()
                .name("centralLibraryRecordMonthlyStudyTimeReader")
                .repository(centralLibraryRecordRepository)
                .methodName("findAll")
                .pageSize(10)
                .arguments(Arrays.asList())
                .sorts(Collections.singletonMap("id", Sort.Direction.ASC))
                .build();
    }

    @StepScope
    @Bean
    public ItemProcessor<CentralLibraryRecord, CentralLibraryRecord> centralLibraryRecordMonthlyStudyTimeUpdateProcessor() {

        return new ItemProcessor<CentralLibraryRecord, CentralLibraryRecord>() {
            @Override
            public CentralLibraryRecord process(CentralLibraryRecord item) throws Exception {
                item.resetMonthlyStudyTime();
                return item;
            }
        };
    }

    @StepScope
    @Bean
    public RepositoryItemWriter<CentralLibraryRecord> centralLibraryRecordMonthlyStudyTimeWriter() {

        return new RepositoryItemWriterBuilder<CentralLibraryRecord>()
                .repository(centralLibraryRecordRepository)
                .methodName("save")
                .build();
    }

}
