package koreaUniv.koreaUnivRankSys.web.building.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum StudyTimeCriteria {

    TOTAL_STUDY_TIME("total_study_time"),
    DAILY_STUDY_TIME("daily_study_time"),
    WEEKLY_STUDY_TIME("weekly_study_time"),
    MONTHLY_STUDY_TIME("monthly_study_time");

    private String criteria;

}
