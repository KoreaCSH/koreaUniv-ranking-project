package koreaUniv.koreaUnivRankSys.service;

import koreaUniv.koreaUnivRankSys.domain.Member;
import koreaUniv.koreaUnivRankSys.domain.MemorialHall;
import koreaUniv.koreaUnivRankSys.repository.H2MemorialHallRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemorialServiceTest {

    @Autowired
    MemorialService memorialService;
    @Autowired
    H2MemorialHallRepository memorialHallRepository;
    @Autowired
    EntityManager em;

    @Test
    void 공부_기록_등록() {

        // given
        Member member = new Member("test", "1", "hi");
        em.persist(member);

        // when
        MemorialHall memorialHall = MemorialHall.createMemorialHallStudyingRecord(member);
        memorialService.makeRecode(memorialHall);

        // then
        Optional<MemorialHall> findRecord = memorialHallRepository.findOne(memorialHall.getId());
        Assertions.assertThat(memorialHall.getId()).isEqualTo(findRecord.get().getId());

    }

    @Test
    void 공부_기록_측정() throws InterruptedException {
        // given
        Member member = new Member("test", "1", "hi");
        em.persist(member);

        MemorialHall memorialHall = MemorialHall.createMemorialHallStudyingRecord(member);
        memorialService.makeRecode(memorialHall);

        // when
        memorialService.updateStartTime(memorialHall.getId());
        Thread.sleep(5000);
        memorialService.updateFinishTime(memorialHall.getId());
        memorialService.updateStudyingTime(memorialHall.getId());

        // then
        System.out.println(memorialHall.getStudyingTime());
    }

}