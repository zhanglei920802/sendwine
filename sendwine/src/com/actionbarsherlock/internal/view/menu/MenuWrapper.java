/**   
* @Title: MenuWrapper.java 
* @Package com.actionbarsherlock.internal.view.menu 
* @Description: 
*			声明:
*			1)掌控校园是本人的大学的创业作品,也是本人的毕业设计
*			2)本程序尚未进行开放源代码,所以禁止组织或者是个人泄露源码,否则将会追究其刑事责任
*			3)编写本软件,我需要感谢彭老师以及其他鼓励和支持我的同学以及朋友
*			4)本程序的最终所有权属于本人	
* @author  张雷 794857063@qq.com
* @date 2013-11-14 19:33:56 
* @version V1.0   
*/
package com.actionbarsherlock.internal.view.menu;

import java.util.WeakHashMap;
import android.content.ComponentName;
import android.content.Intent;
import android.view.KeyEvent;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.SubMenu;

// TODO: Auto-generated Javadoc
/**
 * The Class MenuWrapper.
 */
public class MenuWrapper implements Menu {
	
	/** The m native menu. */
	private final android.view.Menu mNativeMenu;

	/** The m native map. */
	private final WeakHashMap<android.view.MenuItem, MenuItem> mNativeMap = new WeakHashMap<android.view.MenuItem, MenuItem>();

	/**
	 * Instantiates a new menu wrapper.
	 * 
	 * @param nativeMenu
	 *            the native menu
	 */
	public MenuWrapper(android.view.Menu nativeMenu) {
		mNativeMenu = nativeMenu;
	}

	/**
	 * Unwrap.
	 * 
	 * @return the android.view. menu
	 */
	public android.view.Menu unwrap() {
		return mNativeMenu;
	}

	/**
	 * Adds the internal.
	 * 
	 * @param nativeItem
	 *            the native item
	 * @return the menu item
	 */
	private MenuItem addInternal(android.view.MenuItem nativeItem) {
		MenuItem item = new MenuItemWrapper(nativeItem);
		mNativeMap.put(nativeItem, item);
		return item;
	}

	/** 
	* <p>Title: add</p> 
	* <p>Description: </p> 
	* @param title
	* @return 
	* @see com.actionbarsherlock.view.Menu#add(java.lang.CharSequence) 
	*/
	@Override
	public MenuItem add(CharSequence title) {
		return addInternal(mNativeMenu.add(title));
	}

	/** 
	* <p>Title: add</p> 
	* <p>Description: </p> 
	* @param titleRes
	* @return 
	* @see com.actionbarsherlock.view.Menu#add(int) 
	*/
	@Override
	public MenuItem add(int titleRes) {
		return addInternal(mNativeMenu.add(titleRes));
	}

	/** 
	* <p>Title: add</p> 
	* <p>Description: </p> 
	* @param groupId
	* @param itemId
	* @param order
	* @param title
	* @return 
	* @see com.actionbarsherlock.view.Menu#add(int, int, int, java.lang.CharSequence) 
	*/
	@Override
	public MenuItem add(int groupId, int itemId, int order, CharSequence title) {
		return addInternal(mNativeMenu.add(groupId, itemId, order, title));
	}

	/** 
	* <p>Title: add</p> 
	* <p>Description: </p> 
	* @param groupId
	* @param itemId
	* @param order
	* @param titleRes
	* @return 
	* @see com.actionbarsherlock.view.Menu#add(int, int, int, int) 
	*/
	@Override
	public MenuItem add(int groupId, int itemId, int order, int titleRes) {
		return addInternal(mNativeMenu.add(groupId, itemId, order, titleRes));
	}

	/**
	 * Adds the internal.
	 * 
	 * @param nativeSubMenu
	 *            the native sub menu
	 * @return the sub menu
	 */
	private SubMenu addInternal(android.view.SubMenu nativeSubMenu) {
		SubMenu subMenu = new SubMenuWrapper(nativeSubMenu);
		android.view.MenuItem nativeItem = nativeSubMenu.getItem();
		MenuItem item = subMenu.getItem();
		mNativeMap.put(nativeItem, item);
		return subMenu;
	}

