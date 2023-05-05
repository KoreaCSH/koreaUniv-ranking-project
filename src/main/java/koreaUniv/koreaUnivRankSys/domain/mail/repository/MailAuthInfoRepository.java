package koreaUniv.koreaUnivRankSys.domain.mail.repository;

import koreaUniv.koreaUnivRankSys.domain.mail.domain.MailAuthInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MailAuthInfoRepository {

    private final EntityManager em;

    public Long create(MailAuthInfo mailAuthInfo) {
        em.persist(mailAuthInfo);
        return mailAuthInfo.getId();
    }

    public Optional<MailAuthInfo> findByEmail(String email) {
        List<MailAuthInfo> result = em.createQuery("select m from MailAuthInfo m where m.email =: email", MailAuthInfo.class)
                .setParameter("email", email)
                .getResultList();

        return result.stream().findAny();
    }

}
