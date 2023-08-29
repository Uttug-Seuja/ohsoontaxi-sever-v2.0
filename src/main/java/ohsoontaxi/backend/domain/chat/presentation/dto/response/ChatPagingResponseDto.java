package ohsoontaxi.backend.domain.chat.presentation.dto.response;



import lombok.*;
import ohsoontaxi.backend.domain.chat.domain.Chat;
import ohsoontaxi.backend.domain.chat.presentation.dto.request.ChatMessageSaveDto;

@Getter
@NoArgsConstructor
@Builder
@Setter
@AllArgsConstructor
public class ChatPagingResponseDto {

    private Long reservationId;
    private Long userId;
    private Long participationId;
    private String writer;
    private String message;
    private String createdAt;
    private String profilePath;


    public static ChatPagingResponseDto of(Chat chat){
        return ChatPagingResponseDto.builder()
                .userId(chat.getUserId())
                .participationId(chat.getParticipationId())
                .writer(chat.getUserName())
                .reservationId(chat.getReservation().getId())
                .createdAt(chat.getCreatedAt())
                .message(chat.getMessage())
                .build();
    }

    public static ChatPagingResponseDto byChatMessageDto(ChatMessageSaveDto chatMessageSaveDto){
        return ChatPagingResponseDto.builder()
                .userId(chatMessageSaveDto.getUserId())
                .participationId(chatMessageSaveDto.getParticipationId())
                .writer(chatMessageSaveDto.getWriter())
                .createdAt(chatMessageSaveDto.getCreatedAt())
                .reservationId(Long.parseLong(chatMessageSaveDto.getRoomId()))
                .message(chatMessageSaveDto.getMessage())
                .build();
    }



}