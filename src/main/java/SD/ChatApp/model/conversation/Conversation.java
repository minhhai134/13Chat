package SD.ChatApp.model.conversation;

import SD.ChatApp.model.enums.Conversation_Type;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Entity
@Table(name = "conversations")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Conversation {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private Conversation_Type type;

    private long lastMessageID;

    private Instant lastActive;

}
