/**   
* @Title: ActionBarImpl.java 
* @Package com.actionbarsherlock.internal.app 
* @Description: 
*			澹版槑:
*			1)鎺屾帶鏍″洯鏄湰浜虹殑澶у鐨勫垱涓氫綔鍝�涔熸槸鏈汉鐨勬瘯涓氳璁�*			2)鏈▼搴忓皻鏈繘琛屽紑鏀炬簮浠ｇ爜,鎵�互绂佹缁勭粐鎴栬�鏄釜浜烘硠闇叉簮鐮�鍚﹀垯灏嗕細杩界┒鍏跺垜浜嬭矗浠�*			3)缂栧啓鏈蒋浠�鎴戦渶瑕佹劅璋㈠江鑰佸笀浠ュ強鍏朵粬榧撳姳鍜屾敮鎸佹垜鐨勫悓瀛︿互鍙婃湅鍙�*			4)鏈▼搴忕殑鏈�粓鎵�湁鏉冨睘浜庢湰浜�
* @author  寮犻浄 794857063@qq.com
* @date 2013-11-14 19:34:33 
* @version V1.0   
*/

package com.actionbarsherlock.internal.app;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.TypedValue;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.accessibility.AccessibilityEvent;
import android.widget.SpinnerAdapter;

import com.sendwine.app.R;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.internal.nineoldandroids.animation.Animator;
import com.actionbarsherlock.internal.nineoldandroids.animation.AnimatorListenerAdapter;
import com.actionbarsherlock.internal.nineoldandroids.animation.AnimatorSet;
import com.actionbarsherlock.internal.nineoldandroids.animation.ObjectAnimator;
import com.actionbarsherlock.internal.nineoldandroids.animation.Animator.AnimatorListener;
import com.actionbarsherlock.internal.nineoldandroids.widget.NineFrameLayout;
import com.actionbarsherlock.internal.view.menu.MenuBuilder;
import com.actionbarsherlock.internal.view.menu.MenuPopupHelper;
import com.actionbarsherlock.internal.view.menu.SubMenuBuilder;
import com.actionbarsherlock.internal.widget.ActionBarContainer;
import com.actionbarsherlock.internal.widget.ActionBarContextView;
import com.actionbarsherlock.internal.widget.ActionBarView;
import com.actionbarsherlock.internal.widget.ScrollingTabContainerView;
import com.actionbarsherlock.view.ActionMode;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import static com.actionbarsherlock.internal.ResourcesCompat.getResources_getBoolean;

// TODO: Auto-generated Javadoc
/**
 * ActionBarImpl is the ActionBar implementation used by devices of all screen
 * sizes. If it detects a compatible decor, it will split contextual modes
 * across both the ActionBarView at the top of the screen and a horizontal
 * LinearLayout at the bottom which is normally hidden.
 */
public class ActionBarImpl extends ActionBar {
	// UNUSED private static final String TAG = "ActionBarImpl";

	/** The m context. */
	private Context mContext;
	
	/** The m themed context. */
	private Context mThemedContext;
	
	/** The m activity. */
	private Activity mActivity;
	// UNUSED private Dialog mDialog;

	/** The m container view. */
	private ActionBarContainer mContainerView;
	
	/** The m action view. */
	private ActionBarView mActionView;
	
	/** The m context view. */
	private ActionBarContextView mContextView;
	
	/** The m split view. */
	private ActionBarContainer mSplitView;
	
	/** The m content view. */
	private NineFrameLayout mContentView;
	
	/** The m tab scroll view. */
	private ScrollingTabContainerView mTabScrollView;

	/** The m tabs. */
	private ArrayList<TabImpl> mTabs = new ArrayList<TabImpl>();

	/** The m selected tab. */
	private TabImpl mSelectedTab;
	
	/** The m saved tab position. */
	private int mSavedTabPosition = INVALID_POSITION;

	/** The m action mode. */
	ActionModeImpl mActionMode;
	
	/** The m deferred destroy action mode. */
	ActionMode mDeferredDestroyActionMode;
	
	/** The m deferred mode destroy callback. */
	ActionMode.Callback mDeferredModeDestroyCallback;

	/** The m last menu visibility. */
	private boolean mLastMenuVisibility;
	
	/** The m menu visibility listeners. */
	private ArrayList<OnMenuVisibilityListener> mMenuVisibilityListeners = new ArrayList<OnMenuVisibilityListener>();

	/** The Constant CONTEXT_DISPLAY_NORMAL. */
	private static final int CONTEXT_DISPLAY_NORMAL = 0;
	
	/** The Constant CONTEXT_DISPLAY_SPLIT. */
	private static final int CONTEXT_DISPLAY_SPLIT = 1;

	/** The Constant INVALID_POSITION. */
	private static final int INVALID_POSITION = -1;

	/** The m context display mode. */
	private int mContextDisplayMode;
	
	/** The m has embedded tabs. */
	private boolean mHasEmbeddedTabs;

	/** The m handler. */
	final Handler mHandler = new Handler();
	
	/** The m tab selector. */
	Runnable mTabSelector;

	/** The m current show anim. */
	private Animator mCurrentShowAnim;
	
	/** The m current mode anim. */
	private Animator mCurrentModeAnim;
	
	/** The m show hide animation enabled. */
	private boolean mShowHideAnimationEnabled;
	
	/** The m was hidden before mode. */
	boolean mWasHiddenBeforeMode;

	/** The m hide listener. */
	final AnimatorListener mHideListener = new AnimatorListenerAdapter() {
		@Override
		public void onAnimationEnd(Animator animation) {
			if (mContentView != null) {
				mContentView.setTranslationY(0);
				mContainerView.setTranslationY(0);
			}
			if (mSplitView != null
					&& mContextDisplayMode == CONTEXT_DISPLAY_SPLIT) {
				mSplitView.setVisibility(View.GONE);
			}
			mContainerView.setVisibility(View.GONE);
			mContainerView.setTransitioning(false);
			mCurrentShowAnim = null;
			completeDeferredDestroyActionMode();
		}
	};

	/** The m show listener. */
	final AnimatorListener mShowListener = new AnimatorListenerAdapter() {
		@Override
		public void onAnimationEnd(Animator animation) {
			mCurrentShowAnim = null;
			mContainerView.requestLayout();
		}
	};

	/**
	 * Instantiates a new action bar impl.
	 * 
	 * @param activity
	 *            the activity
	 * @param features
	 *            the features
	 */
	public ActionBarImpl(Activity activity, int features) {
		mActivity = activity;
		Window window = activity.getWindow();
		View decor = window.getDecorView();
		init(decor);

		// window.hasFeature() workaround for pre-3.0
		if ((features & (1 << Window.FEATURE_ACTION_BAR_OVERLAY)) == 0) {
			mContentView = (NineFrameLayout) decor
					.findViewById(android.R.id.content);
		}
	}

