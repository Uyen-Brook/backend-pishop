package com.backend.pishop.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.backend.pishop.config.APIResponse;
import com.backend.pishop.entity.Brand;
import com.backend.pishop.enums.ResourceType;
import com.backend.pishop.mapper.BrandMapper;
import com.backend.pishop.repository.BrandRepository;
import com.backend.pishop.response.BrandResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BrandService {

	private final BrandRepository brandRepository;
	private final CloudinaryService cloudinaryService;

	// CREATE
	public Brand create(String name, String website, MultipartFile image) {
		Brand brand = new Brand();
		brand.setName(name);
		brand.setWebsite(website);

		if (image != null && !image.isEmpty()) {
			String imageUrl = cloudinaryService.uploadImage(image, ResourceType.LOGO);
			brand.setImage(imageUrl);
		}

		return brandRepository.save(brand);
	}

	// UPDATE
	public Brand update(Long id, String name, String website, MultipartFile image) {
		Brand brand = brandRepository.findById(id).orElseThrow(() -> new RuntimeException("Brand không tồn tại"));

		brand.setName(name);
		brand.setWebsite(website);

		if (image != null && !image.isEmpty()) {
			// TODO: xóa ảnh cũ nếu muốn
			String imageUrl = cloudinaryService.uploadImage(image, ResourceType.LOGO);
			brand.setImage(imageUrl);
		}

		return brandRepository.save(brand);
	}

	// DELETE
	public void delete(Long id) {
		Brand brand = brandRepository.findById(id).orElseThrow(() -> new RuntimeException("Brand không tồn tại"));

		// TODO: xóa ảnh trên cloud nếu cần
		brandRepository.delete(brand);
	}

	// GET ALL
	public List<BrandResponse> getAll() {
		List<BrandResponse> data = BrandMapper.toResponseList(
				brandRepository.findAll()
		);
		return data;
	}

	// GET BY ID
	public Brand getById(Long id) {
		return brandRepository.findById(id).orElseThrow(() -> new RuntimeException("Brand không tồn tại"));
	}

	//SEARCH (name hoặc id)
	public APIResponse<List<BrandResponse>> search(String keyword) {
	    if (keyword == null || keyword.trim().isEmpty()) {
	        List<BrandResponse> data = BrandMapper.toResponseList(
	                brandRepository.findAll()
	        );
	        return APIResponse.success(data);
	    }
	    List<BrandResponse> result = BrandMapper.toResponseList(
	            brandRepository.search(keyword.trim())
	    );
	    // không có kết quả
	    if (result.isEmpty()) {
	        return APIResponse.notFound();
	    }
	    return APIResponse.success(result);
	}
}