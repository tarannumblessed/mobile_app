package com.appsdeveloperblog.app.ws.mobileappws.ui.model.response;

public enum ErrorMessages {
	MISSING_REQUIRED_FEILD("Message Required Feild .Please Check documentation for required Feild"),
	RECORD_ALREADY_EXIXTS("record already exist"),
	INTERNAL_SERVER_ERROR("Internal Server Error"),
	NO_RECORD_FOUND("Record with provided id is not found"),
	AUTHENTICATION_FAILED("Authentication Failed"),
	COULD_NOT_UPDATE_RECORD("Could Not update Record"),
	COULD_NOT_DELETE_RECORD("Could Not Delete Record"),
	EMAIL_ADDRESS_NOT_VERIFIED("Email Address Could not be Verified");
	private String errorMessage;

	private ErrorMessages(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	

}
