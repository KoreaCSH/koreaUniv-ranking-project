package koreaUniv.koreaUnivRankSys.web.auth;

import koreaUniv.koreaUnivRankSys.web.auth.dto.AccessTokenDto;
import koreaUniv.koreaUnivRankSys.domain.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;

@RestController
@RequestMapping("/api/token")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/refresh")
    public ResponseEntity<AccessTokenDto> refresh(
            @CookieValue(value = "refreshToken", required = false) Cookie rtCookie) {

        String refreshToken = rtCookie.getValue();

        AccessTokenDto dto = authService.refresh(refreshToken);
        return ResponseEntity.ok().body(dto);
    }

    @PostMapping("/logout")
    public ResponseEntity<AccessTokenDto> logout(@RequestBody AccessTokenDto accessTokenDto) {
        AccessTokenDto dto = authService.logout(accessTokenDto);
        ResponseCookie responseCookie = authService.removeRefreshTokenCookie();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, responseCookie.toString())
                .body(dto);
    }

}
