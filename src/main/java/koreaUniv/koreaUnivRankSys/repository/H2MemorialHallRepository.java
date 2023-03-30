package koreaUniv.koreaUnivRankSys.repository;

import koreaUniv.koreaUnivRankSys.domain.MemorialHall;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
public class H2MemorialHallRepository implements MemorialHallRepository {

    private final EntityManager em;

    @Override
    public Long save(MemorialHall memorialHall) {
        em.persist(memorialHall);
        return memorialHall.getId();
    }



}
