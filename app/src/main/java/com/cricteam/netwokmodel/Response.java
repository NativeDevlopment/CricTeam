package com.cricteam.netwokmodel;



public class Response {

	public int statusCode;
	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public String message;
	public Object data;
	private boolean success;
	private String path;

	public Response() {
	}

	public Response(Object body, String message, String path) {
		this.success = true;
		this.data = body;
		this.message = message;
		this.path = path;
		this.statusCode = 200;
	}


	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}



	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	
}
