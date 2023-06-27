package koreaUniv.koreaUnivRankSys.domain.building.repository.ranking;

import koreaUniv.koreaUnivRankSys.domain.building.dto.MyRankingResponse;
import koreaUniv.koreaUnivRankSys.domain.building.dto.RankingDto;
import koreaUniv.koreaUnivRankSys.domain.building.dto.mapper.DailyRankingDtoRowMapper;
import koreaUniv.koreaUnivRankSys.domain.building.dto.mapper.TotalMyRankingResultMapper;
import koreaUniv.koreaUnivRankSys.domain.building.dto.mapper.TotalRankingDtoRowMapper;
import koreaUniv.koreaUnivRankSys.domain.building.dto.mapper.WeeklyRankingDtoRowMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CentralLibraryRankingQueryRepository {

    private final JdbcTemplate jdbcTemplate;

    // 해당 쿼리들도 View 로 만들어서 select 연산만 하는 것이 더 빠를까?
    // 아니면 rank() 함수가 있기 때문에 view 로 만들 수 없나?

    public List<RankingDto> findRankingsByTotalStudyTime() {
        return jdbcTemplate.query("select path, nick_name, total_study_time, " +
                "row_number() over (order by total_study_time desc) as \'ranking\' " +
                "from (member natural left outer join member_image) natural join central_library_record",
                new TotalRankingDtoRowMapper());
    }

    public List<RankingDto> findRankingsByDailyStudyTime() {
        return jdbcTemplate.query("select path, nick_name, daily_study_time, " +
                        "row_number() over (order by daily_study_time desc) as \'ranking\' " +
                        "from (member natural left outer join member_image) natural join central_library_record",
                new DailyRankingDtoRowMapper());

    }

    // Member 와 Building Entity 모두 BaseEntity 를 상속받았기에, 이름이 같은 컬럼 2개가 추가 되어 natural join 이 정상적으로 동작하지 않았다.
    // 그로 인해, natural join 이 아닌 join where 로 변경 불가피
    public List<RankingDto> findRankingsByWeeklyStudyTime() {
        return jdbcTemplate.query("select path, nick_name, weekly_study_time, row_number() over (order by weekly_study_time desc) as 'ranking' " +
                        "from (member natural left outer join member_image) join central_library_record " +
                        "where member.central_library_record_id = central_library_record.central_library_record_id",
                new WeeklyRankingDtoRowMapper());

    }

    public List<RankingDto> findRankingsByMonthlyStudyTime() {
        return jdbcTemplate.query("select path, nick_name, monthly_study_time, " +
                        "row_number() over (order by monthly_study_time desc) as \'ranking\' " +
                        "from (member natural left outer join member_image) natural join central_library_record",
                new WeeklyRankingDtoRowMapper());
    }

    public Optional<MyRankingResponse> findMyRankingByTotalStudyTime(String nickName) {
        return jdbcTemplate.query("select nick_name, total_study_time, ranking, prev_ranking, next_ranking " +
                        "from (select nick_name, total_study_time, " +
                        "row_number() over (order by total_study_time desc) as 'ranking', " +
                        "LAG(total_study_time, 1) over (order by total_study_time desc) prev_ranking, " +
                        "LEAD(total_study_time, 1) over (order by total_study_time desc) next_ranking " +
                        "from member natural join central_library_record) as t " +
                        "where nick_name=?", new TotalMyRankingResultMapper(), nickName)
                .stream().findAny();
    }

}
