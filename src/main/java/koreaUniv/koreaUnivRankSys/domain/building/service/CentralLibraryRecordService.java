package koreaUniv.koreaUnivRankSys.domain.building.service;

import koreaUniv.koreaUnivRankSys.domain.building.dto.RankingDto;
import koreaUniv.koreaUnivRankSys.domain.building.domain.CentralLibraryRecord;
import koreaUniv.koreaUnivRankSys.domain.building.repository.CentralLibraryRecordRepository;
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
        return centralLibraryRecordRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("공부 기록이 존재하지 않습니다."));
    }
    public CentralLibraryRecord findByUserId(String userId) {
        return centralLibraryRecordRepository.findByMemberUserId(userId)
                .orElseThrow(() -> new IllegalStateException("공부 기록이 존재하지 않습니다."));
    }

    public List<CentralLibraryRecord> findAll() {
        return centralLibraryRecordRepository.findAll();
    }

    public List<RankingDto> findAllByRanking() {
        return centralLibraryRankingQueryRepository.findRankingsByTotalStudyTime();
    }

    @Transactional
    public Long recordStudyingTime(String userId, Long studyTime) {
        CentralLibraryRecord findRecord = findByUserId(userId);
        findRecord.updateStudyTime(studyTime);
        return findRecord.getId();
    }

}
