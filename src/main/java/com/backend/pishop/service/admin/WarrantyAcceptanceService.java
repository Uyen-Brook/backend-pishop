package com.backend.pishop.service.admin;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.backend.pishop.entity.Product;
import com.backend.pishop.entity.SerialNumber;
import com.backend.pishop.entity.WarrantyAcceptance;
import com.backend.pishop.repository.SerialNumberRepository;
import com.backend.pishop.repository.WarrantyAcceptanceRepository;
import com.backend.pishop.request.WarrantyAcceptanceRequest;
import com.backend.pishop.response.WarrantyAcceptanceResponse;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class WarrantyAcceptanceService {

    private final WarrantyAcceptanceRepository warrantyAcceptanceRepository;
    private final SerialNumberRepository serialNumberRepository;

    // =====================================================
    // GET ALL
    // =====================================================

    public List<WarrantyAcceptanceResponse> getAll() {

        return warrantyAcceptanceRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    // =====================================================
    // DETAIL
    // =====================================================

    public WarrantyAcceptanceResponse getDetail(Long id) {

        WarrantyAcceptance warranty = warrantyAcceptanceRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException("Warranty not found"));

        return mapToResponse(warranty);
    }

    // =====================================================
    // SEARCH
    // =====================================================

    public List<WarrantyAcceptanceResponse> search(
            String keyword
    ) {

        return warrantyAcceptanceRepository.search(keyword)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    // =====================================================
    // CREATE
    // =====================================================

    public WarrantyAcceptanceResponse create(
            WarrantyAcceptanceRequest request
    ) {

        SerialNumber serial = serialNumberRepository
                .findById(request.getSerialNumber())
                .orElseThrow(() ->
                        new IllegalArgumentException("Serial not found"));

        Product product = serial.getProduct();

        WarrantyAcceptance warranty = new WarrantyAcceptance();

        warranty.setStartDate(LocalDateTime.now());

        warranty.setCustomerName(request.getCustomerName());
        warranty.setPhone(request.getPhone());
        warranty.setEmail(request.getEmail());

        // auto lấy tên máy từ serial
        warranty.setProductName(
                product != null
                        ? product.getModelName()
                        : null
        );

        warranty.setProductStatus(request.getProductStatus());

        warranty.setProblem(request.getProblem());

        warranty.setAccessories(request.getAccessories());

        warranty.setAccountStatus(request.getAccountStatus());

        warranty.setPassword(request.getPassword());

        warranty.setDataStatus(request.getDataStatus());

        warranty.setEstimate(request.getEstimate());

        warranty.setSerialNumberRef(serial);

        WarrantyAcceptance saved =
                warrantyAcceptanceRepository.save(warranty);

        return mapToResponse(saved);
    }

    // =====================================================
    // UPDATE
    // =====================================================

    public WarrantyAcceptanceResponse update(
            Long id,
            WarrantyAcceptanceRequest request
    ) {

        WarrantyAcceptance warranty =
                warrantyAcceptanceRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException("Warranty not found"));

        SerialNumber serial = serialNumberRepository
                .findById(request.getSerialNumber())
                .orElseThrow(() ->
                        new IllegalArgumentException("Serial not found"));

        Product product = serial.getProduct();

        warranty.setCustomerName(request.getCustomerName());
        warranty.setPhone(request.getPhone());
        warranty.setEmail(request.getEmail());

        warranty.setProductName(
                product != null
                        ? product.getModelName()
                        : null
        );

        warranty.setProductStatus(request.getProductStatus());

        warranty.setProblem(request.getProblem());

        warranty.setAccessories(request.getAccessories());

        warranty.setAccountStatus(request.getAccountStatus());

        warranty.setPassword(request.getPassword());

        warranty.setDataStatus(request.getDataStatus());

        warranty.setEstimate(request.getEstimate());

        warranty.setSerialNumberRef(serial);

        WarrantyAcceptance updated =
                warrantyAcceptanceRepository.save(warranty);

        return mapToResponse(updated);
    }

    // =====================================================
    // DELETE
    // =====================================================

    public void delete(Long id) {

        WarrantyAcceptance warranty =
                warrantyAcceptanceRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException("Warranty not found"));

        warrantyAcceptanceRepository.delete(warranty);
    }

    // =====================================================
    // MAPPER
    // =====================================================

    private WarrantyAcceptanceResponse mapToResponse(
            WarrantyAcceptance warranty
    ) {

        SerialNumber serial = warranty.getSerialNumberRef();

        Product product = serial != null
                ? serial.getProduct()
                : null;

        boolean inWarranty = false;

        if (serial != null
                && serial.getSoldDate() != null
                && serial.getWarrantyPeriod() != null) {

            LocalDateTime expiredDate =
                    serial.getSoldDate()
                    .plusMonths(serial.getWarrantyPeriod());

            inWarranty = expiredDate.isAfter(LocalDateTime.now());
        }

        return WarrantyAcceptanceResponse.builder()
                .id(warranty.getId())
                .startDate(warranty.getStartDate())

                .customerName(warranty.getCustomerName())
                .phone(warranty.getPhone())
                .email(warranty.getEmail())

                .serialNumber(
                        serial != null
                                ? serial.getSerialNumber()
                                : null
                )

                .productName(warranty.getProductName())

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

                .productStatus(warranty.getProductStatus())

                .problem(warranty.getProblem())

                .accessories(warranty.getAccessories())

                .accountStatus(warranty.getAccountStatus())

                .password(warranty.getPassword())

                .dataStatus(warranty.getDataStatus())

                .estimate(warranty.getEstimate())

                .warrantyPeriod(
                        serial != null
                                ? serial.getWarrantyPeriod()
                                : null
                )

                .soldDate(
                        serial != null
                                ? serial.getSoldDate()
                                : null
                )

                .inWarranty(inWarranty)

                .build();
    }
}