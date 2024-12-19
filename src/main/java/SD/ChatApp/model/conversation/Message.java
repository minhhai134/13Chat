package SD.ChatApp.model.conversation;

import SD.ChatApp.model.enums.Message_Status;
import SD.ChatApp.model.enums.Message_Type;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Entity
@Table(name="messages")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String conversationId;

    private String senderId;

    private Instant sentTime;

    private Message_Type type;

    private String content;

    private Message_Status messageStatus;

}
