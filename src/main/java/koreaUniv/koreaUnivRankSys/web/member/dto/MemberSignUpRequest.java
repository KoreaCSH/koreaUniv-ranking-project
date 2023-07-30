package koreaUniv.koreaUnivRankSys.web.member.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import koreaUniv.koreaUnivRankSys.domain.member.domain.College;
import koreaUniv.koreaUnivRankSys.domain.member.domain.Department;
import koreaUniv.koreaUnivRankSys.domain.member.domain.Member;
import koreaUniv.koreaUnivRankSys.domain.member.domain.MemberInfoStatus;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberSignUpRequest {

    @Schema(description = "아이디", example = "회원의 아이디입니다.")
    @NotBlank(message = "아이디를 입력해주세요.")
    private String userId;

    @Schema(description = "이메일", example = "@korea.ac.kr 을 안 붙이고 넘겨도 됩니다.")
    @NotBlank(message = "이메일을 입력해주세요.")
    private String email;

    @Schema(description = "비밀번호", example = "비밀번호는 영어, 숫자, 특수문자를 포함하고, 4~16자로 공백 없이 작성되어야 합니다.")
    @NotBlank(message = "비밀번호를 입력해주세요.")
    @Pattern(message = "비밀번호는 영어, 숫자, 특수문자를 포함하고, 4~16자로 공백 없이 작성되어야 합니다.",
            regexp = "^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{4,16}")
    private String password;

    @Schema(description = "닉네임", example = "닉네임은 2~10자 이어야 합니다.")
    @NotBlank(message = "닉네임을 입력해주세요.")
    @Size(min = 2, max = 10, message = "닉네임은 2~10자 이어야 합니다.")
    private String nickName;

    @Schema(description = "단과대학", example = "select 태그로 넘겨주기")
    @NotBlank(message = "단과대학을 선택해주세요.")
    private String collegeName;

    @Schema(description = "학과", example = "select 태그로 넘겨주기")
    @NotBlank(message = "학과를 선택해주세요.")
    private String departmentName;

    @Schema(description = "회원정보 공개 여부", example = "String 타입인 Y or N 을 넘겨주세요.")
    private String memberInfoStatus;

    @Schema(description = "공부 영역", example = "null 을 넘겨도 됩니다.")
    private String profileMessage;

    @Schema(description = "프로필 사진", example = "MultipartFile 타입 및 jpeg, png 등의 이미지 파일")
    private MultipartFile profileImage;

    public Member toEntity(String password, MemberInfoStatus infoStatus, College college, Department department) {
        return Member.builder()
                .userId(userId)
                .email(email + "@korea.ac.kr")
                .password(password)
                .memberInfoStatus(infoStatus)
                .profileMessage(profileMessage)
                .nickName(nickName)
                .college(college)
                .department(department)
                .build();
    }

}
