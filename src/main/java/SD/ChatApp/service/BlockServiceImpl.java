package SD.ChatApp.service;

import SD.ChatApp.dto.block.BlockRequest;
import SD.ChatApp.model.Block;
import SD.ChatApp.repository.BlockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BlockServiceImpl implements BlockService {

    @Autowired
    private BlockRepository blockRepository;

    public boolean checkBlockstatus(String blockerUserId, String destinationUserId) {
        return blockRepository.findByBlockerUserIdAndDestinationUserId(blockerUserId, destinationUserId).isPresent();
    }

    public Block block(String blockerUserId, String destinationUserId) {
        return blockRepository.save(Block.builder().
                blockerUserId(blockerUserId).
                destinationUserId(destinationUserId).
                build());
    }

    public void unBlock(String blockId) {
        blockRepository.deleteById(blockId);
    }
}
