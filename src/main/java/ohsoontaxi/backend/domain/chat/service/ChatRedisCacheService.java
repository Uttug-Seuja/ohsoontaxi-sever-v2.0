package ohsoontaxi.backend.domain.chat.service;



import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ohsoontaxi.backend.domain.chat.domain.Chat;
import ohsoontaxi.backend.domain.chat.domain.repository.ChatRepository;
import ohsoontaxi.backend.domain.chat.presentation.dto.request.ChatMessageSaveDto;
import ohsoontaxi.backend.domain.chat.presentation.dto.request.ChatPagingDto;
import ohsoontaxi.backend.domain.chat.presentation.dto.response.ChatHistoryDto;
import ohsoontaxi.backend.domain.chat.presentation.dto.response.ChatPagingResponseDto;
import ohsoontaxi.backend.domain.chat.presentation.dto.response.ChatResponse;
import ohsoontaxi.backend.domain.participation.domain.Participation;
import ohsoontaxi.backend.domain.participation.service.ParticipationUtils;
import ohsoontaxi.backend.domain.reservation.domain.Reservation;
import ohsoontaxi.backend.domain.reservation.domain.vo.ReservationBaseInfoVo;
import ohsoontaxi.backend.domain.reservation.presentation.dto.response.ReservationResponse;
import ohsoontaxi.backend.domain.reservation.service.ReservationUtils;
import ohsoontaxi.backend.domain.user.domain.User;
import ohsoontaxi.backend.domain.user.domain.repository.UserRepository;
import ohsoontaxi.backend.domain.user.domain.vo.UserInfoVO;
import ohsoontaxi.backend.global.utils.chat.ChatUtils;
import ohsoontaxi.backend.global.utils.security.SecurityUtils;
import ohsoontaxi.backend.global.utils.user.UserUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static ohsoontaxi.backend.domain.chat.domain.repository.ChatRoomRepository.CHAT_SORTED_SET_;


@Service
@RequiredArgsConstructor
@Slf4j
public class ChatRedisCacheService {

    private final ChatUtils chatUtils;
    public static final String NEW_CHAT = "NEW_CHAT";
    public static final String OUT_USER = "탈퇴한 회원";
    public static final String USERNAME_PROFILE = "USERNAME_PROFILE";

    private final RedisTemplate<String, Object> redisTemplate;

    private final ChatRepository chatRepository;

    private final RedisTemplate<String, ChatMessageSaveDto> chatRedisTemplate;

    private final RedisTemplate<String, String> roomRedisTemplate;
    private ZSetOperations<String, ChatMessageSaveDto> zSetOperations;

    private final UserUtils userUtils;

    private final ParticipationUtils participationUtils;

    @PostConstruct
    private void init() {
        zSetOperations = chatRedisTemplate.opsForZSet();
    }

    //redis chat data 삽입
    public void addChat(ChatMessageSaveDto chatMessageSaveDto) {
        ChatMessageSaveDto savedData = ChatMessageSaveDto.createChatMessageSaveDto(chatMessageSaveDto);
        redisTemplate.opsForZSet().add(NEW_CHAT, savedData, chatUtils.changeLocalDateTimeToDouble(savedData.getCreatedAt()));
        redisTemplate.opsForZSet().add(CHAT_SORTED_SET_ + savedData.getRoomId(), savedData, chatUtils.changeLocalDateTimeToDouble(savedData.getCreatedAt()));
    }


