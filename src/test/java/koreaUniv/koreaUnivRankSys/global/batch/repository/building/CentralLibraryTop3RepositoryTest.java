package koreaUniv.koreaUnivRankSys.global.batch.repository.building;

import koreaUniv.koreaUnivRankSys.global.batch.domain.building.CentralLibraryTop3;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class CentralLibraryTop3RepositoryTest {

    @Autowired
    CentralLibraryTop3Repository centralLibraryTop3Repository;

    @Test
    void top3_조회하기() {
        CentralLibraryTop3 record1 = CentralLibraryTop3.builder()
                .nickName("test1")
                .ranking(1L)
                .studyTime(100L)
                .imageUrl(null)
                .build();

        CentralLibraryTop3 record2 = CentralLibraryTop3.builder()
                .nickName("test2")
                .ranking(2L)
                .studyTime(50L)
                .imageUrl(null)
                .build();

        CentralLibraryTop3 record3 = CentralLibraryTop3.builder()
                .nickName("test3")
                .ranking(3L)
                .studyTime(30L)
                .imageUrl(null)
                .build();

        CentralLibraryTop3 record4 = CentralLibraryTop3.builder()
                .nickName("test4")
                .ranking(4L)
                .studyTime(20L)
                .imageUrl(null)
                .build();

        CentralLibraryTop3 record5 = CentralLibraryTop3.builder()
                .nickName("test5")
                .ranking(5L)
                .studyTime(10L)
                .imageUrl(null)
                .build();

        centralLibraryTop3Repository.save(record3);
        centralLibraryTop3Repository.save(record4);
        centralLibraryTop3Repository.save(record1);
        centralLibraryTop3Repository.save(record2);
        centralLibraryTop3Repository.save(record5);

        List<CentralLibraryTop3> top3 = centralLibraryTop3Repository.findTop3();

        Assertions.assertThat(top3.size()).isEqualTo(3);
        Assertions.assertThat(top3.get(0).getNickName()).isEqualTo("test1");
        Assertions.assertThat(top3.get(1).getNickName()).isEqualTo("test2");
        Assertions.assertThat(top3.get(2).getNickName()).isEqualTo("test3");
    }

}