package SD.ChatApp.dto.conversation.group;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
//@AllArgsConstructor
public class GetGroupMemberResponse {
//    private String conversationId;
    private String memberId;
    private String memberName;

    public GetGroupMemberResponse(String memberId, String memberName, String memberAvt) {
        this.memberId = memberId;
        this.memberName = memberName;
        this.memberAvt = memberAvt;
    }

    private String memberAvt;
}
