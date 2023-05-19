package koreaUniv.koreaUnivRankSys.domain.member.api;

import koreaUniv.koreaUnivRankSys.domain.member.dto.MemberSignUpRequest;
import koreaUniv.koreaUnivRankSys.domain.member.service.MemberService;
import koreaUniv.koreaUnivRankSys.global.exception.ErrorResult;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
public class MemberController {

    private final MemberService memberService;

    @PostMapping
    public ResponseEntity<String> join(@Valid @RequestBody MemberSignUpRequest request) {
        memberService.join(request);

        return ResponseEntity.ok().body(request.getUserId() + "님이 가입되었습니다");
    }

    @GetMapping("/{id}/exists")
    public ResponseEntity<Boolean> checkDuplicateId(@PathVariable String id) {

        return ResponseEntity.ok().body(memberService.existsByUserId(id));
    }

}
