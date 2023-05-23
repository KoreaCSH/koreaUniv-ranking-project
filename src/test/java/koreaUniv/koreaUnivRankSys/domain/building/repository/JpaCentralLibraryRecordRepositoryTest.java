package koreaUniv.koreaUnivRankSys.domain.building.repository;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@SpringBootTest
@Transactional
class JpaCentralLibraryRecordRepositoryTest {

    @Autowired
    EntityManager em;

    @Autowired
    JpaCentralLibraryRecordRepository centralLibraryRecordRepository;

    @Test
    void 공부_기록_생성() {

    }

}