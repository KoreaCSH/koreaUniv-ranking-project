package koreaUniv.koreaUnivRankSys.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ErrorCode {

    // Member 관련 예외
    MEMBER_USERID_DUPLICATED(HttpStatus.CONFLICT, "사용할 수 없는 아이디입니다."),
    MEMBER_NICKNAME_DUPLICATED(HttpStatus.CONFLICT, "사용할 수 없는 닉네임입니다."),
    MEMBER_NOTFOUND(HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다."),
    INVALID_PASSWORD(HttpStatus.BAD_REQUEST, "비밀번호가 일치하지 않습니다."),
    NOT_MATCH_PASSWORD(HttpStatus.BAD_REQUEST, "새 비밀번호와 비밀번호 확인이 일치하지 않습니다."),
    COLLEGE_NOTFOUND(HttpStatus.NOT_FOUND, "단과대학을 찾을 수 없습니다."),
    DEPARTMENT_NOTFOUND(HttpStatus.NOT_FOUND, "학과를 찾을 수 없습니다."),
    HIGHLIGHT_NOTFOUND(HttpStatus.NOT_FOUND, "하이라이트를 조회할 수 없습니다."),

    // 메일 인증 관련 예외
    EMAIL_DUPLICATED(HttpStatus.CONFLICT, "이미 인증이 완료된 이메일입니다."),
    EMAIL_NOTFOUND(HttpStatus.NOT_FOUND, "이메일을 찾을 수 없습니다."),
    NOT_MATCH_AUTHCODE(HttpStatus.BAD_REQUEST, "인증번호가 일치하지 않습니다."),
    NOT_AUTH_MAIL(HttpStatus.BAD_REQUEST, "메일 인증을 해 주세요."),

    // 공부 기록 관련 예외
    RECORD_NOTFOUND(HttpStatus.NOT_FOUND, "공부 기록이 없습니다."),
    MEMBER_RECORD_NOTFOUND(HttpStatus.NOT_FOUND, "회원의 공부 기록이 없습니다."),
    RANKING_NOTFOUND(HttpStatus.NOT_FOUND, "랭킹을 조회할 수 없습니다."),

    // JWT 관련 예외
    REFRESH_TOKEN_NOTFOUND(HttpStatus.NOT_FOUND, "Refresh Token 을 찾을 수 없습니다."),
    REFRESH_TOKEN_NOT_MATCH(HttpStatus.BAD_REQUEST, "Refresh Token 이 일치하지 않습니다."),
    TOKEN_EXPIRED(HttpStatus.BAD_REQUEST, "이미 로그아웃된 토큰입니다."),

    // 날짜 관련 예외
    INVALID_DATE(HttpStatus.BAD_REQUEST, "유효하지 않은 날짜입니다.");

    private HttpStatus httpStatus;
    private String message;

}
