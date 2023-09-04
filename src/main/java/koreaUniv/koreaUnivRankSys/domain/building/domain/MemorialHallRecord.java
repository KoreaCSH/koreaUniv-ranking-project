package koreaUniv.koreaUnivRankSys.domain.building.domain;

import koreaUniv.koreaUnivRankSys.domain.member.domain.Member;
import koreaUniv.koreaUnivRankSys.global.common.BaseEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemorialHallRecord extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "memorial_hall_record_id")
    private Long id;

    @OneToOne(mappedBy = "memorialHallRecord")
    private Member member;

    private Long dailyStudyTime;
    private Long weeklyStudyTime;
    private Long monthlyStudyTime;
    private Long totalStudyTime;

    public void setMember(Member member) {
        this.member = member;
    }

    public static MemorialHallRecord createMemorialHallRecord() {
        MemorialHallRecord memorialHallRecord = new MemorialHallRecord();
        memorialHallRecord.dailyStudyTime = 0L;
        memorialHallRecord.weeklyStudyTime = 0L;
        memorialHallRecord.monthlyStudyTime = 0L;
        memorialHallRecord.totalStudyTime = 0L;
        return memorialHallRecord;
    }

    public void updateStudyTime(Long studyTime) {
        this.dailyStudyTime += studyTime;
        this.weeklyStudyTime += studyTime;
        this.monthlyStudyTime += studyTime;
        this.totalStudyTime += studyTime;
        this.member.getMemberStudyTime().trackMemberStudyTime(studyTime);
    }

    public void resetDailyStudyTime() {
        this.dailyStudyTime = 0L;
    }

}
