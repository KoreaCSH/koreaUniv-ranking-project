package koreaUniv.koreaUnivRankSys.batch.domain.building.history;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum BuildingName {

    CL("중앙도서관"),
    CS("중앙광장"),
    EH("교육관"),
    HS("하나스퀘어"),
    MH("백주년기념관"),
    SL("과학도서관"),
    NONE("아직 하이라이트 기록이 없습니다.");

    private String name;

}
