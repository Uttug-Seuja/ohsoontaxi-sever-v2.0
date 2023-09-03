package ohsoontaxi.backend.domain.chat.presentation.dto.request;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ChatMessageRequest {

    private String roomId;
    private String message;
    private String accessToken;

}
