package ohsoontaxi.backend.domain.temperature.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ohsoontaxi.backend.domain.temperature.domain.vo.TemperatureInfoVo;

import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
public class Temperature {

    @Id @GeneratedValue(strategy = IDENTITY)
    @Column(name = "temperature_id")
    private Long id;

    private Double currentTemperature;
    private Integer reportedNum;
    private Integer participationNum;

    @Builder
    public Temperature(Double currentTemperature, Integer reportedNum, Integer participationNum) {
        this.currentTemperature = currentTemperature;
        this.reportedNum = reportedNum;
        this.participationNum = participationNum;
    }

    public static Temperature createTemperature() {
        return builder()
                .currentTemperature(36.5)
                .reportedNum(0)
                .participationNum(0)
                .build();
    }

    public TemperatureInfoVo getTemperatureInfoVo() {
        return new TemperatureInfoVo(id, currentTemperature, reportedNum, participationNum);
    }

    public void updateTemperature(Double temperature) {
        this.currentTemperature = temperature;
    }

    public void addReportNum() {
        this.reportedNum++;
    }

    public void subReportNum() {
        this.reportedNum--;
    }

    public void addParticipationNum() {
        this.participationNum++;
    }

    public void subParticipationNum() {
        this.participationNum--;
    }
}
