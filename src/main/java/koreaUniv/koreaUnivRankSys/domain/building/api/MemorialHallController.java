package koreaUniv.koreaUnivRankSys.domain.building.api;

import koreaUniv.koreaUnivRankSys.domain.auth.service.AuthMember;
import koreaUniv.koreaUnivRankSys.domain.building.dto.RankingDto;
import koreaUniv.koreaUnivRankSys.domain.building.dto.RankingResultDto;
import koreaUniv.koreaUnivRankSys.domain.building.dto.StudyTimeDto;
import koreaUniv.koreaUnivRankSys.domain.building.service.MemorialHallRecordService;
import koreaUniv.koreaUnivRankSys.domain.member.domain.Member;
import koreaUniv.koreaUnivRankSys.global.common.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/memorial-hall")
@RequiredArgsConstructor
public class MemorialHallController {

    private final MemorialHallRecordService memorialHallRecordService;

    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<CommonResponse> trackStudyTime(@AuthMember Member member, @RequestBody StudyTimeDto studyTimeDto) {

        memorialHallRecordService.trackStudyTime(member.getUserId(), studyTimeDto.getStudyTime());

        return ResponseEntity.ok().body(new CommonResponse(String.valueOf(HttpStatus.CREATED.value()),
                studyTimeDto.getStudyTime() + "분이 기록되었습니다."));
    }

    @GetMapping("/total-rankings")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<RankingResultDto> getRankings() {

        List<RankingDto> rankings = memorialHallRecordService.findAllByRanking();

        return ResponseEntity.ok().body(RankingResultDto.of(rankings));
    }

}
