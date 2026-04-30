package com.backend.pishop.util;

import com.backend.pishop.service.CloudinaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CloudinaryHelper {

    private final CloudinaryService cloudinaryService;

    public void deleteImage(String url) {
        try {
            String publicId = ImageUtils.extractPublicId(url);
            cloudinaryService.deleteImage(publicId);
        } catch (Exception e) {
            // log thôi
        }
    }
}