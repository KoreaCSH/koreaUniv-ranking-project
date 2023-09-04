package koreaUniv.koreaUnivRankSys.batch.job.building.history;

import koreaUniv.koreaUnivRankSys.batch.common.JobLoggerListener;
import koreaUniv.koreaUnivRankSys.batch.domain.building.history.MemorialHallRecordHistory;
import koreaUniv.koreaUnivRankSys.batch.repository.building.history.MemorialHallRecordHistoryRepository;
import koreaUniv.koreaUnivRankSys.domain.building.domain.MemorialHallRecord;
import koreaUniv.koreaUnivRankSys.domain.building.repository.MemorialHallRecordRepository;
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
public class MemorialHallRecordMigrationJobConfig {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    private final MemorialHallRecordHistoryRepository memorialHallRecordHistoryRepository;
    private final MemorialHallRecordRepository memorialHallRecordRepository;

    private final MemberService memberService;
    private final DataSource dataSource;

    @Bean
    public Job memorialHallRecordMigrationJob(Step memorialHallRecordMigrationStep,
                                               Step memorialHallDailyStudyTimeUpdateStep) {
        return jobBuilderFactory.get("memorialHallRecordMigrationJob")
                .incrementer(new RunIdIncrementer())
                .listener(new JobLoggerListener())
                .start(memorialHallRecordMigrationStep)
                .on("COMPLETED").to(memorialHallDailyStudyTimeUpdateStep)
                .from(memorialHallRecordMigrationStep)
                .end()
                .build();
    }

    @JobScope
    @Bean
    public Step memorialHallRecordMigrationStep(ItemReader memorialHallRecordRankingReader,
                                                 ItemProcessor memorialHallRecordProcessor,
                                                 ItemWriter memorialHallRecordHistoryWriter) {

        return stepBuilderFactory.get("memorialHallRecordMigrationStep")
                .<RankingDto, MemorialHallRecordHistory>chunk(10)
                .reader(memorialHallRecordRankingReader)
                .processor(memorialHallRecordProcessor)
                .writer(memorialHallRecordHistoryWriter)
                .build();
    }

    @StepScope
    @Bean
    public JdbcCursorItemReader<RankingDto> memorialHallRecordRankingReader() {

        return new JdbcCursorItemReaderBuilder<RankingDto>()
                .dataSource(dataSource)
                .name("memorialHallRecordReader")
                .sql("select member_id, path, nick_name, daily_study_time, " +
                        "row_number() over (order by daily_study_time desc) as \'ranking\' " +
                        "from (member natural left outer join member_image) join memorial_hall_record " +
                        "where member.memorial_hall_record_id = memorial_hall_record.memorial_hall_record_id")
                .rowMapper(new DailyRankingDtoRowMapper())
                .build();
    }

    @StepScope
    @Bean
    public ItemProcessor<RankingDto, MemorialHallRecordHistory> memorialHallRecordProcessor() {
        return new ItemProcessor<RankingDto, MemorialHallRecordHistory>() {
            @Override
            public MemorialHallRecordHistory process(RankingDto item) throws Exception {

                Member findMember = memberService.findById(item.getMemberId());
                return new MemorialHallRecordHistory(findMember, item.getRanking(), item.getStudyTime());
            }
        };
    }

    @StepScope
    @Bean
    public RepositoryItemWriter<MemorialHallRecordHistory> memorialHallRecordHistoryWriter() {

        return new RepositoryItemWriterBuilder<MemorialHallRecordHistory>()
                .repository(memorialHallRecordHistoryRepository)
                .methodName("save")
                .build();
    }

    @JobScope
    @Bean
    public Step memorialHallDailyStudyTimeUpdateStep(ItemReader memorialHallRecordDailyStudyTimeReader,
                                                      ItemProcessor memorialHallRecordDailyStudyTimeUpdateProcessor,
                                                      ItemWriter memorialHallRecordDailyStudyTimeWriter) {

        return stepBuilderFactory.get("memorialHallDailyStudyTimeUpdateStep")
                .<MemorialHallRecord, MemorialHallRecord>chunk(10)
                .reader(memorialHallRecordDailyStudyTimeReader)
                .processor(memorialHallRecordDailyStudyTimeUpdateProcessor)
                .writer(memorialHallRecordDailyStudyTimeWriter)
                .build();
    }

    @StepScope
    @Bean
    public RepositoryItemReader<MemorialHallRecord> memorialHallRecordDailyStudyTimeReader() {

        return new RepositoryItemReaderBuilder<MemorialHallRecord>()
                .name("memorialHallRecordDailyStudyTimeReader")
                .repository(memorialHallRecordRepository)
                .methodName("findAll")
                .pageSize(10)
                .arguments(Arrays.asList())
                .sorts(Collections.singletonMap("id", Sort.Direction.ASC))
                .build();
    }

    @StepScope
    @Bean
    public ItemProcessor<MemorialHallRecord, MemorialHallRecord> memorialHallRecordDailyStudyTimeUpdateProcessor() {

        return new ItemProcessor<MemorialHallRecord, MemorialHallRecord>() {
            @Override
            public MemorialHallRecord process(MemorialHallRecord item) throws Exception {
                item.resetDailyStudyTime();
                return item;
            }
        };
    }

    @StepScope
    @Bean
    public RepositoryItemWriter<MemorialHallRecord> memorialHallRecordDailyStudyTimeWriter() {

        return new RepositoryItemWriterBuilder<MemorialHallRecord>()
                .repository(memorialHallRecordRepository)
                .methodName("save")
                .build();
    }

}
