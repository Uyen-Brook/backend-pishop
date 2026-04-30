package com.backend.pishop.service.admin;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.backend.pishop.entity.Product;
import com.backend.pishop.entity.SerialNumber;
import com.backend.pishop.enums.SerialStatus;
import com.backend.pishop.repository.ProductRepository;
import com.backend.pishop.repository.SerialNumberRepository;
import com.backend.pishop.request.SerialNumberRequest;
import com.backend.pishop.response.SerialNumberResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class SerialNumberService {

    private final SerialNumberRepository serialNumberRepository;
    private final ProductRepository productRepository;

    // =====================================================
    // GET ALL
    // =====================================================

    public List<SerialNumberResponse> getAll() {

        return serialNumberRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    // =====================================================
    // DETAIL
    // =====================================================

    public SerialNumberResponse getDetail(String serialNumber) {

        SerialNumber serial = serialNumberRepository.findById(serialNumber)
                .orElseThrow(() ->
                        new IllegalArgumentException("Serial not found"));

        return mapToResponse(serial);
    }

    // =====================================================
    // SEARCH
    // =====================================================

    public List<SerialNumberResponse> search(
            String keyword,
            SerialStatus status
    ) {

        return serialNumberRepository.search(
                keyword,
                status
        ).stream()
         .map(this::mapToResponse)
         .toList();
    }

    // =====================================================
    // CREATE
    // =====================================================

    public SerialNumberResponse create(
            SerialNumberRequest request
    ) {

        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() ->
                        new IllegalArgumentException("Product not found"));

        SerialNumber serial = new SerialNumber();

        serial.setSerialNumber(request.getSerialNumber());

        serial.setStatus(SerialStatus.IN_STOCK);

//        serial.setImportDate(LocalDateTime.now());
//
//        serial.setImportPrice(request.getImportPrice());
//
//        serial.setWarehouseLocation(request.getWarehouseLocation());
//
//        serial.setSellingPrice(request.getSellingPrice());

        serial.setWarrantyPeriod(request.getWarrantyPeriod());

        serial.setCreateAt(LocalDateTime.now());

        serial.setLastUpdate(LocalDateTime.now());

        serial.setProduct(product);

        SerialNumber saved = serialNumberRepository.save(serial);

        return mapToResponse(saved);
    }

    // =====================================================
    // UPDATE
    // =====================================================

    public SerialNumberResponse update(
            String serialNumber,
            SerialNumberRequest request
    ) {

        SerialNumber serial = serialNumberRepository
                .findById(serialNumber)
                .orElseThrow(() ->
                        new IllegalArgumentException("Serial not found"));

        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() ->
                        new IllegalArgumentException("Product not found"));

//        serial.setImportPrice(request.getImportPrice());
//
//        serial.setWarehouseLocation(request.getWarehouseLocation());
//
//        serial.setSellingPrice(request.getSellingPrice());

        serial.setWarrantyPeriod(request.getWarrantyPeriod());

        serial.setLastUpdate(LocalDateTime.now());

        serial.setProduct(product);

        SerialNumber updated = serialNumberRepository.save(serial);

        return mapToResponse(updated);
    }

    // =====================================================
    // CHANGE STATUS
    // =====================================================

    public SerialNumberResponse changeStatus(
            String serialNumber,
            SerialStatus status
    ) {

        SerialNumber serial = serialNumberRepository
                .findById(serialNumber)
                .orElseThrow(() ->
                        new IllegalArgumentException("Serial not found"));

        serial.setStatus(status);

        serial.setLastUpdate(LocalDateTime.now());

        SerialNumber updated = serialNumberRepository.save(serial);

        return mapToResponse(updated);
    }

    // =====================================================
    // DELETE
    // =====================================================

    public void delete(String serialNumber) {

        SerialNumber serial = serialNumberRepository
                .findById(serialNumber)
                .orElseThrow(() ->
                        new IllegalArgumentException("Serial not found"));

        serialNumberRepository.delete(serial);
    }

    // =====================================================
    // MAP RESPONSE
    // =====================================================

    private SerialNumberResponse mapToResponse(
            SerialNumber serial
    ) {

        Product product = serial.getProduct();

        return SerialNumberResponse.builder()
                .serialNumber(serial.getSerialNumber())
                .status(serial.getStatus())
//                .importDate(serial.getImportDate())
//                .importPrice(serial.getImportPrice())
//                .warehouseLocation(serial.getWarehouseLocation())
//                .sellingPrice(serial.getSellingPrice())
                .soldDate(serial.getSoldDate())
                .warrantyPeriod(serial.getWarrantyPeriod())
                .createAt(serial.getCreateAt())
                .lastUpdate(serial.getLastUpdate())

                .productId(
                        product != null
                                ? product.getId()
                                : null
                )

                .modelName(
                        product != null
                                ? product.getModelName()
                                : null
                )

                .modelNumber(
                        product != null
                                ? product.getModelNumber()
                                : null
                )

                .thumbnail(
                        product != null
                                ? product.getThumbnail()
                                : null
                )

                .brandName(
                        product != null
                        && product.getBrand() != null
                                ? product.getBrand().getName()
                                : null
                )

                .categoryName(
                        product != null
                        && product.getCategory() != null
                                ? product.getCategory().getName()
                                : null
                )

                .orderItemId(
                        serial.getOrderItem() != null
                                ? serial.getOrderItem().getId()
                                : null
                )

                .build();
    }
}