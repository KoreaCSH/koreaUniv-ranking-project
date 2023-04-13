package koreaUniv.koreaUnivRankSys.domain.building.repository.interfaces;

import koreaUniv.koreaUnivRankSys.domain.building.domain.MemorialHallRecord;

import java.util.List;
import java.util.Optional;

public interface MemorialHallRecordRepository {

    Long save(MemorialHallRecord memorialHallRecord);

    Optional<MemorialHallRecord> findOne(Long id);

    Optional<MemorialHallRecord> findByStringId(String stringId);

    List<MemorialHallRecord> findAll();

}
