package koreaUniv.koreaUnivRankSys.web.member;

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

@Validated
@RestController
@RequestMapping("/api/department")
@RequiredArgsConstructor
public class DepartmentController {

    private final DepartmentService departmentService;

    @GetMapping("/departments/{collegeName}")
    public ResponseEntity<DepartmentResponse> getDepartmentList(@PathVariable String collegeName) {

        if(collegeName.isBlank()) {
            throw new CustomException(ErrorCode.COLLEGE_NOTFOUND);
        }

        List<DepartmentDto> departmentList = departmentService.findByCollegeName(collegeName);

        return ResponseEntity.ok().body(DepartmentResponse.of(departmentList));
    }

}
