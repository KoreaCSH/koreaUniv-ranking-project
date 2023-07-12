package koreaUniv.koreaUnivRankSys.domain.member.domain;

import koreaUniv.koreaUnivRankSys.global.common.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberImage extends BaseEntity {

    @Id
    @GeneratedValue
    @Column(name = "member_image_id")
    private long id;

    private String originName;
    private String fileName;
    private String path;

    @Builder
    public MemberImage(String originName, String fileName, String path) {
        this.originName = originName;
        this.fileName = fileName;
        this.path = path;
    }

}
