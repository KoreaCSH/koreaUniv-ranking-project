package koreaUniv.koreaUnivRankSys.repository.interfaces;

import koreaUniv.koreaUnivRankSys.domain.building.CentralLibraryRecord;

import java.util.List;
import java.util.Optional;

public interface CentralLibraryRecordRepository {

    Optional<CentralLibraryRecord> findByOne(Long id);

    Optional<CentralLibraryRecord> findByStringId(String stringId);

    List<CentralLibraryRecord> findAll();

}
