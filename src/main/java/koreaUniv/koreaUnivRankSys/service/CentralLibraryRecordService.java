package koreaUniv.koreaUnivRankSys.service;

import koreaUniv.koreaUnivRankSys.domain.CentralLibraryRecord;
import koreaUniv.koreaUnivRankSys.repository.JpaCentralLibraryRecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CentralLibraryRecordService {

    private final JpaCentralLibraryRecordRepository centralLibraryRecordRepository;

    @Transactional
    public Long makeRecord(CentralLibraryRecord centralLibraryRecord) {
        centralLibraryRecordRepository.save(centralLibraryRecord);
        return centralLibraryRecord.getId();
    }

    @Transactional
    public Long updateStartTime(String stringId) {
        CentralLibraryRecord findRecord = centralLibraryRecordRepository.findByStringId(stringId)
                .orElseThrow(() -> new IllegalStateException("없는 공부 기록입니다."));

        findRecord.recordStartTime();
        return findRecord.getId();
    }

}
