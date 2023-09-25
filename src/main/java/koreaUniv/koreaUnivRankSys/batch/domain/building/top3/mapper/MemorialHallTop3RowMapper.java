package koreaUniv.koreaUnivRankSys.batch.domain.building.top3.mapper;

import koreaUniv.koreaUnivRankSys.batch.domain.building.top3.MemorialHallTop3;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MemorialHallTop3RowMapper implements RowMapper<MemorialHallTop3> {

    @Override
    public MemorialHallTop3 mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new MemorialHallTop3(
                rs.getString("nick_name"),
                rs.getString("path"),
                rs.getLong("ranking"),
                rs.getLong("weekly_study_time")
        );
    }

}
