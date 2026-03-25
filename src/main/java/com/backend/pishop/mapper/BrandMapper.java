package com.backend.pishop.mapper;

import com.backend.pishop.entity.Brand;
import com.backend.pishop.response.BrandRespone;
import com.fasterxml.jackson.databind.ObjectMapper;

public class BrandMapper {
	  private static final ObjectMapper mapper = new ObjectMapper();
	  public static BrandRespone toResponse(Brand brand) {
	        BrandRespone dto = new BrandRespone();
	        dto.setId(brand.getId());
	        dto.setBrandName(brand.getName());
	        dto.setImage(brand.getImage());
	        dto.setWebsite(brand.getWebsite());
	        return dto;
	  }
}
