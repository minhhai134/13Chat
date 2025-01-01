package SD.ChatApp.service.minio;

import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.UUID;

@Service
@Slf4j
public class UploadServiceImpl implements UploadService {
    @Autowired
    MinioClient minioClient;

    public String uploadFile(MultipartFile file){
        String fileName = String.format("%s", UUID.randomUUID().toString());
       try{
           InputStream inputStream = file.getInputStream();
           minioClient.putObject(PutObjectArgs.builder().bucket("chat-app")
                   .object(fileName)
                   .stream(inputStream, file.getSize(), -1).build());
       } catch (Exception e) {
           log.info("Error when upload file: {}", e.toString());
           return null;
       }
       return fileName;

    }
}
