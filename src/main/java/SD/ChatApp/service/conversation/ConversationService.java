package SD.ChatApp.service.conversation;

import SD.ChatApp.dto.conversation.*;
import SD.ChatApp.model.conversation.Membership;
import SD.ChatApp.model.enums.Membership_Status;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

@Service
public interface ConversationService {

    CreateOneToOneConversationResponse createOneToOneConversation(
            Principal principal,
            CreateOneToOneConversationRequest request);

    List<GetOneToOneConversationListResponse> getConversationList(
            Principal principal,
            Membership_Status membershipStatus);

     Membership changeMembershipStatus(
            Principal principal,
            ChangeMembershipStatusRequest request);

    CreateGroupResponse createGroupConversation(
            Principal principal,
            CreateGroupRequest request);
}
