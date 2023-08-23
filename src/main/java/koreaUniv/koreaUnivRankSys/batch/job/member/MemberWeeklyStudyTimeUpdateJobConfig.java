package koreaUniv.koreaUnivRankSys.batch.job.member;

import koreaUniv.koreaUnivRankSys.batch.common.JobLoggerListener;
import koreaUniv.koreaUnivRankSys.domain.member.domain.MemberStudyTime;
import koreaUniv.koreaUnivRankSys.domain.member.repository.MemberStudyTimeRepository;
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

// --spring.batch.job.names=memberWeeklyStudyTimeUpdateJob

@Slf4j
@Configuration
@RequiredArgsConstructor
public class MemberWeeklyStudyTimeUpdateJobConfig {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    private final MemberStudyTimeRepository memberStudyTimeRepository;

    @Bean
    public Job memberWeeklyStudyTimeUpdateJob(Step memberWeeklyStudyTimeUpdateStep) {

        return jobBuilderFactory.get("memberWeeklyStudyTimeUpdateJob")
                .incrementer(new RunIdIncrementer()) // JobInstance 의 식별자 자동으로 생성
                .listener(new JobLoggerListener())
                .start(memberWeeklyStudyTimeUpdateStep)
                .build();
    }

    @JobScope
    @Bean
    public Step memberWeeklyStudyTimeUpdateStep(
            ItemReader memberWeeklyStudyTimeReader,
            ItemProcessor memberWeeklyStudyTimeUpdateProcessor,
            ItemWriter memberWeeklyStudyTimeWriter) {

        return stepBuilderFactory.get("memberWeeklyStudyTimeUpdateStep")
                .<MemberStudyTime, MemberStudyTime>chunk(10)
                .reader(memberWeeklyStudyTimeReader)
                .processor(memberWeeklyStudyTimeUpdateProcessor)
                .writer(memberWeeklyStudyTimeWriter)
                .build();
    }

    @StepScope
    @Bean
    public RepositoryItemReader<MemberStudyTime> memberWeeklyStudyTimeReader() {
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
    public ItemProcessor<MemberStudyTime, MemberStudyTime> memberWeeklyStudyTimeUpdateProcessor() {
        return new ItemProcessor<MemberStudyTime, MemberStudyTime>() {
            @Override
            public MemberStudyTime process(MemberStudyTime item) throws Exception {
                item.resetMemberWeeklyStudyTime();
                return item;
            }
        };
    }

    @StepScope
    @Bean
    public RepositoryItemWriter<MemberStudyTime> memberWeeklyStudyTimeWriter() {
        return new RepositoryItemWriterBuilder<MemberStudyTime>()
                .repository(memberStudyTimeRepository)
                .methodName("save")
                .build();
    }

}
