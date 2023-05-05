package koreaUniv.koreaUnivRankSys.domain.mail.service;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
@RequiredArgsConstructor
public class MailSendService {

    private final JavaMailSender javaMailSender;
    private final TemplateEngine templateEngine;
    private String authCode;

    public String sendEmail(String toEmail) throws MessagingException {

        MimeMessage emailForm = createEmailForm(toEmail);
        javaMailSender.send(emailForm);

        return authCode;
    }

    private void createAuthCode() {
        java.util.Random generator = new java.util.Random();
        generator.setSeed(System.currentTimeMillis());
        authCode = String.valueOf(generator.nextInt(1000000) % 1000000);
    }

    private MimeMessage createEmailForm(String email) throws MessagingException {

        createAuthCode();
        String setFrom = "seungheon7495@gmail.com";
        String toEmail = email;
        String title = "KoreaRankSys 회원가입 인증 번호";

        MimeMessage mailForm = javaMailSender.createMimeMessage();
        mailForm.addRecipients(MimeMessage.RecipientType.TO, toEmail);
        mailForm.setSubject(title);
        mailForm.setFrom(setFrom);
        mailForm.setText(setContext(authCode), "utf-8", "html");

        return mailForm;
    }

    private String setContext(String code) {
        Context context = new Context();
        context.setVariable("code", code);
        return templateEngine.process("mail", context); //mail.html
    }

}
