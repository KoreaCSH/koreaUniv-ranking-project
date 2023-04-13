package koreaUniv.koreaUnivRankSys.service;

import koreaUniv.koreaUnivRankSys.domain.Member;
import koreaUniv.koreaUnivRankSys.exception.DuplicateMemberIdException;
import koreaUniv.koreaUnivRankSys.exception.DuplicateMemberNickNameException;
import koreaUniv.koreaUnivRankSys.repository.interfaces.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public Long join(Member member) {
        validateDuplicateMember(member);
        validateDuplicateMemberNickName(member);

        memberRepository.save(member);
        return member.getId();
    }
    private void validateDuplicateMember(Member member) {
        memberRepository.findById(member.getString_id()).ifPresent(
                m -> {throw new DuplicateMemberIdException();}
        );
    }

    private void validateDuplicateMemberNickName(Member member) {
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
