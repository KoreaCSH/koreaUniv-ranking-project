package koreaUniv.koreaUnivRankSys.domain.building.service;

import koreaUniv.koreaUnivRankSys.domain.building.dto.MyRankingResponse;
import koreaUniv.koreaUnivRankSys.domain.building.dto.RankingDto;
import koreaUniv.koreaUnivRankSys.domain.building.domain.CentralLibraryRecord;
import koreaUniv.koreaUnivRankSys.domain.building.repository.CentralLibraryRecordRepository;
import koreaUniv.koreaUnivRankSys.domain.building.repository.ranking.CentralLibraryRankingQueryRepository;
import koreaUniv.koreaUnivRankSys.global.exception.CustomException;
import koreaUniv.koreaUnivRankSys.global.exception.ErrorCode;
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

    @Transactional
    public Long trackStudyTime(String userId, Long studyTime) {
        CentralLibraryRecord findRecord = findByUserId(userId);
        findRecord.updateStudyTime(studyTime);
        return findRecord.getId();
    }
    public CentralLibraryRecord findOne(Long id) {
        return centralLibraryRecordRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.RECORD_NOTFOUND));
    }
    public CentralLibraryRecord findByUserId(String userId) {
        return centralLibraryRecordRepository.findByMemberUserId(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.RECORD_NOTFOUND));
    }

    public List<RankingDto> findTotalRankings() {
        return centralLibraryRankingQueryRepository.findRankingsByTotalStudyTime();
    }

    public List<RankingDto> findDailyRankings() {
        return centralLibraryRankingQueryRepository.findRankingsByDailyStudyTime();
    }

    public List<RankingDto> findWeeklyRankings() {
        return centralLibraryRankingQueryRepository.findRankingsByWeeklyStudyTime();
    }

    public List<RankingDto> findMonthlyRankings() {
        return centralLibraryRankingQueryRepository.findRankingsByMonthlyStudyTime();
    }

    public MyRankingResponse findMyRankingByTotalStudyTime(String nickName) {
        return centralLibraryRankingQueryRepository.findMyRankingByTotalStudyTime(nickName)
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_RECORD_NOTFOUND));
    }


}
