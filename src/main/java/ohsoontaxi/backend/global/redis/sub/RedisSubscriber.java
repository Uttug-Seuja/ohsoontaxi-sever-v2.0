package ohsoontaxi.backend.global.redis.sub;


import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ohsoontaxi.backend.domain.chat.presentation.dto.request.ChatMessageSaveDto;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;


@Slf4j
@RequiredArgsConstructor
@Service
public class RedisSubscriber {

    private final ObjectMapper objectMapper;

    private final SimpMessageSendingOperations messagingTemplate;

    public void sendMessage(String publishMessage){

        try{
            //redis에서 발행된 데이터를 받아 deserialize
            ChatMessageSaveDto roomMessage = objectMapper.readValue(publishMessage, ChatMessageSaveDto.class);

            //WebSocket 구독자에게 채팅 메시지 Send
            messagingTemplate.convertAndSend("/sub/chat/room/"+roomMessage.getRoomId(),roomMessage);
        }catch (Exception e){
            log.error(e.getMessage());
        }

    }
}