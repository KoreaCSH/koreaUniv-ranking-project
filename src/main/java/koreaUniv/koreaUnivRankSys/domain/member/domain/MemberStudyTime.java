package koreaUniv.koreaUnivRankSys.domain.member.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberStudyTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private long memberDailyStudyTime;
    private long memberWeeklyStudyTime;
    private long memberMonthlyStudyTime;
    private long memberTotalStudyTime;

    public static MemberStudyTime createStudyTime() {
        MemberStudyTime memberStudyTime = new MemberStudyTime();
        memberStudyTime.memberDailyStudyTime = 0;
        memberStudyTime.memberWeeklyStudyTime = 0;
        memberStudyTime.memberMonthlyStudyTime = 0;
        memberStudyTime.memberTotalStudyTime = 0;
        return memberStudyTime;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public void trackMemberStudyTime(long studyTime) {
        this.memberDailyStudyTime += studyTime;
        this.memberWeeklyStudyTime += studyTime;
        this.memberMonthlyStudyTime += studyTime;
        this.memberTotalStudyTime += studyTime;
    }

}
