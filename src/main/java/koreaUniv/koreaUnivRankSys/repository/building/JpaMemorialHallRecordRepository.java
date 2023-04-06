package koreaUniv.koreaUnivRankSys.repository.building;

import koreaUniv.koreaUnivRankSys.domain.building.MemorialHallRecord;
import koreaUniv.koreaUnivRankSys.repository.interfaces.MemorialHallRecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class JpaMemorialHallRecordRepository implements MemorialHallRecordRepository {

    private final EntityManager em;

    @Override
    public Long save(MemorialHallRecord memorialHallRecord) {
        em.persist(memorialHallRecord);
        return memorialHallRecord.getId();
    }

    @Override
    public Optional<MemorialHallRecord> findOne(Long id) {
        return Optional.ofNullable(em.find(MemorialHallRecord.class, id));
    }

    @Override
    public Optional<MemorialHallRecord> findByStringId(String string_id) {
        List<MemorialHallRecord> result = em.createQuery("select m from MemorialHallRecord m where m.member.string_id =: string_id", MemorialHallRecord.class)
                .setParameter("string_id", string_id)
                .getResultList();

        return result.stream().findAny();
    }

    @Override
    public List<MemorialHallRecord> findAll() {
        return em.createQuery("select m from MemorialHallRecord m", MemorialHallRecord.class).getResultList();
    }
}
