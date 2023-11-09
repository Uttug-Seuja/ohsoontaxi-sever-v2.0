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
    private String temperatureImage;
    private Integer reportedNum;
    private Integer participationNum;

    @Builder
    public Temperature(Double currentTemperature, String temperatureImage, Integer reportedNum, Integer participationNum) {
        this.currentTemperature = currentTemperature;
        this.temperatureImage = temperatureImage;
        this.reportedNum = reportedNum;
        this.participationNum = participationNum;
    }

    public static Temperature createTemperature() {
        return builder()
                .currentTemperature(36.5)
                .temperatureImage("https://ohsoonbucket.s3.ap-northeast-2.amazonaws.com/image/1%7Cb34fae75-7946-4e5c-8176-5f7a63dcc120.png")
                .reportedNum(0)
                .participationNum(0)
                .build();
    }

    public TemperatureInfoVo getTemperatureInfoVo() {
        return new TemperatureInfoVo(id, currentTemperature, temperatureImage, reportedNum, participationNum);
    }

    public void updateTemperature(Double temperature) {
        this.currentTemperature = temperature;
    }
    public void updateTemperatureImage(String temperatureImage) {
        this.temperatureImage = temperatureImage;
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
