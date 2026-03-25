package com.backend.pishop.mapper;

import com.backend.pishop.entity.Category;
import com.backend.pishop.response.CategoryDetailResponse;
import com.backend.pishop.response.CategoryResponse;
import com.backend.pishop.response.ProductSumaryResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CategoryDetailMapper {
	private static final ObjectMapper mapper = new ObjectMapper();
	
	  public static CategoryDetailResponse toResponse(Category category) {
	        CategoryDetailResponse dto = new CategoryDetailResponse();
	        dto.setId(category.getId());
	        dto.setName(category.getName());
	        dto.setDescription(category.getDescription());
	        dto.setProducts(category.getProducts().stream()
	        		.map(ProductMapper::toSummaryResponse)
	        		.toList());
	        
	        return dto;
	  }
}
