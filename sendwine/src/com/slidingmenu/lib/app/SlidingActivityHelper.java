/**   
* @Title: SlidingActivityHelper.java 
* @Package com.slidingmenu.lib.app 
* @Description: 
*			澹版槑:
*			1)鎺屾帶鏍″洯鏄湰浜虹殑澶у鐨勫垱涓氫綔鍝�涔熸槸鏈汉鐨勬瘯涓氳璁�*			2)鏈▼搴忓皻鏈繘琛屽紑鏀炬簮浠ｇ爜,鎵�互绂佹缁勭粐鎴栬�鏄釜浜烘硠闇叉簮鐮�鍚﹀垯灏嗕細杩界┒鍏跺垜浜嬭矗浠�*			3)缂栧啓鏈蒋浠�鎴戦渶瑕佹劅璋㈠江鑰佸笀浠ュ強鍏朵粬榧撳姳鍜屾敮鎸佹垜鐨勫悓瀛︿互鍙婃湅鍙�*			4)鏈▼搴忕殑鏈�粓鎵�湁鏉冨睘浜庢湰浜�
* @author  寮犻浄 794857063@qq.com
* @date 2013-11-14 19:33:59 
* @version V1.0   
*/
package com.slidingmenu.lib.app;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;


import com.sendwine.app.R;
import com.slidingmenu.lib.SlidingMenu;

// TODO: Auto-generated Javadoc
/**
 * The Class SlidingActivityHelper.
 */
public class SlidingActivityHelper {

	/** The m activity. */
	private Activity mActivity;

	/** The m sliding menu. */
	private SlidingMenu mSlidingMenu;

	/** The m view above. */
	private View mViewAbove;

	/** The m view behind. */
	private View mViewBehind;

	/** The m broadcasting. */
	private boolean mBroadcasting = false;

	/** The m on post create called. */
	private boolean mOnPostCreateCalled = false;

	/** The m enable slide. */
	private boolean mEnableSlide = true;

	/**
	 * Instantiates a new SlidingActivityHelper.
	 * 
	 * @param activity
	 *            the associated activity
	 */
	public SlidingActivityHelper(Activity activity) {
		mActivity = activity;
	}

	/**
	 * Sets mSlidingMenu as a newly inflated SlidingMenu. Should be called
	 * within the activitiy's onCreate()
	 * 
	 * @param savedInstanceState
	 *            the saved instance state (unused)
	 */
	public void onCreate(Bundle savedInstanceState) {
		mSlidingMenu = (SlidingMenu) LayoutInflater.from(mActivity).inflate(
				R.layout.slidingmenumain, null);
	}

	/**
	 * Further SlidingMenu initialization. Should be called within the
	 * activitiy's onPostCreate()
	 * 
	 * @param savedInstanceState
	 *            the saved instance state (unused)
	 */
	public void onPostCreate(Bundle savedInstanceState) {
		if (mViewBehind == null || mViewAbove == null) {
			throw new IllegalStateException(
					"Both setBehindContentView must be called "
							+ "in onCreate in addition to setContentView.");
		}

		mOnPostCreateCalled = true;

		mSlidingMenu.attachToActivity(mActivity,
				mEnableSlide ? SlidingMenu.SLIDING_WINDOW
						: SlidingMenu.SLIDING_CONTENT);

		final boolean open;
		final boolean secondary;
		if (savedInstanceState != null) {
			open = savedInstanceState.getBoolean("SlidingActivityHelper.open");
			secondary = savedInstanceState
					.getBoolean("SlidingActivityHelper.secondary");
		} else {
			open = false;
			secondary = false;
		}
		new Handler().post(new Runnable() {
			@Override
			public void run() {
				if (open) {
					if (secondary) {
						mSlidingMenu.showSecondaryMenu(false);
					} else {
						mSlidingMenu.showMenu(false);
					}
				} else {
					mSlidingMenu.showContent(false);
				}
			}
		});
	}

