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

    private Conversation_Type conversationType;

    private Instant conversationLastActive;

    private long lastMessageID;

    private String lastMessageContent;

    private Instant lastSeen;

    private String membershipId;

    private String adminId;

    private String groupName;

}
