package koreaUniv.koreaUnivRankSys.domain.building.service;


import koreaUniv.koreaUnivRankSys.domain.building.domain.MemorialHallRecord;
import koreaUniv.koreaUnivRankSys.domain.building.repository.interfaces.MemorialHallRecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemorialHallRecordService {

    private final MemorialHallRecordRepository memorialHallRepository;

    @Transactional
    public void recordStudyingTime(String stringId, long studyingTime) {
        MemorialHallRecord findRecord = memorialHallRepository.findByStringId(stringId)
                .orElseThrow(() -> new IllegalStateException("공부 기록이 존재하지 않습니다."));

        findRecord.updateStudyingTime(studyingTime);
    }

}
