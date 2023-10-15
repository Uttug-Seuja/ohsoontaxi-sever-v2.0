package ohsoontaxi.backend.domain.chat.presentation;



import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ohsoontaxi.backend.domain.chat.presentation.dto.request.ChatMessageRequest;
import ohsoontaxi.backend.domain.chat.presentation.dto.request.ChatMessageSaveDto;
import ohsoontaxi.backend.domain.chat.service.ChatRedisCacheService;
import ohsoontaxi.backend.domain.participation.domain.Participation;
import ohsoontaxi.backend.domain.participation.service.ParticipationUtils;
import ohsoontaxi.backend.global.redis.pub.RedisPublisher;
import ohsoontaxi.backend.global.security.JwtTokenProvider;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
@RequiredArgsConstructor
@RestController
public class ChatController {

    private final RedisPublisher redisPublisher;
    private final ChatRedisCacheService chatRedisCacheService;
    private final ChannelTopic channelTopic;
    private final JwtTokenProvider jwtTokenProvider;
    private final ParticipationUtils participationUtils;

//    @MessageMapping("/chat/message")
//    public void message(ChatMessageDto chatMessageDto, @Header("Authorization") String rawToken){
//
//        String token = jwtTokenProvider.resolveTokenWeb(rawToken);
//
//        log.info("token={}",token);
//
//        jwtTokenProvider.validateToken(jwtTokenProvider.resolveTokenWeb(rawToken));
//
//        String userId = jwtTokenProvider.getUserId(token);
//
//        Participation participation = participationUtils.findParticipation(Long.valueOf(userId), Long.valueOf(chatMessageDto.getRoomId()));
//
//        ChatMessageSaveDto message = ChatMessageSaveDto.builder()
//                .type(ChatMessageSaveDto.MessageType.TALK)
//                .message(chatMessageDto.getMessage())
//                .profilePath(participation.getUser().getProfilePath())
//                .roomId(chatMessageDto.getRoomId())
//                .writer(participation.getUser().getName())
//                .createdAt(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss.SSS")))
//                .userId(participation.getUser().getId()).build();
//
//
//        redisPublisher.publish(channelTopic,message);
//        chatRedisCacheService.addChat(message);
//    }

    @MessageMapping("/chat/message")
    public void message(ChatMessageRequest chatMessageRequest){

        String token = jwtTokenProvider.resolveTokenWeb(chatMessageRequest.getAccessToken());

        jwtTokenProvider.validateToken(token);

        String userId = jwtTokenProvider.getUserId(token);

        Participation participation = participationUtils.findParticipation(Long.valueOf(userId), Long.valueOf(chatMessageRequest.getRoomId()));

        log.info(participation.getUser().getName());

        ChatMessageSaveDto message = ChatMessageSaveDto.builder()
                .type(ChatMessageSaveDto.MessageType.TALK)
                .message(chatMessageRequest.getMessage())
                .profilePath(participation.getUser().getProfilePath())
                .roomId(chatMessageRequest.getRoomId())
                .writer(participation.getUser().getName())
                .createdAt(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss.SSS")))
                .userId(participation.getUser().getId())
                .participationId(participation.getId())
                .build();

        redisPublisher.publish(channelTopic,message);
        chatRedisCacheService.addChat(message);
    }


}