	/**
	 * Instantiates a new action bar impl.
	 * 
	 * @param dialog
	 *            the dialog
	 */
	public ActionBarImpl(Dialog dialog) {
		// UNUSED mDialog = dialog;
		init(dialog.getWindow().getDecorView());
	}

	/**
	 * Inits the.
	 * 
	 * @param decor
	 *            the decor
	 */
	private void init(View decor) {
		mContext = decor.getContext();
		mActionView = (ActionBarView) decor.findViewById(R.id.abs__action_bar);
		mContextView = (ActionBarContextView) decor
				.findViewById(R.id.abs__action_context_bar);
		mContainerView = (ActionBarContainer) decor
				.findViewById(R.id.abs__action_bar_container);
		mSplitView = (ActionBarContainer) decor
				.findViewById(R.id.abs__split_action_bar);

		if (mActionView == null || mContextView == null
				|| mContainerView == null) {
			throw new IllegalStateException(getClass().getSimpleName()
					+ " can only be used "
					+ "with a compatible window decor layout");
		}

		mActionView.setContextView(mContextView);
		mContextDisplayMode = mActionView.isSplitActionBar() ? CONTEXT_DISPLAY_SPLIT
				: CONTEXT_DISPLAY_NORMAL;

		// Older apps get the home button interaction enabled by default.
		// Newer apps need to enable it explicitly.
		setHomeButtonEnabled(mContext.getApplicationInfo().targetSdkVersion < 14);

		setHasEmbeddedTabs(getResources_getBoolean(mContext,
				R.bool.abs__action_bar_embed_tabs));
	}

	/**
	 * On configuration changed.
	 * 
	 * @param newConfig
	 *            the new config
	 */
	public void onConfigurationChanged(Configuration newConfig) {
		setHasEmbeddedTabs(getResources_getBoolean(mContext,
				R.bool.abs__action_bar_embed_tabs));

		// Manually dispatch a configuration change to the action bar view on
		// pre-2.2
		if (Build.VERSION.SDK_INT < Build.VERSION_CODES.FROYO) {
			mActionView.onConfigurationChanged(newConfig);
			if (mContextView != null) {
				mContextView.onConfigurationChanged(newConfig);
			}
		}
	}

	/**
	 * Sets the checks for embedded tabs.
	 * 
	 * @param hasEmbeddedTabs
	 *            the new checks for embedded tabs
	 */
	private void setHasEmbeddedTabs(boolean hasEmbeddedTabs) {
		mHasEmbeddedTabs = hasEmbeddedTabs;
		// Switch tab layout configuration if needed
		if (!mHasEmbeddedTabs) {
			mActionView.setEmbeddedTabView(null);
			mContainerView.setTabContainer(mTabScrollView);
		} else {
			mContainerView.setTabContainer(null);
			mActionView.setEmbeddedTabView(mTabScrollView);
		}
		final boolean isInTabMode = getNavigationMode() == NAVIGATION_MODE_TABS;
		if (mTabScrollView != null) {
			mTabScrollView
					.setVisibility(isInTabMode ? View.VISIBLE : View.GONE);
		}
		mActionView.setCollapsable(!mHasEmbeddedTabs && isInTabMode);
	}

	/**
	 * Ensure tabs exist.
	 */
	private void ensureTabsExist() {
		if (mTabScrollView != null) {
			return;
		}

		ScrollingTabContainerView tabScroller = new ScrollingTabContainerView(
				mContext);

		if (mHasEmbeddedTabs) {
			tabScroller.setVisibility(View.VISIBLE);
			mActionView.setEmbeddedTabView(tabScroller);
		} else {
			tabScroller
					.setVisibility(getNavigationMode() == NAVIGATION_MODE_TABS ? View.VISIBLE
							: View.GONE);
			mContainerView.setTabContainer(tabScroller);
		}
		mTabScrollView = tabScroller;
	}

	/**
	 * Complete deferred destroy action mode.
	 */
	void completeDeferredDestroyActionMode() {
		if (mDeferredModeDestroyCallback != null) {
			mDeferredModeDestroyCallback
					.onDestroyActionMode(mDeferredDestroyActionMode);
			mDeferredDestroyActionMode = null;
			mDeferredModeDestroyCallback = null;
		}
	}

	/**
	 * Enables or disables animation between show/hide states. If animation is
	 * disabled using this method, animations in progress will be finished.
	 * 
	 * @param enabled
	 *            true to animate, false to not animate.
	 */
	public void setShowHideAnimationEnabled(boolean enabled) {
		mShowHideAnimationEnabled = enabled;
		if (!enabled && mCurrentShowAnim != null) {
			mCurrentShowAnim.end();
		}
	}

	/** 
	* <p>Title: addOnMenuVisibilityListener</p> 
	* <p>Description: </p> 
	* @param listener 
	* @see com.actionbarsherlock.app.ActionBar#addOnMenuVisibilityListener(com.actionbarsherlock.app.ActionBar.OnMenuVisibilityListener) 
	*/
	@Override
	public void addOnMenuVisibilityListener(OnMenuVisibilityListener listener) {
		mMenuVisibilityListeners.add(listener);
	}

	/** 
	* <p>Title: removeOnMenuVisibilityListener</p> 
	* <p>Description: </p> 
	* @param listener 
	* @see com.actionbarsherlock.app.ActionBar#removeOnMenuVisibilityListener(com.actionbarsherlock.app.ActionBar.OnMenuVisibilityListener) 
	*/
	@Override
	public void removeOnMenuVisibilityListener(OnMenuVisibilityListener listener) {
		mMenuVisibilityListeners.remove(listener);
	}

	/**
	 * Dispatch menu visibility changed.
	 * 
	 * @param isVisible
	 *            the is visible
	 */
	public void dispatchMenuVisibilityChanged(boolean isVisible) {
		if (isVisible == mLastMenuVisibility) {
			return;
		}
		mLastMenuVisibility = isVisible;

		final int count = mMenuVisibilityListeners.size();
		for (int i = 0; i < count; i++) {
			mMenuVisibilityListeners.get(i).onMenuVisibilityChanged(isVisible);
		}
	}

	/** 
	* <p>Title: setCustomView</p> 
	* <p>Description: </p> 
	* @param resId 
	* @see com.actionbarsherlock.app.ActionBar#setCustomView(int) 
	*/
	@Override
	public void setCustomView(int resId) {
		setCustomView(LayoutInflater.from(getThemedContext()).inflate(resId,
				mActionView, false));
	}

