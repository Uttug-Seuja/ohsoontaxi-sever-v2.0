package ohsoontaxi.backend.global.redis.scheduler;




import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ohsoontaxi.backend.domain.chat.domain.Chat;
import ohsoontaxi.backend.domain.chat.domain.repository.ChatJdbcRepository;
import ohsoontaxi.backend.domain.chat.domain.repository.ChatRepository;
import ohsoontaxi.backend.domain.chat.presentation.dto.request.ChatMessageSaveDto;
import ohsoontaxi.backend.domain.reservation.domain.Reservation;
import ohsoontaxi.backend.domain.reservation.domain.repository.ReservationRepository;
import ohsoontaxi.backend.domain.reservation.service.ReservationUtils;
import org.springframework.data.redis.core.*;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class ChatWriteBackScheduling {

    private final RedisTemplate<String,Object> redisTemplate;

    private final RedisTemplate<String, ChatMessageSaveDto> chatRedisTemplate;

    private final ChatRepository chatRepository;

    //private final ChatRoomJpaRepository chatRoomJpaRepository;

    private final ChatJdbcRepository chatJdbcRepository;

    private final ReservationRepository reservationRepository;

    //@Scheduled(cron = "0 0 0/1 * * *")
    @Scheduled(cron = "0 0/2 * * * *")
    @Transactional
    public void writeBack(){
        log.info("------Scheduling start------");
        //여기서부터 읽어오는 과정.
        BoundZSetOperations<String, ChatMessageSaveDto> setOperations = chatRedisTemplate.boundZSetOps("NEW_CHAT");

        ScanOptions scanOptions = ScanOptions.scanOptions().build();

        List<Chat> chatList = new ArrayList<>();
        try(Cursor<ZSetOperations.TypedTuple<ChatMessageSaveDto>> cursor= setOperations.scan(scanOptions)){

            while(cursor.hasNext()){
                ZSetOperations.TypedTuple<ChatMessageSaveDto> chatMessageDto = cursor.next();

                Reservation reservation = reservationRepository
                        .findById(Long.parseLong(chatMessageDto.getValue().getRoomId()))
                        .orElse(null);

                if(reservation==null) {
                    continue;
                }

                chatList.add(Chat.of(chatMessageDto.getValue(),reservation));
            }
            chatJdbcRepository.batchInsertRoomInventories(chatList);

            redisTemplate.delete("NEW_CHAT");

        }catch (Exception e){
            e.printStackTrace();
        }

        log.info("-----Scheduling done------");
    }
}