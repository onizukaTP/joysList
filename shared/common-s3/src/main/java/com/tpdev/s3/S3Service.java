package com.tpdev.s3;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class S3Service {

    private final S3Client s3Client;

    @Value("${aws.s3.bucket}")
    private String bucket;

    @Value("${aws.s3.region}")
    private String region;

    /**
     * Uploads a file to S3 under the given folder prefix.
     *
     * @param file   the multipart file to upload
     * @param folder e.g. "avatars" or "ad-images"
     * @return the public HTTPS URL of the uploaded object
     */
    public String uploadFile(MultipartFile file, String folder) {
        String originalFilename = file.getOriginalFilename();
        String extension = originalFilename != null && originalFilename.contains(".")
                ? originalFilename.substring(originalFilename.lastIndexOf("."))
                : "";
        String key = folder + "/" + UUID.randomUUID() + extension;

        try {
            PutObjectRequest request = PutObjectRequest.builder()
                    .bucket(bucket)
                    .key(key)
                    .contentType(file.getContentType())
                    .contentLength(file.getSize())
                    .build();

            s3Client.putObject(request, RequestBody.fromBytes(file.getBytes()));

            String url = String.format("https://%s.s3.%s.amazonaws.com/%s", bucket, region, key);
            log.info("Uploaded file to S3: {}", url);
            return url;

        } catch (IOException e) {
            log.error("Failed to upload file to S3: {}", e.getMessage());
            throw new RuntimeException("S3 upload failed: " + e.getMessage(), e);
        }
    }

    /**
     * Deletes an object from S3 by its full URL or key.
     *
     * @param urlOrKey full S3 URL or object key (e.g. "avatars/abc.jpg")
     */
    public void deleteFile(String urlOrKey) {
        // Extract key from URL if a full URL was passed
        String key = urlOrKey.contains(".amazonaws.com/")
                ? urlOrKey.substring(urlOrKey.indexOf(".amazonaws.com/") + ".amazonaws.com/".length())
                : urlOrKey;

        DeleteObjectRequest request = DeleteObjectRequest.builder()
                .bucket(bucket)
                .key(key)
                .build();

        s3Client.deleteObject(request);
        log.info("Deleted S3 object: {}", key);
    }
}
