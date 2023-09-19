package koreaUniv.koreaUnivRankSys.batch.repository.building.history;

import koreaUniv.koreaUnivRankSys.batch.domain.building.history.EducationHallRecordHistory;
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

public interface EducationHallRecordHistoryRepository extends JpaRepository<EducationHallRecordHistory, Long> {

    @Query("SELECT e FROM EducationHallRecordHistory e WHERE e.member.id = :id AND e.studyDate = :studyDate")
    Optional<EducationHallRecordHistory> findByMemberIdAndStudyDate(@Param("id") Long id, @Param("studyDate") LocalDate studyDate);

    @Query("SELECT e FROM EducationHallRecordHistory e WHERE e.member.id = :id ORDER BY e.ranking")
    List<EducationHallRecordHistory> findTopByRanking(@Param("id")Long id, Pageable pageable);

    default EducationHallRecordHistory findTop(Long id) {
        return findTopByRanking(id, PageRequest.of(0, 1)).stream().findAny()
                .orElseThrow(() -> new CustomException(ErrorCode.RECORD_NOTFOUND));
    }

}
