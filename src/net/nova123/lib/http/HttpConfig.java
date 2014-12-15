package net.nova123.lib.http;

public class HttpConfig {

	public static final int DEFAULT_TIME_OUT = 6 * 1000;

	public static final String DEFAULT_CHARSET = "UTF-8";

	public static final int DEFAULT_POOL_THREADS = 5;

	private int timeOut = DEFAULT_TIME_OUT;

	private String charSet = DEFAULT_CHARSET;

	private int poolThreads = DEFAULT_POOL_THREADS;

	public static final HttpConfig getDefaultConfig() {
		return new HttpConfig();
	}

	public int getTimeOut() {
		return timeOut;
	}

	public void setTimeOut(int timeOut) {
		this.timeOut = timeOut;
	}

	public String getCharSet() {
		return charSet;
	}

	/**
	 * default charset is UTF-8
	 * 
	 * @param charSet
	 */
	public void setCharSet(String charSet) {
		this.charSet = charSet;
	}

	public int getPoolThreads() {
		return poolThreads;
	}

	public void setPoolThreads(int poolThreads) {
		this.poolThreads = poolThreads;
	}

}
