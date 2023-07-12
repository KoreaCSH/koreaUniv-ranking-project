package koreaUniv.koreaUnivRankSys.domain.member.service;

import koreaUniv.koreaUnivRankSys.domain.member.domain.Member;
import koreaUniv.koreaUnivRankSys.domain.member.domain.MemberStudyTime;
import koreaUniv.koreaUnivRankSys.domain.member.dto.MyPageResponse;
import koreaUniv.koreaUnivRankSys.domain.member.repository.MemberRepository;
import koreaUniv.koreaUnivRankSys.domain.member.repository.MemberStudyTimeRepository;
import koreaUniv.koreaUnivRankSys.global.exception.CustomException;
import koreaUniv.koreaUnivRankSys.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberStudyTimeService {

    private final MemberStudyTimeRepository memberStudyTimeRepository;
    private final MemberRepository memberRepository;

    public MyPageResponse getMyPage(Member member) {

        // 공부기록 중 가장 높은 랭킹을 어떻게 찾아서 넘겨줄까?
        // 추후 현재 접속한 닉네임과 요청한 닉네임이 같다면 '자신의 마이페이지'를, 다르다면 '타인의 마이페이지'를 리턴하도록 구현
        Member findMember = memberRepository.findByUserId(member.getUserId())
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOTFOUND));

        MemberStudyTime findMemberStudyTime = findByUserId(member.getUserId());

        return MyPageResponse.of(findMember, findMemberStudyTime);
    }

    public MemberStudyTime findByUserId(String userId) {

        return memberStudyTimeRepository.findByMemberUserId(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_RECORD_NOTFOUND));
    }

}
