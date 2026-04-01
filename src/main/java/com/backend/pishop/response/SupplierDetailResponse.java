package com.backend.pishop.response;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
public class SupplierDetailResponse {
	    private Long id;

	    private String name;
	    
	    private String taxcode;
	    
	    private String email;
	    
	    private String phone;
	    
	    private String logo;
	    
	    private String address;
	    
	    private String note;
	    
	    private String website;
}
