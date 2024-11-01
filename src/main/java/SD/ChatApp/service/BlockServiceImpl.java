package SD.ChatApp.service;

import SD.ChatApp.repository.BlockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BlockServiceImpl implements BlockService {

    @Autowired
    private BlockRepository blockRepository;

    public boolean checkBlockstatus(String blockerUserId, String destinationUserId){
        if(blockRepository.findByBlockUserIdAndDestinationUserId(blockerUserId, destinationUserId).isPresent())
            return true;
        else return false;
    }
}
