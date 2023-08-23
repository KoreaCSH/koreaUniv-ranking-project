package koreaUniv.koreaUnivRankSys.global.exception;

import koreaUniv.koreaUnivRankSys.global.common.CommonResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;

@RestControllerAdvice
public class ExceptionManager {

    // request, Dto 관련 Exception
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
        String message = e.getBindingResult()
                .getAllErrors()
                .get(0)
                .getDefaultMessage();

        return ResponseEntity.badRequest()
                             .body(message);
    }

    // RequestParam, PathVariable 관련 Exception
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<?> constraintViolationException(ConstraintViolationException e) {
        String message = e.getMessage();

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
