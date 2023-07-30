package koreaUniv.koreaUnivRankSys.batch.job.member;

import koreaUniv.koreaUnivRankSys.batch.common.JobLoggerListener;
import koreaUniv.koreaUnivRankSys.batch.repository.member.MemberStudyTimeHistoryRepository;
import koreaUniv.koreaUnivRankSys.domain.member.domain.MemberStudyTime;
import koreaUniv.koreaUnivRankSys.domain.member.repository.MemberStudyTimeRepository;
import koreaUniv.koreaUnivRankSys.batch.domain.member.MemberStudyTimeHistory;
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
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;

import java.util.*;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class MemberStudyTimeMigrationJobConfig {

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
                .listener(new JobLoggerListener())
                .start(memberStudyTimeMigrationStep)
                .on("COMPLETED").to(memberStudyTimeUpdateStep)
                .from(memberStudyTimeMigrationStep)
                .end()
                .build();
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
                .faultTolerant()
                // 추후 retry, skip 할 Exception 구체화하기
                .retry(Exception.class)
                .retryLimit(3)
                .skip(Exception.class)
                .skipLimit(3)
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

    // 추후 RetryTemplate 활용해서 custom retry, skip policy 구현하기
    @Bean
    public RetryTemplate retryTemplate() {

        Map<Class<? extends Throwable>, Boolean> exceptionClass = new HashMap<>();
        exceptionClass.put(Exception.class, true);

        SimpleRetryPolicy policy = new SimpleRetryPolicy(3, exceptionClass);

        RetryTemplate retryTemplate = new RetryTemplate();
        retryTemplate.setRetryPolicy(policy);

        return retryTemplate;
    }

}