	/** 
	* <p>Title: setDisplayUseLogoEnabled</p> 
	* <p>Description: </p> 
	* @param useLogo 
	* @see com.actionbarsherlock.app.ActionBar#setDisplayUseLogoEnabled(boolean) 
	*/
	@Override
	public void setDisplayUseLogoEnabled(boolean useLogo) {
		setDisplayOptions(useLogo ? DISPLAY_USE_LOGO : 0, DISPLAY_USE_LOGO);
	}

	/** 
	* <p>Title: setDisplayShowHomeEnabled</p> 
	* <p>Description: </p> 
	* @param showHome 
	* @see com.actionbarsherlock.app.ActionBar#setDisplayShowHomeEnabled(boolean) 
	*/
	@Override
	public void setDisplayShowHomeEnabled(boolean showHome) {
		setDisplayOptions(showHome ? DISPLAY_SHOW_HOME : 0, DISPLAY_SHOW_HOME);
	}

	/** 
	* <p>Title: setDisplayHomeAsUpEnabled</p> 
	* <p>Description: </p> 
	* @param showHomeAsUp 
	* @see com.actionbarsherlock.app.ActionBar#setDisplayHomeAsUpEnabled(boolean) 
	*/
	@Override
	public void setDisplayHomeAsUpEnabled(boolean showHomeAsUp) {
		setDisplayOptions(showHomeAsUp ? DISPLAY_HOME_AS_UP : 0,
				DISPLAY_HOME_AS_UP);
	}

	/** 
	* <p>Title: setDisplayShowTitleEnabled</p> 
	* <p>Description: </p> 
	* @param showTitle 
	* @see com.actionbarsherlock.app.ActionBar#setDisplayShowTitleEnabled(boolean) 
	*/
	@Override
	public void setDisplayShowTitleEnabled(boolean showTitle) {
		setDisplayOptions(showTitle ? DISPLAY_SHOW_TITLE : 0,
				DISPLAY_SHOW_TITLE);
	}

	/** 
	* <p>Title: setDisplayShowCustomEnabled</p> 
	* <p>Description: </p> 
	* @param showCustom 
	* @see com.actionbarsherlock.app.ActionBar#setDisplayShowCustomEnabled(boolean) 
	*/
	@Override
	public void setDisplayShowCustomEnabled(boolean showCustom) {
		setDisplayOptions(showCustom ? DISPLAY_SHOW_CUSTOM : 0,
				DISPLAY_SHOW_CUSTOM);
	}

	/** 
	* <p>Title: setHomeButtonEnabled</p> 
	* <p>Description: </p> 
	* @param enable 
	* @see com.actionbarsherlock.app.ActionBar#setHomeButtonEnabled(boolean) 
	*/
	@Override
	public void setHomeButtonEnabled(boolean enable) {
		mActionView.setHomeButtonEnabled(enable);
	}

	/** 
	* <p>Title: setTitle</p> 
	* <p>Description: </p> 
	* @param resId 
	* @see com.actionbarsherlock.app.ActionBar#setTitle(int) 
	*/
	@Override
	public void setTitle(int resId) {
		setTitle(mContext.getString(resId));
	}

	/** 
	* <p>Title: setSubtitle</p> 
	* <p>Description: </p> 
	* @param resId 
	* @see com.actionbarsherlock.app.ActionBar#setSubtitle(int) 
	*/
	@Override
	public void setSubtitle(int resId) {
		setSubtitle(mContext.getString(resId));
	}

	/** 
	* <p>Title: setSelectedNavigationItem</p> 
	* <p>Description: </p> 
	* @param position 
	* @see com.actionbarsherlock.app.ActionBar#setSelectedNavigationItem(int) 
	*/
	@Override
	public void setSelectedNavigationItem(int position) {
		switch (mActionView.getNavigationMode()) {
		case NAVIGATION_MODE_TABS:
			selectTab(mTabs.get(position));
			break;
		case NAVIGATION_MODE_LIST:
			mActionView.setDropdownSelectedPosition(position);
			break;
		default:
			throw new IllegalStateException(
					"setSelectedNavigationIndex not valid for current navigation mode");
		}
	}

	/** 
	* <p>Title: removeAllTabs</p> 
	* <p>Description: </p>  
	* @see com.actionbarsherlock.app.ActionBar#removeAllTabs() 
	*/
	@Override
	public void removeAllTabs() {
		cleanupTabs();
	}

	/**
	 * Cleanup tabs.
	 */
	private void cleanupTabs() {
		if (mSelectedTab != null) {
			selectTab(null);
		}
		mTabs.clear();
		if (mTabScrollView != null) {
			mTabScrollView.removeAllTabs();
		}
		mSavedTabPosition = INVALID_POSITION;
	}

	/** 
	* <p>Title: setTitle</p> 
	* <p>Description: </p> 
	* @param title 
	* @see com.actionbarsherlock.app.ActionBar#setTitle(java.lang.CharSequence) 
	*/
	@Override
	public void setTitle(CharSequence title) {
		mActionView.setTitle(title);
	}

	/** 
	* <p>Title: setSubtitle</p> 
	* <p>Description: </p> 
	* @param subtitle 
	* @see com.actionbarsherlock.app.ActionBar#setSubtitle(java.lang.CharSequence) 
	*/
	@Override
	public void setSubtitle(CharSequence subtitle) {
		mActionView.setSubtitle(subtitle);
	}

	/** 
	* <p>Title: setDisplayOptions</p> 
	* <p>Description: </p> 
	* @param options 
	* @see com.actionbarsherlock.app.ActionBar#setDisplayOptions(int) 
	*/
	@Override
	public void setDisplayOptions(int options) {
		mActionView.setDisplayOptions(options);
	}

	/** 
	* <p>Title: setDisplayOptions</p> 
	* <p>Description: </p> 
	* @param options
	* @param mask 
	* @see com.actionbarsherlock.app.ActionBar#setDisplayOptions(int, int) 
	*/
	@Override
	public void setDisplayOptions(int options, int mask) {
		final int current = mActionView.getDisplayOptions();
		mActionView.setDisplayOptions((options & mask) | (current & ~mask));
	}

	/** 
	* <p>Title: setBackgroundDrawable</p> 
	* <p>Description: </p> 
	* @param d 
	* @see com.actionbarsherlock.app.ActionBar#setBackgroundDrawable(android.graphics.drawable.Drawable) 
	*/
	@Override
	public void setBackgroundDrawable(Drawable d) {
		mContainerView.setPrimaryBackground(d);
	}

	/** 
	* <p>Title: setStackedBackgroundDrawable</p> 
	* <p>Description: </p> 
	* @param d 
	* @see com.actionbarsherlock.app.ActionBar#setStackedBackgroundDrawable(android.graphics.drawable.Drawable) 
	*/
	@Override
	public void setStackedBackgroundDrawable(Drawable d) {
		mContainerView.setStackedBackground(d);
	}

