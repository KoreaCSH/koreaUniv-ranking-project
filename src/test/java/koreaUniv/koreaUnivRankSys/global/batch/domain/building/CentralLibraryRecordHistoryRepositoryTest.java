package koreaUniv.koreaUnivRankSys.global.batch.domain.building;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class CentralLibraryRecordHistoryRepositoryTest {

    @Autowired
    CentralLibraryRecordHistoryRepository centralLibraryRecordHistoryRepository;

    @Test
    void 엔터티_저장_성공() {

        CentralLibraryRecordHistory centralLibraryRecordHistory = CentralLibraryRecordHistory.builder()
                                                                    .nickName("test")
                                                                    .ranking(1L)
                                                                    .studyTime(10L)
                                                                    .build();

        centralLibraryRecordHistoryRepository.save(centralLibraryRecordHistory);

        Assertions.assertThat(centralLibraryRecordHistory.getRanking()).isEqualTo(1L);
        Assertions.assertThat(centralLibraryRecordHistory.getNickName()).isEqualTo("test");
        Assertions.assertThat(centralLibraryRecordHistory.getStudyTime()).isEqualTo(10L);
    }

}