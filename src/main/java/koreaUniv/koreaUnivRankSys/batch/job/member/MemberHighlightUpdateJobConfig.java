package koreaUniv.koreaUnivRankSys.batch.job.member;

import koreaUniv.koreaUnivRankSys.batch.common.JobLoggerListener;
import koreaUniv.koreaUnivRankSys.batch.domain.building.history.*;
import koreaUniv.koreaUnivRankSys.batch.repository.building.history.*;
import koreaUniv.koreaUnivRankSys.domain.member.domain.Member;
import koreaUniv.koreaUnivRankSys.domain.member.domain.MemberHighlight;
import koreaUniv.koreaUnivRankSys.domain.member.repository.MemberHighlightRepository;
import koreaUniv.koreaUnivRankSys.domain.member.repository.MemberRepository;
import koreaUniv.koreaUnivRankSys.global.exception.CustomException;
import koreaUniv.koreaUnivRankSys.global.exception.ErrorCode;
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

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.PriorityQueue;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class MemberHighlightUpdateJobConfig {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    private final MemberRepository memberRepository;
    private final MemberHighlightRepository memberHighlightRepository;

    private final CentralLibraryRecordHistoryRepository centralLibraryRecordHistoryRepository;
    private final CentralSquareRecordHistoryRepository centralSquareRecordHistoryRepository;
    private final EducationHallRecordHistoryRepository educationHallRecordHistoryRepository;
    private final HanaSquareRecordHistoryRepository hanaSquareRecordHistoryRepository;
    private final MemorialHallRecordHistoryRepository memorialHallRecordHistoryRepository;
    private final ScienceLibraryRecordHistoryRepository scienceLibraryRecordHistoryRepository;

    @Bean
    public Job memberHighlightUpdateJob(Step memberHighlightUpdateStep) {
        return jobBuilderFactory.get("memberHighlightUpdateJob")
                .incrementer(new RunIdIncrementer())
                .listener(new JobLoggerListener())
                .start(memberHighlightUpdateStep)
                .build();
    }

    @JobScope
    @Bean
    public Step memberHighlightUpdateStep(ItemReader memberReader,
                                          ItemProcessor memberHighlightProcessor,
                                          ItemWriter memberHighlightWriter) {
        return stepBuilderFactory.get("memberHighlightUpdateStep")
                .<Member, MemberHighlight>chunk(10)
                .reader(memberReader)
                .processor(memberHighlightProcessor)
                .writer(memberHighlightWriter)
                .build();
    }

    @StepScope
    @Bean
    public RepositoryItemReader<Member> memberReader() {
        return new RepositoryItemReaderBuilder<Member>()
                .name("memberReader")
                .repository(memberRepository)
                .methodName("findAll")
                .pageSize(10)
                .arguments(Arrays.asList())
                .sorts(Collections.singletonMap("id", Sort.Direction.ASC))
                .build();
    }

    @StepScope
    @Bean
    public ItemProcessor<Member, MemberHighlight> memberHighlightProcessor() {
        return new ItemProcessor<Member, MemberHighlight>() {
            @Override
            public MemberHighlight process(Member member) throws Exception {

                PriorityQueue<BuildingRecordHistory> pq = new PriorityQueue<>();

                Member findMember = memberRepository.findById(member.getId())
                        .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOTFOUND));

                MemberHighlight findMemberHighlight = memberHighlightRepository.findByMemberId(findMember.getId())
                        .orElseThrow(() -> new CustomException(ErrorCode.HIGHLIGHT_NOTFOUND));

                CentralLibraryRecordHistory centralLibraryTopRecord = centralLibraryRecordHistoryRepository.findTop(findMember.getId());
                CentralSquareRecordHistory centralSquareTopRecord = centralSquareRecordHistoryRepository.findTop(findMember.getId());
                EducationHallRecordHistory educationHallTopRecord = educationHallRecordHistoryRepository.findTop(member.getId());
                HanaSquareRecordHistory hanaSquareTopRecord = hanaSquareRecordHistoryRepository.findTop(member.getId());
                MemorialHallRecordHistory memorialHallTopRecord = memorialHallRecordHistoryRepository.findTop(member.getId());
                ScienceLibraryRecordHistory scienceLibraryTopRecord = scienceLibraryRecordHistoryRepository.findTop(member.getId());

                // studyTime == 0 이라면 highlight 로 추가하지 않는 로직 추가
                // 즉, 공부 기록이 있는 건물만 highlight 에 집계되도록 구현

                if(centralLibraryTopRecord.getStudyTime() != 0L) {
                    pq.add(centralLibraryTopRecord);
                }
                if(centralSquareTopRecord.getStudyTime() != 0L) {
                    pq.add(centralSquareTopRecord);
                }
                if(educationHallTopRecord.getStudyTime() != 0L) {
                    pq.add(educationHallTopRecord);
                }
                if(hanaSquareTopRecord.getStudyTime() != 0L) {
                    pq.add(hanaSquareTopRecord);
                }
                if(memorialHallTopRecord.getStudyTime() != 0L) {
                    pq.add(memorialHallTopRecord);
                }
                if(scienceLibraryTopRecord.getStudyTime() != 0L) {
                    pq.add(scienceLibraryTopRecord);
                }

                if(!pq.isEmpty()) {
                    BuildingRecordHistory topRecord = pq.poll();
                    String buildingName = topRecord.getBuildingName().getName();
                    Long topRanking = topRecord.getRanking();
                    LocalDate studyDate = topRecord.getStudyDate();

                    if(findMemberHighlight.getRanking() >= topRanking) {
                        findMemberHighlight.updateHighlight(buildingName, topRanking, studyDate);
                    }
                }

                return findMemberHighlight;
            }
        };
    }

    @StepScope
    @Bean
    public RepositoryItemWriter<MemberHighlight> memberHighlightWriter() {
        return new RepositoryItemWriterBuilder<MemberHighlight>()
                .repository(memberHighlightRepository)
                .methodName("save")
                .build();
    }

}
