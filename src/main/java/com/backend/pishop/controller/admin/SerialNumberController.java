package com.backend.pishop.controller.admin;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.backend.pishop.enums.SerialStatus;
import com.backend.pishop.request.SerialNumberRequest;
import com.backend.pishop.response.SerialNumberResponse;
import com.backend.pishop.service.admin.SerialNumberService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/admin/serial-numbers")
@RequiredArgsConstructor
public class SerialNumberController {

    private final SerialNumberService serialNumberService;

    // =====================================================
    // GET ALL
    // =====================================================

    @GetMapping
    public ResponseEntity<List<SerialNumberResponse>> getAll() {

        return ResponseEntity.ok(
                serialNumberService.getAll()
        );
    }

    // =====================================================
    // DETAIL
    // =====================================================

    @GetMapping("/{serialNumber}")
    public ResponseEntity<SerialNumberResponse> getDetail(
            @PathVariable String serialNumber
    ) {

        return ResponseEntity.ok(
                serialNumberService.getDetail(serialNumber)
        );
    }

    // =====================================================
    // SEARCH
    // =====================================================

    @GetMapping("/search")
    public ResponseEntity<List<SerialNumberResponse>> search(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) SerialStatus status
    ) {

        return ResponseEntity.ok(
                serialNumberService.search(
                        keyword,
                        status
                )
        );
    }

    // =====================================================
    // CREATE
    // =====================================================

    @PostMapping
    public ResponseEntity<SerialNumberResponse> create(
            @RequestBody SerialNumberRequest request
    ) {

        return ResponseEntity.ok(
                serialNumberService.create(request)
        );
    }

    // =====================================================
    // UPDATE
    // =====================================================

    @PutMapping("/{serialNumber}")
    public ResponseEntity<SerialNumberResponse> update(
            @PathVariable String serialNumber,
            @RequestBody SerialNumberRequest request
    ) {

        return ResponseEntity.ok(
                serialNumberService.update(
                        serialNumber,
                        request
                )
        );
    }

    // =====================================================
    // CHANGE STATUS
    // =====================================================

    @PatchMapping("/{serialNumber}/status")
    public ResponseEntity<SerialNumberResponse> changeStatus(
            @PathVariable String serialNumber,
            @RequestParam SerialStatus status
    ) {

        return ResponseEntity.ok(
                serialNumberService.changeStatus(
                        serialNumber,
                        status
                )
        );
    }

    // =====================================================
    // DELETE
    // =====================================================

    @DeleteMapping("/{serialNumber}")
    public ResponseEntity<Void> delete(
            @PathVariable String serialNumber
    ) {

        serialNumberService.delete(serialNumber);

        return ResponseEntity.ok().build();
    }
}