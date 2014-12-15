package net.nova123.lib.http;

class HttpRequestResult {

	public static final int HTTP_SUCCESS = 200;

	private int code;

	private String response;

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}

}
