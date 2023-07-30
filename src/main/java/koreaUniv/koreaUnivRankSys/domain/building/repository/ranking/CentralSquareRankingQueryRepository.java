package koreaUniv.koreaUnivRankSys.domain.building.repository.ranking;

import koreaUniv.koreaUnivRankSys.web.building.dto.MyRankingResponse;
import koreaUniv.koreaUnivRankSys.web.building.dto.RankingDto;
import koreaUniv.koreaUnivRankSys.web.building.dto.mapper.*;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CentralSquareRankingQueryRepository {

    private final JdbcTemplate jdbcTemplate;

    public List<RankingDto> findRankingsByTotalStudyTime() {
        return jdbcTemplate.query("select member_id, path, nick_name, total_study_time, " +
                        "row_number() over (order by total_study_time desc) as \'ranking\' " +
                        "from (member natural left outer join member_image) join central_square_record " +
                        "where member.central_square_record_id = central_square_record.central_square_record_id",
                new TotalRankingDtoRowMapper());

    }

    public List<RankingDto> findRankingsByDailyStudyTime() {
        return jdbcTemplate.query("select member_id, path, nick_name, daily_study_time, " +
                        "row_number() over (order by daily_study_time desc) as \'ranking\' " +
                        "from (member natural left outer join member_image) join central_square_record " +
                        "where member.central_square_record_id = central_square_record.central_square_record_id",
                new DailyRankingDtoRowMapper());

    }

    public List<RankingDto> findRankingsByWeeklyStudyTime() {
        return jdbcTemplate.query("select member_id, path, nick_name, weekly_study_time, " +
                        "row_number() over (order by weekly_study_time desc) as \'ranking\' " +
                        "from (member natural left outer join member_image) join central_square_record " +
                        "where member.central_square_record_id = central_square_record.central_square_record_id",
                new WeeklyRankingDtoRowMapper());

    }

    public List<RankingDto> findRankingsByMonthlyStudyTime() {
        return jdbcTemplate.query("select member_id, path, nick_name, monthly_study_time, " +
                        "row_number() over (order by monthly_study_time desc) as \'ranking\' " +
                        "from (member natural left outer join member_image) join central_square_record " +
                        "where member.central_square_record_id = central_square_record.central_square_record_id",
                new MonthlyRankingDtoRowMapper());
    }

    public Optional<MyRankingResponse> findMyRankingByTotalStudyTime(String nickName) {
        return jdbcTemplate.query("select nick_name, total_study_time, ranking, prev_ranking, next_ranking " +
                        "from (select nick_name, total_study_time, " +
                        "row_number() over (order by total_study_time desc) as 'ranking', " +
                        "LAG(total_study_time, 1) over (order by total_study_time desc) prev_ranking, " +
                        "LEAD(total_study_time, 1) over (order by total_study_time desc) next_ranking " +
                        "from member join central_square_record where member.central_square_record_id = central_square_record.central_square_record_id) as t " +
                        "where nick_name=?", new TotalMyRankingResultMapper(), nickName)
                .stream().findAny();
    }

}
