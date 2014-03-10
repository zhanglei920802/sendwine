/**   
* @Title: Window.java 
* @Package com.actionbarsherlock.view 
* @Description: 
*			声明:
*			1)掌控校园是本人的大学的创业作品,也是本人的毕业设计
*			2)本程序尚未进行开放源代码,所以禁止组织或者是个人泄露源码,否则将会追究其刑事责任
*			3)编写本软件,我需要感谢彭老师以及其他鼓励和支持我的同学以及朋友
*			4)本程序的最终所有权属于本人	
* @author  张雷 794857063@qq.com
* @date 2013-11-14 19:45:03 
* @version V1.0   
*/

package com.actionbarsherlock.view;

import android.content.Context;

// TODO: Auto-generated Javadoc
/**
 * <p>
 * Abstract base class for a top-level window look and behavior policy. An
 * instance of this class should be used as the top-level view added to the
 * window manager. It provides standard UI policies such as a background, title
 * area, default key processing, etc.
 * </p>
 * 
 * <p>
 * The only existing implementation of this abstract class is
 * android.policy.PhoneWindow, which you should instantiate when needing a
 * Window. Eventually that class will be refactored and a factory method added
 * for creating Window instances without knowing about a particular
 * implementation.
 * </p>
 */
public abstract class Window extends android.view.Window {
	
	/** The Constant FEATURE_ACTION_BAR. */
	public static final long FEATURE_ACTION_BAR = android.view.Window.FEATURE_ACTION_BAR;
	
	/** The Constant FEATURE_ACTION_BAR_OVERLAY. */
	public static final long FEATURE_ACTION_BAR_OVERLAY = android.view.Window.FEATURE_ACTION_BAR_OVERLAY;
	
	/** The Constant FEATURE_ACTION_MODE_OVERLAY. */
	public static final long FEATURE_ACTION_MODE_OVERLAY = android.view.Window.FEATURE_ACTION_MODE_OVERLAY;
	
	/** The Constant FEATURE_NO_TITLE. */
	public static final long FEATURE_NO_TITLE = android.view.Window.FEATURE_NO_TITLE;
	
	/** The Constant FEATURE_PROGRESS. */
	public static final long FEATURE_PROGRESS = android.view.Window.FEATURE_PROGRESS;
	
	/** The Constant FEATURE_INDETERMINATE_PROGRESS. */
	public static final long FEATURE_INDETERMINATE_PROGRESS = android.view.Window.FEATURE_INDETERMINATE_PROGRESS;

	/**
	 * Create a new instance for a context.
	 * 
	 * @param context
	 *            Context.
	 */
	private Window(Context context) {
		super(context);
	}

	/**
	 * The Interface Callback.
	 */
	public interface Callback {
		/**
		 * Called when a panel's menu item has been selected by the user.
		 * 
		 * @param featureId
		 *            The panel that the menu is in.
		 * @param item
		 *            The menu item that was selected.
		 * 
		 * @return boolean Return true to finish processing of selection, or
		 *         false to perform the normal menu handling (calling its
		 *         Runnable or sending a Message to its target Handler).
		 */
		public boolean onMenuItemSelected(int featureId, MenuItem item);
	}
}
