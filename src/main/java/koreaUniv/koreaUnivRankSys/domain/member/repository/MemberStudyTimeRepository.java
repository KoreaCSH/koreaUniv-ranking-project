package koreaUniv.koreaUnivRankSys.domain.member.repository;

import koreaUniv.koreaUnivRankSys.domain.member.domain.MemberStudyTime;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberStudyTimeRepository extends JpaRepository<MemberStudyTime, Long> {

    Optional<MemberStudyTime> findByMemberUserId(String userId);

}
