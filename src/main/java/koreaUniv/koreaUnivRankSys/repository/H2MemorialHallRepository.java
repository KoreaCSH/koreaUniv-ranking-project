package koreaUniv.koreaUnivRankSys.repository;

import koreaUniv.koreaUnivRankSys.domain.MemorialHall;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class H2MemorialHallRepository implements MemorialHallRepository {

    private final EntityManager em;

    @Override
    public Long save(MemorialHall memorialHall) {
        em.persist(memorialHall);
        return memorialHall.getId();
    }

    @Override
    public Optional<MemorialHall> findOne(Long id) {
        return Optional.ofNullable(em.find(MemorialHall.class, id));
    }

    @Override
    public Optional<MemorialHall> findByStringId(String string_id) {
        List<MemorialHall> result = em.createQuery("select m from MemorialHall m where member.string_id =: string_id", MemorialHall.class)
                .setParameter("string_id", string_id)
                .getResultList();

        return result.stream().findAny();
    }

    @Override
    public List<MemorialHall> findAll() {
        return em.createQuery("select m from MemorialHall m", MemorialHall.class).getResultList();
    }
}
