package SD.ChatApp.dto.conversation.group;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AddMemberResponse {
    private String memberId;
    private String memberName;
    private String memberAvt;
}
