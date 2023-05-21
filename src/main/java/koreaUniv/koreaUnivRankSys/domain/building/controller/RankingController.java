package koreaUniv.koreaUnivRankSys.domain.building.controller;

import koreaUniv.koreaUnivRankSys.domain.building.dto.RankingDto;
import koreaUniv.koreaUnivRankSys.domain.building.service.CentralLibraryRecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class RankingController {

    private final CentralLibraryRecordService centralLibraryRecordService;

    @GetMapping("centralLibrary/ranking")
    public String rankingList(Model model) {
        List<RankingDto> ranking = centralLibraryRecordService.findAllByRanking();
        model.addAttribute("ranking", ranking);
        return "buildings/centralLibrary";
    }

}
