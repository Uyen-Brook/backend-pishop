package com.backend.pishop.mapper;

import com.backend.pishop.entity.Supplier;
import com.backend.pishop.response.SupplierDetailResponse;
import com.backend.pishop.response.SupplierResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

public class SupplierMapper {
	private static final ObjectMapper mapper = new ObjectMapper();
	 public static SupplierResponse toResponse(Supplier supplier) {
	        SupplierResponse dto = new SupplierResponse();
	        dto.setId(supplier.getId());
	        dto.setName(supplier.getName());
	        dto.setLogo(supplier.getLogo());
	        dto.setAddress(supplier.getAddress());
	        dto.setPhone(supplier.getPhone());
	        dto.setAddress(supplier.getTaxcode());
	        return dto;
	  }
	 
	 public static SupplierDetailResponse toResponseDetail(Supplier supplier) {
		 SupplierDetailResponse dto = new SupplierDetailResponse();
		 dto.setId(supplier.getId());
		 dto.setName(supplier.getName());
		 dto.setTaxcode(supplier.getTaxcode());
		 dto.setEmail(supplier.getEmail());
		 dto.setPhone(supplier.getPhone());
		 dto.setAddress(supplier.getAddress());
		 dto.setNote(supplier.getNote());
		 dto.setWebsite(supplier.getWebsite());
		 dto.setLogo(supplier.getLogo());
		 return dto;
	 }
}
