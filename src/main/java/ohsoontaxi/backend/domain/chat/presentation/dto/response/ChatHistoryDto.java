package ohsoontaxi.backend.domain.chat.presentation.dto.response;



import lombok.*;
import ohsoontaxi.backend.domain.chat.domain.Chat;
import ohsoontaxi.backend.domain.chat.presentation.dto.request.ChatMessageSaveDto;

@Getter
@NoArgsConstructor
@Builder
@Setter
@AllArgsConstructor
public class ChatHistoryDto {

    private Long reservationId;
    private Long userId;
    private Long participationId;
    private String writer;
    private String message;
    private String createdAt;
    private String profilePath;
    private boolean iSend;

    public ChatHistoryDto(boolean result, ChatPagingResponseDto chatPagingResponseDto) {

        reservationId = Long.valueOf(chatPagingResponseDto.getReservationId());
        userId = chatPagingResponseDto.getUserId();
        participationId = chatPagingResponseDto.getParticipationId();
        writer = chatPagingResponseDto.getWriter();
        message = chatPagingResponseDto.getMessage();
        createdAt = chatPagingResponseDto.getCreatedAt();
        profilePath = chatPagingResponseDto.getProfilePath();
        iSend = result;

    }
}