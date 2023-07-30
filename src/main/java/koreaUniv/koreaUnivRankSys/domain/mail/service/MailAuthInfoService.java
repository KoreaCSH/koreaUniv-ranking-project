package koreaUniv.koreaUnivRankSys.domain.mail.service;

import koreaUniv.koreaUnivRankSys.domain.mail.domain.MailAuthInfo;
import koreaUniv.koreaUnivRankSys.domain.mail.domain.MailAuthStatus;
import koreaUniv.koreaUnivRankSys.web.mail.dto.AuthCodeRequest;
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

        // 다시 요청한 경우 authCode 만 변경한다.
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

    @Transactional
    public void validateAuthCode(String email, AuthCodeRequest request) {
        MailAuthInfo findInfo = findByEmail(email);

        if(!findInfo.getAuthCode().equals(request.getAuthCode())) {
            throw new CustomException(ErrorCode.NOT_MATCH_AUTHCODE);
        }

        findInfo.changeStatus(MailAuthStatus.Y);
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

    public boolean existsByEmail(String email) {
        return mailAuthInfoRepository.existsByEmail(email);
    }

    public MailAuthInfo findByEmail(String email) {
        return mailAuthInfoRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(ErrorCode.EMAIL_NOTFOUND));
    }

}
