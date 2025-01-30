package SD.ChatApp.service.conversation;

import SD.ChatApp.dto.conversation.common.ChangeMembershipStatusRequest;
import SD.ChatApp.dto.conversation.common.GetConversationListResponse;
import SD.ChatApp.dto.conversation.group.*;
import SD.ChatApp.dto.conversation.onetoone.CreateOneToOneConversationRequest;
import SD.ChatApp.dto.conversation.onetoone.CreateOneToOneConversationResponse;
import SD.ChatApp.model.conversation.Membership;
import SD.ChatApp.enums.Membership_Status;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

@Service
public interface ConversationService {

    CreateOneToOneConversationResponse createOneToOneConversation(
            Principal principal,
            CreateOneToOneConversationRequest request);

    GetConversationListResponse getConversationList(
            Principal principal,
            Membership_Status membershipStatus);

     Membership changeMembershipStatus(
            Principal principal,
            ChangeMembershipStatusRequest request);

    CreateGroupResponse createGroupConversation(
            Principal principal,
            CreateGroupRequest request);

    AddMemberResponse addMember(
            Principal principal,
            AddMemberRequest request);

    LeaveGroupResponse leaveGroup(
            Principal principal,
            LeaveGroupRequest request);

    DeleteMemberResponse deleteMember(
            Principal principal,
            DeleteMemberRequest request);

    List<GetGroupMemberResponse> getMemberList(
            Principal principal,
            String conversationId);

}
