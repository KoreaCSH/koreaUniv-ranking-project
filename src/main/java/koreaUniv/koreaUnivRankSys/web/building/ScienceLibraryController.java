package koreaUniv.koreaUnivRankSys.web.building;

import koreaUniv.koreaUnivRankSys.domain.auth.service.AuthMember;
import koreaUniv.koreaUnivRankSys.web.building.dto.MyRankingResponse;
import koreaUniv.koreaUnivRankSys.web.building.dto.RankingDto;
import koreaUniv.koreaUnivRankSys.web.building.dto.RankingsResponse;
import koreaUniv.koreaUnivRankSys.web.building.dto.StudyTimeRequest;
import koreaUniv.koreaUnivRankSys.domain.building.service.ScienceLibraryRecordService;
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
@RequestMapping("/api/science-library")
@RequiredArgsConstructor
public class ScienceLibraryController {

    private final ScienceLibraryRecordService scienceLibraryRecordService;

    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<CommonResponse> trackStudyTime(@AuthMember Member member, @Valid @RequestBody StudyTimeRequest studyTimeRequest) {

        scienceLibraryRecordService.trackStudyTime(member, studyTimeRequest.getStudyTime());

        return ResponseEntity.ok().body(new CommonResponse(String.valueOf(HttpStatus.CREATED.value()),
                studyTimeRequest.getStudyTime() + "분이 기록되었습니다."));
    }

    @GetMapping("/total-rankings")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<RankingsResponse> getTotalRankings() {

        List<RankingDto> rankings = scienceLibraryRecordService.findTotalRankings();

        return ResponseEntity.ok().body(RankingsResponse.of(rankings));
    }

    @GetMapping("/daily-rankings")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<RankingsResponse> getDailyRankings() {

        List<RankingDto> rankings = scienceLibraryRecordService.findDailyRankings();

        return ResponseEntity.ok().body(RankingsResponse.of(rankings));
    }

    @GetMapping("/weekly-rankings")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<RankingsResponse> getWeeklyRankings() {

        List<RankingDto> rankings = scienceLibraryRecordService.findWeeklyRankings();

        return ResponseEntity.ok().body(RankingsResponse.of(rankings));
    }

    @GetMapping("/monthly-rankings")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<RankingsResponse> getMonthlyRankings() {

        List<RankingDto> rankings = scienceLibraryRecordService.findMonthlyRankings();

        return ResponseEntity.ok().body(RankingsResponse.of(rankings));
    }

    @GetMapping("/my-total-ranking")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<MyRankingResponse> getMyTotalRanking(@AuthMember Member member) {

        return ResponseEntity.ok().body(scienceLibraryRecordService.findMyRankingByTotalStudyTime(member.getNickName()));
    }

}
