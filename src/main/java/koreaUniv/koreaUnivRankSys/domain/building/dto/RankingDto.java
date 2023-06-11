package koreaUniv.koreaUnivRankSys.domain.building.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class RankingDto {

    @Schema(description = "랭킹", type = "long")
    private Long ranking;

    @Schema(description = "닉네임", example = "닉네임")
    private String nickName;

    @Schema(description = "프로필 사진 url", example = "프로필 사진 url")
    private String imageUrl;

    @Schema(description = "공부 시간", example = "공부 시간")
    private long studyTime;

}
