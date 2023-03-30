package koreaUniv.koreaUnivRankSys.domain;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

@Entity
@Getter
public class MemorialHall {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private long startTime;
    private long finishTime;
    private long studyingTime;

    protected MemorialHall() {
    }

    public MemorialHall createMemorialHallStudyingRecord(Member member) {
        MemorialHall memorialHall = new MemorialHall();
        memorialHall.member = member;
        memorialHall.startTime = 0L;
        memorialHall.finishTime = 0L;
        memorialHall.studyingTime = 0L;
        return memorialHall;
    }

}
