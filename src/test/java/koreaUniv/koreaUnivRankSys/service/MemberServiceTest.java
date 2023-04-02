package koreaUniv.koreaUnivRankSys.service;

import koreaUniv.koreaUnivRankSys.domain.Member;
import koreaUniv.koreaUnivRankSys.exception.NotMatchPasswordException;
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
        Member member = new Member("test", "1", "최승헌", 3);

        // when
        memberService.join(member);

        // then
        Member findMember = memberService.findOne(member.getId()).get();
        Assertions.assertThat(member.getId()).isEqualTo(findMember.getId());
    }

    @Test
    void 회원찾기() {
        // given
        Member member = new Member("test", "1", "A", 3);
        memberService.join(member);

        // when
        Member findMember = memberService.findById(member.getString_id()).get();

        // then
        Assertions.assertThat(member.getId()).isEqualTo(findMember.getId());
    }

    @Test
    void 회원가입_중복예외처리() {
        // given
        Member member1 = new Member("test2", "1", "A", 3);
        memberService.join(member1);

        // when
        Member member2 = new Member("test2", "2", "B", 3);

        // then
        assertThrows(IllegalStateException.class,
                () -> memberService.join(member2));
    }

    @Test
    void 비밀번호_변경성공() {
        // given
        Member member = new Member("test", "1", "hi", 3);
        memberService.join(member);

        // when
        memberService.updatePassword(member.getId(), "1", "2");

        // then
        Assertions.assertThat(member.getPassword()).isEqualTo("2");
    }

    @Test
    void 비밀번호_변경실패() {
        // given
        Member member = new Member("test", "1", "hi", 3);
        memberService.join(member);

        // when
        assertThrows(NotMatchPasswordException.class,
                () -> memberService.updatePassword(member.getId(), "2", "3"));

        // then
    }

}