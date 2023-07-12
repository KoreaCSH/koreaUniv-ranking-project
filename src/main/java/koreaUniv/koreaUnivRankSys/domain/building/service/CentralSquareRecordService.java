package koreaUniv.koreaUnivRankSys.domain.building.service;

import koreaUniv.koreaUnivRankSys.domain.building.domain.CentralSquareRecord;
import koreaUniv.koreaUnivRankSys.domain.building.dto.MyRankingResponse;
import koreaUniv.koreaUnivRankSys.domain.building.dto.RankingDto;
import koreaUniv.koreaUnivRankSys.domain.building.repository.CentralSquareRecordRepository;
import koreaUniv.koreaUnivRankSys.domain.building.repository.ranking.CentralSquareRankingQueryRepository;
import koreaUniv.koreaUnivRankSys.domain.member.domain.Member;
import koreaUniv.koreaUnivRankSys.global.exception.CustomException;
import koreaUniv.koreaUnivRankSys.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CentralSquareRecordService {

    private final CentralSquareRecordRepository centralSquareRecordRepository;
    private final CentralSquareRankingQueryRepository centralSquareRankingQueryRepository;

    @Transactional
    public Long trackStudyTime(Member member, Long studyTime) {
        CentralSquareRecord findRecord = findByMemberUserId(member.getUserId());
        findRecord.updateStudyTime(studyTime);
        return findRecord.getId();
    }

    public CentralSquareRecord findOne(Long id) {
        return centralSquareRecordRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.RECORD_NOTFOUND));
    }

    public CentralSquareRecord findByMemberUserId(String userId) {
        return centralSquareRecordRepository.findByMemberUserId(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.RECORD_NOTFOUND));
    }

    public List<RankingDto> findTotalRankings() {
        return centralSquareRankingQueryRepository.findRankingsByTotalStudyTime();
    }

    public List<RankingDto> findDailyRankings() {
        return centralSquareRankingQueryRepository.findRankingsByDailyStudyTime();
    }

    public List<RankingDto> findWeeklyRankings() {
        return centralSquareRankingQueryRepository.findRankingsByWeeklyStudyTime();
    }

    public List<RankingDto> findMonthlyRankings() {
        return centralSquareRankingQueryRepository.findRankingsByMonthlyStudyTime();
    }

    public MyRankingResponse findMyRankingByTotalStudyTime(String nickName) {
        return centralSquareRankingQueryRepository.findMyRankingByTotalStudyTime(nickName)
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_RECORD_NOTFOUND));
    }

}
