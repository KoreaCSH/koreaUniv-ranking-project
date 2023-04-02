package koreaUniv.koreaUnivRankSys.domain;

import koreaUniv.koreaUnivRankSys.exception.NotMatchPasswordException;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    private String string_id;
    private String password;
    private String nickName;
    private int grade;
    // 단과대학 추가
    // 학과 추가
    // 프로필 사진 추가

    protected Member() {
    }

    public Member(String string_id, String password, String nickName, int grade) {
        this.string_id = string_id;
        this.password = password;
        this.nickName = nickName;
        this.grade = grade;
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

}
