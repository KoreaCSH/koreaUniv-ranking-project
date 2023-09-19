package koreaUniv.koreaUnivRankSys.batch.repository.building.history;

import koreaUniv.koreaUnivRankSys.batch.domain.building.history.MemorialHallRecordHistory;
import koreaUniv.koreaUnivRankSys.batch.domain.building.history.ScienceLibraryRecordHistory;
import koreaUniv.koreaUnivRankSys.global.exception.CustomException;
import koreaUniv.koreaUnivRankSys.global.exception.ErrorCode;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ScienceLibraryRecordHistoryRepository extends JpaRepository<ScienceLibraryRecordHistory, Long> {

    @Query("SELECT s FROM ScienceLibraryRecordHistory s WHERE s.member.id = :id AND s.studyDate = :studyDate")
    Optional<ScienceLibraryRecordHistory> findByMemberIdAndStudyDate(@Param("id") Long id, @Param("studyDate") LocalDate studyDate);

    @Query("SELECT s FROM ScienceLibraryRecordHistory s WHERE s.member.id = :id ORDER BY s.ranking")
    List<ScienceLibraryRecordHistory> findTopByRanking(@Param("id")Long id, Pageable pageable);

    default ScienceLibraryRecordHistory findTop(Long id) {
        return findTopByRanking(id, PageRequest.of(0, 1)).stream().findAny()
                .orElseThrow(() -> new CustomException(ErrorCode.RECORD_NOTFOUND));
    }

}
