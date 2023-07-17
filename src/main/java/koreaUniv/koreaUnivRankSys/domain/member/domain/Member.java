package koreaUniv.koreaUnivRankSys.domain.member.domain;

import koreaUniv.koreaUnivRankSys.domain.building.domain.*;
import koreaUniv.koreaUnivRankSys.domain.member.dto.MemberUpdateRequest;
import koreaUniv.koreaUnivRankSys.global.batch.domain.building.CentralLibraryRecordHistory;
import koreaUniv.koreaUnivRankSys.global.batch.domain.building.CentralSquareRecordHistory;
import koreaUniv.koreaUnivRankSys.global.common.BaseEntity;
import lombok.*;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseEntity {

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
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "memorial_hall_record_id")
    private MemorialHallRecord memorialHallRecord;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "central_library_record_id")
    private CentralLibraryRecord centralLibraryRecord;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "central_square_record_id")
    private CentralSquareRecord centralSquareRecord;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "education_hall_record_id")
    private EducationHallRecord educationHallRecord;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "hana_square_record_id")
    private HanaSquareRecord hanaSquareRecord;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "science_library_record_id")
    private ScienceLibraryRecord scienceLibraryRecord;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "member_study_time_id")
    //@JsonIgnore
    private MemberStudyTime memberStudyTime;

    @OneToMany(mappedBy = "member")
    private List<CentralLibraryRecordHistory> centralLibraryRecordHistory = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<CentralSquareRecordHistory> centralSquareRecordHistory = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "member_image_id")
    private MemberImage memberImage;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "college_id")
    private College college;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "department_id")
    private Department department;

    @Enumerated(EnumType.STRING)
    private MemberInfoStatus memberInfoStatus;

    @Builder
    public Member(String userId, String email, String password, String nickName, MemberImage memberImage,
                  String profileMessage, College college, Department department, MemberInfoStatus memberInfoStatus) {

        this.userId = userId;
        this.email = email;
        this.password = password;
        this.nickName = nickName;
        this.profileMessage = profileMessage;
        this.setMemorialHallRecord(MemorialHallRecord.createMemorialHallRecord());
        this.setCentralLibraryRecord(CentralLibraryRecord.createCentralLibraryRecord());
        this.setCentralSquareRecord(CentralSquareRecord.createCentralSquareRecord());
        this.setEducationHallRecord(EducationHallRecord.educationHallRecord());
        this.setHanaSquareRecord(HanaSquareRecord.createHanaSquareRecord());
        this.setScienceLibraryRecord(ScienceLibraryRecord.createScienceLibraryRecord());
        this.setMemberStudyTime(MemberStudyTime.createStudyTime());
        this.memberImage = memberImage;
        this.college = college;
        this.department = department;
        this.memberInfoStatus = memberInfoStatus;
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

    public void setCentralSquareRecord(CentralSquareRecord centralSquareRecord) {
        this.centralSquareRecord = centralSquareRecord;
        centralSquareRecord.setMember(this);
    }

    public void setEducationHallRecord(EducationHallRecord educationHallRecord) {
        this.educationHallRecord = educationHallRecord;
        educationHallRecord.setMember(this);
    }

    public void setHanaSquareRecord(HanaSquareRecord hanaSquareRecord) {
        this.hanaSquareRecord = hanaSquareRecord;
        hanaSquareRecord.setMember(this);
    }

    public void setScienceLibraryRecord(ScienceLibraryRecord scienceLibraryRecord) {
        this.scienceLibraryRecord = scienceLibraryRecord;
        scienceLibraryRecord.setMember(this);
    }

    private void setMemberStudyTime(MemberStudyTime memberStudyTime) {
        this.memberStudyTime = memberStudyTime;
        memberStudyTime.setMember(this);
    }

    public void setMemberImage(MemberImage memberImage) {
        this.memberImage = memberImage;
    }

    /**
     * 비밀번호 변경 로직
     */
    public void changePassword(String newPassword) {
        this.password = newPassword;
    }

    /**
     * memberInfo update 로직
     */
    public void update(MemberUpdateRequest request) {
        if(StringUtils.hasText(request.getNickName())) {
            this.nickName = request.getNickName();
        }
        this.profileMessage = request.getProfileMessage();
    }

}
