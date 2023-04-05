package koreaUniv.koreaUnivRankSys.repository;

import koreaUniv.koreaUnivRankSys.domain.CentralLibraryRecord;

import java.util.List;
import java.util.Optional;

public interface CentralLibraryRecordRepository {

    Long save(CentralLibraryRecord centralLibraryRecord);

    Optional<CentralLibraryRecord> findByOne(Long id);

    Optional<CentralLibraryRecord> findByStringId(String stringId);

    List<CentralLibraryRecord> findAll();

}
