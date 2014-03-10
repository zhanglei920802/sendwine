/**   
* @Title: ActionBarSherlockNative.java 
* @Package com.actionbarsherlock.internal 
* @Description: 
*			声明:
*			1)掌控校园是本人的大学的创业作品,也是本人的毕业设计
*			2)本程序尚未进行开放源代码,所以禁止组织或者是个人泄露源码,否则将会追究其刑事责任
*			3)编写本软件,我需要感谢彭老师以及其他鼓励和支持我的同学以及朋友
*			4)本程序的最终所有权属于本人	
* @author  张雷 794857063@qq.com
* @date 2013-11-14 19:34:19 
* @version V1.0   
*/
package com.actionbarsherlock.internal;

import com.actionbarsherlock.ActionBarSherlock;
import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.internal.app.ActionBarWrapper;
import com.actionbarsherlock.internal.view.menu.MenuWrapper;
import com.actionbarsherlock.view.ActionMode;
import com.actionbarsherlock.view.MenuInflater;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.util.TypedValue;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.view.Window;
import android.view.ViewGroup.LayoutParams;

// TODO: Auto-generated Javadoc
/**
 * The Class ActionBarSherlockNative.
 */
@ActionBarSherlock.Implementation(api = 14)
public class ActionBarSherlockNative extends ActionBarSherlock {
	
	/** The m action bar. */
	private ActionBarWrapper mActionBar;
	
	/** The m action mode. */
	private ActionModeWrapper mActionMode;
	
	/** The m menu. */
	private MenuWrapper mMenu;

	/**
	 * Instantiates a new action bar sherlock native.
	 * 
	 * @param activity
	 *            the activity
	 * @param flags
	 *            the flags
	 */
	public ActionBarSherlockNative(Activity activity, int flags) {

		super(activity, flags);
		System.out.println("ActionBarSherlockNative.ActionBarSherlockNative()");
	}

	/** 
	* <p>Title: getActionBar</p> 
	* <p>Description: </p> 
	* @return 
	* @see com.actionbarsherlock.ActionBarSherlock#getActionBar() 
	*/
	@Override
	public ActionBar getActionBar() {
		if (DEBUG)
			Log.d(TAG, "[getActionBar]");

		initActionBar();
		return mActionBar;
	}

	/**
	 * Inits the action bar.
	 */
	private void initActionBar() {
		if (mActionBar != null || mActivity.getActionBar() == null) {
			return;
		}

		mActionBar = new ActionBarWrapper(mActivity);
	}

	/** 
	* <p>Title: dispatchInvalidateOptionsMenu</p> 
	* <p>Description: </p>  
	* @see com.actionbarsherlock.ActionBarSherlock#dispatchInvalidateOptionsMenu() 
	*/
	@Override
	public void dispatchInvalidateOptionsMenu() {
		if (DEBUG)
			Log.d(TAG, "[dispatchInvalidateOptionsMenu]");

		mActivity.getWindow().invalidatePanelMenu(Window.FEATURE_OPTIONS_PANEL);
	}

	/** 
	* <p>Title: dispatchCreateOptionsMenu</p> 
	* <p>Description: </p> 
	* @param menu
	* @return 
	* @see com.actionbarsherlock.ActionBarSherlock#dispatchCreateOptionsMenu(android.view.Menu) 
	*/
	@Override
	public boolean dispatchCreateOptionsMenu(android.view.Menu menu) {
		if (DEBUG)
			Log.d(TAG, "[dispatchCreateOptionsMenu] menu: " + menu);

		if (mMenu == null || menu != mMenu.unwrap()) {
			mMenu = new MenuWrapper(menu);
		}

		final boolean result = callbackCreateOptionsMenu(mMenu);
		if (DEBUG)
			Log.d(TAG, "[dispatchCreateOptionsMenu] returning " + result);
		return result;
	}

	/** 
	* <p>Title: dispatchPrepareOptionsMenu</p> 
	* <p>Description: </p> 
	* @param menu
	* @return 
	* @see com.actionbarsherlock.ActionBarSherlock#dispatchPrepareOptionsMenu(android.view.Menu) 
	*/
	@Override
	public boolean dispatchPrepareOptionsMenu(android.view.Menu menu) {
		if (DEBUG)
			Log.d(TAG, "[dispatchPrepareOptionsMenu] menu: " + menu);

		final boolean result = callbackPrepareOptionsMenu(mMenu);
		if (DEBUG)
			Log.d(TAG, "[dispatchPrepareOptionsMenu] returning " + result);
		return result;
	}

