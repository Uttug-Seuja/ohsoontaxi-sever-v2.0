package ohsoontaxi.backend.domain.credential.presentation.dto.response;

import lombok.Getter;

@Getter
public class AccessTokenDto {

    private String accessToken;

    private String test;
    public AccessTokenDto(String accessToken){
        this.accessToken = accessToken;
    }
    

}
