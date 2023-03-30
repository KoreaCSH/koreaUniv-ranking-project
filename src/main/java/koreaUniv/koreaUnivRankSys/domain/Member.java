package koreaUniv.koreaUnivRankSys.domain;

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
    private String name;

    public Member(String string_id, String password, String name) {
        this.string_id = string_id;
        this.password = password;
        this.name = name;
    }
}
