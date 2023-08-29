package ohsoontaxi.backend.domain.chat.domain.repository;




import lombok.RequiredArgsConstructor;
import ohsoontaxi.backend.domain.chat.domain.Chat;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ChatJdbcRepository {

    private final JdbcTemplate jdbcTemplate;

    public void batchInsertRoomInventories(List<Chat> chatList){

        String sql = "INSERT INTO chats"
                +  "(message,user_id,participation_id,user_name,reservation_id,created_at) VALUE(?,?,?,?,?,?)";


        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                Chat chat = chatList.get(i);
                ps.setString(1,chat.getMessage());
                ps.setLong(2,chat.getUserId());
                ps.setLong(3,chat.getParticipationId());
                ps.setString(4,chat.getUserName());
                ps.setLong(5,chat.getReservation().getId());
                ps.setString(6,chat.getCreatedAt());

            }

            @Override
            public int getBatchSize() {
                return chatList.size();
            }
        });
    }

}