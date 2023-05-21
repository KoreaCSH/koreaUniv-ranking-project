package koreaUniv.koreaUnivRankSys.domain.member.api;

import koreaUniv.koreaUnivRankSys.domain.member.dto.MemberSignUpRequest;
import koreaUniv.koreaUnivRankSys.domain.member.service.MemberService;
import koreaUniv.koreaUnivRankSys.global.common.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
public class MemberController {

    private final MemberService memberService;

    @PostMapping
    public ResponseEntity<CommonResponse> join(@Valid @RequestBody MemberSignUpRequest request) {
        memberService.join(request);

        return ResponseEntity.ok().body(
                new CommonResponse(String.valueOf(HttpStatus.CREATED.value()),
                        request.getUserId() + "님이 가입되었습니다"));
    }

    @GetMapping("id/{userId}/exists")
    public ResponseEntity<Boolean> checkDuplicateUserId(@PathVariable String userId) {

        return ResponseEntity.ok().body(memberService.existsByUserId(userId));
    }

    @GetMapping("nickname/{nickName}/exists")
    public ResponseEntity<Boolean> checkDuplicateNickName(@PathVariable String nickName) {

        return ResponseEntity.ok().body(memberService.existsByNickName(nickName));
    }

}
