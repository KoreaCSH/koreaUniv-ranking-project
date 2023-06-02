package koreaUniv.koreaUnivRankSys.domain.member.service;

import koreaUniv.koreaUnivRankSys.domain.member.domain.College;
import koreaUniv.koreaUnivRankSys.domain.member.domain.Department;
import koreaUniv.koreaUnivRankSys.domain.member.repository.DepartmentRepository;
import koreaUniv.koreaUnivRankSys.global.exception.CustomException;
import koreaUniv.koreaUnivRankSys.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

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
}
