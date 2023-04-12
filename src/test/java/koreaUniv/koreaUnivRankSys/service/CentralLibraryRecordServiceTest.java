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

    }

}