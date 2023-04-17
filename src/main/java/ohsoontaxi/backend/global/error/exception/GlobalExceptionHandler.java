package ohsoontaxi.backend.global.error.exception;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import ohsoontaxi.backend.global.error.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.util.UriComponentsBuilder;
import java.io.IOException;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(OhSoonException.class)
    public ResponseEntity<ErrorResponse> bookMarkExceptionHandler(
            OhSoonException e, HttpServletRequest request) {

        ErrorCode code = e.getErrorCode();
        ErrorResponse errorResponse =
                new ErrorResponse(
                        code.getStatus(),
                        code.getReason(),
                        request.getRequestURL().toString());
        return ResponseEntity.status(HttpStatus.valueOf(code.getStatus())).body(errorResponse);
    }


    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ErrorResponse> handleException(Exception e, HttpServletRequest request)
            throws IOException {
        String url =
                UriComponentsBuilder.fromHttpRequest(new ServletServerHttpRequest(request))
                        .build()
                        .toUriString();

        log.error("INTERNAL_SERVER_ERROR", e);
        ErrorCode internalServerError = ErrorCode.INTERNAL_SERVER_ERROR;
        ErrorResponse errorResponse =
                new ErrorResponse(
                        internalServerError.getStatus(),
                        internalServerError.getReason(),
                        url);

        return ResponseEntity.status(HttpStatus.valueOf(internalServerError.getStatus()))
                .body(errorResponse);
    }

}