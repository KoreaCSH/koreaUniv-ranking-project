package koreaUniv.koreaUnivRankSys.batch.domain.building.top3;

import koreaUniv.koreaUnivRankSys.global.common.BaseEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDate;

@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public abstract class Top3 extends BaseEntity {

    private String nickName;
    private String imageUrl;
    private Long ranking;
    private Long studyTime;
    private LocalDate studyDate;

    public Top3(String nickName, String imageUrl, Long ranking, Long studyTime) {
        this.nickName = nickName;
        this.imageUrl = imageUrl;
        this.ranking = ranking;
        this.studyTime = studyTime;
        this.studyDate = LocalDate.now().minusDays(1);
    }

}
