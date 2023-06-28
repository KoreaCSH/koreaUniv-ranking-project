package koreaUniv.koreaUnivRankSys.global.batch.domain.member;

import koreaUniv.koreaUnivRankSys.domain.member.domain.MemberStudyTime;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor
public class MemberStudyTimeHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nickName;
    private Long memberDailyStudyTime;
    private LocalDate studyDate;

    @Builder
    public MemberStudyTimeHistory(MemberStudyTime memberStudyTime) {
        this.nickName = memberStudyTime.getMember().getNickName();
        this.memberDailyStudyTime = memberStudyTime.getMemberDailyStudyTime();
        this.studyDate = LocalDate.now().minusDays(1);
    }

}
