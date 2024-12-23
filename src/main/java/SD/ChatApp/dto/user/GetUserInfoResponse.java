package SD.ChatApp.dto.user;

import SD.ChatApp.model.enums.Relationship_Type;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GetUserInfoResponse {
    private String userId;

    private String name;

    private Relationship_Type relationship;
}
