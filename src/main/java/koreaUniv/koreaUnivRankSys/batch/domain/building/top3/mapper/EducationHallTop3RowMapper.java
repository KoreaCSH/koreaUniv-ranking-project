package koreaUniv.koreaUnivRankSys.batch.domain.building.top3.mapper;

import koreaUniv.koreaUnivRankSys.batch.domain.building.top3.CentralSquareTop3;
import koreaUniv.koreaUnivRankSys.batch.domain.building.top3.EducationHallTop3;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class EducationHallTop3RowMapper implements RowMapper<EducationHallTop3> {

    @Override
    public EducationHallTop3 mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new EducationHallTop3(
                rs.getString("nick_name"),
                rs.getString("path"),
                rs.getLong("ranking"),
                rs.getLong("weekly_study_time"));
    }

}
