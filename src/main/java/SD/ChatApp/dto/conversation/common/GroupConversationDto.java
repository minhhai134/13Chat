package SD.ChatApp.dto.conversation.common;

import SD.ChatApp.model.enums.Conversation_Type;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;

@Data
@Builder
@AllArgsConstructor
public class GroupConversationDto {
    private String conversationId;

    private Conversation_Type type;

    private Instant lastActive;

    private Instant lastSeen;

    private String membershipId;

    private String groupName;

}
