package koreaUniv.koreaUnivRankSys.domain.mail.repository;

import koreaUniv.koreaUnivRankSys.domain.mail.domain.MailAuthInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

public interface MailAuthInfoRepository extends JpaRepository<MailAuthInfo, Long> {

    Optional<MailAuthInfo> findByEmail(String email);

    boolean existsByEmail(String email);

}
