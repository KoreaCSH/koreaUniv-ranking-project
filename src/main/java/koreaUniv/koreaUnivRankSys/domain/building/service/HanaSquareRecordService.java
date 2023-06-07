package koreaUniv.koreaUnivRankSys.domain.building.service;

import koreaUniv.koreaUnivRankSys.domain.building.domain.HanaSquareRecord;
import koreaUniv.koreaUnivRankSys.domain.building.dto.MyRankingResult;
import koreaUniv.koreaUnivRankSys.domain.building.dto.RankingDto;
import koreaUniv.koreaUnivRankSys.domain.building.repository.HanaSquareRecordRepository;
import koreaUniv.koreaUnivRankSys.domain.building.repository.ranking.HanaSquareRankingQueryRepository;
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
public class HanaSquareRecordService {

    private final HanaSquareRecordRepository hanaSquareRecordRepository;
    private final HanaSquareRankingQueryRepository hanaSquareRankingQueryRepository;

    @Transactional
    public Long trackStudyTime(Member member, long studyTime) {
        HanaSquareRecord findRecord = findByMemberUserId(member.getUserId());
        findRecord.updateStudyTime(studyTime);
        return findRecord.getId();
    }

    public HanaSquareRecord findOne(Long id) {
        return hanaSquareRecordRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.RECORD_NOTFOUND));
    }

    public HanaSquareRecord findByMemberUserId(String userId) {
        return hanaSquareRecordRepository.findByMemberUserId(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.RECORD_NOTFOUND));
    }

    public List<RankingDto> findTotalRankings() {
        return hanaSquareRankingQueryRepository.findRankingsByTotalStudyTime();
    }

    public List<RankingDto> findDailyRankings() {
        return hanaSquareRankingQueryRepository.findRankingsByDailyStudyTime();
    }

    public List<RankingDto> findWeeklyRankings() {
        return hanaSquareRankingQueryRepository.findRankingsByWeeklyStudyTime();
    }

    public MyRankingResult findMyRankingByTotalStudyTime(String nickName) {
        return hanaSquareRankingQueryRepository.findMyRankingByTotalStudyTime(nickName)
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_RECORD_NOTFOUND));
    }

}
