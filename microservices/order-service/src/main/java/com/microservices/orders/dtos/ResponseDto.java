package com.microservices.orders.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

public class ResponseDto {

    private boolean success;

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public ResponseDto(boolean success) {
		super();
		this.success = success;
	}
    
}
