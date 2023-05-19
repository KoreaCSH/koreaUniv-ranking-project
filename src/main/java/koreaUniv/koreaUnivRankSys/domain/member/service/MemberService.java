package koreaUniv.koreaUnivRankSys.domain.member.service;

import koreaUniv.koreaUnivRankSys.domain.member.dto.MemberSignUpRequest;
import koreaUniv.koreaUnivRankSys.domain.member.dto.MemberUpdateRequest;
import koreaUniv.koreaUnivRankSys.domain.member.domain.Member;
import koreaUniv.koreaUnivRankSys.domain.member.domain.MemberImage;
import koreaUniv.koreaUnivRankSys.domain.member.exception.DuplicateMemberIdException;
import koreaUniv.koreaUnivRankSys.domain.member.exception.DuplicateMemberNickNameException;
import koreaUniv.koreaUnivRankSys.domain.member.repository.MemberRepository;
import koreaUniv.koreaUnivRankSys.global.exception.CustomException;
import koreaUniv.koreaUnivRankSys.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;
    private final MemberImageService memberImageService;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public Long join(MemberSignUpRequest request) {
        validateDuplicateMember(request.getUserId());
        validateDuplicateMemberNickName(request.getNickName());

        // 이메일 인증 받았는지에 대한 validate 한 번 더 검사

        String password = passwordEncoder.encode(request.getPassword());

        // request 값 valid 필요
        Member member = request.toEntity(password);

        if(request.getProfileImage() != null && !request.getProfileImage().isEmpty()) {
            MemberImage memberImage = memberImageService.createMemberImage(request.getProfileImage());
            member.setMemberImage(memberImage);
        }

        memberRepository.save(member);
        return member.getId();
}

    public void validateDuplicateMember(String id) {
        memberRepository.findByUserId(id).ifPresent(
                m -> {throw new CustomException(ErrorCode.MEMBER_ID_DUPLICATED);}
        );
    }

    public void validateDuplicateMemberNickName(String nickName) {
        memberRepository.findByNickName(nickName).ifPresent(
                m -> {throw new DuplicateMemberNickNameException();}
        );
    }

    @Transactional
    public Long updatePassword(Long id, String oldPassword, String newPassword) {
        Member findMember = memberRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("사용자를 찾을 수 없습니다."));

        findMember.changePassword(oldPassword, newPassword);
        return findMember.getId();
    }

    // nickName, profileMessage, memberImage 등 변경 가능
    @Transactional
    public Long updateMember(Long id, MemberUpdateRequest request) {
        // dirty checking 으로 update
        Member findMember = memberRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("사용자를 찾을 수 없습니다."));

        MemberImage currentMemberImage = findMember.getMemberImage();
        MultipartFile newProfileImage = request.getProfileImage();

        // newProfileImage 가 null 이 아닐 때만 변경
        if(newProfileImage != null && newProfileImage.isEmpty()) {
            if(currentMemberImage != null) {
                memberImageService.delete(findMember, currentMemberImage);
            }

            MemberImage memberImage = memberImageService.createMemberImage(request.getProfileImage());
            findMember.setMemberImage(memberImage);
        }

        // request.nickName valid 필요
        findMember.update(request);

        return findMember.getId();
    }

    public Member findById(Long id) {
        return memberRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("사용자를 찾을 수 없습니다."));
    }

    public Member findByUserId(String userId) {
        return memberRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalStateException("사용자를 찾을 수 없습니다."));
    }

    public Member findByNickName(String nickName) {
        return memberRepository.findByNickName(nickName)
                .orElseThrow(() -> new IllegalStateException("사용자를 찾을 수 없습니다."));
    }

    public List<Member> findAll() {
        return memberRepository.findAll();
    }

    public boolean existsByUserId(String userId) {
        return memberRepository.existsByUserId(userId);
    }

    public long findMemberTotalStudyingTime(String id) {
        return findByUserId(id).getMemberTotalStudyingTime();
    }

}
