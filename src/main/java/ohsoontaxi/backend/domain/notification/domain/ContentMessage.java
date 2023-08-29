package ohsoontaxi.backend.domain.notification.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ContentMessage {

    PARTICIPATION(" 님이 \"", "\" 방에 참여하였습니다."),
    PARTICIPATION_CANCEL(" 님이 \"", "\" 방에서 참여 취소하였습니다."),
    CHATTING(" 님이 ", "채팅을 보냈습니다."),
    RESERVATION_MODIFY("회원님이 참여한 \"", "\" 방이 수정되었습니다. 앱에 들어가서 확인 부탁드립니다."),
    RESERVATION_DELETE("회원님이 참여한 \"", "\" 방이 삭제되었습니다. 확인 부탁드립니다."),
    TIME("회원님이 참여한 \"", "\" 방 약속 시간이 10분 남았습니다. 확인 부탁드립니다."),
    DEADLINE("회원님이 참여한 \"", "\" 방이 마감되었습니다.");

    String content1;
    String content2;
}
