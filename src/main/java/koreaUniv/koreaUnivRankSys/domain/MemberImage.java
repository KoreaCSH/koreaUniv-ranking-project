package koreaUniv.koreaUnivRankSys.domain;

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
public class MemberImage {

    @Id
    @GeneratedValue
    @Column(name = "member_image_id")
    private long id;

    //
    private String originName;

}
