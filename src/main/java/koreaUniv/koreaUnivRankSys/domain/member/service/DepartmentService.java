package koreaUniv.koreaUnivRankSys.domain.member.service;

import java.util.List;
import java.util.stream.Collectors;

import koreaUniv.koreaUnivRankSys.web.member.dto.DepartmentDto;
import org.springframework.stereotype.Service;

import koreaUniv.koreaUnivRankSys.domain.member.domain.Department;
import koreaUniv.koreaUnivRankSys.domain.member.repository.DepartmentRepository;
import koreaUniv.koreaUnivRankSys.global.exception.CustomException;
import koreaUniv.koreaUnivRankSys.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DepartmentService {

    private final DepartmentRepository departmentRepository;

    public Department findByName(String name) {
        return departmentRepository.findByName(name)
                .orElseThrow(() -> new CustomException(ErrorCode.DEPARTMENT_NOTFOUND));
    }

    public List<DepartmentDto> findByCollegeName(String collegeName) {

        return departmentRepository.findByCollegeName(collegeName)
                .stream()
                .map(d -> DepartmentDto.of(d))
                .collect(Collectors.toList());
    }

}
