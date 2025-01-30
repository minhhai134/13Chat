package SD.ChatApp.dto.block;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UnblockRequest {
    private String blockId;
    private String destinationUserId;
}
