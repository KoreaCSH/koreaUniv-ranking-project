package koreaUniv.koreaUnivRankSys.batch.job.building.top3;

import koreaUniv.koreaUnivRankSys.domain.building.domain.CentralLibraryRecord;
import koreaUniv.koreaUnivRankSys.domain.building.repository.CentralLibraryRecordRepository;
import koreaUniv.koreaUnivRankSys.batch.domain.building.top3.CentralLibraryTop3;
import koreaUniv.koreaUnivRankSys.batch.repository.building.top3.CentralLibraryTop3Repository;
import koreaUniv.koreaUnivRankSys.batch.domain.building.top3.mapper.CentralLibraryTop3RowMapper;
import koreaUniv.koreaUnivRankSys.batch.common.JobLoggerListener;
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

// --spring.batch.job.names=centralLibraryTopRecordJob

@Slf4j
@Configuration
@RequiredArgsConstructor
public class CentralLibraryTopRecordJobConfig {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    private final CentralLibraryTop3Repository centralLibraryTop3Repository;
    private final CentralLibraryRecordRepository centralLibraryRecordRepository;

    private final DataSource dataSource;

    /**
     * @param centralLibraryGetTop3RecordStep step 을 매개변수로 받는다.
     * @return 중앙도서관 주간 랭킹 top 3 를 찾고, DB 로 이관하는 step 을 시작. 해당 step 이 성공적으로 종료되면 중앙도서관 주간 공부 기록을 0 으로 초기화하는 step 을 시작하는 Job 설정이다.
     */
    @Bean
    public Job centralLibraryTopRecordJob(Step centralLibraryGetTop3RecordStep,
                                          Step centralLibraryStudyTimeUpdateStep) {

        return jobBuilderFactory.get("centralLibraryTopRecordJob")
                .incrementer(new RunIdIncrementer())
                .listener(new JobLoggerListener())
                .start(centralLibraryGetTop3RecordStep)
                .on("COMPLETED").to(centralLibraryStudyTimeUpdateStep)
                .from(centralLibraryGetTop3RecordStep)
                .end()
                .build();
    }

    /**
     * @param centralLibraryGetTop3Reader DB로 부터 CentralLibraryTop3 를 읽어오는 ItemReader 이다.
     * @param centralLibraryTop3Writer DB에 CentralLibraryTop3 를 저장하는 ItemWriter 이다.
     * @return ItemReader 와 ItemWriter 를 활용하여 중앙도서관 주간 랭킹 top 3 를 찾고, DB 로 이관하는 Step 관련 설정이다.
     */
    @JobScope
    @Bean
    public Step centralLibraryGetTop3RecordStep(ItemReader<CentralLibraryTop3> centralLibraryGetTop3Reader,
                                                ItemWriter centralLibraryTop3Writer) {

        return stepBuilderFactory.get("centralLibraryGetTop3RecordStep")
                .<CentralLibraryTop3, CentralLibraryTop3>chunk(3)
                .reader(centralLibraryGetTop3Reader)
                .writer(centralLibraryTop3Writer)
                .build();
    }

    /**
     * @return 중앙도서관 주간 공부 기록을 0 으로 초기화하는 step
     */
    @JobScope
    @Bean
    public Step centralLibraryStudyTimeUpdateStep(ItemReader centralLibraryRecordReader,
                                              ItemProcessor centralLibraryRecordUpdateProcessor,
                                              ItemWriter centralLibraryRecordWriter) {

        return stepBuilderFactory.get("centralLibraryStudyTimeUpdate")
                .<CentralLibraryRecord, CentralLibraryRecord>chunk(10)
                .reader(centralLibraryRecordReader)
                .processor(centralLibraryRecordUpdateProcessor)
                .writer(centralLibraryRecordWriter)
                .build();
    }

    @StepScope
    @Bean
    public JdbcCursorItemReader<CentralLibraryTop3> centralLibraryGetTop3Reader() {

        return new JdbcCursorItemReaderBuilder<CentralLibraryTop3>()
                .dataSource(dataSource)
                .name("centralLibraryGetTop3Reader")
                .sql("select path, nick_name, weekly_study_time, row_number() over (order by weekly_study_time desc) as \'ranking\' " +
                        "from (member natural left outer join member_image) join central_library_record " +
                        "where member.central_library_record_id = central_library_record.central_library_record_id limit 3")
                .rowMapper(new CentralLibraryTop3RowMapper())
                .build();
    }

    @StepScope
    @Bean
    public RepositoryItemWriter<CentralLibraryTop3> centralLibraryTop3Writer() {

        return new RepositoryItemWriterBuilder<CentralLibraryTop3>()
                .repository(centralLibraryTop3Repository)
                .methodName("save")
                .build();
    }

    @StepScope
    @Bean
    public RepositoryItemReader<CentralLibraryRecord> centralLibraryRecordReader() {

        return new RepositoryItemReaderBuilder<CentralLibraryRecord>()
                .name("centralLibraryRecordReader")
                .repository(centralLibraryRecordRepository)
                .methodName("findAll")
                .pageSize(10)
                .arguments(Arrays.asList())
                .sorts(Collections.singletonMap("id", Sort.Direction.ASC))
                .build();
    }

    @StepScope
    @Bean
    public ItemProcessor<CentralLibraryRecord, CentralLibraryRecord> centralLibraryRecordUpdateProcessor() {
        return new ItemProcessor<CentralLibraryRecord, CentralLibraryRecord>() {
            @Override
            public CentralLibraryRecord process(CentralLibraryRecord item) throws Exception {
                item.resetWeeklyStudyTime();
                return item;
            }
        };
    }

    @StepScope
    @Bean
    public RepositoryItemWriter<CentralLibraryRecord> centralLibraryRecordWriter() {

        return new RepositoryItemWriterBuilder<CentralLibraryRecord>()
                .repository(centralLibraryRecordRepository)
                .methodName("save")
                .build();
    }

}
