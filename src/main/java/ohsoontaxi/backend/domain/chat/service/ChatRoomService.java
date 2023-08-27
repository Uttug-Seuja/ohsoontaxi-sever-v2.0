package ohsoontaxi.backend.domain.chat.service;



import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ohsoontaxi.backend.domain.chat.domain.repository.ChatRoomRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ChatRoomService {

    public final ChatRoomRepository chatRoomRepository;

    public void enterChatRoom(String roomId, String sessionId,String username) {
        chatRoomRepository.enterChatRoom(roomId,sessionId,username);
    }

    public void enterChatRoomTest(String roomId, String sessionId, String email,String participationId) {
        chatRoomRepository.enterChatRoomTest(roomId,sessionId,email,participationId);
    }

    public String disconnectWebsocket(String sessionId){
        return chatRoomRepository.disconnectWebsocket(sessionId);
    }
    public String disconnectWebsocketTest(String participationId,String roomId){
        return chatRoomRepository.disconnectWebsocketTest(participationId,roomId);
    }

    public void leaveChatRoomTest(String participationId,String roomId){
        chatRoomRepository.leaveChatRoomTest(participationId,roomId);
    }

    public String leaveChatRoom(String sessionId){
        return chatRoomRepository.leaveChatRoom(sessionId);
    }

    public List<String> findUser(String roomId, String sessionId){

        return chatRoomRepository.findUsersInWorkSpace(roomId,sessionId);
    }




}