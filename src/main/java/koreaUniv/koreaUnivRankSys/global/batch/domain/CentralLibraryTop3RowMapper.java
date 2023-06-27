package koreaUniv.koreaUnivRankSys.global.batch.domain;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CentralLibraryTop3RowMapper implements RowMapper<CentralLibraryTop3> {

    @Override
    public CentralLibraryTop3 mapRow(ResultSet rs, int rowNum) throws SQLException {
        return CentralLibraryTop3.builder()
                .nickName(rs.getString("nick_name"))
                .studyTime(rs.getLong("weekly_study_time"))
                .imageUrl(rs.getString("path"))
                .ranking(rs.getLong("ranking"))
                .build();
    }
}
