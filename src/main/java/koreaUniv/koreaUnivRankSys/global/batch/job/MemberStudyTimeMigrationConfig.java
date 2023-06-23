package koreaUniv.koreaUnivRankSys.global.batch.job;

import koreaUniv.koreaUnivRankSys.domain.member.domain.MemberStudyTime;
import koreaUniv.koreaUnivRankSys.domain.member.repository.MemberStudyTimeRepository;
import koreaUniv.koreaUnivRankSys.global.batch.domain.MemberStudyTimeHistory;
import koreaUniv.koreaUnivRankSys.global.batch.domain.MemberStudyTimeHistoryRepository;
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
import java.util.List;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class MemberStudyTimeMigrationConfig {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    private final MemberStudyTimeRepository memberStudyTimeRepository;
    private final MemberStudyTimeHistoryRepository memberStudyTimeHistoryRepository;

    @Bean
    public Job memberStudyTimeMigrationJob(
                        Step memberStudyTimeMigrationStep,
                        Step memberStudyTimeUpdateStep) {
        return jobBuilderFactory.get("memberStudyTimeMigrationJob")
                .incrementer(new RunIdIncrementer()) // JobInstance 의 식별자 자동으로 생성
                .start(memberStudyTimeMigrationStep)
                .on("COMPLETED").to(memberStudyTimeUpdateStep)
                .from(memberStudyTimeMigrationStep)
                .end()
                .build();

        // history table 로 이전 -> MemberStudyTime 의 DailyStudyTime 을 0 으로 Update 도 해야 할 것.
        // 그렇다면 두 개의 Step 을 사용하면 되는가?

    }

    @JobScope
    @Bean
    public Step memberStudyTimeMigrationStep(
                        ItemReader memberStudyTimeReader,
                        ItemProcessor memberStudyTimeProcessor,
                        ItemWriter memberStudyTimeHistoryWriter) {
        return stepBuilderFactory.get("memberStudyTimeMigrationStep")
                .<MemberStudyTime, MemberStudyTimeHistory>chunk(10)
                .reader(memberStudyTimeReader)
                .processor(memberStudyTimeProcessor)
                .writer(memberStudyTimeHistoryWriter)
                .build();
    }

    @JobScope
    @Bean
    public Step memberStudyTimeUpdateStep(
                        ItemReader memberStudyTimeReader,
                        ItemProcessor memberStudyTimeUpdateProcessor,
                        ItemWriter memberStudyTimeUpdateWriter) {
        return stepBuilderFactory.get("memberStudyTimeUpdateStep")
                .<MemberStudyTime, MemberStudyTime>chunk(10)
                .reader(memberStudyTimeReader)
                .processor(memberStudyTimeUpdateProcessor)
                .writer(memberStudyTimeUpdateWriter)
                .build();
    }

    @StepScope
    @Bean
    public RepositoryItemReader<MemberStudyTime> memberStudyTimeReader() {
        return new RepositoryItemReaderBuilder<MemberStudyTime>()
                .name("memberStudyTimeReader")
                .repository(memberStudyTimeRepository)
                .methodName("findAll")
                .pageSize(10)
                .arguments(Arrays.asList())
                .sorts(Collections.singletonMap("id", Sort.Direction.ASC))
                .build();
    }

    @StepScope
    @Bean
    public ItemProcessor<MemberStudyTime, MemberStudyTimeHistory> memberStudyTimeProcessor() {
        return new ItemProcessor<MemberStudyTime, MemberStudyTimeHistory>() {
            @Override
            public MemberStudyTimeHistory process(MemberStudyTime item) throws Exception {
                return MemberStudyTimeHistory.builder()
                        .memberStudyTime(item)
                        .build();
            }
        };
    }

    @StepScope
    @Bean
    public RepositoryItemWriter<MemberStudyTimeHistory> memberStudyTimeHistoryWriter() {
        return new RepositoryItemWriterBuilder<MemberStudyTimeHistory>()
                .repository(memberStudyTimeHistoryRepository)
                .methodName("save")
                .build();
    }

    @StepScope
    @Bean
    public ItemProcessor<MemberStudyTime, MemberStudyTime> memberStudyTimeUpdateProcessor() {
        return new ItemProcessor<MemberStudyTime, MemberStudyTime>() {
            @Override
            public MemberStudyTime process(MemberStudyTime item) throws Exception {
                item.resetMemberDailyStudyTime();
                return item;
            }
        };
    }

    @StepScope
    @Bean
    public RepositoryItemWriter<MemberStudyTime> memberStudyTimeUpdateWriter() {
        return new RepositoryItemWriterBuilder<MemberStudyTime>()
                .repository(memberStudyTimeRepository)
                .methodName("save")
                .build();
    }

}
