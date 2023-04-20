package koreaUniv.koreaUnivRankSys.domain.building.api.dto;

import koreaUniv.koreaUnivRankSys.domain.member.domain.MemberImage;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CentralLibraryRankingDto {

    private Long ranking;
    private String nickName;
    private MemberImage memberImage;
    private long totalStudyingTime;

}
