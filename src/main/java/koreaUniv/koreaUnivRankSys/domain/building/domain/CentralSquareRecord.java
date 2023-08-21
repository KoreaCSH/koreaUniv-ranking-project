package koreaUniv.koreaUnivRankSys.domain.building.domain;

import koreaUniv.koreaUnivRankSys.domain.member.domain.Member;
import koreaUniv.koreaUnivRankSys.global.common.BaseEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;


// building 이라는 상위 클래스를 만들고, 나머지 building 클래스가 이를 상속받도록 설계하면 되지 않을까?
// 단, 자식 클래스의 1대 1, 다 대 다 관계는 어떻게 관리해야 하는걸까?

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CentralSquareRecord extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "central_square_record_id")
    private Long id;

    @OneToOne(mappedBy = "memorialHallRecord")
    private Member member;

    private Long dailyStudyTime;
    private Long weeklyStudyTime;
    private Long monthlyStudyTime;
    private Long totalStudyTime;

    public void setMember(Member member) {
        this.member = member;
    }

    public static CentralSquareRecord createCentralSquareRecord() {
        CentralSquareRecord centralSquareRecord = new CentralSquareRecord();
        centralSquareRecord.dailyStudyTime = 0L;
        centralSquareRecord.weeklyStudyTime = 0L;
        centralSquareRecord.monthlyStudyTime = 0L;
        centralSquareRecord.totalStudyTime = 0L;
        return centralSquareRecord;
    }

    public void updateStudyTime(Long studyTime) {
        this.dailyStudyTime += studyTime;
        this.weeklyStudyTime += studyTime;
        this.monthlyStudyTime += studyTime;
        this.totalStudyTime += studyTime;
        this.member.getMemberStudyTime().trackMemberStudyTime(studyTime);
    }

    public void resetDailyStudyTime() {
        this.dailyStudyTime = 0L;
    }

}
