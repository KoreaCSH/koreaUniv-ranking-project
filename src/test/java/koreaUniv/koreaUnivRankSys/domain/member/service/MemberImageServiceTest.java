package koreaUniv.koreaUnivRankSys.domain.member.service;

import koreaUniv.koreaUnivRankSys.domain.member.dto.MemberSignUpRequest;
import koreaUniv.koreaUnivRankSys.domain.member.domain.Member;
import koreaUniv.koreaUnivRankSys.domain.member.repository.MemberImageRepository;
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
class MemberImageServiceTest {

    @Autowired
    MemberService memberService;
    
    @Autowired
    MemberImageService memberImageService;

    @Autowired
    MemberImageRepository memberImageRepository;

    @Value("${koreaUniv.upload.path}")
    private String uploadPath;

    @Test
    void 프로필_삭제() throws IOException {
        // given
        MockMultipartFile multipartFile = new MockMultipartFile("image", "gitprofile.jpeg", "image/jpeg",
                new FileInputStream(uploadPath + File.separator +"gitprofile.jpeg"));

        MemberSignUpRequest request = new MemberSignUpRequest();
        request.setString_id("test1");
        request.setEmail("test1@gmail.com");
        request.setPassword("1234");
        request.setNickName("korea");
        request.setProfileImage(multipartFile);

        Long memberId = memberService.join(request);
        Member findMember = memberService.findOne(memberId);

        // when
        memberImageService.delete(findMember, findMember.getMemberImage());

        // then
        Assertions.assertThat(findMember.getMemberImage()).isNull();
    }

}