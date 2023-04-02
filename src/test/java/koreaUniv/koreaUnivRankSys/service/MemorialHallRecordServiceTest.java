package koreaUniv.koreaUnivRankSys.service;

import koreaUniv.koreaUnivRankSys.domain.Member;
import koreaUniv.koreaUnivRankSys.domain.MemorialHallRecord;
import koreaUniv.koreaUnivRankSys.repository.JpaMemorialHallRecordRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.util.Optional;

@SpringBootTest
@Transactional
class MemorialHallRecordServiceTest {

    @Autowired
    MemorialHallRecordService memorialHallRecordService;
    @Autowired
    JpaMemorialHallRecordRepository memorialHallRepository;
    @Autowired
    EntityManager em;

    @Test
    void 공부_기록_등록() {

        // given
        Member member = new Member("test", "1", "hi", 3);
        em.persist(member);

        // when
        MemorialHallRecord memorialHallRecord = MemorialHallRecord.createMemorialHallRecord(member);
        memorialHallRecordService.makeRecode(memorialHallRecord);

        // then
        Optional<MemorialHallRecord> findRecord = memorialHallRepository.findOne(memorialHallRecord.getId());
        Assertions.assertThat(memorialHallRecord.getId()).isEqualTo(findRecord.get().getId());

    }

    @Test
    void 공부_기록_조회() {
        // given
        Member member = new Member("test", "1", "aaa", 3);
        em.persist(member);

        MemorialHallRecord memorialHallRecord = MemorialHallRecord.createMemorialHallRecord(member);
        memorialHallRecordService.makeRecode(memorialHallRecord);

        // when
        Optional<MemorialHallRecord> findRecord = memorialHallRepository.findByStringId(member.getString_id());

        // then
        Assertions.assertThat(memorialHallRecord.getId()).isEqualTo(findRecord.get().getId());
    }

    @Test
    void 공부_기록_측정() throws InterruptedException {
        // given
        Member member = new Member("test", "1", "hi", 3);
        em.persist(member);

        MemorialHallRecord memorialHallRecord = MemorialHallRecord.createMemorialHallRecord(member);
        memorialHallRecordService.makeRecode(memorialHallRecord);

        // when
        memorialHallRecordService.updateStartTime(memorialHallRecord.getId());
        Thread.sleep(5000);
        memorialHallRecordService.updateFinishTime(memorialHallRecord.getId());
        memorialHallRecordService.updateStudyingTime(memorialHallRecord.getId());

        memorialHallRecordService.updateStartTime(memorialHallRecord.getId());
        Thread.sleep(5000);
        memorialHallRecordService.updateFinishTime(memorialHallRecord.getId());
        memorialHallRecordService.updateStudyingTime(memorialHallRecord.getId());

        // then
        System.out.println(memorialHallRecord.getStudyingTime());
    }

}