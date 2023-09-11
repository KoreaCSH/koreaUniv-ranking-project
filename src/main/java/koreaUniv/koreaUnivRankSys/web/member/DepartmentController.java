package koreaUniv.koreaUnivRankSys.web.member;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import koreaUniv.koreaUnivRankSys.domain.member.service.DepartmentService;
import koreaUniv.koreaUnivRankSys.global.exception.CustomException;
import koreaUniv.koreaUnivRankSys.global.exception.ErrorCode;
import koreaUniv.koreaUnivRankSys.web.member.dto.DepartmentDto;
import koreaUniv.koreaUnivRankSys.web.member.dto.DepartmentResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "학과 API", description = "DepartmentController")
@Validated
@RestController
@RequestMapping("/api/department")
@RequiredArgsConstructor
public class DepartmentController {

    private final DepartmentService departmentService;

    @Operation(summary = "학과 조회", description = "getDepartmentList")
    @GetMapping("/departmentList/{collegeName}")
    public ResponseEntity<DepartmentResponse> getDepartmentList(@PathVariable String collegeName) {

        if(collegeName.isBlank()) {
            throw new CustomException(ErrorCode.COLLEGE_NOTFOUND);
        }

        List<DepartmentDto> departmentList = departmentService.findByCollegeName(collegeName);

        return ResponseEntity.ok().body(DepartmentResponse.of(departmentList));
    }

}
