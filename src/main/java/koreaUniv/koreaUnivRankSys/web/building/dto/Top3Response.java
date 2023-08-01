package koreaUniv.koreaUnivRankSys.web.building.dto;

import lombok.*;

import java.util.List;

@Getter
@Builder(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Top3Response {

    private List<Top3Dto> top3;
    private Integer count;

    public static Top3Response of(List<Top3Dto> top3) {
        return Top3Response.builder()
                .top3(top3)
                .count(top3.size())
                .build();
    }

}
