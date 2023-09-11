package koreaUniv.koreaUnivRankSys.web.member;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import koreaUniv.koreaUnivRankSys.domain.auth.service.AuthMember;
import koreaUniv.koreaUnivRankSys.domain.member.domain.Member;
import koreaUniv.koreaUnivRankSys.domain.member.service.PlannerService;
import koreaUniv.koreaUnivRankSys.web.member.dto.PlannerRequest;
import koreaUniv.koreaUnivRankSys.web.member.dto.PlannerResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "플래너 API - 쿼리스트링으로 날짜 넘겨줄 것", description = "PlannerController")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/planner")
public class PlannerController {

    private final PlannerService plannerService;

    @Operation(summary = "플래너 조회", description = "getPlans")
    @GetMapping("/plans")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<PlannerResponse> getPlans(@AuthMember Member member, PlannerRequest plannerRequest) {

        // @RequestParam 으로 받을 경우 Converter 가 있어야 하지만, 애노테이션 없이 PlannerRequest 객체를 받을 경우 Converter 가 필요 없다.
        // service 내부에서 null 체크 및 throw new Exception 하자.
        PlannerResponse myPlanner = plannerService.getMyPlanner(member, plannerRequest);

        return ResponseEntity.ok().body(myPlanner);
    }

}
