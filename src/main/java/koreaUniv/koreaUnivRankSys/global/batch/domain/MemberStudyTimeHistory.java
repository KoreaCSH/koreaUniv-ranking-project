package koreaUniv.koreaUnivRankSys.global.batch.domain;

import koreaUniv.koreaUnivRankSys.domain.member.domain.MemberStudyTime;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Getter
@NoArgsConstructor
public class MemberStudyTimeHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nickName;
    private Long memberDailyStudyTime;
    private LocalDate StudyDate;

    @Builder
    public MemberStudyTimeHistory(MemberStudyTime memberStudyTime) {
        this.nickName = memberStudyTime.getMember().getNickName();
        this.memberDailyStudyTime = memberStudyTime.getMemberDailyStudyTime();
        this.StudyDate = LocalDate.now().minusDays(1);
    }

}
