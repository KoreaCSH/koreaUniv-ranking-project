package koreaUniv.koreaUnivRankSys.domain.building;

import koreaUniv.koreaUnivRankSys.domain.Member;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class CentralLibraryRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "central_library_record_id")
    private Long id;

    @OneToOne(mappedBy = "centralLibraryRecord", fetch = FetchType.LAZY)
    private Member member;

    private long startTime;
    private long finishTime;
    private long studyingTime;

    public void setMember(Member member) {
        this.member = member;
    }

    protected CentralLibraryRecord() {
    }

    public static CentralLibraryRecord createCentralLibraryRecord() {
        CentralLibraryRecord centralLibraryRecord = new CentralLibraryRecord();
        centralLibraryRecord.startTime = 0L;
        centralLibraryRecord.finishTime = 0L;
        centralLibraryRecord.studyingTime = 0L;
        return centralLibraryRecord;
    }

    public void recordStartTime() {
        this.startTime = System.currentTimeMillis();
    }

    public void recordFinishTime() {
        this.finishTime = System.currentTimeMillis();
    }

    public void recordStudyingTime() {
        this.studyingTime += this.finishTime - this.startTime;
    }
}
