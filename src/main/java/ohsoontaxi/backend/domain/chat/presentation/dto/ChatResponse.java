package ohsoontaxi.backend.domain.chat.presentation.dto;

import lombok.Getter;
import ohsoontaxi.backend.domain.participation.domain.Participation;
import ohsoontaxi.backend.domain.participation.presentation.dto.response.ParticipationInfoDto;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class ChatResponse {

    private List<ChatPagingResponseDto> chatPagingResponseDtoList;
    public ChatResponse(List<ChatPagingResponseDto> chatPagingResponseDto) {

        chatPagingResponseDtoList = chatPagingResponseDto;
    }

}
