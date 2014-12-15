package net.nova123.lib.http;

@Deprecated
public interface HttpRepsoneListener {

	public void onResponeResultString(int requestCode, int result, String str);

	public <T> void onResponeResultObject(int requestCode, T t);

	public void onHttpFailed(int code);
}
