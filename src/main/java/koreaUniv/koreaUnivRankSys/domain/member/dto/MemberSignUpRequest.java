package koreaUniv.koreaUnivRankSys.domain.member.dto;

import koreaUniv.koreaUnivRankSys.domain.member.domain.Member;
import koreaUniv.koreaUnivRankSys.domain.member.domain.MemberInfoStatus;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberSignUpRequest {

    @NotBlank(message = "아이디를 입력해주세요.")
    private String userId;

    @NotBlank(message = "이메일을 입력해주세요.")
    private String email;

    @NotBlank(message = "비밀번호를 입력해주세요.")
    @Pattern(message = "비밀번호는 영어, 숫자, 특수문자를 포함하고, 4~16자로 공백 없이 작성되어야 합니다.",
            regexp = "^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{4,16}")
    private String password;

    @NotBlank(message = "닉네임을 입력해주세요.")
    @Size(min = 2, max = 10, message = "닉네임은 2~10자 이어야 합니다.")
    private String nickName;

    private String collegeName;
    private String departmentName;
    private String memberInfoStatus;
    private String profileMessage;
    private MultipartFile profileImage;

    public Member toEntity(String password, MemberInfoStatus infoStatus) {
        return Member.builder()
                .userId(userId)
                .email(email + "@korea.ac.kr")
                .password(password)
                .memberInfoStatus(infoStatus)
                .profileMessage(profileMessage)
                .nickName(nickName)
                .build();
    }

}
