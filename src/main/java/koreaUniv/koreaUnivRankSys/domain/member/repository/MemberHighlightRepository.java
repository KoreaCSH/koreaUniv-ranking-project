package koreaUniv.koreaUnivRankSys.domain.member.repository;

import koreaUniv.koreaUnivRankSys.domain.member.domain.MemberHighlight;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberHighlightRepository extends JpaRepository<MemberHighlight, Long> {

    Optional<MemberHighlight> findByMemberId(Long id);

}
