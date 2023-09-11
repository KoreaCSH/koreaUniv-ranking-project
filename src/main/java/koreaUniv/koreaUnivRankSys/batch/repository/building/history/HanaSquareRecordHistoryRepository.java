package koreaUniv.koreaUnivRankSys.batch.repository.building.history;

import koreaUniv.koreaUnivRankSys.batch.domain.building.history.HanaSquareRecordHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.Optional;

public interface HanaSquareRecordHistoryRepository extends JpaRepository<HanaSquareRecordHistory, Long> {

    @Query("SELECT h FROM HanaSquareRecordHistory h WHERE h.member.id = :id AND h.studyDate = :studyDate")
    Optional<HanaSquareRecordHistory> findByMemberIdAndStudyDate(@Param("id") Long id, @Param("studyDate") LocalDate studyDate);

}
