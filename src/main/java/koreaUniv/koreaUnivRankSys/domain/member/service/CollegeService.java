package koreaUniv.koreaUnivRankSys.domain.member.service;

import koreaUniv.koreaUnivRankSys.domain.member.domain.College;
import koreaUniv.koreaUnivRankSys.domain.member.repository.CollegeRepository;
import koreaUniv.koreaUnivRankSys.global.exception.CustomException;
import koreaUniv.koreaUnivRankSys.global.exception.ErrorCode;
import koreaUniv.koreaUnivRankSys.web.member.dto.CollegeDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class CollegeService {

    private final CollegeRepository collegeRepository;

    public College findByName(String name) {
        return collegeRepository.findByName(name)
                .orElseThrow(() -> new CustomException(ErrorCode.COLLEGE_NOTFOUND));
    }

    public List<CollegeDto> findAll() {
        return collegeRepository.findAll()
                .stream()
                .map(c -> CollegeDto.of(c))
                .collect(Collectors.toList());
    }

}
