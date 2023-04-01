package koreaUniv.koreaUnivRankSys.domain;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class MemorialHallRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "memorial_hall_record_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private long startTime;
    private long finishTime;
    private long studyingTime;

    protected MemorialHallRecord() {
    }

    public static MemorialHallRecord createMemorialHallRecord(Member member) {
        MemorialHallRecord memorialHallRecord = new MemorialHallRecord();
        memorialHallRecord.member = member;
        memorialHallRecord.startTime = 0L;
        memorialHallRecord.finishTime = 0L;
        memorialHallRecord.studyingTime = 0L;
        return memorialHallRecord;
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
