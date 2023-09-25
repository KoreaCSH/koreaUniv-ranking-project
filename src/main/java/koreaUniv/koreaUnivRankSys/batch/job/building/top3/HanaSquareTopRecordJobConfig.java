package koreaUniv.koreaUnivRankSys.batch.job.building.top3;

import koreaUniv.koreaUnivRankSys.batch.common.JobLoggerListener;
import koreaUniv.koreaUnivRankSys.batch.domain.building.top3.HanaSquareTop3;
import koreaUniv.koreaUnivRankSys.batch.domain.building.top3.mapper.HanaSquareTop3RowMapper;
import koreaUniv.koreaUnivRankSys.batch.repository.building.top3.HanaSquareTop3Repository;
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
public class HanaSquareTopRecordJobConfig {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    private final HanaSquareTop3Repository hanaSquareTop3Repository;
    private final HanaSquareRecordRepository hanaSquareRecordRepository;

    private final DataSource dataSource;

    @Bean
    public Job hanaSquareTopRecordJob(Step hanaSquareGetTop3RecordStep,
                                         Step hanaSquareStudyTimeUpdateStep) {

        return jobBuilderFactory.get("hanaSquareTopRecordJob")
                .incrementer(new RunIdIncrementer())
                .listener(new JobLoggerListener())
                .start(hanaSquareGetTop3RecordStep)
                .on("COMPLETED").to(hanaSquareStudyTimeUpdateStep)
                .from(hanaSquareGetTop3RecordStep)
                .end()
                .build();
    }

    @JobScope
    @Bean
    public Step hanaSquareGetTop3RecordStep(ItemReader<HanaSquareTop3> hanaSquareGetTop3Reader,
                                               ItemWriter hanaSquareTop3Writer) {

        return stepBuilderFactory.get("hanaSquareGetTop3RecordStep")
                .<HanaSquareTop3, HanaSquareTop3>chunk(3)
                .reader(hanaSquareGetTop3Reader)
                .writer(hanaSquareTop3Writer)
                .build();
    }

    @JobScope
    @Bean
    public Step hanaSquareStudyTimeUpdateStep(ItemReader hanaSquareRecordReader,
                                                 ItemProcessor hanaSquareRecordUpdateProcessor,
                                                 ItemWriter hanaSquareRecordWriter) {

        return stepBuilderFactory.get("hanaSquareStudyTimeUpdateStep")
                .<HanaSquareRecord, HanaSquareRecord>chunk(10)
                .reader(hanaSquareRecordReader)
                .processor(hanaSquareRecordUpdateProcessor)
                .writer(hanaSquareRecordWriter)
                .build();
    }

    @StepScope
    @Bean
    public JdbcCursorItemReader<HanaSquareTop3> hanaSquareGetTop3Reader() {

        return new JdbcCursorItemReaderBuilder<HanaSquareTop3>()
                .dataSource(dataSource)
                .name("hanaSquareGetTop3Reader")
                .sql("select path, nick_name, weekly_study_time, row_number() over (order by weekly_study_time desc) as \'ranking\' " +
                        "from (member natural left outer join member_image) join hana_square_record " +
                        "where member.hana_square_record_id = hana_square_record.hana_square_record_id limit 3")
                .rowMapper(new HanaSquareTop3RowMapper())
                .build();
    }

    @StepScope
    @Bean
    public RepositoryItemWriter<HanaSquareTop3> hanaSquareTop3Writer() {

        return new RepositoryItemWriterBuilder<HanaSquareTop3>()
                .repository(hanaSquareTop3Repository)
                .methodName("save")
                .build();
    }

    @StepScope
    @Bean
    public RepositoryItemReader<HanaSquareRecord> hanaSquareRecordReader() {

        return new RepositoryItemReaderBuilder<HanaSquareRecord>()
                .name("hanaSquareRecordReader")
                .repository(hanaSquareRecordRepository)
                .methodName("findAll")
                .pageSize(10)
                .arguments(Arrays.asList())
                .sorts(Collections.singletonMap("id", Sort.Direction.ASC))
                .build();
    }

    @StepScope
    @Bean
    public ItemProcessor<HanaSquareRecord, HanaSquareRecord> hanaSquareRecordUpdateProcessor() {
        return new ItemProcessor<HanaSquareRecord, HanaSquareRecord>() {
            @Override
            public HanaSquareRecord process(HanaSquareRecord item) throws Exception {
                item.resetWeeklyStudyTime();
                return item;
            }
        };
    }

    @StepScope
    @Bean
    public RepositoryItemWriter<HanaSquareRecord> hanaSquareRecordWriter() {

        return new RepositoryItemWriterBuilder<HanaSquareRecord>()
                .repository(hanaSquareRecordRepository)
                .methodName("save")
                .build();
    }

}
