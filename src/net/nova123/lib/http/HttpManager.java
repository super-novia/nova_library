package net.nova123.lib.http;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.alibaba.fastjson.JSON;

public class HttpManager {

	ExecutorService executor = Executors.newFixedThreadPool(5);

	private static HttpManager httpManager = new HttpManager();

	private HttpExecutor httpExecutor = new HttpExecutor();

	private HttpManager() {
	}

	public void setHttpConfig(HttpConfig config) {
		httpExecutor.setConfig(config);
	}

	public static final HttpManager getInstance() {
		return httpManager;
	}

	public void exeGet(final int requestCode, final String url,
			final HttpResultReceiver receiver) {
		executor.execute(new Runnable() {

			@Override
			public void run() {
				HttpRequestResult result = httpExecutor.executeHttpGet(url);
				if (result.getCode() == HttpRequestResult.HTTP_SUCCESS) {
					receiver.onStringMessageReceive(requestCode,
							result.getResponse());
					System.out.println("exe Get");
				} else {
					receiver.onHttpFailed(requestCode, result.getCode());
				}
			}
		});
	}

	public <T> void exeGetAndParseJson(final int requestCode, final String url,
			final Class<T> cls, final HttpResultReceiver receiver) {
		executor.execute(new Runnable() {

			@Override
			public void run() {
				HttpRequestResult result = httpExecutor.executeHttpGet(url);
				if (result.getCode() == HttpRequestResult.HTTP_SUCCESS) {
					String str = result.getResponse();
					T t = JSON.parseObject(str, cls);
					receiver.onObjectMessageReceive(requestCode, t);
				} else {
					receiver.onHttpFailed(requestCode, result.getCode());
				}
			}
		});
	}

	/**
	 * 这个方法还未实现！请不要使用
	 */
	@Deprecated
	public void exeGetAndParseXml() {
	}

	public void exePost(final int requestCode, final String url,
			final String content, final HttpResultReceiver receiver) {
		executor.execute(new Runnable() {

			@Override
			public void run() {
				HttpRequestResult result = httpExecutor.executeHttpPost(url,
						content);
				if (result.getCode() == HttpRequestResult.HTTP_SUCCESS) {
					receiver.onStringMessageReceive(requestCode,
							result.getResponse());
					System.out.println("exe exePost");
				} else {
					receiver.onHttpFailed(requestCode, result.getCode());
				}
			}
		});
	}

	public void exePostAndParseJson() {
	}

	/**
	 * 这个方法还未实现！请不要使用
	 */
	@Deprecated
	public void exePostAndParseXml() {
	}

}
