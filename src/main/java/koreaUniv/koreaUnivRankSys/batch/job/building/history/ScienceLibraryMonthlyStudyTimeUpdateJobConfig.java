package koreaUniv.koreaUnivRankSys.batch.job.building.history;

import koreaUniv.koreaUnivRankSys.batch.common.JobLoggerListener;
import koreaUniv.koreaUnivRankSys.domain.building.domain.ScienceLibraryRecord;
import koreaUniv.koreaUnivRankSys.domain.building.repository.ScienceLibraryRecordRepository;
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
public class ScienceLibraryMonthlyStudyTimeUpdateJobConfig {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final ScienceLibraryRecordRepository scienceLibraryRecordRepository;

    @Bean
    public Job scienceLibraryRecordMonthlyStudyTimeUpdateJob(Step scienceLibraryRecordMonthlyStudyTimeUpdateStep) {
        return jobBuilderFactory.get("scienceLibraryRecordMonthlyStudyTimeUpdateJob")
                .incrementer(new RunIdIncrementer())
                .listener(new JobLoggerListener())
                .start(scienceLibraryRecordMonthlyStudyTimeUpdateStep)
                .build();
    }

    @JobScope
    @Bean
    public Step scienceLibraryRecordMonthlyStudyTimeUpdateStep(ItemReader scienceLibraryRecordMonthlyStudyTimeReader,
                                                             ItemProcessor scienceLibraryRecordMonthlyStudyTimeUpdateProcessor,
                                                             ItemWriter scienceLibraryRecordMonthlyStudyTimeWriter) {

        return stepBuilderFactory.get("scienceLibraryRecordMonthlyStudyTimeUpdateStep")
                .<ScienceLibraryRecord, ScienceLibraryRecord>chunk(10)
                .reader(scienceLibraryRecordMonthlyStudyTimeReader)
                .processor(scienceLibraryRecordMonthlyStudyTimeUpdateProcessor)
                .writer(scienceLibraryRecordMonthlyStudyTimeWriter)
                .build();
    }

    @StepScope
    @Bean
    public RepositoryItemReader<ScienceLibraryRecord> scienceLibraryRecordMonthlyStudyTimeReader() {

        return new RepositoryItemReaderBuilder<ScienceLibraryRecord>()
                .name("scienceLibraryRecordMonthlyStudyTimeReader")
                .repository(scienceLibraryRecordRepository)
                .methodName("findAll")
                .pageSize(10)
                .arguments(Arrays.asList())
                .sorts(Collections.singletonMap("id", Sort.Direction.ASC))
                .build();
    }

    @StepScope
    @Bean
    public ItemProcessor<ScienceLibraryRecord, ScienceLibraryRecord> scienceLibraryRecordMonthlyStudyTimeUpdateProcessor() {

        return new ItemProcessor<ScienceLibraryRecord, ScienceLibraryRecord>() {
            @Override
            public ScienceLibraryRecord process(ScienceLibraryRecord item) throws Exception {
                item.resetMonthlyStudyTime();
                return item;
            }
        };
    }

    @StepScope
    @Bean
    public RepositoryItemWriter<ScienceLibraryRecord> scienceLibraryRecordMonthlyStudyTimeWriter() {

        return new RepositoryItemWriterBuilder<ScienceLibraryRecord>()
                .repository(scienceLibraryRecordRepository)
                .methodName("save")
                .build();
    }

}
