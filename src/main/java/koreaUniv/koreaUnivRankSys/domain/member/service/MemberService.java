package koreaUniv.koreaUnivRankSys.domain.member.service;

import koreaUniv.koreaUnivRankSys.domain.mail.service.MailAuthInfoService;
import koreaUniv.koreaUnivRankSys.domain.member.dto.MemberSignUpRequest;
import koreaUniv.koreaUnivRankSys.domain.member.dto.MemberUpdateRequest;
import koreaUniv.koreaUnivRankSys.domain.member.domain.Member;
import koreaUniv.koreaUnivRankSys.domain.member.domain.MemberImage;
import koreaUniv.koreaUnivRankSys.domain.member.repository.MemberRepository;
import koreaUniv.koreaUnivRankSys.global.exception.CustomException;
import koreaUniv.koreaUnivRankSys.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;
    private final MemberImageService memberImageService;
    private final PasswordEncoder passwordEncoder;
    private final MailAuthInfoService mailAuthInfoService;

    @Transactional
    public Long join(MemberSignUpRequest request) {
        validateDuplicateMember(request.getUserId());
        validateDuplicateMemberNickName(request.getNickName());
        mailAuthInfoService.validateMailAuth(request.getEmail() + "@korea.ac.kr");

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

    private void validateDuplicateMember(String userId) {
        memberRepository.findByUserId(userId).ifPresent(
                m -> {throw new CustomException(ErrorCode.MEMBER_USERID_DUPLICATED);}
        );
    }

    private void validateDuplicateMemberNickName(String nickName) {
        memberRepository.findByNickName(nickName).ifPresent(
                m -> {throw new CustomException(ErrorCode.MEMBER_NICKNAME_DUPLICATED);}
        );
    }

    // 수정 필요 - matches 써야하므로.
    @Transactional
    public Long updatePassword(Long id, String oldPassword, String newPassword) {
        Member findMember = memberRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("사용자를 찾을 수 없습니다."));

        // matches

        // true 라면 change, false 라면 Exception

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
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOTFOUND));
    }

    public Member findByUserId(String userId) {
        return memberRepository.findByUserId(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOTFOUND));
    }

    public Member findByNickName(String nickName) {
        return memberRepository.findByNickName(nickName)
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOTFOUND));
    }

    public List<Member> findAll() {
        return memberRepository.findAll();
    }

    public boolean existsByUserId(String userId) {
        return memberRepository.existsByUserId(userId);
    }

    public boolean existsByNickName(String nickName) {
        return memberRepository.existsByNickName(nickName);
    }

    public long findMemberTotalStudyingTime(String id) {
        return findByUserId(id).getMemberTotalStudyingTime();
    }

}
