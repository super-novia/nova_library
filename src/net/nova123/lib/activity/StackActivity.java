package net.nova123.lib.activity;

import java.util.LinkedList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

/**
 * This is a class used to manager the activities in the back stack. It is make
 * you easy to close the activities in the back stack,and you can print all the
 * back stack activities too.
 * 
 * @author nova
 *
 */
public class StackActivity extends Activity implements View.OnClickListener {

	/**
	 * The List record all the activities in back stack.
	 */
	private static final List<StackActivity> mList = new LinkedList<StackActivity>();
	private static final String TAG = "StackActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mList.add(this);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		mList.remove(this);
	}

	public List<StackActivity> getBackStackActivityList() {
		return mList;
	}

	/**
	 * Print the back stack activities in order to logCat.devel d.
	 */
	public final void printActivityStack() {
		StringBuilder builder = new StringBuilder();
		for (StackActivity activity : mList) {
			String name = activity.getActivitySimpleName();
			builder.append(name + " --> ");
		}
		Log.d(TAG, builder.toString());
	}

	/**
	 * Get the simpleName of activity.only the class name.
	 * 
	 * @return simpleName of activity.only the class name.
	 */
	public final String getActivitySimpleName() {
		return this.getClass().getSimpleName();
	}

	/**
	 * Get the name of activity. include the packageName.
	 * 
	 * @return name of activity,include the packageName.
	 */
	public final String getActivityName() {
		return this.getClass().getName();
	}

	/**
	 * Close all the activity in back stack until the activity you specified. <br>
	 * Note: <br>
	 * \t1.All the activities above the activity you specified will be
	 * close(finish),then the activity you specified will show .and it will at
	 * the top of back stack activities. <br>
	 * 2.If the activity you specified is not exist in the back stack,it's will
	 * close all the activities in back stack. <br>
	 * 
	 * @param cls
	 *            The activity you specified.
	 */
	public final void closeToActivity(Class<?> activity) {
		for (int i = mList.size() - 1; i >= 0; i--) {
			StackActivity activity2 = mList.get(i);
			if (!activity2.getClass().getName().equals(activity.getName())) {
				activity2.finish();
			} else {
				break;
			}
		}
	}

	/**
	 * Close all the activity in back stack until the activity you specified.
	 * But the activity current showing will not close. <br>
	 * Note: <br>
	 * 1.Activities above the activity you specified will be close(finish),But
	 * the activity current showing will keep showing. and the activity you
	 * specified will under the current showing activity in back stack
	 * activities. <br>
	 * 2.If the activity you specified is not exist in the back stack,it's will
	 * close all activities in back stack.Only the current showing activity
	 * still showing,and it is the only one activity in the back stack
	 * activities. <br>
	 * 
	 * @param cls
	 *            The activity you specified.
	 */
	public final void closeBackActivitysTo(Class<?> activity) {
		for (int i = mList.size() - 2; i >= 0; i--) {
			StackActivity activity2 = mList.get(i);
			if (!activity2.getClass().getName().equals(activity.getName())) {
				activity2.finish();
			} else {
				break;
			}
		}
	}

	/**
	 * View.OnClickListener
	 */
	@Override
	public void onClick(View v) {
	}
}