    //chat_data 조회
    public ChatResponse getChatsFromRedis(Long workSpaceId, ChatPagingDto chatPagingDto) {

        Long currentUserId = SecurityUtils.getCurrentUserId();
        Participation participation = participationUtils.findParticipation(currentUserId, workSpaceId);

        log.info("------start--------");

        //마지막 채팅을 기준으로 redis의 Sorted set에 몇번째 항목인지 파악
        ChatMessageSaveDto cursorDto = ChatMessageSaveDto.builder()
                .type(ChatMessageSaveDto.MessageType.TALK)
                .roomId(workSpaceId.toString())
                .userId(chatPagingDto.getUserId())
                .participationId(chatPagingDto.getParticipationId())
                .createdAt(chatPagingDto.getCursor())
                .message(chatPagingDto.getMessage())
                .writer(chatPagingDto.getWriter())
                .build();


        //마지막 chat_data cursor Rank 조회
        Long rank = zSetOperations.reverseRank(CHAT_SORTED_SET_ + workSpaceId,  cursorDto);

        log.info("rank={}",rank);

        //Cursor 없을 경우 -> 최신채팅 조회
        if (rank == null)
            rank = 0L;
        else rank = rank + 1;

        //Redis 로부터 chat_data 조회
        Set<ChatMessageSaveDto> chatMessageSaveDtoSet = zSetOperations.reverseRange(CHAT_SORTED_SET_ + workSpaceId, rank, rank + 10);


        List<ChatPagingResponseDto> chatMessageDtoList =
                chatMessageSaveDtoSet
                        .stream()
                        .map(ChatPagingResponseDto::byChatMessageDto)
                        .collect(Collectors.toList());


        //Chat_data 부족할경우 MYSQL 추가 조회
        if (chatMessageDtoList.size() != 10) {
            findOtherChatDataInMysql(chatMessageDtoList, workSpaceId, chatPagingDto.getCursor());
        }

        for (ChatPagingResponseDto chatPagingResponseDto : chatMessageDtoList) {
            chatPagingResponseDto.setProfilePath(findProfileById(String.valueOf(chatPagingResponseDto.getUserId())));
        }


        List<ChatHistoryDto> chatHistoryList = chatMessageDtoList.stream()
                .map(dto -> new ChatHistoryDto(dto.getUserId().equals(currentUserId), dto))
                .collect(Collectors.toList());

        return getChatResponseTest(participation.getId(),participation.getReservation(),currentUserId,chatHistoryList);
    }

    public void cachingDBDataToRedis(Chat chat) {
        ChatMessageSaveDto chatMessageSaveDto = ChatMessageSaveDto.of(chat);
        redisTemplate.opsForZSet()
                .add(
                        CHAT_SORTED_SET_ + chatMessageSaveDto.getRoomId(),
                        chatMessageSaveDto,
                        chatUtils.changeLocalDateTimeToDouble(chatMessageSaveDto.getCreatedAt()));
    }

    public String findProfileById(String userId) {

        log.info("userId={}",userId);

        String profile = (String) roomRedisTemplate.opsForHash().get(USERNAME_PROFILE, userId);

        log.info("profile={}",profile);

        if (profile != null)
            return profile;

        User user = userUtils.getUserById(Long.valueOf(userId));

        log.info("email={}",userId);

        roomRedisTemplate.opsForHash().put(USERNAME_PROFILE, userId, user.getProfilePath());

        return user.getProfilePath();
    }


    public void changeUserCachingProfile(String userId, String profile) {
        roomRedisTemplate.opsForHash().put(USERNAME_PROFILE, userId, profile);
    }

    public void deleteUserCachingNickname(String username) {
        roomRedisTemplate.opsForHash().delete(USERNAME_PROFILE, username);
    }

    private void findOtherChatDataInMysql(List<ChatPagingResponseDto> chatMessageDtoList, Long workSpaceId, String cursor) {

        String lastCursor;
        // 데이터가 하나도 없을 경우 현재시간을 Cursor로 활용
        if (chatMessageDtoList.size() == 0 && cursor == null) {
            lastCursor = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss.SSS"));
        }

        //redis 적재된 마지막 데이터를 입력했을 경우.
        else if (chatMessageDtoList.size() == 0 && cursor != null) {
            lastCursor = cursor;
        }

        // 데이터가 존재할 경우 CreatedAt을 Cursor로 사용
        else lastCursor = chatMessageDtoList.get(chatMessageDtoList.size() - 1).getCreatedAt();

        int dtoListSize = chatMessageDtoList.size();
        Slice<Chat> chatSlice =
                chatRepository
                        .findAllByCreatedAtBeforeAndReservationIdOrderByCreatedAtDesc(
                                lastCursor,
                                workSpaceId,
                                PageRequest.of(0, 30)
                        );

        for (Chat chat : chatSlice.getContent()) {
            cachingDBDataToRedis(chat);
        }


        //추가 데이터가 없을 때 return;
        if (chatSlice.getContent().isEmpty())
            return;

        //추가 데이터가 존재하다면, responseDto에  데이터 추가.
        for (int i = dtoListSize; i <= 10; i++) {
            try {
                Chat chat = chatSlice.getContent().get(i - dtoListSize);
                chatMessageDtoList.add(ChatPagingResponseDto.of(chat));
            } catch (IndexOutOfBoundsException e) {
                return;
            }
        }

    }

    private ChatResponse getChatResponseTest(Long participationId,Reservation reservation,Long currentUserId, List<ChatHistoryDto> chatHistoryList) {
        return new ChatResponse(
                participationId,
                reservation.getReservationBaseInfoVo(),
                reservation.getUser().getUserInfo(),
                reservation.checkUserIsHost(currentUserId),
                chatHistoryList);
    }
}