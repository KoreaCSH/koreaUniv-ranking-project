package koreaUniv.koreaUnivRankSys.domain.building.domain;

import koreaUniv.koreaUnivRankSys.domain.member.domain.Member;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CentralLibraryRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "central_library_record_id")
    private Long id;

    @OneToOne(mappedBy = "centralLibraryRecord")
    private Member member;

    private long dailyStudyTime;
    private long weeklyStudyTime;
    private long monthlyStudyTime;
    private long totalStudyTime;

    public void setMember(Member member) {
        this.member = member;
    }

    public static CentralLibraryRecord createCentralLibraryRecord() {
        CentralLibraryRecord centralLibraryRecord = new CentralLibraryRecord();
        centralLibraryRecord.dailyStudyTime = 0L;
        centralLibraryRecord.weeklyStudyTime = 0L;
        centralLibraryRecord.monthlyStudyTime = 0L;
        centralLibraryRecord.totalStudyTime = 0L;
        return centralLibraryRecord;
    }

    public void updateStudyTime(Long studyTime) {
        this.dailyStudyTime += studyTime;
        this.weeklyStudyTime += studyTime;
        this.monthlyStudyTime += studyTime;
        this.totalStudyTime += studyTime;
        this.member.getMemberStudyTime().trackMemberStudyTime(studyTime);
    }
}
