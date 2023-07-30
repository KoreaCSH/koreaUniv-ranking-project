package koreaUniv.koreaUnivRankSys.web.building.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class StudyTimeRequest {

    @Min(value = 1, message = "기록이 저장되지 않았습니다.")
    Long studyTime;

}