	/** 
	* <p>Title: setSplitBackgroundDrawable</p> 
	* <p>Description: </p> 
	* @param d 
	* @see com.actionbarsherlock.app.ActionBar#setSplitBackgroundDrawable(android.graphics.drawable.Drawable) 
	*/
	@Override
	public void setSplitBackgroundDrawable(Drawable d) {
		if (mSplitView != null) {
			mSplitView.setSplitBackground(d);
		}
	}

	/** 
	* <p>Title: getCustomView</p> 
	* <p>Description: </p> 
	* @return 
	* @see com.actionbarsherlock.app.ActionBar#getCustomView() 
	*/
	@Override
	public View getCustomView() {
		return mActionView.getCustomNavigationView();
	}

	/** 
	* <p>Title: getTitle</p> 
	* <p>Description: </p> 
	* @return 
	* @see com.actionbarsherlock.app.ActionBar#getTitle() 
	*/
	@Override
	public CharSequence getTitle() {
		return mActionView.getTitle();
	}

	/** 
	* <p>Title: getSubtitle</p> 
	* <p>Description: </p> 
	* @return 
	* @see com.actionbarsherlock.app.ActionBar#getSubtitle() 
	*/
	@Override
	public CharSequence getSubtitle() {
		return mActionView.getSubtitle();
	}

	/** 
	* <p>Title: getNavigationMode</p> 
	* <p>Description: </p> 
	* @return 
	* @see com.actionbarsherlock.app.ActionBar#getNavigationMode() 
	*/
	@Override
	public int getNavigationMode() {
		return mActionView.getNavigationMode();
	}

	/** 
	* <p>Title: getDisplayOptions</p> 
	* <p>Description: </p> 
	* @return 
	* @see com.actionbarsherlock.app.ActionBar#getDisplayOptions() 
	*/
	@Override
	public int getDisplayOptions() {
		return mActionView.getDisplayOptions();
	}

	/**
	 * Start action mode.
	 * 
	 * @param callback
	 *            the callback
	 * @return the action mode
	 */
	public ActionMode startActionMode(ActionMode.Callback callback) {
		boolean wasHidden = false;
		if (mActionMode != null) {
			wasHidden = mWasHiddenBeforeMode;
			mActionMode.finish();
		}

		mContextView.killMode();
		ActionModeImpl mode = new ActionModeImpl(callback);
		if (mode.dispatchOnCreate()) {
			mWasHiddenBeforeMode = !isShowing() || wasHidden;
			mode.invalidate();
			mContextView.initForMode(mode);
			animateToMode(true);
			if (mSplitView != null
					&& mContextDisplayMode == CONTEXT_DISPLAY_SPLIT) {
				// TODO animate this
				mSplitView.setVisibility(View.VISIBLE);
			}
			mContextView
					.sendAccessibilityEvent(AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED);
			mActionMode = mode;
			return mode;
		}
		return null;
	}

	/**
	 * Configure tab.
	 * 
	 * @param tab
	 *            the tab
	 * @param position
	 *            the position
	 */
	private void configureTab(Tab tab, int position) {
		final TabImpl tabi = (TabImpl) tab;
		final ActionBar.TabListener callback = tabi.getCallback();

		if (callback == null) {
			throw new IllegalStateException(
					"Action Bar Tab must have a Callback");
		}

		tabi.setPosition(position);
		mTabs.add(position, tabi);

		final int count = mTabs.size();
		for (int i = position + 1; i < count; i++) {
			mTabs.get(i).setPosition(i);
		}
	}

	/** 
	* <p>Title: addTab</p> 
	* <p>Description: </p> 
	* @param tab 
	* @see com.actionbarsherlock.app.ActionBar#addTab(com.actionbarsherlock.app.ActionBar.Tab) 
	*/
	@Override
	public void addTab(Tab tab) {
		addTab(tab, mTabs.isEmpty());
	}

	/** 
	* <p>Title: addTab</p> 
	* <p>Description: </p> 
	* @param tab
	* @param position 
	* @see com.actionbarsherlock.app.ActionBar#addTab(com.actionbarsherlock.app.ActionBar.Tab, int) 
	*/
	@Override
	public void addTab(Tab tab, int position) {
		addTab(tab, position, mTabs.isEmpty());
	}

	/** 
	* <p>Title: addTab</p> 
	* <p>Description: </p> 
	* @param tab
	* @param setSelected 
	* @see com.actionbarsherlock.app.ActionBar#addTab(com.actionbarsherlock.app.ActionBar.Tab, boolean) 
	*/
	@Override
	public void addTab(Tab tab, boolean setSelected) {
		ensureTabsExist();
		mTabScrollView.addTab(tab, setSelected);
		configureTab(tab, mTabs.size());
		if (setSelected) {
			selectTab(tab);
		}
	}

	/** 
	* <p>Title: addTab</p> 
	* <p>Description: </p> 
	* @param tab
	* @param position
	* @param setSelected 
	* @see com.actionbarsherlock.app.ActionBar#addTab(com.actionbarsherlock.app.ActionBar.Tab, int, boolean) 
	*/
	@Override
	public void addTab(Tab tab, int position, boolean setSelected) {
		ensureTabsExist();
		mTabScrollView.addTab(tab, position, setSelected);
		configureTab(tab, position);
		if (setSelected) {
			selectTab(tab);
		}
	}

	/** 
	* <p>Title: newTab</p> 
	* <p>Description: </p> 
	* @return 
	* @see com.actionbarsherlock.app.ActionBar#newTab() 
	*/
	@Override
	public Tab newTab() {
		return new TabImpl();
	}

	/** 
	* <p>Title: removeTab</p> 
	* <p>Description: </p> 
	* @param tab 
	* @see com.actionbarsherlock.app.ActionBar#removeTab(com.actionbarsherlock.app.ActionBar.Tab) 
	*/
	@Override
	public void removeTab(Tab tab) {
		removeTabAt(tab.getPosition());
	}

	/** 
	* <p>Title: removeTabAt</p> 
	* <p>Description: </p> 
	* @param position 
	* @see com.actionbarsherlock.app.ActionBar#removeTabAt(int) 
	*/
	@Override
	public void removeTabAt(int position) {
		if (mTabScrollView == null) {
			// No tabs around to remove
			return;
		}

		int selectedTabPosition = mSelectedTab != null ? mSelectedTab
				.getPosition() : mSavedTabPosition;
		mTabScrollView.removeTabAt(position);
		TabImpl removedTab = mTabs.remove(position);
		if (removedTab != null) {
			removedTab.setPosition(-1);
		}

		final int newTabCount = mTabs.size();
		for (int i = position; i < newTabCount; i++) {
			mTabs.get(i).setPosition(i);
		}

		if (selectedTabPosition == position) {
			selectTab(mTabs.isEmpty() ? null : mTabs.get(Math.max(0,
					position - 1)));
		}
	}

