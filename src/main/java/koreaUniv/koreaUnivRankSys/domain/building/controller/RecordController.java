package koreaUniv.koreaUnivRankSys.domain.building.controller;

import koreaUniv.koreaUnivRankSys.domain.building.service.CentralLibraryRecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class RecordController {

    private final CentralLibraryRecordService centralLibraryRecordService;

    @GetMapping("centralLibrary/updateForm")
    public String updateForm(Model model) {
        model.addAttribute("updateRequest", new UpdateRequest());
        return "buildings/updateForm";
    }

    @PostMapping("centralLibrary/update")
    public String update(UpdateRequest request) {
        centralLibraryRecordService.recordStudyingTime(request.getString_id(), request.getStudyingTime());
        return "redirect:/";
    }

}
