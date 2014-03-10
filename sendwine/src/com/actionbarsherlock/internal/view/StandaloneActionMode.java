/**   
* @Title: StandaloneActionMode.java 
* @Package com.actionbarsherlock.internal.view 
* @Description: 
*			声明:
*			1)掌控校园是本人的大学的创业作品,也是本人的毕业设计
*			2)本程序尚未进行开放源代码,所以禁止组织或者是个人泄露源码,否则将会追究其刑事责任
*			3)编写本软件,我需要感谢彭老师以及其他鼓励和支持我的同学以及朋友
*			4)本程序的最终所有权属于本人	
* @author  张雷 794857063@qq.com
* @date 2013-11-14 19:33:49 
* @version V1.0   
*/
package com.actionbarsherlock.internal.view;

import android.content.Context;
import android.view.View;
import android.view.accessibility.AccessibilityEvent;

import java.lang.ref.WeakReference;

import com.actionbarsherlock.internal.view.menu.MenuBuilder;
import com.actionbarsherlock.internal.view.menu.MenuPopupHelper;
import com.actionbarsherlock.internal.view.menu.SubMenuBuilder;
import com.actionbarsherlock.internal.widget.ActionBarContextView;
import com.actionbarsherlock.view.ActionMode;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;

// TODO: Auto-generated Javadoc
/**
 * The Class StandaloneActionMode.
 */
public class StandaloneActionMode extends ActionMode implements
		MenuBuilder.Callback {
	
	/** The m context. */
	private Context mContext;
	
	/** The m context view. */
	private ActionBarContextView mContextView;
	
	/** The m callback. */
	private ActionMode.Callback mCallback;
	
	/** The m custom view. */
	private WeakReference<View> mCustomView;
	
	/** The m finished. */
	private boolean mFinished;
	
	/** The m focusable. */
	private boolean mFocusable;

	/** The m menu. */
	private MenuBuilder mMenu;

	/**
	 * Instantiates a new standalone action mode.
	 * 
	 * @param context
	 *            the context
	 * @param view
	 *            the view
	 * @param callback
	 *            the callback
	 * @param isFocusable
	 *            the is focusable
	 */
	public StandaloneActionMode(Context context, ActionBarContextView view,
			ActionMode.Callback callback, boolean isFocusable) {
		mContext = context;
		mContextView = view;
		mCallback = callback;

		mMenu = new MenuBuilder(context)
				.setDefaultShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
		mMenu.setCallback(this);
		mFocusable = isFocusable;
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
	* @param resId 
	* @see com.actionbarsherlock.view.ActionMode#setTitle(int) 
	*/
	@Override
	public void setTitle(int resId) {
		setTitle(mContext.getString(resId));
	}

	/** 
	* <p>Title: setSubtitle</p> 
	* <p>Description: </p> 
	* @param resId 
	* @see com.actionbarsherlock.view.ActionMode#setSubtitle(int) 
	*/
	@Override
	public void setSubtitle(int resId) {
		setSubtitle(mContext.getString(resId));
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
		mCustomView = view != null ? new WeakReference<View>(view) : null;
	}

	/** 
	* <p>Title: invalidate</p> 
	* <p>Description: </p>  
	* @see com.actionbarsherlock.view.ActionMode#invalidate() 
	*/
	@Override
	public void invalidate() {
		mCallback.onPrepareActionMode(this, mMenu);
	}

	/** 
	* <p>Title: finish</p> 
	* <p>Description: </p>  
	* @see com.actionbarsherlock.view.ActionMode#finish() 
	*/
	@Override
	public void finish() {
		if (mFinished) {
			return;
		}
		mFinished = true;

		mContextView
				.sendAccessibilityEvent(AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED);
		mCallback.onDestroyActionMode(this);
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
	* <p>Title: getMenuInflater</p> 
	* <p>Description: </p> 
	* @return 
	* @see com.actionbarsherlock.view.ActionMode#getMenuInflater() 
	*/
	@Override
	public MenuInflater getMenuInflater() {
		return new MenuInflater(mContext);
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
		return mCallback.onActionItemClicked(this, item);
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
		if (!subMenu.hasVisibleItems()) {
			return true;
		}

		new MenuPopupHelper(mContext, subMenu).show();
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
		invalidate();
		mContextView.showOverflowMenu();
	}

	/** 
	* <p>Title: isUiFocusable</p> 
	* <p>Description: </p> 
	* @return 
	* @see com.actionbarsherlock.view.ActionMode#isUiFocusable() 
	*/
	@Override
	public boolean isUiFocusable() {
		return mFocusable;
	}
}