	/** 
	* <p>Title: dispatchOptionsItemSelected</p> 
	* <p>Description: </p> 
	* @param item
	* @return 
	* @see com.actionbarsherlock.ActionBarSherlock#dispatchOptionsItemSelected(android.view.MenuItem) 
	*/
	@Override
	public boolean dispatchOptionsItemSelected(android.view.MenuItem item) {
		if (DEBUG)
			Log.d(TAG,
					"[dispatchOptionsItemSelected] item: "
							+ item.getTitleCondensed());

		final boolean result = callbackOptionsItemSelected(mMenu.findItem(item));
		if (DEBUG)
			Log.d(TAG, "[dispatchOptionsItemSelected] returning " + result);
		return result;
	}

	/** 
	* <p>Title: hasFeature</p> 
	* <p>Description: </p> 
	* @param feature
	* @return 
	* @see com.actionbarsherlock.ActionBarSherlock#hasFeature(int) 
	*/
	@Override
	public boolean hasFeature(int feature) {
		if (DEBUG)
			Log.d(TAG, "[hasFeature] feature: " + feature);

		final boolean result = mActivity.getWindow().hasFeature(feature);
		if (DEBUG)
			Log.d(TAG, "[hasFeature] returning " + result);
		return result;
	}

	/** 
	* <p>Title: requestFeature</p> 
	* <p>Description: </p> 
	* @param featureId
	* @return 
	* @see com.actionbarsherlock.ActionBarSherlock#requestFeature(int) 
	*/
	@Override
	public boolean requestFeature(int featureId) {
		if (DEBUG)
			Log.d(TAG, "[requestFeature] featureId: " + featureId);

		final boolean result = mActivity.getWindow().requestFeature(featureId);
		if (DEBUG)
			Log.d(TAG, "[requestFeature] returning " + result);
		return result;
	}

	/** 
	* <p>Title: setUiOptions</p> 
	* <p>Description: </p> 
	* @param uiOptions 
	* @see com.actionbarsherlock.ActionBarSherlock#setUiOptions(int) 
	*/
	@Override
	public void setUiOptions(int uiOptions) {
		if (DEBUG)
			Log.d(TAG, "[setUiOptions] uiOptions: " + uiOptions);

		mActivity.getWindow().setUiOptions(uiOptions);
	}

	/** 
	* <p>Title: setUiOptions</p> 
	* <p>Description: </p> 
	* @param uiOptions
	* @param mask 
	* @see com.actionbarsherlock.ActionBarSherlock#setUiOptions(int, int) 
	*/
	@Override
	public void setUiOptions(int uiOptions, int mask) {
		if (DEBUG)
			Log.d(TAG, "[setUiOptions] uiOptions: " + uiOptions + ", mask: "
					+ mask);

		mActivity.getWindow().setUiOptions(uiOptions, mask);
	}

	/** 
	* <p>Title: setContentView</p> 
	* <p>Description: </p> 
	* @param layoutResId 
	* @see com.actionbarsherlock.ActionBarSherlock#setContentView(int) 
	*/
	@Override
	public void setContentView(int layoutResId) {
		if (DEBUG)
			Log.d(TAG, "[setContentView] layoutResId: " + layoutResId);

		mActivity.getWindow().setContentView(layoutResId);
		initActionBar();
	}

	/** 
	* <p>Title: setContentView</p> 
	* <p>Description: </p> 
	* @param view
	* @param params 
	* @see com.actionbarsherlock.ActionBarSherlock#setContentView(android.view.View, android.view.ViewGroup.LayoutParams) 
	*/
	@Override
	public void setContentView(View view, LayoutParams params) {
		Log.d(TAG, "ActionBarSherlockNative:[setContentView] view: " + view
				+ ", params: " + params);

		mActivity.getWindow().setContentView(view, params);
		initActionBar();
	}

