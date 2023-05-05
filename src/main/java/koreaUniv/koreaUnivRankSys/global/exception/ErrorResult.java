package koreaUniv.koreaUnivRankSys.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ErrorResult {

    private String status;
    private String message;

}
