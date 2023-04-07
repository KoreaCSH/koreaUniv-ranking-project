package koreaUniv.koreaUnivRankSys.controller;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberForm {

    private String string_id;
    private String email;
    private String password;
    private String nickName;
    private int grade;

}
