package koreaUniv.koreaUnivRankSys.domain.building.dto.mapper;

import koreaUniv.koreaUnivRankSys.domain.building.dto.RankingDto;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DailyRankingDtoRowMapper implements RowMapper<RankingDto> {

    @Override
    public RankingDto mapRow(ResultSet rs, int rowNum) throws SQLException {

        return RankingDto.builder()
                .nickName(rs.getString("nick_name"))
                .path(rs.getString("path"))
                .studyTime(rs.getLong("daily_study_time"))
                .ranking(rs.getLong("ranking"))
                .build();
    }

}
