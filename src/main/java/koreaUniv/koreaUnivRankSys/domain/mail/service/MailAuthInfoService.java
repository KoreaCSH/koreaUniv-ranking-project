package koreaUniv.koreaUnivRankSys.domain.mail.service;

import koreaUniv.koreaUnivRankSys.domain.mail.domain.MailAuthInfo;
import koreaUniv.koreaUnivRankSys.domain.mail.domain.MailAuthStatus;
import koreaUniv.koreaUnivRankSys.domain.mail.repository.MailAuthInfoRepository;
import koreaUniv.koreaUnivRankSys.global.exception.CustomException;
import koreaUniv.koreaUnivRankSys.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MailAuthInfoService {

    private final MailAuthInfoRepository mailAuthInfoRepository;

    @Transactional
    public Long create(String email, String authCode) {

        MailAuthInfo mailAuthInfo;

        if(existsByEmail(email)) {
            mailAuthInfo = findByEmail(email);
            validateDuplicateEmail(mailAuthInfo);
            mailAuthInfo.changeAuthCode(authCode);
        }
        else {
            mailAuthInfo = MailAuthInfo.builder()
                    .email(email)
                    .authCode(authCode)
                    .build();

            mailAuthInfoRepository.save(mailAuthInfo);
        }

        return mailAuthInfo.getId();
    }

    private void validateDuplicateEmail(MailAuthInfo mailAuthInfo) {

        if(mailAuthInfo.getStatus() == MailAuthStatus.Y) {
            throw new CustomException(ErrorCode.EMAIL_DUPLICATED);
        }

    }

    public void validateMailAuth(String email) {
        MailAuthInfo mailAuthInfo = findByEmail(email);
        if(!(mailAuthInfo.getStatus() == MailAuthStatus.Y)) {
            throw new CustomException(ErrorCode.NOT_AUTH_MAIL);
        }
    }

    @Transactional
    public void changeStatus(MailAuthInfo mailAuthInfo, MailAuthStatus status) {
        mailAuthInfo.changeStatus(status);
    }

    public boolean existsByEmail(String email) {
        return mailAuthInfoRepository.existsByEmail(email);
    }

    public MailAuthInfo findByEmail(String email) {
        return mailAuthInfoRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(ErrorCode.EMAIL_NOTFOUND));
    }

}
