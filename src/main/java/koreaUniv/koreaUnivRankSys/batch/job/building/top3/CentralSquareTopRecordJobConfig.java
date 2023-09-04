package koreaUniv.koreaUnivRankSys.batch.job.building.top3;

import koreaUniv.koreaUnivRankSys.batch.common.JobLoggerListener;
import koreaUniv.koreaUnivRankSys.batch.domain.building.top3.CentralSquareTop3;
import koreaUniv.koreaUnivRankSys.batch.domain.building.top3.mapper.CentralSquareTop3RowMapper;
import koreaUniv.koreaUnivRankSys.batch.repository.building.top3.CentralSquareTop3Repository;
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
public class CentralSquareTopRecordJobConfig {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    private final CentralSquareTop3Repository centralSquareTop3Repository;
    private final CentralSquareRecordRepository centralSquareRecordRepository;

    private final DataSource dataSource;

    @Bean
    public Job centralSquareTopRecordJob(Step centralSquareGetTop3RecordStep,
                                          Step centralSquareStudyTimeUpdateStep) {

        return jobBuilderFactory.get("centralSquareTopRecordJob")
                .incrementer(new RunIdIncrementer())
                .listener(new JobLoggerListener())
                .start(centralSquareGetTop3RecordStep)
                .on("COMPLETED").to(centralSquareStudyTimeUpdateStep)
                .from(centralSquareGetTop3RecordStep)
                .end()
                .build();
    }

    @JobScope
    @Bean
    public Step centralSquareGetTop3RecordStep(ItemReader<CentralSquareTop3> centralSquareGetTop3Reader,
                                                ItemWriter centralSquareTop3Writer) {

        return stepBuilderFactory.get("centralSquareGetTop3RecordStep")
                .<CentralSquareTop3, CentralSquareTop3>chunk(3)
                .reader(centralSquareGetTop3Reader)
                .writer(centralSquareTop3Writer)
                .build();
    }

    @JobScope
    @Bean
    public Step centralSquareStudyTimeUpdateStep(ItemReader centralSquareRecordReader,
                                                  ItemProcessor centralSquareRecordUpdateProcessor,
                                                  ItemWriter centralSquareRecordWriter) {

        return stepBuilderFactory.get("centralSquareStudyTimeUpdateStep")
                .<CentralSquareRecord, CentralSquareRecord>chunk(10)
                .reader(centralSquareRecordReader)
                .processor(centralSquareRecordUpdateProcessor)
                .writer(centralSquareRecordWriter)
                .build();
    }

    @StepScope
    @Bean
    public JdbcCursorItemReader<CentralSquareTop3> centralSquareGetTop3Reader() {

        return new JdbcCursorItemReaderBuilder<CentralSquareTop3>()
                .dataSource(dataSource)
                .name("centralSquareGetTop3Reader")
                .sql("select path, nick_name, weekly_study_time, row_number() over (order by weekly_study_time desc) as \'ranking\' " +
                        "from (member natural left outer join member_image) join central_square_record " +
                        "where member.central_square_record_id = central_square_record.central_square_record_id limit 3")
                .rowMapper(new CentralSquareTop3RowMapper())
                .build();
    }

    @StepScope
    @Bean
    public RepositoryItemWriter<CentralSquareTop3> centralSquareTop3Writer() {

        return new RepositoryItemWriterBuilder<CentralSquareTop3>()
                .repository(centralSquareTop3Repository)
                .methodName("save")
                .build();
    }

    @StepScope
    @Bean
    public RepositoryItemReader<CentralSquareRecord> centralSquareRecordReader() {

        return new RepositoryItemReaderBuilder<CentralSquareRecord>()
                .name("centralSquareRecordReader")
                .repository(centralSquareRecordRepository)
                .methodName("findAll")
                .pageSize(10)
                .arguments(Arrays.asList())
                .sorts(Collections.singletonMap("id", Sort.Direction.ASC))
                .build();
    }

    @StepScope
    @Bean
    public ItemProcessor<CentralSquareRecord, CentralSquareRecord> centralSquareRecordUpdateProcessor() {
        return new ItemProcessor<CentralSquareRecord, CentralSquareRecord>() {
            @Override
            public CentralSquareRecord process(CentralSquareRecord item) throws Exception {
                item.resetWeeklyStudyTime();
                return item;
            }
        };
    }

    @StepScope
    @Bean
    public RepositoryItemWriter<CentralSquareRecord> centralSquareRecordWriter() {

        return new RepositoryItemWriterBuilder<CentralSquareRecord>()
                .repository(centralSquareRecordRepository)
                .methodName("save")
                .build();
    }

}
