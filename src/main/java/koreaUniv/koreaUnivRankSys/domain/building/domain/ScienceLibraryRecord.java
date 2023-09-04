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
public class ScienceLibraryRecord extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "science_library_record_id")
    private Long id;

    @OneToOne(mappedBy = "scienceLibraryRecord")
    private Member member;

    private Long dailyStudyTime;
    private Long weeklyStudyTime;
    private Long monthlyStudyTime;
    private Long totalStudyTime;

    public void setMember(Member member) {
        this.member = member;
    }

    public static ScienceLibraryRecord createScienceLibraryRecord() {
        ScienceLibraryRecord scienceLibraryRecord = new ScienceLibraryRecord();
        scienceLibraryRecord.dailyStudyTime = 0L;
        scienceLibraryRecord.weeklyStudyTime = 0L;
        scienceLibraryRecord.monthlyStudyTime = 0L;
        scienceLibraryRecord.totalStudyTime = 0L;
        return scienceLibraryRecord;
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
