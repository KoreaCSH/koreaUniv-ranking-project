package koreaUniv.koreaUnivRankSys.batch.repository.building.top3;

import koreaUniv.koreaUnivRankSys.batch.domain.building.top3.HanaSquareTop3;
import koreaUniv.koreaUnivRankSys.batch.domain.building.top3.Top3;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface HanaSquareTop3Repository extends JpaRepository<HanaSquareTop3, Long> {

    @Query("SELECT h FROM HanaSquareTop3 h ORDER BY h.studyDate DESC, h.ranking")
    List<Top3> findTop3ByStudyDateDescAndRanking(Pageable pageable);

    default List<Top3> findTop3() {
        return findTop3ByStudyDateDescAndRanking(PageRequest.of(0, 3));
    }

}
