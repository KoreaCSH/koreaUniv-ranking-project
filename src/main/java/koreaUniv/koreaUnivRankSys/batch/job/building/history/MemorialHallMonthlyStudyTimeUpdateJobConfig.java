package koreaUniv.koreaUnivRankSys.batch.job.building.history;

import koreaUniv.koreaUnivRankSys.batch.common.JobLoggerListener;
import koreaUniv.koreaUnivRankSys.domain.building.domain.MemorialHallRecord;
import koreaUniv.koreaUnivRankSys.domain.building.repository.MemorialHallRecordRepository;
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
public class MemorialHallMonthlyStudyTimeUpdateJobConfig {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final MemorialHallRecordRepository memorialHallRecordRepository;

    @Bean
    public Job memorialHallRecordMonthlyStudyTimeUpdateJob(Step memorialHallRecordMonthlyStudyTimeUpdateStep) {
        return jobBuilderFactory.get("memorialHallRecordMonthlyStudyTimeUpdateJob")
                .incrementer(new RunIdIncrementer())
                .listener(new JobLoggerListener())
                .start(memorialHallRecordMonthlyStudyTimeUpdateStep)
                .build();
    }

    @JobScope
    @Bean
    public Step memorialHallRecordMonthlyStudyTimeUpdateStep(ItemReader memorialHallRecordMonthlyStudyTimeReader,
                                                           ItemProcessor memorialHallRecordMonthlyStudyTimeUpdateProcessor,
                                                           ItemWriter memorialHallRecordMonthlyStudyTimeWriter) {

        return stepBuilderFactory.get("memorialHallRecordMonthlyStudyTimeUpdateStep")
                .<MemorialHallRecord, MemorialHallRecord>chunk(10)
                .reader(memorialHallRecordMonthlyStudyTimeReader)
                .processor(memorialHallRecordMonthlyStudyTimeUpdateProcessor)
                .writer(memorialHallRecordMonthlyStudyTimeWriter)
                .build();
    }

    @StepScope
    @Bean
    public RepositoryItemReader<MemorialHallRecord> memorialHallRecordMonthlyStudyTimeReader() {

        return new RepositoryItemReaderBuilder<MemorialHallRecord>()
                .name("memorialHallRecordMonthlyStudyTimeReader")
                .repository(memorialHallRecordRepository)
                .methodName("findAll")
                .pageSize(10)
                .arguments(Arrays.asList())
                .sorts(Collections.singletonMap("id", Sort.Direction.ASC))
                .build();
    }

    @StepScope
    @Bean
    public ItemProcessor<MemorialHallRecord, MemorialHallRecord> memorialHallRecordMonthlyStudyTimeUpdateProcessor() {

        return new ItemProcessor<MemorialHallRecord, MemorialHallRecord>() {
            @Override
            public MemorialHallRecord process(MemorialHallRecord item) throws Exception {
                item.resetMonthlyStudyTime();
                return item;
            }
        };
    }

    @StepScope
    @Bean
    public RepositoryItemWriter<MemorialHallRecord> memorialHallRecordMonthlyStudyTimeWriter() {

        return new RepositoryItemWriterBuilder<MemorialHallRecord>()
                .repository(memorialHallRecordRepository)
                .methodName("save")
                .build();
    }

}
