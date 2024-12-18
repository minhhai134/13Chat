package SD.ChatApp.dto.block;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BlockRequest {
    private String destinationUserId;
}
