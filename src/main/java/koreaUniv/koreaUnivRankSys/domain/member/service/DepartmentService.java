package koreaUniv.koreaUnivRankSys.domain.member.service;

<<<<<<< HEAD
import koreaUniv.koreaUnivRankSys.domain.member.domain.College;
import koreaUniv.koreaUnivRankSys.domain.member.domain.Department;
import koreaUniv.koreaUnivRankSys.domain.member.repository.DepartmentRepository;
import koreaUniv.koreaUnivRankSys.global.exception.CustomException;
import koreaUniv.koreaUnivRankSys.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
=======
import java.util.List;
>>>>>>> 55f884dfcc6ff5ef9cd31a1f5f101dcb68e7aa87

import javax.transaction.Transactional;
import java.util.List;

import org.springframework.stereotype.Service;

import koreaUniv.koreaUnivRankSys.domain.member.domain.College;
import koreaUniv.koreaUnivRankSys.domain.member.domain.Department;
import koreaUniv.koreaUnivRankSys.domain.member.repository.DepartmentRepository;

@Service
@Transactional
@RequiredArgsConstructor
public class DepartmentService {
    private final DepartmentRepository departmentRepository;

    public Department findByName(String name) {
        return departmentRepository.findByName(name)
                .orElseThrow(() -> new CustomException(ErrorCode.DEPARTMENT_NOTFOUND));
    }

    public List<Department> findByCollegeName(String collegeName) {
        return departmentRepository.findByCollegeName(collegeName);
    }

    public Department findByName(String name) {
        return departmentRepository.findByName(name).get();
        // 예외 처리 필요
    }

    public List<Department> findByCollege(College college) {
        return departmentRepository.findByCollege(college);
    }
}
