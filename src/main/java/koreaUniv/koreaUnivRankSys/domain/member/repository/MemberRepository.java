package koreaUniv.koreaUnivRankSys.domain.member.repository;

import koreaUniv.koreaUnivRankSys.domain.member.domain.Member;

import java.util.List;
import java.util.Optional;

public interface MemberRepository {

    void save(Member member);

    Optional<Member> findOne(Long id);

    Optional<Member> findById(String id);

    Optional<Member> findByNickName(String nickName);

    List<Member> findAll();

}
