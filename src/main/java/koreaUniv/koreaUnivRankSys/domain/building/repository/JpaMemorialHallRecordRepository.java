package koreaUniv.koreaUnivRankSys.domain.building.repository;

import koreaUniv.koreaUnivRankSys.domain.building.domain.MemorialHallRecord;
import koreaUniv.koreaUnivRankSys.domain.building.repository.interfaces.MemorialHallRecordRepository;
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
    public Optional<MemorialHallRecord> findOne(Long id) {
        return Optional.ofNullable(em.find(MemorialHallRecord.class, id));
    }

    @Override
    public Optional<MemorialHallRecord> findByStringId(String userId) {
        List<MemorialHallRecord> result = em.createQuery("select m from MemorialHallRecord m where m.member.userId =: userId", MemorialHallRecord.class)
                .setParameter("userId", userId)
                .getResultList();

        return result.stream().findAny();
    }

    @Override
    public List<MemorialHallRecord> findAll() {
        return em.createQuery("select m from MemorialHallRecord m", MemorialHallRecord.class).getResultList();
    }

}
