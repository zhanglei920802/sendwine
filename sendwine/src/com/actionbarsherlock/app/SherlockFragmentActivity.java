/**   
* @Title: SherlockFragmentActivity.java 
* @Package com.actionbarsherlock.app 
* @Description: 
*			澹版槑:
*			1)鎺屾帶鏍″洯鏄湰浜虹殑澶у鐨勫垱涓氫綔鍝�涔熸槸鏈汉鐨勬瘯涓氳璁�*			2)鏈▼搴忓皻鏈繘琛屽紑鏀炬簮浠ｇ爜,鎵�互绂佹缁勭粐鎴栬�鏄釜浜烘硠闇叉簮鐮�鍚﹀垯灏嗕細杩界┒鍏跺垜浜嬭矗浠�*			3)缂栧啓鏈蒋浠�鎴戦渶瑕佹劅璋㈠江鑰佸笀浠ュ強鍏朵粬榧撳姳鍜屾敮鎸佹垜鐨勫悓瀛︿互鍙婃湅鍙�*			4)鏈▼搴忕殑鏈�粓鎵�湁鏉冨睘浜庢湰浜�
* @author  寮犻浄 794857063@qq.com
* @date 2013-11-14 19:33:48 
* @version V1.0   
*/
package com.actionbarsherlock.app;

import android.content.res.Configuration;
import android.os.Bundle;
import com.sendwine.app.R;

import android.support.v4.app.Watson;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import com.actionbarsherlock.ActionBarSherlock;
import com.actionbarsherlock.view.ActionMode;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;

import static com.actionbarsherlock.ActionBarSherlock.OnActionModeFinishedListener;
import static com.actionbarsherlock.ActionBarSherlock.OnActionModeStartedListener;

// TODO: Auto-generated Javadoc
/**
 * The Class SherlockFragmentActivity.
 * 
 * @see {@link android.support.v4.app.Watson}
 */
