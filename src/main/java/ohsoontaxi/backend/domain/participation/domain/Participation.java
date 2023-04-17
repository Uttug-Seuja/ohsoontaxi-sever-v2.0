package ohsoontaxi.backend.domain.participation.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.GenerationType.*;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class Participation {

    @Id @GeneratedValue(strategy = IDENTITY)
    @Column(name = "participation_id")
    private Long id;

}
