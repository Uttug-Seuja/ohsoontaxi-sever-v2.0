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

    @Builder
    public EmailMessage(String email, String code){
        this.email = email;
        this.code = code;
    }

    public void changeCode(String code){
        this.code = code;
    }

}
