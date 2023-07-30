package koreaUniv.koreaUnivRankSys.domain.building.service;

import koreaUniv.koreaUnivRankSys.batch.domain.building.top3.Top3;
import koreaUniv.koreaUnivRankSys.web.building.dto.MyRankingResponse;
import koreaUniv.koreaUnivRankSys.web.building.dto.RankingDto;
import koreaUniv.koreaUnivRankSys.domain.building.domain.CentralLibraryRecord;
import koreaUniv.koreaUnivRankSys.domain.building.repository.CentralLibraryRecordRepository;
import koreaUniv.koreaUnivRankSys.domain.building.repository.ranking.CentralLibraryRankingQueryRepository;
import koreaUniv.koreaUnivRankSys.batch.domain.building.top3.CentralLibraryTop3;
import koreaUniv.koreaUnivRankSys.batch.repository.building.top3.CentralLibraryTop3Repository;
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
public class CentralLibraryRecordService {

    private final CentralLibraryRecordRepository centralLibraryRecordRepository;
    private final CentralLibraryRankingQueryRepository centralLibraryRankingQueryRepository;
    private final CentralLibraryTop3Repository centralLibraryTop3Repository;

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

    /**
     * @return 중앙도서관 totalStudyTime 기준 랭킹 리스트
     */
    public List<RankingDto> findTotalRankings() {

        List<RankingDto> rankings = centralLibraryRankingQueryRepository.findRankingsByTotalStudyTime();

        if(CollectionUtils.isEmpty(rankings)) {
            throw new CustomException(ErrorCode.RANKING_NOTFOUND);
        }

        return rankings;
    }

    /**
     * @return 중앙도서관 dailyStudyTime 기준 랭킹 리스트를 조회
     */
    public List<RankingDto> findDailyRankings() {

        List<RankingDto> rankings = centralLibraryRankingQueryRepository.findRankingsByDailyStudyTime();

        if(CollectionUtils.isEmpty(rankings)) {
            throw new CustomException(ErrorCode.RANKING_NOTFOUND);
        }

        return rankings;
    }

    /**
     * @return 중앙도서관 weeklyStudyTime 기준 랭킹 리스트
     */
    public List<RankingDto> findWeeklyRankings() {

        List<RankingDto> rankings = centralLibraryRankingQueryRepository.findRankingsByWeeklyStudyTime();

        if(CollectionUtils.isEmpty(rankings)) {
            throw new CustomException(ErrorCode.RANKING_NOTFOUND);
        }

        return rankings;
    }

    /**
     * @return 중앙도서관 monthlyStudyTime 기준 랭킹 리스트
     */
    public List<RankingDto> findMonthlyRankings() {

        List<RankingDto> rankings = centralLibraryRankingQueryRepository.findRankingsByMonthlyStudyTime();

        if(CollectionUtils.isEmpty(rankings)) {
            throw new CustomException(ErrorCode.RANKING_NOTFOUND);
        }

        return rankings;
    }

    /**
     * @param nickName 해당 메서드를 호출한 멤버의 닉네임
     * @return 중앙도서관 totalStudyTime 기준 나의 랭킹과 나의 앞 순위 랭커 및 뒷 순위 랭커의 공부시간
     */
    public MyRankingResponse findMyRankingByTotalStudyTime(String nickName) {
        return centralLibraryRankingQueryRepository.findMyRankingByTotalStudyTime(nickName)
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_RECORD_NOTFOUND));
    }

    public List<Top3> findTop3ByWeeklyStudyTime() {

        List<Top3> top3 = centralLibraryTop3Repository.findTop3();

        if(CollectionUtils.isEmpty(top3)) {
            throw new CustomException(ErrorCode.RANKING_NOTFOUND);
        }

        return top3;
    }

}
