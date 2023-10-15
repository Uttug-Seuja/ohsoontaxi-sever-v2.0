package ohsoontaxi.backend.domain.chat.domain.repository;



import ohsoontaxi.backend.domain.chat.domain.Chat;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ChatRepository extends JpaRepository<Chat,Long> {
    Slice<Chat> findAllByCreatedAtBeforeAndReservationIdOrderByCreatedAtDesc(String cursorCreatedAt, Long reservationId, Pageable pageable);
    List<Chat> findAllByCreatedAtAfterOrderByCreatedAtDesc(String cursorCreatedAt);
    List<Chat> findAllByReservationIsNotNullAndCreatedAtAfterOrderByCreatedAtDesc(String cursorCreatedAt);
    @Modifying(flushAutomatically = true)
    @Query("update Chat c set c.reservation = null where c.reservation.id = :reservationId")
    void updateReservationNull(@Param("reservationId") Long reservationId);
}