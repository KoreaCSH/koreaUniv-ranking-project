package koreaUniv.koreaUnivRankSys.domain;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class CentralLibraryRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "central_library_record_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private long startTime;
    private long finishTime;
    private long studyingTime;

    protected CentralLibraryRecord() {
    }

    public static CentralLibraryRecord createCentralLibraryRecord(Member member) {
        CentralLibraryRecord centralLibraryRecord = new CentralLibraryRecord();
        centralLibraryRecord.member = member;
        centralLibraryRecord.startTime = 0L;
        centralLibraryRecord.finishTime = 0L;
        centralLibraryRecord.studyingTime = 0L;
        return centralLibraryRecord;
    }

    public void recordStartTime() {
        this.startTime = System.currentTimeMillis();
    }
}
