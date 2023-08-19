package ohsoontaxi.backend.domain.chat.presentation.dto;



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
    private String writer;
    private String message;
    private String createdAt;
    private String profilePath;


    public static ChatPagingResponseDto of(Chat chat){
        return ChatPagingResponseDto.builder()
                .userId(chat.getUserId())
                .writer(chat.getUserName())
                .reservationId(chat.getReservation().getId())
                .createdAt(chat.getCreatedAt())
                .message(chat.getMessage())
                .build();
    }

    public static ChatPagingResponseDto byChatMessageDto(ChatMessageSaveDto chatMessageSaveDto){
        return ChatPagingResponseDto.builder()
                .userId(chatMessageSaveDto.getUserId())
                .writer(chatMessageSaveDto.getWriter())
                .createdAt(chatMessageSaveDto.getCreatedAt())
                .reservationId(Long.parseLong(chatMessageSaveDto.getRoomId()))
                .message(chatMessageSaveDto.getMessage())
                .build();
    }
}