package net.nova123.lib.ui.roundimage;

import net.nova123.lib.R;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.NinePatchDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ImageView;

/**
 * 圆形ImageView，可设置�??多两个宽度不同且颜色不同的圆形边框�??
 * 
 * @author Alan
 */
public class RoundImageView extends ImageView {
	private int mBorderThickness = 0;
	private Context mContext;
	private int defaultColor = 0xFFFFFFFF;
	// 如果只有其中�??个有值，则只画一个圆形边�??
	private int mBorderOutsideColor = 0;
	private int mBorderInsideColor = 0;
	// 控件默认长�?�宽
	private int defaultWidth = 0;
	private int defaultHeight = 0;

	private boolean isPressed;
	private Paint mPaint = new Paint();

	private int pressedColor = 0xaa333333;

	public RoundImageView(Context context) {
		super(context);
		mContext = context;
		initPaint();
	}

	public RoundImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
		setCustomAttributes(attrs);
		initPaint();
	}

	public RoundImageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		mContext = context;
		setCustomAttributes(attrs);
		initPaint();
	}

	private void initPaint() {
		if (mPaint == null)
			mPaint = new Paint();

	}

	private void setCustomAttributes(AttributeSet attrs) {
		TypedArray a = mContext.obtainStyledAttributes(attrs,
				R.styleable.roundedimageview);
		mBorderThickness = a.getDimensionPixelSize(
				R.styleable.roundedimageview_border_thickness, 0);
		mBorderOutsideColor = a
				.getColor(R.styleable.roundedimageview_border_outside_color,
						defaultColor);
		mBorderInsideColor = a.getColor(
				R.styleable.roundedimageview_border_inside_color, defaultColor);
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent event) {
		int x = (int) event.getX();
		int y = (int) event.getY();
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			if (isContainMotion(event)) {
				isPressed = true;
				invalidate();
			}
		} else if (event.getAction() == MotionEvent.ACTION_UP) {
			isPressed = false;
			invalidate();
		} else if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
			isPressed = false;
			invalidate();
		} else if (event.getAction() == MotionEvent.ACTION_MOVE) {
			Log.e("", "x:" + x + "    y:" + y);
			if (!isContainMotion(event)) {
				isPressed = false;
				invalidate();
			}
		}
		return true;
	}

	private boolean isContainMotion(MotionEvent event) {
		float tx = this.getWidth();
		float ty = this.getHeight();
		float mx = event.getX();
		float my = event.getY();
		if (mx < 0 || my < 0 || mx > tx || my > ty) {
			return false;
		}

		float centerX = tx / 2;
		float centerY = ty / 2;
		float distance = (mx - centerX) * (mx - centerX) + (my - centerY)
				* (my - centerY);
		distance = (float) Math.sqrt(distance);
		if (distance > centerX) {
			return false;
		}
		return true;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		Drawable drawable = getDrawable();
		if (drawable == null) {
			return;
		}

		if (getWidth() == 0 || getHeight() == 0) {
			return;
		}
		this.measure(0, 0);
		if (drawable.getClass() == NinePatchDrawable.class)
			return;
		Bitmap b = ((BitmapDrawable) drawable).getBitmap();
		Bitmap bitmap = b.copy(Bitmap.Config.ARGB_8888, true);
		if (defaultWidth == 0) {
			defaultWidth = getWidth();

		}
		if (defaultHeight == 0) {
			defaultHeight = getHeight();
		}
		// 保证重新读取图片后不会因为图片大小�?�改变控件宽、高的大小（针对宽�?�高为wrap_content布局的imageview，但会导致margin无效�??
		// if (defaultWidth != 0 && defaultHeight != 0) {
		// LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
		// defaultWidth, defaultHeight);
		// setLayoutParams(params);
		// }
		int radius = 0;
		if (mBorderInsideColor != defaultColor
				&& mBorderOutsideColor != defaultColor) {// 定义画两个边框，分别为外圆边框和内圆边框
			radius = (defaultWidth < defaultHeight ? defaultWidth
					: defaultHeight) / 2 - 2 * mBorderThickness;
			// 画内�??
			drawCircleBorder(canvas, radius + mBorderThickness / 2,
					mBorderInsideColor);
			// 画外�??
			drawCircleBorder(canvas, radius + mBorderThickness
					+ mBorderThickness / 2, mBorderOutsideColor);
		} else if (mBorderInsideColor != defaultColor
				&& mBorderOutsideColor == defaultColor) {// 定义画一个边�??
			radius = (defaultWidth < defaultHeight ? defaultWidth
					: defaultHeight) / 2 - mBorderThickness;
			drawCircleBorder(canvas, radius + mBorderThickness / 2,
					mBorderInsideColor);
		} else if (mBorderInsideColor == defaultColor
				&& mBorderOutsideColor != defaultColor) {// 定义画一个边�??
			radius = (defaultWidth < defaultHeight ? defaultWidth
					: defaultHeight) / 2 - mBorderThickness;
			drawCircleBorder(canvas, radius + mBorderThickness / 2,
					mBorderOutsideColor);
		} else {// 没有边框
			radius = (defaultWidth < defaultHeight ? defaultWidth
					: defaultHeight) / 2;
		}
		Bitmap roundBitmap = getCroppedRoundBitmap(bitmap, radius);
		canvas.drawBitmap(roundBitmap, defaultWidth / 2 - radius, defaultHeight
				/ 2 - radius, null);

		System.out.println("ispressed:" + isPressed);
		if (isPressed) {
			mPaint.setAntiAlias(true);
			mPaint.setColor(pressedColor);
			canvas.drawCircle(getWidth() / 2, getHeight() / 2, radius, mPaint);
		}
	}

	/**
	 * 获取裁剪后的圆形图片
	 * 
	 * @param radius
	 *            半径
	 */
	public Bitmap getCroppedRoundBitmap(Bitmap bmp, int radius) {
		Bitmap scaledSrcBmp;
		int diameter = radius * 2;

		// 为了防止宽高不相等，造成圆形图片变形，因此截取长方形中处于中间位置最大的正方形图�??
		int bmpWidth = bmp.getWidth();
		int bmpHeight = bmp.getHeight();
		int squareWidth = 0, squareHeight = 0;
		int x = 0, y = 0;
		Bitmap squareBitmap;
		if (bmpHeight > bmpWidth) {// 高大于宽
			squareWidth = squareHeight = bmpWidth;
			x = 0;
			y = (bmpHeight - bmpWidth) / 2;
			// 截取正方形图�??
			squareBitmap = Bitmap.createBitmap(bmp, x, y, squareWidth,
					squareHeight);
		} else if (bmpHeight < bmpWidth) {// 宽大于高
			squareWidth = squareHeight = bmpHeight;
			x = (bmpWidth - bmpHeight) / 2;
			y = 0;
			squareBitmap = Bitmap.createBitmap(bmp, x, y, squareWidth,
					squareHeight);
		} else {
			squareBitmap = bmp;
		}

		if (squareBitmap.getWidth() != diameter
				|| squareBitmap.getHeight() != diameter) {
			scaledSrcBmp = Bitmap.createScaledBitmap(squareBitmap, diameter,
					diameter, true);

		} else {
			scaledSrcBmp = squareBitmap;
		}
		Bitmap output = Bitmap.createBitmap(scaledSrcBmp.getWidth(),
				scaledSrcBmp.getHeight(), Config.ARGB_8888);
		Canvas canvas = new Canvas(output);

		Paint paint = new Paint();
		Rect rect = new Rect(0, 0, scaledSrcBmp.getWidth(),
				scaledSrcBmp.getHeight());

		paint.setAntiAlias(true);
		paint.setFilterBitmap(true);
		paint.setDither(true);
		canvas.drawARGB(0, 0, 0, 0);
		canvas.drawCircle(scaledSrcBmp.getWidth() / 2,
				scaledSrcBmp.getHeight() / 2, scaledSrcBmp.getWidth() / 2,
				paint);
		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		canvas.drawBitmap(scaledSrcBmp, rect, rect, paint);
		// bitmap回收(recycle导致在布�??文件XML看不到效�??)
		// bmp.recycle();
		// squareBitmap.recycle();
		// scaledSrcBmp.recycle();
		bmp = null;
		squareBitmap = null;
		scaledSrcBmp = null;
		return output;
	}

	/**
	 * 边缘画圆
	 */
	private void drawCircleBorder(Canvas canvas, int radius, int color) {
		Paint paint = new Paint();
		/* 去锯�?? */
		paint.setAntiAlias(true);
		paint.setFilterBitmap(true);
		paint.setDither(true);
		paint.setColor(color);
		/* 设置paint的�??style�??为STROKE：空�?? */
		paint.setStyle(Paint.Style.STROKE);
		/* 设置paint的外框宽�?? */
		paint.setStrokeWidth(mBorderThickness);
		canvas.drawCircle(defaultWidth / 2, defaultHeight / 2, radius, paint);
	}

}
