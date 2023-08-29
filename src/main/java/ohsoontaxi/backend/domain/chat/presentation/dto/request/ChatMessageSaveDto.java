package ohsoontaxi.backend.domain.chat.presentation.dto.request;


import lombok.*;
import ohsoontaxi.backend.domain.chat.domain.Chat;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChatMessageSaveDto {

    public enum MessageType{
        ENTER,TALK,QUIT
    }

    private Long userId;
    private Long participationId;
    private MessageType type;
    private String roomId;
    private String writer;
    private String profilePath;
    private String message;
    private String createdAt;
    private List<String> userList;

    public static ChatMessageSaveDto of (Chat chat){
        return ChatMessageSaveDto.builder()
                .userId(chat.getUserId())
                .participationId(chat.getParticipationId())
                .type(MessageType.TALK)
                .roomId(chat.getReservation().getId().toString())
                .writer(chat.getUserName())
                .createdAt(chat.getCreatedAt())
                .message(chat.getMessage())
                .build();
    }

    public static ChatMessageSaveDto createChatMessageSaveDto(ChatMessageSaveDto saveDto){
        return ChatMessageSaveDto.builder()
                .type(MessageType.TALK)
                .userId(saveDto.getUserId())
                .participationId(saveDto.participationId)
                .roomId(saveDto.getRoomId())
                .writer(saveDto.getWriter())
                .createdAt(saveDto.getCreatedAt())
                .message(saveDto.getMessage())
                .build();
    }

}