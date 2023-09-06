package koreaUniv.koreaUnivRankSys.web.member.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class PlannerRequest {

    // @RequestBody 일 경우 @Setter 가 필요없지만, Params 일 경우 @Setter 가 필요하다.

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDate studyDate;

}
