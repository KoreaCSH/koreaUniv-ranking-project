package koreaUniv.koreaUnivRankSys.domain.auth.api;

import koreaUniv.koreaUnivRankSys.domain.auth.dto.AccessTokenResponse;
import koreaUniv.koreaUnivRankSys.domain.auth.service.AuthMember;
import koreaUniv.koreaUnivRankSys.domain.auth.service.AuthService;
import koreaUniv.koreaUnivRankSys.domain.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;

@RestController
@RequestMapping("/api/token")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/refresh")
    public ResponseEntity<AccessTokenResponse> refresh(
            @CookieValue(value = "refreshToken", required = false) Cookie rtCookie) {

        String refreshToken = rtCookie.getValue();

        AccessTokenResponse response = authService.refresh(refreshToken);
        return ResponseEntity.ok().body(response);
    }

}
