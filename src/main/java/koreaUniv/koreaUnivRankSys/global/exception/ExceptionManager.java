package koreaUniv.koreaUnivRankSys.global.exception;

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

        return ResponseEntity.badRequest().body(message);
    }

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<CustomResult> customExceptionHandler(CustomException e) {

        return ResponseEntity.status(e.getErrorCode().getHttpStatus())
                .body(new CustomResult(String.valueOf(e.getErrorCode().getHttpStatus().value()),
                        e.getErrorCode().getMessage()));
    }

}
