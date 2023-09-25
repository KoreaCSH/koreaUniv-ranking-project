package koreaUniv.koreaUnivRankSys.batch.job.building.top3;

import koreaUniv.koreaUnivRankSys.batch.common.JobLoggerListener;
import koreaUniv.koreaUnivRankSys.batch.domain.building.top3.ScienceLibraryTop3;
import koreaUniv.koreaUnivRankSys.batch.domain.building.top3.mapper.ScienceLibraryTop3RowMapper;
import koreaUniv.koreaUnivRankSys.batch.repository.building.top3.ScienceLibraryTop3Repository;
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
public class ScienceLibraryTopRecordJobConfig {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    private final ScienceLibraryTop3Repository scienceLibraryTop3Repository;
    private final ScienceLibraryRecordRepository scienceLibraryRecordRepository;

    private final DataSource dataSource;

    @Bean
    public Job scienceLibraryTopRecordJob(Step scienceLibraryGetTop3RecordStep,
                                        Step scienceLibraryStudyTimeUpdateStep) {

        return jobBuilderFactory.get("scienceLibraryTopRecordJob")
                .incrementer(new RunIdIncrementer())
                .listener(new JobLoggerListener())
                .start(scienceLibraryGetTop3RecordStep)
                .on("COMPLETED").to(scienceLibraryStudyTimeUpdateStep)
                .from(scienceLibraryGetTop3RecordStep)
                .end()
                .build();
    }

    @JobScope
    @Bean
    public Step scienceLibraryGetTop3RecordStep(ItemReader<ScienceLibraryTop3> scienceLibraryGetTop3Reader,
                                              ItemWriter scienceLibraryTop3Writer) {

        return stepBuilderFactory.get("scienceLibraryGetTop3RecordStep")
                .<ScienceLibraryTop3, ScienceLibraryTop3>chunk(3)
                .reader(scienceLibraryGetTop3Reader)
                .writer(scienceLibraryTop3Writer)
                .build();
    }

    @JobScope
    @Bean
    public Step scienceLibraryStudyTimeUpdateStep(ItemReader scienceLibraryRecordReader,
                                                ItemProcessor scienceLibraryRecordUpdateProcessor,
                                                ItemWriter scienceLibraryRecordWriter) {

        return stepBuilderFactory.get("scienceLibraryStudyTimeUpdateStep")
                .<ScienceLibraryRecord, ScienceLibraryRecord>chunk(10)
                .reader(scienceLibraryRecordReader)
                .processor(scienceLibraryRecordUpdateProcessor)
                .writer(scienceLibraryRecordWriter)
                .build();
    }

    @StepScope
    @Bean
    public JdbcCursorItemReader<ScienceLibraryTop3> scienceLibraryGetTop3Reader() {

        return new JdbcCursorItemReaderBuilder<ScienceLibraryTop3>()
                .dataSource(dataSource)
                .name("scienceLibraryGetTop3Reader")
                .sql("select path, nick_name, weekly_study_time, row_number() over (order by weekly_study_time desc) as \'ranking\' " +
                        "from (member natural left outer join member_image) join science_library_record " +
                        "where member.science_library_record_id = science_library_record.science_library_record_id limit 3")
                .rowMapper(new ScienceLibraryTop3RowMapper())
                .build();
    }

    @StepScope
    @Bean
    public RepositoryItemWriter<ScienceLibraryTop3> scienceLibraryTop3Writer() {

        return new RepositoryItemWriterBuilder<ScienceLibraryTop3>()
                .repository(scienceLibraryTop3Repository)
                .methodName("save")
                .build();
    }

    @StepScope
    @Bean
    public RepositoryItemReader<ScienceLibraryRecord> scienceLibraryRecordReader() {

        return new RepositoryItemReaderBuilder<ScienceLibraryRecord>()
                .name("scienceLibraryRecordReader")
                .repository(scienceLibraryRecordRepository)
                .methodName("findAll")
                .pageSize(10)
                .arguments(Arrays.asList())
                .sorts(Collections.singletonMap("id", Sort.Direction.ASC))
                .build();
    }

    @StepScope
    @Bean
    public ItemProcessor<ScienceLibraryRecord, ScienceLibraryRecord> scienceLibraryRecordUpdateProcessor() {
        return new ItemProcessor<ScienceLibraryRecord, ScienceLibraryRecord>() {
            @Override
            public ScienceLibraryRecord process(ScienceLibraryRecord item) throws Exception {
                item.resetWeeklyStudyTime();
                return item;
            }
        };
    }

    @StepScope
    @Bean
    public RepositoryItemWriter<ScienceLibraryRecord> scienceLibraryRecordWriter() {

        return new RepositoryItemWriterBuilder<ScienceLibraryRecord>()
                .repository(scienceLibraryRecordRepository)
                .methodName("save")
                .build();
    }

}
