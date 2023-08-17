package koreaUniv.koreaUnivRankSys.web.member;

import koreaUniv.koreaUnivRankSys.domain.member.service.CollegeService;
import koreaUniv.koreaUnivRankSys.web.member.dto.CollegeDto;
import koreaUniv.koreaUnivRankSys.web.member.dto.CollegeResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/college")
@RequiredArgsConstructor
public class CollegeController {

    private final CollegeService collegeService;

    @GetMapping
    public ResponseEntity<CollegeResponse> getCollegeList() {
        List<CollegeDto> collegeList = collegeService.findAll();

        return ResponseEntity.ok().body(CollegeResponse.of(collegeList));
    }

}
