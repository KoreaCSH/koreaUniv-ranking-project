package koreaUniv.koreaUnivRankSys.web.member.dto;

import io.swagger.v3.oas.annotations.media.Schema;
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

        @Schema(description = "total 일간 공부 시간", type = "long")
        private Long dailyTotalStudyTime;

        @Schema(description = "중도 일간 공부 시간", type = "long")
        private Long centralLibraryDailyStudyTime;

        @Schema(description = "중광 일간 공부 시간", type = "long")
        private Long centralSquareDailyStudyTime;

        @Schema(description = "교육관 일간 공부 시간", type = "long")
        private Long educationHallDailyStudyTime;

        @Schema(description = "하스 일간 공부 시간", type = "long")
        private Long hanaSquareDailyStudyTime;

        @Schema(description = "백기 일간 공부 시간", type = "long")
        private Long memorialHallDailyStudyTime;

        @Schema(description = "과도 일간 공부 시간", type = "long")
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
