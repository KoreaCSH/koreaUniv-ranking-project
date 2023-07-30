package koreaUniv.koreaUnivRankSys.web.member.dto;

import koreaUniv.koreaUnivRankSys.domain.member.domain.Member;
import koreaUniv.koreaUnivRankSys.domain.member.domain.MemberStudyTime;
import lombok.*;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MyPageResponse {

    private MemberInfoResponse memberInfoResponse;
    private MemberStudyTimeResponse memberStudyTimeResponse;

    @Builder
    public MyPageResponse(Member member, MemberStudyTime memberStudyTime) {
        this.memberInfoResponse = new MemberInfoResponse(member);
        this.memberStudyTimeResponse = new MemberStudyTimeResponse(memberStudyTime);
    }

    // 추후 학부 , 학과 추가
    // 하이라이트 - 최고 랭킹 추가

    public static MyPageResponse of(Member member, MemberStudyTime memberStudyTime) {
        return MyPageResponse.builder()
                .member(member)
                .memberStudyTime(memberStudyTime)
                .build();
    }

    @Getter
    @NoArgsConstructor
    public static class MemberInfoResponse {

        private String nickName;
        private String imageUrl;
        private String CollegeName;
        private String departmentName;

        public MemberInfoResponse(Member member) {
            this.nickName = member.getNickName();
            this.CollegeName = member.getCollege().getName();
            this.departmentName = member.getDepartment().getName();

            if(member.getMemberImage() != null) {
                this.imageUrl = member.getMemberImage().getPath();
            }

        }

    }

    @Getter
    @NoArgsConstructor
    public static class MemberStudyTimeResponse {

        private long memberDailyStudyTime;
        private long memberWeeklyStudyTime;
        private long memberMonthlyStudyTime;
        private long memberTotalStudyTime;

        public MemberStudyTimeResponse(MemberStudyTime memberStudyTime) {
            this.memberDailyStudyTime = memberStudyTime.getMemberDailyStudyTime();
            this.memberWeeklyStudyTime = memberStudyTime.getMemberWeeklyStudyTime();
            this.memberMonthlyStudyTime = memberStudyTime.getMemberMonthlyStudyTime();
            this.memberTotalStudyTime = memberStudyTime.getMemberTotalStudyTime();
        }

    }

}
