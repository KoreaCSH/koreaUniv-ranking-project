package koreaUniv.koreaUnivRankSys.domain.member.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import koreaUniv.koreaUnivRankSys.global.common.BaseEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class MemberStudyTime extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_study_time_id")
    private Long id;

        @OneToOne(mappedBy = "memberStudyTime")
        private Member member;

        private Long memberDailyStudyTime;
        private Long memberWeeklyStudyTime;
        private Long memberMonthlyStudyTime;
        private Long memberTotalStudyTime;

        public static MemberStudyTime createStudyTime() {
            MemberStudyTime memberStudyTime = new MemberStudyTime();
            memberStudyTime.memberDailyStudyTime = 0L;
            memberStudyTime.memberWeeklyStudyTime = 0L;
            memberStudyTime.memberMonthlyStudyTime = 0L;
            memberStudyTime.memberTotalStudyTime = 0L;
            return memberStudyTime;
        }

        public void setMember(Member member) {
            this.member = member;
        }

        public void trackMemberStudyTime(Long studyTime) {
            this.memberDailyStudyTime += studyTime;
            this.memberWeeklyStudyTime += studyTime;
            this.memberMonthlyStudyTime += studyTime;
            this.memberTotalStudyTime += studyTime;
    }

}
