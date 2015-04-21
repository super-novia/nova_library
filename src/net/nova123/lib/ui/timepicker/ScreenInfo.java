package net.nova123.lib.ui.timepicker;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

/**
 * �õ���Ļ����ܶȵ�?
 * 
 * @author
 * @email chenshi011@163.com
 * 
 */
public class ScreenInfo {
	private Context context;
	/**
	 * ��Ļ��ȣ����أ�?/
	 * 
	 */
	private int width;
	/** ��Ļ�߶ȣ����أ� */
	private int height;
	/** ��Ļ�ܶȣ�0.75 / 1.0 / 1.5�� */
	private float density;
	/** ��Ļ�ܶ�DPI��120 / 160 / 240�� */
	private int densityDpi;

	public Context getActivity() {
		return context;
	}

	public void setActivity(Context context) {
		this.context = context;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public float getDensity() {
		return density;
	}

	public void setDensity(float density) {
		this.density = density;
	}

	public int getDensityDpi() {
		return densityDpi;
	}

	public void setDensityDpi(int densityDpi) {
		this.densityDpi = densityDpi;
	}

	public ScreenInfo(Context context) {
		this.context = context;
		ini();
	}

	private void ini() {
		DisplayMetrics metric = new DisplayMetrics();
		WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		manager.getDefaultDisplay().getMetrics(metric);
		// ((Activity)
		// activity).getWindowManager().getDefaultDisplay().getMetrics(metric);
		width = metric.widthPixels;
		height = metric.heightPixels;
		density = metric.density;
		densityDpi = metric.densityDpi;
	}

}
