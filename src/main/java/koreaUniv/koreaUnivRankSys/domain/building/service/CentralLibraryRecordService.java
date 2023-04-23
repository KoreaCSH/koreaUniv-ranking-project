package koreaUniv.koreaUnivRankSys.domain.building.service;

import koreaUniv.koreaUnivRankSys.domain.building.api.dto.RankingDto;
import koreaUniv.koreaUnivRankSys.domain.building.domain.CentralLibraryRecord;
import koreaUniv.koreaUnivRankSys.domain.building.repository.interfaces.CentralLibraryRecordRepository;
import koreaUniv.koreaUnivRankSys.domain.building.repository.ranking.CentralLibraryRankingQueryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CentralLibraryRecordService {

    private final CentralLibraryRecordRepository centralLibraryRecordRepository;
    private final CentralLibraryRankingQueryRepository centralLibraryRankingQueryRepository;

    public CentralLibraryRecord findOne(Long id) {
        return centralLibraryRecordRepository.findOne(id)
                .orElseThrow(() -> new IllegalStateException("공부 기록이 존재하지 않습니다."));
    }
    public CentralLibraryRecord findByStringId(String stringId) {
        return centralLibraryRecordRepository.findByStringId(stringId)
                .orElseThrow(() -> new IllegalStateException("공부 기록이 존재하지 않습니다."));
    }

    public List<CentralLibraryRecord> findAll() {
        return centralLibraryRecordRepository.findAll();
    }

    public List<RankingDto> findAllByRanking() {
        return centralLibraryRankingQueryRepository.findAllByRanking();
    }

    @Transactional
    public Long recordStudyingTime(String stringId, Long studyingTime) {
        CentralLibraryRecord findRecord = findByStringId(stringId);
        findRecord.updateStudyingTime(studyingTime);
        return findRecord.getId();
    }

}
