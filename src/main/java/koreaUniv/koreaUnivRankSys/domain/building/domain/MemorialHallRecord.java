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

    @OneToOne(mappedBy = "memorialHallRecord")
    private Member member;

    private long dailyStudyTime;
    private long weeklyStudyTime;
    private long monthlyStudyTime;
    private long totalStudyTime;

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

    public void updateStudyTime(long studyTime) {
        this.dailyStudyTime += studyTime;
        this.weeklyStudyTime += studyTime;
        this.monthlyStudyTime += studyTime;
        this.totalStudyTime += studyTime;
        this.member.getMemberStudyTime().trackMemberStudyTime(studyTime);
    }

}
