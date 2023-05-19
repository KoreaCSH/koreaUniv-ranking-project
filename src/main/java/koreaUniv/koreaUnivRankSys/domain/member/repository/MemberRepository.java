package koreaUniv.koreaUnivRankSys.domain.member.repository;

import koreaUniv.koreaUnivRankSys.domain.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByUserId(String userId);

    Optional<Member> findByNickName(String nickName);

    List<Member> findAll();

    boolean existsByUserId(String userId);

}
