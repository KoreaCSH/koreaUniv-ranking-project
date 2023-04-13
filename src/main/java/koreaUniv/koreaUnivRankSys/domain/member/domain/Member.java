package koreaUniv.koreaUnivRankSys.domain.member.domain;

import koreaUniv.koreaUnivRankSys.domain.building.domain.CentralLibraryRecord;
import koreaUniv.koreaUnivRankSys.domain.building.domain.MemorialHallRecord;
import koreaUniv.koreaUnivRankSys.domain.member.exception.NotMatchPasswordException;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    private String string_id;
    private String email;
    private String password;
    private String nickName;
    private long memberTotalStudyingTime;

    // 단과대학 추가
    // 학과 추가
    // 프로필 사진 추가

    @OneToOne(mappedBy = "member", cascade = CascadeType.ALL)
    private MemorialHallRecord memorialHallRecord;

    @OneToOne(mappedBy = "member", cascade = CascadeType.ALL)
    private CentralLibraryRecord centralLibraryRecord;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private MemberImage memberImage;

    // Builder 에 연관관계 편의 메서드 추가하면 어떻게 될까.
    @Builder
    public Member(String string_id, String email, String password, String nickName,
                  MemorialHallRecord memorialHallRecord, CentralLibraryRecord centralLibraryRecord) {

        this.string_id = string_id;
        this.email = email;
        this.password = password;
        this.nickName = nickName;
        this.memberTotalStudyingTime = 0L;
        this.setMemorialHallRecord(memorialHallRecord);
        this.setCentralLibraryRecord(centralLibraryRecord);

    }

    // 연관관계 편의 메서드
    public void setMemorialHallRecord(MemorialHallRecord memorialHallRecord) {
        this.memorialHallRecord = memorialHallRecord;
        memorialHallRecord.setMember(this);
    }
    public void setCentralLibraryRecord(CentralLibraryRecord centralLibraryRecord) {
        this.centralLibraryRecord = centralLibraryRecord;
        centralLibraryRecord.setMember(this);
    }


    /*
    * 비밀번호 변경 로직
    * */
    public void changePassword(String oldPassword, String newPassword) {
        if(!oldPassword.equals(this.password)) {
            throw new NotMatchPasswordException("현재 비밀번호와 일치하지 않습니다.");
        }
        this.password = newPassword;
    }

    /*
     * memberTotalStudyingTime update 로직
     * */
    public void updateMemberTotalStudyingTime(long studyingTime) {
        this.memberTotalStudyingTime += studyingTime;
    }

}
