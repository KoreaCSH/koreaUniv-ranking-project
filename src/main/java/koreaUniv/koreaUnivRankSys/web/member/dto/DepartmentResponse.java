package koreaUniv.koreaUnivRankSys.web.member.dto;

import lombok.*;

import java.util.List;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DepartmentResponse {

    private List<DepartmentDto> departmentList;

    public static DepartmentResponse of(List<DepartmentDto> departmentList) {
        return DepartmentResponse.builder()
                .departmentList(departmentList)
                .build();
    }

}
