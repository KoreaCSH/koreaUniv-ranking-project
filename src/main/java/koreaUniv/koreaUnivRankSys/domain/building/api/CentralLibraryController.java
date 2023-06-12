package koreaUniv.koreaUnivRankSys.domain.building.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import koreaUniv.koreaUnivRankSys.domain.auth.service.AuthMember;
import koreaUniv.koreaUnivRankSys.domain.building.dto.MyRankingResponse;
import koreaUniv.koreaUnivRankSys.domain.building.dto.RankingDto;
import koreaUniv.koreaUnivRankSys.domain.building.dto.RankingsResponse;
import koreaUniv.koreaUnivRankSys.domain.building.dto.StudyTimeRequest;
import koreaUniv.koreaUnivRankSys.domain.building.service.CentralLibraryRecordService;
import koreaUniv.koreaUnivRankSys.domain.member.domain.Member;
import koreaUniv.koreaUnivRankSys.global.common.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Tag(name = "중앙 도서관 API", description = "CentralLibraryController")
@RestController
@RequestMapping("/api/central-library")
@RequiredArgsConstructor
public class CentralLibraryController {

    private final CentralLibraryRecordService centralLibraryRecordService;

    @Operation(summary = "공부 기록 측정", description = "trackStudyTime")
    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<CommonResponse> trackStudyTime(@AuthMember Member member, @Valid @RequestBody StudyTimeRequest studyTimeRequest) {

        centralLibraryRecordService.trackStudyTime(member.getUserId(), studyTimeRequest.getStudyTime());

        return ResponseEntity.ok().body(new CommonResponse(String.valueOf(HttpStatus.CREATED.value()),
                studyTimeRequest.getStudyTime() + "분이 기록되었습니다."));
    }

    @Operation(summary = "total 공부 기록 순위", description = "getTotalRankings")
    @GetMapping("/total-rankings")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<RankingsResponse> getTotalRankings() {

        List<RankingDto> rankings = centralLibraryRecordService.findTotalRankings();

        return ResponseEntity.ok().body(RankingsResponse.of(rankings));
    }

    @Operation(summary = "daily 공부 기록 순위", description = "getDailyRankings")
    @GetMapping("/daily-rankings")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<RankingsResponse> getDailyRankings() {

        List<RankingDto> rankings = centralLibraryRecordService.findDailyRankings();

        return ResponseEntity.ok().body(RankingsResponse.of(rankings));
    }

    @Operation(summary = "weekly 공부 기록 순위", description = "getWeeklyRankings")
    @GetMapping("/weekly-rankings")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<RankingsResponse> getWeeklyRankings() {

        List<RankingDto> rankings = centralLibraryRecordService.findWeeklyRankings();

        return ResponseEntity.ok().body(RankingsResponse.of(rankings));
    }

    @Operation(summary = "monthly 공부 기록 순위", description = "getMonthlyRankings")
    @GetMapping("/monthly-rankings")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<RankingsResponse> getMonthlyRankings() {

        List<RankingDto> rankings = centralLibraryRecordService.findMonthlyRankings();

        return ResponseEntity.ok().body(RankingsResponse.of(rankings));
    }

    @Operation(summary = "중앙 도서관 나의 순위", description = "getMyTotalRanking")
    @GetMapping("/my-total-ranking")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<MyRankingResponse> getMyTotalRanking(@AuthMember Member member) {

        return ResponseEntity.ok().body(centralLibraryRecordService.findMyRankingByTotalStudyTime(member.getNickName()));
    }

}
