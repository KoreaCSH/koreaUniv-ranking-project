package koreaUniv.koreaUnivRankSys.domain.member.service;

import koreaUniv.koreaUnivRankSys.domain.member.api.dto.MemberSignUpRequest;
import koreaUniv.koreaUnivRankSys.domain.member.domain.Member;
import koreaUniv.koreaUnivRankSys.domain.member.domain.MemberImage;
import koreaUniv.koreaUnivRankSys.domain.member.exception.DuplicateMemberIdException;
import koreaUniv.koreaUnivRankSys.domain.member.exception.DuplicateMemberNickNameException;
import koreaUniv.koreaUnivRankSys.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;

    @Value("${koreaUniv.upload.path}")
    private String uploadPath;

    @Transactional
    public Long join(MemberSignUpRequest request) {
        validateDuplicateMember(request);
        validateDuplicateMemberNickName(request);

        Member member = request.toEntity();

        // test code - refactoring 필요
        if(request.getProfileImage() != null && !request.getProfileImage().isEmpty()) {
            MultipartFile profileImage = request.getProfileImage();
            String originName = profileImage.getOriginalFilename();
            String fileName = originName.substring(originName.lastIndexOf("\\") + 1);
            String saveName = uploadPath + File.separator + fileName;
            Path savePath = Paths.get(saveName);
            try {
                profileImage.transferTo(savePath);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            MemberImage memberImage = MemberImage.builder()
                    .originName(originName)
                    .fileName(fileName)
                    .path(saveName)
                    .build();

            member.setMemberImage(memberImage);
        }

        memberRepository.save(member);
        return member.getId();
    }
    private void validateDuplicateMember(MemberSignUpRequest request) {
        memberRepository.findById(request.getString_id()).ifPresent(
                m -> {throw new DuplicateMemberIdException();}
        );
    }

    private void validateDuplicateMemberNickName(MemberSignUpRequest member) {
        memberRepository.findByNickName(member.getNickName()).ifPresent(
                m -> {throw new DuplicateMemberNickNameException();}
        );
    }

    @Transactional
    public Long updatePassword(Long id, String oldPassword, String newPassword) {
        Member findMember = memberRepository.findOne(id)
                .orElseThrow(() -> new IllegalStateException("사용자를 찾을 수 없습니다."));

        findMember.changePassword(oldPassword, newPassword);
        return findMember.getId();
    }

    public Member findOne(Long id) {
        return memberRepository.findOne(id)
                .orElseThrow(() -> new IllegalStateException("사용자를 찾을 수 없습니다."));
    }

    public Member findById(String id) {
        return memberRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("사용자를 찾을 수 없습니다."));
    }

    public Member findByNickName(String nickName) {
        return memberRepository.findByNickName(nickName)
                .orElseThrow(() -> new IllegalStateException("사용자를 찾을 수 없습니다."));
    }

    public List<Member> findAll() {
        return memberRepository.findAll();
    }

    public long findMemberTotalStudyingTime(String id) {
        return findById(id).getMemberTotalStudyingTime();
    }

}
