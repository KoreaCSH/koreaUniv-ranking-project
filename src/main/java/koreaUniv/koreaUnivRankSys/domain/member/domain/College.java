package koreaUniv.koreaUnivRankSys.domain.member.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class College {

    @Id
    @GeneratedValue
    @Column(name = "college_id")
    private Long id;

    @Column(name = "college_name")
    private String name;

}
