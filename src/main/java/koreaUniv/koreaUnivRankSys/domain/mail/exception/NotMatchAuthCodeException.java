package koreaUniv.koreaUnivRankSys.domain.mail.exception;

public class NotMatchAuthCodeException extends RuntimeException {

    public NotMatchAuthCodeException(String message) {
        super(message);
    }
}
