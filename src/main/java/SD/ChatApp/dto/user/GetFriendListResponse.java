package SD.ChatApp.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
//@AllArgsConstructor
public class GetFriendListResponse {
    private String relationshipId;

    private String friendId;

    private String friendName;

    private String friendAvt;

    public GetFriendListResponse(String relationshipId, String friendId, String friendName, String friendAvt) {
        this.relationshipId = relationshipId;
        this.friendId = friendId;
        this.friendName = friendName;
        this.friendAvt = friendAvt;
    }


}
