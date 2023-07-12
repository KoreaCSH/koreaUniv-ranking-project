package koreaUniv.koreaUnivRankSys.domain.auth.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AccessTokenDto {

    @NotBlank(message = "Access Token 이 입력되지 않았습니다.")
    private String accessToken;

}