	/** 
	* <p>Title: selectTab</p> 
	* <p>Description: </p> 
	* @param tab 
	* @see com.actionbarsherlock.app.ActionBar#selectTab(com.actionbarsherlock.app.ActionBar.Tab) 
	*/
	@Override
	public void selectTab(Tab tab) {
		if (getNavigationMode() != NAVIGATION_MODE_TABS) {
			mSavedTabPosition = tab != null ? tab.getPosition()
					: INVALID_POSITION;
			return;
		}

		FragmentTransaction trans = null;
		if (mActivity instanceof FragmentActivity) {
			trans = ((FragmentActivity) mActivity).getSupportFragmentManager()
					.beginTransaction().disallowAddToBackStack();
		}

		if (mSelectedTab == tab) {
			if (mSelectedTab != null) {
				mSelectedTab.getCallback().onTabReselected(mSelectedTab, trans);
				mTabScrollView.animateToTab(tab.getPosition());
			}
		} else {
			mTabScrollView.setTabSelected(tab != null ? tab.getPosition()
					: Tab.INVALID_POSITION);
			if (mSelectedTab != null) {
				mSelectedTab.getCallback().onTabUnselected(mSelectedTab, trans);
			}
			mSelectedTab = (TabImpl) tab;
			if (mSelectedTab != null) {
				mSelectedTab.getCallback().onTabSelected(mSelectedTab, trans);
			}
		}

		if (trans != null && !trans.isEmpty()) {
			trans.commit();
		}
	}

	/** 
	* <p>Title: getSelectedTab</p> 
	* <p>Description: </p> 
	* @return 
	* @see com.actionbarsherlock.app.ActionBar#getSelectedTab() 
	*/
	@Override
	public Tab getSelectedTab() {
		return mSelectedTab;
	}

	/** 
	* <p>Title: getHeight</p> 
	* <p>Description: </p> 
	* @return 
	* @see com.actionbarsherlock.app.ActionBar#getHeight() 
	*/
	@Override
	public int getHeight() {
		return mContainerView.getHeight();
	}

	/** 
	* <p>Title: show</p> 
	* <p>Description: </p>  
	* @see com.actionbarsherlock.app.ActionBar#show() 
	*/
	@Override
	public void show() {
		show(true);
	}

	/**
	 * Show.
	 * 
	 * @param markHiddenBeforeMode
	 *            the mark hidden before mode
	 */
	void show(boolean markHiddenBeforeMode) {
		if (mCurrentShowAnim != null) {
			mCurrentShowAnim.end();
		}
		if (mContainerView.getVisibility() == View.VISIBLE) {
			if (markHiddenBeforeMode)
				mWasHiddenBeforeMode = false;
			return;
		}
		mContainerView.setVisibility(View.VISIBLE);

		if (mShowHideAnimationEnabled) {
			mContainerView.setAlpha(0);
			AnimatorSet anim = new AnimatorSet();
			AnimatorSet.Builder b = anim.play(ObjectAnimator.ofFloat(
					mContainerView, "alpha", 1));
			if (mContentView != null) {
				b.with(ObjectAnimator.ofFloat(mContentView, "translationY",
						-mContainerView.getHeight(), 0));
				mContainerView.setTranslationY(-mContainerView.getHeight());
				b.with(ObjectAnimator
						.ofFloat(mContainerView, "translationY", 0));
			}
			if (mSplitView != null
					&& mContextDisplayMode == CONTEXT_DISPLAY_SPLIT) {
				mSplitView.setAlpha(0);
				mSplitView.setVisibility(View.VISIBLE);
				b.with(ObjectAnimator.ofFloat(mSplitView, "alpha", 1));
			}
			anim.addListener(mShowListener);
			mCurrentShowAnim = anim;
			anim.start();
		} else {
			mContainerView.setAlpha(1);
			mContainerView.setTranslationY(0);
			mShowListener.onAnimationEnd(null);
		}
	}

	/** 
	* <p>Title: hide</p> 
	* <p>Description: </p>  
	* @see com.actionbarsherlock.app.ActionBar#hide() 
	*/
	@Override
	public void hide() {
		if (mCurrentShowAnim != null) {
			mCurrentShowAnim.end();
		}
		if (mContainerView.getVisibility() == View.GONE) {
			return;
		}

		if (mShowHideAnimationEnabled) {
			mContainerView.setAlpha(1);
			mContainerView.setTransitioning(true);
			AnimatorSet anim = new AnimatorSet();
			AnimatorSet.Builder b = anim.play(ObjectAnimator.ofFloat(
					mContainerView, "alpha", 0));
			if (mContentView != null) {
				b.with(ObjectAnimator.ofFloat(mContentView, "translationY", 0,
						-mContainerView.getHeight()));
				b.with(ObjectAnimator.ofFloat(mContainerView, "translationY",
						-mContainerView.getHeight()));
			}
			if (mSplitView != null
					&& mSplitView.getVisibility() == View.VISIBLE) {
				mSplitView.setAlpha(1);
				b.with(ObjectAnimator.ofFloat(mSplitView, "alpha", 0));
			}
			anim.addListener(mHideListener);
			mCurrentShowAnim = anim;
			anim.start();
		} else {
			mHideListener.onAnimationEnd(null);
		}
	}

	/** 
	* <p>Title: isShowing</p> 
	* <p>Description: </p> 
	* @return 
	* @see com.actionbarsherlock.app.ActionBar#isShowing() 
	*/
	@Override
	public boolean isShowing() {
		return mContainerView.getVisibility() == View.VISIBLE;
	}

	/**
	 * Animate to mode.
	 * 
	 * @param toActionMode
	 *            the to action mode
	 */
	void animateToMode(boolean toActionMode) {
		if (toActionMode) {
			show(false);
		}
		if (mCurrentModeAnim != null) {
			mCurrentModeAnim.end();
		}

		mActionView
				.animateToVisibility(toActionMode ? View.GONE : View.VISIBLE);
		mContextView.animateToVisibility(toActionMode ? View.VISIBLE
				: View.GONE);
		if (mTabScrollView != null && !mActionView.hasEmbeddedTabs()
				&& mActionView.isCollapsed()) {
			mTabScrollView.animateToVisibility(toActionMode ? View.GONE
					: View.VISIBLE);
		}
	}

