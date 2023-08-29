package ohsoontaxi.backend.domain.chat.presentation;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ohsoontaxi.backend.domain.chat.presentation.dto.request.ChatPagingRequest;
import ohsoontaxi.backend.domain.chat.presentation.dto.response.ChatResponse;
import ohsoontaxi.backend.domain.chat.service.ChatRedisCacheService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/chat")
public class ChatDataController {

    private final ChatRedisCacheService cacheService;


    @PostMapping("/{reservationId}")
    public ChatResponse getChatting(@PathVariable Long reservationId, @RequestBody(required = false) ChatPagingRequest chatPagingRequest){

        //Cursor 존재하지 않을 경우,현재시간을 기준으로 paging
        if(chatPagingRequest ==null|| chatPagingRequest.getCursor()==null || chatPagingRequest.getCursor().equals("")){
            chatPagingRequest = ChatPagingRequest.builder()
                    .cursor( LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss.SSS")))
                    .build();
        }
        return cacheService.getChatsFromRedis(reservationId, chatPagingRequest);
    }



}