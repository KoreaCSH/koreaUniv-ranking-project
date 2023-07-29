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
class EducationHallRecordServiceTest {

    @Autowired
    EducationHallRecordService educationHallRecordService;

    @Test
    void 랭킹_조회_실패() {
        Assertions.assertThrows(CustomException.class, () -> {
            educationHallRecordService.findDailyRankings();
        });

        Assertions.assertThrows(CustomException.class, () -> {
            educationHallRecordService.findWeeklyRankings();
        });

        Assertions.assertThrows(CustomException.class, () -> {
            educationHallRecordService.findMonthlyRankings();
        });

        Assertions.assertThrows(CustomException.class, () -> {
            educationHallRecordService.findTotalRankings();
        });
    }

}