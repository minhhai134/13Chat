package SD.ChatApp.dto.conversation.common;

import SD.ChatApp.enums.Membership_Status;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ChangeMembershipStatusRequest {
    private String conversationId;
    private Membership_Status newStatus;
}
