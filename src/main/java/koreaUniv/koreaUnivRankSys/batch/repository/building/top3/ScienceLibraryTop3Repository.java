package koreaUniv.koreaUnivRankSys.batch.repository.building.top3;

import koreaUniv.koreaUnivRankSys.batch.domain.building.top3.ScienceLibraryTop3;
import koreaUniv.koreaUnivRankSys.batch.domain.building.top3.Top3;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ScienceLibraryTop3Repository extends JpaRepository<ScienceLibraryTop3, Long> {

    @Query("SELECT s FROM ScienceLibraryTop3 s ORDER BY s.studyDate DESC, s.ranking")
    List<Top3> findTop3ByStudyDateDescAndRanking(Pageable pageable);

    default List<Top3> findTop3() {
        return findTop3ByStudyDateDescAndRanking(PageRequest.of(0, 3));
    }

}
