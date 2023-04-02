package koreaUniv.koreaUnivRankSys.service;

import koreaUniv.koreaUnivRankSys.domain.Member;
import koreaUniv.koreaUnivRankSys.repository.JpaMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final JpaMemberRepository memberRepository;

    @Transactional
    public Long join(Member member) {
        validateDuplicateMember(member);

        memberRepository.save(member);
        return member.getId();
    }
    private void validateDuplicateMember(Member member) {
        memberRepository.findById(member.getString_id()).ifPresent(
                m -> {throw new IllegalStateException("이미 가입된 아이디입니다.");}
        );
    }

    @Transactional
    public Long updatePassword(Long id, String oldPassword, String newPassword) {
        Member findMember = memberRepository.findOne(id)
                .orElseThrow(() -> new IllegalStateException("사용자를 찾을 수 없습니다."));

        findMember.changePassword(oldPassword, newPassword);
        return findMember.getId();
    }

    public Optional<Member> findOne(Long id) {
        return memberRepository.findOne(id);
    }

    public Optional<Member> findById(String id) {
        return memberRepository.findById(id);
    }

    public List<Member> findAll() {
        return memberRepository.findAll();
    }

}
