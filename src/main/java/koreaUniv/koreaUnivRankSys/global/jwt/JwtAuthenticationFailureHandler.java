package koreaUniv.koreaUnivRankSys.global.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import koreaUniv.koreaUnivRankSys.global.common.CommonResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthenticationFailureHandler implements AuthenticationFailureHandler {

    private ObjectMapper objectMapper = new ObjectMapper();


    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {

        String errorMessage;

        if(exception instanceof BadCredentialsException) {
            errorMessage = "아이디 또는 비밀번호를 잘못 입력했습니다.";
        } else {
            errorMessage = "Exception";
        }

        CommonResponse commonResponse = new CommonResponse(String.valueOf(HttpServletResponse.SC_UNAUTHORIZED), errorMessage);

        String json = objectMapper.writeValueAsString(commonResponse);

        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getWriter().write(json);

    }
}
