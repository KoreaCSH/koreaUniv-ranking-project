package koreaUniv.koreaUnivRankSys.service.building;

import koreaUniv.koreaUnivRankSys.domain.building.CentralLibraryRecord;
import koreaUniv.koreaUnivRankSys.repository.building.JpaCentralLibraryRecordRepository;
import koreaUniv.koreaUnivRankSys.repository.interfaces.CentralLibraryRecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CentralLibraryRecordService {

    private final CentralLibraryRecordRepository centralLibraryRecordRepository;

    public Optional<CentralLibraryRecord> findByStringId(String stringId) {
        return centralLibraryRecordRepository.findByStringId(stringId);
    }

    @Transactional
    public void recordStudyingTime(String stringId, Long studyingTime) {
        CentralLibraryRecord findRecord = centralLibraryRecordRepository.findByStringId(stringId)
                .orElseThrow(() -> new IllegalStateException("공부 기록이 존재하지 않습니다."));

        findRecord.updateStudyingTime(studyingTime);
    }

}
