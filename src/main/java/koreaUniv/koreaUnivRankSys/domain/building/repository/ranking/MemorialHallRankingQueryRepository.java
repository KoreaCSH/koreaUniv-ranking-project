package koreaUniv.koreaUnivRankSys.domain.building.repository.ranking;

import koreaUniv.koreaUnivRankSys.domain.building.api.dto.RankingDto;
import koreaUniv.koreaUnivRankSys.domain.building.api.dto.RankingDtoRowMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemorialHallRankingQueryRepository {

    private final JdbcTemplate jdbcTemplate;

    public List<RankingDto> findAllByRanking() {
        return jdbcTemplate.query("select path, nick_name, total_studying_time, " +
                        "rank() over (order by total_studying_time desc) as \'ranking\' " +
                        "from (member natural left outer join member_image) natural join memorial_hall_record",
                new RankingDtoRowMapper());
        }

}
