package koreaUniv.koreaUnivRankSys.global.batch.domain.building;

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
public abstract class BuildingRecordHistory {

    private String nickName;
    private Long ranking;
    private Long studyTime;
    private LocalDate studyDate;

    public BuildingRecordHistory(String nickName, Long ranking, Long studyTime) {
        this.nickName = nickName;
        this.ranking = ranking;
        this.studyTime = studyTime;
        this.studyDate = LocalDate.now().minusDays(1);
    }

}
