package koreaUniv.koreaUnivRankSys.domain.building.dto.mapper;

import koreaUniv.koreaUnivRankSys.domain.building.dto.RankingDto;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class WeeklyRankingDtoRowMapper implements RowMapper<RankingDto> {

    @Override
    public RankingDto mapRow(ResultSet rs, int rowNum) throws SQLException {

        return RankingDto.builder()
                .memberId(rs.getLong("member_id"))
                .nickName(rs.getString("nick_name"))
                .imageUrl(rs.getString("path"))
                .studyTime(rs.getLong("weekly_study_time"))
                .ranking(rs.getLong("ranking"))
                .build();
    }

}
