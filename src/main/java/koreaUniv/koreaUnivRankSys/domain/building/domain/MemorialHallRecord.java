package koreaUniv.koreaUnivRankSys.domain.building.domain;

import koreaUniv.koreaUnivRankSys.domain.member.domain.Member;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemorialHallRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "memorial_hall_record_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private long dailyStudyingTime;
    private long weeklyStudyingTime;
    private long monthlyStudyingTime;
    private long totalStudyingTime;

    public void setMember(Member member) {
        this.member = member;
    }

    public static MemorialHallRecord createMemorialHallRecord() {
        MemorialHallRecord memorialHallRecord = new MemorialHallRecord();
        memorialHallRecord.dailyStudyingTime = 0L;
        memorialHallRecord.weeklyStudyingTime = 0L;
        memorialHallRecord.monthlyStudyingTime = 0L;
        memorialHallRecord.totalStudyingTime = 0L;
        return memorialHallRecord;
    }

    public void updateStudyingTime(long studyingTime) {
        this.dailyStudyingTime += studyingTime;
        this.weeklyStudyingTime += studyingTime;
        this.monthlyStudyingTime += studyingTime;
        this.totalStudyingTime += studyingTime;
        this.member.updateMemberTotalStudyingTime(studyingTime);
    }

}
