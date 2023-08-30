package ohsoontaxi.backend.global.error.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    /* 400 BAD_REQUEST : 잘못된 요청 */
    INVALID_REFRESH_TOKEN(400, "리프레시 토큰이 유효하지 않습니다"),
    INVALID_ACCESS_TOKEN(400, "Access 토큰이 유효하지 않습니다"),
    MISMATCH_REFRESH_TOKEN(400, "리프레시 토큰의 유저 정보가 일치하지 않습니다"),
    REFRESH_TOKEN_NOT_EXIST(400, "리프레시 토큰 정보가 올바르지 않습니다"),
    DUPLICATED_PARTICIPATION(400, "중복된 참여입니다."),
    DUPLICATED_SEATPOSITION(400, "중복된 좌석입니다."),
    GENDER_NOT_EQUAL(400, "잘못된 성별입니다."),
    FULL_PARTICIPATION_EXCEPTION(400, "참여자가 만석인 게시글 입니다."),
    RESERVATION_STATUS_EXCEPTION(400, "마감인 게시글 입니다."),
    IS_HOST_EXCEPTION(400, "방장입니다."),
    NOTIFICATION_FCM_TOKEN_INVALID(400, "FCM Token이 유효하지 않습니다."),

    /* 401 UNAUTHORIZED : 인증되지 않은 사용자 */
    INVALID_TOKEN(401, "토큰이 유효하지 않습니다."),
    UNAUTHORIZED_MEMBER(401, "현재 내 계정 정보가 존재하지 않습니다"),
    EXPIRED_TOKEN(401, "토큰이 만료되었습니다."),

    /* 404 NOT_FOUND : Resource를 찾을 수 없음 */
    USER_NOT_FOUND(404, "해당하는 정보의 사용자를 찾을 수 없습니다."),
    USER_EXISTED(404, "이미 존재하는 회원입니다"),
    RESERVATION_NOT_HOST(400,  "방 주인이 아닙니다"),
    PARTICIPATION_NOT_HOST(400,  "참여 주인이 아닙니다"),
    OUT_OF_PAGE(400,  "책 페이지 범위를 초과했습니다"),
    REPORT_NOT_FOUND(404, "신고 정보를 찾을 수 없습니다."),
    PARTICIPATION_NOT_FOUND(404, "참여 정보를 찾을 수 없습니다."),
    EMAIL_MESSAGE_NOT_FOUND(404,"해당 메세지를 찾을 수 없습니다."),

    /* 403 */
    REGISTER_EXPIRED_TOKEN(403,"만료된 리프레쉬 토큰입니다."),

    REFRESH_TOKEN_NOT_FOUND(404, "로그아웃 된 사용자입니다"),
    NO_ERROR_TYPE(404, "오류 발생"),
    PROFILE_IMAGE_NOT_FOUND(404,  "PROFILE Not Found"),
    MOOD_IMAGE_NOT_FOUND(404,  "Mood Not Found"),
    COLOR_IMAGE_NOT_FOUND(404,  "COLOR Not Found"),
    EXPIRED_CODE(404, "코드가 만료되었습니다."),

    RESERVATION_NOT_FOUND(404,  "방이 존재하지 않습니다."),
    BOOK_MARK_NOT_FOUND(404,  "BOOKMARK Not Found"),
    BAD_FILE_EXTENSION(404,  "FILE extension error"),
    FILE_EMPTY(404,  "FILE empty"),
    FILE_UPLOAD_FAIL(404,  "FILE upload fail"),
    CHARACTER_NOT_FOUND(404,  "Character Not Found"),
    EMAIL_SEND_FAIL(404,"Email 전송을 실패하였습니다."),
    BAD_EMAIL_ADDRESS(404, "학교 이메일 형식에 맞지 않습니다."),
    CODE_NOT_MATCHED(404, "코드가 동일하지 않습니다."),
    NOTIFICATION_RESERVATION_ALREADY_EXIST(404, "이미 해당 예약 알림이 있습니다."),

    /* 409 : CONFLICT : Resource의 현재 상태와 충돌. 보통 중복된 데이터 존재 */
    DUPLICATE_RESOURCE(409, "데이터가 이미 존재합니다."),
    INTERNAL_SERVER_ERROR(500,"서버 에러")
    ;

    private int status;
    private String reason;
}