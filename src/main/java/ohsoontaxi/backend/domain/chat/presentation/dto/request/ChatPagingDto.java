package ohsoontaxi.backend.domain.chat.presentation.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class ChatPagingDto {

    private String  message;
    private String  writer;
    private String  cursor;
    private Long userId;
}