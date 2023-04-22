package koreaUniv.koreaUnivRankSys.domain.building.api.dto;

import koreaUniv.koreaUnivRankSys.domain.member.domain.MemberImage;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CentralLibraryRankingDto {

    private Long ranking;
    private String nickName;
    private String path;
    private Long totalStudyingTime;

}
