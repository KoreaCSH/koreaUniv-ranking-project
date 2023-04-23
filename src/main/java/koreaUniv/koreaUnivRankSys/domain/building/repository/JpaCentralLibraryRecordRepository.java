package koreaUniv.koreaUnivRankSys.domain.building.repository;

import koreaUniv.koreaUnivRankSys.domain.building.domain.CentralLibraryRecord;
import koreaUniv.koreaUnivRankSys.domain.building.repository.interfaces.CentralLibraryRecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class JpaCentralLibraryRecordRepository implements CentralLibraryRecordRepository {

    private final EntityManager em;

    @Override
    public Optional<CentralLibraryRecord> findOne(Long id) {
        return Optional.ofNullable(em.find(CentralLibraryRecord.class, id));
    }

    @Override
    public Optional<CentralLibraryRecord> findByStringId(String stringId) {
        List<CentralLibraryRecord> result = em.createQuery("select c from CentralLibraryRecord c where c.member.string_id =: stringId",
                        CentralLibraryRecord.class)
                .setParameter("stringId", stringId)
                .getResultList();

        return result.stream().findAny();
    }

    @Override
    public List<CentralLibraryRecord> findAll() {
        return em.createQuery("select c from CentralLibraryRecord c", CentralLibraryRecord.class)
                .getResultList();
    }
}
