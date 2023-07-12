package koreaUniv.koreaUnivRankSys.domain.member.service;

import koreaUniv.koreaUnivRankSys.domain.member.domain.College;
import koreaUniv.koreaUnivRankSys.global.exception.CustomException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class CollegeServiceTest {

    @Autowired
    CollegeService collegeService;

    @Test
    void 학과_조회_성공() {
        College college = collegeService.findByName("사범대학");

        Assertions.assertThat(college.getName()).isEqualTo("사범대학");
    }

    @Test
    void 학과_조회_실패() {
        org.junit.jupiter.api.Assertions.assertThrows(CustomException.class, () -> {
            collegeService.findByName("???");
        });
    }

}