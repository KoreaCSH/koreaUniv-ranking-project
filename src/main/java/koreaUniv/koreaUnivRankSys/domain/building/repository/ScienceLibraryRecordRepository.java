package koreaUniv.koreaUnivRankSys.domain.building.repository;

import koreaUniv.koreaUnivRankSys.domain.building.domain.ScienceLibraryRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ScienceLibraryRecordRepository extends JpaRepository<ScienceLibraryRecord, Long> {

    Optional<ScienceLibraryRecord> findByMemberUserId(String userId);

}
