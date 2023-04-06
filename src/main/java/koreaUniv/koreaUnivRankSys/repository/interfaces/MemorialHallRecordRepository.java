package koreaUniv.koreaUnivRankSys.repository.interfaces;

import koreaUniv.koreaUnivRankSys.domain.building.MemorialHallRecord;

import java.util.List;
import java.util.Optional;

public interface MemorialHallRecordRepository {

    Long save(MemorialHallRecord memorialHallRecord);

    Optional<MemorialHallRecord> findOne(Long id);

    Optional<MemorialHallRecord> findByStringId(String stringId);

    List<MemorialHallRecord> findAll();

}
