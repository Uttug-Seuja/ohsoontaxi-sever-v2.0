package ohsoontaxi.backend.domain.chat.presentation.dto.response;

import lombok.Getter;

import java.util.List;

@Getter
public class ChatResponse {

    private List<ChatPagingResponseDto> chatPagingResponseDtoList;
    public ChatResponse(List<ChatPagingResponseDto> chatPagingResponseDto) {

        chatPagingResponseDtoList = chatPagingResponseDto;
    }

}
