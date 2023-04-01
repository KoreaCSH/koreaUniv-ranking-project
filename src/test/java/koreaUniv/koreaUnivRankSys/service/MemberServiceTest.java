package koreaUniv.koreaUnivRankSys.service;

import koreaUniv.koreaUnivRankSys.domain.Member;
import koreaUniv.koreaUnivRankSys.repository.JpaMemberRepository;
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
    JpaMemberRepository memberRepository;

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

    @Test
    void 회원찾기() {
        // given
        Member member = new Member("test", "1", "A");
        memberService.join(member);

        // when
        Member findMember = memberService.findById(member.getString_id()).get();

        // then
        Assertions.assertThat(member.getId()).isEqualTo(findMember.getId());
    }

    @Test
    void 회원가입_중복예외처리() {
        // given
        Member member1 = new Member("test2", "1", "A");
        memberService.join(member1);

        // when
        Member member2 = new Member("test2", "2", "B");

        // then
        assertThrows(IllegalStateException.class,
                () -> memberService.join(member2));
    }

}