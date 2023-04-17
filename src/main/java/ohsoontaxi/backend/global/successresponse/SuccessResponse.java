package ohsoontaxi.backend.global.successresponse;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class SuccessResponse {

    private final boolean success = true;
    private final int status;
    private final Object data;
    private final LocalDateTime timeStamp;

    public SuccessResponse(int status, Object data) {
        this.status = status;
        this.data = data;
        this.timeStamp = LocalDateTime.now();
    }

}