package com.backend.pishop.mapper;

import com.backend.pishop.entity.Address;
import com.backend.pishop.response.AddressResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

public class AddressMapper {
	 private static final ObjectMapper mapper = new ObjectMapper();
	  public static AddressResponse toResponse(Address address) {
	        AddressResponse dto = new AddressResponse();
	        dto.setId(address.getId());
	        dto.setFullName(address.getFullName());
	        dto.setPhone(address.getPhone());
	        dto.setSpecificAddress(address.getSpecificAddress());
	        dto.setProvinceName(address.getProvince().getFullName());
	        dto.setWardName(address.getWard().getFullName());
	        dto.setDefault(address.isDefault());
	        
	        return dto;
	  }
}
