package ohsoontaxi.backend.domain.email.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ohsoontaxi.backend.global.database.BaseEntity;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class EmailMessage extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "email_message_id")
    private Long id;

    private String email;

    private String code;

    private String oauthProvider;

    private Boolean isApproved;

    @Builder
    public EmailMessage(String email, String code, String oauthProvider, Boolean isApproved){
        this.email = email;
        this.code = code;
        this.oauthProvider = oauthProvider;
        this.isApproved = isApproved;
    }

    public void changeCode(String code){
        this.code = code;
    }

    public void changeStateTrue() {
        this.isApproved = true;
    }
}
