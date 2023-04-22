package koreaUniv.koreaUnivRankSys.domain.building.repository.ranking;

import koreaUniv.koreaUnivRankSys.domain.building.api.dto.MemorialHallRankingDto;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemorialHallRankingQueryRepository {

    private final JdbcTemplate jdbcTemplate;

    public List<MemorialHallRankingDto> findAllByRanking() {
        return jdbcTemplate.query("select path, nick_name, total_studying_time, " +
                        "rank() over (order by total_studying_time desc) as \'ranking\' " +
                        "from (member natural left outer join member_image) natural join memorial_hall_record",
                memorialHallRankingDtoRowMapper());
        }

    private RowMapper<MemorialHallRankingDto> memorialHallRankingDtoRowMapper() {
        return (rs, rowNum) -> {
            MemorialHallRankingDto memorialHallRankingDto = new MemorialHallRankingDto();
            memorialHallRankingDto.setNickName(rs.getString("nick_name"));
            memorialHallRankingDto.setPath(rs.getString("path"));
            memorialHallRankingDto.setTotalStudyingTime(rs.getLong("total_studying_time"));
            memorialHallRankingDto.setRanking(rs.getLong("ranking"));
            return memorialHallRankingDto;
            };

    }

}
