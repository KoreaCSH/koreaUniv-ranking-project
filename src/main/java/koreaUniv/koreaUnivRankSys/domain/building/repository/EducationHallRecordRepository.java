package koreaUniv.koreaUnivRankSys.domain.building.repository;

import koreaUniv.koreaUnivRankSys.domain.building.domain.EducationHallRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EducationHallRecordRepository extends JpaRepository<EducationHallRecord, Long> {

    Optional<EducationHallRecord> findByMemberUserId(String userId);

}
