package koreaUniv.koreaUnivRankSys.domain.member.service;

import koreaUniv.koreaUnivRankSys.domain.member.domain.Department;
import koreaUniv.koreaUnivRankSys.global.exception.CustomException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
/*
    DepartmentServiceTest 시 sql.sql 의 insert 문 실행 및 ddl-auto: none 로 설정
 */
@SpringBootTest
@Transactional
class DepartmentServiceTest {

    @Autowired
    DepartmentService departmentService;

    @Test
    void 학과_조회_성공() {
        Department education_department = departmentService.findByName("교육학과");

        Assertions.assertThat(education_department.getName()).isEqualTo("교육학과");
    }

    @Test
    void 학과_조회_실패() {
        org.junit.jupiter.api.Assertions.assertThrows(CustomException.class, () -> {
            departmentService.findByName("???");
        });
    }

    @Test
    void 학부로_학과_조회() {
        List<Department> departments = departmentService.findByCollegeName("사범대학");

        for(Department department : departments) {
            System.out.println(department.getName());
        }

        Assertions.assertThat(departments.size()).isEqualTo(7);
    }

}