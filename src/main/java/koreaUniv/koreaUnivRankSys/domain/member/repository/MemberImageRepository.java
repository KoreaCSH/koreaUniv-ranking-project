package koreaUniv.koreaUnivRankSys.domain.member.repository;

import koreaUniv.koreaUnivRankSys.domain.member.domain.MemberImage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MemberImageRepository {

    private final EntityManager em;

    public Optional<MemberImage> findOne(Long id) {
        MemberImage memberImage = em.find(MemberImage.class, id);
        return Optional.ofNullable(memberImage);
    }

    public void delete(MemberImage memberImage) {
        em.remove(memberImage);
    }

}
