package koreaUniv.koreaUnivRankSys.domain.member.service;

import koreaUniv.koreaUnivRankSys.domain.member.dto.MemberSignUpRequest;
import koreaUniv.koreaUnivRankSys.domain.member.domain.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired
    MemberService memberService;

    @Autowired
    JpaMemberRepository memberRepository;

    @Value("${koreaUniv.upload.path}")
    private String uploadPath;

    @Test
    void 회원가입_프로필_X() {
        //given
        MemberSignUpRequest request = new MemberSignUpRequest();
        request.setString_id("test1");
        request.setEmail("test1@gmail.com");
        request.setPassword("1234");
        request.setNickName("korea");

        //when
        Long memberId = memberService.join(request);

        //then
        Member findMember = memberService.findOne(memberId);
        Assertions.assertThat(request.getString_id()).isEqualTo(findMember.getString_id());
        Assertions.assertThat(request.getEmail()).isEqualTo(findMember.getEmail());
        Assertions.assertThat(request.getPassword()).isEqualTo(findMember.getPassword());
        Assertions.assertThat(request.getNickName()).isEqualTo(findMember.getNickName());
    }

    @Test
    void 회원가입_프로필_O() throws IOException {
        //given
        MockMultipartFile multipartFile = new MockMultipartFile("image", "gitprofile.jpeg", "image/jpeg",
                                                                new FileInputStream(uploadPath + File.separator +"gitprofile.jpeg"));

        MemberSignUpRequest request = new MemberSignUpRequest();
        request.setString_id("test1");
        request.setEmail("test1@gmail.com");
        request.setPassword("1234");
        request.setNickName("korea");
        request.setProfileImage(multipartFile);

        //when
        Long memberId = memberService.join(request);

        //then
        Member findMember = memberService.findOne(memberId);
        Assertions.assertThat(request.getString_id()).isEqualTo(findMember.getString_id());
        Assertions.assertThat(request.getEmail()).isEqualTo(findMember.getEmail());
        Assertions.assertThat(request.getPassword()).isEqualTo(findMember.getPassword());
        Assertions.assertThat(request.getNickName()).isEqualTo(findMember.getNickName());
        Assertions.assertThat(request.getProfileImage()).isEqualTo(multipartFile);
    }

//    @Test
//    void 회원찾기_id() {
//        //given
//        MemberSignUpRequest request = new MemberSignUpRequest();
//        request.setString_id("test1");
//        request.setEmail("test1@gmail.com");
//        request.setPassword("1234");
//        request.setNickName("korea");
//
//        Long memberId = memberService.join(request);
//
//        //when
//        Member findMember = memberService.findOne(memberId);
//
//        //then
//        Assertions.assertThat(member).isSameAs(findMember);
//    }
//
//    @Test
//    void 회원찾기_nickName() {
//        //given
//        Member member = Member.builder()
//                .string_id("test1")
//                .email("test1@gmail.com")
//                .password("1234")
//                .nickName("korea")
//                .memorialHallRecord(MemorialHallRecord.createMemorialHallRecord())
//                .centralLibraryRecord(CentralLibraryRecord.createCentralLibraryRecord())
//                .build();
//
//        Long memberId = memberService.join(member);
//
//        //when
//        Member findMember = memberService.findByNickName(member.getNickName());
//
//        //then
//        Assertions.assertThat(member.getId()).isEqualTo(findMember.getId());
//    }
//
//    @Test
//    void 회원가입_중복예외처리_byId() {
//        // given
//        Member member1 = Member.builder()
//                .string_id("test1")
//                .email("test1@gmail.com")
//                .password("1234")
//                .nickName("korea")
//                .memorialHallRecord(MemorialHallRecord.createMemorialHallRecord())
//                .centralLibraryRecord(CentralLibraryRecord.createCentralLibraryRecord())
//                .build();
//
//        memberService.join(member1);
//
//        Member member2 = Member.builder()
//                .string_id("test1")
//                .email("test1@gmail.com")
//                .password("1234")
//                .nickName("korea2")
//                .memorialHallRecord(MemorialHallRecord.createMemorialHallRecord())
//                .centralLibraryRecord(CentralLibraryRecord.createCentralLibraryRecord())
//                .build();
//
//
//        // when
//        assertThrows(DuplicateMemberIdException.class,
//                () -> memberService.join(member2));
//
//        // then
//    }
//
//    @Test
//    void 회원가입_중복예외처리_byNickName() {
//        // given
//        Member member1 = Member.builder()
//                .string_id("test1")
//                .email("test1@gmail.com")
//                .password("1234")
//                .nickName("korea")
//                .memorialHallRecord(MemorialHallRecord.createMemorialHallRecord())
//                .centralLibraryRecord(CentralLibraryRecord.createCentralLibraryRecord())
//                .build();
//
//        memberService.join(member1);
//
//        Member member2 = Member.builder()
//                .string_id("test2")
//                .email("test1@gmail.com")
//                .password("1234")
//                .nickName("korea")
//                .memorialHallRecord(MemorialHallRecord.createMemorialHallRecord())
//                .centralLibraryRecord(CentralLibraryRecord.createCentralLibraryRecord())
//                .build();
//
//
//        // when
//        assertThrows(DuplicateMemberNickNameException.class,
//                () -> memberService.join(member2));
//
//        // then
//    }
//
//    @Test
//    void 비밀번호_변경성공() {
//        // given
//        Member member1 = Member.builder()
//                .string_id("test1")
//                .email("test1@gmail.com")
//                .password("1234")
//                .nickName("korea")
//                .memorialHallRecord(MemorialHallRecord.createMemorialHallRecord())
//                .centralLibraryRecord(CentralLibraryRecord.createCentralLibraryRecord())
//                .build();
//
//        memberService.join(member1);
//
//        // when
//        memberService.updatePassword(member1.getId(), "1234", "1111");
//
//        // then
//        Assertions.assertThat(member1.getPassword()).isEqualTo("1111");
//    }
//
//    @Test
//    void 비밀번호_변경실패() {
//        // given
//        Member member1 = Member.builder()
//                .string_id("test1")
//                .email("test1@gmail.com")
//                .password("1234")
//                .nickName("korea")
//                .memorialHallRecord(MemorialHallRecord.createMemorialHallRecord())
//                .centralLibraryRecord(CentralLibraryRecord.createCentralLibraryRecord())
//                .build();
//
//        memberService.join(member1);
//
//        // when
//        assertThrows(NotMatchPasswordException.class,
//                () -> memberService.updatePassword(member1.getId(), "1111", "1234"));
//
//        // then
//    }
//
//    @Test
//    void memberTotalStudyingTime_조회() {
//        // given
//        Member member1 = Member.builder()
//                .string_id("test1")
//                .email("test1@gmail.com")
//                .password("1234")
//                .nickName("korea")
//                .memorialHallRecord(MemorialHallRecord.createMemorialHallRecord())
//                .centralLibraryRecord(CentralLibraryRecord.createCentralLibraryRecord())
//                .build();
//
//        // when
//        member1.updateMemberTotalStudyingTime(5);
//        memberService.join(member1);
//
//        long findStudyingTime = memberService.findMemberTotalStudyingTime(member1.getString_id());
//
//        // then
//        Assertions.assertThat(member1.getMemberTotalStudyingTime()).isEqualTo(findStudyingTime);
//    }

}