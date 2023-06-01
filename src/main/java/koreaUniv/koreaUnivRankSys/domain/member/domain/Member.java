package koreaUniv.koreaUnivRankSys.domain.member.domain;

import koreaUniv.koreaUnivRankSys.domain.building.domain.CentralLibraryRecord;
import koreaUniv.koreaUnivRankSys.domain.building.domain.MemorialHallRecord;
import koreaUniv.koreaUnivRankSys.domain.member.dto.MemberUpdateRequest;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    private String userId;
    private String email;
    private String password;
    private String nickName;
    private String profileMessage;

    // 각 건물별 기록과의 연관관계에서 누가 주인이 되어야 할 지 고민해보자.

    // 양방향 관계
    @OneToOne(mappedBy = "member", cascade = CascadeType.ALL)
    //@JsonIgnore
    private MemorialHallRecord memorialHallRecord;

    // 양방향 관계
    @OneToOne(mappedBy = "member", cascade = CascadeType.ALL)
    //@JsonIgnore
    private CentralLibraryRecord centralLibraryRecord;

    @OneToOne(mappedBy = "member", cascade = CascadeType.ALL)
    private MemberStudyTime memberStudyTime;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "member_image_id")
    private MemberImage memberImage;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "college_id")
    private College college;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id")
    private Department department;

    // boolean departmentPublic

    // Builder 에 연관관계 편의 메서드 추가하면 어떻게 될까.
    @Builder
    public Member(String userId, String email, String password, String nickName, MemberImage memberImage,
                  College college, Department department) {

        this.userId = userId;
        this.email = email;
        this.password = password;
        this.nickName = nickName;
        this.profileMessage = null;
        this.setMemorialHallRecord(MemorialHallRecord.createMemorialHallRecord());
        this.setCentralLibraryRecord(CentralLibraryRecord.createCentralLibraryRecord());
        this.setMemberStudyTime(MemberStudyTime.createStudyTime());
        this.memberImage = memberImage;
        this.college = college;
        this.department = department;

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

    private void setMemberStudyTime(MemberStudyTime memberStudyTime) {
        this.memberStudyTime = memberStudyTime;
        memberStudyTime.setMember(this);
    }

    public void setMemberImage(MemberImage memberImage) {
        this.memberImage = memberImage;
    }

    /*
    * 비밀번호 변경 로직
    * */
    public void changePassword(String newPassword) {
        this.password = newPassword;
    }

    /*
     * memberInfo update 로직
     * */
    public void update(MemberUpdateRequest request) {
        if(StringUtils.hasText(request.getNickName())) {
            this.nickName = request.getNickName();
        }
        this.profileMessage = request.getProfileMessage();
    }

}
