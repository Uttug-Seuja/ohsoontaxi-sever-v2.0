package ohsoontaxi.backend.domain.notification.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TitleMessage {

    PARTICIPATION("[참여 알림]"),
    PARTICIPATION_CANCEL("[참여 취소 알림]"),
    CHATTING("[채팅 알림]"),
    RESERVATION_MODIFY("[방 수정 알림]"),
    RESERVATION_DELETE("[방 삭제 알림]"),
    TIME("[시간 알림]"),
    DEADLINE("[마감 알림]");

    String title;
}
