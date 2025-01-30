package SD.ChatApp.model.conversation;

import SD.ChatApp.enums.Membership_Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Entity
@Table(name="memberships")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Membership {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String conversationId;

    private String userId;

    private Membership_Status status;

    private Instant lastSeen;

}
