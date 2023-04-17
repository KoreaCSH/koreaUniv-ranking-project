package koreaUniv.koreaUnivRankSys.domain.building.service;

import koreaUniv.koreaUnivRankSys.domain.member.domain.Member;
import koreaUniv.koreaUnivRankSys.domain.building.domain.CentralLibraryRecord;
import koreaUniv.koreaUnivRankSys.domain.building.domain.MemorialHallRecord;
import koreaUniv.koreaUnivRankSys.domain.building.repository.interfaces.MemorialHallRecordRepository;
import koreaUniv.koreaUnivRankSys.domain.building.service.MemorialHallRecordService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@SpringBootTest
@Transactional
class MemorialHallRecordServiceTest {

    @Autowired
    MemorialHallRecordService memorialHallRecordService;
    @Autowired
    MemorialHallRecordRepository memorialHallRepository;
    @Autowired
    EntityManager em;

//    @Test
//    void 공부_기록_등록() {
//
//        // given
//        Member member = Member.builder()
//                .string_id("test1")
//                .email("test1@gmail.com")
//                .password("1234")
//                .nickName("korea")
//                .memorialHallRecord(MemorialHallRecord.createMemorialHallRecord())
//                .centralLibraryRecord(CentralLibraryRecord.createCentralLibraryRecord())
//                .build();
//
//        em.persist(member);
//
//        MemorialHallRecord findRecord = memorialHallRepository.findByStringId(member.getString_id()).get();
//
//        // when
//        findRecord.updateStudyingTime(5);
//
//        // then
//        Assertions.assertThat(findRecord.getDailyStudyingTime()).isEqualTo(5);
//        Assertions.assertThat(findRecord.getWeeklyStudyingTime()).isEqualTo(5);
//        Assertions.assertThat(findRecord.getMonthlyStudyingTime()).isEqualTo(5);
//        Assertions.assertThat(findRecord.getTotalStudyingTime()).isEqualTo(5);
//        Assertions.assertThat(member.getMemberTotalStudyingTime()).isEqualTo(5);
//
//    }
//
//    @Test
//    void 공부_기록_조회() {
//        // given
//        MemorialHallRecord memorialHallRecord = MemorialHallRecord.createMemorialHallRecord();
//
//        Member member = Member.builder()
//                .string_id("test1")
//                .email("test1@gmail.com")
//                .password("1234")
//                .nickName("korea")
//                .memorialHallRecord(memorialHallRecord)
//                .centralLibraryRecord(CentralLibraryRecord.createCentralLibraryRecord())
//                .build();
//
//        em.persist(member);
//
//        // when
//        MemorialHallRecord findRecord = memorialHallRepository.findByStringId(member.getString_id()).get();
//
//        // then
//        Assertions.assertThat(memorialHallRecord.getId()).isEqualTo(findRecord.getId());
//        Assertions.assertThat(memorialHallRecord).isSameAs(findRecord);
//    }


}