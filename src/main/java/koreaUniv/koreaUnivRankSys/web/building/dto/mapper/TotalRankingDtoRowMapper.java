package koreaUniv.koreaUnivRankSys.web.building.dto.mapper;

import koreaUniv.koreaUnivRankSys.web.building.dto.RankingDto;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TotalRankingDtoRowMapper implements RowMapper<RankingDto> {

    @Override
    public RankingDto mapRow(ResultSet rs, int rowNum) throws SQLException {

        return RankingDto.builder()
                .memberId(rs.getLong("member_id"))
                .nickName(rs.getString("nick_name"))
                .imageUrl(rs.getString("path"))
                .studyTime(rs.getLong("total_study_time"))
                .ranking(rs.getLong("ranking"))
                .build();
    }
}
