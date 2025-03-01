package SD.ChatApp.service.videocall;

import SD.ChatApp.dto.websocket.conversation.InComingCallNotification;
import SD.ChatApp.enums.Notification_Type;
import SD.ChatApp.model.User;
import SD.ChatApp.repository.UserRepository;
import SD.ChatApp.repository.conversation.ConversationRepository;
import io.getstream.exceptions.StreamException;
import io.getstream.models.framework.StreamResponse;
import io.getstream.services.Call;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
//import io.getstream.services.VideoService;
import io.getstream.services.framework.StreamSDKClient;
import io.getstream.models.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class VideoCallService {

    @Autowired
    private StreamSDKClient streamSDKClient;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ConversationRepository conversationRepository;
    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    private final String CALL_TYPE = "default";


    public String createCall(Principal principal, String conversationId) throws StreamException {
        User user = userRepository.findByUsername(principal.getName()).orElseThrow();

        List<MemberRequest> members = new ArrayList<>();
        conversationRepository.getMemberList(conversationId).
                forEach(member -> {
                    members.add(MemberRequest.builder().userID(member.getMemberId()).build());
                });

        Call call = new Call(CALL_TYPE, UUID.randomUUID().toString(), streamSDKClient.video());
        StreamResponse<GetOrCreateCallResponse> response = call.getOrCreate(
                GetOrCreateCallRequest.builder()
                        .data(
                                CallRequest.builder()
                                        .createdByID(user.getId())
                                        .members(members)
                                        .build()
                        )
                        .notify(true)
//                        .ring(true)
                        .build()
        );
        // Send websocket notification
        InComingCallNotification notification = InComingCallNotification.builder()
                .notificationType(Notification_Type.INCOMING_CALL)
                .callId(response.getData().getCall().getId())
                .conversationId(conversationId)
                .build();
        members.forEach(member -> {
            if(!member.getUserID().equals(user.getId())){
                User friend = userRepository.findById(member.getUserID()).orElseThrow(); // PHẢI SỬA CÁCH ĐỊNH DANH CỦA SPRING SECURITY
            messagingTemplate.convertAndSendToUser(
                    friend.getUsername(), "/queue/messages", notification);}
        });


        return response.getData().getCall().getId();
    }


    public void updateUser(Principal principal) throws StreamException {
        User user = userRepository.findByUsername(principal.getName()).orElseThrow();
        streamSDKClient.updateUsers(
           UpdateUsersRequest.builder()
                   .users(
                     Map.of(
                       user.getId(),
                       UserRequest.builder()
                               .id(user.getId())
                               .name(user.getName())
//                               .custom(Map.of("country", "NL"))
                               .image(user.getAvatar())
                               .build())).build()).execute();
    }

    public String generateStreamUserToken(Principal principal){
        User user = userRepository.findByUsername(principal.getName()).orElseThrow();
        return streamSDKClient.tokenBuilder().createToken(user.getId());
    }








}
