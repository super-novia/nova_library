package net.nova123.lib.http;

public interface HttpResultReceiver {

	public void onHttpFailed(int requestCode, int code);

	public void onFailedMessageCode(int requestCode, int code, String msg);

	public void onStringMessageReceive(int requestCode, String str);

	public <T> void onObjectMessageReceive(int requestCode, T t);
}
