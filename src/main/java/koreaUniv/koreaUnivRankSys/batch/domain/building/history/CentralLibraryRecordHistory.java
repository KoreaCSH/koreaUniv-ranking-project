package koreaUniv.koreaUnivRankSys.batch.domain.building.history;

import koreaUniv.koreaUnivRankSys.domain.member.domain.Member;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CentralLibraryRecordHistory extends BuildingRecordHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "central_library_record_history_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    public CentralLibraryRecordHistory(Member member, Long ranking, Long studyTime) {
        super(ranking, studyTime);
        setMember(member);
    }

    public void setMember(Member member) {
        this.member = member;
        member.getCentralLibraryRecordHistory().add(this);
    }

}
