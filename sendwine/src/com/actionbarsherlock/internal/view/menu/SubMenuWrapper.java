/**   
* @Title: SubMenuWrapper.java 
* @Package com.actionbarsherlock.internal.view.menu 
* @Description: 
*			声明:
*			1)掌控校园是本人的大学的创业作品,也是本人的毕业设计
*			2)本程序尚未进行开放源代码,所以禁止组织或者是个人泄露源码,否则将会追究其刑事责任
*			3)编写本软件,我需要感谢彭老师以及其他鼓励和支持我的同学以及朋友
*			4)本程序的最终所有权属于本人	
* @author  张雷 794857063@qq.com
* @date 2013-11-14 19:34:52 
* @version V1.0   
*/
package com.actionbarsherlock.internal.view.menu;

import android.graphics.drawable.Drawable;
import android.view.View;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.SubMenu;

// TODO: Auto-generated Javadoc
/**
 * The Class SubMenuWrapper.
 */
public class SubMenuWrapper extends MenuWrapper implements SubMenu {
	
	/** The m native sub menu. */
	private final android.view.SubMenu mNativeSubMenu;
	
	/** The m item. */
	private MenuItem mItem = null;

	/**
	 * Instantiates a new sub menu wrapper.
	 * 
	 * @param nativeSubMenu
	 *            the native sub menu
	 */
	public SubMenuWrapper(android.view.SubMenu nativeSubMenu) {
		super(nativeSubMenu);
		mNativeSubMenu = nativeSubMenu;
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
		mNativeSubMenu.setHeaderTitle(titleRes);
		return this;
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
		mNativeSubMenu.setHeaderTitle(title);
		return this;
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
		mNativeSubMenu.setHeaderIcon(iconRes);
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
		mNativeSubMenu.setHeaderIcon(icon);
		return this;
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
		mNativeSubMenu.setHeaderView(view);
		return this;
	}

	/** 
	* <p>Title: clearHeader</p> 
	* <p>Description: </p>  
	* @see com.actionbarsherlock.view.SubMenu#clearHeader() 
	*/
	@Override
	public void clearHeader() {
		mNativeSubMenu.clearHeader();
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
		mNativeSubMenu.setIcon(iconRes);
		return this;
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
		mNativeSubMenu.setIcon(icon);
		return this;
	}

	/** 
	* <p>Title: getItem</p> 
	* <p>Description: </p> 
	* @return 
	* @see com.actionbarsherlock.view.SubMenu#getItem() 
	*/
	@Override
	public MenuItem getItem() {
		if (mItem == null) {
			mItem = new MenuItemWrapper(mNativeSubMenu.getItem());
		}
		return mItem;
	}
}
