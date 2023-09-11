package koreaUniv.koreaUnivRankSys.web.member.dto;

import lombok.*;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PlannerResponse {

    private StudyTimeResponse studyTimeResponse;

    @Builder
    public PlannerResponse(StudyTimeDto studyTimeDto) {
        this.studyTimeResponse = new StudyTimeResponse(studyTimeDto);
    }

    @Getter
    @NoArgsConstructor
    public static class StudyTimeResponse {

        private Long dailyTotalStudyTime;
        private Long centralLibraryDailyStudyTime;
        private Long centralSquareDailyStudyTime;
        private Long educationHallDailyStudyTime;
        private Long hanaSquareDailyStudyTime;
        private Long memorialHallDailyStudyTime;
        private Long scienceLibraryDailyStudyTime;

        public StudyTimeResponse(StudyTimeDto studyTimeDto) {
            dailyTotalStudyTime = studyTimeDto.getDailyTotalStudyTime();
            centralLibraryDailyStudyTime = studyTimeDto.getCentralLibraryDailyStudyTime();
            centralSquareDailyStudyTime = studyTimeDto.getCentralSquareDailyStudyTime();
            educationHallDailyStudyTime = studyTimeDto.getEducationHallDailyStudyTime();
            hanaSquareDailyStudyTime = studyTimeDto.getHanaSquareDailyStudyTime();
            memorialHallDailyStudyTime = studyTimeDto.getMemorialHallDailyStudyTime();
            scienceLibraryDailyStudyTime = studyTimeDto.getScienceLibraryDailyStudyTime();
        }

    }

}
