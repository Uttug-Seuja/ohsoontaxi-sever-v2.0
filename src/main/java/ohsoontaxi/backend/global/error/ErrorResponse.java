package ohsoontaxi.backend.global.error;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ErrorResponse {

    private final boolean success = false;
    private final int status;
    private final String reason;
    private final LocalDateTime timeStamp;
    private final String path;


    public ErrorResponse(int status, String reason, String path) {
        this.status = status;
        this.reason = reason;
        this.timeStamp = LocalDateTime.now();
        this.path = path;
    }
}