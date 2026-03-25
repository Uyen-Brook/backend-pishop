package com.backend.pishop.mapper;

import java.util.List;
import java.util.Map;

import com.backend.pishop.entity.Product;
import com.backend.pishop.response.ProductResponse;
import com.backend.pishop.response.ProductSumaryResponse;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;


public class ProductMapper {

    private static final ObjectMapper mapper = new ObjectMapper();

    public static ProductResponse toResponse(Product product) {
        ProductResponse dto = new ProductResponse();

        dto.setId(product.getId());
        dto.setModelName(product.getModelName());
        dto.setModelNumber(product.getModelNumber());
        dto.setThumbnail(product.getThumbnail());
        dto.setDescription(product.getDescription());
        dto.setBasePrice(product.getBasePrice());
        dto.setProductStatus(product.getProductStatus());
        dto.setCreateAt(product.getCreateAt());
        dto.setUpdateAt(product.getUpdateAt());

        // Parse JSON → Map
        try {
            dto.setSpecification(
                mapper.readValue(product.getSpecification(), new TypeReference<Map<String, String>>() {})
            );
        } catch (Exception e) {
            dto.setSpecification(null);
        }

        // Parse JSON → List
        try {
            dto.setListImage(
                mapper.readValue(product.getListImage(), new TypeReference<List<String>>() {})
            );
        } catch (Exception e) {
            dto.setListImage(null);
        }

        // Relational fields
        dto.setBrandName(product.getBrand().getName());
        dto.setSupplierName(product.getSupplier().getName());
        dto.setCategoryName(product.getCategory().getName());

        return dto;
    }
    
    public static ProductSumaryResponse toSummaryResponse(Product product) {
        ProductSumaryResponse dto = new ProductSumaryResponse();

        dto.setId(product.getId());
        dto.setModelName(product.getModelName());
        dto.setModelNumber(product.getModelNumber());
        dto.setThumbnail(product.getThumbnail());
        dto.setBasePrice(product.getBasePrice());
        dto.setProductStatus(product.getProductStatus());
        dto.setCreateAt(product.getCreateAt());
        dto.setUpdateAt(product.getUpdateAt());

        // lấy name từ entity liên kết
        dto.setBrandName(product.getBrand().getName());
        dto.setSupplierName(product.getSupplier().getName());
        dto.setCategoryName(product.getCategory().getName());

        return dto;
    }
}

