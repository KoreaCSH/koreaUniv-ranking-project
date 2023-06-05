package koreaUniv.koreaUnivRankSys.domain.building.domain;

import koreaUniv.koreaUnivRankSys.domain.member.domain.Member;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CentralSquareRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "central_square_record_id")
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

    public static CentralSquareRecord createCentralSquareRecord() {
        CentralSquareRecord centralSquareRecord = new CentralSquareRecord();
        centralSquareRecord.dailyStudyTime = 0L;
        centralSquareRecord.weeklyStudyTime = 0L;
        centralSquareRecord.monthlyStudyTime = 0L;
        centralSquareRecord.totalStudyTime = 0L;
        return centralSquareRecord;
    }

    public void updateStudyTime(long studyTime) {
        this.dailyStudyTime += studyTime;
        this.weeklyStudyTime += studyTime;
        this.monthlyStudyTime += studyTime;
        this.totalStudyTime += studyTime;
        this.member.getMemberStudyTime().trackMemberStudyTime(studyTime);
    }

}
