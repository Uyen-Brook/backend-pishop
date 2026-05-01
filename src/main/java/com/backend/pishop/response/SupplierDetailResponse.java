package com.backend.pishop.response;

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
	    private String representative;
	    private String note;
	    
	    private String website;
}
