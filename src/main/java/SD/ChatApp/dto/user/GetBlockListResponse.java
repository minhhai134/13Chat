package SD.ChatApp.dto.user;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GetBlockListResponse {
    private String blockId;

    private String destinationUserId;

    private String destinationUserName;

    public GetBlockListResponse(String blockId, String destinationUserId, String destinationUserName) {
        this.blockId = blockId;
        this.destinationUserId = destinationUserId;
        this.destinationUserName = destinationUserName;
    }
}
