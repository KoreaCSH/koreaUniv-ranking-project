package koreaUniv.koreaUnivRankSys.domain.building.api;

import koreaUniv.koreaUnivRankSys.domain.auth.service.AuthMember;
import koreaUniv.koreaUnivRankSys.domain.building.dto.MyRankingResult;
import koreaUniv.koreaUnivRankSys.domain.building.dto.RankingDto;
import koreaUniv.koreaUnivRankSys.domain.building.dto.RankingResult;
import koreaUniv.koreaUnivRankSys.domain.building.dto.StudyTimeRequest;
import koreaUniv.koreaUnivRankSys.domain.building.service.CentralSquareRecordService;
import koreaUniv.koreaUnivRankSys.domain.member.domain.Member;
import koreaUniv.koreaUnivRankSys.global.common.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/central-square")
@RequiredArgsConstructor
public class CentralSquareController {

    private final CentralSquareRecordService centralSquareRecordService;

    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<CommonResponse> trackStudyTime(@AuthMember Member member, @Valid @RequestBody StudyTimeRequest studyTimeRequest) {

        centralSquareRecordService.trackStudyTime(member, studyTimeRequest.getStudyTime());

        return ResponseEntity.ok().body(new CommonResponse(String.valueOf(HttpStatus.CREATED.value()),
                studyTimeRequest.getStudyTime() + "분이 기록되었습니다."));
    }

    @GetMapping("/total-rankings")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<RankingResult> getTotalRankings() {

        List<RankingDto> rankings = centralSquareRecordService.findTotalRankings();

        return ResponseEntity.ok().body(RankingResult.of(rankings));
    }

    @GetMapping("/daily-rankings")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<RankingResult> getDailyRankings() {

        List<RankingDto> rankings = centralSquareRecordService.findDailyRankings();

        return ResponseEntity.ok().body(RankingResult.of(rankings));
    }

    @GetMapping("/weekly-rankings")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<RankingResult> getWeeklyRankings() {

        List<RankingDto> rankings = centralSquareRecordService.findWeeklyRankings();

        return ResponseEntity.ok().body(RankingResult.of(rankings));
    }

    @GetMapping("/my-total-ranking")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<MyRankingResult> getMyTotalRanking(@AuthMember Member member) {

        return ResponseEntity.ok().body(centralSquareRecordService.findMyRankingByTotalStudyTime(member.getNickName()));
    }

}
