package ohsoontaxi.backend.domain.credential.service;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum OauthProvider {


    KAKAO("KAKAO"),
    GOOGLE("GOOGLE");
    private String oauthProviderName;

    @JsonValue
    public String getValue() {
        return oauthProviderName;
    }
}
