package SD.ChatApp.dto.conversation.onetoone;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@Builder
public class CreateOneToOneConversationRequest {
    private String friendId;
    private Instant lastActive;
}
