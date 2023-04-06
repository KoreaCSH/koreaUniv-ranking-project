package koreaUniv.koreaUnivRankSys.service;

import koreaUniv.koreaUnivRankSys.domain.Member;
import koreaUniv.koreaUnivRankSys.domain.building.CentralLibraryRecord;
import koreaUniv.koreaUnivRankSys.repository.building.JpaCentralLibraryRecordRepository;
import koreaUniv.koreaUnivRankSys.service.building.CentralLibraryRecordService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@SpringBootTest
@Transactional
class CentralLibraryRecordServiceTest {

    @Autowired
    EntityManager em;

    @Autowired
    JpaCentralLibraryRecordRepository centralLibraryRecordRepository;

    @Autowired
    CentralLibraryRecordService centralLibraryRecordService;

    @Test
    //@Rollback(value = false)
    void 공부_기록_측정() throws InterruptedException {
        // given
        CentralLibraryRecord centralLibraryRecord1 = CentralLibraryRecord.createCentralLibraryRecord();
        Member member1 = Member.createMember("test1", "1", "Korea", 4, centralLibraryRecord1);
        em.persist(member1);

        CentralLibraryRecord centralLibraryRecord2 = CentralLibraryRecord.createCentralLibraryRecord();
        Member member2 = Member.createMember("test2", "2", "Korea1", 4, centralLibraryRecord2);
        em.persist(member2);

        CentralLibraryRecord member1Record = centralLibraryRecordService.findByStringId(member1.getString_id()).get();
        CentralLibraryRecord member2Record = centralLibraryRecordService.findByStringId(member2.getString_id()).get();

        // when
        centralLibraryRecordService.updateStartTime(member1.getString_id());
        Thread.sleep(5000);
        centralLibraryRecordService.updateFinishTime(member1.getString_id());
        centralLibraryRecordService.updateStudyingTime(member1.getString_id());

        centralLibraryRecordService.updateStartTime(member2.getString_id());
        Thread.sleep(5000);
        centralLibraryRecordService.updateFinishTime(member2.getString_id());
        centralLibraryRecordService.updateStudyingTime(member2.getString_id());
        // then
    }

}