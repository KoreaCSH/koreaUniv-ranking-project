package koreaUniv.koreaUnivRankSys.domain.building.domain;

import koreaUniv.koreaUnivRankSys.domain.member.domain.Member;
import koreaUniv.koreaUnivRankSys.global.common.BaseEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * 건물 내에서의 공부 기록을 저장하는 Entity
 * 과제 : 어떻게 슈퍼타입 (중복되는 공부 기록 관련 필드 저장) 과 서브타입을 나누어서 공부 기록을 관리할 수 있을까?
 * 과제의 필요성 : 확장성이 떨어진다, Member Entity 의 연관관계가 과중하다.
 */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CentralLibraryRecord extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "central_library_record_id")
    private Long id;

    @OneToOne(mappedBy = "centralLibraryRecord")
    private Member member;

    private Long dailyStudyTime;
    private Long weeklyStudyTime;
    private Long monthlyStudyTime;
    private Long totalStudyTime;

    public void setMember(Member member) {
        this.member = member;
    }

    public static CentralLibraryRecord createCentralLibraryRecord() {
        CentralLibraryRecord centralLibraryRecord = new CentralLibraryRecord();
        centralLibraryRecord.dailyStudyTime = 0L;
        centralLibraryRecord.weeklyStudyTime = 0L;
        centralLibraryRecord.monthlyStudyTime = 0L;
        centralLibraryRecord.totalStudyTime = 0L;
        return centralLibraryRecord;
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

    public void resetWeeklyStudyTime() {
        this.weeklyStudyTime = 0L;
    }

    public void resetMonthlyStudyTime() {
        this.monthlyStudyTime = 0L;
    }

}
