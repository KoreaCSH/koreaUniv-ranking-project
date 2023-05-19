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

        return ResponseEntity.ok().body(request.getString_id() + "님이 가입되었습니다");
    }

    @GetMapping("/{id}/exists")
    public ResponseEntity<ErrorResult> checkDuplicateId(@PathVariable String id) {
        memberService.validateDuplicateMember(id);

        return ResponseEntity.ok().body(new ErrorResult(
                "200", "사용 가능한 아이디입니다."
        ));
    }

}
