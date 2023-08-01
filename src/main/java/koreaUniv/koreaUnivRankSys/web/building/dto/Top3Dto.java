package koreaUniv.koreaUnivRankSys.web.building.dto;

import koreaUniv.koreaUnivRankSys.batch.domain.building.top3.Top3;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class Top3Dto {

    private String nickName;
    private String imageUrl;
    private Long ranking;
    private Long studyTime;
    private LocalDate studyDate;

    public static Top3Dto of(Top3 top3) {
        Top3Dto top3Dto = new Top3Dto();
        top3Dto.nickName = top3.getNickName();
        top3Dto.imageUrl = top3.getImageUrl();
        top3Dto.ranking = top3.getRanking();
        top3Dto.studyTime = top3.getStudyTime();
        top3Dto.studyDate = top3.getStudyDate();
        return top3Dto;
    }

}
