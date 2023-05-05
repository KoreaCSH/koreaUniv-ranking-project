package koreaUniv.koreaUnivRankSys.domain.mail.api;

import koreaUniv.koreaUnivRankSys.domain.mail.domain.MailAuthInfo;
import koreaUniv.koreaUnivRankSys.domain.mail.exception.NotMatchAuthCodeException;
import koreaUniv.koreaUnivRankSys.domain.mail.service.MailAuthInfoService;
import koreaUniv.koreaUnivRankSys.domain.mail.service.MailSendService;
import koreaUniv.koreaUnivRankSys.global.exception.ErrorResult;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;

@RestController
@RequiredArgsConstructor
public class MailSendController {

    private final MailSendService mailSendService;
    private final MailAuthInfoService mailAuthInfoService;

    @PostMapping("/mails")
    @ResponseStatus(value = HttpStatus.CREATED)
    public String sendAuthCode(@RequestBody EmailDto emailDto) throws MessagingException {

        String authCode = mailSendService.sendEmail(emailDto.getEmail());
        // 해당 이메일이 Member table 에 있는지 확인하는 로직 추가
        mailAuthInfoService.create(emailDto.getEmail(), authCode);
        return "인증번호를 전송했습니다.";
    }

    @GetMapping("/mails/check")
    @ResponseStatus(value = HttpStatus.OK)
    public String checkAuthCode(@RequestBody AuthCodeCheckDto authCodeCheckDto) {
        MailAuthInfo findInfo = mailAuthInfoService.findByEmail(authCodeCheckDto.getEmail());
        if(!findInfo.getAuthCode().equals(authCodeCheckDto.getAuthCode())) {
            throw new NotMatchAuthCodeException("인증번호가 일치하지 않습니다.");
        }

        return "인증에 성공했습니다";
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NotMatchAuthCodeException.class)
    public ErrorResult notMatchAuthCodeExHandler(NotMatchAuthCodeException e) {
        return new ErrorResult("BAD_REQUEST", e.getMessage());
    }

    @Data
    static class EmailDto {
        public String email;
    }

    @Data
    static class AuthCodeCheckDto {
        private String email;
        private String authCode;
    }

}
