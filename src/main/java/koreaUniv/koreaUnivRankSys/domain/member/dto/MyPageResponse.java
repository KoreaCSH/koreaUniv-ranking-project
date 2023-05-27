package koreaUniv.koreaUnivRankSys.domain.member.dto;

import koreaUniv.koreaUnivRankSys.domain.member.domain.MemberStudyTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class MyPageResponse {

    // 추후 학부 , 학과 추가
    // 하이라이트 - 최고 랭킹 추가
    private String nickName;
    private long memberDailyStudyTime;
    private long memberWeeklyStudyTime;
    private long memberMonthlyStudyTime;
    private long memberTotalStudyTime;

    public static MyPageResponse of(String nickName, MemberStudyTime memberStudyTime) {
        return MyPageResponse.builder()
                .nickName(nickName)
                .memberDailyStudyTime(memberStudyTime.getMemberDailyStudyTime())
                .memberWeeklyStudyTime(memberStudyTime.getMemberWeeklyStudyTime())
                .memberMonthlyStudyTime(memberStudyTime.getMemberMonthlyStudyTime())
                .memberTotalStudyTime(memberStudyTime.getMemberTotalStudyTime())
                .build();
    }

}
