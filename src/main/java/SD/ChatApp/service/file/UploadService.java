package SD.ChatApp.service.file;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public interface UploadService {
    String uploadFile(MultipartFile file);
}
