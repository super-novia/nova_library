package net.nova123.lib.http;

public interface HttpResultReceiver {

	public void onHttpFailure(int requestCode, int code, String str);

	public void onHttpSuccess(int requestCode, Object t);

	/**
	 * you can parse the msg to a object here
	 * 
	 * @param requestCode
	 * @param str
	 */
	public Object onStringMessageReceive(int requestCode, String str);

}
