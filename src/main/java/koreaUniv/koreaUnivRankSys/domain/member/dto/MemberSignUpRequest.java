package koreaUniv.koreaUnivRankSys.domain.member.dto;

import koreaUniv.koreaUnivRankSys.domain.member.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MemberSignUpRequest {

    private String string_id;
    private String email;
    private String password;
    private String nickName;
    private MultipartFile profileImage;

    public Member toEntity(String password) {
        return Member.builder()
                .string_id(string_id)
                .email(email)
                .password(password)
                .nickName(nickName)
                .build();
    }

}
