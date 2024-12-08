package SD.ChatApp.service.network;

import SD.ChatApp.model.network.Block;
import org.springframework.stereotype.Service;

@Service
public interface BlockService {
    boolean checkBlockstatus(String blockerUserId, String destinationUserId);

    Block block(String blockerUserId, String destinationUserId);

    void unBlock(String blockId);
}
