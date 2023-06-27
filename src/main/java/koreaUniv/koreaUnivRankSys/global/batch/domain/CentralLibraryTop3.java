package koreaUniv.koreaUnivRankSys.global.batch.domain;

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
public class CentralLibraryTop3 {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nickName;
    private String imageUrl;
    private Long ranking;
    private Long studyTime;
    private LocalDate studyDate;

    @Builder
    public CentralLibraryTop3(String nickName, String imageUrl, Long ranking, Long studyTime) {
        this.nickName = nickName;
        this.imageUrl = imageUrl;
        this.ranking = ranking;
        this.studyTime = studyTime;
        this.studyDate = LocalDate.now().minusDays(1);
    }

}
