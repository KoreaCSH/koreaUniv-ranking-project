package koreaUniv.koreaUnivRankSys.domain.building.repository;

import koreaUniv.koreaUnivRankSys.domain.building.domain.CentralSquareRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CentralSquareRecordRepository extends JpaRepository<CentralSquareRecord, Long> {

    Optional<CentralSquareRecord> findByMemberUserId(String userId);

}
