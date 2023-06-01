package koreaUniv.koreaUnivRankSys.domain.building.repository.ranking;

import koreaUniv.koreaUnivRankSys.domain.building.dto.RankingDto;
import koreaUniv.koreaUnivRankSys.domain.building.dto.mapper.TotalRankingDtoRowMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CentralLibraryRankingQueryRepository {

    private final JdbcTemplate jdbcTemplate;

    public List<RankingDto> findAllByRanking() {
        return jdbcTemplate.query("select path, nick_name, total_study_time, " +
                "rank() over (order by total_study_time desc) as \'ranking\' " +
                "from (member natural left outer join member_image) natural join central_library_record",
                new TotalRankingDtoRowMapper());
    }

}
