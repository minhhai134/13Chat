package SD.ChatApp.service.conversation;

import SD.ChatApp.model.conversation.Message;
import SD.ChatApp.model.enums.Message_Type;
import SD.ChatApp.service.conversation.MessageServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.security.Principal;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

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
import SD.ChatApp.service.network.BlockService;

@SpringBootTest
class MessageServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private ConversationRepository conversationRepository;

    @Mock
    private MembershipRepository membershipRepository;

    @Mock
    private MessageRepository messageRepository;

    @Mock
    private BlockService blockService;

    @InjectMocks
    private MessageServiceImpl messageService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void sendMessage_shouldSaveMessageAndReturnReceivingObject() {
        // Arrange
        Principal principal = mock(Principal.class);
        when(principal.getName()).thenReturn("senderUsername");

        User sender = new User();
        sender.setId("senderId");
        when(userRepository.findByUsername("senderUsername")).thenReturn(Optional.of(sender));

        ChatMessageSending message = ChatMessageSending.builder()
                .conversationId("conversationId")
                .sentTime(Instant.now())
                .messageType(Message_Type.TEXT_MESSAGE)
                .content("Hello World")
                .conversationType(Conversation_Type.OneToOne)
                .destinationId("receiverUsername")
                .build();

        User receiver = new User();
        receiver.setId("receiverId");
        when(userRepository.findByUsername("receiverUsername")).thenReturn(Optional.of(receiver));
        when(blockService.checkBlockstatus("senderId", "receiverId")).thenReturn(false);

        Message savedMessage = new Message();
        savedMessage.setId((long)1);
        savedMessage.setConversationId("conversationId");
        savedMessage.setContent("Hello World");
        when(messageRepository.save(any(Message.class))).thenReturn(savedMessage);

        Conversation conversation = new Conversation();
        conversation.setId("conversationId");
        when(conversationRepository.findById("conversationId")).thenReturn(Optional.of(conversation));

        // Act
        ChatMessageReceiving result = messageService.sendMessage(principal, message);

        // Assert
        assertNotNull(result);
        assertEquals("receiverId", result.getDestinationId());
        assertEquals("messageId", result.getMessage().getId());

        verify(messageRepository, times(1)).save(any(Message.class));
        verify(conversationRepository, times(1)).save(conversation);
    }

    @Test
    void sendMessage_shouldThrowExceptionWhenSenderNotFound() {
        // Arrange
        Principal principal = mock(Principal.class);
        when(principal.getName()).thenReturn("unknownUser");
        when(userRepository.findByUsername("unknownUser")).thenReturn(Optional.empty());

        ChatMessageSending message = ChatMessageSending.builder().build();

        // Act & Assert
        assertThrows(UsernameNotFoundException.class, () ->
                messageService.sendMessage(principal, message));

        verify(userRepository, times(1)).findByUsername("unknownUser");
    }

    @Test
    void getMessages_shouldReturnMessages() {
        // Arrange
        Principal principal = mock(Principal.class);
        String conversationId = "conversationId";
        long pivotId = 100L;

        Message message1 = new Message();
        message1.setId((long)1);
        Message message2 = new Message();
        message2.setId((long)2);

        when(messageRepository.getMessage(conversationId, pivotId))
                .thenReturn(List.of(message1, message2));

        // Act
        List<Message> result = messageService.getMessages(principal, conversationId, pivotId);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(messageRepository, times(1)).getMessage(conversationId, pivotId);
    }
}
