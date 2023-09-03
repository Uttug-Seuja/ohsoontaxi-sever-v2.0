package ohsoontaxi.backend.global.redis.pub;



import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ohsoontaxi.backend.domain.chat.presentation.dto.request.ChatMessageSaveDto;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Slf4j
public class RedisPublisher {

    //redis 발행

    private final RedisTemplate<String, Object> redisTemplate;

    public void publish(ChannelTopic topic, ChatMessageSaveDto messageDto) {

        redisTemplate.convertAndSend(topic.getTopic(), messageDto);
    }
}