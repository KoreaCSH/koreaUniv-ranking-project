package koreaUniv.koreaUnivRankSys.global.batch.domain.building;

import koreaUniv.koreaUnivRankSys.domain.member.domain.Member;
import koreaUniv.koreaUnivRankSys.domain.member.repository.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class CentralLibraryRecordHistoryRepositoryTest {

    @Autowired
    CentralLibraryRecordHistoryRepository centralLibraryRecordHistoryRepository;

    @Autowired
    MemberRepository memberRepository;

    @Test
    void 엔터티_저장_성공() {

        Member member1 = Member.builder()
                .userId("testId1")
                .nickName("test1")
                .email("test@email.com")
                .build();

        memberRepository.save(member1);

        CentralLibraryRecordHistory centralLibraryRecordHistory = new CentralLibraryRecordHistory(member1, 1L, 10L);

        centralLibraryRecordHistoryRepository.save(centralLibraryRecordHistory);

        Assertions.assertThat(centralLibraryRecordHistory.getRanking()).isEqualTo(1L);
        Assertions.assertThat(centralLibraryRecordHistory.getNickName()).isEqualTo("test1");
        Assertions.assertThat(centralLibraryRecordHistory.getStudyTime()).isEqualTo(10L);
        Assertions.assertThat(centralLibraryRecordHistory.getMember()).isEqualTo(member1);
    }

    @Test
    void History_데이터_list_조회() {

        Member member1 = Member.builder()
                .userId("testId1")
                .nickName("test1")
                .email("test@email.com")
                .build();

        memberRepository.save(member1);

        CentralLibraryRecordHistory centralLibraryRecordHistory1 = new CentralLibraryRecordHistory(member1, 1L, 10L);
        CentralLibraryRecordHistory centralLibraryRecordHistory2 = new CentralLibraryRecordHistory(member1, 2L, 20L);

        centralLibraryRecordHistoryRepository.save(centralLibraryRecordHistory1);
        centralLibraryRecordHistoryRepository.save(centralLibraryRecordHistory2);

        for(CentralLibraryRecordHistory centralLibraryRecordHistory : member1.getCentralLibraryRecordHistory()) {
            System.out.println(centralLibraryRecordHistory + " " + "date: " + centralLibraryRecordHistory.getStudyDate());
        }

    }

}