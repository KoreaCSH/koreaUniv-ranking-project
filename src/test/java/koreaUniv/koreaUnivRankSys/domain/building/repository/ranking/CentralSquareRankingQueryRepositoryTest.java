package koreaUniv.koreaUnivRankSys.domain.building.repository.ranking;

import koreaUniv.koreaUnivRankSys.web.building.dto.MyRankingResponse;
import koreaUniv.koreaUnivRankSys.web.building.dto.RankingDto;
import koreaUniv.koreaUnivRankSys.domain.building.service.CentralSquareRecordService;
import koreaUniv.koreaUnivRankSys.domain.member.domain.Member;
import koreaUniv.koreaUnivRankSys.domain.member.repository.MemberRepository;
import koreaUniv.koreaUnivRankSys.global.exception.CustomException;
import koreaUniv.koreaUnivRankSys.global.exception.ErrorCode;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
@Transactional
class CentralSquareRankingQueryRepositoryTest {

    @Autowired
    CentralSquareRankingQueryRepository centralSquareRankingQueryRepository;

    @Autowired
    CentralSquareRecordService centralSquareRecordService;

    @Autowired
    MemberRepository memberRepository;

    @BeforeEach
    void member_추가() {

        Member member1 = Member.builder()
                .userId("testId1")
                .nickName("test1")
                .email("test@email.com")
                .build();

        Member member2 = Member.builder()
                .userId("testId2")
                .nickName("test2")
                .email("test2@email.com")
                .build();

        Member member3 = Member.builder()
                .userId("testId3")
                .nickName("test3")
                .email("test3@email.com")
                .build();

        memberRepository.save(member1);
        memberRepository.save(member2);
        memberRepository.save(member3);

        centralSquareRecordService.trackStudyTime(member1, 10L);
        centralSquareRecordService.trackStudyTime(member2, 20L);
        centralSquareRecordService.trackStudyTime(member3, 15L);
        // dirty checking 이 바로 반영되지 않는 문제
        centralSquareRecordService.trackStudyTime(member3, 1L);
    }

    @Test
    void totalStudyTime_조회() {
        List<RankingDto> records = centralSquareRankingQueryRepository.findRankingsByTotalStudyTime();

        Assertions.assertThat(records.size()).isEqualTo(3);
        Assertions.assertThat(records.get(0).getNickName()).isEqualTo("test2");
        Assertions.assertThat(records.get(1).getNickName()).isEqualTo("test3");
        Assertions.assertThat(records.get(2).getNickName()).isEqualTo("test1");
    }

    @Test
    void dailyStudyTime_조회() {
        List<RankingDto> records = centralSquareRankingQueryRepository.findRankingsByDailyStudyTime();

        Assertions.assertThat(records.size()).isEqualTo(3);
        Assertions.assertThat(records.get(0).getNickName()).isEqualTo("test2");
        Assertions.assertThat(records.get(1).getNickName()).isEqualTo("test3");
        Assertions.assertThat(records.get(2).getNickName()).isEqualTo("test1");
    }

    @Test
    void weeklyStudyTime_조회() {
        List<RankingDto> records = centralSquareRankingQueryRepository.findRankingsByWeeklyStudyTime();

        Assertions.assertThat(records.size()).isEqualTo(3);
        Assertions.assertThat(records.get(0).getNickName()).isEqualTo("test2");
        Assertions.assertThat(records.get(1).getNickName()).isEqualTo("test3");
        Assertions.assertThat(records.get(2).getNickName()).isEqualTo("test1");
    }

    @Test
    void monthlyStudyTime_조회() {
        List<RankingDto> records = centralSquareRankingQueryRepository.findRankingsByMonthlyStudyTime();

        Assertions.assertThat(records.size()).isEqualTo(3);
        Assertions.assertThat(records.get(0).getNickName()).isEqualTo("test2");
        Assertions.assertThat(records.get(1).getNickName()).isEqualTo("test3");
        Assertions.assertThat(records.get(2).getNickName()).isEqualTo("test1");
    }

    @Test
    void myRanking_조회() {
        MyRankingResponse myRanking = centralSquareRankingQueryRepository.findMyRankingByTotalStudyTime("test3")
                .orElseThrow(() -> new CustomException(ErrorCode.RECORD_NOTFOUND));

        Assertions.assertThat(myRanking.getNickName()).isEqualTo("test3");
        Assertions.assertThat(myRanking.getPrevRanking()).isEqualTo(20L);
        Assertions.assertThat(myRanking.getNextRanking()).isEqualTo(10L);
    }

}