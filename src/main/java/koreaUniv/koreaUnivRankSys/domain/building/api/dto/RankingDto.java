package koreaUniv.koreaUnivRankSys.domain.building.api.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class RankingDto {

    private Long ranking;
    private String nickName;
    private String path;
    private Long totalStudyingTime;

}
