package koreaUniv.koreaUnivRankSys.batch.domain.building.top3.mapper;

import koreaUniv.koreaUnivRankSys.batch.domain.building.top3.CentralSquareTop3;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CentralSquareTop3RowMapper implements RowMapper<CentralSquareTop3> {

    @Override
    public CentralSquareTop3 mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new CentralSquareTop3(
                rs.getString("nick_name"),
                rs.getString("path"),
                rs.getLong("ranking"),
                rs.getLong("weekly_study_time"));
    }

}
