package com.oniesoft.serviceimpl;

import com.oniesoft.service.S3Service;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.io.File;

@Service
public class S3ServiceImpl implements S3Service {

    private final S3Client s3Client;

    @Value("${aws.s3.bucket}")
    private String bucketName;

    public S3ServiceImpl(S3Client s3Client) {
        this.s3Client = s3Client;
    }

    // Upload an image
    public void uploadFile(String key, File file) {
        try {
            PutObjectRequest request = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(key)
                    .build();

            s3Client.putObject(request, file.toPath());
//            System.out.println("File uploaded to S3 successfully!");
        } catch (S3Exception e) {
            throw new RuntimeException("Error uploading file to S3: " + e.awsErrorDetails().errorMessage(), e);
        } catch (Exception e) {
            throw new RuntimeException("Unexpected error occurred during file upload", e);
        }
    }

    // Generate a pre-signed URL for downloading an image
    public String getPresignedUrl(String key) {
        try {
            GetObjectRequest request = GetObjectRequest.builder()
                    .bucket(bucketName)
                    .key(key)
                    .build();

            return s3Client.utilities().getUrl(b -> b.bucket(bucketName).key(key)).toExternalForm();
        } catch (S3Exception e) {
            throw new RuntimeException("Error generating pre-signed URL: " + e.awsErrorDetails().errorMessage(), e);
        } catch (Exception e) {
            throw new RuntimeException("Unexpected error occurred while generating pre-signed URL", e);
        }
    }
}

