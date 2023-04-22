package koreaUniv.koreaUnivRankSys.domain.building.service;

import koreaUniv.koreaUnivRankSys.domain.building.domain.CentralLibraryRecord;
import koreaUniv.koreaUnivRankSys.domain.building.repository.JpaCentralLibraryRecordRepository;
import koreaUniv.koreaUnivRankSys.domain.building.service.CentralLibraryRecordService;
import koreaUniv.koreaUnivRankSys.domain.member.api.dto.MemberSignUpRequest;
import koreaUniv.koreaUnivRankSys.domain.member.domain.Member;
import koreaUniv.koreaUnivRankSys.domain.member.service.MemberService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.Optional;

@SpringBootTest
@Transactional
class CentralLibraryRecordServiceTest {

    @Autowired
    MemberService memberService;

    @Autowired
    CentralLibraryRecordService centralLibraryRecordService;

    @Test
    //@Rollback(value = false)
    void 공부_기록_측정() throws InterruptedException {
        MemberSignUpRequest request = new MemberSignUpRequest();
        request.setString_id("test1");
        request.setNickName("korea1");

        Long memberId = memberService.join(request);
        Member findMember = memberService.findOne(memberId);
        centralLibraryRecordService.recordStudyingTime(findMember.getString_id(), 10L);

        MemberSignUpRequest request2 = new MemberSignUpRequest();
        request2.setString_id("test2");
        request2.setNickName("korea2");

        Long memberId2 = memberService.join(request2);
        Member findMember2 = memberService.findOne(memberId2);
        centralLibraryRecordService.recordStudyingTime(findMember2.getString_id(), 20L);

        CentralLibraryRecord findRecord = centralLibraryRecordService.findByStringId(findMember.getString_id());
        Assertions.assertThat(findRecord).isEqualTo(findMember.getCentralLibraryRecord());
        Assertions.assertThat(findRecord.getTotalStudyingTime()).isEqualTo(10L);

        CentralLibraryRecord findRecord2 = centralLibraryRecordService.findByStringId(findMember2.getString_id());
        Assertions.assertThat(findRecord2).isEqualTo(findMember2.getCentralLibraryRecord());
        Assertions.assertThat(findRecord2.getTotalStudyingTime()).isEqualTo(20L);
    }

}