package koreaUniv.koreaUnivRankSys.domain.building.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class RankingsResponse {

    private List<RankingDto> rankings;
    private Integer count;

    public static RankingsResponse of(List<RankingDto> rankings) {
        return RankingsResponse.builder()
                .rankings(rankings)
                .count(rankings.size())
                .build();
    }

}
