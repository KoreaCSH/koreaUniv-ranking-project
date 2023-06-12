package koreaUniv.koreaUnivRankSys.domain.building.service;

import koreaUniv.koreaUnivRankSys.domain.building.domain.ScienceLibraryRecord;
import koreaUniv.koreaUnivRankSys.domain.building.dto.MyRankingResponse;
import koreaUniv.koreaUnivRankSys.domain.building.dto.RankingDto;
import koreaUniv.koreaUnivRankSys.domain.building.repository.ScienceLibraryRecordRepository;
import koreaUniv.koreaUnivRankSys.domain.building.repository.ranking.ScienceLibraryRankingQueryRepository;
import koreaUniv.koreaUnivRankSys.domain.member.domain.Member;
import koreaUniv.koreaUnivRankSys.global.exception.CustomException;
import koreaUniv.koreaUnivRankSys.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ScienceLibraryRecordService {

    private final ScienceLibraryRecordRepository scienceLibraryRecordRepository;
    private final ScienceLibraryRankingQueryRepository scienceLibraryRankingQueryRepository;

    @Transactional
    public Long trackStudyTime(Member member, long studyTime) {
        ScienceLibraryRecord findRecord = findByMemberUserId(member.getUserId());
        findRecord.updateStudyTime(studyTime);
        return findRecord.getId();
    }

    public ScienceLibraryRecord findOne(Long id) {
        return scienceLibraryRecordRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.RECORD_NOTFOUND));
    }

    public ScienceLibraryRecord findByMemberUserId(String userId) {
        return scienceLibraryRecordRepository.findByMemberUserId(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.RECORD_NOTFOUND));
    }

    public List<RankingDto> findTotalRankings() {
        return scienceLibraryRankingQueryRepository.findRankingsByTotalStudyTime();
    }

    public List<RankingDto> findDailyRankings() {
        return scienceLibraryRankingQueryRepository.findRankingsByDailyStudyTime();
    }

    public List<RankingDto> findWeeklyRankings() {
        return scienceLibraryRankingQueryRepository.findRankingsByWeeklyStudyTime();
    }

    public List<RankingDto> findMonthlyRankings() {
        return scienceLibraryRankingQueryRepository.findRankingsByMonthlyStudyTime();
    }

    public MyRankingResponse findMyRankingByTotalStudyTime(String nickName) {
        return scienceLibraryRankingQueryRepository.findMyRankingByTotalStudyTime(nickName)
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_RECORD_NOTFOUND));
    }

}
