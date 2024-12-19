package SD.ChatApp.dto.conversation;

import SD.ChatApp.model.conversation.Membership;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ChangeMembershipStatusResponse {
    private Membership membership;
}
