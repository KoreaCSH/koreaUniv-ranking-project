package koreaUniv.koreaUnivRankSys.batch.repository.building.history;

import koreaUniv.koreaUnivRankSys.batch.domain.building.history.EducationHallRecordHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.Optional;

public interface EducationHallRecordHistoryRepository extends JpaRepository<EducationHallRecordHistory, Long> {

    @Query("SELECT e FROM EducationHallRecordHistory e WHERE e.member.id = :id AND e.studyDate = :studyDate")
    Optional<EducationHallRecordHistory> findByMemberIdAndStudyDate(@Param("id") Long id, @Param("studyDate") LocalDate studyDate);

}
