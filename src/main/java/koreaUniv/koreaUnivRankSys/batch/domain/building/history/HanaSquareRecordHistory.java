package koreaUniv.koreaUnivRankSys.batch.domain.building.history;

import koreaUniv.koreaUnivRankSys.domain.member.domain.Member;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class HanaSquareRecordHistory extends BuildingRecordHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "hana_square_record_history_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    public HanaSquareRecordHistory(Member member, Long ranking, Long studyTime) {
        super(ranking, studyTime);
        setMember(member);
    }

    public void setMember(Member member) {
        this.member = member;
        member.getHanaSquareRecordHistory().add(this);
    }

}
