package koreaUniv.koreaUnivRankSys.batch.job.building.top3;

import koreaUniv.koreaUnivRankSys.batch.common.JobLoggerListener;
import koreaUniv.koreaUnivRankSys.batch.domain.building.top3.MemorialHallTop3;
import koreaUniv.koreaUnivRankSys.batch.domain.building.top3.mapper.MemorialHallTop3RowMapper;
import koreaUniv.koreaUnivRankSys.batch.repository.building.top3.MemorialHallTop3Repository;
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
public class MemorialHallTopRecordJobConfig {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    private final MemorialHallTop3Repository memorialHallTop3Repository;
    private final MemorialHallRecordRepository memorialHallRecordRepository;

    private final DataSource dataSource;

    @Bean
    public Job memorialHallTopRecordJob(Step memorialHallGetTop3RecordStep,
                                      Step memorialHallStudyTimeUpdateStep) {

        return jobBuilderFactory.get("memorialHallTopRecordJob")
                .incrementer(new RunIdIncrementer())
                .listener(new JobLoggerListener())
                .start(memorialHallGetTop3RecordStep)
                .on("COMPLETED").to(memorialHallStudyTimeUpdateStep)
                .from(memorialHallGetTop3RecordStep)
                .end()
                .build();
    }

    @JobScope
    @Bean
    public Step memorialHallGetTop3RecordStep(ItemReader<MemorialHallTop3> memorialHallGetTop3Reader,
                                            ItemWriter memorialHallTop3Writer) {

        return stepBuilderFactory.get("memorialHallGetTop3RecordStep")
                .<MemorialHallTop3, MemorialHallTop3>chunk(3)
                .reader(memorialHallGetTop3Reader)
                .writer(memorialHallTop3Writer)
                .build();
    }

    @JobScope
    @Bean
    public Step memorialHallStudyTimeUpdateStep(ItemReader memorialHallRecordReader,
                                              ItemProcessor memorialHallRecordUpdateProcessor,
                                              ItemWriter memorialHallRecordWriter) {

        return stepBuilderFactory.get("memorialHallStudyTimeUpdateStep")
                .<MemorialHallRecord, MemorialHallRecord>chunk(10)
                .reader(memorialHallRecordReader)
                .processor(memorialHallRecordUpdateProcessor)
                .writer(memorialHallRecordWriter)
                .build();
    }

    @StepScope
    @Bean
    public JdbcCursorItemReader<MemorialHallTop3> memorialHallGetTop3Reader() {

        return new JdbcCursorItemReaderBuilder<MemorialHallTop3>()
                .dataSource(dataSource)
                .name("memorialHallGetTop3Reader")
                .sql("select path, nick_name, weekly_study_time, row_number() over (order by weekly_study_time desc) as \'ranking\' " +
                        "from (member natural left outer join member_image) join memorial_hall_record " +
                        "where member.memorial_hall_record_id = memorial_hall_record.memorial_hall_record_id limit 3")
                .rowMapper(new MemorialHallTop3RowMapper())
                .build();
    }

    @StepScope
    @Bean
    public RepositoryItemWriter<MemorialHallTop3> memorialHallTop3Writer() {

        return new RepositoryItemWriterBuilder<MemorialHallTop3>()
                .repository(memorialHallTop3Repository)
                .methodName("save")
                .build();
    }

    @StepScope
    @Bean
    public RepositoryItemReader<MemorialHallRecord> memorialHallRecordReader() {

        return new RepositoryItemReaderBuilder<MemorialHallRecord>()
                .name("memorialHallRecordReader")
                .repository(memorialHallRecordRepository)
                .methodName("findAll")
                .pageSize(10)
                .arguments(Arrays.asList())
                .sorts(Collections.singletonMap("id", Sort.Direction.ASC))
                .build();
    }

    @StepScope
    @Bean
    public ItemProcessor<MemorialHallRecord, MemorialHallRecord> memorialHallRecordUpdateProcessor() {
        return new ItemProcessor<MemorialHallRecord, MemorialHallRecord>() {
            @Override
            public MemorialHallRecord process(MemorialHallRecord item) throws Exception {
                item.resetWeeklyStudyTime();
                return item;
            }
        };
    }

    @StepScope
    @Bean
    public RepositoryItemWriter<MemorialHallRecord> memorialHallRecordWriter() {

        return new RepositoryItemWriterBuilder<MemorialHallRecord>()
                .repository(memorialHallRecordRepository)
                .methodName("save")
                .build();
    }

}