	/** 
	* <p>Title: getThemedContext</p> 
	* <p>Description: </p> 
	* @return 
	* @see com.actionbarsherlock.app.ActionBar#getThemedContext() 
	*/
	@Override
	public Context getThemedContext() {
		if (mThemedContext == null) {
			TypedValue outValue = new TypedValue();
			Resources.Theme currentTheme = mContext.getTheme();
			currentTheme.resolveAttribute(R.attr.actionBarWidgetTheme,
					outValue, true);
			final int targetThemeRes = outValue.resourceId;

			if (targetThemeRes != 0) { // XXX && mContext.getThemeResId() !=
										// targetThemeRes) {
				mThemedContext = new ContextThemeWrapper(mContext,
						targetThemeRes);
			} else {
				mThemedContext = mContext;
			}
		}
		return mThemedContext;
	}

	/**
	 * The Class ActionModeImpl.
	 * 
	 * @hide
	 */
	public class ActionModeImpl extends ActionMode implements
			MenuBuilder.Callback {
		
		/** The m callback. */
		private ActionMode.Callback mCallback;
		
		/** The m menu. */
		private MenuBuilder mMenu;
		
		/** The m custom view. */
		private WeakReference<View> mCustomView;

		/**
		 * Instantiates a new action mode impl.
		 * 
		 * @param callback
		 *            the callback
		 */
		public ActionModeImpl(ActionMode.Callback callback) {
			mCallback = callback;
			mMenu = new MenuBuilder(getThemedContext())
					.setDefaultShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
			mMenu.setCallback(this);
		}

		/** 
		* <p>Title: getMenuInflater</p> 
		* <p>Description: </p> 
		* @return 
		* @see com.actionbarsherlock.view.ActionMode#getMenuInflater() 
		*/
		@Override
		public MenuInflater getMenuInflater() {
			return new MenuInflater(getThemedContext());
		}

		/** 
		* <p>Title: getMenu</p> 
		* <p>Description: </p> 
		* @return 
		* @see com.actionbarsherlock.view.ActionMode#getMenu() 
		*/
		@Override
		public Menu getMenu() {
			return mMenu;
		}

		/** 
		* <p>Title: finish</p> 
		* <p>Description: </p>  
		* @see com.actionbarsherlock.view.ActionMode#finish() 
		*/
		@Override
		public void finish() {
			if (mActionMode != this) {
				// Not the active action mode - no-op
				return;
			}

			// If we were hidden before the mode was shown, defer the onDestroy
			// callback until the animation is finished and associated relayout
			// is about to happen. This lets apps better anticipate visibility
			// and layout behavior.
			if (mWasHiddenBeforeMode) {
				mDeferredDestroyActionMode = this;
				mDeferredModeDestroyCallback = mCallback;
			} else {
				mCallback.onDestroyActionMode(this);
			}
			mCallback = null;
			animateToMode(false);

			// Clear out the context mode views after the animation finishes
			mContextView.closeMode();
			mActionView
					.sendAccessibilityEvent(AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED);

			mActionMode = null;

			if (mWasHiddenBeforeMode) {
				hide();
			}
		}

		/** 
		* <p>Title: invalidate</p> 
		* <p>Description: </p>  
		* @see com.actionbarsherlock.view.ActionMode#invalidate() 
		*/
		@Override
		public void invalidate() {
			mMenu.stopDispatchingItemsChanged();
			try {
				mCallback.onPrepareActionMode(this, mMenu);
			} finally {
				mMenu.startDispatchingItemsChanged();
			}
		}

		/**
		 * Dispatch on create.
		 * 
		 * @return true, if successful
		 */
		public boolean dispatchOnCreate() {
			mMenu.stopDispatchingItemsChanged();
			try {
				return mCallback.onCreateActionMode(this, mMenu);
			} finally {
				mMenu.startDispatchingItemsChanged();
			}
		}

		/** 
		* <p>Title: setCustomView</p> 
		* <p>Description: </p> 
		* @param view 
		* @see com.actionbarsherlock.view.ActionMode#setCustomView(android.view.View) 
		*/
		@Override
		public void setCustomView(View view) {
			mContextView.setCustomView(view);
			mCustomView = new WeakReference<View>(view);
		}

		/** 
		* <p>Title: setSubtitle</p> 
		* <p>Description: </p> 
		* @param subtitle 
		* @see com.actionbarsherlock.view.ActionMode#setSubtitle(java.lang.CharSequence) 
		*/
		@Override
		public void setSubtitle(CharSequence subtitle) {
			mContextView.setSubtitle(subtitle);
		}

		/** 
		* <p>Title: setTitle</p> 
		* <p>Description: </p> 
		* @param title 
		* @see com.actionbarsherlock.view.ActionMode#setTitle(java.lang.CharSequence) 
		*/
		@Override
		public void setTitle(CharSequence title) {
			mContextView.setTitle(title);
		}

		/** 
		* <p>Title: setTitle</p> 
		* <p>Description: </p> 
		* @param resId 
		* @see com.actionbarsherlock.view.ActionMode#setTitle(int) 
		*/
		@Override
		public void setTitle(int resId) {
			setTitle(mContext.getResources().getString(resId));
		}

		/** 
		* <p>Title: setSubtitle</p> 
		* <p>Description: </p> 
		* @param resId 
		* @see com.actionbarsherlock.view.ActionMode#setSubtitle(int) 
		*/
		@Override
		public void setSubtitle(int resId) {
			setSubtitle(mContext.getResources().getString(resId));
		}

		/** 
		* <p>Title: getTitle</p> 
		* <p>Description: </p> 
		* @return 
		* @see com.actionbarsherlock.view.ActionMode#getTitle() 
		*/
		@Override
		public CharSequence getTitle() {
			return mContextView.getTitle();
		}

		/** 
		* <p>Title: getSubtitle</p> 
		* <p>Description: </p> 
		* @return 
		* @see com.actionbarsherlock.view.ActionMode#getSubtitle() 
		*/
		@Override
		public CharSequence getSubtitle() {
			return mContextView.getSubtitle();
		}

		/** 
		* <p>Title: getCustomView</p> 
		* <p>Description: </p> 
		* @return 
		* @see com.actionbarsherlock.view.ActionMode#getCustomView() 
		*/
		@Override
		public View getCustomView() {
			return mCustomView != null ? mCustomView.get() : null;
		}

		/** 
		* <p>Title: onMenuItemSelected</p> 
		* <p>Description: </p> 
		* @param menu
		* @param item
		* @return 
		* @see com.actionbarsherlock.internal.view.menu.MenuBuilder.Callback#onMenuItemSelected(com.actionbarsherlock.internal.view.menu.MenuBuilder, com.actionbarsherlock.view.MenuItem) 
		*/
		@Override
		public boolean onMenuItemSelected(MenuBuilder menu, MenuItem item) {
			if (mCallback != null) {
				return mCallback.onActionItemClicked(this, item);
			} else {
				return false;
			}
		}

		/**
		 * On close menu.
		 * 
		 * @param menu
		 *            the menu
		 * @param allMenusAreClosing
		 *            the all menus are closing
		 */
		public void onCloseMenu(MenuBuilder menu, boolean allMenusAreClosing) {
		}

		/**
		 * On sub menu selected.
		 * 
		 * @param subMenu
		 *            the sub menu
		 * @return true, if successful
		 */
		public boolean onSubMenuSelected(SubMenuBuilder subMenu) {
			if (mCallback == null) {
				return false;
			}

			if (!subMenu.hasVisibleItems()) {
				return true;
			}

			new MenuPopupHelper(getThemedContext(), subMenu).show();
			return true;
		}

		/**
		 * On close sub menu.
		 * 
		 * @param menu
		 *            the menu
		 */
		public void onCloseSubMenu(SubMenuBuilder menu) {
		}

		/** 
		* <p>Title: onMenuModeChange</p> 
		* <p>Description: </p> 
		* @param menu 
		* @see com.actionbarsherlock.internal.view.menu.MenuBuilder.Callback#onMenuModeChange(com.actionbarsherlock.internal.view.menu.MenuBuilder) 
		*/
		@Override
		public void onMenuModeChange(MenuBuilder menu) {
			if (mCallback == null) {
				return;
			}
			invalidate();
			mContextView.showOverflowMenu();
		}
	}

