package koreaUniv.koreaUnivRankSys.batch.repository.building.history;

import koreaUniv.koreaUnivRankSys.batch.domain.building.history.MemorialHallRecordHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.Optional;

public interface MemorialHallRecordHistoryRepository extends JpaRepository<MemorialHallRecordHistory, Long> {

    @Query("SELECT m FROM MemorialHallRecordHistory m WHERE m.member.id = :id AND m.studyDate = :studyDate")
    Optional<MemorialHallRecordHistory> findByMemberIdAndStudyDate(@Param("id") Long id, @Param("studyDate") LocalDate studyDate);

}
