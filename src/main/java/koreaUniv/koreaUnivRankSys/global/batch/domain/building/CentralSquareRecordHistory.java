package koreaUniv.koreaUnivRankSys.global.batch.domain.building;

import koreaUniv.koreaUnivRankSys.domain.member.domain.Member;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CentralSquareRecordHistory extends BuildingRecordHistory{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "central_square_record_history_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    public CentralSquareRecordHistory(Member member, Long ranking, Long studyTime) {
        super(ranking, studyTime);
        setMember(member);
    }

    public void setMember(Member member) {
        this.member = member;
        member.getCentralSquareRecordHistory().add(this);
    }

}
