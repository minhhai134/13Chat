package SD.ChatApp.dto.conversation;

import SD.ChatApp.model.enums.Membership_Status;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ChangeMembershipStatusRequest {
    private String membershipId;
    private Membership_Status newStatus;
}