	/** 
	* <p>Title: addContentView</p> 
	* <p>Description: </p> 
	* @param view
	* @param params 
	* @see com.actionbarsherlock.ActionBarSherlock#addContentView(android.view.View, android.view.ViewGroup.LayoutParams) 
	*/
	@Override
	public void addContentView(View view, LayoutParams params) {
		if (DEBUG)
			Log.d(TAG, "[addContentView] view: " + view + ", params: " + params);

		mActivity.getWindow().addContentView(view, params);
		initActionBar();
	}

	/** 
	* <p>Title: setTitle</p> 
	* <p>Description: </p> 
	* @param title 
	* @see com.actionbarsherlock.ActionBarSherlock#setTitle(java.lang.CharSequence) 
	*/
	@Override
	public void setTitle(CharSequence title) {
		if (DEBUG)
			Log.d(TAG, "[setTitle] title: " + title);

		mActivity.getWindow().setTitle(title);
	}

	/** 
	* <p>Title: setProgressBarVisibility</p> 
	* <p>Description: </p> 
	* @param visible 
	* @see com.actionbarsherlock.ActionBarSherlock#setProgressBarVisibility(boolean) 
	*/
	@Override
	public void setProgressBarVisibility(boolean visible) {
		if (DEBUG)
			Log.d(TAG, "[setProgressBarVisibility] visible: " + visible);

		mActivity.setProgressBarVisibility(visible);
	}

	/** 
	* <p>Title: setProgressBarIndeterminateVisibility</p> 
	* <p>Description: </p> 
	* @param visible 
	* @see com.actionbarsherlock.ActionBarSherlock#setProgressBarIndeterminateVisibility(boolean) 
	*/
	@Override
	public void setProgressBarIndeterminateVisibility(boolean visible) {
		if (DEBUG)
			Log.d(TAG, "[setProgressBarIndeterminateVisibility] visible: "
					+ visible);

		mActivity.setProgressBarIndeterminateVisibility(visible);
	}

	/** 
	* <p>Title: setProgressBarIndeterminate</p> 
	* <p>Description: </p> 
	* @param indeterminate 
	* @see com.actionbarsherlock.ActionBarSherlock#setProgressBarIndeterminate(boolean) 
	*/
	@Override
	public void setProgressBarIndeterminate(boolean indeterminate) {
		if (DEBUG)
			Log.d(TAG, "[setProgressBarIndeterminate] indeterminate: "
					+ indeterminate);

		mActivity.setProgressBarIndeterminate(indeterminate);
	}

	/** 
	* <p>Title: setProgress</p> 
	* <p>Description: </p> 
	* @param progress 
	* @see com.actionbarsherlock.ActionBarSherlock#setProgress(int) 
	*/
	@Override
	public void setProgress(int progress) {
		if (DEBUG)
			Log.d(TAG, "[setProgress] progress: " + progress);

		mActivity.setProgress(progress);
	}

	/** 
	* <p>Title: setSecondaryProgress</p> 
	* <p>Description: </p> 
	* @param secondaryProgress 
	* @see com.actionbarsherlock.ActionBarSherlock#setSecondaryProgress(int) 
	*/
	@Override
	public void setSecondaryProgress(int secondaryProgress) {
		if (DEBUG)
			Log.d(TAG, "[setSecondaryProgress] secondaryProgress: "
					+ secondaryProgress);

		mActivity.setSecondaryProgress(secondaryProgress);
	}

	/** 
	* <p>Title: getThemedContext</p> 
	* <p>Description: </p> 
	* @return 
	* @see com.actionbarsherlock.ActionBarSherlock#getThemedContext() 
	*/
	@Override
	protected Context getThemedContext() {
		Context context = mActivity;
		TypedValue outValue = new TypedValue();
		mActivity.getTheme().resolveAttribute(
				android.R.attr.actionBarWidgetTheme, outValue, true);
		if (outValue.resourceId != 0) {
			// We are unable to test if this is the same as our current theme
			// so we just wrap it and hope that if the attribute was specified
			// then the user is intentionally specifying an alternate theme.
			context = new ContextThemeWrapper(context, outValue.resourceId);
		}
		return context;
	}

