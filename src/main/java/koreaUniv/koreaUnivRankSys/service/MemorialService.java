package koreaUniv.koreaUnivRankSys.service;


import koreaUniv.koreaUnivRankSys.domain.MemorialHall;
import koreaUniv.koreaUnivRankSys.repository.H2MemorialHallRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemorialService {

    private final H2MemorialHallRepository memorialHallRepository;

    @Transactional
    public Long makeRecode(MemorialHall memorialHall) {
        memorialHallRepository.save(memorialHall);
        return memorialHall.getId();
    }

    @Transactional
    public Long updateStartTime(Long id) {
        MemorialHall findRecord = memorialHallRepository.findOne(id)
                .orElseThrow(() -> new IllegalStateException("없는 기록입니다."));

        findRecord.recordStartTime();
        return findRecord.getId();
    }

    @Transactional
    public Long updateFinishTime(Long id) {
        MemorialHall findRecord = memorialHallRepository.findOne(id)
                .orElseThrow(() -> new IllegalStateException("없는 기록입니다."));

        findRecord.recordFinishTime();
        return findRecord.getId();
    }

    @Transactional
    public Long updateStudyingTime(Long id) {
        MemorialHall findRecord = memorialHallRepository.findOne(id)
                .orElseThrow(() -> new IllegalStateException("없는 기록입니다."));

        findRecord.recordStudyingTime();
        return findRecord.getId();
    }

}
