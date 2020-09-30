package kloud.backend.util;

import io.minio.*;
import io.minio.errors.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Service
public class MinioUtil {
    @Value("http://${minioIP}:30900")
    private String endpoint;

    @Bean
    public MinioClient newMinioClient() {
        return MinioClient.builder()
                .endpoint(endpoint)
                .credentials("minioadmin", "minioadmin")
                .build();
    }



    @Autowired
    private MinioClient minioClient;

    private static String config = "{\n" +
            "     \"Statement\": [\n" +
            "         {\n" +
            "             \"Action\": [\n" +
            "                 \"s3:GetBucketLocation\",\n" +
            "                 \"s3:ListBucket\"\n" +
            "             ],\n" +
            "             \"Effect\": \"Allow\",\n" +
            "             \"Principal\": \"*\",\n" +
            "             \"Resource\": \"arn:aws:s3:::buckName\"\n" +
            "         },\n" +
            "         {\n" +
            "             \"Action\": \"s3:GetObject\",\n" +
            "             \"Effect\": \"Allow\",\n" +
            "             \"Principal\": \"*\",\n" +
            "             \"Resource\": \"arn:aws:s3:::buckName/*\"\n" +
            "         }\n" +
            "     ],\n" +
            "     \"Version\": \"2012-10-17\"\n" +
            " }";
    private static String replace = "buckName";

    //null if failed
    public String upload(String bucketName, MultipartFile multipartFile, String fileName) {
        try {
            InputStream inputStream = multipartFile.getInputStream();
            if (!minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build())) {
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
                //设置桶策略
                minioClient.setBucketPolicy(
                                SetBucketPolicyArgs.builder()
                                        .bucket(bucketName)
                                        .config(config.replaceAll(replace, bucketName))
                                        .build());
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
            ignored.printStackTrace();
        }
        return null;
    }

    //null if any exception
    public String getUrl(String bucketName, String filename) {
        try {
            return minioClient.getObjectUrl(bucketName, filename);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

//    public boolean exits() {
//        minioClient.
//    }

    public void removeObject(String buckName, String fileName) {
        try {
            minioClient.removeObject(RemoveObjectArgs.builder().bucket(buckName).object(fileName).build());
        } catch (ErrorResponseException e) {
            e.printStackTrace();
        } catch (InsufficientDataException e) {
            e.printStackTrace();
        } catch (InternalException e) {
            e.printStackTrace();
        } catch (InvalidBucketNameException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (InvalidResponseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (XmlParserException e) {
            e.printStackTrace();
        }
    }

    public void removeBucket(String buckName) {
        try {
            if(minioClient.bucketExists(BucketExistsArgs.builder().bucket(buckName).build())) {
                minioClient.removeBucket(RemoveBucketArgs.builder().bucket(buckName).build());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
