package com.backend.pishop.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.backend.pishop.entity.Supplier;
import com.backend.pishop.enums.ResourceType;
import com.backend.pishop.mapper.SupplierMapper;
import com.backend.pishop.repository.SupplierRepository;
import com.backend.pishop.request.SupplierRequest;
import com.backend.pishop.response.SupplierResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SupplierService {

    private final SupplierRepository supplierRepository;
    private final CloudinaryService cloudinaryService;

    // CREATE
    public SupplierResponse create(SupplierRequest request, MultipartFile logo) {
        Supplier supplier = new Supplier();

        supplier.setName(request.getName());
        supplier.setTaxcode(request.getTaxcode());
        supplier.setEmail(request.getEmail());
        supplier.setPhone(request.getPhone());
        supplier.setAddress(request.getAddress());
        supplier.setNote(request.getNote());
        supplier.setWebsite(request.getWebsite());

        if (logo != null && !logo.isEmpty()) {
            String logoUrl = cloudinaryService.uploadImage(logo, ResourceType.LOGO);
            supplier.setLogo(logoUrl);
        }

        return SupplierMapper.toResponse(supplierRepository.save(supplier));
    }

    // UPDATE (partial)
    public SupplierResponse update(Long id, SupplierRequest request, MultipartFile logo) {
        Supplier supplier = supplierRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Supplier not found"));

        // update nếu != null
        Optional.ofNullable(request.getName()).ifPresent(supplier::setName);
        Optional.ofNullable(request.getTaxcode()).ifPresent(supplier::setTaxcode);
        Optional.ofNullable(request.getEmail()).ifPresent(supplier::setEmail);
        Optional.ofNullable(request.getPhone()).ifPresent(supplier::setPhone);
        Optional.ofNullable(request.getAddress()).ifPresent(supplier::setAddress);
        Optional.ofNullable(request.getNote()).ifPresent(supplier::setNote);
        Optional.ofNullable(request.getWebsite()).ifPresent(supplier::setWebsite);

        if (logo != null && !logo.isEmpty()) {
            String logoUrl = cloudinaryService.uploadImage(logo, ResourceType.LOGO);
            supplier.setLogo(logoUrl);
        }

        return SupplierMapper.toResponse(supplierRepository.save(supplier));
    }

    // DELETE
    public void delete(Long id) {
        supplierRepository.deleteById(id);
    }

    // GET ALL
    public List<SupplierResponse> getAll() {
        return supplierRepository.findAll()
                .stream()
                .map(SupplierMapper::toResponse)
                .toList();
    }

    // GET BY ID
    public SupplierResponse getById(Long id) {
        Supplier supplier = supplierRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Supplier not found"));

        return SupplierMapper.toResponse(supplier);
    }

    // SEARCH
    public List<SupplierResponse> search(String keyword) {
        return supplierRepository.search(keyword)
                .stream()
                .map(SupplierMapper::toResponse)
                .toList();
    }
}