	/** 
	* <p>Title: startActionMode</p> 
	* <p>Description: </p> 
	* @param callback
	* @return 
	* @see com.actionbarsherlock.ActionBarSherlock#startActionMode(com.actionbarsherlock.view.ActionMode.Callback) 
	*/
	@Override
	public ActionMode startActionMode(
			com.actionbarsherlock.view.ActionMode.Callback callback) {
		if (DEBUG)
			Log.d(TAG, "[startActionMode] callback: " + callback);

		if (mActionMode != null) {
			mActionMode.finish();
		}
		ActionModeCallbackWrapper wrapped = null;
		if (callback != null) {
			wrapped = new ActionModeCallbackWrapper(callback);
		}

		// Calling this will trigger the callback wrapper's onCreate which
		// is where we will set the new instance to mActionMode since we need
		// to pass it through to the sherlock callbacks and the call below
		// will not have returned yet to store its value.
		if (mActivity.startActionMode(wrapped) == null) {
			mActionMode = null;
		}
		if (mActivity instanceof OnActionModeStartedListener
				&& mActionMode != null) {
			((OnActionModeStartedListener) mActivity)
					.onActionModeStarted(mActionMode);
		}

		return mActionMode;
	}

	/**
	 * The Class ActionModeCallbackWrapper.
	 */
	private class ActionModeCallbackWrapper implements
			android.view.ActionMode.Callback {
		
		/** The m callback. */
		private final ActionMode.Callback mCallback;

		/**
		 * Instantiates a new action mode callback wrapper.
		 * 
		 * @param callback
		 *            the callback
		 */
		public ActionModeCallbackWrapper(ActionMode.Callback callback) {
			mCallback = callback;
		}

		/** 
		* <p>Title: onCreateActionMode</p> 
		* <p>Description: </p> 
		* @param mode
		* @param menu
		* @return 
		* @see android.view.ActionMode.Callback#onCreateActionMode(android.view.ActionMode, android.view.Menu) 
		*/
		@Override
		public boolean onCreateActionMode(android.view.ActionMode mode,
				android.view.Menu menu) {
			// See ActionBarSherlockNative#startActionMode
			mActionMode = new ActionModeWrapper(mode);

			return mCallback.onCreateActionMode(mActionMode,
					mActionMode.getMenu());
		}

		/** 
		* <p>Title: onPrepareActionMode</p> 
		* <p>Description: </p> 
		* @param mode
		* @param menu
		* @return 
		* @see android.view.ActionMode.Callback#onPrepareActionMode(android.view.ActionMode, android.view.Menu) 
		*/
		@Override
		public boolean onPrepareActionMode(android.view.ActionMode mode,
				android.view.Menu menu) {
			return mCallback.onPrepareActionMode(mActionMode,
					mActionMode.getMenu());
		}

		/** 
		* <p>Title: onActionItemClicked</p> 
		* <p>Description: </p> 
		* @param mode
		* @param item
		* @return 
		* @see android.view.ActionMode.Callback#onActionItemClicked(android.view.ActionMode, android.view.MenuItem) 
		*/
		@Override
		public boolean onActionItemClicked(android.view.ActionMode mode,
				android.view.MenuItem item) {
			return mCallback.onActionItemClicked(mActionMode, mActionMode
					.getMenu().findItem(item));
		}

		/** 
		* <p>Title: onDestroyActionMode</p> 
		* <p>Description: </p> 
		* @param mode 
		* @see android.view.ActionMode.Callback#onDestroyActionMode(android.view.ActionMode) 
		*/
		@Override
		public void onDestroyActionMode(android.view.ActionMode mode) {
			mCallback.onDestroyActionMode(mActionMode);
			if (mActivity instanceof OnActionModeFinishedListener) {
				((OnActionModeFinishedListener) mActivity)
						.onActionModeFinished(mActionMode);
			}
		}
	}

	/**
	 * The Class ActionModeWrapper.
	 */
	private class ActionModeWrapper extends ActionMode {
		
		/** The m action mode. */
		private final android.view.ActionMode mActionMode;
		
		/** The m menu. */
		private MenuWrapper mMenu = null;

		/**
		 * Instantiates a new action mode wrapper.
		 * 
		 * @param actionMode
		 *            the action mode
		 */
		ActionModeWrapper(android.view.ActionMode actionMode) {
			mActionMode = actionMode;
		}

