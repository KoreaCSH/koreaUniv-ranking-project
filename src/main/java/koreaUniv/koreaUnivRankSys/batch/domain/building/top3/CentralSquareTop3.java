package koreaUniv.koreaUnivRankSys.batch.domain.building.top3;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter
@NoArgsConstructor
public class CentralSquareTop3 extends Top3 {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public CentralSquareTop3(String nickName, String imageUrl, Long ranking, Long studyTime) {
        super(nickName, imageUrl, ranking, studyTime);
    }

}
