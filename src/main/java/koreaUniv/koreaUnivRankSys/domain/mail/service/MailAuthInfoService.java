package koreaUniv.koreaUnivRankSys.domain.mail.service;

import koreaUniv.koreaUnivRankSys.domain.mail.domain.MailAuthInfo;
import koreaUniv.koreaUnivRankSys.domain.mail.repository.MailAuthInfoRepository;
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

        MailAuthInfo mailAuthInfo = MailAuthInfo.builder()
                    .email(email)
                    .authCode(authCode)
                    .build();

        mailAuthInfoRepository.create(mailAuthInfo);
        return mailAuthInfo.getId();
    }

    public MailAuthInfo findByEmail(String email) {
        return mailAuthInfoRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalStateException("잘못된 메일입니다"));
    }

}
