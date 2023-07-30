package koreaUniv.koreaUnivRankSys.batch.repository.building;

import koreaUniv.koreaUnivRankSys.batch.domain.building.top3.CentralLibraryTop3;
import koreaUniv.koreaUnivRankSys.batch.domain.building.top3.Top3;
import koreaUniv.koreaUnivRankSys.batch.repository.building.top3.CentralLibraryTop3Repository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
@Transactional
class CentralLibraryTop3RepositoryTest {

    @Autowired
    CentralLibraryTop3Repository centralLibraryTop3Repository;

    @Test
    void top3_조회하기() {
        CentralLibraryTop3 record1 = new CentralLibraryTop3("test1", null, 1L, 100L);
        CentralLibraryTop3 record2 = new CentralLibraryTop3("test2", null, 2L, 50L);
        CentralLibraryTop3 record3 = new CentralLibraryTop3("test3", null, 3L, 30L);
        CentralLibraryTop3 record4 = new CentralLibraryTop3("test4", null, 4L, 20L);
        CentralLibraryTop3 record5 = new CentralLibraryTop3("test5", null, 5L, 10L);

        centralLibraryTop3Repository.save(record3);
        centralLibraryTop3Repository.save(record4);
        centralLibraryTop3Repository.save(record1);
        centralLibraryTop3Repository.save(record2);
        centralLibraryTop3Repository.save(record5);

        List<Top3> top3 = centralLibraryTop3Repository.findTop3();

        Assertions.assertThat(top3.size()).isEqualTo(3);
        Assertions.assertThat(top3.get(0).getNickName()).isEqualTo("test1");
        Assertions.assertThat(top3.get(0).getRanking()).isEqualTo(1L);
        Assertions.assertThat(top3.get(1).getNickName()).isEqualTo("test2");
        Assertions.assertThat(top3.get(1).getRanking()).isEqualTo(2L);
        Assertions.assertThat(top3.get(2).getNickName()).isEqualTo("test3");
        Assertions.assertThat(top3.get(2).getRanking()).isEqualTo(3L);
    }

}