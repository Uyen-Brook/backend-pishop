package com.backend.pishop.service;

import java.io.IOException;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.backend.pishop.enums.ResourceType;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CloudinaryService {
    private final Cloudinary cloudinary;

//    // Upload ảnh lên Cloudinary và trả về URL
    public String uploadImage(MultipartFile file, ResourceType type) {
        try {
            String folder = switch (type) {
                case AVATAR -> "pishop/avatars";
                case PRODUCT -> "pishop/products";
                case LOGO -> "pishop/brands";
                case CATEGORY -> "pishop/categories";
                default -> "pishop/others";
            };

            String originalName = file.getOriginalFilename();
            String extension = originalName.substring(originalName.lastIndexOf("."));

            String fileName = "brand_" + System.currentTimeMillis() + "_" 
                    + UUID.randomUUID().toString().substring(0, 8);

            Map uploadResult = cloudinary.uploader().upload(
                    file.getBytes(),
                    ObjectUtils.asMap(
                            "folder", folder,
                            "public_id", fileName,
                            "resource_type", "auto"
                    )
            );
            return uploadResult.get("secure_url").toString();

        } catch (IOException e) {
            throw new RuntimeException("Không thể tải ảnh lên cloudinary: " + e.getMessage());
        }
    }
    
    public String deleteImage(String publicId) {
		try {
			Map result = cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
			return result.get("result").toString();
		} catch (IOException e) {
			throw new RuntimeException("Không thể xóa ảnh trên Cloudinary: " + e.getMessage());
		}
	}
}
