package koreaUniv.koreaUnivRankSys.service.building;

import koreaUniv.koreaUnivRankSys.domain.building.CentralLibraryRecord;
import koreaUniv.koreaUnivRankSys.repository.building.JpaCentralLibraryRecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CentralLibraryRecordService {

    private final JpaCentralLibraryRecordRepository centralLibraryRecordRepository;

    public Optional<CentralLibraryRecord> findByStringId(String stringId) {
        return centralLibraryRecordRepository.findByStringId(stringId);
    }

    @Transactional
    public Long updateStartTime(String stringId) {
        CentralLibraryRecord findRecord = centralLibraryRecordRepository.findByStringId(stringId)
                .orElseThrow(() -> new IllegalStateException("공부 기록이 없습니다."));

        findRecord.recordStartTime();
        return findRecord.getId();
    }

    @Transactional
    public Long updateFinishTime(String stringId) {
        CentralLibraryRecord findRecord = centralLibraryRecordRepository.findByStringId(stringId)
                .orElseThrow(() -> new IllegalStateException("공부 기록이 없습니다."));

        findRecord.recordFinishTime();
        return findRecord.getId();
    }

    @Transactional
    public Long updateStudyingTime(String stringId) {
        CentralLibraryRecord findRecord = centralLibraryRecordRepository.findByStringId(stringId)
                .orElseThrow(() -> new IllegalStateException("공부 기록이 없습니다."));

        findRecord.recordStudyingTime();
        return findRecord.getId();
    }

}
