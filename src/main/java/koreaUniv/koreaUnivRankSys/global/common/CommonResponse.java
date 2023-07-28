package koreaUniv.koreaUnivRankSys.global.common;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class CommonResponse {

    private String status;
    private String message;

    public static CommonResponse of(HttpStatus status, String message) {
        CommonResponse commonResponse = new CommonResponse();
        commonResponse.status = String.valueOf(status.value());
        commonResponse.message = message;
        return commonResponse;
    }

}
