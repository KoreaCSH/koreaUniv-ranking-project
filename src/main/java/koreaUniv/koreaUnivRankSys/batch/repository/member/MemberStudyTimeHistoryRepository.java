package koreaUniv.koreaUnivRankSys.batch.repository.member;

import koreaUniv.koreaUnivRankSys.batch.domain.member.MemberStudyTimeHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.Optional;

public interface MemberStudyTimeHistoryRepository extends JpaRepository<MemberStudyTimeHistory, Long> {

    @Query("SELECT m FROM MemberStudyTimeHistory m WHERE m.member.id = :id AND m.studyDate = :studyDate")
    Optional<MemberStudyTimeHistory> findByMemberIdAndStudyDate(@Param("id") Long id, @Param("studyDate") LocalDate studyDate);

}
