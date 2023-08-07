package koreaUniv.koreaUnivRankSys.batch.domain.member;

import koreaUniv.koreaUnivRankSys.domain.member.domain.Member;
import koreaUniv.koreaUnivRankSys.domain.member.domain.MemberStudyTime;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class MemberStudyTimeHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_study_time_history")
    private Long id;

    /*
       Service 에서 Controller 로 넘길 때 DTO 생성해서 넘기기. Member 를 바로 넘기지 말 것.
     */

    private Long memberDailyStudyTime;
    private LocalDate studyDate;

    @ManyToOne
    private Member member;

    @Builder
    public MemberStudyTimeHistory(MemberStudyTime memberStudyTime) {
        setMember(memberStudyTime.getMember());
        this.memberDailyStudyTime = memberStudyTime.getMemberDailyStudyTime();
        this.studyDate = LocalDate.now().minusDays(1);
    }

    public void setMember(Member member) {
        this.member = member;
        member.getMemberStudyTimeHistory().add(this);
    }

}
