package koreaUniv.koreaUnivRankSys.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CustomException extends  RuntimeException {

    private ErrorCode errorCode;

}
