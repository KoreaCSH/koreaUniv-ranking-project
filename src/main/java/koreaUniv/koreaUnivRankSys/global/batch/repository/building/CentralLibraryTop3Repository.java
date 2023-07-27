package koreaUniv.koreaUnivRankSys.global.batch.repository.building;

import koreaUniv.koreaUnivRankSys.global.batch.domain.building.CentralLibraryTop3;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CentralLibraryTop3Repository extends JpaRepository<CentralLibraryTop3, Long> {

    // 저번 주 주간 랭킹 1, 2, 3등을 조회하는 쿼리 -> 추후 Service 객체 생성 후 Service 객체 내부에서 PageRequest.of(0, 3) 해주기
    // or default 메서드 정의해서 PageRequest.of(0, 3) 넘겨주기
    @Query("SELECT c FROM CentralLibraryTop3 c ORDER BY c.studyDate DESC, c.ranking")
    List<CentralLibraryTop3> findTop3ByStudyDateDescAndRanking(Pageable pageable);

    default List<CentralLibraryTop3> findTop3() {
        return findTop3ByStudyDateDescAndRanking(PageRequest.of(0, 3));
    }

}