	/**
	 * The Class TabImpl.
	 * 
	 * @hide
	 */
	public class TabImpl extends ActionBar.Tab {
		
		/** The m callback. */
		private ActionBar.TabListener mCallback;
		
		/** The m tag. */
		private Object mTag;
		
		/** The m icon. */
		private Drawable mIcon;
		
		/** The m text. */
		private CharSequence mText;
		
		/** The m content desc. */
		private CharSequence mContentDesc;
		
		/** The m position. */
		private int mPosition = -1;
		
		/** The m custom view. */
		private View mCustomView;

		/** 
		* <p>Title: getTag</p> 
		* <p>Description: </p> 
		* @return 
		* @see com.actionbarsherlock.app.ActionBar.Tab#getTag() 
		*/
		@Override
		public Object getTag() {
			return mTag;
		}

		/** 
		* <p>Title: setTag</p> 
		* <p>Description: </p> 
		* @param tag
		* @return 
		* @see com.actionbarsherlock.app.ActionBar.Tab#setTag(java.lang.Object) 
		*/
		@Override
		public Tab setTag(Object tag) {
			mTag = tag;
			return this;
		}

		/**
		 * Gets the callback.
		 * 
		 * @return the callback
		 */
		public ActionBar.TabListener getCallback() {
			return mCallback;
		}

		/** 
		* <p>Title: setTabListener</p> 
		* <p>Description: </p> 
		* @param callback
		* @return 
		* @see com.actionbarsherlock.app.ActionBar.Tab#setTabListener(com.actionbarsherlock.app.ActionBar.TabListener) 
		*/
		@Override
		public Tab setTabListener(ActionBar.TabListener callback) {
			mCallback = callback;
			return this;
		}

		/** 
		* <p>Title: getCustomView</p> 
		* <p>Description: </p> 
		* @return 
		* @see com.actionbarsherlock.app.ActionBar.Tab#getCustomView() 
		*/
		@Override
		public View getCustomView() {
			return mCustomView;
		}

		/** 
		* <p>Title: setCustomView</p> 
		* <p>Description: </p> 
		* @param view
		* @return 
		* @see com.actionbarsherlock.app.ActionBar.Tab#setCustomView(android.view.View) 
		*/
		@Override
		public Tab setCustomView(View view) {
			mCustomView = view;
			if (mPosition >= 0) {
				mTabScrollView.updateTab(mPosition);
			}
			return this;
		}

		/** 
		* <p>Title: setCustomView</p> 
		* <p>Description: </p> 
		* @param layoutResId
		* @return 
		* @see com.actionbarsherlock.app.ActionBar.Tab#setCustomView(int) 
		*/
		@Override
		public Tab setCustomView(int layoutResId) {
			return setCustomView(LayoutInflater.from(getThemedContext())
					.inflate(layoutResId, null));
		}

		/** 
		* <p>Title: getIcon</p> 
		* <p>Description: </p> 
		* @return 
		* @see com.actionbarsherlock.app.ActionBar.Tab#getIcon() 
		*/
		@Override
		public Drawable getIcon() {
			return mIcon;
		}

		/** 
		* <p>Title: getPosition</p> 
		* <p>Description: </p> 
		* @return 
		* @see com.actionbarsherlock.app.ActionBar.Tab#getPosition() 
		*/
		@Override
		public int getPosition() {
			return mPosition;
		}

		/**
		 * Sets the position.
		 * 
		 * @param position
		 *            the new position
		 */
		public void setPosition(int position) {
			mPosition = position;
		}

		/** 
		* <p>Title: getText</p> 
		* <p>Description: </p> 
		* @return 
		* @see com.actionbarsherlock.app.ActionBar.Tab#getText() 
		*/
		@Override
		public CharSequence getText() {
			return mText;
		}

		/** 
		* <p>Title: setIcon</p> 
		* <p>Description: </p> 
		* @param icon
		* @return 
		* @see com.actionbarsherlock.app.ActionBar.Tab#setIcon(android.graphics.drawable.Drawable) 
		*/
		@Override
		public Tab setIcon(Drawable icon) {
			mIcon = icon;
			if (mPosition >= 0) {
				mTabScrollView.updateTab(mPosition);
			}
			return this;
		}

		/** 
		* <p>Title: setIcon</p> 
		* <p>Description: </p> 
		* @param resId
		* @return 
		* @see com.actionbarsherlock.app.ActionBar.Tab#setIcon(int) 
		*/
		@Override
		public Tab setIcon(int resId) {
			return setIcon(mContext.getResources().getDrawable(resId));
		}

		/** 
		* <p>Title: setText</p> 
		* <p>Description: </p> 
		* @param text
		* @return 
		* @see com.actionbarsherlock.app.ActionBar.Tab#setText(java.lang.CharSequence) 
		*/
		@Override
		public Tab setText(CharSequence text) {
			mText = text;
			if (mPosition >= 0) {
				mTabScrollView.updateTab(mPosition);
			}
			return this;
		}

		/** 
		* <p>Title: setText</p> 
		* <p>Description: </p> 
		* @param resId
		* @return 
		* @see com.actionbarsherlock.app.ActionBar.Tab#setText(int) 
		*/
		@Override
		public Tab setText(int resId) {
			return setText(mContext.getResources().getText(resId));
		}

