package kloud.backend.util;

import io.minio.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

@Service
public class MinioUtil {
    @Value("http://${minioIP}:30900")
    private String endpoint;

    private MinioClient newMinioClient() {
        return MinioClient.builder()
                .endpoint(endpoint)
                .credentials("minioadmin", "minioadmin")
                .build();
    }

    //null if failed
    public String upload(String bucketName, MultipartFile multipartFile, String fileName) {
        try {
            InputStream inputStream = multipartFile.getInputStream();
            MinioClient minioClient = newMinioClient();
            if (!minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build())) {
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
            }
            minioClient.putObject(PutObjectArgs.builder()
                    .bucket(bucketName).object(fileName)
                    // 文件的ContentType
                    .contentType(multipartFile.getContentType())
                    // 上传配置(文件大小，内存中文件分片大小)
                    .stream(inputStream, multipartFile.getSize(), PutObjectOptions.MIN_MULTIPART_SIZE)
                    .build());
            return minioClient.getObjectUrl(bucketName, fileName);
        } catch (Exception ignored) {
            return null;
        }
    }

    //null if any exception
    public String getUrl(String bucketName, String filename) {
        try {
            return newMinioClient().getObjectUrl(bucketName, filename);
        } catch (Exception e) {
            return null;
        }
    }
}
