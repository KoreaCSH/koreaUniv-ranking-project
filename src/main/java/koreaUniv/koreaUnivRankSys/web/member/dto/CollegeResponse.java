package koreaUniv.koreaUnivRankSys.web.member.dto;

import lombok.*;

import java.util.List;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CollegeResponse {

    private List<CollegeDto> collegeList;

    public static CollegeResponse of(List<CollegeDto> collegeList) {
        return CollegeResponse.builder()
                .collegeList(collegeList)
                .build();
    }

}
