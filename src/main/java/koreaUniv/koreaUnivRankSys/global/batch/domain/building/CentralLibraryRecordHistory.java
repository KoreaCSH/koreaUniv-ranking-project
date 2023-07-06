package koreaUniv.koreaUnivRankSys.global.batch.domain.building;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CentralLibraryRecordHistory extends BuildingRecordHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "central_library_record_history_id")
    private Long id;

    @Builder
    public CentralLibraryRecordHistory(String nickName, Long ranking, Long studyTime) {
        super(nickName, ranking, studyTime);
    }

}
