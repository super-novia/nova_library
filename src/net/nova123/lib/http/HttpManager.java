package net.nova123.lib.http;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.os.Handler;

public class HttpManager {

	ExecutorService executor = Executors.newFixedThreadPool(5);

	private static HttpManager httpManager = new HttpManager();

	private HttpExecutor httpExecutor = new HttpExecutor();

	private Handler mHandler = new Handler();

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
				final HttpRequestResult result = httpExecutor
						.executeHttpGet(url);
				if (result.getCode() == HttpRequestResult.HTTP_SUCCESS) {
					final Object obj = receiver.onStringMessageReceive(
							requestCode, result.getResponse());
					mHandler.post(new Runnable() {

						@Override
						public void run() {
							receiver.onHttpSuccess(requestCode, obj);
						}
					});
				} else {
					mHandler.post(new Runnable() {

						@Override
						public void run() {
							receiver.onHttpFailure(requestCode,
									result.getCode(),
									"ÍøÂçÇëÇó³ö´í,´íÎóÂë:" + result.getCode());
						}
					});
				}
			}
		});
	}

	public void exePost(final int requestCode, final String url,
			final String content, final HttpResultReceiver receiver) {
		executor.execute(new Runnable() {

			@Override
			public void run() {
				final HttpRequestResult result = httpExecutor.executeHttpPost(
						url, content);
				if (result.getCode() == HttpRequestResult.HTTP_SUCCESS) {
					final Object obj = receiver.onStringMessageReceive(
							requestCode, result.getResponse());
					mHandler.post(new Runnable() {

						@Override
						public void run() {
							receiver.onHttpSuccess(requestCode, obj);
						}
					});
				} else {
					mHandler.post(new Runnable() {

						@Override
						public void run() {
							receiver.onHttpFailure(requestCode,
									result.getCode(),
									"ÍøÂçÇëÇó³ö´í,´íÎóÂë:" + result.getCode());
						}
					});
				}
			}
		});
	}

}
