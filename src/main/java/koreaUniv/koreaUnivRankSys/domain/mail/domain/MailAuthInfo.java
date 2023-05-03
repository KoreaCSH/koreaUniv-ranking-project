package koreaUniv.koreaUnivRankSys.domain.mail.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter
@NoArgsConstructor
public class MailAuthInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;
    private String authCode;

    @Builder
    public MailAuthInfo(String email, String authCode) {
        this.email = email;
        this.authCode = authCode;
    }

}
