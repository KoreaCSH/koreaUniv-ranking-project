package koreaUniv.koreaUnivRankSys.domain.building.repository;

import koreaUniv.koreaUnivRankSys.domain.building.domain.HanaSquareRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HanaSquareRecordRepository extends JpaRepository<HanaSquareRecord, Long> {

    Optional<HanaSquareRecord> findByMemberUserId(String userId);

}
