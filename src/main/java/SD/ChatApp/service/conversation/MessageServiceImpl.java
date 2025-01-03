package SD.ChatApp.service.conversation;

import SD.ChatApp.dto.websocket.message.ChatMessageReceiving;
import SD.ChatApp.dto.websocket.message.ChatMessageSending;
import SD.ChatApp.exception.conversation.GroupNotFoundException;
import SD.ChatApp.exception.user.UserNotFoundException;
import SD.ChatApp.model.User;
import SD.ChatApp.model.conversation.Conversation;
import SD.ChatApp.model.conversation.Membership;
import SD.ChatApp.model.conversation.Message;
import SD.ChatApp.model.enums.Conversation_Type;
import SD.ChatApp.model.enums.Membership_Status;
import SD.ChatApp.repository.conversation.ConversationRepository;
import SD.ChatApp.repository.conversation.MembershipRepository;
import SD.ChatApp.repository.conversation.MessageRepository;
import SD.ChatApp.repository.UserRepository;
import SD.ChatApp.service.file.UploadService;
import SD.ChatApp.service.network.BlockService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class MessageServiceImpl implements MessageService {

    private final UserRepository userRepository;
    private final ConversationRepository conversationRepository;
    private final MembershipRepository membershipRepository;
    private final MessageRepository messageRepository;
    private final BlockService blockService;
    private final UploadService uploadService;

    private Message saveMessage(User sender, ChatMessageSending message){
        Message savedMesssage = messageRepository.save(
                Message.builder().
                        conversationId(message.getConversationId()).
                        senderId(sender.getId()).
                        sentTime(message.getSentTime()).
                        type(message.getMessageType()).
                        content(message.getContent()).
                        build()
        );

        /*
        Update Membership_Status if needed
         */
        log.info("Message:{}", message);
//        if(message.getMembershipStatus()==Membership_Status.PENDING){
            Membership membership = membershipRepository.findByConversationIdAndUserId(
                    message.getConversationId(), sender.getId()).orElseThrow();
            membership.setStatus(Membership_Status.ACTIVE);
            membershipRepository.save(membership);
//        }

        /*
        Update Conversation's lastActive and lastMessageId
         */
        Conversation conversation = conversationRepository.findById(message.getConversationId()).orElseThrow();
        conversation.setLastActive(message.getSentTime());
        conversation.setLastMessageID(savedMesssage.getId());
        conversation.setLastMessageContent(savedMesssage.getContent());
        conversationRepository.save(conversation);

        return savedMesssage;
    }

    private String checkMessageDestination(User sender, ChatMessageSending message) throws RuntimeException{
        if(message.getConversationType()== Conversation_Type.OneToOne){
            User receiver = userRepository.findById(message.getDestinationId()).
                    orElseThrow(UserNotFoundException::new);
            if(blockService.checkBlockstatus(sender.getId(), receiver.getId())
                    || blockService.checkBlockstatus(receiver.getId(), sender.getId())) {
                throw new UserNotFoundException();
            }
            return receiver.getId();

        }
        else if(message.getConversationType()== Conversation_Type.Group){
            Conversation groupConversation = conversationRepository.findById(message.getDestinationId()).
                    orElseThrow(GroupNotFoundException::new);

            membershipRepository.findByConversationIdAndUserId(
                    message.getConversationId(), sender.getId()).orElseThrow(GroupNotFoundException::new);

            return groupConversation.getId();
        }
        else return null;
    }

    public ChatMessageReceiving sendMessage(
            Principal principal,
            ChatMessageSending message){

        User sender = userRepository.findByUsername(principal.getName()).get();

//        User receiver = userRepository.findByUsername(message.getReceiverId()).
//                orElseThrow(UserNotFoundException::new);
        String destinationId = checkMessageDestination(sender, message);
        if(destinationId==null) return null;
        log.info("Message: {}", message);


        // Handle invalid message:


        /*
        Existed conversation
         */

        // Need to CHECK MEMBERSHIP
        Message savedMesssage = saveMessage(sender, message);

        return ChatMessageReceiving.builder().
                message(savedMesssage).
                destinationId(destinationId).
                build();
    }

    public List<Message> getMessages(Principal principal, String conversationId, long pivotId){

        return messageRepository.getMessage(conversationId, pivotId);
    }

    public ChatMessageReceiving sendFile(
            Principal principal,
            ChatMessageSending message,
            MultipartFile file){

        User sender = userRepository.findByUsername(principal.getName()).get();

        String destinationId = checkMessageDestination(sender, message);

        log.info("Message: {}", message);
        // Handle invalid message:

        /*
        Existed conversation
         */

        // Need to CHECK MEMBERSHIP
        Message savedMesssage = saveMessage(sender, message);
        String fileName = uploadService.uploadFile(file);
        if(file.isEmpty()){ throw new RuntimeException("File uploading error"); }
        savedMesssage.setContent(fileName);
        messageRepository.save(savedMesssage);

        return ChatMessageReceiving.builder().
                message(savedMesssage).destinationId(destinationId)
                .build();
    }
}
