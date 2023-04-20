package ohsoontaxi.backend.domain.credential.presentation.dto.response;

import lombok.Getter;

@Getter
public class AccessTokenDto {

    private String accessToken;

    public AccessTokenDto(String accessToken){
        this.accessToken = accessToken;
    }

}
