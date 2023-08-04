package koreaUniv.koreaUnivRankSys.domain.building.repository.ranking;

import koreaUniv.koreaUnivRankSys.web.building.dto.MyRankingResponse;
import koreaUniv.koreaUnivRankSys.web.building.dto.RankingDto;
import koreaUniv.koreaUnivRankSys.web.building.dto.StudyTimeCriteria;
import koreaUniv.koreaUnivRankSys.web.building.dto.mapper.*;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class CentralLibraryRankingQueryRepository {

    private final JdbcTemplate jdbcTemplate;

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    // 해당 쿼리들도 View 로 만들어서 select 연산만 하는 것이 더 빠를까?
    // 아니면 rank() 함수가 있기 때문에 view 로 만들 수 없나?

    // 사실 중복되는 코드가 대부분이다.
    // total 인지, daily 인지, week 인지를 매개변수로 넘겨 동적쿼리로 작동하게 코드를 작성할 순 없을까?

    // select 할 컬럼과 order by 에 조건을 동적으로 걸어야 했기에, NamedParameterJdbcTemplate 을 사용할 수 없었다.
    // 아래의 방법은 SQL injection
    public List<RankingDto> findRankings(StudyTimeCriteria criteria) {

        String sql = String.format("select member_id, path, nick_name, %s, " +
                "row_number() over (order by %s desc) as \'ranking\' " +
                "from (member natural left outer join member_image) join central_library_record " +
                "where member.central_library_record_id = central_library_record.central_library_record_id",
                criteria.getCriteria(), criteria.getCriteria());

        return jdbcTemplate.query(sql, new RankingDtoRowMapper(criteria.getCriteria()));
    }

    public List<RankingDto> findRankingsByTotalStudyTime() {
        return jdbcTemplate.query("select member_id, path, nick_name, total_study_time, " +
                "row_number() over (order by total_study_time desc) as \'ranking\' " +
                "from (member natural left outer join member_image) join central_library_record " +
                "where member.central_library_record_id = central_library_record.central_library_record_id",
                new TotalRankingDtoRowMapper());
    }

    public List<RankingDto> findRankingsByDailyStudyTime() {
        return jdbcTemplate.query("select member_id, path, nick_name, daily_study_time, " +
                        "row_number() over (order by daily_study_time desc) as \'ranking\' " +
                        "from (member natural left outer join member_image) join central_library_record " +
                        "where member.central_library_record_id = central_library_record.central_library_record_id",
                new DailyRankingDtoRowMapper());

    }

    // Member 와 Building Entity 모두 BaseEntity 를 상속받았기에, 이름이 같은 컬럼 2개가 추가 되어 natural join 이 정상적으로 동작하지 않았다.
    // 그로 인해, natural join 이 아닌 join where 로 변경 불가피
    public List<RankingDto> findRankingsByWeeklyStudyTime() {
        return jdbcTemplate.query("select member_id, path, nick_name, weekly_study_time, row_number() over (order by weekly_study_time desc) as 'ranking' " +
                        "from (member natural left outer join member_image) join central_library_record " +
                        "where member.central_library_record_id = central_library_record.central_library_record_id",
                new WeeklyRankingDtoRowMapper());

    }

    public List<RankingDto> findRankingsByMonthlyStudyTime() {
        return jdbcTemplate.query("select member_id, path, nick_name, monthly_study_time, " +
                        "row_number() over (order by monthly_study_time desc) as \'ranking\' " +
                        "from (member natural left outer join member_image) join central_library_record " +
                        "where member.central_library_record_id = central_library_record.central_library_record_id",
                new MonthlyRankingDtoRowMapper());
    }

    public Optional<MyRankingResponse> findMyRankingByTotalStudyTime(String nickName) {
        return jdbcTemplate.query("select nick_name, total_study_time, ranking, prev_ranking, next_ranking " +
                        "from (select nick_name, total_study_time, " +
                        "row_number() over (order by total_study_time desc) as 'ranking', " +
                        "LAG(total_study_time, 1) over (order by total_study_time desc) prev_ranking, " +
                        "LEAD(total_study_time, 1) over (order by total_study_time desc) next_ranking " +
                        "from member join central_library_record where member.central_library_record_id = central_library_record.central_library_record_id) as t " +
                        "where nick_name=?", new TotalMyRankingResultMapper(), nickName)
                .stream().findAny();
    }

}
