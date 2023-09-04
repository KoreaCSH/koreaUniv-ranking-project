package koreaUniv.koreaUnivRankSys.batch.job.building.history;

import koreaUniv.koreaUnivRankSys.batch.common.JobLoggerListener;
import koreaUniv.koreaUnivRankSys.batch.domain.building.history.HanaSquareRecordHistory;
import koreaUniv.koreaUnivRankSys.batch.repository.building.history.HanaSquareRecordHistoryRepository;
import koreaUniv.koreaUnivRankSys.domain.building.domain.HanaSquareRecord;
import koreaUniv.koreaUnivRankSys.domain.building.repository.HanaSquareRecordRepository;
import koreaUniv.koreaUnivRankSys.domain.member.domain.Member;
import koreaUniv.koreaUnivRankSys.domain.member.service.MemberService;
import koreaUniv.koreaUnivRankSys.web.building.dto.RankingDto;
import koreaUniv.koreaUnivRankSys.web.building.dto.mapper.DailyRankingDtoRowMapper;
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
public class HanaSquareRecordMigrationJobConfig {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    private final HanaSquareRecordHistoryRepository hanaSquareRecordHistoryRepository;
    private final HanaSquareRecordRepository hanaSquareRecordRepository;

    private final MemberService memberService;
    private final DataSource dataSource;

    @Bean
    public Job hanaSquareRecordMigrationJob(Step hanaSquareRecordMigrationStep,
                                               Step hanaSquareDailyStudyTimeUpdateStep) {
        return jobBuilderFactory.get("hanaSquareRecordMigrationJob")
                .incrementer(new RunIdIncrementer())
                .listener(new JobLoggerListener())
                .start(hanaSquareRecordMigrationStep)
                .on("COMPLETED").to(hanaSquareDailyStudyTimeUpdateStep)
                .from(hanaSquareRecordMigrationStep)
                .end()
                .build();
    }

    @JobScope
    @Bean
    public Step hanaSquareRecordMigrationStep(ItemReader hanaSquareRecordRankingReader,
                                                 ItemProcessor hanaSquareRecordProcessor,
                                                 ItemWriter hanaSquareRecordHistoryWriter) {

        return stepBuilderFactory.get("hanaSquareRecordMigrationStep")
                .<RankingDto, HanaSquareRecordHistory>chunk(10)
                .reader(hanaSquareRecordRankingReader)
                .processor(hanaSquareRecordProcessor)
                .writer(hanaSquareRecordHistoryWriter)
                .build();
    }

    @StepScope
    @Bean
    public JdbcCursorItemReader<RankingDto> hanaSquareRecordRankingReader() {

        return new JdbcCursorItemReaderBuilder<RankingDto>()
                .dataSource(dataSource)
                .name("hanaSquareRecordReader")
                .sql("select member_id, path, nick_name, daily_study_time, " +
                        "row_number() over (order by daily_study_time desc) as \'ranking\' " +
                        "from (member natural left outer join member_image) join hana_square_record " +
                        "where member.hana_square_record_id = hana_square_record.hana_square_record_id")
                .rowMapper(new DailyRankingDtoRowMapper())
                .build();
    }

    @StepScope
    @Bean
    public ItemProcessor<RankingDto, HanaSquareRecordHistory> hanaSquareRecordProcessor() {
        return new ItemProcessor<RankingDto, HanaSquareRecordHistory>() {
            @Override
            public HanaSquareRecordHistory process(RankingDto item) throws Exception {

                Member findMember = memberService.findById(item.getMemberId());
                return new HanaSquareRecordHistory(findMember, item.getRanking(), item.getStudyTime());
            }
        };
    }

    @StepScope
    @Bean
    public RepositoryItemWriter<HanaSquareRecordHistory> hanaSquareRecordHistoryWriter() {

        return new RepositoryItemWriterBuilder<HanaSquareRecordHistory>()
                .repository(hanaSquareRecordHistoryRepository)
                .methodName("save")
                .build();
    }

    @JobScope
    @Bean
    public Step hanaSquareDailyStudyTimeUpdateStep(ItemReader hanaSquareRecordDailyStudyTimeReader,
                                                      ItemProcessor hanaSquareRecordDailyStudyTimeUpdateProcessor,
                                                      ItemWriter hanaSquareRecordDailyStudyTimeWriter) {

        return stepBuilderFactory.get("hanaSquareDailyStudyTimeUpdateStep")
                .<HanaSquareRecord, HanaSquareRecord>chunk(10)
                .reader(hanaSquareRecordDailyStudyTimeReader)
                .processor(hanaSquareRecordDailyStudyTimeUpdateProcessor)
                .writer(hanaSquareRecordDailyStudyTimeWriter)
                .build();
    }

    @StepScope
    @Bean
    public RepositoryItemReader<HanaSquareRecord> hanaSquareRecordDailyStudyTimeReader() {

        return new RepositoryItemReaderBuilder<HanaSquareRecord>()
                .name("hanaSquareRecordDailyStudyTimeReader")
                .repository(hanaSquareRecordRepository)
                .methodName("findAll")
                .pageSize(10)
                .arguments(Arrays.asList())
                .sorts(Collections.singletonMap("id", Sort.Direction.ASC))
                .build();
    }

    @StepScope
    @Bean
    public ItemProcessor<HanaSquareRecord, HanaSquareRecord> hanaSquareRecordDailyStudyTimeUpdateProcessor() {

        return new ItemProcessor<HanaSquareRecord, HanaSquareRecord>() {
            @Override
            public HanaSquareRecord process(HanaSquareRecord item) throws Exception {
                item.resetDailyStudyTime();
                return item;
            }
        };
    }

    @StepScope
    @Bean
    public RepositoryItemWriter<HanaSquareRecord> hanaSquareRecordDailyStudyTimeWriter() {

        return new RepositoryItemWriterBuilder<HanaSquareRecord>()
                .repository(hanaSquareRecordRepository)
                .methodName("save")
                .build();
    }

}
