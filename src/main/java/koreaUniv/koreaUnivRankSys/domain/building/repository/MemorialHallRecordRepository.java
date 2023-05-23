package koreaUniv.koreaUnivRankSys.domain.building.repository;

import koreaUniv.koreaUnivRankSys.domain.building.domain.MemorialHallRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MemorialHallRecordRepository extends JpaRepository<MemorialHallRecord, Long> {

    Optional<MemorialHallRecord> findByMemberUserId(String userId);

}
