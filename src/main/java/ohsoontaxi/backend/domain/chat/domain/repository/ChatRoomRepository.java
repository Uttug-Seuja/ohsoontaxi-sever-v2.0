package ohsoontaxi.backend.domain.chat.domain.repository;



import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ohsoontaxi.backend.domain.chat.presentation.dto.request.ChatMessageSaveDto;
import ohsoontaxi.backend.domain.chat.service.ChatRedisCacheService;
import ohsoontaxi.backend.global.utils.chat.ChatUtils;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Repository
@Getter
@Slf4j
public class ChatRoomRepository {

    private final RedisTemplate<String, Object> redisTemplate;
    private final RedisTemplate<String, String> roomRedisTemplate;


    private final ChatRedisCacheService chatRedisCacheService;

    private final ChatUtils chatUtils;
    private final RedisTemplate<String, ChatMessageSaveDto> chatRedisTemplate;

    private final ChatRepository chatRepository;
    private HashOperations<String, String, String> opsHashEnterRoom;

    private BoundHashOperations<String, String, String> setOperations;


    public static final String CHAT_ROOMS = "CHAT_ROOM";
    public static final String CHAT_ROOM_ID_ = "CHAT_ROOM_ID_";

    public static final String SESSION_ID = "SESSION_ID";
    public static final String CHAT_SORTED_SET_ = "CHAT_SORTED_SET_";


    @PostConstruct
    private void init() {
        opsHashEnterRoom = roomRedisTemplate.opsForHash();
    }

    //채팅 SubScribe 할 때, WebSocket SessionId 를 통해서 redis에 저장
    public void enterChatRoom(String roomId, String sessionId, String email) {

        //세션 - 세션ID - 방 번호
        opsHashEnterRoom.put(SESSION_ID, sessionId, roomId);

        //채팅방 - 세션ID - 유저 아이디
        opsHashEnterRoom.put(CHAT_ROOM_ID_ + roomId, sessionId, email);


    }

    // TODO: 2023/08/21 방 퇴장시 세션id를 받지 않기
    public void enterChatRoomTest(String roomId, String sessionId, String email,String participationId) {

        //세션 - 세션ID - 방 번호
        opsHashEnterRoom.put(SESSION_ID, participationId, sessionId);
        //채팅방 - 세션ID - 유저 아이디
        opsHashEnterRoom.put(CHAT_ROOM_ID_ + roomId, sessionId, email);

        //opsHashEnterRoom.put("email","userId",SESSION_ID);

    }


    //채팅 DisConnect 할 때, WebSocket SessionId 를 통해서 redis에서 삭제
    public String disconnectWebsocket(String sessionId) {
        String roomId = opsHashEnterRoom.get(SESSION_ID, sessionId);
        opsHashEnterRoom.delete(CHAT_ROOM_ID_ + roomId, sessionId);
        opsHashEnterRoom.delete(SESSION_ID, sessionId);
        return roomId;
    }

    // TODO: 2023/08/21 방 퇴장시 세션id를 받지 않기
    public String disconnectWebsocketTest(String participationId,String roomId) {
        String sessionId = opsHashEnterRoom.get(SESSION_ID, participationId);
        opsHashEnterRoom.delete(CHAT_ROOM_ID_ + roomId, sessionId);
        opsHashEnterRoom.delete(SESSION_ID, sessionId);
        return roomId;
    }


    //채팅 unsubscribe 할떄 ,
    public String leaveChatRoom(String sessionId) {
        String roomId = opsHashEnterRoom.get(SESSION_ID, sessionId);
        opsHashEnterRoom.delete(CHAT_ROOM_ID_ + roomId, sessionId);
        return roomId;
    }

    // TODO: 2023/08/21 방 퇴장시 세션id를 받지 않기
    public void leaveChatRoomTest(String participationId,String roomId) {
        String sessionId = opsHashEnterRoom.get(SESSION_ID, participationId);
        opsHashEnterRoom.delete(CHAT_ROOM_ID_ + roomId, sessionId);

    }


}