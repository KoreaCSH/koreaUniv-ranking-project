package koreaUniv.koreaUnivRankSys.domain.building.api;

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

@RestController
@RequestMapping("/api/central-library")
@RequiredArgsConstructor
public class CentralLibraryController {

    private final CentralLibraryRecordService centralLibraryRecordService;

    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<CommonResponse> trackStudyTime(@AuthMember Member member, @Valid @RequestBody StudyTimeRequest studyTimeRequest) {

        centralLibraryRecordService.trackStudyTime(member.getUserId(), studyTimeRequest.getStudyTime());

        return ResponseEntity.ok().body(new CommonResponse(String.valueOf(HttpStatus.CREATED.value()),
                studyTimeRequest.getStudyTime() + "분이 기록되었습니다."));
    }

    @GetMapping("/total-rankings")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<RankingsResponse> getTotalRankings() {

        List<RankingDto> rankings = centralLibraryRecordService.findTotalRankings();

        return ResponseEntity.ok().body(RankingsResponse.of(rankings));
    }

    @GetMapping("/daily-rankings")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<RankingsResponse> getDailyRankings() {

        List<RankingDto> rankings = centralLibraryRecordService.findDailyRankings();

        return ResponseEntity.ok().body(RankingsResponse.of(rankings));
    }

    @GetMapping("/weekly-rankings")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<RankingsResponse> getWeeklyRankings() {

        List<RankingDto> rankings = centralLibraryRecordService.findWeeklyRankings();

        return ResponseEntity.ok().body(RankingsResponse.of(rankings));
    }

    @GetMapping("/my-total-ranking")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<MyRankingResponse> getMyTotalRanking(@AuthMember Member member) {

        return ResponseEntity.ok().body(centralLibraryRecordService.findMyRankingByTotalStudyTime(member.getNickName()));
    }

}
