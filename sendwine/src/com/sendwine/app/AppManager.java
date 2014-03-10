/**   
 * @Title: AppManager.java 
 * @Package com.sendwine.app 
 * @Description: 
 *			
 * @author  张雷 794857063@qq.com
 * @date 2014-1-1 下午12:05:54 
 * @version V1.0   
 */

package com.sendwine.app;

import java.util.Stack;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;

/**
 * @ClassName: AppManager
 * @Description: TODO 应用程序管理类
 * @author 张雷 794857063@qq.com
 * @date 2014-1-1 下午12:05:54
 * 
 */
public class AppManager {

	/** The activity stack. */
	private static Stack<Activity> activityStack = null;

	/** The m app manger. */
	public static AppManager mAppManger = null;

	/**
	 * Gets the activity stack.
	 * 
	 * @return the activity stack
	 */
	public static Stack<Activity> getActivityStack() {
		return activityStack;
	}

	/**
	 * 使用单例模式获取一个实例
	 * 
	 * @return single instance of AppManager
	 */
	public static AppManager getInstance() {
		if (null == mAppManger) {
			mAppManger = new AppManager();
		}
		return mAppManger;
	}

	/**
	 * 向堆栈中压入activity
	 * 
	 * @param activity
	 *            the activity
	 */
	public void addActivity(Activity activity) {
		if (activityStack == null) {
			activityStack = new Stack<Activity>();
		}
		activityStack.add(activity);
	}

	/**
	 * 退出应用程序
	 * 
	 * @param context
	 *            the context
	 */
	@SuppressWarnings("deprecation")
	public void AppExit(Context context) {
		try {
			finishAllActivity();
			ActivityManager activityMgr = (ActivityManager) context
					.getSystemService(Context.ACTIVITY_SERVICE);
			activityMgr.restartPackage(context.getPackageName());

			System.exit(0);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * 获取当前的Activity
	 * 
	 * @return the activity
	 */
	public Activity currentActivity() {
		Activity activity = activityStack.lastElement();
		return activity;
	}

	/**
	 * 出栈一个元素
	 */
	public void finishActivity() {
		Activity activity = activityStack.lastElement();
		finishActivity(activity);
	}

	/**
	 * Finish activity.
	 * 
	 * @param activity
	 *            the activity
	 */
	public void finishActivity(Activity activity) {

		if (activity != null) {
			activityStack.remove(activity);
			activity.finish();

		}

	}

	/**
	 * Finish all activity.
	 */
	public void finishAllActivity() {
		for (int i = 0, size = activityStack.size(); i < size; i++) {
			if (null != activityStack.get(i)) {
				activityStack.get(i).finish();
			}
		}
		activityStack.clear();

	}
}
