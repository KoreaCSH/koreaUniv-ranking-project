package koreaUniv.koreaUnivRankSys.domain.member.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import koreaUniv.koreaUnivRankSys.domain.auth.service.AuthMember;
import koreaUniv.koreaUnivRankSys.domain.member.domain.Member;
import koreaUniv.koreaUnivRankSys.domain.member.dto.MemberSignUpRequest;
import koreaUniv.koreaUnivRankSys.domain.member.dto.MyPageResponse;
import koreaUniv.koreaUnivRankSys.domain.member.dto.PasswordUpdateRequest;
import koreaUniv.koreaUnivRankSys.domain.member.service.MemberService;
import koreaUniv.koreaUnivRankSys.domain.member.service.MemberStudyTimeService;
import koreaUniv.koreaUnivRankSys.global.common.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Tag(name = "회원 관련 API", description = "MemberController")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
public class MemberController {

    private final MemberService memberService;
    private final MemberStudyTimeService memberStudyTimeService;

    @Operation(summary = "회원가입", description = "join")
    @PostMapping
    public ResponseEntity<CommonResponse> join(@Valid @RequestBody MemberSignUpRequest request) {
        memberService.join(request);

        // 프론트 단에서 메일 인증을 '완료'해야만 '가입하기' 버튼을 누를 수 있도록 구현할 수 있나?
        // 회원가입 페이지 '비밀번호 확인' 추가!

        return ResponseEntity.ok().body(
                new CommonResponse(String.valueOf(HttpStatus.OK.value()),
                        request.getUserId() + "님이 가입되었습니다"));
    }

    @Operation(summary = "아이디 중복 조회", description = "checkDuplicateUserId")
    @GetMapping("/id/{userId}/exists")
    public ResponseEntity<Boolean> checkDuplicateUserId(@PathVariable String userId) {

        return ResponseEntity.ok().body(memberService.existsByUserId(userId));
    }

    @Operation(summary = "닉네임 중복 조회", description = "checkDuplicateNickName")
    @GetMapping("/nickname/{nickName}/exists")
    public ResponseEntity<Boolean> checkDuplicateNickName(@PathVariable String nickName) {

        return ResponseEntity.ok().body(memberService.existsByNickName(nickName));
    }


    @Operation(summary = "비밀번호 변경", description = "changePassword")
    @PutMapping("/password")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<CommonResponse> changePassword(@AuthMember Member member,
                                                          @Valid @RequestBody PasswordUpdateRequest request) {

        memberService.updatePassword(member.getUserId(),
                                    request.getOldPassword(),
                                    request.getNewPassword(),
                                    request.getValidNewPassword());

        return ResponseEntity.ok().body(new CommonResponse(String.valueOf(HttpStatus.OK.value()),
                    "비밀번호가 변경되었습니다."));
    }

    @Operation(summary = "마이페이지", description = "getMyPage")
    @GetMapping("/my-page")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<MyPageResponse> getMyPage(@AuthMember Member member) {
        MyPageResponse myPage = memberStudyTimeService.getMyPage(member);

        return ResponseEntity.ok().body(myPage);
    }

}
