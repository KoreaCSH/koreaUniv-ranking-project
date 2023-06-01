package koreaUniv.koreaUnivRankSys.domain.member.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
public class MemberUpdateRequest {

    private String nickName;
    private String profileMessage;
    private MultipartFile profileImage;

}
