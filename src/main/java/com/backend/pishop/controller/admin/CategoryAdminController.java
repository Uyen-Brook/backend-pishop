package com.backend.pishop.controller.admin;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.backend.pishop.config.APIResponse;
import com.backend.pishop.request.CategoryRequest;
import com.backend.pishop.response.CategoryResponse;
import com.backend.pishop.service.CategoryService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/admin/categories")
@RequiredArgsConstructor
public class CategoryAdminController {

    private final CategoryService categoryService;

    // CREATE
    @PostMapping
    public APIResponse<CategoryResponse> create(
            @ModelAttribute CategoryRequest request,
            @RequestParam(required = false) MultipartFile image
    ) {
        return APIResponse.<CategoryResponse>builder()
                .result(categoryService.create(request, image))
                .build();
    }

    // UPDATE
    @PutMapping("/{id}")
    public APIResponse<CategoryResponse> update(
            @PathVariable Long id,
            @ModelAttribute CategoryRequest request,
            @RequestParam(required = false) MultipartFile image
    ) {
        return APIResponse.<CategoryResponse>builder()
                .result(categoryService.update(id, request, image))
                .build();
    }

    // DELETE
    @DeleteMapping("/{id}")
    public APIResponse<Void> delete(@PathVariable Long id) {
        categoryService.delete(id);
        return APIResponse.<Void>builder().message("Deleted").build();
    }

    // GET ALL
    @GetMapping
    public APIResponse<List<CategoryResponse>> getAll() {
        return APIResponse.<List<CategoryResponse>>builder()
                .result(categoryService.getAll())
                .build();
    }

    // SEARCH
    @GetMapping("/search")
    public APIResponse<List<CategoryResponse>> search(
            @RequestParam(required = false) String keyword
    ) {
        return APIResponse.<List<CategoryResponse>>builder()
                .result(categoryService.search(keyword))
                .build();
    }

    // GET BY ID
    @GetMapping("/{id}")
    public APIResponse<CategoryResponse> getById(@PathVariable Long id) {
        return APIResponse.<CategoryResponse>builder()
                .result(categoryService.getById(id))
                .build();
    }
}