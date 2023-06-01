package koreaUniv.koreaUnivRankSys.domain.member.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import koreaUniv.koreaUnivRankSys.domain.member.domain.College;
import koreaUniv.koreaUnivRankSys.domain.member.domain.Department;
import koreaUniv.koreaUnivRankSys.domain.member.repository.DepartmentRepository;

@Service
@Transactional
public class DepartmentService {
    private final DepartmentRepository departmentRepository;

    public DepartmentService(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    public Department findByName(String name) {
        return departmentRepository.findByName(name).get();
        // 예외 처리 필요
    }

    public List<Department> findByCollege(College college) {
        return departmentRepository.findByCollege(college);
    }
}
