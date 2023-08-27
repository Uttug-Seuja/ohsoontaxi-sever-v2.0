package ohsoontaxi.backend.global.config;



import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ohsoontaxi.backend.domain.chat.presentation.dto.request.ChatMessageSaveDto;
import ohsoontaxi.backend.domain.chat.service.ChatRoomService;
import ohsoontaxi.backend.domain.participation.domain.Participation;
import ohsoontaxi.backend.domain.participation.service.ParticipationUtils;
import ohsoontaxi.backend.global.property.JwtProperties;
import ohsoontaxi.backend.global.redis.pub.RedisPublisher;
import ohsoontaxi.backend.global.security.JwtTokenProvider;
import ohsoontaxi.backend.global.utils.chat.ChatUtils;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class StompHandler implements ChannelInterceptor {
    public static final String TOKEN = "token";
    public static final String SIMP_DESTINATION = "simpDestination";
    public static final String SIMP_SESSION_ID = "simpSessionId";
    public static final String INVALID_ROOM_ID = "InvalidRoomId";

    private final ChatUtils chatUtils;

    private final ChannelTopic topic;

    private final ChatRoomService chatRoomService;

    private final RedisPublisher redisPublisher;

    private final ParticipationUtils participationUtils;

    private final JwtTokenProvider jwtTokenProvider;

    private final JwtProperties jwtProperties;


    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {

        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);

        if (StompCommand.CONNECT == accessor.getCommand()) {

//            String rawHeader = accessor.getFirstNativeHeader(jwtProperties.getHeader());
//
//            String token = jwtTokenProvider.resolveTokenWeb(rawHeader);
//
//            log.info("token={}",token);
//
//            jwtTokenProvider.validateToken(jwtTokenProvider.resolveTokenWeb(rawHeader));


            // TODO: 2023/08/14 웹소켓 테스트

            String rawHeader = accessor.getFirstNativeHeader(jwtProperties.getHeader());

            String token = jwtTokenProvider.resolveTokenWeb(rawHeader);

            log.info("token={}",token);

            jwtTokenProvider.validateToken(jwtTokenProvider.resolveTokenWeb(rawHeader));
            String userId = jwtTokenProvider.getUserId(token);

            //String roomId = accessor.getFirstNativeHeader("roomId");

            log.info("userId={}",userId);


            String destination = accessor.getFirstNativeHeader(SIMP_DESTINATION);

            String sessionId = accessor.getFirstNativeHeader(SIMP_SESSION_ID);

//            String destination = Optional.ofNullable(
//                    (String) message.getHeaders().get(SIMP_DESTINATION)
//            ).orElse(INVALID_ROOM_ID);

//            String sessionId = Optional.ofNullable(
//                    (String) message.getHeaders().get(SIMP_SESSION_ID)
//            ).orElse(null);

            log.info("sessionId={}",sessionId);

            String roomId = chatUtils.getRoodIdFromDestination(destination);

            Participation participation = participationUtils.findParticipation(Long.valueOf(userId), Long.valueOf(roomId));

            chatRoomService.enterChatRoomTest(roomId, sessionId, participation.getUser().getEmail(), String.valueOf(participation.getId()));

//            redisPublisher.publish(topic,
//                    ChatMessageSaveDto.builder()
//                            .type(ChatMessageSaveDto.MessageType.ENTER)
//                            .roomId(roomId)
//                            .userList(chatRoomService.findUser(roomId, sessionId))
//                            .build()
//            );


        }

        // 소켓 연결 후 ,SUBSCRIBE 등록
        else if (StompCommand.SUBSCRIBE == accessor.getCommand()) {

//            String rawHeader = accessor.getFirstNativeHeader(jwtProperties.getHeader());
//
//            String token = jwtTokenProvider.resolveTokenWeb(rawHeader);
//
//            log.info("token={}",token);
//
//            jwtTokenProvider.validateToken(jwtTokenProvider.resolveTokenWeb(rawHeader));
//            String userId = jwtTokenProvider.getUserId(token);
//
//            log.info("userId={}",userId);
//
//
//            String destination = Optional.ofNullable(
//                    (String) message.getHeaders().get(SIMP_DESTINATION)
//            ).orElse(INVALID_ROOM_ID);
//
//            String sessionId = Optional.ofNullable(
//                    (String) message.getHeaders().get(SIMP_SESSION_ID)
//            ).orElse(null);
//
//            String roomId = chatUtils.getRoodIdFromDestination(destination);
//
//            Participation participation = participationUtils.findParticipation(Long.valueOf(userId), Long.valueOf(roomId));
//
//            chatRoomService.enterChatRoom(roomId, sessionId, participation.getUser().getName());
//
//            redisPublisher.publish(topic,
//                    ChatMessageSaveDto.builder()
//                            .type(ChatMessageSaveDto.MessageType.ENTER)
//                            .roomId(roomId)
//                            .userList(chatRoomService.findUser(roomId, sessionId))
//                            .build()
//            );


        // TODO: 2023/08/21 세션ID 레디스에 다르게 저장
//        else if (StompCommand.SUBSCRIBE == accessor.getCommand()) {
//
//                String rawHeader = accessor.getFirstNativeHeader(jwtProperties.getHeader());
//
//                String token = jwtTokenProvider.resolveTokenWeb(rawHeader);
//
//                log.info("token={}",token);
//
//                jwtTokenProvider.validateToken(jwtTokenProvider.resolveTokenWeb(rawHeader));
//                String userId = jwtTokenProvider.getUserId(token);
//
//                log.info("userId={}",userId);
//
//
//                String destination = Optional.ofNullable(
//                        (String) message.getHeaders().get(SIMP_DESTINATION)
//                ).orElse(INVALID_ROOM_ID);
//
//                String sessionId = Optional.ofNullable(
//                        (String) message.getHeaders().get(SIMP_SESSION_ID)
//                ).orElse(null);
//
//                String roomId = chatUtils.getRoodIdFromDestination(destination);
//
//                Participation participation = participationUtils.findParticipation(Long.valueOf(userId), Long.valueOf(roomId));
//
//                chatRoomService.enterChatRoomTest(roomId,sessionId,participation.getUser().getEmail(), String.valueOf(participation.getId()));
//
//                redisPublisher.publish(topic,
//                        ChatMessageSaveDto.builder()
//                                .type(ChatMessageSaveDto.MessageType.ENTER)
//                                .roomId(roomId)
//                                .userList(chatRoomService.findUser(roomId, sessionId))
//                                .build()
//                );



            }

        //reids SubScribe 해제
        else if (StompCommand.UNSUBSCRIBE == accessor.getCommand()) {

//            String sessionId = Optional.ofNullable(
//                    (String) message.getHeaders().get(SIMP_SESSION_ID)
//            ).orElse(null);
//
//            String roomId = chatRoomService.leaveChatRoom(sessionId);
//
//            redisPublisher.publish(topic,
//                    ChatMessageSaveDto.builder()
//                            .type(ChatMessageSaveDto.MessageType.QUIT)
//                            .roomId(roomId)
//                            .userList(chatRoomService.findUser(roomId, sessionId))
//                            .build()
//            );


            // TODO: 2023/08/21 세션ID 레디스에 다르게 저장


            String rawHeader = accessor.getFirstNativeHeader(jwtProperties.getHeader());

            String token = jwtTokenProvider.resolveTokenWeb(rawHeader);

            log.info("token={}",token);

            jwtTokenProvider.validateToken(jwtTokenProvider.resolveTokenWeb(rawHeader));

            String userId = jwtTokenProvider.getUserId(token);

            log.info("userId={}",userId);


            String destination = Optional.ofNullable(
                    (String) message.getHeaders().get(SIMP_DESTINATION)
            ).orElse(INVALID_ROOM_ID);

            String sessionId = Optional.ofNullable(
                    (String) message.getHeaders().get(SIMP_SESSION_ID)
            ).orElse(null);

            String roomId = chatUtils.getRoodIdFromDestination(destination);

            Participation participation = participationUtils.findParticipation(Long.valueOf(userId), Long.valueOf(roomId));

            chatRoomService.leaveChatRoomTest(String.valueOf(participation.getId()),roomId);

//            redisPublisher.publish(topic,
//                    ChatMessageSaveDto.builder()
//                            .type(ChatMessageSaveDto.MessageType.QUIT)
//                            .roomId(roomId)
//                            .userList(chatRoomService.findUser(roomId, sessionId))
//                            .build()
//            );





        }

        //소켓 연결 후 , 소켓 연결 해제 시
        else if (StompCommand.DISCONNECT == accessor.getCommand()) {

//            String sessionId = Optional.ofNullable(
//                    (String) message.getHeaders().get(SIMP_SESSION_ID)
//            ).orElse(null);
//
//            log.info("sessionId={}",sessionId);
//
//            String roomId = chatRoomService.disconnectWebsocket(sessionId);
//
//            redisPublisher.publish(topic,
//                    ChatMessageSaveDto.builder()
//                            .type(ChatMessageSaveDto.MessageType.QUIT)
//                            .roomId(roomId)
//                            .userList(chatRoomService.findUser(roomId, sessionId))
//                            .build()
//            );


            String rawHeader = accessor.getFirstNativeHeader(jwtProperties.getHeader());

            String token = jwtTokenProvider.resolveTokenWeb(rawHeader);

            log.info("token={}",token);

            jwtTokenProvider.validateToken(jwtTokenProvider.resolveTokenWeb(rawHeader));
            String userId = jwtTokenProvider.getUserId(token);

            log.info("userId={}",userId);


            String destination = Optional.ofNullable(
                    (String) message.getHeaders().get(SIMP_DESTINATION)
            ).orElse(INVALID_ROOM_ID);

            String sessionId = Optional.ofNullable(
                    (String) message.getHeaders().get(SIMP_SESSION_ID)
            ).orElse(null);

            String roomId = chatUtils.getRoodIdFromDestination(destination);

            Participation participation = participationUtils.findParticipation(Long.valueOf(userId), Long.valueOf(roomId));

            chatRoomService.disconnectWebsocketTest(String.valueOf(participation.getId()),roomId);

//            redisPublisher.publish(topic,
//                    ChatMessageSaveDto.builder()
//                            .type(ChatMessageSaveDto.MessageType.QUIT)
//                            .roomId(roomId)
//                            .userList(chatRoomService.findUser(roomId, sessionId))
//                            .build()
//            );


        }
        return message;
    }
}