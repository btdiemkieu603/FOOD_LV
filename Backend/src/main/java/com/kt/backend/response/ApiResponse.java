package com.kt.backend.response;

//import lombok.AllArgsConstructor;
//import lombok.Getter;
import lombok.NoArgsConstructor;
//import lombok.Setter;

//@Setter
//@Getter
@NoArgsConstructor
//@AllArgsConstructor
public class ApiResponse {

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	private String message;
	private boolean success;

	public ApiResponse(String message, boolean success) {
		this.message = message;
		this.success = success;
	}

}
