package koreaUniv.koreaUnivRankSys.web.member.dto;

import koreaUniv.koreaUnivRankSys.domain.member.domain.College;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CollegeDto {

    private String collegeName;

    public static CollegeDto of(College college) {
        CollegeDto collegeDto = new CollegeDto();
        collegeDto.collegeName = college.getName();
        return collegeDto;
    }

}
