package koreaUniv.koreaUnivRankSys.web.member.dto;

import koreaUniv.koreaUnivRankSys.domain.member.domain.Department;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class DepartmentDto {

    private String name;

    public static DepartmentDto of(Department department) {
        DepartmentDto departmentDto = new DepartmentDto();
        departmentDto.name = department.getName();
        return departmentDto;
    }

}
