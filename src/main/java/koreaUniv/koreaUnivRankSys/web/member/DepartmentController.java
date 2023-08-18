package koreaUniv.koreaUnivRankSys.web.member;

import koreaUniv.koreaUnivRankSys.domain.member.service.DepartmentService;
import koreaUniv.koreaUnivRankSys.web.member.dto.DepartmentDto;
import koreaUniv.koreaUnivRankSys.web.member.dto.DepartmentResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/department")
@RequiredArgsConstructor
public class DepartmentController {

    private final DepartmentService departmentService;

    @GetMapping("{collegeName}")
    public ResponseEntity<DepartmentResponse> getDepartmentList(@PathVariable String collegeName) {
        List<DepartmentDto> departmentList = departmentService.findByCollegeName(collegeName);

        return ResponseEntity.ok().body(DepartmentResponse.of(departmentList));
    }

}
