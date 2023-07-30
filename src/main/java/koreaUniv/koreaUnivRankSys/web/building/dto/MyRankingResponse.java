package koreaUniv.koreaUnivRankSys.web.building.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MyRankingResponse {

    @Schema(description = "닉네임", example = "나의 닉네임")
    private String nickName;

    @Schema(description = "나의 랭킹", type = "long")
    private Long ranking;

    @Schema(description = "나의 공부시간", type = "나의 공부시간")
    private Long studyTime;

    @Schema(description = "앞 순위 회원의 공부시간")
    private Long prevRanking;

    @Schema(description = "뒷 순위 회원의 공부시간")
    private Long nextRanking;

}
