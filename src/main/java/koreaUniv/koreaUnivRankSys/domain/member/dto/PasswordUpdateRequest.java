package koreaUniv.koreaUnivRankSys.domain.member.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PasswordUpdateRequest {

    @NotBlank(message = "기존의 비밀번호를 입력해주세요.")
    private String oldPassword;

    @NotBlank(message = "새로운 비밀번호를 입력해주세요.")
    @Pattern(message = "새로운 비밀번호는 영어, 숫자, 특수문자를 포함하고, 4~16자로 공백 없이 작성되어야 합니다.",
            regexp = "^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{4,16}")
    private String newPassword;

    @NotBlank(message = "비밀번호 확인을 입력해주세요.")
    private String validNewPassword;

}
