package koreaUniv.koreaUnivRankSys.web.building.dto.mapper;

import koreaUniv.koreaUnivRankSys.web.building.dto.RankingDto;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RankingDtoRowMapper implements RowMapper<RankingDto> {

    private String criteria;

    public RankingDtoRowMapper(String criteria) {
        this.criteria = criteria;
    }

    @Override
    public RankingDto mapRow(ResultSet rs, int rowNum) throws SQLException {
        return RankingDto.builder()
                .memberId(rs.getLong("member_id"))
                .nickName(rs.getString("nick_name"))
                .imageUrl(rs.getString("path"))
                .studyTime(rs.getLong(this.criteria))
                .ranking(rs.getLong("ranking"))
                .build();
    }
}
