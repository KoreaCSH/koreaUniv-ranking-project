package koreaUniv.koreaUnivRankSys.domain.member.domain;

import koreaUniv.koreaUnivRankSys.batch.domain.building.history.BuildingName;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberHighlight {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_highlight_id")
    private Long id;

    @OneToOne(mappedBy = "memberHighlight")
    private Member member;

    private String buildingName;
    private Long ranking;
    private LocalDate studyDate;

    public static MemberHighlight createMemberHighlight() {
        MemberHighlight memberHighlight = new MemberHighlight();
        memberHighlight.buildingName = BuildingName.NONE.getName();
        memberHighlight.ranking = Long.MAX_VALUE;
        memberHighlight.studyDate = LocalDate.now();
        return memberHighlight;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public void updateHighlight(String buildingName, Long ranking, LocalDate studyDate) {
        this.buildingName = buildingName;
        this.ranking = ranking;
        this.studyDate = studyDate;
    }

}
