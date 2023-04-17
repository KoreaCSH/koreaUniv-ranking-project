package koreaUniv.koreaUnivRankSys.domain.building.service;

import koreaUniv.koreaUnivRankSys.domain.building.repository.JpaCentralLibraryRecordRepository;
import koreaUniv.koreaUnivRankSys.domain.building.service.CentralLibraryRecordService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
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