package koreaUniv.koreaUnivRankSys.global.exception;

import koreaUniv.koreaUnivRankSys.global.common.CommonResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionManager {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
        String message = e.getBindingResult()
                .getAllErrors()
                .get(0)
                .getDefaultMessage();

        return ResponseEntity.badRequest()
                             .body(message);
    }

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<CommonResponse> customExceptionHandler(CustomException e) {

        HttpStatus status = e.getHttpStatus();
        String message = e.getMessage();

        return ResponseEntity.status(status)
                             .body(CommonResponse.of(status, message));
    }

}
