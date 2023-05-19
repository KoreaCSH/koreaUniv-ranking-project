package koreaUniv.koreaUnivRankSys.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ErrorCode {

    MEMBER_ID_DUPLICATED(HttpStatus.CONFLICT, "사용할 수 없는 아이디입니다.");

    private HttpStatus httpStatus;
    private String message;

}
