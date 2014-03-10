/**   
* @Title: MenuItemWrapper.java 
* @Package com.actionbarsherlock.internal.view.menu 
* @Description: 
*			声明:
*			1)掌控校园是本人的大学的创业作品,也是本人的毕业设计
*			2)本程序尚未进行开放源代码,所以禁止组织或者是个人泄露源码,否则将会追究其刑事责任
*			3)编写本软件,我需要感谢彭老师以及其他鼓励和支持我的同学以及朋友
*			4)本程序的最终所有权属于本人	
* @author  张雷 794857063@qq.com
* @date 2013-11-14 19:34:38 
* @version V1.0   
*/
package com.actionbarsherlock.internal.view.menu;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View;
import com.actionbarsherlock.internal.view.ActionProviderWrapper;
import com.actionbarsherlock.internal.widget.CollapsibleActionViewWrapper;
import com.actionbarsherlock.view.ActionProvider;
import com.actionbarsherlock.view.CollapsibleActionView;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.SubMenu;

// TODO: Auto-generated Javadoc
/**
 * The Class MenuItemWrapper.
 */
public class MenuItemWrapper implements MenuItem,
		android.view.MenuItem.OnMenuItemClickListener {
	
	/** The m native item. */
	private final android.view.MenuItem mNativeItem;
	
	/** The m sub menu. */
	private SubMenu mSubMenu = null;
	
	/** The m menu item click listener. */
	private OnMenuItemClickListener mMenuItemClickListener = null;
	
	/** The m action expand listener. */
	private OnActionExpandListener mActionExpandListener = null;
	
	/** The m native action expand listener. */
	private android.view.MenuItem.OnActionExpandListener mNativeActionExpandListener = null;

	/**
	 * Instantiates a new menu item wrapper.
	 * 
	 * @param nativeItem
	 *            the native item
	 */
	public MenuItemWrapper(android.view.MenuItem nativeItem) {
		if (nativeItem == null) {
			throw new IllegalStateException("Wrapped menu item cannot be null.");
		}
		mNativeItem = nativeItem;
	}

	/** 
	* <p>Title: getItemId</p> 
	* <p>Description: </p> 
	* @return 
	* @see com.actionbarsherlock.view.MenuItem#getItemId() 
	*/
	@Override
	public int getItemId() {
		return mNativeItem.getItemId();
	}

	/** 
	* <p>Title: getGroupId</p> 
	* <p>Description: </p> 
	* @return 
	* @see com.actionbarsherlock.view.MenuItem#getGroupId() 
	*/
	@Override
	public int getGroupId() {
		return mNativeItem.getGroupId();
	}

	/** 
	* <p>Title: getOrder</p> 
	* <p>Description: </p> 
	* @return 
	* @see com.actionbarsherlock.view.MenuItem#getOrder() 
	*/
	@Override
	public int getOrder() {
		return mNativeItem.getOrder();
	}

	/** 
	* <p>Title: setTitle</p> 
	* <p>Description: </p> 
	* @param title
	* @return 
	* @see com.actionbarsherlock.view.MenuItem#setTitle(java.lang.CharSequence) 
	*/
	@Override
	public MenuItem setTitle(CharSequence title) {
		mNativeItem.setTitle(title);
		return this;
	}

	/** 
	* <p>Title: setTitle</p> 
	* <p>Description: </p> 
	* @param title
	* @return 
	* @see com.actionbarsherlock.view.MenuItem#setTitle(int) 
	*/
	@Override
	public MenuItem setTitle(int title) {
		mNativeItem.setTitle(title);
		return this;
	}

	/** 
	* <p>Title: getTitle</p> 
	* <p>Description: </p> 
	* @return 
	* @see com.actionbarsherlock.view.MenuItem#getTitle() 
	*/
	@Override
	public CharSequence getTitle() {
		return mNativeItem.getTitle();
	}

	/** 
	* <p>Title: setTitleCondensed</p> 
	* <p>Description: </p> 
	* @param title
	* @return 
	* @see com.actionbarsherlock.view.MenuItem#setTitleCondensed(java.lang.CharSequence) 
	*/
	@Override
	public MenuItem setTitleCondensed(CharSequence title) {
		mNativeItem.setTitleCondensed(title);
		return this;
	}

	/** 
	* <p>Title: getTitleCondensed</p> 
	* <p>Description: </p> 
	* @return 
	* @see com.actionbarsherlock.view.MenuItem#getTitleCondensed() 
	*/
	@Override
	public CharSequence getTitleCondensed() {
		return mNativeItem.getTitleCondensed();
	}

	/** 
	* <p>Title: setIcon</p> 
	* <p>Description: </p> 
	* @param icon
	* @return 
	* @see com.actionbarsherlock.view.MenuItem#setIcon(android.graphics.drawable.Drawable) 
	*/
	@Override
	public MenuItem setIcon(Drawable icon) {
		mNativeItem.setIcon(icon);
		return this;
	}

	/** 
	* <p>Title: setIcon</p> 
	* <p>Description: </p> 
	* @param iconRes
	* @return 
	* @see com.actionbarsherlock.view.MenuItem#setIcon(int) 
	*/
	@Override
	public MenuItem setIcon(int iconRes) {
		mNativeItem.setIcon(iconRes);
		return this;
	}

	/** 
	* <p>Title: getIcon</p> 
	* <p>Description: </p> 
	* @return 
	* @see com.actionbarsherlock.view.MenuItem#getIcon() 
	*/
	@Override
	public Drawable getIcon() {
		return mNativeItem.getIcon();
	}

	/** 
	* <p>Title: setIntent</p> 
	* <p>Description: </p> 
	* @param intent
	* @return 
	* @see com.actionbarsherlock.view.MenuItem#setIntent(android.content.Intent) 
	*/
	@Override
	public MenuItem setIntent(Intent intent) {
		mNativeItem.setIntent(intent);
		return this;
	}

	/** 
	* <p>Title: getIntent</p> 
	* <p>Description: </p> 
	* @return 
	* @see com.actionbarsherlock.view.MenuItem#getIntent() 
	*/
	@Override
	public Intent getIntent() {
		return mNativeItem.getIntent();
	}

	/** 
	* <p>Title: setShortcut</p> 
	* <p>Description: </p> 
	* @param numericChar
	* @param alphaChar
	* @return 
	* @see com.actionbarsherlock.view.MenuItem#setShortcut(char, char) 
	*/
	@Override
	public MenuItem setShortcut(char numericChar, char alphaChar) {
		mNativeItem.setShortcut(numericChar, alphaChar);
		return this;
	}

	/** 
	* <p>Title: setNumericShortcut</p> 
	* <p>Description: </p> 
	* @param numericChar
	* @return 
	* @see com.actionbarsherlock.view.MenuItem#setNumericShortcut(char) 
	*/
	@Override
	public MenuItem setNumericShortcut(char numericChar) {
		mNativeItem.setNumericShortcut(numericChar);
		return this;
	}

	/** 
	* <p>Title: getNumericShortcut</p> 
	* <p>Description: </p> 
	* @return 
	* @see com.actionbarsherlock.view.MenuItem#getNumericShortcut() 
	*/
	@Override
	public char getNumericShortcut() {
		return mNativeItem.getNumericShortcut();
	}

	/** 
	* <p>Title: setAlphabeticShortcut</p> 
	* <p>Description: </p> 
	* @param alphaChar
	* @return 
	* @see com.actionbarsherlock.view.MenuItem#setAlphabeticShortcut(char) 
	*/
	@Override
	public MenuItem setAlphabeticShortcut(char alphaChar) {
		mNativeItem.setAlphabeticShortcut(alphaChar);
		return this;
	}

	/** 
	* <p>Title: getAlphabeticShortcut</p> 
	* <p>Description: </p> 
	* @return 
	* @see com.actionbarsherlock.view.MenuItem#getAlphabeticShortcut() 
	*/
	@Override
	public char getAlphabeticShortcut() {
		return mNativeItem.getAlphabeticShortcut();
	}

	/** 
	* <p>Title: setCheckable</p> 
	* <p>Description: </p> 
	* @param checkable
	* @return 
	* @see com.actionbarsherlock.view.MenuItem#setCheckable(boolean) 
	*/
	@Override
	public MenuItem setCheckable(boolean checkable) {
		mNativeItem.setCheckable(checkable);
		return this;
	}

	/** 
	* <p>Title: isCheckable</p> 
	* <p>Description: </p> 
	* @return 
	* @see com.actionbarsherlock.view.MenuItem#isCheckable() 
	*/
	@Override
	public boolean isCheckable() {
		return mNativeItem.isCheckable();
	}

	/** 
	* <p>Title: setChecked</p> 
	* <p>Description: </p> 
	* @param checked
	* @return 
	* @see com.actionbarsherlock.view.MenuItem#setChecked(boolean) 
	*/
	@Override
	public MenuItem setChecked(boolean checked) {
		mNativeItem.setChecked(checked);
		return this;
	}

	/** 
	* <p>Title: isChecked</p> 
	* <p>Description: </p> 
	* @return 
	* @see com.actionbarsherlock.view.MenuItem#isChecked() 
	*/
	@Override
	public boolean isChecked() {
		return mNativeItem.isChecked();
	}

	/** 
	* <p>Title: setVisible</p> 
	* <p>Description: </p> 
	* @param visible
	* @return 
	* @see com.actionbarsherlock.view.MenuItem#setVisible(boolean) 
	*/
	@Override
	public MenuItem setVisible(boolean visible) {
		mNativeItem.setVisible(visible);
		return this;
	}

	/** 
	* <p>Title: isVisible</p> 
	* <p>Description: </p> 
	* @return 
	* @see com.actionbarsherlock.view.MenuItem#isVisible() 
	*/
	@Override
	public boolean isVisible() {
		return mNativeItem.isVisible();
	}

	/** 
	* <p>Title: setEnabled</p> 
	* <p>Description: </p> 
	* @param enabled
	* @return 
	* @see com.actionbarsherlock.view.MenuItem#setEnabled(boolean) 
	*/
	@Override
	public MenuItem setEnabled(boolean enabled) {
		mNativeItem.setEnabled(enabled);
		return this;
	}

	/** 
	* <p>Title: isEnabled</p> 
	* <p>Description: </p> 
	* @return 
	* @see com.actionbarsherlock.view.MenuItem#isEnabled() 
	*/
	@Override
	public boolean isEnabled() {
		return mNativeItem.isEnabled();
	}

	/** 
	* <p>Title: hasSubMenu</p> 
	* <p>Description: </p> 
	* @return 
	* @see com.actionbarsherlock.view.MenuItem#hasSubMenu() 
	*/
	@Override
	public boolean hasSubMenu() {
		return mNativeItem.hasSubMenu();
	}

	/** 
	* <p>Title: getSubMenu</p> 
	* <p>Description: </p> 
	* @return 
	* @see com.actionbarsherlock.view.MenuItem#getSubMenu() 
	*/
	@Override
	public SubMenu getSubMenu() {
		if (hasSubMenu() && (mSubMenu == null)) {
			mSubMenu = new SubMenuWrapper(mNativeItem.getSubMenu());
		}
		return mSubMenu;
	}

	/** 
	* <p>Title: setOnMenuItemClickListener</p> 
	* <p>Description: </p> 
	* @param menuItemClickListener
	* @return 
	* @see com.actionbarsherlock.view.MenuItem#setOnMenuItemClickListener(com.actionbarsherlock.view.MenuItem.OnMenuItemClickListener) 
	*/
	@Override
	public MenuItem setOnMenuItemClickListener(
			OnMenuItemClickListener menuItemClickListener) {
		mMenuItemClickListener = menuItemClickListener;
		// Register ourselves as the listener to proxy
		mNativeItem.setOnMenuItemClickListener(this);
		return this;
	}

	/** 
	* <p>Title: onMenuItemClick</p> 
	* <p>Description: </p> 
	* @param item
	* @return 
	* @see android.view.MenuItem.OnMenuItemClickListener#onMenuItemClick(android.view.MenuItem) 
	*/
	@Override
	public boolean onMenuItemClick(android.view.MenuItem item) {
		if (mMenuItemClickListener != null) {
			return mMenuItemClickListener.onMenuItemClick(this);
		}
		return false;
	}

	/** 
	* <p>Title: getMenuInfo</p> 
	* <p>Description: </p> 
	* @return 
	* @see com.actionbarsherlock.view.MenuItem#getMenuInfo() 
	*/
	@Override
	public ContextMenuInfo getMenuInfo() {
		return mNativeItem.getMenuInfo();
	}

	/** 
	* <p>Title: setShowAsAction</p> 
	* <p>Description: </p> 
	* @param actionEnum 
	* @see com.actionbarsherlock.view.MenuItem#setShowAsAction(int) 
	*/
	@Override
	public void setShowAsAction(int actionEnum) {
		mNativeItem.setShowAsAction(actionEnum);
	}

	/** 
	* <p>Title: setShowAsActionFlags</p> 
	* <p>Description: </p> 
	* @param actionEnum
	* @return 
	* @see com.actionbarsherlock.view.MenuItem#setShowAsActionFlags(int) 
	*/
	@Override
	public MenuItem setShowAsActionFlags(int actionEnum) {
		mNativeItem.setShowAsActionFlags(actionEnum);
		return this;
	}

	/** 
	* <p>Title: setActionView</p> 
	* <p>Description: </p> 
	* @param view
	* @return 
	* @see com.actionbarsherlock.view.MenuItem#setActionView(android.view.View) 
	*/
	@Override
	public MenuItem setActionView(View view) {
		if (view != null && view instanceof CollapsibleActionView) {
			view = new CollapsibleActionViewWrapper(view);
		}
		mNativeItem.setActionView(view);
		return this;
	}

	/** 
	* <p>Title: setActionView</p> 
	* <p>Description: </p> 
	* @param resId
	* @return 
	* @see com.actionbarsherlock.view.MenuItem#setActionView(int) 
	*/
	@Override
	public MenuItem setActionView(int resId) {
		// Allow the native menu to inflate the resource
		mNativeItem.setActionView(resId);
		if (resId != 0) {
			// Get newly created view
			View view = mNativeItem.getActionView();
			if (view instanceof CollapsibleActionView) {
				// Wrap it and re-set it
				mNativeItem
						.setActionView(new CollapsibleActionViewWrapper(view));
			}
		}
		return this;
	}

	/** 
	* <p>Title: getActionView</p> 
	* <p>Description: </p> 
	* @return 
	* @see com.actionbarsherlock.view.MenuItem#getActionView() 
	*/
	@Override
	public View getActionView() {
		View actionView = mNativeItem.getActionView();
		if (actionView instanceof CollapsibleActionViewWrapper) {
			return ((CollapsibleActionViewWrapper) actionView).unwrap();
		}
		return actionView;
	}

	/** 
	* <p>Title: setActionProvider</p> 
	* <p>Description: </p> 
	* @param actionProvider
	* @return 
	* @see com.actionbarsherlock.view.MenuItem#setActionProvider(com.actionbarsherlock.view.ActionProvider) 
	*/
	@Override
	public MenuItem setActionProvider(ActionProvider actionProvider) {
		mNativeItem
				.setActionProvider(new ActionProviderWrapper(actionProvider));
		return this;
	}

	/** 
	* <p>Title: getActionProvider</p> 
	* <p>Description: </p> 
	* @return 
	* @see com.actionbarsherlock.view.MenuItem#getActionProvider() 
	*/
	@Override
	public ActionProvider getActionProvider() {
		android.view.ActionProvider nativeProvider = mNativeItem
				.getActionProvider();
		if (nativeProvider != null
				&& nativeProvider instanceof ActionProviderWrapper) {
			return ((ActionProviderWrapper) nativeProvider).unwrap();
		}
		return null;
	}

	/** 
	* <p>Title: expandActionView</p> 
	* <p>Description: </p> 
	* @return 
	* @see com.actionbarsherlock.view.MenuItem#expandActionView() 
	*/
	@Override
	public boolean expandActionView() {
		return mNativeItem.expandActionView();
	}

	/** 
	* <p>Title: collapseActionView</p> 
	* <p>Description: </p> 
	* @return 
	* @see com.actionbarsherlock.view.MenuItem#collapseActionView() 
	*/
	@Override
	public boolean collapseActionView() {
		return mNativeItem.collapseActionView();
	}

	/** 
	* <p>Title: isActionViewExpanded</p> 
	* <p>Description: </p> 
	* @return 
	* @see com.actionbarsherlock.view.MenuItem#isActionViewExpanded() 
	*/
	@Override
	public boolean isActionViewExpanded() {
		return mNativeItem.isActionViewExpanded();
	}

	/** 
	* <p>Title: setOnActionExpandListener</p> 
	* <p>Description: </p> 
	* @param listener
	* @return 
	* @see com.actionbarsherlock.view.MenuItem#setOnActionExpandListener(com.actionbarsherlock.view.MenuItem.OnActionExpandListener) 
	*/
	@Override
	public MenuItem setOnActionExpandListener(OnActionExpandListener listener) {
		mActionExpandListener = listener;

		if (mNativeActionExpandListener == null) {
			mNativeActionExpandListener = new android.view.MenuItem.OnActionExpandListener() {
				@Override
				public boolean onMenuItemActionExpand(
						android.view.MenuItem menuItem) {
					if (mActionExpandListener != null) {
						return mActionExpandListener
								.onMenuItemActionExpand(MenuItemWrapper.this);
					}
					return false;
				}

				@Override
				public boolean onMenuItemActionCollapse(
						android.view.MenuItem menuItem) {
					if (mActionExpandListener != null) {
						return mActionExpandListener
								.onMenuItemActionCollapse(MenuItemWrapper.this);
					}
					return false;
				}
			};

			// Register our inner-class as the listener to proxy method calls
			mNativeItem.setOnActionExpandListener(mNativeActionExpandListener);
		}

		return this;
	}
}
