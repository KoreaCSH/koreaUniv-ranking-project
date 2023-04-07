package koreaUniv.koreaUnivRankSys.controller;

import koreaUniv.koreaUnivRankSys.domain.Member;
import koreaUniv.koreaUnivRankSys.domain.building.CentralLibraryRecord;
import koreaUniv.koreaUnivRankSys.service.MemberService;
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
        model.addAttribute("memberForm", new MemberForm());
        return "members/signIn";
    }

    @PostMapping("members/new")
    public String createMember(MemberForm memberForm) {

        Member member = Member.createMember(
            memberForm.getString_id(),
            memberForm.getEmail(),
            memberForm.getPassword(),
            memberForm.getNickName(),
                memberForm.getGrade(),
                CentralLibraryRecord.createCentralLibraryRecord()
        );

        memberService.join(member);
        return "redirect:/";
    }

}
