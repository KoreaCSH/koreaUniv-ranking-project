package koreaUniv.koreaUnivRankSys.batch.domain.building.top3;

import koreaUniv.koreaUnivRankSys.global.common.BaseEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor
@ToString
public class CentralLibraryTop3 extends Top3 {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public CentralLibraryTop3(String nickName, String imageUrl, Long ranking, Long studyTime) {
        super(nickName, imageUrl, ranking, studyTime);
    }

}
