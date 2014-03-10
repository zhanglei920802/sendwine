/**   
* @Title: SubMenuBuilder.java 
* @Package com.actionbarsherlock.internal.view.menu 
* @Description: 
*			声明:
*			1)掌控校园是本人的大学的创业作品,也是本人的毕业设计
*			2)本程序尚未进行开放源代码,所以禁止组织或者是个人泄露源码,否则将会追究其刑事责任
*			3)编写本软件,我需要感谢彭老师以及其他鼓励和支持我的同学以及朋友
*			4)本程序的最终所有权属于本人	
* @author  张雷 794857063@qq.com
* @date 2013-11-14 19:34:21 
* @version V1.0   
*/

package com.actionbarsherlock.internal.view.menu;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;

import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.SubMenu;

// TODO: Auto-generated Javadoc
/**
 * The model for a sub menu, which is an extension of the menu. Most methods are
 * proxied to the parent menu.
 */
public class SubMenuBuilder extends MenuBuilder implements SubMenu {
	
	/** The m parent menu. */
	private MenuBuilder mParentMenu;
	
	/** The m item. */
	private MenuItemImpl mItem;

	/**
	 * Instantiates a new sub menu builder.
	 * 
	 * @param context
	 *            the context
	 * @param parentMenu
	 *            the parent menu
	 * @param item
	 *            the item
	 */
	public SubMenuBuilder(Context context, MenuBuilder parentMenu,
			MenuItemImpl item) {
		super(context);

		mParentMenu = parentMenu;
		mItem = item;
	}

	/** 
	* <p>Title: setQwertyMode</p> 
	* <p>Description: </p> 
	* @param isQwerty 
	* @see com.actionbarsherlock.internal.view.menu.MenuBuilder#setQwertyMode(boolean) 
	*/
	@Override
	public void setQwertyMode(boolean isQwerty) {
		mParentMenu.setQwertyMode(isQwerty);
	}

	/** 
	* <p>Title: isQwertyMode</p> 
	* <p>Description: </p> 
	* @return 
	* @see com.actionbarsherlock.internal.view.menu.MenuBuilder#isQwertyMode() 
	*/
	@Override
	public boolean isQwertyMode() {
		return mParentMenu.isQwertyMode();
	}

	/** 
	* <p>Title: setShortcutsVisible</p> 
	* <p>Description: </p> 
	* @param shortcutsVisible 
	* @see com.actionbarsherlock.internal.view.menu.MenuBuilder#setShortcutsVisible(boolean) 
	*/
	@Override
	public void setShortcutsVisible(boolean shortcutsVisible) {
		mParentMenu.setShortcutsVisible(shortcutsVisible);
	}

	/** 
	* <p>Title: isShortcutsVisible</p> 
	* <p>Description: </p> 
	* @return 
	* @see com.actionbarsherlock.internal.view.menu.MenuBuilder#isShortcutsVisible() 
	*/
	@Override
	public boolean isShortcutsVisible() {
		return mParentMenu.isShortcutsVisible();
	}

	/**
	 * Gets the parent menu.
	 * 
	 * @return the parent menu
	 */
	public Menu getParentMenu() {
		return mParentMenu;
	}

	/** 
	* <p>Title: getItem</p> 
	* <p>Description: </p> 
	* @return 
	* @see com.actionbarsherlock.view.SubMenu#getItem() 
	*/
	@Override
	public MenuItem getItem() {
		return mItem;
	}

	/** 
	* <p>Title: setCallback</p> 
	* <p>Description: </p> 
	* @param callback 
	* @see com.actionbarsherlock.internal.view.menu.MenuBuilder#setCallback(com.actionbarsherlock.internal.view.menu.MenuBuilder.Callback) 
	*/
	@Override
	public void setCallback(Callback callback) {
		mParentMenu.setCallback(callback);
	}

	/** 
	* <p>Title: getRootMenu</p> 
	* <p>Description: </p> 
	* @return 
	* @see com.actionbarsherlock.internal.view.menu.MenuBuilder#getRootMenu() 
	*/
	@Override
	public MenuBuilder getRootMenu() {
		return mParentMenu;
	}

