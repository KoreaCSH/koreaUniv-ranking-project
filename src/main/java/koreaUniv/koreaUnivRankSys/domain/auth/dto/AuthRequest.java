package koreaUniv.koreaUnivRankSys.domain.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AuthRequest {

    private String userId;
    private String password;

}
