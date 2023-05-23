package koreaUniv.koreaUnivRankSys.domain.building.service;


import koreaUniv.koreaUnivRankSys.domain.building.dto.RankingDto;
import koreaUniv.koreaUnivRankSys.domain.building.domain.MemorialHallRecord;
import koreaUniv.koreaUnivRankSys.domain.building.repository.MemorialHallRecordRepository;
import koreaUniv.koreaUnivRankSys.domain.building.repository.ranking.MemorialHallRankingQueryRepository;
import koreaUniv.koreaUnivRankSys.global.exception.CustomException;
import koreaUniv.koreaUnivRankSys.global.exception.ErrorCode;
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
        return memorialHallRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.RECORD_NOTFOUND));
    }

    public MemorialHallRecord findByMemberUserId(String userId) {
        return memorialHallRepository.findByMemberUserId(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.RECORD_NOTFOUND));
    }

    public List<RankingDto> findAllByRanking() {
        return memorialHallRankingQueryRepository.findAllByRanking();
    }

    @Transactional
    public Long trackStudyTime(String userId, long studyingTime) {
        MemorialHallRecord findRecord = findByMemberUserId(userId);
        findRecord.updateStudyTime(studyingTime);
        return findRecord.getId();
    }

}
