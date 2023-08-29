package ohsoontaxi.backend.domain.reservation.service;


import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ohsoontaxi.backend.domain.chat.domain.repository.ChatRepository;
import ohsoontaxi.backend.domain.chat.presentation.dto.request.ChatMessageSaveDto;
import ohsoontaxi.backend.domain.chat.presentation.dto.response.ChatPagingResponseDto;
import ohsoontaxi.backend.domain.participation.domain.Participation;
import ohsoontaxi.backend.domain.participation.domain.repository.ParticipationRepository;
import ohsoontaxi.backend.domain.reservation.domain.Reservation;
import ohsoontaxi.backend.domain.reservation.domain.repository.ReservationRepository;
import ohsoontaxi.backend.domain.reservation.exception.ReservationNotFoundException;
import ohsoontaxi.backend.domain.reservation.presentation.dto.request.CreateReservationRequest;
import ohsoontaxi.backend.domain.reservation.presentation.dto.request.UpdateReservationRequest;
import ohsoontaxi.backend.domain.reservation.presentation.dto.response.ChatRoomBriefInfoDto;
import ohsoontaxi.backend.domain.reservation.presentation.dto.response.KeywordDto;
import ohsoontaxi.backend.domain.reservation.presentation.dto.response.ReservationBriefInfoDto;
import ohsoontaxi.backend.domain.reservation.presentation.dto.response.ReservationResponse;
import ohsoontaxi.backend.domain.user.domain.User;
import ohsoontaxi.backend.global.common.reservation.ReservationStatus;
import ohsoontaxi.backend.global.utils.security.SecurityUtils;
import ohsoontaxi.backend.global.utils.user.UserUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
@Transactional(readOnly = true)
@Slf4j
public class ReservationService implements ReservationUtils {

    private final ReservationRepository reservationRepository;
    private final UserUtils userUtils;
    private final ParticipationRepository participationRepository;
    private final RedisTemplate<String, ChatMessageSaveDto> chatRedisTemplate;
    private ZSetOperations<String, ChatMessageSaveDto> zSetOperations;
    private final ChatRepository chatRepository;

    @PostConstruct
    private void init() {
        zSetOperations = chatRedisTemplate.opsForZSet();
    }

    @Transactional
    public ReservationResponse createReservation(CreateReservationRequest createReservationRequest){

        User user = userUtils.getUserFromSecurityContext();

        Reservation reservation = makeReservation(createReservationRequest, user);

        reservation.checkReservationGender();

        reservationRepository.save(reservation);

        return getReservationResponse(reservation, user.getId());
    }

    @Transactional
    public void deleteReservation(Long reservationId){

        User user = userUtils.getUserFromSecurityContext();

        Reservation reservation = queryReservation(reservationId);

        reservation.validUserIsHost(user.getId());

        chatRepository.updateReservationNull(reservationId);

        reservationRepository.delete(reservation);
    }

    //방 상세정보
    public ReservationResponse getReservationDetailById(Long reservationId) {

        Long currentUserId = SecurityUtils.getCurrentUserId();

        Reservation reservation = queryReservation(reservationId);

        return getReservationResponse(reservation,currentUserId);
    }

    @Transactional
    public ReservationResponse updateReservation(Long reservationId, UpdateReservationRequest updateReservationRequest) {

        Long currentUserId = SecurityUtils.getCurrentUserId();

        Reservation reservation = queryReservation(reservationId);

        reservation.validUserIsHost(currentUserId);

        reservation.updateReservation(updateReservationRequest.toUpdateReservationDto());

        return getReservationResponse(reservation,currentUserId);
    }



    // 가까운 순서대로 페이징 해서 가져오기
    public Slice<ReservationBriefInfoDto> findAllReservation(PageRequest pageRequest) {

        Slice<Reservation> sliceReservation =
                reservationRepository.findSliceBy(pageRequest);

        return sliceReservation.map(reservation -> new ReservationBriefInfoDto(reservation.getReservationBaseInfoVo()));
    }

    // 내가 만든방
    public List<ReservationBriefInfoDto> reservedByMe() {

        Long currentUserId = SecurityUtils.getCurrentUserId();

        List<Reservation> myReservation = reservationRepository.findReservedByMe(currentUserId);

        return myReservation.stream().map(reservation -> new ReservationBriefInfoDto(reservation.getReservationBaseInfoVo())).collect(Collectors.toList());
    }

    // 내가 참여한방

