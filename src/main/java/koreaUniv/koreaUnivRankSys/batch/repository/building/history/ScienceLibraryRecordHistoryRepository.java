package koreaUniv.koreaUnivRankSys.batch.repository.building.history;

import koreaUniv.koreaUnivRankSys.batch.domain.building.history.ScienceLibraryRecordHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.Optional;

public interface ScienceLibraryRecordHistoryRepository extends JpaRepository<ScienceLibraryRecordHistory, Long> {

    @Query("SELECT s FROM ScienceLibraryRecordHistory s WHERE s.member.id = :id AND s.studyDate = :studyDate")
    Optional<ScienceLibraryRecordHistory> findByMemberIdAndStudyDate(@Param("id") Long id, @Param("studyDate") LocalDate studyDate);

}
