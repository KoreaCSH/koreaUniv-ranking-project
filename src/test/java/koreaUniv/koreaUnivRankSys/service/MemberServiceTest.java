package koreaUniv.koreaUnivRankSys.service;

import koreaUniv.koreaUnivRankSys.domain.Member;
import koreaUniv.koreaUnivRankSys.domain.building.CentralLibraryRecord;
import koreaUniv.koreaUnivRankSys.domain.building.MemorialHallRecord;
import koreaUniv.koreaUnivRankSys.exception.DuplicateMemberIdException;
import koreaUniv.koreaUnivRankSys.exception.DuplicateMemberNickNameException;
import koreaUniv.koreaUnivRankSys.exception.NotMatchPasswordException;
import koreaUniv.koreaUnivRankSys.repository.JpaMemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

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
        //given
        Member member = Member.builder()
                .string_id("test1")
                .email("test1@gmail.com")
                .password("1234")
                .nickName("korea")
                .memorialHallRecord(MemorialHallRecord.createMemorialHallRecord())
                .centralLibraryRecord(CentralLibraryRecord.createCentralLibraryRecord())
                .build();

        //when
        Long memberId = memberService.join(member);

        //then
        Assertions.assertThat(member.getId()).isEqualTo(memberId);
    }

    @Test
    void 회원찾기_id() {
        //given
        Member member = Member.builder()
                .string_id("test1")
                .email("test1@gmail.com")
                .password("1234")
                .nickName("korea")
                .memorialHallRecord(MemorialHallRecord.createMemorialHallRecord())
                .centralLibraryRecord(CentralLibraryRecord.createCentralLibraryRecord())
                .build();

        Long memberId = memberService.join(member);

        //when
        Member findMember = memberService.findOne(memberId);

        //then
        Assertions.assertThat(member).isSameAs(findMember);
    }

    @Test
    void 회원찾기_nickName() {
        //given
        Member member = Member.builder()
                .string_id("test1")
                .email("test1@gmail.com")
                .password("1234")
                .nickName("korea")
                .memorialHallRecord(MemorialHallRecord.createMemorialHallRecord())
                .centralLibraryRecord(CentralLibraryRecord.createCentralLibraryRecord())
                .build();

        Long memberId = memberService.join(member);

        //when
        Member findMember = memberService.findByNickName(member.getNickName());

        //then
        Assertions.assertThat(member.getId()).isEqualTo(findMember.getId());
    }

    @Test
    void 회원가입_중복예외처리_byId() {
        // given
        Member member1 = Member.builder()
                .string_id("test1")
                .email("test1@gmail.com")
                .password("1234")
                .nickName("korea")
                .memorialHallRecord(MemorialHallRecord.createMemorialHallRecord())
                .centralLibraryRecord(CentralLibraryRecord.createCentralLibraryRecord())
                .build();

        memberService.join(member1);

        Member member2 = Member.builder()
                .string_id("test1")
                .email("test1@gmail.com")
                .password("1234")
                .nickName("korea2")
                .memorialHallRecord(MemorialHallRecord.createMemorialHallRecord())
                .centralLibraryRecord(CentralLibraryRecord.createCentralLibraryRecord())
                .build();


        // when
        assertThrows(DuplicateMemberIdException.class,
                () -> memberService.join(member2));

        // then
    }

    @Test
    void 회원가입_중복예외처리_byNickName() {
        // given
        Member member1 = Member.builder()
                .string_id("test1")
                .email("test1@gmail.com")
                .password("1234")
                .nickName("korea")
                .memorialHallRecord(MemorialHallRecord.createMemorialHallRecord())
                .centralLibraryRecord(CentralLibraryRecord.createCentralLibraryRecord())
                .build();

        memberService.join(member1);

        Member member2 = Member.builder()
                .string_id("test2")
                .email("test1@gmail.com")
                .password("1234")
                .nickName("korea")
                .memorialHallRecord(MemorialHallRecord.createMemorialHallRecord())
                .centralLibraryRecord(CentralLibraryRecord.createCentralLibraryRecord())
                .build();


        // when
        assertThrows(DuplicateMemberNickNameException.class,
                () -> memberService.join(member2));

        // then
    }

    @Test
    void 비밀번호_변경성공() {
        // given
        Member member1 = Member.builder()
                .string_id("test1")
                .email("test1@gmail.com")
                .password("1234")
                .nickName("korea")
                .memorialHallRecord(MemorialHallRecord.createMemorialHallRecord())
                .centralLibraryRecord(CentralLibraryRecord.createCentralLibraryRecord())
                .build();

        memberService.join(member1);

        // when
        memberService.updatePassword(member1.getId(), "1234", "1111");

        // then
        Assertions.assertThat(member1.getPassword()).isEqualTo("1111");
    }

    @Test
    void 비밀번호_변경실패() {
        // given
        Member member1 = Member.builder()
                .string_id("test1")
                .email("test1@gmail.com")
                .password("1234")
                .nickName("korea")
                .memorialHallRecord(MemorialHallRecord.createMemorialHallRecord())
                .centralLibraryRecord(CentralLibraryRecord.createCentralLibraryRecord())
                .build();

        memberService.join(member1);

        // when
        assertThrows(NotMatchPasswordException.class,
                () -> memberService.updatePassword(member1.getId(), "1111", "1234"));

        // then
    }

    @Test
    void memberTotalStudyingTime_조회() {
        // given
        Member member1 = Member.builder()
                .string_id("test1")
                .email("test1@gmail.com")
                .password("1234")
                .nickName("korea")
                .memorialHallRecord(MemorialHallRecord.createMemorialHallRecord())
                .centralLibraryRecord(CentralLibraryRecord.createCentralLibraryRecord())
                .build();

        // when
        member1.updateMemberTotalStudyingTime(5);
        memberService.join(member1);

        long findStudyingTime = memberService.findMemberTotalStudyingTime(member1.getString_id());

        // then
        Assertions.assertThat(member1.getMemberTotalStudyingTime()).isEqualTo(findStudyingTime);
    }

}