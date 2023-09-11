package koreaUniv.koreaUnivRankSys.web.member.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class StudyTimeDto {

    private Long dailyTotalStudyTime;
    private Long centralLibraryDailyStudyTime;
    private Long centralSquareDailyStudyTime;
    private Long educationHallDailyStudyTime;
    private Long hanaSquareDailyStudyTime;
    private Long memorialHallDailyStudyTime;
    private Long scienceLibraryDailyStudyTime;

    public static StudyTimeDto of(Long dailyTotalStudyTime,
                                  Long centralLibraryDailyStudyTime,
                                  Long centralSquareDailyStudyTime,
                                  Long educationHallDailyStudyTime,
                                  Long hanaSquareDailyStudyTime,
                                  Long memorialHallDailyStudyTime,
                                  Long scienceLibraryDailyStudyTime) {

        StudyTimeDto studyTimeDto = new StudyTimeDto();
        studyTimeDto.dailyTotalStudyTime = dailyTotalStudyTime;
        studyTimeDto.centralLibraryDailyStudyTime = centralLibraryDailyStudyTime;
        studyTimeDto.centralSquareDailyStudyTime = centralSquareDailyStudyTime;
        studyTimeDto.educationHallDailyStudyTime = educationHallDailyStudyTime;
        studyTimeDto.hanaSquareDailyStudyTime = hanaSquareDailyStudyTime;
        studyTimeDto.memorialHallDailyStudyTime = memorialHallDailyStudyTime;
        studyTimeDto.scienceLibraryDailyStudyTime = scienceLibraryDailyStudyTime;
        return studyTimeDto;
    }

}