	/** 
	* <p>Title: addSubMenu</p> 
	* <p>Description: </p> 
	* @param title
	* @return 
	* @see com.actionbarsherlock.view.Menu#addSubMenu(java.lang.CharSequence) 
	*/
	@Override
	public SubMenu addSubMenu(CharSequence title) {
		return addInternal(mNativeMenu.addSubMenu(title));
	}

	/** 
	* <p>Title: addSubMenu</p> 
	* <p>Description: </p> 
	* @param titleRes
	* @return 
	* @see com.actionbarsherlock.view.Menu#addSubMenu(int) 
	*/
	@Override
	public SubMenu addSubMenu(int titleRes) {
		return addInternal(mNativeMenu.addSubMenu(titleRes));
	}

	/** 
	* <p>Title: addSubMenu</p> 
	* <p>Description: </p> 
	* @param groupId
	* @param itemId
	* @param order
	* @param title
	* @return 
	* @see com.actionbarsherlock.view.Menu#addSubMenu(int, int, int, java.lang.CharSequence) 
	*/
	@Override
	public SubMenu addSubMenu(int groupId, int itemId, int order,
			CharSequence title) {
		return addInternal(mNativeMenu
				.addSubMenu(groupId, itemId, order, title));
	}

	/** 
	* <p>Title: addSubMenu</p> 
	* <p>Description: </p> 
	* @param groupId
	* @param itemId
	* @param order
	* @param titleRes
	* @return 
	* @see com.actionbarsherlock.view.Menu#addSubMenu(int, int, int, int) 
	*/
	@Override
	public SubMenu addSubMenu(int groupId, int itemId, int order, int titleRes) {
		return addInternal(mNativeMenu.addSubMenu(groupId, itemId, order,
				titleRes));
	}

	/** 
	* <p>Title: addIntentOptions</p> 
	* <p>Description: </p> 
	* @param groupId
	* @param itemId
	* @param order
	* @param caller
	* @param specifics
	* @param intent
	* @param flags
	* @param outSpecificItems
	* @return 
	* @see com.actionbarsherlock.view.Menu#addIntentOptions(int, int, int, android.content.ComponentName, android.content.Intent[], android.content.Intent, int, com.actionbarsherlock.view.MenuItem[]) 
	*/
	@Override
	public int addIntentOptions(int groupId, int itemId, int order,
			ComponentName caller, Intent[] specifics, Intent intent, int flags,
			MenuItem[] outSpecificItems) {
		int result;
		if (outSpecificItems != null) {
			android.view.MenuItem[] nativeOutItems = new android.view.MenuItem[outSpecificItems.length];
			result = mNativeMenu.addIntentOptions(groupId, itemId, order,
					caller, specifics, intent, flags, nativeOutItems);
			for (int i = 0, length = outSpecificItems.length; i < length; i++) {
				outSpecificItems[i] = new MenuItemWrapper(nativeOutItems[i]);
			}
		} else {
			result = mNativeMenu.addIntentOptions(groupId, itemId, order,
					caller, specifics, intent, flags, null);
		}
		return result;
	}

	/** 
	* <p>Title: removeItem</p> 
	* <p>Description: </p> 
	* @param id 
	* @see com.actionbarsherlock.view.Menu#removeItem(int) 
	*/
	@Override
	public void removeItem(int id) {
		mNativeMenu.removeItem(id);
	}

	/** 
	* <p>Title: removeGroup</p> 
	* <p>Description: </p> 
	* @param groupId 
	* @see com.actionbarsherlock.view.Menu#removeGroup(int) 
	*/
	@Override
	public void removeGroup(int groupId) {
		mNativeMenu.removeGroup(groupId);
	}

	/** 
	* <p>Title: clear</p> 
	* <p>Description: </p>  
	* @see com.actionbarsherlock.view.Menu#clear() 
	*/
	@Override
	public void clear() {
		mNativeMap.clear();
		mNativeMenu.clear();
	}

	/** 
	* <p>Title: setGroupCheckable</p> 
	* <p>Description: </p> 
	* @param group
	* @param checkable
	* @param exclusive 
	* @see com.actionbarsherlock.view.Menu#setGroupCheckable(int, boolean, boolean) 
	*/
	@Override
	public void setGroupCheckable(int group, boolean checkable,
			boolean exclusive) {
		mNativeMenu.setGroupCheckable(group, checkable, exclusive);
	}

