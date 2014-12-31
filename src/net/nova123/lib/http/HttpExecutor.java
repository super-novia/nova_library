package net.nova123.lib.http;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.os.Handler;

public class HttpExecutor {

	private HttpConfig mConfig = HttpConfig.getDefaultConfig();

	public void setConfig(HttpConfig config) {
		mConfig = config;
	}

	HttpRequestResult executeHttpGet(String urls) {
		BufferedReader reader = null;
		HttpRequestResult result = new HttpRequestResult();
		StringBuilder builder = new StringBuilder();
		try {
			URL url = new URL(urls);
			HttpURLConnection connection = (HttpURLConnection) url
					.openConnection();
			connection.setRequestProperty("content-type", "application/json");
			connection.setRequestProperty("User-Agent", "");
			connection.setConnectTimeout(mConfig.getTimeOut());
			connection.setDoInput(true);
			connection.connect();

			result.setCode(connection.getResponseCode());
			if (connection.getResponseCode() == 200) {
				reader = new BufferedReader(new InputStreamReader(
						connection.getInputStream(), mConfig.getCharSet()));
				String str;
				while ((str = reader.readLine()) != null) {
					builder.append(str);
				}
				result.setResponse(builder.toString());
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (reader != null)
					reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	HttpRequestResult executeHttpPost(String urls, String strs) {
		BufferedReader reader = null;
		HttpRequestResult result = new HttpRequestResult();
		StringBuilder builder = new StringBuilder();

		BufferedWriter bufferedWriter = null;
		try {
			URL url = new URL(urls);
			HttpURLConnection connection = (HttpURLConnection) url
					.openConnection();
			connection.setConnectTimeout(mConfig.getTimeOut());
			connection.setRequestProperty("content-type", "application/json");
			connection.setRequestProperty("User-Agent", "");
			connection.setDoOutput(true);
			connection.setDoInput(true);

			bufferedWriter = new BufferedWriter(new OutputStreamWriter(
					connection.getOutputStream()));
			bufferedWriter.write(strs);
			bufferedWriter.flush();

			connection.connect();
			result.setCode(connection.getResponseCode());
			if (connection.getResponseCode() == 200) {
				// write the content to server

				reader = new BufferedReader(new InputStreamReader(
						connection.getInputStream(), mConfig.getCharSet()));
				String str;
				while ((str = reader.readLine()) != null) {
					builder.append(str);
				}
				result.setResponse(builder.toString());
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (reader != null)
					reader.close();
				if (bufferedWriter != null)
					bufferedWriter.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
}
