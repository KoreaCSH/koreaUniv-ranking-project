package koreaUniv.koreaUnivRankSys.batch.domain.building.top3.mapper;

import koreaUniv.koreaUnivRankSys.batch.domain.building.top3.ScienceLibraryTop3;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ScienceLibraryTop3RowMapper implements RowMapper<ScienceLibraryTop3> {

    @Override
    public ScienceLibraryTop3 mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new ScienceLibraryTop3(
                rs.getString("nick_name"),
                rs.getString("path"),
                rs.getLong("ranking"),
                rs.getLong("weekly_study_time")
        );
    }

}
