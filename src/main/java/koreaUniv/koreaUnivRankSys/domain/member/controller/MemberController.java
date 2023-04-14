package koreaUniv.koreaUnivRankSys.domain.member.controller;

import koreaUniv.koreaUnivRankSys.domain.member.api.dto.MemberSignUpRequest;
import koreaUniv.koreaUnivRankSys.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("members/signIn")
    public String createForm(Model model) {
        model.addAttribute("memberSignUpRequest", new MemberSignUpRequest());
        return "members/signIn";
    }

    @PostMapping("members/new")
    public String createMember(MemberSignUpRequest request) {

        memberService.join(request);

        return "redirect:/";
    }

}
