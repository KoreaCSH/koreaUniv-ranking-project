package koreaUniv.koreaUnivRankSys.service;


import koreaUniv.koreaUnivRankSys.domain.MemorialHall;
import koreaUniv.koreaUnivRankSys.repository.H2MemorialHallRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemorialService {

    private final H2MemorialHallRepository memorialHallRepository;

    public Long makeRecode(MemorialHall memorialHall) {
        memorialHallRepository.save(memorialHall);
        return memorialHall.getId();
    }

}