		/** 
		* <p>Title: setTitle</p> 
		* <p>Description: </p> 
		* @param title 
		* @see com.actionbarsherlock.view.ActionMode#setTitle(java.lang.CharSequence) 
		*/
		@Override
		public void setTitle(CharSequence title) {
			mActionMode.setTitle(title);
		}

		/** 
		* <p>Title: setTitle</p> 
		* <p>Description: </p> 
		* @param resId 
		* @see com.actionbarsherlock.view.ActionMode#setTitle(int) 
		*/
		@Override
		public void setTitle(int resId) {
			mActionMode.setTitle(resId);
		}

		/** 
		* <p>Title: setSubtitle</p> 
		* <p>Description: </p> 
		* @param subtitle 
		* @see com.actionbarsherlock.view.ActionMode#setSubtitle(java.lang.CharSequence) 
		*/
		@Override
		public void setSubtitle(CharSequence subtitle) {
			mActionMode.setSubtitle(subtitle);
		}

		/** 
		* <p>Title: setSubtitle</p> 
		* <p>Description: </p> 
		* @param resId 
		* @see com.actionbarsherlock.view.ActionMode#setSubtitle(int) 
		*/
		@Override
		public void setSubtitle(int resId) {
			mActionMode.setSubtitle(resId);
		}

		/** 
		* <p>Title: setCustomView</p> 
		* <p>Description: </p> 
		* @param view 
		* @see com.actionbarsherlock.view.ActionMode#setCustomView(android.view.View) 
		*/
		@Override
		public void setCustomView(View view) {
			mActionMode.setCustomView(view);
		}

		/** 
		* <p>Title: invalidate</p> 
		* <p>Description: </p>  
		* @see com.actionbarsherlock.view.ActionMode#invalidate() 
		*/
		@Override
		public void invalidate() {
			mActionMode.invalidate();
		}

		/** 
		* <p>Title: finish</p> 
		* <p>Description: </p>  
		* @see com.actionbarsherlock.view.ActionMode#finish() 
		*/
		@Override
		public void finish() {
			mActionMode.finish();
		}

		/** 
		* <p>Title: getMenu</p> 
		* <p>Description: </p> 
		* @return 
		* @see com.actionbarsherlock.view.ActionMode#getMenu() 
		*/
		@Override
		public MenuWrapper getMenu() {
			if (mMenu == null) {
				mMenu = new MenuWrapper(mActionMode.getMenu());
			}
			return mMenu;
		}

		/** 
		* <p>Title: getTitle</p> 
		* <p>Description: </p> 
		* @return 
		* @see com.actionbarsherlock.view.ActionMode#getTitle() 
		*/
		@Override
		public CharSequence getTitle() {
			return mActionMode.getTitle();
		}

		/** 
		* <p>Title: getSubtitle</p> 
		* <p>Description: </p> 
		* @return 
		* @see com.actionbarsherlock.view.ActionMode#getSubtitle() 
		*/
		@Override
		public CharSequence getSubtitle() {
			return mActionMode.getSubtitle();
		}

		/** 
		* <p>Title: getCustomView</p> 
		* <p>Description: </p> 
		* @return 
		* @see com.actionbarsherlock.view.ActionMode#getCustomView() 
		*/
		@Override
		public View getCustomView() {
			return mActionMode.getCustomView();
		}

		/** 
		* <p>Title: getMenuInflater</p> 
		* <p>Description: </p> 
		* @return 
		* @see com.actionbarsherlock.view.ActionMode#getMenuInflater() 
		*/
		@Override
		public MenuInflater getMenuInflater() {
			return ActionBarSherlockNative.this.getMenuInflater();
		}

		/** 
		* <p>Title: setTag</p> 
		* <p>Description: </p> 
		* @param tag 
		* @see com.actionbarsherlock.view.ActionMode#setTag(java.lang.Object) 
		*/
		@Override
		public void setTag(Object tag) {
			mActionMode.setTag(tag);
		}

		/** 
		* <p>Title: getTag</p> 
		* <p>Description: </p> 
		* @return 
		* @see com.actionbarsherlock.view.ActionMode#getTag() 
		*/
		@Override
		public Object getTag() {
			return mActionMode.getTag();
		}
	}
}