    public List<ReservationBriefInfoDto> participatedReservation() {

        Long currentUserId = SecurityUtils.getCurrentUserId();

        List<Reservation> myReservation = reservationRepository.findParticipatedReservation(currentUserId);

        return myReservation.stream().map(reservation -> new ReservationBriefInfoDto(reservation.getReservationBaseInfoVo())).collect(Collectors.toList());
    }

    public Slice<KeywordDto> getKeyword(String word, Pageable pageable){

        Slice<Reservation> reservations = reservationRepository.keywordBySlice(word, pageable);

        return reservations.map(reservation -> new KeywordDto(reservation.getReservationBaseInfoVo()));
    }


    public Slice<ReservationBriefInfoDto> search(String word, Pageable pageable){

        Slice<Reservation> reservations = reservationRepository.searchBySlice(word, pageable);

        return reservations.map(reservation -> new ReservationBriefInfoDto(reservation.getReservationBaseInfoVo()));
    }


    public List<KeywordDto> getRecommendWord(){

        List<Reservation> reservations = reservationRepository.findTop5ByOrderByIdDesc();

        return reservations.stream().map(reservation -> new KeywordDto(reservation.getReservationBaseInfoVo())).collect(Collectors.toList());
    }



    private Reservation makeReservation(CreateReservationRequest createReservationRequest,User user){

        Reservation reservation = Reservation.builder()
                .user(user)
                .title(createReservationRequest.getTitle())
                .startPoint(createReservationRequest.getStartPoint())
                .destination(createReservationRequest.getDestination())
                .departureDate(createReservationRequest.getDepartureDate())
                .reservationStatus(ReservationStatus.POSSIBLE)
                .gender(createReservationRequest.getGender())
                .passengerNum(4)
                .currentNum(0)
                .startLatitude(createReservationRequest.getStartLatitude())
                .startLongitude(createReservationRequest.getStartLongitude())
                .destinationLatitude(createReservationRequest.getDestinationLatitude())
                .destinationLongitude(createReservationRequest.getDestinationLongitude())
                .build();

        return reservation;
    }

    private ReservationResponse getReservationResponse(Reservation reservation,Long currentUserId) {
        return new ReservationResponse(
                reservation.getReservationBaseInfoVo(),
                reservation.getUser().getUserInfo(),
                reservation.checkUserIsHost(currentUserId));
    }

//    private ChatRoomBriefInfoDto getChatRoomResponse(Participation participation) {
//        return new ChatRoomBriefInfoDto(
//                participation.getReservation().getReservationBaseInfoVo(), participation);
//    }

    @Override
    public Reservation queryReservation(Long id) {
        return reservationRepository
                .findById(id)
                .orElseThrow(()-> ReservationNotFoundException.EXCEPTION);
    }



    public List<ChatRoomBriefInfoDto> getChatRoom(){

        Long currentUserId = SecurityUtils.getCurrentUserId();

        List<Reservation> reservations = reservationRepository.findParticipatedReservation(currentUserId);


        log.info("currentUserId={}",currentUserId);

        log.info("participation length={}");

        //List<Participation> participations = participationRepository.findByUserId(currentUserId);

        List<ChatRoomBriefInfoDto> chatRoomBriefInfoDtoList = new ArrayList<>();

        for (Reservation reservation : reservations) {

            log.info("------start----------");

            Set<ChatMessageSaveDto> chatMessageSaveDtos = zSetOperations.reverseRange(CHAT_SORTED_SET_ + reservation.getId(), 0, 0);

            if(!chatMessageSaveDtos.isEmpty()){

                List<ChatPagingResponseDto> chatMessageDtoList =
                        chatMessageSaveDtos
                                .stream()
                                .map(ChatPagingResponseDto::byChatMessageDto)
                                .collect(Collectors.toList());

                log.info("len={}",chatMessageDtoList.size());

                ChatPagingResponseDto chatPagingResponseDto = chatMessageDtoList.get(0);

                log.info("participation num={}",reservation.getParticipations().size());

                ChatRoomBriefInfoDto chatRoomBriefInfoDto = new ChatRoomBriefInfoDto(reservation.getReservationBaseInfoVo(), chatPagingResponseDto);

                chatRoomBriefInfoDtoList.add(chatRoomBriefInfoDto);

            }else{

                ChatPagingResponseDto message = ChatPagingResponseDto.builder()
                        .message("채팅내역이 없습니다")
                        .createdAt(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss.SSS"))).build();

                ChatRoomBriefInfoDto chatRoomBriefInfoDto = new ChatRoomBriefInfoDto(reservation.getReservationBaseInfoVo(), message);

                chatRoomBriefInfoDtoList.add(chatRoomBriefInfoDto);

            }


        }


        return chatRoomBriefInfoDtoList;

    }



}
