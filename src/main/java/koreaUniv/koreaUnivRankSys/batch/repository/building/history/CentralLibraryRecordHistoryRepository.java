package koreaUniv.koreaUnivRankSys.batch.repository.building.history;

import koreaUniv.koreaUnivRankSys.batch.domain.building.history.CentralLibraryRecordHistory;
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

public interface CentralLibraryRecordHistoryRepository extends JpaRepository<CentralLibraryRecordHistory, Long> {

    @Query("SELECT c FROM CentralLibraryRecordHistory c WHERE c.member.id = :id AND c.studyDate = :studyDate")
    Optional<CentralLibraryRecordHistory> findByMemberIdAndStudyDate(@Param("id") Long id, @Param("studyDate")LocalDate studyDate);

    @Query("SELECT c FROM CentralLibraryRecordHistory c WHERE c.member.id = :id ORDER BY c.ranking")
    List<CentralLibraryRecordHistory> findTopByRanking(@Param("id")Long id, Pageable pageable);

    default CentralLibraryRecordHistory findTop(Long id) {
        return findTopByRanking(id, PageRequest.of(0, 1)).stream().findAny()
                .orElseThrow(() -> new CustomException(ErrorCode.RECORD_NOTFOUND));
    }

}
