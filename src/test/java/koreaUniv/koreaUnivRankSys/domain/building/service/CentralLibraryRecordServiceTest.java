package koreaUniv.koreaUnivRankSys.domain.building.service;

import koreaUniv.koreaUnivRankSys.global.exception.CustomException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class CentralLibraryRecordServiceTest {

    @Autowired
    CentralLibraryRecordService centralLibraryRecordService;

    @Test
    void 랭킹_조회_실패() {
        Assertions.assertThrows(CustomException.class, () -> {
            centralLibraryRecordService.findDailyRankings();
        });

        Assertions.assertThrows(CustomException.class, () -> {
            centralLibraryRecordService.findWeeklyRankings();
        });

        Assertions.assertThrows(CustomException.class, () -> {
            centralLibraryRecordService.findMonthlyRankings();
        });

        Assertions.assertThrows(CustomException.class, () -> {
            centralLibraryRecordService.findTotalRankings();
        });
    }

}