	/** 
	* <p>Title: dispatchMenuItemSelected</p> 
	* <p>Description: </p> 
	* @param menu
	* @param item
	* @return 
	* @see com.actionbarsherlock.internal.view.menu.MenuBuilder#dispatchMenuItemSelected(com.actionbarsherlock.internal.view.menu.MenuBuilder, com.actionbarsherlock.view.MenuItem) 
	*/
	@Override
	boolean dispatchMenuItemSelected(MenuBuilder menu, MenuItem item) {
		return super.dispatchMenuItemSelected(menu, item)
				|| mParentMenu.dispatchMenuItemSelected(menu, item);
	}

	/** 
	* <p>Title: setIcon</p> 
	* <p>Description: </p> 
	* @param icon
	* @return 
	* @see com.actionbarsherlock.view.SubMenu#setIcon(android.graphics.drawable.Drawable) 
	*/
	@Override
	public SubMenu setIcon(Drawable icon) {
		mItem.setIcon(icon);
		return this;
	}

	/** 
	* <p>Title: setIcon</p> 
	* <p>Description: </p> 
	* @param iconRes
	* @return 
	* @see com.actionbarsherlock.view.SubMenu#setIcon(int) 
	*/
	@Override
	public SubMenu setIcon(int iconRes) {
		mItem.setIcon(iconRes);
		return this;
	}

	/** 
	* <p>Title: setHeaderIcon</p> 
	* <p>Description: </p> 
	* @param icon
	* @return 
	* @see com.actionbarsherlock.view.SubMenu#setHeaderIcon(android.graphics.drawable.Drawable) 
	*/
	@Override
	public SubMenu setHeaderIcon(Drawable icon) {
		return (SubMenu) super.setHeaderIconInt(icon);
	}

	/** 
	* <p>Title: setHeaderIcon</p> 
	* <p>Description: </p> 
	* @param iconRes
	* @return 
	* @see com.actionbarsherlock.view.SubMenu#setHeaderIcon(int) 
	*/
	@Override
	public SubMenu setHeaderIcon(int iconRes) {
		return (SubMenu) super.setHeaderIconInt(iconRes);
	}

	/** 
	* <p>Title: setHeaderTitle</p> 
	* <p>Description: </p> 
	* @param title
	* @return 
	* @see com.actionbarsherlock.view.SubMenu#setHeaderTitle(java.lang.CharSequence) 
	*/
	@Override
	public SubMenu setHeaderTitle(CharSequence title) {
		return (SubMenu) super.setHeaderTitleInt(title);
	}

	/** 
	* <p>Title: setHeaderTitle</p> 
	* <p>Description: </p> 
	* @param titleRes
	* @return 
	* @see com.actionbarsherlock.view.SubMenu#setHeaderTitle(int) 
	*/
	@Override
	public SubMenu setHeaderTitle(int titleRes) {
		return (SubMenu) super.setHeaderTitleInt(titleRes);
	}

	/** 
	* <p>Title: setHeaderView</p> 
	* <p>Description: </p> 
	* @param view
	* @return 
	* @see com.actionbarsherlock.view.SubMenu#setHeaderView(android.view.View) 
	*/
	@Override
	public SubMenu setHeaderView(View view) {
		return (SubMenu) super.setHeaderViewInt(view);
	}

	/** 
	* <p>Title: expandItemActionView</p> 
	* <p>Description: </p> 
	* @param item
	* @return 
	* @see com.actionbarsherlock.internal.view.menu.MenuBuilder#expandItemActionView(com.actionbarsherlock.internal.view.menu.MenuItemImpl) 
	*/
	@Override
	public boolean expandItemActionView(MenuItemImpl item) {
		return mParentMenu.expandItemActionView(item);
	}

	/** 
	* <p>Title: collapseItemActionView</p> 
	* <p>Description: </p> 
	* @param item
	* @return 
	* @see com.actionbarsherlock.internal.view.menu.MenuBuilder#collapseItemActionView(com.actionbarsherlock.internal.view.menu.MenuItemImpl) 
	*/
	@Override
	public boolean collapseItemActionView(MenuItemImpl item) {
		return mParentMenu.collapseItemActionView(item);
	}

	/** 
	* <p>Title: getActionViewStatesKey</p> 
	* <p>Description: </p> 
	* @return 
	* @see com.actionbarsherlock.internal.view.menu.MenuBuilder#getActionViewStatesKey() 
	*/
	@Override
	public String getActionViewStatesKey() {
		final int itemId = mItem != null ? mItem.getItemId() : 0;
		if (itemId == 0) {
			return null;
		}
		return super.getActionViewStatesKey() + ":" + itemId;
	}
}
