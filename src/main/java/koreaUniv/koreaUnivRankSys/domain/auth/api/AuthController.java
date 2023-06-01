package koreaUniv.koreaUnivRankSys.domain.auth.api;

import koreaUniv.koreaUnivRankSys.domain.auth.service.AuthMember;
import koreaUniv.koreaUnivRankSys.domain.member.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {

    @GetMapping("/api/test")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<String> authTest(@AuthMember Member member) {
        String nickName = member.getNickName();
        return ResponseEntity.ok().body(nickName + "님 환영합니다.");
    }

}
