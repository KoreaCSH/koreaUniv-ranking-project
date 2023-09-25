package koreaUniv.koreaUnivRankSys.batch.domain.building.top3.mapper;

import koreaUniv.koreaUnivRankSys.batch.domain.building.top3.HanaSquareTop3;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class HanaSquareTop3RowMapper implements RowMapper<HanaSquareTop3> {

    @Override
    public HanaSquareTop3 mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new HanaSquareTop3(
                rs.getString("nick_name"),
                rs.getString("path"),
                rs.getLong("ranking"),
                rs.getLong("weekly_study_time")
        );
    }
}
