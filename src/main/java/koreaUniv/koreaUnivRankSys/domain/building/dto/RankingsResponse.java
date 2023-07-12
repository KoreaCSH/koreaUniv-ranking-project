package koreaUniv.koreaUnivRankSys.domain.building.dto;

import io.swagger.v3.oas.annotations.media.Schema;
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

    @Schema(description = "데이터 수", example = "30명 조회 했다면 30 return")
    private Integer count;

    public static RankingsResponse of(List<RankingDto> rankings) {
        return RankingsResponse.builder()
                .rankings(rankings)
                .count(rankings.size())
                .build();
    }

}
