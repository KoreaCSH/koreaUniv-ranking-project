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

    public String sendEmail(String toEmail, String authCode) throws MessagingException {

        MimeMessage emailForm = createEmailForm(toEmail, authCode);
        javaMailSender.send(emailForm);

        return authCode;
    }

    public String createAuthCode() {
        String authCode;
        java.util.Random generator = new java.util.Random();
        generator.setSeed(System.currentTimeMillis());
        authCode = String.valueOf(generator.nextInt(1000000) % 1000000);
        return authCode;
    }

    private MimeMessage createEmailForm(String email, String authCode) throws MessagingException {

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

    private String setContext(String authCode) {
        Context context = new Context();
        context.setVariable("code", authCode);
        return templateEngine.process("mail", context); //mail.html
    }

}
