package com.backend.pishop.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.backend.pishop.entity.Category;
import com.backend.pishop.enums.ResourceType;
import com.backend.pishop.mapper.CategoryMapper;
import com.backend.pishop.repository.CategoryRepository;
import com.backend.pishop.request.CategoryRequest;
import com.backend.pishop.response.CategoryResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final CloudinaryService cloudinaryService;

    // CREATE
    public CategoryResponse create(CategoryRequest request, MultipartFile image) {
        Category category = new Category();
        category.setName(request.getName());
        category.setDescription(request.getDescription());
        category.setIcon(request.getIcon());
        category.setNote(request.getNote());

        if (image != null && !image.isEmpty()) {
            String imageUrl = cloudinaryService.uploadImage(image, ResourceType.CATEGORY);
            category.setImage(imageUrl);
        }

        return CategoryMapper.toResponse(categoryRepository.save(category));
    }

    // UPDATE
//    public CategoryResponse update(Long id, CategoryRequest request, MultipartFile image) {
//        Category category = categoryRepository.findById(id)
//                .orElseThrow(() -> new RuntimeException("Category not found"));
//
//        category.setName(request.getName());
//        category.setDescription(request.getDescription());
//        category.setIcon(request.getIcon());
//        category.setNote(request.getNote());
//
//        if (image != null && !image.isEmpty()) {
//            String imageUrl = cloudinaryService.uploadImage(image, ResourceType.CATEGORY);
//            category.setImage(imageUrl);
//        }
//    
//        return CategoryMapper.toResponse(categoryRepository.save(category));
//    }
    public CategoryResponse update(Long id, CategoryRequest request, MultipartFile image) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        Optional.ofNullable(request.getName())
                .ifPresent(category::setName);

        Optional.ofNullable(request.getDescription())
                .ifPresent(category::setDescription);

        Optional.ofNullable(request.getIcon())
                .ifPresent(category::setIcon);

        Optional.ofNullable(request.getNote())
                .ifPresent(category::setNote);

        if (image != null && !image.isEmpty()) {
            String imageUrl = cloudinaryService.uploadImage(image, ResourceType.CATEGORY);
            category.setImage(imageUrl);
        }

        return CategoryMapper.toResponse(categoryRepository.save(category));
    }

    // DELETE
    public void delete(Long id) {
        categoryRepository.deleteById(id);
    }

    // GET ALL
    public List<CategoryResponse> getAll() {
        return categoryRepository.findAllByOrderByNameAsc()
                .stream()
                .map(CategoryMapper::toResponse)
                .toList();
    }

    // SEARCH
    public List<CategoryResponse> search(String keyword) {
        return categoryRepository.search(keyword)
                .stream()
                .map(CategoryMapper::toResponse)
                .toList();
    }

    // GET BY ID
    public CategoryResponse getById(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        return CategoryMapper.toResponse(category);
    }
}