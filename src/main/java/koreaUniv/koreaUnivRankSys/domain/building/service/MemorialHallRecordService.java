package koreaUniv.koreaUnivRankSys.domain.building.service;


import koreaUniv.koreaUnivRankSys.domain.building.dto.RankingDto;
import koreaUniv.koreaUnivRankSys.domain.building.domain.MemorialHallRecord;
import koreaUniv.koreaUnivRankSys.domain.building.repository.interfaces.MemorialHallRecordRepository;
import koreaUniv.koreaUnivRankSys.domain.building.repository.ranking.MemorialHallRankingQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemorialHallRecordService {

    private final MemorialHallRecordRepository memorialHallRepository;
    private final MemorialHallRankingQueryRepository memorialHallRankingQueryRepository;

    public MemorialHallRecord findOne(Long id) {
        return memorialHallRepository.findOne(id)
                .orElseThrow(() -> new IllegalStateException("공부 기록이 존재하지 않습니다."));
    }

    public MemorialHallRecord findByStringId(String stringId) {
        return memorialHallRepository.findByStringId(stringId)
                .orElseThrow(() -> new IllegalStateException("공부 기록이 존재하지 않습니다."));
    }

    public List<MemorialHallRecord> findAll() {
        return memorialHallRepository.findAll();
    }

    public List<RankingDto> findAllByRanking() {
        return memorialHallRankingQueryRepository.findAllByRanking();
    }

    @Transactional
    public Long recordStudyingTime(String stringId, long studyingTime) {
        MemorialHallRecord findRecord = memorialHallRepository.findByStringId(stringId)
                .orElseThrow(() -> new IllegalStateException("공부 기록이 존재하지 않습니다."));

        findRecord.updateStudyingTime(studyingTime);
        return findRecord.getId();
    }

}
