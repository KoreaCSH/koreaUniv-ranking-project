package koreaUniv.koreaUnivRankSys.domain.member.repository;

import koreaUniv.koreaUnivRankSys.domain.member.domain.College;
import koreaUniv.koreaUnivRankSys.domain.member.domain.Department;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository // department의 경우에만 오류가 발생해서 annotation 사용해 둠
public interface DepartmentRepository {
    Optional<Department> findByName(String name);

    List<Department> findByCollege(College college);
}