public class SherlockFragmentActivity extends Watson implements
		OnActionModeStartedListener, OnActionModeFinishedListener {
	
	/** The Constant DEBUG. */
	private static final boolean DEBUG = false;
	
	/** The Constant TAG. */
	private static final String TAG = "SherlockFragmentActivity";

	/** The m sherlock. */
	private ActionBarSherlock mSherlock;
	
	/** The m ignore native create. */
	private boolean mIgnoreNativeCreate = false;
	
	/** The m ignore native prepare. */
	private boolean mIgnoreNativePrepare = false;
	
	/** The m ignore native selected. */
	private boolean mIgnoreNativeSelected = false;

	/**
	 * Gets the sherlock.
	 * 
	 * @return the sherlock
	 */
	protected final ActionBarSherlock getSherlock() {
		if (mSherlock == null) {
			// Log.d(TAG, "mSherlock == null锛歵rue");
			mSherlock = ActionBarSherlock.wrap(this,
					ActionBarSherlock.FLAG_DELEGATE);
		}
		return mSherlock;
	}

	// /////////////////////////////////////////////////////////////////////////
	// Action bar and mode
	// /////////////////////////////////////////////////////////////////////////

	/**
	 * Gets the support action bar.
	 * 
	 * @return the support action bar
	 */
	public ActionBar getSupportActionBar() {
		return getSherlock().getActionBar();
	}

	/** 
	* <p>Title: startActionMode</p> 
	* <p>Description: </p> 
	* @param callback
	* @return 
	* @see android.app.Activity#startActionMode(android.view.ActionMode.Callback) 
	*/
	public ActionMode startActionMode(ActionMode.Callback callback) {
		return getSherlock().startActionMode(callback);
	}

	/** 
	* <p>Title: onActionModeStarted</p> 
	* <p>Description: </p> 
	* @param mode 
	* @see android.app.Activity#onActionModeStarted(android.view.ActionMode) 
	*/
	@Override
	public void onActionModeStarted(ActionMode mode) {
	}

	/** 
	* <p>Title: onActionModeFinished</p> 
	* <p>Description: </p> 
	* @param mode 
	* @see android.app.Activity#onActionModeFinished(android.view.ActionMode) 
	*/
	@Override
	public void onActionModeFinished(ActionMode mode) {
	}

	// /////////////////////////////////////////////////////////////////////////
	// General lifecycle/callback dispatching
	// /////////////////////////////////////////////////////////////////////////

	/** 
	* <p>Title: onConfigurationChanged</p> 
	* <p>Description: </p> 
	* @param newConfig 
	* @see android.support.v4.app.FragmentActivity#onConfigurationChanged(android.content.res.Configuration) 
	*/
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		getSherlock().dispatchConfigurationChanged(newConfig);
	}

	/** 
	* <p>Title: onPostResume</p> 
	* <p>Description: </p>  
	* @see android.support.v4.app.FragmentActivity#onPostResume() 
	*/
	@Override
	protected void onPostResume() {
		super.onPostResume();
		getSherlock().dispatchPostResume();
	}

	/** 
	* <p>Title: onPause</p> 
	* <p>Description: </p>  
	* @see android.support.v4.app.FragmentActivity#onPause() 
	*/
	@Override
	protected void onPause() {
		getSherlock().dispatchPause();
		super.onPause();
	}

	/** 
	* <p>Title: onStop</p> 
	* <p>Description: </p>  
	* @see android.support.v4.app.FragmentActivity#onStop() 
	*/
	@Override
	protected void onStop() {
		getSherlock().dispatchStop();
		super.onStop();
	}

	/** 
	* <p>Title: onDestroy</p> 
	* <p>Description: </p>  
	* @see android.support.v4.app.FragmentActivity#onDestroy() 
	*/
	@Override
	protected void onDestroy() {
		getSherlock().dispatchDestroy();
		super.onDestroy();
	}

	/** 
	* <p>Title: onPostCreate</p> 
	* <p>Description: </p> 
	* @param savedInstanceState 
	* @see android.app.Activity#onPostCreate(android.os.Bundle) 
	*/
	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		getSherlock().dispatchPostCreate(savedInstanceState);
		super.onPostCreate(savedInstanceState);
	}

	/** 
	* <p>Title: onTitleChanged</p> 
	* <p>Description: </p> 
	* @param title
	* @param color 
	* @see android.app.Activity#onTitleChanged(java.lang.CharSequence, int) 
	*/
	@Override
	protected void onTitleChanged(CharSequence title, int color) {
		getSherlock().dispatchTitleChanged(title, color);
		super.onTitleChanged(title, color);
	}

	/** 
	* <p>Title: onMenuOpened</p> 
	* <p>Description: </p> 
	* @param featureId
	* @param menu
	* @return 
	* @see android.app.Activity#onMenuOpened(int, android.view.Menu) 
	*/
	@Override
	public final boolean onMenuOpened(int featureId, android.view.Menu menu) {
		if (getSherlock().dispatchMenuOpened(featureId, menu)) {
			return true;
		}
		return super.onMenuOpened(featureId, menu);
	}

	/** 
	* <p>Title: onPanelClosed</p> 
	* <p>Description: </p> 
	* @param featureId
	* @param menu 
	* @see android.support.v4.app.FragmentActivity#onPanelClosed(int, android.view.Menu) 
	*/
	@Override
	public void onPanelClosed(int featureId, android.view.Menu menu) {
		getSherlock().dispatchPanelClosed(featureId, menu);
		super.onPanelClosed(featureId, menu);
	}

	/** 
	* <p>Title: dispatchKeyEvent</p> 
	* <p>Description: </p> 
	* @param event
	* @return 
	* @see android.app.Activity#dispatchKeyEvent(android.view.KeyEvent) 
	*/
	@Override
	public boolean dispatchKeyEvent(KeyEvent event) {
		if (getSherlock().dispatchKeyEvent(event)) {
			return true;
		}
		return super.dispatchKeyEvent(event);
	}

	/** 
	* <p>Title: onSaveInstanceState</p> 
	* <p>Description: </p> 
	* @param outState 
	* @see android.support.v4.app.FragmentActivity#onSaveInstanceState(android.os.Bundle) 
	*/
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		getSherlock().dispatchSaveInstanceState(outState);
	}

	/** 
	* <p>Title: onRestoreInstanceState</p> 
	* <p>Description: </p> 
	* @param savedInstanceState 
	* @see android.app.Activity#onRestoreInstanceState(android.os.Bundle) 
	*/
	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		getSherlock().dispatchRestoreInstanceState(savedInstanceState);
	}

	// /////////////////////////////////////////////////////////////////////////
	// Native menu handling
	// /////////////////////////////////////////////////////////////////////////

	/** 
	* <p>Title: getSupportMenuInflater</p> 
	* <p>Description: </p> 
	* @return 
	* @see android.support.v4.app.Watson#getSupportMenuInflater() 
	*/
	@Override
	public MenuInflater getSupportMenuInflater() {
		if (DEBUG)
			Log.d(TAG, "[getSupportMenuInflater]");

		return getSherlock().getMenuInflater();
	}

	/** 
	* <p>Title: invalidateOptionsMenu</p> 
	* <p>Description: </p>  
	* @see android.app.Activity#invalidateOptionsMenu() 
	*/
	@Override
	public void invalidateOptionsMenu() {
		if (DEBUG)
			Log.d(TAG, "[invalidateOptionsMenu]");

		getSherlock().dispatchInvalidateOptionsMenu();
	}

	/** 
	* <p>Title: supportInvalidateOptionsMenu</p> 
	* <p>Description: </p>  
	* @see android.support.v4.app.FragmentActivity#supportInvalidateOptionsMenu() 
	*/
	@Override
	public void supportInvalidateOptionsMenu() {
		if (DEBUG)
			Log.d(TAG, "[supportInvalidateOptionsMenu]");

		invalidateOptionsMenu();
	}

	/** 
	* <p>Title: onCreatePanelMenu</p> 
	* <p>Description: </p> 
	* @param featureId
	* @param menu
	* @return 
	* @see android.support.v4.app.Watson#onCreatePanelMenu(int, com.actionbarsherlock.view.Menu) 
	*/
	@Override
	public final boolean onCreatePanelMenu(int featureId, android.view.Menu menu) {
		if (DEBUG)
			Log.d(TAG, "[onCreatePanelMenu] featureId: " + featureId
					+ ", menu: " + menu);

		if (featureId == Window.FEATURE_OPTIONS_PANEL && !mIgnoreNativeCreate) {
			mIgnoreNativeCreate = true;
			boolean result = getSherlock().dispatchCreateOptionsMenu(menu);
			mIgnoreNativeCreate = false;

			if (DEBUG)
				Log.d(TAG, "[onCreatePanelMenu] returning " + result);
			return result;
		}
		return super.onCreatePanelMenu(featureId, menu);
	}

	/** 
	* <p>Title: onCreateOptionsMenu</p> 
	* <p>Description: </p> 
	* @param menu
	* @return 
	* @see android.support.v4.app.Watson#onCreateOptionsMenu(com.actionbarsherlock.view.Menu) 
	*/
	@Override
	public final boolean onCreateOptionsMenu(android.view.Menu menu) {
		return true;
	}

	/** 
	* <p>Title: onPreparePanel</p> 
	* <p>Description: </p> 
	* @param featureId
	* @param view
	* @param menu
	* @return 
	* @see android.support.v4.app.Watson#onPreparePanel(int, android.view.View, com.actionbarsherlock.view.Menu) 
	*/
	@Override
	public final boolean onPreparePanel(int featureId, View view,
			android.view.Menu menu) {
		if (DEBUG)
			Log.d(TAG, "[onPreparePanel] featureId: " + featureId + ", view: "
					+ view + ", menu: " + menu);

		if (featureId == Window.FEATURE_OPTIONS_PANEL && !mIgnoreNativePrepare) {
			mIgnoreNativePrepare = true;
			boolean result = getSherlock().dispatchPrepareOptionsMenu(menu);
			mIgnoreNativePrepare = false;

			if (DEBUG)
				Log.d(TAG, "[onPreparePanel] returning " + result);
			return result;
		}
		return super.onPreparePanel(featureId, view, menu);
	}

	/** 
	* <p>Title: onPrepareOptionsMenu</p> 
	* <p>Description: </p> 
	* @param menu
	* @return 
	* @see android.support.v4.app.Watson#onPrepareOptionsMenu(com.actionbarsherlock.view.Menu) 
	*/
	@Override
	public final boolean onPrepareOptionsMenu(android.view.Menu menu) {
		return true;
	}

	/** 
	* <p>Title: onMenuItemSelected</p> 
	* <p>Description: </p> 
	* @param featureId
	* @param item
	* @return 
	* @see android.support.v4.app.Watson#onMenuItemSelected(int, com.actionbarsherlock.view.MenuItem) 
	*/
	@Override
	public final boolean onMenuItemSelected(int featureId,
			android.view.MenuItem item) {
		if (DEBUG)
			Log.d(TAG, "[onMenuItemSelected] featureId: " + featureId
					+ ", item: " + item);

		if (featureId == Window.FEATURE_OPTIONS_PANEL && !mIgnoreNativeSelected) {
			mIgnoreNativeSelected = true;
			boolean result = getSherlock().dispatchOptionsItemSelected(item);
			mIgnoreNativeSelected = false;

			if (DEBUG)
				Log.d(TAG, "[onMenuItemSelected] returning " + result);
			return result;
		}
		return super.onMenuItemSelected(featureId, item);
	}

	/** 
	* <p>Title: onOptionsItemSelected</p> 
	* <p>Description: </p> 
	* @param item
	* @return 
	* @see android.support.v4.app.Watson#onOptionsItemSelected(com.actionbarsherlock.view.MenuItem) 
	*/
	@Override
	public final boolean onOptionsItemSelected(android.view.MenuItem item) {
		return false;
	}

	/** 
	* <p>Title: openOptionsMenu</p> 
	* <p>Description: </p>  
	* @see android.app.Activity#openOptionsMenu() 
	*/
	@Override
	public void openOptionsMenu() {
		if (!getSherlock().dispatchOpenOptionsMenu()) {
			super.openOptionsMenu();
		}
	}

	/** 
	* <p>Title: closeOptionsMenu</p> 
	* <p>Description: </p>  
	* @see android.app.Activity#closeOptionsMenu() 
	*/
	@Override
	public void closeOptionsMenu() {
		if (!getSherlock().dispatchCloseOptionsMenu()) {
			super.closeOptionsMenu();
		}
	}

	// /////////////////////////////////////////////////////////////////////////
	// Sherlock menu handling
	// /////////////////////////////////////////////////////////////////////////

	/** 
	* <p>Title: onCreateOptionsMenu</p> 
	* <p>Description: </p> 
	* @param menu
	* @return 
	* @see android.support.v4.app.Watson#onCreateOptionsMenu(com.actionbarsherlock.view.Menu) 
	*/
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		return true;
	}

	/** 
	* <p>Title: onPrepareOptionsMenu</p> 
	* <p>Description: </p> 
	* @param menu
	* @return 
	* @see android.support.v4.app.Watson#onPrepareOptionsMenu(com.actionbarsherlock.view.Menu) 
	*/
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		return true;
	}

	/** 
	* <p>Title: onOptionsItemSelected</p> 
	* <p>Description: </p> 
	* @param item
	* @return 
	* @see android.support.v4.app.Watson#onOptionsItemSelected(com.actionbarsherlock.view.MenuItem) 
	*/
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		return false;
	}

	// /////////////////////////////////////////////////////////////////////////
	// Content
	// /////////////////////////////////////////////////////////////////////////

	/** 
	* <p>Title: addContentView</p> 
	* <p>Description: </p> 
	* @param view
	* @param params 
	* @see android.app.Activity#addContentView(android.view.View, android.view.ViewGroup.LayoutParams) 
	*/
	@Override
	public void addContentView(View view, LayoutParams params) {
		getSherlock().addContentView(view, params);
	}

	/** 
	* <p>Title: setContentView</p> 
	* <p>Description: </p> 
	* @param layoutResId 
	* @see android.app.Activity#setContentView(int) 
	*/
	@Override
	public void setContentView(int layoutResId) {
		System.out
				.println("SherlockFragmentActivity.setContentView(int layoutResId)");
		getSherlock().setContentView(layoutResId);
	}

	/** 
	* <p>Title: setContentView</p> 
	* <p>Description: </p> 
	* @param view
	* @param params 
	* @see android.app.Activity#setContentView(android.view.View, android.view.ViewGroup.LayoutParams) 
	*/
	@Override
	public void setContentView(View view, LayoutParams params) {
		System.out
				.println("SherlockFragmentActivity.setContentView(View view, LayoutParams params)");
		getSherlock().setContentView(view, params);
	}

	/** 
	* <p>Title: setContentView</p> 
	* <p>Description: </p> 
	* @param view 
	* @see android.app.Activity#setContentView(android.view.View) 
	*/
	@Override
	public void setContentView(View view) {
		System.out
				.println("SherlockFragmentActivity.setContentView(View view)");
		getSherlock().setContentView(view);
	}

	/**
	 * Request window feature.
	 * 
	 * @param featureId
	 *            the feature id
	 */
	public void requestWindowFeature(long featureId) {
		getSherlock().requestFeature((int) featureId);
	}

	// /////////////////////////////////////////////////////////////////////////
	// Progress Indication
	// /////////////////////////////////////////////////////////////////////////

	/**
	 * Sets the support progress.
	 * 
	 * @param progress
	 *            the new support progress
	 */
	public void setSupportProgress(int progress) {
		getSherlock().setProgress(progress);
	}

	/**
	 * Sets the support progress bar indeterminate.
	 * 
	 * @param indeterminate
	 *            the new support progress bar indeterminate
	 */
	public void setSupportProgressBarIndeterminate(boolean indeterminate) {
		getSherlock().setProgressBarIndeterminate(indeterminate);
	}

	/**
	 * Sets the support progress bar indeterminate visibility.
	 * 
	 * @param visible
	 *            the new support progress bar indeterminate visibility
	 */
	public void setSupportProgressBarIndeterminateVisibility(boolean visible) {
		getSherlock().setProgressBarIndeterminateVisibility(visible);
	}

	/**
	 * Sets the support progress bar visibility.
	 * 
	 * @param visible
	 *            the new support progress bar visibility
	 */
	public void setSupportProgressBarVisibility(boolean visible) {
		getSherlock().setProgressBarVisibility(visible);
	}

	/**
	 * Sets the support secondary progress.
	 * 
	 * @param secondaryProgress
	 *            the new support secondary progress
	 */
	public void setSupportSecondaryProgress(int secondaryProgress) {
		getSherlock().setSecondaryProgress(secondaryProgress);
	}
}
