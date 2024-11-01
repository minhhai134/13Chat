package SD.ChatApp.service;

import org.springframework.stereotype.Service;

@Service
public interface BlockService {
    boolean checkBlockstatus(String blockerUserId, String destinationUserId);
}
