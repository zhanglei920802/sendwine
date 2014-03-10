/**   
* @Title: ActionBarWrapper.java 
* @Package com.actionbarsherlock.internal.app 
* @Description: 
*			声明:
*			1)掌控校园是本人的大学的创业作品,也是本人的毕业设计
*			2)本程序尚未进行开放源代码,所以禁止组织或者是个人泄露源码,否则将会追究其刑事责任
*			3)编写本软件,我需要感谢彭老师以及其他鼓励和支持我的同学以及朋友
*			4)本程序的最终所有权属于本人	
* @author  张雷 794857063@qq.com
* @date 2013-11-14 19:34:10 
* @version V1.0   
*/
package com.actionbarsherlock.internal.app;

import java.util.HashSet;
import java.util.Set;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.SpinnerAdapter;

import com.actionbarsherlock.app.ActionBar;

// TODO: Auto-generated Javadoc
/**
 * The Class ActionBarWrapper.
 */
public class ActionBarWrapper extends ActionBar implements
		android.app.ActionBar.OnNavigationListener,
		android.app.ActionBar.OnMenuVisibilityListener {
	
	/** The m activity. */
	private final Activity mActivity;
	
	/** The m action bar. */
	private final android.app.ActionBar mActionBar;
	
	/** The m navigation listener. */
	private ActionBar.OnNavigationListener mNavigationListener;
	
	/** The m menu visibility listeners. */
	private Set<OnMenuVisibilityListener> mMenuVisibilityListeners = new HashSet<OnMenuVisibilityListener>(
			1);
	
	/** The m fragment transaction. */
	private FragmentTransaction mFragmentTransaction;

	/**
	 * Instantiates a new action bar wrapper.
	 * 
	 * @param activity
	 *            the activity
	 */
	public ActionBarWrapper(Activity activity) {
		mActivity = activity;
		mActionBar = activity.getActionBar();
		if (mActionBar != null) {
			mActionBar.addOnMenuVisibilityListener(this);
		}
	}

	/** 
	* <p>Title: setHomeButtonEnabled</p> 
	* <p>Description: </p> 
	* @param enabled 
	* @see com.actionbarsherlock.app.ActionBar#setHomeButtonEnabled(boolean) 
	*/
	@Override
	public void setHomeButtonEnabled(boolean enabled) {
		mActionBar.setHomeButtonEnabled(enabled);
	}

	/** 
	* <p>Title: getThemedContext</p> 
	* <p>Description: </p> 
	* @return 
	* @see com.actionbarsherlock.app.ActionBar#getThemedContext() 
	*/
	@Override
	public Context getThemedContext() {
		return mActionBar.getThemedContext();
	}

	/** 
	* <p>Title: setCustomView</p> 
	* <p>Description: </p> 
	* @param view 
	* @see com.actionbarsherlock.app.ActionBar#setCustomView(android.view.View) 
	*/
	@Override
	public void setCustomView(View view) {
		mActionBar.setCustomView(view);
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
		android.app.ActionBar.LayoutParams lp = new android.app.ActionBar.LayoutParams(
				layoutParams);
		lp.gravity = layoutParams.gravity;
		lp.bottomMargin = layoutParams.bottomMargin;
		lp.topMargin = layoutParams.topMargin;
		lp.leftMargin = layoutParams.leftMargin;
		lp.rightMargin = layoutParams.rightMargin;
		mActionBar.setCustomView(view, lp);
	}

	/** 
	* <p>Title: setCustomView</p> 
	* <p>Description: </p> 
	* @param resId 
	* @see com.actionbarsherlock.app.ActionBar#setCustomView(int) 
	*/
	@Override
	public void setCustomView(int resId) {
		mActionBar.setCustomView(resId);
	}

	/** 
	* <p>Title: setIcon</p> 
	* <p>Description: </p> 
	* @param resId 
	* @see com.actionbarsherlock.app.ActionBar#setIcon(int) 
	*/
	@Override
	public void setIcon(int resId) {
		mActionBar.setIcon(resId);
	}

	/** 
	* <p>Title: setIcon</p> 
	* <p>Description: </p> 
	* @param icon 
	* @see com.actionbarsherlock.app.ActionBar#setIcon(android.graphics.drawable.Drawable) 
	*/
	@Override
	public void setIcon(Drawable icon) {
		mActionBar.setIcon(icon);
	}

	/** 
	* <p>Title: setLogo</p> 
	* <p>Description: </p> 
	* @param resId 
	* @see com.actionbarsherlock.app.ActionBar#setLogo(int) 
	*/
	@Override
	public void setLogo(int resId) {
		mActionBar.setLogo(resId);
	}

	/** 
	* <p>Title: setLogo</p> 
	* <p>Description: </p> 
	* @param logo 
	* @see com.actionbarsherlock.app.ActionBar#setLogo(android.graphics.drawable.Drawable) 
	*/
	@Override
	public void setLogo(Drawable logo) {
		mActionBar.setLogo(logo);
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
		mNavigationListener = callback;
		mActionBar.setListNavigationCallbacks(adapter,
				(callback != null) ? this : null);
	}

	/** 
	* <p>Title: onNavigationItemSelected</p> 
	* <p>Description: </p> 
	* @param itemPosition
	* @param itemId
	* @return 
	* @see android.app.ActionBar.OnNavigationListener#onNavigationItemSelected(int, long) 
	*/
	@Override
	public boolean onNavigationItemSelected(int itemPosition, long itemId) {
		// This should never be a NullPointerException since we only set
		// ourselves as the listener when the callback is not null.
		return mNavigationListener.onNavigationItemSelected(itemPosition,
				itemId);
	}

	/** 
	* <p>Title: setSelectedNavigationItem</p> 
	* <p>Description: </p> 
	* @param position 
	* @see com.actionbarsherlock.app.ActionBar#setSelectedNavigationItem(int) 
	*/
	@Override
	public void setSelectedNavigationItem(int position) {
		mActionBar.setSelectedNavigationItem(position);
	}

	/** 
	* <p>Title: getSelectedNavigationIndex</p> 
	* <p>Description: </p> 
	* @return 
	* @see com.actionbarsherlock.app.ActionBar#getSelectedNavigationIndex() 
	*/
	@Override
	public int getSelectedNavigationIndex() {
		return mActionBar.getSelectedNavigationIndex();
	}

	/** 
	* <p>Title: getNavigationItemCount</p> 
	* <p>Description: </p> 
	* @return 
	* @see com.actionbarsherlock.app.ActionBar#getNavigationItemCount() 
	*/
	@Override
	public int getNavigationItemCount() {
		return mActionBar.getNavigationItemCount();
	}

	/** 
	* <p>Title: setTitle</p> 
	* <p>Description: </p> 
	* @param title 
	* @see com.actionbarsherlock.app.ActionBar#setTitle(java.lang.CharSequence) 
	*/
	@Override
	public void setTitle(CharSequence title) {
		mActionBar.setTitle(title);
	}

	/** 
	* <p>Title: setTitle</p> 
	* <p>Description: </p> 
	* @param resId 
	* @see com.actionbarsherlock.app.ActionBar#setTitle(int) 
	*/
	@Override
	public void setTitle(int resId) {
		mActionBar.setTitle(resId);
	}

	/** 
	* <p>Title: setSubtitle</p> 
	* <p>Description: </p> 
	* @param subtitle 
	* @see com.actionbarsherlock.app.ActionBar#setSubtitle(java.lang.CharSequence) 
	*/
	@Override
	public void setSubtitle(CharSequence subtitle) {
		mActionBar.setSubtitle(subtitle);
	}

	/** 
	* <p>Title: setSubtitle</p> 
	* <p>Description: </p> 
	* @param resId 
	* @see com.actionbarsherlock.app.ActionBar#setSubtitle(int) 
	*/
	@Override
	public void setSubtitle(int resId) {
		mActionBar.setSubtitle(resId);
	}

	/** 
	* <p>Title: setDisplayOptions</p> 
	* <p>Description: </p> 
	* @param options 
	* @see com.actionbarsherlock.app.ActionBar#setDisplayOptions(int) 
	*/
	@Override
	public void setDisplayOptions(int options) {
		mActionBar.setDisplayOptions(options);
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
		mActionBar.setDisplayOptions(options, mask);
	}

	/** 
	* <p>Title: setDisplayUseLogoEnabled</p> 
	* <p>Description: </p> 
	* @param useLogo 
	* @see com.actionbarsherlock.app.ActionBar#setDisplayUseLogoEnabled(boolean) 
	*/
	@Override
	public void setDisplayUseLogoEnabled(boolean useLogo) {
		mActionBar.setDisplayUseLogoEnabled(useLogo);
	}

	/** 
	* <p>Title: setDisplayShowHomeEnabled</p> 
	* <p>Description: </p> 
	* @param showHome 
	* @see com.actionbarsherlock.app.ActionBar#setDisplayShowHomeEnabled(boolean) 
	*/
	@Override
	public void setDisplayShowHomeEnabled(boolean showHome) {
		mActionBar.setDisplayShowHomeEnabled(showHome);
	}

	/** 
	* <p>Title: setDisplayHomeAsUpEnabled</p> 
	* <p>Description: </p> 
	* @param showHomeAsUp 
	* @see com.actionbarsherlock.app.ActionBar#setDisplayHomeAsUpEnabled(boolean) 
	*/
	@Override
	public void setDisplayHomeAsUpEnabled(boolean showHomeAsUp) {
		mActionBar.setDisplayHomeAsUpEnabled(showHomeAsUp);
	}

	/** 
	* <p>Title: setDisplayShowTitleEnabled</p> 
	* <p>Description: </p> 
	* @param showTitle 
	* @see com.actionbarsherlock.app.ActionBar#setDisplayShowTitleEnabled(boolean) 
	*/
	@Override
	public void setDisplayShowTitleEnabled(boolean showTitle) {
		mActionBar.setDisplayShowTitleEnabled(showTitle);
	}

	/** 
	* <p>Title: setDisplayShowCustomEnabled</p> 
	* <p>Description: </p> 
	* @param showCustom 
	* @see com.actionbarsherlock.app.ActionBar#setDisplayShowCustomEnabled(boolean) 
	*/
	@Override
	public void setDisplayShowCustomEnabled(boolean showCustom) {
		mActionBar.setDisplayShowCustomEnabled(showCustom);
	}

	/** 
	* <p>Title: setBackgroundDrawable</p> 
	* <p>Description: </p> 
	* @param d 
	* @see com.actionbarsherlock.app.ActionBar#setBackgroundDrawable(android.graphics.drawable.Drawable) 
	*/
	@Override
	public void setBackgroundDrawable(Drawable d) {
		mActionBar.setBackgroundDrawable(d);
	}

	/** 
	* <p>Title: setStackedBackgroundDrawable</p> 
	* <p>Description: </p> 
	* @param d 
	* @see com.actionbarsherlock.app.ActionBar#setStackedBackgroundDrawable(android.graphics.drawable.Drawable) 
	*/
	@Override
	public void setStackedBackgroundDrawable(Drawable d) {
		mActionBar.setStackedBackgroundDrawable(d);
	}

	/** 
	* <p>Title: setSplitBackgroundDrawable</p> 
	* <p>Description: </p> 
	* @param d 
	* @see com.actionbarsherlock.app.ActionBar#setSplitBackgroundDrawable(android.graphics.drawable.Drawable) 
	*/
	@Override
	public void setSplitBackgroundDrawable(Drawable d) {
		mActionBar.setSplitBackgroundDrawable(d);
	}

	/** 
	* <p>Title: getCustomView</p> 
	* <p>Description: </p> 
	* @return 
	* @see com.actionbarsherlock.app.ActionBar#getCustomView() 
	*/
	@Override
	public View getCustomView() {
		return mActionBar.getCustomView();
	}

	/** 
	* <p>Title: getTitle</p> 
	* <p>Description: </p> 
	* @return 
	* @see com.actionbarsherlock.app.ActionBar#getTitle() 
	*/
	@Override
	public CharSequence getTitle() {
		return mActionBar.getTitle();
	}

	/** 
	* <p>Title: getSubtitle</p> 
	* <p>Description: </p> 
	* @return 
	* @see com.actionbarsherlock.app.ActionBar#getSubtitle() 
	*/
	@Override
	public CharSequence getSubtitle() {
		return mActionBar.getSubtitle();
	}

	/** 
	* <p>Title: getNavigationMode</p> 
	* <p>Description: </p> 
	* @return 
	* @see com.actionbarsherlock.app.ActionBar#getNavigationMode() 
	*/
	@Override
	public int getNavigationMode() {
		return mActionBar.getNavigationMode();
	}

	/** 
	* <p>Title: setNavigationMode</p> 
	* <p>Description: </p> 
	* @param mode 
	* @see com.actionbarsherlock.app.ActionBar#setNavigationMode(int) 
	*/
	@Override
	public void setNavigationMode(int mode) {
		mActionBar.setNavigationMode(mode);
	}

	/** 
	* <p>Title: getDisplayOptions</p> 
	* <p>Description: </p> 
	* @return 
	* @see com.actionbarsherlock.app.ActionBar#getDisplayOptions() 
	*/
	@Override
	public int getDisplayOptions() {
		return mActionBar.getDisplayOptions();
	}

	/**
	 * The Class TabWrapper.
	 */
	public class TabWrapper extends ActionBar.Tab implements
			android.app.ActionBar.TabListener {
		
		/** The m native tab. */
		final android.app.ActionBar.Tab mNativeTab;
		
		/** The m tag. */
		private Object mTag;
		
		/** The m listener. */
		private TabListener mListener;

		/**
		 * Instantiates a new tab wrapper.
		 * 
		 * @param nativeTab
		 *            the native tab
		 */
		public TabWrapper(android.app.ActionBar.Tab nativeTab) {
			mNativeTab = nativeTab;
			mNativeTab.setTag(this);
		}

		/** 
		* <p>Title: getPosition</p> 
		* <p>Description: </p> 
		* @return 
		* @see com.actionbarsherlock.app.ActionBar.Tab#getPosition() 
		*/
		@Override
		public int getPosition() {
			return mNativeTab.getPosition();
		}

		/** 
		* <p>Title: getIcon</p> 
		* <p>Description: </p> 
		* @return 
		* @see com.actionbarsherlock.app.ActionBar.Tab#getIcon() 
		*/
		@Override
		public Drawable getIcon() {
			return mNativeTab.getIcon();
		}

		/** 
		* <p>Title: getText</p> 
		* <p>Description: </p> 
		* @return 
		* @see com.actionbarsherlock.app.ActionBar.Tab#getText() 
		*/
		@Override
		public CharSequence getText() {
			return mNativeTab.getText();
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
			mNativeTab.setIcon(icon);
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
			mNativeTab.setIcon(resId);
			return this;
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
			mNativeTab.setText(text);
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
			mNativeTab.setText(resId);
			return this;
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
			mNativeTab.setCustomView(view);
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
			mNativeTab.setCustomView(layoutResId);
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
			return mNativeTab.getCustomView();
		}

		/** 
		* <p>Title: setTag</p> 
		* <p>Description: </p> 
		* @param obj
		* @return 
		* @see com.actionbarsherlock.app.ActionBar.Tab#setTag(java.lang.Object) 
		*/
		@Override
		public Tab setTag(Object obj) {
			mTag = obj;
			return this;
		}

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
		* <p>Title: setTabListener</p> 
		* <p>Description: </p> 
		* @param listener
		* @return 
		* @see com.actionbarsherlock.app.ActionBar.Tab#setTabListener(com.actionbarsherlock.app.ActionBar.TabListener) 
		*/
		@Override
		public Tab setTabListener(TabListener listener) {
			mNativeTab.setTabListener(listener != null ? this : null);
			mListener = listener;
			return this;
		}

		/** 
		* <p>Title: select</p> 
		* <p>Description: </p>  
		* @see com.actionbarsherlock.app.ActionBar.Tab#select() 
		*/
		@Override
		public void select() {
			mNativeTab.select();
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
			mNativeTab.setContentDescription(resId);
			return this;
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
			mNativeTab.setContentDescription(contentDesc);
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
			return mNativeTab.getContentDescription();
		}

		/** 
		* <p>Title: onTabReselected</p> 
		* <p>Description: </p> 
		* @param tab
		* @param ft 
		* @see android.app.ActionBar.TabListener#onTabReselected(android.app.ActionBar.Tab, android.app.FragmentTransaction) 
		*/
		@Override
		public void onTabReselected(android.app.ActionBar.Tab tab,
				android.app.FragmentTransaction ft) {
			if (mListener != null) {
				FragmentTransaction trans = null;
				if (mActivity instanceof FragmentActivity) {
					trans = ((FragmentActivity) mActivity)
							.getSupportFragmentManager().beginTransaction()
							.disallowAddToBackStack();
				}

				mListener.onTabReselected(this, trans);

				if (trans != null && !trans.isEmpty()) {
					trans.commit();
				}
			}
		}

		/** 
		* <p>Title: onTabSelected</p> 
		* <p>Description: </p> 
		* @param tab
		* @param ft 
		* @see android.app.ActionBar.TabListener#onTabSelected(android.app.ActionBar.Tab, android.app.FragmentTransaction) 
		*/
		@Override
		public void onTabSelected(android.app.ActionBar.Tab tab,
				android.app.FragmentTransaction ft) {
			if (mListener != null) {

				if (mFragmentTransaction == null
						&& mActivity instanceof FragmentActivity) {
					mFragmentTransaction = ((FragmentActivity) mActivity)
							.getSupportFragmentManager().beginTransaction()
							.disallowAddToBackStack();
				}

				mListener.onTabSelected(this, mFragmentTransaction);

				if (mFragmentTransaction != null) {
					if (!mFragmentTransaction.isEmpty()) {
						mFragmentTransaction.commit();
					}
					mFragmentTransaction = null;
				}
			}
		}

		/** 
		* <p>Title: onTabUnselected</p> 
		* <p>Description: </p> 
		* @param tab
		* @param ft 
		* @see android.app.ActionBar.TabListener#onTabUnselected(android.app.ActionBar.Tab, android.app.FragmentTransaction) 
		*/
		@Override
		public void onTabUnselected(android.app.ActionBar.Tab tab,
				android.app.FragmentTransaction ft) {
			if (mListener != null) {
				FragmentTransaction trans = null;
				if (mActivity instanceof FragmentActivity) {
					trans = ((FragmentActivity) mActivity)
							.getSupportFragmentManager().beginTransaction()
							.disallowAddToBackStack();
					mFragmentTransaction = trans;
				}

				mListener.onTabUnselected(this, trans);
			}
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
		return new TabWrapper(mActionBar.newTab());
	}

	/** 
	* <p>Title: addTab</p> 
	* <p>Description: </p> 
	* @param tab 
	* @see com.actionbarsherlock.app.ActionBar#addTab(com.actionbarsherlock.app.ActionBar.Tab) 
	*/
	@Override
	public void addTab(Tab tab) {
		mActionBar.addTab(((TabWrapper) tab).mNativeTab);
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
		mActionBar.addTab(((TabWrapper) tab).mNativeTab, setSelected);
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
		mActionBar.addTab(((TabWrapper) tab).mNativeTab, position);
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
		mActionBar.addTab(((TabWrapper) tab).mNativeTab, position, setSelected);
	}

	/** 
	* <p>Title: removeTab</p> 
	* <p>Description: </p> 
	* @param tab 
	* @see com.actionbarsherlock.app.ActionBar#removeTab(com.actionbarsherlock.app.ActionBar.Tab) 
	*/
	@Override
	public void removeTab(Tab tab) {
		mActionBar.removeTab(((TabWrapper) tab).mNativeTab);
	}

	/** 
	* <p>Title: removeTabAt</p> 
	* <p>Description: </p> 
	* @param position 
	* @see com.actionbarsherlock.app.ActionBar#removeTabAt(int) 
	*/
	@Override
	public void removeTabAt(int position) {
		mActionBar.removeTabAt(position);
	}

	/** 
	* <p>Title: removeAllTabs</p> 
	* <p>Description: </p>  
	* @see com.actionbarsherlock.app.ActionBar#removeAllTabs() 
	*/
	@Override
	public void removeAllTabs() {
		mActionBar.removeAllTabs();
	}

	/** 
	* <p>Title: selectTab</p> 
	* <p>Description: </p> 
	* @param tab 
	* @see com.actionbarsherlock.app.ActionBar#selectTab(com.actionbarsherlock.app.ActionBar.Tab) 
	*/
	@Override
	public void selectTab(Tab tab) {
		mActionBar.selectTab(((TabWrapper) tab).mNativeTab);
	}

	/** 
	* <p>Title: getSelectedTab</p> 
	* <p>Description: </p> 
	* @return 
	* @see com.actionbarsherlock.app.ActionBar#getSelectedTab() 
	*/
	@Override
	public Tab getSelectedTab() {
		android.app.ActionBar.Tab selected = mActionBar.getSelectedTab();
		return (selected != null) ? (Tab) selected.getTag() : null;
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
		android.app.ActionBar.Tab selected = mActionBar.getTabAt(index);
		return (selected != null) ? (Tab) selected.getTag() : null;
	}

	/** 
	* <p>Title: getTabCount</p> 
	* <p>Description: </p> 
	* @return 
	* @see com.actionbarsherlock.app.ActionBar#getTabCount() 
	*/
	@Override
	public int getTabCount() {
		return mActionBar.getTabCount();
	}

	/** 
	* <p>Title: getHeight</p> 
	* <p>Description: </p> 
	* @return 
	* @see com.actionbarsherlock.app.ActionBar#getHeight() 
	*/
	@Override
	public int getHeight() {
		return mActionBar.getHeight();
	}

	/** 
	* <p>Title: show</p> 
	* <p>Description: </p>  
	* @see com.actionbarsherlock.app.ActionBar#show() 
	*/
	@Override
	public void show() {
		mActionBar.show();
	}

	/** 
	* <p>Title: hide</p> 
	* <p>Description: </p>  
	* @see com.actionbarsherlock.app.ActionBar#hide() 
	*/
	@Override
	public void hide() {
		mActionBar.hide();
	}

	/** 
	* <p>Title: isShowing</p> 
	* <p>Description: </p> 
	* @return 
	* @see com.actionbarsherlock.app.ActionBar#isShowing() 
	*/
	@Override
	public boolean isShowing() {
		return mActionBar.isShowing();
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
	* <p>Title: onMenuVisibilityChanged</p> 
	* <p>Description: </p> 
	* @param isVisible 
	* @see android.app.ActionBar.OnMenuVisibilityListener#onMenuVisibilityChanged(boolean) 
	*/
	@Override
	public void onMenuVisibilityChanged(boolean isVisible) {
		for (OnMenuVisibilityListener listener : mMenuVisibilityListeners) {
			listener.onMenuVisibilityChanged(isVisible);
		}
	}
}
