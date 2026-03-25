package com.backend.pishop.mapper;

import com.backend.pishop.entity.Category;
import com.backend.pishop.response.CategoryResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CategoryMapper {
	private static final ObjectMapper mapper = new ObjectMapper();
	  public static CategoryResponse toResponse(Category category) {
	        CategoryResponse dto = new CategoryResponse();
	        dto.setId(category.getId());
	        dto.setName(category.getName());
	        dto.setDescription(category.getDescription());
	        
	        return dto;
	  }
}
