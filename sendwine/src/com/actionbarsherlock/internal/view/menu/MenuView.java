/**   
* @Title: MenuView.java 
* @Package com.actionbarsherlock.internal.view.menu 
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

package com.actionbarsherlock.internal.view.menu;

import android.graphics.drawable.Drawable;

// TODO: Auto-generated Javadoc
/**
 * Minimal interface for a menu view. {@link #initialize(MenuBuilder)} must be
 * called for the menu to be functional.
 * 
 * @hide
 */
public interface MenuView {
	/**
	 * Initializes the menu to the given menu. This should be called after the
	 * view is inflated.
	 * 
	 * @param menu
	 *            The menu that this MenuView should display.
	 */
	public void initialize(MenuBuilder menu);

	/**
	 * Returns the default animations to be used for this menu when
	 * entering/exiting.
	 * 
	 * @return A resource ID for the default animations to be used for this
	 *         menu.
	 */
	public int getWindowAnimations();

	/**
	 * Minimal interface for a menu item view.
	 * {@link #initialize(MenuItemImpl, int)} must be called for the item to be
	 * functional.
	 */
	public interface ItemView {
		/**
		 * Initializes with the provided MenuItemData. This should be called
		 * after the view is inflated.
		 * 
		 * @param itemData
		 *            The item that this ItemView should display.
		 * @param menuType
		 *            The type of this menu, one of
		 *            {@link MenuBuilder#TYPE_ICON},
		 *            {@link MenuBuilder#TYPE_EXPANDED},
		 *            {@link MenuBuilder#TYPE_DIALOG}).
		 */
		public void initialize(MenuItemImpl itemData, int menuType);

		/**
		 * Gets the item data that this view is displaying.
		 * 
		 * @return the item data, or null if there is not one
		 */
		public MenuItemImpl getItemData();

		/**
		 * Sets the title of the item view.
		 * 
		 * @param title
		 *            The title to set.
		 */
		public void setTitle(CharSequence title);

		/**
		 * Sets the enabled state of the item view.
		 * 
		 * @param enabled
		 *            Whether the item view should be enabled.
		 */
		public void setEnabled(boolean enabled);

		/**
		 * Displays the checkbox for the item view. This does not ensure the
		 * item view will be checked, for that use {@link #setChecked}.
		 * 
		 * @param checkable
		 *            Whether to display the checkbox or to hide it
		 */
		public void setCheckable(boolean checkable);

		/**
		 * Checks the checkbox for the item view. If the checkbox is hidden, it
		 * will NOT be made visible, call {@link #setCheckable(boolean)} for
		 * that.
		 * 
		 * @param checked
		 *            Whether the checkbox should be checked
		 */
		public void setChecked(boolean checked);

		/**
		 * Sets the shortcut for the item.
		 * 
		 * @param showShortcut
		 *            Whether a shortcut should be shown(if false, the value of
		 *            shortcutKey should be ignored).
		 * @param shortcutKey
		 *            The shortcut key that should be shown on the ItemView.
		 */
		public void setShortcut(boolean showShortcut, char shortcutKey);

		/**
		 * Set the icon of this item view.
		 * 
		 * @param icon
		 *            The icon of this item. null to hide the icon.
		 */
		public void setIcon(Drawable icon);

		/**
		 * Whether this item view prefers displaying the condensed title rather
		 * than the normal title. If a condensed title is not available, the
		 * normal title will be used.
		 * 
		 * @return Whether this item view prefers displaying the condensed
		 *         title.
		 */
		public boolean prefersCondensedTitle();

		/**
		 * Whether this item view shows an icon.
		 * 
		 * @return Whether this item view shows an icon.
		 */
		public boolean showsIcon();
	}
}
