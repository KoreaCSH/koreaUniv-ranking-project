package koreaUniv.koreaUnivRankSys.web.building.dto.mapper;

import koreaUniv.koreaUnivRankSys.web.building.dto.RankingDto;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DailyRankingDtoRowMapper implements RowMapper<RankingDto> {

    @Override
    public RankingDto mapRow(ResultSet rs, int rowNum) throws SQLException {

        return RankingDto.builder()
                .memberId(rs.getLong("member_id"))
                .nickName(rs.getString("nick_name"))
                .imageUrl(rs.getString("path"))
                .studyTime(rs.getLong("daily_study_time"))
                .ranking(rs.getLong("ranking"))
                .build();
    }

}
