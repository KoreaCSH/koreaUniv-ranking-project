package koreaUniv.koreaUnivRankSys.domain.building.domain;

import koreaUniv.koreaUnivRankSys.domain.member.domain.Member;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class HanaSquareRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hana_square_record_id")
    private Long id;

    @OneToOne(mappedBy = "hanaSquareRecord")
    private Member member;

    private long dailyStudyTime;
    private long weeklyStudyTime;
    private long monthlyStudyTime;
    private long totalStudyTime;

    public void setMember(Member member) {
        this.member = member;
    }

    public static HanaSquareRecord createHanaSquareRecord() {
        HanaSquareRecord hanaSquareRecord = new HanaSquareRecord();
        hanaSquareRecord.dailyStudyTime = 0L;
        hanaSquareRecord.weeklyStudyTime = 0L;
        hanaSquareRecord.monthlyStudyTime = 0L;
        hanaSquareRecord.totalStudyTime = 0L;
        return hanaSquareRecord;
    }

    public void updateStudyTime(long studyTime) {
        this.dailyStudyTime += studyTime;
        this.weeklyStudyTime += studyTime;
        this.monthlyStudyTime += studyTime;
        this.totalStudyTime += studyTime;
        this.member.getMemberStudyTime().trackMemberStudyTime(studyTime);
    }

}
