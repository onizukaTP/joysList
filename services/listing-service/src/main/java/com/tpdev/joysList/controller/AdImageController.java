package com.tpdev.joysList.controller;

import com.tpdev.joysList.service.AdImageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/ads")
@RequiredArgsConstructor
public class AdImageController {

    private final AdImageService adImageService;

    @PostMapping("/{adId}/images")
    public ResponseEntity<List<String>> uploadImages(
            @PathVariable Long adId,
            @RequestParam("files") List<MultipartFile> files) throws IOException {

        log.info("POST /ads/{}/images — {} file(s)", adId, files.size());
        List<String> urls = adImageService.uploadImages(adId, files);
        return ResponseEntity.ok(urls);
    }

    @DeleteMapping("/images/{imageId}")
    public ResponseEntity<String> deleteImage(@PathVariable Long imageId) {
        log.info("DELETE /ads/images/{}", imageId);
        adImageService.deleteImage(imageId);
        return ResponseEntity.ok("Image deleted successfully");
    }

    @GetMapping("/{adId}/images")
    public ResponseEntity<List<String>> getImages(@PathVariable Long adId) {
        log.info("GET /ads/{}/images", adId);
        return ResponseEntity.ok(adImageService.getImageUrls(adId));
    }
}