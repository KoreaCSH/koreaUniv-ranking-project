package koreaUniv.koreaUnivRankSys.domain.building.dto.mapper;

import koreaUniv.koreaUnivRankSys.domain.building.dto.MyRankingResponse;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TotalMyRankingResultMapper implements RowMapper<MyRankingResponse> {

    @Override
    public MyRankingResponse mapRow(ResultSet rs, int rowNum) throws SQLException {
        return MyRankingResponse.builder()
                .nickName(rs.getString("nick_name"))
                .ranking(rs.getLong("ranking"))
                .studyTime(rs.getLong("total_study_time"))
                .prevRanking(rs.getLong("prev_ranking"))
                .nextRanking(rs.getLong("next_ranking"))
                .build();
    }

}