	/**
	 * Controls whether the ActionBar slides along with the above view when the
	 * menu is opened, or if it stays in place.
	 * 
	 * @param slidingActionBarEnabled
	 *            True if you want the ActionBar to slide along with the
	 *            SlidingMenu, false if you want the ActionBar to stay in place
	 */
	public void setSlidingActionBarEnabled(boolean slidingActionBarEnabled) {
		if (mOnPostCreateCalled)
			throw new IllegalStateException(
					"enableSlidingActionBar must be called in onCreate.");
		mEnableSlide = slidingActionBarEnabled;
	}

	/**
	 * Finds a view that was identified by the id attribute from the XML that
	 * was processed in onCreate(Bundle).
	 * 
	 * @param id
	 *            the resource id of the desired view
	 * @return The view if found or null otherwise.
	 */
	public View findViewById(int id) {
		View v;
		if (mSlidingMenu != null) {
			v = mSlidingMenu.findViewById(id);
			if (v != null)
				return v;
		}
		return null;
	}

	/**
	 * Called to retrieve per-instance state from an activity before being
	 * killed so that the state can be restored in onCreate(Bundle) or
	 * onRestoreInstanceState(Bundle) (the Bundle populated by this method will
	 * be passed to both).
	 * 
	 * @param outState
	 *            Bundle in which to place your saved state.
	 */
	public void onSaveInstanceState(Bundle outState) {
		outState.putBoolean("SlidingActivityHelper.open",
				mSlidingMenu.isMenuShowing());
		outState.putBoolean("SlidingActivityHelper.secondary",
				mSlidingMenu.isSecondaryMenuShowing());
	}

	/**
	 * Register the above content view.
	 * 
	 * @param v
	 *            the above content view to register
	 * @param params
	 *            LayoutParams for that view (unused)
	 */
	public void registerAboveContentView(View v, LayoutParams params) {
		if (!mBroadcasting)
			mViewAbove = v;
	}

	/**
	 * Set the activity content to an explicit view. This view is placed
	 * directly into the activity's view hierarchy. It can itself be a complex
	 * view hierarchy. When calling this method, the layout parameters of the
	 * specified view are ignored. Both the width and the height of the view are
	 * set by default to MATCH_PARENT. To use your own layout parameters, invoke
	 * setContentView(android.view.View, android.view.ViewGroup.LayoutParams)
	 * instead.
	 * 
	 * @param v
	 *            The desired content to display.
	 */
	public void setContentView(View v) {
		mBroadcasting = true;
		mActivity.setContentView(v);
	}

	/**
	 * Set the behind view content to an explicit view. This view is placed
	 * directly into the behind view 's view hierarchy. It can itself be a
	 * complex view hierarchy.
	 * 
	 * @param view
	 *            The desired content to display.
	 * @param layoutParams
	 *            Layout parameters for the view. (unused)
	 */
	public void setBehindContentView(View view, LayoutParams layoutParams) {
		mViewBehind = view;
		mSlidingMenu.setMenu(mViewBehind);
	}
	
	public void setSecondaryMenu(View view, LayoutParams layoutParams){
		mSlidingMenu.setSecondaryMenu(mViewBehind);
	}

	/**
	 * Gets the SlidingMenu associated with this activity.
	 * 
	 * @return the SlidingMenu associated with this activity.
	 */
	public SlidingMenu getSlidingMenu() {
		return mSlidingMenu;
	}

	/**
	 * Toggle the SlidingMenu. If it is open, it will be closed, and vice versa.
	 */
	public void toggle() {
		mSlidingMenu.toggle();
	}

	/**
	 * Close the SlidingMenu and show the content view.
	 */
	public void showContent() {
		mSlidingMenu.showContent();
	}

	/**
	 * Open the SlidingMenu and show the menu view.
	 */
	public void showMenu() {
		mSlidingMenu.showMenu();
	}

	/**
	 * Open the SlidingMenu and show the secondary menu view. Will default to
	 * the regular menu if there is only one.
	 */
	public void showSecondaryMenu() {
		mSlidingMenu.showSecondaryMenu();
	}

	/**
	 * On key up.
	 * 
	 * @param keyCode
	 *            the key code
	 * @param event
	 *            the event
	 * @return true, if successful
	 */
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK && mSlidingMenu.isMenuShowing()) {
			showContent();
			return true;
		}
		return false;
	}

}
