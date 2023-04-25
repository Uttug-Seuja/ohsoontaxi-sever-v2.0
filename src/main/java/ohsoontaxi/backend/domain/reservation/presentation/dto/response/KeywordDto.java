package ohsoontaxi.backend.domain.reservation.presentation.dto.response;


import lombok.Getter;
import lombok.NoArgsConstructor;
import ohsoontaxi.backend.domain.reservation.domain.vo.ReservationBaseInfoVo;

@Getter
@NoArgsConstructor
public class KeywordDto {
    private String keyword;
    public KeywordDto(ReservationBaseInfoVo reservationBaseInfoVo) {
        keyword = reservationBaseInfoVo.getTitle();
    }
}
