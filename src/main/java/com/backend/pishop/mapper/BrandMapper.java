package com.backend.pishop.mapper;

import java.util.Collections;
import java.util.List;

import com.backend.pishop.entity.Brand;
import com.backend.pishop.response.BrandResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

public class BrandMapper {
	  public static BrandResponse toResponse(Brand brand) {
	        BrandResponse dto = new BrandResponse();
	        dto.setId(brand.getId());
	        dto.setName(brand.getName());
	        dto.setImage(brand.getImage());
	        dto.setWebsite(brand.getWebsite());
	        return dto;
	  }
	  public static List<BrandResponse> toResponseList(List<Brand> brands) {
	        if (brands == null || brands.isEmpty()) {
	            return Collections.emptyList();
	        }

	        return brands.stream()
	                .map(BrandMapper::toResponse)
	                .toList();
	    }
}
