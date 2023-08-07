package koreaUniv.koreaUnivRankSys.batch.repository.member;

import koreaUniv.koreaUnivRankSys.batch.domain.member.MemberStudyTimeHistory;
import koreaUniv.koreaUnivRankSys.domain.member.domain.Member;
import koreaUniv.koreaUnivRankSys.domain.member.domain.MemberStudyTime;
import koreaUniv.koreaUnivRankSys.domain.member.repository.MemberRepository;
import koreaUniv.koreaUnivRankSys.domain.member.repository.MemberStudyTimeRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberStudyTimeHistoryRepositoryTest {

    @Autowired
    MemberStudyTimeHistoryRepository memberStudyTimeHistoryRepository;

    @Autowired
    MemberStudyTimeRepository memberStudyTimeRepository;

    @Autowired
    MemberRepository memberRepository;

    @Test
    void 엔터디_저장_성공() {

        Member member1 = Member.builder()
                .userId("testId1")
                .nickName("test1")
                .email("test@email.com")
                .build();

        memberRepository.save(member1);

        MemberStudyTime memberStudyTime = MemberStudyTime.createStudyTime();
        memberStudyTime.setMember(member1);

        memberStudyTimeRepository.save(memberStudyTime);

        MemberStudyTimeHistory record1 = MemberStudyTimeHistory.builder()
                .memberStudyTime(memberStudyTime)
                .build();

        MemberStudyTimeHistory record2 = MemberStudyTimeHistory.builder()
                .memberStudyTime(memberStudyTime)
                .build();

        MemberStudyTimeHistory record3 = MemberStudyTimeHistory.builder()
                .memberStudyTime(memberStudyTime)
                .build();

        memberStudyTimeHistoryRepository.save(record1);
        memberStudyTimeHistoryRepository.save(record2);
        memberStudyTimeHistoryRepository.save(record3);

        List<MemberStudyTimeHistory> histories = memberStudyTimeHistoryRepository.findAll();

        Assertions.assertThat(histories.size()).isEqualTo(3);
    }

    @Test
    void 엔터디_list_조회_성공() {

        Member member1 = Member.builder()
                .userId("testId1")
                .nickName("test1")
                .email("test@email.com")
                .build();

        memberRepository.save(member1);

        MemberStudyTime memberStudyTime = MemberStudyTime.createStudyTime();
        memberStudyTime.setMember(member1);

        memberStudyTimeRepository.save(memberStudyTime);

        MemberStudyTimeHistory record1 = MemberStudyTimeHistory.builder()
                .memberStudyTime(memberStudyTime)
                .build();

        MemberStudyTimeHistory record2 = MemberStudyTimeHistory.builder()
                .memberStudyTime(memberStudyTime)
                .build();

        MemberStudyTimeHistory record3 = MemberStudyTimeHistory.builder()
                .memberStudyTime(memberStudyTime)
                .build();

        memberStudyTimeHistoryRepository.save(record1);
        memberStudyTimeHistoryRepository.save(record2);
        memberStudyTimeHistoryRepository.save(record3);

        List<MemberStudyTimeHistory> histories = member1.getMemberStudyTimeHistory();
        System.out.println(histories);
    }

}