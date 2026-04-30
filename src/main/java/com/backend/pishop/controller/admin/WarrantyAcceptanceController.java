package com.backend.pishop.controller.admin;


import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.backend.pishop.request.WarrantyAcceptanceRequest;
import com.backend.pishop.response.WarrantyAcceptanceResponse;
import com.backend.pishop.service.admin.WarrantyAcceptanceService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/admin/warranty")
@RequiredArgsConstructor
public class WarrantyAcceptanceController {

    private final WarrantyAcceptanceService warrantyAcceptanceService;

    // =====================================================
    // GET ALL
    // =====================================================

    @GetMapping
    public ResponseEntity<List<WarrantyAcceptanceResponse>> getAll() {

        return ResponseEntity.ok(
                warrantyAcceptanceService.getAll()
        );
    }

    // =====================================================
    // DETAIL
    // =====================================================

    @GetMapping("/{id}")
    public ResponseEntity<WarrantyAcceptanceResponse> getDetail(
            @PathVariable Long id
    ) {

        return ResponseEntity.ok(
                warrantyAcceptanceService.getDetail(id)
        );
    }

    // =====================================================
    // SEARCH
    // =====================================================

    @GetMapping("/search")
    public ResponseEntity<List<WarrantyAcceptanceResponse>> search(
            @RequestParam(required = false) String keyword
    ) {

        return ResponseEntity.ok(
                warrantyAcceptanceService.search(keyword)
        );
    }

    // =====================================================
    // CREATE
    // =====================================================

    @PostMapping
    public ResponseEntity<WarrantyAcceptanceResponse> create(
            @RequestBody WarrantyAcceptanceRequest request
    ) {

        return ResponseEntity.ok(
                warrantyAcceptanceService.create(request)
        );
    }

    // =====================================================
    // UPDATE
    // =====================================================

    @PutMapping("/{id}")
    public ResponseEntity<WarrantyAcceptanceResponse> update(
            @PathVariable Long id,
            @RequestBody WarrantyAcceptanceRequest request
    ) {

        return ResponseEntity.ok(
                warrantyAcceptanceService.update(id, request)
        );
    }

    // =====================================================
    // DELETE
    // =====================================================

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Long id
    ) {

        warrantyAcceptanceService.delete(id);

        return ResponseEntity.ok().build();
    }
}