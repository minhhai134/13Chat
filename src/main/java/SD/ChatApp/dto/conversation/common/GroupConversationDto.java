package SD.ChatApp.dto.conversation.common;

import SD.ChatApp.model.enums.Conversation_Type;
import SD.ChatApp.model.enums.Membership_Status;
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

    private Membership_Status membershipStatus;

}
