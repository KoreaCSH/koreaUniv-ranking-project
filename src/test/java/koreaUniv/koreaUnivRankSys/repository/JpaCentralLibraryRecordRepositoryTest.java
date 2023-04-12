package koreaUniv.koreaUnivRankSys.repository;


import koreaUniv.koreaUnivRankSys.domain.building.CentralLibraryRecord;
import koreaUniv.koreaUnivRankSys.domain.Member;
import koreaUniv.koreaUnivRankSys.repository.building.JpaCentralLibraryRecordRepository;
import org.assertj.core.api.Assertions;
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