package SD.ChatApp.service.conversation;

import SD.ChatApp.dto.conversation.ChangeMembershipStatusRequest;
import SD.ChatApp.dto.conversation.CreateOneToOneConversationRequest;
import SD.ChatApp.dto.conversation.CreateOneToOneConversationResponse;
import SD.ChatApp.dto.conversation.GetOneToOneConversationListResponse;
import SD.ChatApp.exception.user.UserNotFoundException;
import SD.ChatApp.model.User;
import SD.ChatApp.model.conversation.Conversation;
import SD.ChatApp.model.conversation.Membership;
import SD.ChatApp.model.enums.Conversation_Type;
import SD.ChatApp.model.enums.Membership_Status;
import SD.ChatApp.repository.UserRepository;
import SD.ChatApp.repository.conversation.ConversationRepository;
import SD.ChatApp.repository.conversation.MembershipRepository;
import SD.ChatApp.service.network.BlockService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ConversationServiceImpl implements ConversationService {

    private final ConversationRepository conversationRepository;
    private final UserRepository userRepository;
    private final BlockService blockService;
    private final MembershipRepository membershipRepository;

    public CreateOneToOneConversationResponse createOneToOneConversation(
            Principal principal,
            CreateOneToOneConversationRequest request
    ){
        User user = userRepository.findByUsername(principal.getName()).orElseThrow();
        User friend = userRepository.findByUsername(request.getFriendId()).
                orElseThrow(UserNotFoundException::new);

        if(blockService.checkBlockstatus(user.getId(), friend.getId())
                || blockService.checkBlockstatus(friend.getId(), user.getId())) {
            throw new UserNotFoundException();
        }

        Conversation newConversation = conversationRepository.save(
                Conversation.builder().
                        type(Conversation_Type.OneToOne).
                        lastActive(request.getLastActive()).
                        build()
        );

        Membership myMembership = membershipRepository.save(
                Membership.builder().
                        conversationId(newConversation.getId()).
                        userId(user.getId()).
                        status(Membership_Status.ACTIVE).
                        lastSeen(Instant.now()).
                        build()
        );

        Membership friendMembership = membershipRepository.save(
                Membership.builder().
                        conversationId(newConversation.getId()).
                        userId(friend.getId()).
                        status(Membership_Status.PENDING).
                        build()
        );
        log.info("Friend Membership: {}", friendMembership);


        return CreateOneToOneConversationResponse.builder()
                .conversation(newConversation)
                .membership(myMembership)
                .build();
    }

    public List<GetOneToOneConversationListResponse> getConversationList(
            Principal principal,
            Membership_Status membershipStatus){
        User user = userRepository.findByUsername(principal.getName()).orElseThrow();

        log.info("Membership: {}", membershipStatus);

//        String memberShip_status = membershipStatus.name();

        return conversationRepository.GetOnetoOneConversationList(user.getId(), membershipStatus);
    }

    public Membership changeMembershipStatus(
            Principal principal,
            ChangeMembershipStatusRequest request){
        log.info("Request: {}", request);
        Membership membership = membershipRepository.findById(request.getMembershipId()).orElseThrow();
        membership.setStatus(request.getNewStatus());
        log.info("Membership: {}", membership);
        return membershipRepository.save(membership);

    }
}
