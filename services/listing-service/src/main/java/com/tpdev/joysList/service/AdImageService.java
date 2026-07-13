package com.tpdev.joysList.service;

import com.tpdev.joysList.entity.Ad;
import com.tpdev.joysList.entity.AdImage;
import com.tpdev.joysList.exception.ResourceNotFound;
import com.tpdev.joysList.repo.AdImageRepository;
import com.tpdev.joysList.repo.AdRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdImageService {

    private static final int MAX_IMAGES_PER_AD = 5;
    private static final List<String> ALLOWED_TYPES = List.of(
            "image/jpeg", "image/png", "image/webp"
    );

    private final AdRepository adRepository;
    private final AdImageRepository adImageRepository;
    private final S3Service s3Service;

    public List<String> uploadImages(Long adId, List<MultipartFile> files) throws IOException {

        Ad ad = adRepository.findById(adId)
                .orElseThrow(() -> new ResourceNotFound("Ad not found with id: " + adId));

        int currentCount = adImageRepository.countByAdId(adId);
        if (currentCount + files.size() > MAX_IMAGES_PER_AD) {
            throw new IllegalArgumentException(
                    "Ad already has " + currentCount + " images. " +
                            "Maximum is " + MAX_IMAGES_PER_AD + "."
            );
        }

        for (MultipartFile file : files) {
            if (!ALLOWED_TYPES.contains(file.getContentType())) {
                throw new IllegalArgumentException(
                        "Invalid file type: " + file.getContentType() +
                                ". Allowed: JPEG, PNG, WEBP"
                );
            }
        }

        return files.stream().map(file -> {
            try {
                String url = s3Service.upload(file, adId);
                String s3Key = "ads/" + adId + "/" + url.substring(url.lastIndexOf("/") + 1);

                AdImage image = AdImage.builder()
                        .imageUrl(url)
                        .s3Key("ads/" + adId + "/" + url.substring(url.lastIndexOf("/") + 1))
                        .ad(ad)
                        .build();

                adImageRepository.save(image);
                log.info("Saved image record for adId={}, url={}", adId, url);
                return url;
            } catch (IOException e) {
                throw new RuntimeException("Failed to upload image for adId=" + adId, e);
            }
        }).toList();
    }

    public void deleteImage(Long imageId) {
        AdImage image = adImageRepository.findById(imageId)
                .orElseThrow(() -> new ResourceNotFound("Image not found with id: " + imageId));

        s3Service.delete(image.getS3Key());
        adImageRepository.delete(image);
        log.info("Deleted image id={} from S3 and DB", imageId);
    }

    public List<String> getImageUrls(Long adId) {
        return adImageRepository.findByAdId(adId)
                .stream()
                .map(AdImage::getImageUrl)
                .toList();
    }
}