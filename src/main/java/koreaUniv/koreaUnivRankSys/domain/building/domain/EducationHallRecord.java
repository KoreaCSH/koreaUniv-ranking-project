package koreaUniv.koreaUnivRankSys.domain.building.domain;

import koreaUniv.koreaUnivRankSys.domain.member.domain.Member;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EducationHallRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "education_hall_record_id")
    private Long id;

    @OneToOne(mappedBy = "educationHallRecord")
    private Member member;

    private long dailyStudyTime;
    private long weeklyStudyTime;
    private long monthlyStudyTime;
    private long totalStudyTime;

    public void setMember(Member member) {
        this.member = member;
    }

    public static EducationHallRecord educationHallRecord() {
        EducationHallRecord educationHallRecord = new EducationHallRecord();
        educationHallRecord.dailyStudyTime = 0L;
        educationHallRecord.weeklyStudyTime = 0L;
        educationHallRecord.monthlyStudyTime = 0L;
        educationHallRecord.totalStudyTime = 0L;
        return educationHallRecord;
    }

    public void updateStudyTime(long studyTime) {
        this.dailyStudyTime += studyTime;
        this.weeklyStudyTime += studyTime;
        this.monthlyStudyTime += studyTime;
        this.totalStudyTime += studyTime;
        this.member.getMemberStudyTime().trackMemberStudyTime(studyTime);
    }

}
