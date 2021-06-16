package com.microservices.coupons.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

public class ReturnResponse {
    private String companyId;

	public ReturnResponse(String companyId) {
		super();
		this.companyId = companyId;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
    
    

}
