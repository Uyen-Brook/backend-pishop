package com.backend.pishop.mapper;

import com.backend.pishop.entity.Supplier;
import com.backend.pishop.response.SupplierResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

public class SupplierMapper {
	private static final ObjectMapper mapper = new ObjectMapper();
	 public static SupplierResponse toResponse(Supplier supplier) {
	        SupplierResponse dto = new SupplierResponse();
	        dto.setId(supplier.getId());
	        dto.setName(supplier.getName());
	        dto.setLogo(supplier.getLogo());
	        return dto;
	  }
}
