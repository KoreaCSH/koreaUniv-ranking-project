package koreaUniv.koreaUnivRankSys.batch.domain.building.history;

import koreaUniv.koreaUnivRankSys.global.common.BaseEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDate;

@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public abstract class BuildingRecordHistory extends BaseEntity {

    private Long ranking;
    private Long studyTime;
    private LocalDate studyDate;

    public BuildingRecordHistory(Long ranking, Long studyTime) {
        this.ranking = ranking;
        this.studyTime = studyTime;
        this.studyDate = LocalDate.now().minusDays(1);
    }

}
