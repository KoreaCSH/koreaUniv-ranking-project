package koreaUniv.koreaUnivRankSys.domain.member.service;

import koreaUniv.koreaUnivRankSys.batch.domain.building.history.*;
import koreaUniv.koreaUnivRankSys.batch.domain.member.MemberStudyTimeHistory;
import koreaUniv.koreaUnivRankSys.batch.repository.building.history.*;
import koreaUniv.koreaUnivRankSys.batch.repository.member.MemberStudyTimeHistoryRepository;
import koreaUniv.koreaUnivRankSys.domain.member.domain.Member;
import koreaUniv.koreaUnivRankSys.domain.member.repository.MemberRepository;
import koreaUniv.koreaUnivRankSys.global.exception.CustomException;
import koreaUniv.koreaUnivRankSys.global.exception.ErrorCode;
import koreaUniv.koreaUnivRankSys.web.member.dto.PlannerRequest;
import koreaUniv.koreaUnivRankSys.web.member.dto.PlannerResponse;
import koreaUniv.koreaUnivRankSys.web.member.dto.StudyTimeDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.ZoneId;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PlannerService {

    // toDo List
    // todayTotalStudyTime
    // 건물별 todayTotalStudyTime
    private final MemberRepository memberRepository;
    private final MemberStudyTimeHistoryRepository memberStudyTimeHistoryRepository;
    private final CentralLibraryRecordHistoryRepository centralLibraryRecordHistoryRepository;
    private final CentralSquareRecordHistoryRepository centralSquareRecordHistoryRepository;
    private final EducationHallRecordHistoryRepository educationHallRecordHistoryRepository;
    private final HanaSquareRecordHistoryRepository hanaSquareRecordHistoryRepository;
    private final MemorialHallRecordHistoryRepository memorialHallRecordHistoryRepository;
    private final ScienceLibraryRecordHistoryRepository scienceLibraryRecordHistoryRepository;

    public PlannerResponse getMyPlanner(Member member, PlannerRequest request) {

        validateStudyDate(request.getStudyDate());
        Member findMember = findAndValidateMember(member);

        LocalDate today = LocalDate.now(ZoneId.of("Asia/Seoul"));
        LocalDate studyDate = request.getStudyDate();
        StudyTimeDto studyTimeDto;

        // studyDate 가 오늘일 경우
        if (today.compareTo(studyDate) == 0) {
            studyTimeDto = getTodayStudyTime(findMember);
        }
        // studyDate 가 과거인 경우
        else if(today.compareTo(studyDate) > 0) {
            studyTimeDto = getStudyTimeHistory(findMember, studyDate);
        }
        // 조회할 수 없는 경우
        else {
            throw new CustomException(ErrorCode.INVALID_DATE);
        }

        return PlannerResponse.builder().studyTimeDto(studyTimeDto).build();
    }

    private void validateStudyDate(LocalDate date) {
        if(date == null) {
            throw new CustomException(ErrorCode.INVALID_DATE);
        }
    }

    private Member findAndValidateMember(Member member) {
        return memberRepository.findByUserId(member.getUserId())
                .orElseThrow(() -> new CustomException(ErrorCode.MEMBER_NOTFOUND));
    }

    private StudyTimeDto getTodayStudyTime(Member member) {
        Long dailyTotalStudyTime = member.getMemberStudyTime().getMemberDailyStudyTime();
        Long centralLibraryDailyStudyTime = member.getCentralLibraryRecord().getDailyStudyTime();
        Long centralSquareDailyStudyTime = member.getCentralSquareRecord().getDailyStudyTime();
        Long educationHallDailyStudyTime = member.getEducationHallRecord().getDailyStudyTime();
        Long hanaSquareDailyStudyTime = member.getHanaSquareRecord().getDailyStudyTime();
        Long memorialHallDailyStudyTime = member.getMemorialHallRecord().getDailyStudyTime();
        Long scienceLibraryDailyStudyTime = member.getScienceLibraryRecord().getDailyStudyTime();

        return StudyTimeDto.of(dailyTotalStudyTime, centralLibraryDailyStudyTime,
                            centralSquareDailyStudyTime, educationHallDailyStudyTime,
                            hanaSquareDailyStudyTime, memorialHallDailyStudyTime,
                            scienceLibraryDailyStudyTime);
    }

    private StudyTimeDto getStudyTimeHistory(Member member, LocalDate studyDate) {
        Long dailyTotalStudyTime = findDailyTotalStudyTimeByIdAndStudyDate(member.getId(), studyDate);
        Long centralLibraryDailyStudyTime = findCentralLibraryDailyStudyTimeByIdAndStudyDate(member.getId(), studyDate);
        Long centralSquareDailyStudyTime = findCentralSquareDailyStudyTimeByIdAndStudyDate(member.getId(), studyDate);
        Long educationHallDailyStudyTime = findEducationHallDailyStudyTimeByIdAndStudyDate(member.getId(), studyDate);
        Long hanaSquareDailyStudyTime = findHanaSquareDailyStudyTimeByIdAndStudyDate(member.getId(), studyDate);
        Long memorialHallDailyStudyTime = findMemorialHallDailyStudyTimeByIdAndStudyDate(member.getId(), studyDate);
        Long scienceLibraryDailyStudyTime = findScienceLibraryDailyStudyTimeByIdAndStudyDate(member.getId(), studyDate);

        return StudyTimeDto.of(dailyTotalStudyTime, centralLibraryDailyStudyTime,
                centralSquareDailyStudyTime, educationHallDailyStudyTime,
                hanaSquareDailyStudyTime, memorialHallDailyStudyTime,
                scienceLibraryDailyStudyTime);
    }

    private Long findDailyTotalStudyTimeByIdAndStudyDate(Long id, LocalDate studyDate) {
        MemberStudyTimeHistory history = memberStudyTimeHistoryRepository.findByMemberIdAndStudyDate(id, studyDate)
                .orElseThrow(() -> new CustomException(ErrorCode.INVALID_DATE));

        return history.getMemberDailyStudyTime();
    }

    private Long findCentralLibraryDailyStudyTimeByIdAndStudyDate(Long id, LocalDate studyDate) {
        CentralLibraryRecordHistory history = centralLibraryRecordHistoryRepository.findByMemberIdAndStudyDate(id, studyDate)
                .orElseThrow(() -> new CustomException(ErrorCode.INVALID_DATE));

        return history.getStudyTime();
    }

    private Long findCentralSquareDailyStudyTimeByIdAndStudyDate(Long id, LocalDate studyDate) {
        CentralSquareRecordHistory history = centralSquareRecordHistoryRepository.findByMemberIdAndStudyDate(id, studyDate)
                .orElseThrow(() -> new CustomException(ErrorCode.INVALID_DATE));

        return history.getStudyTime();
    }

    private Long findEducationHallDailyStudyTimeByIdAndStudyDate(Long id, LocalDate studyDate) {
        EducationHallRecordHistory history = educationHallRecordHistoryRepository.findByMemberIdAndStudyDate(id, studyDate)
                .orElseThrow(() -> new CustomException(ErrorCode.INVALID_DATE));

        return history.getStudyTime();
    }

    private Long findHanaSquareDailyStudyTimeByIdAndStudyDate(Long id, LocalDate studyDate) {
        HanaSquareRecordHistory history = hanaSquareRecordHistoryRepository.findByMemberIdAndStudyDate(id, studyDate)
                .orElseThrow(() -> new CustomException(ErrorCode.INVALID_DATE));

        return history.getStudyTime();
    }

    private Long findMemorialHallDailyStudyTimeByIdAndStudyDate(Long id, LocalDate studyDate) {
        MemorialHallRecordHistory history = memorialHallRecordHistoryRepository.findByMemberIdAndStudyDate(id, studyDate)
                .orElseThrow(() -> new CustomException(ErrorCode.INVALID_DATE));

        return history.getStudyTime();
    }

    private Long findScienceLibraryDailyStudyTimeByIdAndStudyDate(Long id, LocalDate studyDate) {
        ScienceLibraryRecordHistory history = scienceLibraryRecordHistoryRepository.findByMemberIdAndStudyDate(id, studyDate)
                .orElseThrow(() -> new CustomException(ErrorCode.INVALID_DATE));

        return history.getStudyTime();
    }

}
