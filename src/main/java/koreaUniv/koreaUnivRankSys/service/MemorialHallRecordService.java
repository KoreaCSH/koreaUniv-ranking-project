package koreaUniv.koreaUnivRankSys.service;


import koreaUniv.koreaUnivRankSys.domain.MemorialHallRecord;
import koreaUniv.koreaUnivRankSys.repository.JpaMemorialHallRecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemorialHallRecordService {

    private final JpaMemorialHallRecordRepository memorialHallRepository;

    @Transactional
    public Long makeRecode(MemorialHallRecord memorialHallRecord) {
        memorialHallRepository.save(memorialHallRecord);
        return memorialHallRecord.getId();
    }

    @Transactional
    public Long updateStartTime(Long id) {
        MemorialHallRecord findRecord = memorialHallRepository.findOne(id)
                .orElseThrow(() -> new IllegalStateException("없는 기록입니다."));

        findRecord.recordStartTime();
        return findRecord.getId();
    }

    @Transactional
    public Long updateFinishTime(Long id) {
        MemorialHallRecord findRecord = memorialHallRepository.findOne(id)
                .orElseThrow(() -> new IllegalStateException("없는 기록입니다."));

        findRecord.recordFinishTime();
        return findRecord.getId();
    }

    @Transactional
    public Long updateStudyingTime(Long id) {
        MemorialHallRecord findRecord = memorialHallRepository.findOne(id)
                .orElseThrow(() -> new IllegalStateException("없는 기록입니다."));

        findRecord.recordStudyingTime();
        return findRecord.getId();
    }

}
