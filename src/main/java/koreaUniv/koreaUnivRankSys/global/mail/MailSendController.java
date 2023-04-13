package koreaUniv.koreaUnivRankSys.global.mail;

import koreaUniv.koreaUnivRankSys.global.mail.MailSendService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;

@RestController
@RequiredArgsConstructor
public class MailSendController {

    private final MailSendService mailSendService;

    @PostMapping("signIn/mailConfirm")
    public String mailConfirm(@RequestBody EmailDto emailDto) throws MessagingException {
        String authCode = mailSendService.sendEmail(emailDto.getEmail());
        return authCode;
    }

    @Data
    static class EmailDto {
        public String email;
    }

}
