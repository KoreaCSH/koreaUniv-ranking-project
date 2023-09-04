package koreaUniv.koreaUnivRankSys.batch.domain.building.top3.mapper;

import koreaUniv.koreaUnivRankSys.batch.domain.building.top3.CentralLibraryTop3;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CentralLibraryTop3RowMapper implements RowMapper<CentralLibraryTop3> {

    @Override
    public CentralLibraryTop3 mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new CentralLibraryTop3(
                    rs.getString("nick_name"),
                    rs.getString("path"),
                    rs.getLong("ranking"),
                    rs.getLong("weekly_study_time"));
    }
}
