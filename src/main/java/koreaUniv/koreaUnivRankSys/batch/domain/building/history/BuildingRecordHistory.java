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
public abstract class BuildingRecordHistory extends BaseEntity implements Comparable<BuildingRecordHistory> {

    private Long ranking;
    private Long studyTime;
    private LocalDate studyDate;

    @Enumerated(EnumType.STRING)
    private BuildingName buildingName;

    public BuildingRecordHistory(Long ranking, Long studyTime, BuildingName buildingName) {
        this.ranking = ranking;
        this.studyTime = studyTime;
        this.buildingName = buildingName;
        this.studyDate = LocalDate.now().minusDays(1);
    }

    @Override
    public int compareTo(BuildingRecordHistory o) {
        if(this.ranking.compareTo(o.ranking) == 0) {
            return this.studyDate.compareTo(o.studyDate);
        }

        return this.ranking.compareTo(o.ranking);
    }
}
