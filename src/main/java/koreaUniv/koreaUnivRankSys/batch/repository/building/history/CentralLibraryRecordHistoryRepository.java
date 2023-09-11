package koreaUniv.koreaUnivRankSys.batch.repository.building.history;

import koreaUniv.koreaUnivRankSys.batch.domain.building.history.CentralLibraryRecordHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.Optional;

public interface CentralLibraryRecordHistoryRepository extends JpaRepository<CentralLibraryRecordHistory, Long> {

    @Query("SELECT c FROM CentralLibraryRecordHistory c WHERE c.member.id = :id AND c.studyDate = :studyDate")
    Optional<CentralLibraryRecordHistory> findByMemberIdAndStudyDate(@Param("id") Long id, @Param("studyDate")LocalDate studyDate);

}
