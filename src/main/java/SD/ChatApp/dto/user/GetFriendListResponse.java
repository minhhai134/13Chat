package SD.ChatApp.dto.user;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GetFriendListResponse {
    private String relationshipId;

    private String friendId;

    private String friendName;

    public GetFriendListResponse(String relationshipId, String friendId, String friendName) {
        this.relationshipId = relationshipId;
        this.friendId = friendId;
        this.friendName = friendName;
    }


}
