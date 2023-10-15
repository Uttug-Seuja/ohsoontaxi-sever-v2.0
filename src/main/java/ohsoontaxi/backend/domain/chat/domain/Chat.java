package ohsoontaxi.backend.domain.chat.domain;


import jakarta.persistence.*;
import lombok.*;
import ohsoontaxi.backend.domain.chat.presentation.dto.request.ChatMessageSaveDto;
import ohsoontaxi.backend.domain.reservation.domain.Reservation;

import java.io.Serializable;

import static jakarta.persistence.FetchType.LAZY;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
@Entity
@Table(name = "chats")
public class Chat implements Serializable {

    private static final long serialVersionUID = 5090380600159441769L;

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "chat_id")
    private Long id;

    private String message;

    private Long userId;

    private Long participationId;

    private String userName;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "reservation_id")
    private Reservation reservation;

    private String createdAt;

    public static Chat of(ChatMessageSaveDto chatMessageSaveDto, Reservation reservation){

        return Chat.builder()
                .message(chatMessageSaveDto.getMessage())
                .createdAt(chatMessageSaveDto.getCreatedAt())
                .userId(chatMessageSaveDto.getUserId())
                .participationId(chatMessageSaveDto.getParticipationId())
                .userName(chatMessageSaveDto.getWriter())
                .reservation(reservation)
                .build();

    }
}