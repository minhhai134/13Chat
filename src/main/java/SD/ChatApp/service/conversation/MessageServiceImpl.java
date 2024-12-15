package SD.ChatApp.service.conversation;

import SD.ChatApp.dto.message.ChatMessageReceiving;
import SD.ChatApp.dto.message.ChatMessageSending;
import SD.ChatApp.exception.user.UserNotFoundException;
import SD.ChatApp.model.User;
import SD.ChatApp.model.conversation.Conversation;
import SD.ChatApp.model.conversation.Membership;
import SD.ChatApp.model.conversation.Message;
import SD.ChatApp.model.entity.Conversation_Type;
import SD.ChatApp.model.entity.Membership_Status;
import SD.ChatApp.repository.conversation.ConversationRepository;
import SD.ChatApp.repository.conversation.MembershipRepository;
import SD.ChatApp.repository.conversation.MessageRepository;
import SD.ChatApp.repository.UserRepository;
import SD.ChatApp.service.network.BlockService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.time.Instant;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {

    private final UserRepository userRepository;
    private final ConversationRepository conversationRepository;
    private final MembershipRepository membershipRepository;
    private final MessageRepository messageRepository;
    private final BlockService blockService;


    public ChatMessageReceiving sendMessage(ChatMessageSending message, Principal userPrincipal){

        User sender = userRepository.findByUsername(userPrincipal.getName()).orElseThrow();

        User receiver = userRepository.findByUsername(message.getReceiverId()).
                orElseThrow(UserNotFoundException::new);

        if(blockService.checkBlockstatus(sender.getId(), receiver.getId())
                || blockService.checkBlockstatus(receiver.getId(), sender.getId())) {
            throw new UserNotFoundException();
        }

        /*
        New conversation
         */
        if(message.getConversationId()==null){
            Conversation newConversation = conversationRepository.save(
                    Conversation.builder().
                            type(Conversation_Type.OnetoOne).
                            build()
            );

            membershipRepository.save(
                    Membership.builder().
                            conversationId(newConversation.getId()).
                            userId(sender.getId()).
                            status(Membership_Status.ACTIVE).
                            lastSeen(Instant.now()).
                            build()
            );

            membershipRepository.save(
                    Membership.builder().
                            conversationId(newConversation.getId()).
                            userId(receiver.getId()).
                            status(Membership_Status.PENDING).
                            build()
            );

            Message savedMesssage = messageRepository.save(
                    Message.builder().
                            conversationId(newConversation.getId()).
                            senderId(sender.getId()).
                            sentTime(message.getSentTime()).
                            type(message.getType()).
                            content(message.getContent()).
                            build()
            );

            return ChatMessageReceiving.builder().
                    message(savedMesssage).receiverId(receiver.getId())
                    .build();

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

        return ChatMessageReceiving.builder().
                message(savedMesssage).receiverId(receiver.getId())
                .build();

    }

}
