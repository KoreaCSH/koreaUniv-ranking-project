package koreaUniv.koreaUnivRankSys.domain.building.service;


import koreaUniv.koreaUnivRankSys.domain.building.dto.MyRankingResponse;
import koreaUniv.koreaUnivRankSys.domain.building.dto.RankingDto;
import koreaUniv.koreaUnivRankSys.domain.building.domain.MemorialHallRecord;
import koreaUniv.koreaUnivRankSys.domain.building.repository.MemorialHallRecordRepository;
import koreaUniv.koreaUnivRankSys.domain.building.repository.ranking.MemorialHallRankingQueryRepository;
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
public class MemorialHallRecordService {

    private final MemorialHallRecordRepository memorialHallRepository;
    private final MemorialHallRankingQueryRepository memorialHallRankingQueryRepository;

    @Transactional
    public Long trackStudyTime(Member member, long studyTime) {
        MemorialHallRecord findRecord = findByMemberUserId(member.getUserId());
        findRecord.updateStudyTime(studyTime);
        return findRecord.getId();
    }

    public MemorialHallRecord findOne(Long id) {
        return memorialHallRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.RECORD_NOTFOUND));
    }

    public MemorialHallRecord findByMemberUserId(String userId) {
        return memorialHallRepository.findByMemberUserId(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.RECORD_NOTFOUND));
    }

    public List<RankingDto> findTotalRankings() {
        return memorialHallRankingQueryRepository.findRankingsByTotalStudyTime();
    }

    public List<RankingDto> findDailyRankings() {
        return memorialHallRankingQueryRepository.findRankingsByDailyStudyTime();
    }

    public List<RankingDto> findWeeklyRankings() {
        return memorialHallRankingQueryRepository.findRankingsByWeeklyStudyTime();
    }

    public MyRankingResponse findMyRankingByTotalStudyTime(String nickName) {
        return memorialHallRankingQueryRepository.findMyRankingByTotalStudyTime(nickName)
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_RECORD_NOTFOUND));
    }

}
