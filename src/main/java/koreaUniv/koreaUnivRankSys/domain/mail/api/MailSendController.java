package koreaUniv.koreaUnivRankSys.domain.mail.api;

import koreaUniv.koreaUnivRankSys.domain.mail.domain.MailAuthInfo;
import koreaUniv.koreaUnivRankSys.domain.mail.domain.MailAuthStatus;
import koreaUniv.koreaUnivRankSys.domain.mail.dto.AuthCodeRequest;
import koreaUniv.koreaUnivRankSys.domain.mail.service.MailAuthInfoService;
import koreaUniv.koreaUnivRankSys.domain.mail.service.MailSendService;
import koreaUniv.koreaUnivRankSys.global.common.CommonResponse;
import koreaUniv.koreaUnivRankSys.global.exception.CustomException;
import koreaUniv.koreaUnivRankSys.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;

@RestController
@RequestMapping("/api/mails")
@RequiredArgsConstructor
public class MailSendController {

    private final MailSendService mailSendService;
    private final MailAuthInfoService mailAuthInfoService;

    @PostMapping("{email}")
    public ResponseEntity<CommonResponse> sendAuthCode(@PathVariable String email)
            throws MessagingException {

        // create 함수 내부에서 authCode 를 생성 및 반환하고 mailSendService 를 한 번만 호출하면 되지 않을까?

        email += "@korea.ac.kr";
        String authCode = mailSendService.createAuthCode();
        mailAuthInfoService.create(email, authCode);
        mailSendService.sendEmail(email, authCode);

        return ResponseEntity.ok().body(
                new CommonResponse(String.valueOf(HttpStatus.CREATED), "인증번호를 전송했습니다.")
        );
    }

    @PostMapping("{email}/check")
    public ResponseEntity<CommonResponse> checkAuthCode(@PathVariable String email, @RequestBody AuthCodeRequest request) {

        email += "@korea.ac.kr";
        mailAuthInfoService.validateAuthCode(email, request);

        return ResponseEntity.ok().body(
                new CommonResponse(String.valueOf(HttpStatus.OK), "인증이 완료되었습니다.")
        );
    }

}
