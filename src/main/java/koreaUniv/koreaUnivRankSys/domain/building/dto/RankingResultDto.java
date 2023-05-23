package koreaUniv.koreaUnivRankSys.domain.building.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class RankingResultDto {

    private List<RankingDto> rankings;
    private Integer count;

    public static RankingResultDto of(List<RankingDto> rankings) {
        return RankingResultDto.builder()
                .rankings(rankings)
                .count(rankings.size())
                .build();
    }

}
