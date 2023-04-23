package koreaUniv.koreaUnivRankSys.domain.building.api.dto;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RankingDtoRowMapper implements RowMapper<RankingDto> {

    @Override
    public RankingDto mapRow(ResultSet rs, int rowNum) throws SQLException {
        RankingDto rankingDto = new RankingDto();
        rankingDto.setNickName(rs.getString("nick_name"));
        rankingDto.setPath(rs.getString("path"));
        rankingDto.setTotalStudyingTime(rs.getLong("total_studying_time"));
        rankingDto.setRanking(rs.getLong("ranking"));
        return rankingDto;
    }
}
