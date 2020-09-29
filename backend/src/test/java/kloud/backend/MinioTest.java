package kloud.backend;

import io.minio.*;
import io.minio.errors.*;
import io.minio.messages.Item;
import kloud.backend.util.MinioUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@SpringBootTest
public class MinioTest {


    @Value("http://${minioIP}:30900")
    private String endpoint;

    private MinioClient newMinioClient() {
        return MinioClient.builder()
                .endpoint(endpoint)
                .credentials("minioadmin", "minioadmin")
                .build();
    }

    @Test
    public void test1() throws IOException, InvalidKeyException, InvalidResponseException, RegionConflictException, InsufficientDataException, NoSuchAlgorithmException, ServerException, InternalException, XmlParserException, InvalidBucketNameException, ErrorResponseException {
        MinioClient minioClient = newMinioClient();
        minioClient.makeBucket(MakeBucketArgs.builder().bucket("test2").build());
    }

    @Test
    public void test2() throws IOException, InvalidKeyException, InvalidResponseException, InsufficientDataException, NoSuchAlgorithmException, ServerException, InternalException, XmlParserException, InvalidBucketNameException, ErrorResponseException {
        MinioClient minioClient = newMinioClient();
        Path path = Paths.get("F:", "1.jpg");
        InputStream inputStream = Files.newInputStream(path);
        minioClient
                .putObject(PutObjectArgs.builder()
                        .bucket("test2")
                        .region("region1")
                        .object(path.toFile().getName())
                        .stream(inputStream, path.toFile().length(), PutObjectOptions.MIN_MULTIPART_SIZE)
                        .build());
    }

    @Test
    public void test3() throws IOException, InvalidKeyException, InvalidResponseException, InsufficientDataException, NoSuchAlgorithmException, ServerException, InternalException, XmlParserException, InvalidBucketNameException, ErrorResponseException {
        MinioClient minioClient = newMinioClient();
        String url = minioClient.getObjectUrl("test2", "Diagram1.png");
        System.out.println(url);
    }

    @Test
    public void test4() throws IOException, InvalidKeyException, InvalidResponseException, BucketPolicyTooLargeException, NoSuchAlgorithmException, ServerException, InternalException, XmlParserException, InvalidBucketNameException, InsufficientDataException, ErrorResponseException {
        MinioClient minioClient = newMinioClient();
        String config = ("{\n" +
                "     \"Statement\": [\n" +
                "         {\n" +
                "             \"Action\": [\n" +
                "                 \"s3:GetBucketLocation\",\n" +
                "                 \"s3:ListBucket\"\n" +
                "             ],\n" +
                "             \"Effect\": \"Allow\",\n" +
                "             \"Principal\": \"*\",\n" +
                "             \"Resource\": \"arn:aws:s3:::myBucket\"\n" +
                "         },\n" +
                "         {\n" +
                "             \"Action\": \"s3:GetObject\",\n" +
                "             \"Effect\": \"Allow\",\n" +
                "             \"Principal\": \"*\",\n" +
                "             \"Resource\": \"arn:aws:s3:::myBucket/*\"\n" +
                "         }\n" +
                "     ],\n" +
                "     \"Version\": \"2012-10-17\"\n" +
                " }").replaceAll("myBucket", "test2");
        System.out.println(config);
//        minioClient.setBucketPolicy(SetBucketPolicyArgs.builder().bucket("test2").config(config).build());
//        String test2 = minioClient.getBucketPolicy(GetBucketPolicyArgs.builder().bucket("test2").build());
//        System.out.println(test2);
    }


    @Autowired
    private MinioUtil minioUtil;



}


