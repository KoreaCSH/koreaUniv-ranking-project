package koreaUniv.koreaUnivRankSys.domain.member.service;

import koreaUniv.koreaUnivRankSys.domain.member.domain.College;
import koreaUniv.koreaUnivRankSys.domain.member.repository.CollegeRepository;
import koreaUniv.koreaUnivRankSys.global.exception.CustomException;
import koreaUniv.koreaUnivRankSys.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class CollegeService {

    private final CollegeRepository collegeRepository;

    public College findByName(String name) {
        return collegeRepository.findByName(name)
                .orElseThrow(() -> new CustomException(ErrorCode.COLLEGE_NOTFOUND));
    }

    public List<College> findAll() {
        return collegeRepository.findAll();
    }

}
