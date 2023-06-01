package koreaUniv.koreaUnivRankSys.domain.building.dto.mapper;

import koreaUniv.koreaUnivRankSys.domain.building.dto.MyRankingResult;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TotalMyRankingResultMapper implements RowMapper<MyRankingResult> {

    @Override
    public MyRankingResult mapRow(ResultSet rs, int rowNum) throws SQLException {
        return MyRankingResult.builder()
                .nickName(rs.getString("nick_name"))
                .ranking(rs.getLong("ranking"))
                .studyTime(rs.getLong("total_study_time"))
                .prevRanking(rs.getLong("prev_ranking"))
                .nextRanking(rs.getLong("next_ranking"))
                .build();
    }

}
