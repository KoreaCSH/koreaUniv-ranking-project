package koreaUniv.koreaUnivRankSys.domain.building.service;

import koreaUniv.koreaUnivRankSys.domain.building.domain.EducationHallRecord;
import koreaUniv.koreaUnivRankSys.domain.building.dto.MyRankingResponse;
import koreaUniv.koreaUnivRankSys.domain.building.dto.RankingDto;
import koreaUniv.koreaUnivRankSys.domain.building.repository.EducationHallRecordRepository;
import koreaUniv.koreaUnivRankSys.domain.building.repository.ranking.EducationHallRankingQueryRepository;
import koreaUniv.koreaUnivRankSys.domain.member.domain.Member;
import koreaUniv.koreaUnivRankSys.global.exception.CustomException;
import koreaUniv.koreaUnivRankSys.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class EducationHallRecordService {

    private final EducationHallRecordRepository educationHallRecordRepository;
    private final EducationHallRankingQueryRepository educationHallRankingQueryRepository;

    @Transactional
    public Long trackStudyTime(Member member, Long studyTime) {
        EducationHallRecord findRecord = findByMemberUserId(member.getUserId());
        findRecord.updateStudyTime(studyTime);
        return findRecord.getId();
    }

    public EducationHallRecord findOne(Long id) {
        return educationHallRecordRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.RECORD_NOTFOUND));
    }

    public EducationHallRecord findByMemberUserId(String userId) {
        return educationHallRecordRepository.findByMemberUserId(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.RECORD_NOTFOUND));
    }

    public List<RankingDto> findTotalRankings() {

        List<RankingDto> rankings = educationHallRankingQueryRepository.findRankingsByTotalStudyTime();

        if(CollectionUtils.isEmpty(rankings)) {
            throw new CustomException(ErrorCode.RANKING_NOTFOUND);
        }

        return rankings;
    }

    public List<RankingDto> findDailyRankings() {

        List<RankingDto> rankings = educationHallRankingQueryRepository.findRankingsByDailyStudyTime();

        if(CollectionUtils.isEmpty(rankings)) {
            throw new CustomException(ErrorCode.RANKING_NOTFOUND);
        }

        return rankings;
    }

    public List<RankingDto> findWeeklyRankings() {

        List<RankingDto> rankings = educationHallRankingQueryRepository.findRankingsByWeeklyStudyTime();

        if(CollectionUtils.isEmpty(rankings)) {
            throw new CustomException(ErrorCode.RANKING_NOTFOUND);
        }

        return rankings;
    }

    public List<RankingDto> findMonthlyRankings() {

        List<RankingDto> rankings = educationHallRankingQueryRepository.findRankingsByMonthlyStudyTime();

        if(CollectionUtils.isEmpty(rankings)) {
            throw new CustomException(ErrorCode.RANKING_NOTFOUND);
        }

        return rankings;
    }

    public MyRankingResponse findMyRankingByTotalStudyTime(String nickName) {
        return educationHallRankingQueryRepository.findMyRankingByTotalStudyTime(nickName)
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_RECORD_NOTFOUND));
    }

}
