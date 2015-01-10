package net.nova123.lib.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Base64;

/**
 * This class is used to save object and get object from sharePreference file.
 * this file content is encoded.
 * 
 * @author nova
 * 
 */
public class SharePreferenceUtil {

	/**
	 * This is the default sharePreference file name, you can change the file
	 * name here.
	 */
	private String mFileName;

	public SharePreferenceUtil(String sharePreferenceFileName) {
		this.mFileName = sharePreferenceFileName;
	}

	/**
	 * Save a object to sharePreference file with base64 encode.
	 * 
	 * @param context
	 * @param key
	 * @param obj
	 * @return true save success. false save failed.
	 */
	public final boolean saveObject(Context context, String key, Object obj) {
		SharedPreferences preferences = context.getSharedPreferences(mFileName, Context.MODE_PRIVATE);
		Editor editor = preferences.edit();
		if (obj == null) {
			editor.putString(key, null);
			editor.commit();
		} else {
			ByteArrayOutputStream bos = null;
			ObjectOutputStream oos = null;
			try {
				bos = new ByteArrayOutputStream();
				oos = new ObjectOutputStream(bos);
				oos.writeObject(obj);
				String str = new String(Base64.encode(bos.toByteArray(), Base64.DEFAULT));
				editor.putString(key, str);
				editor.commit();
			} catch (IOException e) {
				e.printStackTrace();
				return false;
			} finally {
				try {
					if (bos != null)
						bos.close();
					if (oos != null)
						oos.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return true;
	}

	/**
	 * Get a saved object from sharePreference file.
	 * 
	 * @param context
	 * @param key
	 * @return notice:this function may return null!
	 */
	public final Object getObject(Context context, String key) {
		SharedPreferences preferences = context.getSharedPreferences(mFileName, Context.MODE_PRIVATE);
		String str = preferences.getString(key, null);
		Object obj = null;
		if (str == null) {
			return null;
		} else {
			byte[] bytes = Base64.decode(str.getBytes(), Base64.DEFAULT);
			ByteArrayInputStream bis = null;
			ObjectInputStream ois = null;
			try {
				bis = new ByteArrayInputStream(bytes);
				ois = new ObjectInputStream(bis);
				obj = ois.readObject();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					if (bis != null)
						bis.close();
					if (ois != null)
						ois.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return obj;
	}

}