	/** 
	* <p>Title: setGroupVisible</p> 
	* <p>Description: </p> 
	* @param group
	* @param visible 
	* @see com.actionbarsherlock.view.Menu#setGroupVisible(int, boolean) 
	*/
	@Override
	public void setGroupVisible(int group, boolean visible) {
		mNativeMenu.setGroupVisible(group, visible);
	}

	/** 
	* <p>Title: setGroupEnabled</p> 
	* <p>Description: </p> 
	* @param group
	* @param enabled 
	* @see com.actionbarsherlock.view.Menu#setGroupEnabled(int, boolean) 
	*/
	@Override
	public void setGroupEnabled(int group, boolean enabled) {
		mNativeMenu.setGroupEnabled(group, enabled);
	}

	/** 
	* <p>Title: hasVisibleItems</p> 
	* <p>Description: </p> 
	* @return 
	* @see com.actionbarsherlock.view.Menu#hasVisibleItems() 
	*/
	@Override
	public boolean hasVisibleItems() {
		return mNativeMenu.hasVisibleItems();
	}

	/** 
	* <p>Title: findItem</p> 
	* <p>Description: </p> 
	* @param id
	* @return 
	* @see com.actionbarsherlock.view.Menu#findItem(int) 
	*/
	@Override
	public MenuItem findItem(int id) {
		android.view.MenuItem nativeItem = mNativeMenu.findItem(id);
		return findItem(nativeItem);
	}

	/**
	 * Find item.
	 * 
	 * @param nativeItem
	 *            the native item
	 * @return the menu item
	 */
	public MenuItem findItem(android.view.MenuItem nativeItem) {
		if (nativeItem == null) {
			return null;
		}

		MenuItem wrapped = mNativeMap.get(nativeItem);
		if (wrapped != null) {
			return wrapped;
		}

		return addInternal(nativeItem);
	}

	/** 
	* <p>Title: size</p> 
	* <p>Description: </p> 
	* @return 
	* @see com.actionbarsherlock.view.Menu#size() 
	*/
	@Override
	public int size() {
		return mNativeMenu.size();
	}

	/** 
	* <p>Title: getItem</p> 
	* <p>Description: </p> 
	* @param index
	* @return 
	* @see com.actionbarsherlock.view.Menu#getItem(int) 
	*/
	@Override
	public MenuItem getItem(int index) {
		android.view.MenuItem nativeItem = mNativeMenu.getItem(index);
		return findItem(nativeItem);
	}

	/** 
	* <p>Title: close</p> 
	* <p>Description: </p>  
	* @see com.actionbarsherlock.view.Menu#close() 
	*/
	@Override
	public void close() {
		mNativeMenu.close();
	}

	/** 
	* <p>Title: performShortcut</p> 
	* <p>Description: </p> 
	* @param keyCode
	* @param event
	* @param flags
	* @return 
	* @see com.actionbarsherlock.view.Menu#performShortcut(int, android.view.KeyEvent, int) 
	*/
	@Override
	public boolean performShortcut(int keyCode, KeyEvent event, int flags) {
		return mNativeMenu.performShortcut(keyCode, event, flags);
	}

	/** 
	* <p>Title: isShortcutKey</p> 
	* <p>Description: </p> 
	* @param keyCode
	* @param event
	* @return 
	* @see com.actionbarsherlock.view.Menu#isShortcutKey(int, android.view.KeyEvent) 
	*/
	@Override
	public boolean isShortcutKey(int keyCode, KeyEvent event) {
		return mNativeMenu.isShortcutKey(keyCode, event);
	}

	/** 
	* <p>Title: performIdentifierAction</p> 
	* <p>Description: </p> 
	* @param id
	* @param flags
	* @return 
	* @see com.actionbarsherlock.view.Menu#performIdentifierAction(int, int) 
	*/
	@Override
	public boolean performIdentifierAction(int id, int flags) {
		return mNativeMenu.performIdentifierAction(id, flags);
	}

	/** 
	* <p>Title: setQwertyMode</p> 
	* <p>Description: </p> 
	* @param isQwerty 
	* @see com.actionbarsherlock.view.Menu#setQwertyMode(boolean) 
	*/
	@Override
	public void setQwertyMode(boolean isQwerty) {
		mNativeMenu.setQwertyMode(isQwerty);
	}
}
