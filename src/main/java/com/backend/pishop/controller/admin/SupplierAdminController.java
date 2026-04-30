package com.backend.pishop.controller.admin;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.backend.pishop.request.SupplierRequest;
import com.backend.pishop.response.SupplierResponse;
import com.backend.pishop.service.SupplierService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/admin/suppliers")
@RequiredArgsConstructor
public class SupplierAdminController {

    private final SupplierService supplierService;

    // CREATE
    @PostMapping
    public SupplierResponse create(
            @ModelAttribute SupplierRequest request,
            @RequestParam(required = false) MultipartFile logo
    ) {
        return supplierService.create(request, logo);
    }

    // UPDATE (PATCH)
    @PatchMapping("/{id}")
    public SupplierResponse update(
            @PathVariable Long id,
            @ModelAttribute SupplierRequest request,
            @RequestParam(required = false) MultipartFile logo
    ) {
        return supplierService.update(id, request, logo);
    }

    // DELETE
    @DeleteMapping("/{id}")
    public String delete(@PathVariable Long id) {
        supplierService.delete(id);
        return "Deleted";
    }

    // GET ALL
    @GetMapping
    public List<SupplierResponse> getAll() {
        return supplierService.getAll();
    }

    // SEARCH
    @GetMapping("/search")
    public List<SupplierResponse> search(
            @RequestParam(required = false) String keyword
    ) {
        return supplierService.search(keyword);
    }

    // GET BY ID
    @GetMapping("/{id}")
    public SupplierResponse getById(@PathVariable Long id) {
        return supplierService.getById(id);
    }
}