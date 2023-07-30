package koreaUniv.koreaUnivRankSys.global.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import koreaUniv.koreaUnivRankSys.web.auth.dto.AuthRequest;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private ObjectMapper objectMapper = new ObjectMapper();

    public JwtAuthenticationFilter() {
        super(new AntPathRequestMatcher("/api/members/login", "POST"));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException, IOException, ServletException {

        if (!HttpMethod.POST.name().equals(request.getMethod())) {
            throw new IllegalArgumentException("Authentication method not supported");
        }

        AuthRequest authRequest = objectMapper.readValue(request.getReader(), AuthRequest.class);
        if (StringUtils.isEmpty(authRequest.getUserId()) || StringUtils.isEmpty(authRequest.getPassword())) {
            throw new AuthenticationServiceException("Username or Password not provided");
        }

        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(authRequest.getUserId(), authRequest.getPassword());

        return this.getAuthenticationManager().authenticate(token);
    }
}
