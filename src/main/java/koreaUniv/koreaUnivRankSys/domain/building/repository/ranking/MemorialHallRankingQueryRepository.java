package koreaUniv.koreaUnivRankSys.domain.building.repository.ranking;

import koreaUniv.koreaUnivRankSys.domain.building.dto.mapper.DailyRankingDtoRowMapper;
import koreaUniv.koreaUnivRankSys.domain.building.dto.RankingDto;
import koreaUniv.koreaUnivRankSys.domain.building.dto.mapper.TotalRankingDtoRowMapper;
import koreaUniv.koreaUnivRankSys.domain.building.dto.mapper.WeeklyRankingDtoRowMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemorialHallRankingQueryRepository {

    private final JdbcTemplate jdbcTemplate;

    public List<RankingDto> findRankingsByTotalStudyTime() {
        return jdbcTemplate.query("select path, nick_name, total_study_time, " +
                        "rank() over (order by total_study_time desc) as \'ranking\' " +
                        "from (member natural left outer join member_image) natural join memorial_hall_record",
                new TotalRankingDtoRowMapper());

    }

    public List<RankingDto> findRankingsByDailyStudyTime() {
        return jdbcTemplate.query("select path, nick_name, daily_study_time, " +
                        "rank() over (order by daily_study_time desc) as \'ranking\' " +
                        "from (member natural left outer join member_image) natural join memorial_hall_record",
                new DailyRankingDtoRowMapper());

    }

    public List<RankingDto> findRankingsByWeeklyStudyTime() {
        return jdbcTemplate.query("select path, nick_name, weekly_study_time, " +
                        "rank() over (order by weekly_study_time desc) as \'ranking\' " +
                        "from (member natural left outer join member_image) natural join memorial_hall_record",
                new WeeklyRankingDtoRowMapper());

    }

}
