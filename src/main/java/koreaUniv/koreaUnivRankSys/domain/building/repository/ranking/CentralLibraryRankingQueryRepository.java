package koreaUniv.koreaUnivRankSys.domain.building.repository.ranking;

import koreaUniv.koreaUnivRankSys.domain.building.api.dto.CentralLibraryRankingDto;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CentralLibraryRankingQueryRepository {

    private final JdbcTemplate jdbcTemplate;

    public List<CentralLibraryRankingDto> findAllByRanking() {
        return jdbcTemplate.query("select path, nick_name, total_studying_time, " +
                "rank() over (order by total_studying_time desc) as \'ranking\' " +
                "from (member natural left outer join member_image) natural join central_library_record",
                centralLibraryRankingDtoRowMapper());
    }

    private RowMapper<CentralLibraryRankingDto> centralLibraryRankingDtoRowMapper() {
        return (rs, rowNum) -> {
            CentralLibraryRankingDto centralLibraryRankingDto = new CentralLibraryRankingDto();
            centralLibraryRankingDto.setNickName(rs.getString("nick_name"));
            centralLibraryRankingDto.setPath(rs.getString("path"));
            centralLibraryRankingDto.setTotalStudyingTime(rs.getLong("total_studying_time"));
            centralLibraryRankingDto.setRanking(rs.getLong("ranking"));
            return centralLibraryRankingDto;
        };
    }

}
