package koreaUniv.koreaUnivRankSys.domain.building.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MyRankingResponse {

    private String nickName;
    private Long ranking;
    private long studyTime;
    private Long prevRanking;
    private Long nextRanking;

}
