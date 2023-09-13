package ohsoontaxi.backend.domain.user.presentation.dto.response;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeployTestDto {

    private String test;

    public DeployTestDto() {
        test ="배포가 완료됐습니다";
    }
}
