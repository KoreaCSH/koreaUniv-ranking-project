package koreaUniv.koreaUnivRankSys.domain.building.repository.interfaces;

import koreaUniv.koreaUnivRankSys.domain.building.domain.CentralLibraryRecord;

import java.util.List;
import java.util.Optional;

public interface CentralLibraryRecordRepository {

    Optional<CentralLibraryRecord> findByOne(Long id);

    Optional<CentralLibraryRecord> findByStringId(String stringId);

    List<CentralLibraryRecord> findAll();

}
