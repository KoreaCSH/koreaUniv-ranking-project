package koreaUniv.koreaUnivRankSys.batch.repository.building.history;

import koreaUniv.koreaUnivRankSys.batch.domain.building.history.CentralSquareRecordHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.Optional;

public interface CentralSquareRecordHistoryRepository extends JpaRepository<CentralSquareRecordHistory, Long> {

    @Query("SELECT c FROM CentralSquareRecordHistory c WHERE c.member.id = :id AND c.studyDate = :studyDate")
    Optional<CentralSquareRecordHistory> findByMemberIdAndStudyDate(@Param("id") Long id, @Param("studyDate") LocalDate studyDate);

}
