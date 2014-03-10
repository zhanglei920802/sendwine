/**   
* @Title: SubMenu.java 
* @Package com.actionbarsherlock.view 
* @Description: 
*			声明:
*			1)掌控校园是本人的大学的创业作品,也是本人的毕业设计
*			2)本程序尚未进行开放源代码,所以禁止组织或者是个人泄露源码,否则将会追究其刑事责任
*			3)编写本软件,我需要感谢彭老师以及其他鼓励和支持我的同学以及朋友
*			4)本程序的最终所有权属于本人	
* @author  张雷 794857063@qq.com
* @date 2013-11-14 19:45:02 
* @version V1.0   
*/

package com.actionbarsherlock.view;

import android.graphics.drawable.Drawable;
import android.view.View;

// TODO: Auto-generated Javadoc
/**
 * Subclass of {@link Menu} for sub menus.
 * <p>
 * Sub menus do not support item icons, or nested sub menus.
 * 
 * <div class="special reference">
 * <h3>Developer Guides</h3>
 * <p>
 * For information about creating menus, read the <a href="{@docRoot}
 * guide/topics/ui/menus.html">Menus</a> developer guide.
 * </p>
 * </div>
 */

public interface SubMenu extends Menu {
	/**
	 * Sets the submenu header's title to the title given in <var>titleRes</var>
	 * resource identifier.
	 * 
	 * @param titleRes
	 *            The string resource identifier used for the title.
	 * @return This SubMenu so additional setters can be called.
	 */
	public SubMenu setHeaderTitle(int titleRes);

	/**
	 * Sets the submenu header's title to the title given in <var>title</var>.
	 * 
	 * @param title
	 *            The character sequence used for the title.
	 * @return This SubMenu so additional setters can be called.
	 */
	public SubMenu setHeaderTitle(CharSequence title);

	/**
	 * Sets the submenu header's icon to the icon given in <var>iconRes</var>
	 * resource id.
	 * 
	 * @param iconRes
	 *            The resource identifier used for the icon.
	 * @return This SubMenu so additional setters can be called.
	 */
	public SubMenu setHeaderIcon(int iconRes);

	/**
	 * Sets the submenu header's icon to the icon given in <var>icon</var>.
	 * 
	 * @param icon
	 *            The {@link Drawable} used for the icon.
	 * @return This SubMenu so additional setters can be called.
	 *         {@link Drawable}.
	 */
	public SubMenu setHeaderIcon(Drawable icon);

	/**
	 * Sets the header of the submenu to the {@link View} given in
	 * <var>view</var>. This replaces the header title and icon (and those
	 * replace this).
	 * 
	 * @param view
	 *            The {@link View} used for the header.
	 * @return This SubMenu so additional setters can be called.
	 */
	public SubMenu setHeaderView(View view);

	/**
	 * Clears the header of the submenu.
	 */
	public void clearHeader();

	/**
	 * Change the icon associated with this submenu's item in its parent menu.
	 * 
	 * @param iconRes
	 *            The new icon (as a resource ID) to be displayed.
	 * @return This SubMenu so additional setters can be called.
	 * @see MenuItem#setIcon(int)
	 */
	public SubMenu setIcon(int iconRes);

	/**
	 * Change the icon associated with this submenu's item in its parent menu.
	 * 
	 * @param icon
	 *            The new icon (as a Drawable) to be displayed.
	 * @return This SubMenu so additional setters can be called.
	 * @see MenuItem#setIcon(Drawable)
	 */
	public SubMenu setIcon(Drawable icon);

	/**
	 * Gets the {@link MenuItem} that represents this submenu in the parent
	 * menu. Use this for setting additional item attributes.
	 * 
	 * @return The {@link MenuItem} that launches the submenu when invoked.
	 */
	public MenuItem getItem();
}
