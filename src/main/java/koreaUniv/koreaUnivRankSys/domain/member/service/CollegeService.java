package koreaUniv.koreaUnivRankSys.domain.member.service;

import koreaUniv.koreaUnivRankSys.domain.member.domain.College;
import koreaUniv.koreaUnivRankSys.domain.member.repository.CollegeRepository;
import koreaUniv.koreaUnivRankSys.global.exception.CustomException;
import koreaUniv.koreaUnivRankSys.global.exception.ErrorCode;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class CollegeService {

    private final CollegeRepository collegeRepository;


    public CollegeService(CollegeRepository collegeRepository) {
        this.collegeRepository = collegeRepository;
    }

    public College findByName(String name) {
        return collegeRepository.findByName(name).get();
        // 예외 처리 필요
    }

}
