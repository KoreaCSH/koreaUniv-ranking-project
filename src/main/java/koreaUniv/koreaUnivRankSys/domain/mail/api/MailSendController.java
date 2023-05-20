package koreaUniv.koreaUnivRankSys.domain.mail.api;

import koreaUniv.koreaUnivRankSys.domain.mail.domain.MailAuthInfo;
import koreaUniv.koreaUnivRankSys.domain.mail.domain.MailAuthStatus;
import koreaUniv.koreaUnivRankSys.domain.mail.dto.AuthCodeRequestDto;
import koreaUniv.koreaUnivRankSys.domain.mail.service.MailAuthInfoService;
import koreaUniv.koreaUnivRankSys.domain.mail.service.MailSendService;
import koreaUniv.koreaUnivRankSys.global.exception.CustomException;
import koreaUniv.koreaUnivRankSys.global.exception.CustomResult;
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
    public ResponseEntity<CustomResult> sendAuthCode(@PathVariable String email)
            throws MessagingException {

        email += "@korea.ac.kr";
        String authCode = mailSendService.createAuthCode();
        mailAuthInfoService.create(email, authCode);
        mailSendService.sendEmail(email, authCode);

        return ResponseEntity.ok().body(
                new CustomResult(String.valueOf(HttpStatus.CREATED), "인증번호를 전송했습니다.")
        );
    }

    @PostMapping("{email}/check")
    public ResponseEntity<CustomResult> checkAuthCode(@PathVariable String email, @RequestBody AuthCodeRequestDto request) {

        email += "@korea.ac.kr";

        MailAuthInfo findInfo = mailAuthInfoService.findByEmail(email);

        if(!findInfo.getAuthCode().equals(request.getAuthCode())) {
            throw new CustomException(ErrorCode.NOT_MATCH_AUTHCODE);
        }

        mailAuthInfoService.changeStatus(findInfo, MailAuthStatus.Y);

        return ResponseEntity.ok().body(
                new CustomResult(String.valueOf(HttpStatus.OK), "인증이 완료되었습니다.")
        );
    }

}
