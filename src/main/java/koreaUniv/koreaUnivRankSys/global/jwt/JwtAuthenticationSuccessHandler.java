package koreaUniv.koreaUnivRankSys.global.jwt;

import koreaUniv.koreaUnivRankSys.domain.member.service.MemberAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
public class JwtAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final JwtProvider jwtProvider;

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
        final String token = jwtProvider.createToken(memberAdapter.getMember().getUserId());
        response.addHeader("Authorization", "Bearer " + token);
    }
}
