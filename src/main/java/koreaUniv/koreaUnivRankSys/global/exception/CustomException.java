package koreaUniv.koreaUnivRankSys.global.exception;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CustomException extends RuntimeException {

    private ErrorCode errorCode;

    public HttpStatus getHttpStatus() {
        return this.errorCode.getHttpStatus();
    }

    public String getMessage() {
        return this.errorCode.getMessage();
    }

}
