package SD.ChatApp.service.conversation;

import SD.ChatApp.dto.conversation.CreateOneToOneConversationRequest;
import SD.ChatApp.dto.conversation.CreateOneToOneConversationResponse;
import SD.ChatApp.dto.conversation.GetOneToOneConversationListResponse;
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
}
