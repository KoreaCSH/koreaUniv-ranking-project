package koreaUniv.koreaUnivRankSys.global.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import koreaUniv.koreaUnivRankSys.global.common.CommonResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtAccessDeniedHandler implements AccessDeniedHandler {

    // 인증은 완료했으나 자격 권한이 없을 때 AccessDeniedHandler

    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException)
            throws IOException, ServletException {

        CommonResponse commonResponse = new CommonResponse(String.valueOf(HttpServletResponse.SC_FORBIDDEN), accessDeniedException.getMessage());

        String json = objectMapper.writeValueAsString(commonResponse);

        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.getWriter().write(json);

    }
}
