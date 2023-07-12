package koreaUniv.koreaUnivRankSys.global.jwt;

import koreaUniv.koreaUnivRankSys.domain.auth.service.MemberAdapter;
import koreaUniv.koreaUnivRankSys.global.redis.RedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final JwtProvider jwtProvider;
    private final RedisService redisService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, ServletException {
        AuthenticationSuccessHandler.super.onAuthenticationSuccess(request, response, chain, authentication);
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        // 전달받은 인증 정보 SecurityContextHolder 에 저장
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // JWT 발급
        MemberAdapter memberAdapter = (MemberAdapter) authentication.getPrincipal();
        final String accessToken = jwtProvider.createAccessToken(memberAdapter.getMember().getUserId());
        ResponseCookie responseCookie = createRefreshTokenCookie(memberAdapter.getMember().getUserId());
        response.setHeader("Authorization", "Bearer " + accessToken);
        response.setHeader("Set-Cookie", responseCookie.toString());
    }

    public ResponseCookie createRefreshTokenCookie(String userId) {

        String refreshToken = jwtProvider.createRefreshToken(userId);
        Long refreshTokenValidationMs = jwtProvider.getRefreshTokenExpireTimeNs();

        redisService.setData("RefreshToken:" + userId, refreshToken, refreshTokenValidationMs);

        return ResponseCookie.from("refreshToken", refreshToken)
                // .domain()
                // .secure(true) HTTPS 로 통신할 때만 쿠키 전송
                .path("/")
                .maxAge(TimeUnit.MILLISECONDS.toSeconds(refreshTokenValidationMs))
                // CSRF 공격으로부터 웹 사이트 보호
                .sameSite("none")
                .httpOnly(true)
                .build();
    }

}