		/** 
		* <p>Title: select</p> 
		* <p>Description: </p>  
		* @see com.actionbarsherlock.app.ActionBar.Tab#select() 
		*/
		@Override
		public void select() {
			selectTab(this);
		}

		/** 
		* <p>Title: setContentDescription</p> 
		* <p>Description: </p> 
		* @param resId
		* @return 
		* @see com.actionbarsherlock.app.ActionBar.Tab#setContentDescription(int) 
		*/
		@Override
		public Tab setContentDescription(int resId) {
			return setContentDescription(mContext.getResources().getText(resId));
		}

		/** 
		* <p>Title: setContentDescription</p> 
		* <p>Description: </p> 
		* @param contentDesc
		* @return 
		* @see com.actionbarsherlock.app.ActionBar.Tab#setContentDescription(java.lang.CharSequence) 
		*/
		@Override
		public Tab setContentDescription(CharSequence contentDesc) {
			mContentDesc = contentDesc;
			if (mPosition >= 0) {
				mTabScrollView.updateTab(mPosition);
			}
			return this;
		}

		/** 
		* <p>Title: getContentDescription</p> 
		* <p>Description: </p> 
		* @return 
		* @see com.actionbarsherlock.app.ActionBar.Tab#getContentDescription() 
		*/
		@Override
		public CharSequence getContentDescription() {
			return mContentDesc;
		}
	}

	/** 
	* <p>Title: setCustomView</p> 
	* <p>Description: </p> 
	* @param view 
	* @see com.actionbarsherlock.app.ActionBar#setCustomView(android.view.View) 
	*/
	@Override
	public void setCustomView(View view) {
		mActionView.setCustomNavigationView(view);
	}

	/** 
	* <p>Title: setCustomView</p> 
	* <p>Description: </p> 
	* @param view
	* @param layoutParams 
	* @see com.actionbarsherlock.app.ActionBar#setCustomView(android.view.View, com.actionbarsherlock.app.ActionBar.LayoutParams) 
	*/
	@Override
	public void setCustomView(View view, LayoutParams layoutParams) {
		view.setLayoutParams(layoutParams);
		mActionView.setCustomNavigationView(view);
	}

	/** 
	* <p>Title: setListNavigationCallbacks</p> 
	* <p>Description: </p> 
	* @param adapter
	* @param callback 
	* @see com.actionbarsherlock.app.ActionBar#setListNavigationCallbacks(android.widget.SpinnerAdapter, com.actionbarsherlock.app.ActionBar.OnNavigationListener) 
	*/
	@Override
	public void setListNavigationCallbacks(SpinnerAdapter adapter,
			OnNavigationListener callback) {
		mActionView.setDropdownAdapter(adapter);
		mActionView.setCallback(callback);
	}

	/** 
	* <p>Title: getSelectedNavigationIndex</p> 
	* <p>Description: </p> 
	* @return 
	* @see com.actionbarsherlock.app.ActionBar#getSelectedNavigationIndex() 
	*/
	@Override
	public int getSelectedNavigationIndex() {
		switch (mActionView.getNavigationMode()) {
		case NAVIGATION_MODE_TABS:
			return mSelectedTab != null ? mSelectedTab.getPosition() : -1;
		case NAVIGATION_MODE_LIST:
			return mActionView.getDropdownSelectedPosition();
		default:
			return -1;
		}
	}

	/** 
	* <p>Title: getNavigationItemCount</p> 
	* <p>Description: </p> 
	* @return 
	* @see com.actionbarsherlock.app.ActionBar#getNavigationItemCount() 
	*/
	@Override
	public int getNavigationItemCount() {
		switch (mActionView.getNavigationMode()) {
		case NAVIGATION_MODE_TABS:
			return mTabs.size();
		case NAVIGATION_MODE_LIST:
			SpinnerAdapter adapter = mActionView.getDropdownAdapter();
			return adapter != null ? adapter.getCount() : 0;
		default:
			return 0;
		}
	}

	/** 
	* <p>Title: getTabCount</p> 
	* <p>Description: </p> 
	* @return 
	* @see com.actionbarsherlock.app.ActionBar#getTabCount() 
	*/
	@Override
	public int getTabCount() {
		return mTabs.size();
	}

	/** 
	* <p>Title: setNavigationMode</p> 
	* <p>Description: </p> 
	* @param mode 
	* @see com.actionbarsherlock.app.ActionBar#setNavigationMode(int) 
	*/
	@Override
	public void setNavigationMode(int mode) {
		final int oldMode = mActionView.getNavigationMode();
		switch (oldMode) {
		case NAVIGATION_MODE_TABS:
			mSavedTabPosition = getSelectedNavigationIndex();
			selectTab(null);
			mTabScrollView.setVisibility(View.GONE);
			break;
		}
		mActionView.setNavigationMode(mode);
		switch (mode) {
		case NAVIGATION_MODE_TABS:
			ensureTabsExist();
			mTabScrollView.setVisibility(View.VISIBLE);
			if (mSavedTabPosition != INVALID_POSITION) {
				setSelectedNavigationItem(mSavedTabPosition);
				mSavedTabPosition = INVALID_POSITION;
			}
			break;
		}
		mActionView.setCollapsable(mode == NAVIGATION_MODE_TABS
				&& !mHasEmbeddedTabs);
	}

	/** 
	* <p>Title: getTabAt</p> 
	* <p>Description: </p> 
	* @param index
	* @return 
	* @see com.actionbarsherlock.app.ActionBar#getTabAt(int) 
	*/
	@Override
	public Tab getTabAt(int index) {
		return mTabs.get(index);
	}

	/** 
	* <p>Title: setIcon</p> 
	* <p>Description: </p> 
	* @param resId 
	* @see com.actionbarsherlock.app.ActionBar#setIcon(int) 
	*/
	@Override
	public void setIcon(int resId) {
		mActionView.setIcon(resId);
	}

	/** 
	* <p>Title: setIcon</p> 
	* <p>Description: </p> 
	* @param icon 
	* @see com.actionbarsherlock.app.ActionBar#setIcon(android.graphics.drawable.Drawable) 
	*/
	@Override
	public void setIcon(Drawable icon) {
		mActionView.setIcon(icon);
	}

	/** 
	* <p>Title: setLogo</p> 
	* <p>Description: </p> 
	* @param resId 
	* @see com.actionbarsherlock.app.ActionBar#setLogo(int) 
	*/
	@Override
	public void setLogo(int resId) {
		mActionView.setLogo(resId);
	}

	/** 
	* <p>Title: setLogo</p> 
	* <p>Description: </p> 
	* @param logo 
	* @see com.actionbarsherlock.app.ActionBar#setLogo(android.graphics.drawable.Drawable) 
	*/
	@Override
	public void setLogo(Drawable logo) {
		mActionView.setLogo(logo);
	}
}
