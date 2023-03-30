package koreaUniv.koreaUnivRankSys.service;

import koreaUniv.koreaUnivRankSys.domain.Member;
import koreaUniv.koreaUnivRankSys.repository.H2MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired
    MemberService memberService;

    @Autowired
    H2MemberRepository memberRepository;

    @Test
    void 회원가입() {
        // given
        Member member = new Member("test", "1", "최승헌");

        // when
        memberService.join(member);

        // then
        Member findMember = memberService.findOne(member.getId()).get();
        Assertions.assertThat(member.getId()).isEqualTo(findMember.getId());
    }

}