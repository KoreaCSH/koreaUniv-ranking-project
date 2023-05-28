package koreaUniv.koreaUnivRankSys.domain.member.repository;

import koreaUniv.koreaUnivRankSys.domain.member.domain.College;
import koreaUniv.koreaUnivRankSys.domain.member.domain.Department;

import java.util.List;
import java.util.Optional;

public interface DepartmentRepository {
    Optional<Department> findByName(String name);

    List<Department> findByCollege(College college);
}
