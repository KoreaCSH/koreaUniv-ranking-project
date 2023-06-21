package koreaUniv.koreaUnivRankSys.global.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import koreaUniv.koreaUnivRankSys.global.common.CommonResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtEntryPoint implements AuthenticationEntryPoint {

    // 인증을 받지 않은 사용자가 자원에 접근할 때에는 EntryPoint

    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {

        CommonResponse commonResponse = new CommonResponse(String.valueOf(HttpServletResponse.SC_UNAUTHORIZED), authException.getMessage());

        String json = objectMapper.writeValueAsString(commonResponse);

        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().write(json);
    }
}
