package koreaUniv.koreaUnivRankSys.domain.building;

import koreaUniv.koreaUnivRankSys.domain.Member;
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

    public static CentralLibraryRecord createCentralLibraryRecord() {
        CentralLibraryRecord centralLibraryRecord = new CentralLibraryRecord();
        centralLibraryRecord.dailyStudyingTime = 0L;
        centralLibraryRecord.weeklyStudyingTime = 0L;
        centralLibraryRecord.monthlyStudyingTime = 0L;
        centralLibraryRecord.totalStudyingTime = 0L;
        return centralLibraryRecord;
    }

    public void updateStudyingTime(Long studyingTime) {
        this.dailyStudyingTime += studyingTime;
        this.weeklyStudyingTime += studyingTime;
        this.monthlyStudyingTime += studyingTime;
        this.totalStudyingTime += studyingTime;
        this.member.updateMemberTotalStudyingTime(studyingTime);
    }
}
