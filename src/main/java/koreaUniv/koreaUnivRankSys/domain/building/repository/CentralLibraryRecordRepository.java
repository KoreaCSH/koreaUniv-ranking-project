package koreaUniv.koreaUnivRankSys.domain.building.repository;

import koreaUniv.koreaUnivRankSys.domain.building.domain.CentralLibraryRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CentralLibraryRecordRepository extends JpaRepository<CentralLibraryRecord, Long> {

    Optional<CentralLibraryRecord> findByMemberUserId(String userId);

}
