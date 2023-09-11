package koreaUniv.koreaUnivRankSys.web.member;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import koreaUniv.koreaUnivRankSys.domain.member.service.CollegeService;
import koreaUniv.koreaUnivRankSys.web.member.dto.CollegeDto;
import koreaUniv.koreaUnivRankSys.web.member.dto.CollegeResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "학부 API", description = "CollegeController")
@RestController
@RequestMapping("/api/college")
@RequiredArgsConstructor
public class CollegeController {

    private final CollegeService collegeService;

    @Operation(summary = "학부 조회", description = "getCollegeList")
    @GetMapping("/collegeList")
    public ResponseEntity<CollegeResponse> getCollegeList() {
        List<CollegeDto> collegeList = collegeService.findAll();

        return ResponseEntity.ok().body(CollegeResponse.of(collegeList));
    }

}
