package SD.ChatApp.service.file;

import com.cloudinary.*;
import io.minio.MinioClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
@Slf4j
public class UploadServiceImpl implements UploadService {
    @Autowired
    MinioClient minioClient;

    @Autowired
    private Cloudinary cloudinary;

    public String uploadFile(MultipartFile file) {
        String fileName = String.format("%s", UUID.randomUUID().toString());
       try{
           Map<String, String> options = new HashMap<>();
           Map uploadResult = cloudinary.uploader().upload(file.getBytes(), options);
           fileName = uploadResult.get("url").toString();
       } catch (Exception e) {
           log.info("Error when upload file: {}", e.toString());
           return null;
       }
       return fileName;
    }


//    public String uploadFile(MultipartFile file){
//        String fileName = String.format("%s", UUID.randomUUID().toString());
//       try{
//           InputStream inputStream = file.getInputStream();
//           minioClient.putObject(PutObjectArgs.builder().bucket("chat-app")
//                   .object(fileName)
//                   .stream(inputStream, file.getSize(), -1).build());
//       } catch (Exception e) {
//           log.info("Error when upload file: {}", e.toString());
//           return null;
//       }
//       return fileName;
//
//    }




}
