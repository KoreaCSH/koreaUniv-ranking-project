//package koreaUniv.koreaUnivRankSys.domain.building.repository.ranking;
//
//import koreaUniv.koreaUnivRankSys.domain.building.dto.RankingDto;
//import koreaUniv.koreaUnivRankSys.domain.building.domain.MemorialHallRecord;
//import koreaUniv.koreaUnivRankSys.domain.building.repository.MemorialHallRecordRepository;
//import koreaUniv.koreaUnivRankSys.domain.building.service.MemorialHallRecordService;
//import koreaUniv.koreaUnivRankSys.domain.member.dto.MemberSignUpRequest;
//import koreaUniv.koreaUnivRankSys.domain.member.domain.Member;
//import koreaUniv.koreaUnivRankSys.domain.member.service.MemberService;
//import org.assertj.core.api.Assertions;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.annotation.Rollback;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.List;
//
//@SpringBootTest
//@Transactional
//class MemorialHallRankingQueryRepositoryTest {
//
//    @Autowired
//    MemberService memberService;
//
//    @Autowired
//    MemorialHallRecordService memorialHallRecordService;
//
//    @Autowired
//    MemorialHallRecordRepository memorialHallRecordRepository;
//
//    @Autowired
//    MemorialHallRankingQueryRepository memorialHallRankingQueryRepository;
//
//    @Test
//    @Rollback(value = false)
//    void 랭킹_조회() {
//        //given
//        MemberSignUpRequest request = new MemberSignUpRequest();
//        request.setString_id("test1");
//        request.setNickName("korea1");
//        Long memberId1 = memberService.join(request);
//        Member findMember1 = memberService.findOne(memberId1);
//        memorialHallRecordService.recordStudyingTime(findMember1.getString_id(), 10L);
//
//        MemberSignUpRequest request2 = new MemberSignUpRequest();
//        request2.setString_id("test2");
//        request2.setNickName("korea2");
//        Long memberId2 = memberService.join(request2);
//        Member findMember2 = memberService.findOne(memberId2);
//        memorialHallRecordService.recordStudyingTime(findMember2.getString_id(), 20L);
//
//        MemberSignUpRequest request3 = new MemberSignUpRequest();
//        request3.setString_id("test3");
//        request3.setNickName("korea3");
//        Long memberId3 = memberService.join(request3);
//        Member findMember3 = memberService.findOne(memberId3);
//        memorialHallRecordService.recordStudyingTime(findMember3.getString_id(), 30L);
//
//        MemberSignUpRequest request4 = new MemberSignUpRequest();
//        request4.setString_id("test4");
//        request4.setNickName("korea4");
//        Long memberId4 = memberService.join(request4);
//        Member findMember4 = memberService.findOne(memberId4);
//        Long recordId = memorialHallRecordService.recordStudyingTime(findMember4.getString_id(), 15L);
//        MemorialHallRecord findRecord = memorialHallRecordService.findOne(recordId);
//        Assertions.assertThat(findRecord.getTotalStudyingTime()).isEqualTo(15L);
//
//        memorialHallRecordService.recordStudyingTime(findMember4.getString_id(), 1L);
//        findRecord = memorialHallRecordService.findOne(recordId);
//        Assertions.assertThat(findRecord.getTotalStudyingTime()).isEqualTo(16L);
//
//        List<RankingDto> findRankings = memorialHallRecordService.findAllByRanking();
//        findRankings.stream().forEach(System.out::println);
//
//        Assertions.assertThat(findRecord.getTotalStudyingTime()).isEqualTo(16L);
//    }
//
//}