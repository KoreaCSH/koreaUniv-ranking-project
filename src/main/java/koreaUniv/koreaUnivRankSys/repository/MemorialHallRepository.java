package koreaUniv.koreaUnivRankSys.repository;

import koreaUniv.koreaUnivRankSys.domain.MemorialHall;

import java.util.List;
import java.util.Optional;

public interface MemorialHallRepository {

    Long save(MemorialHall memorialHall);

    Optional<MemorialHall> findOne(Long id);

    Optional<MemorialHall> findByStringId(String stringId);

    List<MemorialHall> findAll();

}
