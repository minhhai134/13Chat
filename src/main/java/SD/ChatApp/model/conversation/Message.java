package SD.ChatApp.model.conversation;

import SD.ChatApp.model.entity.Message_Type;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;

@Entity
@Table(name="messages")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String conversationId;

    private String senderId;

    private Instant sentTime;

    private Message_Type type;

    private String content;

}
