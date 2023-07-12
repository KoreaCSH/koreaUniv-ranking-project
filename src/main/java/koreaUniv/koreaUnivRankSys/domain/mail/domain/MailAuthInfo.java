package koreaUniv.koreaUnivRankSys.domain.mail.domain;

import koreaUniv.koreaUnivRankSys.global.common.BaseEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class MailAuthInfo extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;
    private String authCode;

    @Enumerated(EnumType.STRING)
    private MailAuthStatus status;

    @Builder
    public MailAuthInfo(String email, String authCode) {
        this.email = email;
        this.authCode = authCode;
        this.status = MailAuthStatus.N;
    }

    public void changeStatus(MailAuthStatus status) {
        this.status = status;
    }

    public void changeAuthCode(String authCode) {
        this.authCode = authCode;
    }

}
