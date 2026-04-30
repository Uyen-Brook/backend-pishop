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
    public CategoryResponse create(
            @ModelAttribute CategoryRequest request,
            @RequestParam(required = false) MultipartFile image
    ) {
        return categoryService.create(request, image);
    }

    // UPDATE
    @PutMapping("/{id}")
    public CategoryResponse update(
            @PathVariable Long id,
            @ModelAttribute CategoryRequest request,
            @RequestParam(required = false) MultipartFile image
    ) {
        return categoryService.update(id, request, image);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        categoryService.delete(id);
        return "Deleted";
    }

    // GET ALL
    @GetMapping
    public List<CategoryResponse> getAll() {
        return categoryService.getAll();
    }

    // SEARCH
    @GetMapping("/search")
    public List<CategoryResponse> search(
            @RequestParam(required = false) String keyword
    ) {
        return categoryService.search(keyword);
    }

    // GET BY ID
    @GetMapping("/{id}")
    public CategoryResponse getById(@PathVariable Long id) {
        return categoryService.getById(id);
    }
}