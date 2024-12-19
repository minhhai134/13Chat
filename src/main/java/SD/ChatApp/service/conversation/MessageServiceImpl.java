package SD.ChatApp.service.conversation;

import SD.ChatApp.dto.message.ChatMessageReceiving;
import SD.ChatApp.dto.message.ChatMessageSending;
import SD.ChatApp.exception.user.UserNotFoundException;
import SD.ChatApp.model.User;
import SD.ChatApp.model.conversation.Conversation;
import SD.ChatApp.model.conversation.Membership;
import SD.ChatApp.model.conversation.Message;
import SD.ChatApp.model.enums.Membership_Status;
import SD.ChatApp.repository.conversation.ConversationRepository;
import SD.ChatApp.repository.conversation.MembershipRepository;
import SD.ChatApp.repository.conversation.MessageRepository;
import SD.ChatApp.repository.UserRepository;
import SD.ChatApp.service.network.BlockService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
@RequiredArgsConstructor
@Slf4j
public class MessageServiceImpl implements MessageService {

    private final UserRepository userRepository;
    private final ConversationRepository conversationRepository;
    private final MembershipRepository membershipRepository;
    private final MessageRepository messageRepository;
    private final BlockService blockService;


    public ChatMessageReceiving sendMessage(ChatMessageSending message, Principal principal){

        User sender = userRepository.findByUsername(principal.getName()).orElseThrow();

        User receiver = userRepository.findByUsername(message.getReceiverId()).
                orElseThrow(UserNotFoundException::new);

        log.info("Message: {}", message);


        // Handle invalid message:

        if(blockService.checkBlockstatus(sender.getId(), receiver.getId())
                || blockService.checkBlockstatus(receiver.getId(), sender.getId())) {
            throw new UserNotFoundException();
        }

        /*
        Existed conversation
         */

        // Need to CHECK MEMBERSHIP

        Message savedMesssage = messageRepository.save(
                Message.builder().
                        conversationId(message.getConversationId()).
                        senderId(sender.getId()).
                        sentTime(message.getSentTime()).
                        type(message.getType()).
                        content(message.getContent()).
                        build()
        );

        /*
        Update Membership_Status if needed
         */
        if(message.getMembershipStatus()==Membership_Status.PENDING){
            Membership membership = membershipRepository.findByConversationIdAndUserId(
                    message.getConversationId(), sender.getId()).orElseThrow();
            membership.setStatus(Membership_Status.ACTIVE);
            membershipRepository.save(membership);
        }

        /*
        Update Conversation's lastActive
         */
        Conversation conversation = conversationRepository.findById(message.getConversationId()).orElseThrow();
        conversation.setLastActive(message.getSentTime());
        conversationRepository.save(conversation);

        return ChatMessageReceiving.builder().
                message(savedMesssage).receiverId(receiver.getId())
                .build();

    }

}
