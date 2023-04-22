package koreaUniv.koreaUnivRankSys.domain.building.api.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemorialHallRankingDto {

    private Long ranking;
    private String nickName;
    private String path;
    private long totalStudyingTime;

}
