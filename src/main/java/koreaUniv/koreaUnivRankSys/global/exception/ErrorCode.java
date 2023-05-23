package koreaUniv.koreaUnivRankSys.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ErrorCode {

    MEMBER_USERID_DUPLICATED(HttpStatus.CONFLICT, "사용할 수 없는 아이디입니다."),
    MEMBER_NICKNAME_DUPLICATED(HttpStatus.CONFLICT, "사용할 수 없는 닉네임입니다."),
    MEMBER_NOTFOUND(HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다."),
    INVALID_PASSWORD(HttpStatus.BAD_REQUEST, "비밀번호가 일치하지 않습니다."),

    EMAIL_DUPLICATED(HttpStatus.CONFLICT, "이미 인증이 완료된 이메일입니다."),
    EMAIL_NOTFOUND(HttpStatus.NOT_FOUND, "이메일을 찾을 수 없습니다."),
    NOT_MATCH_AUTHCODE(HttpStatus.BAD_REQUEST, "인증번호가 일치하지 않습니다."),
    NOT_AUTH_MAIL(HttpStatus.BAD_REQUEST, "메일 인증을 해 주세요."),

    RECORD_NOTFOUND(HttpStatus.NOT_FOUND, "공부 기록이 없습니다.");

    private HttpStatus httpStatus;
    private String message;

}
