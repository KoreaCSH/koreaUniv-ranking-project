package koreaUniv.koreaUnivRankSys.batch.repository.building;

import koreaUniv.koreaUnivRankSys.batch.repository.building.history.CentralSquareRecordHistoryRepository;
import koreaUniv.koreaUnivRankSys.domain.member.domain.Member;
import koreaUniv.koreaUnivRankSys.domain.member.repository.MemberRepository;
import koreaUniv.koreaUnivRankSys.batch.domain.building.history.CentralSquareRecordHistory;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class CentralSquareRecordHistoryRepositoryTest {

    @Autowired
    CentralSquareRecordHistoryRepository centralSquareRecordHistoryRepository;

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

        CentralSquareRecordHistory centralSquareRecordHistory = new CentralSquareRecordHistory(member1, 1L, 10L);

        centralSquareRecordHistoryRepository.save(centralSquareRecordHistory);

        Assertions.assertThat(centralSquareRecordHistory.getRanking()).isEqualTo(1L);
        Assertions.assertThat(centralSquareRecordHistory.getMember().getNickName()).isEqualTo("test1");
        Assertions.assertThat(centralSquareRecordHistory.getStudyTime()).isEqualTo(10L);
        Assertions.assertThat(centralSquareRecordHistory.getMember()).isEqualTo(member1);
    }

    @Test
    void History_데이터_list_조회() {

        Member member1 = Member.builder()
                .userId("testId1")
                .nickName("test1")
                .email("test@email.com")
                .build();

        memberRepository.save(member1);

        CentralSquareRecordHistory centralSquareRecordHistory1 = new CentralSquareRecordHistory(member1, 1L, 10L);
        CentralSquareRecordHistory centralSquareRecordHistory2 = new CentralSquareRecordHistory(member1, 2L, 20L);

        centralSquareRecordHistoryRepository.save(centralSquareRecordHistory1);
        centralSquareRecordHistoryRepository.save(centralSquareRecordHistory2);

        for(CentralSquareRecordHistory centralSquareRecordHistory : member1.getCentralSquareRecordHistory()) {
            System.out.println(centralSquareRecordHistory + " " + "date: " + centralSquareRecordHistory.getStudyDate());
        }

    }

}