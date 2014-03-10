/**   
* @Title: SlidingActivityBase.java 
* @Package com.slidingmenu.lib.app 
* @Description: 
*			声明:
*			1)掌控校园是本人的大学的创业作品,也是本人的毕业设计
*			2)本程序尚未进行开放源代码,所以禁止组织或者是个人泄露源码,否则将会追究其刑事责任
*			3)编写本软件,我需要感谢彭老师以及其他鼓励和支持我的同学以及朋友
*			4)本程序的最终所有权属于本人	
* @author  张雷 794857063@qq.com
* @date 2013-11-14 19:34:41 
* @version V1.0   
*/
package com.slidingmenu.lib.app;

import android.view.View;
import android.view.ViewGroup.LayoutParams;

import com.slidingmenu.lib.SlidingMenu;

// TODO: Auto-generated Javadoc
/**
 * The Interface SlidingActivityBase.
 */
public interface SlidingActivityBase {

	/**
	 * Set the behind view content to an explicit view. This view is placed
	 * directly into the behind view 's view hierarchy. It can itself be a
	 * complex view hierarchy.
	 * 
	 * @param view
	 *            The desired content to display.
	 * @param layoutParams
	 *            Layout parameters for the view.
	 */
	public void setBehindContentView(View view, LayoutParams layoutParams);

	/**
	 * Set the behind view content to an explicit view. This view is placed
	 * directly into the behind view 's view hierarchy. It can itself be a
	 * complex view hierarchy. When calling this method, the layout parameters
	 * of the specified view are ignored. Both the width and the height of the
	 * view are set by default to MATCH_PARENT. To use your own layout
	 * parameters, invoke setContentView(android.view.View,
	 * android.view.ViewGroup.LayoutParams) instead.
	 * 
	 * @param view
	 *            The desired content to display.
	 */
	public void setBehindContentView(View view);

	/**
	 * Set the behind view content from a layout resource. The resource will be
	 * inflated, adding all top-level views to the behind view.
	 * 
	 * @param layoutResID
	 *            Resource ID to be inflated.
	 */
	public void setBehindContentView(int layoutResID);

	/**
	 * Gets the SlidingMenu associated with this activity.
	 * 
	 * @return the SlidingMenu associated with this activity.
	 */
	public SlidingMenu getSlidingMenu();

	/**
	 * Toggle the SlidingMenu. If it is open, it will be closed, and vice versa.
	 */
	public void toggle();

	/**
	 * Close the SlidingMenu and show the content view.
	 */
	public void showContent();

	/**
	 * Open the SlidingMenu and show the menu view.
	 */
	public void showMenu();

	/**
	 * Open the SlidingMenu and show the secondary (right) menu view. Will
	 * default to the regular menu if there is only one.
	 */
	public void showSecondaryMenu();

	/**
	 * Controls whether the ActionBar slides along with the above view when the
	 * menu is opened, or if it stays in place.
	 * 
	 * @param slidingActionBarEnabled
	 *            True if you want the ActionBar to slide along with the
	 *            SlidingMenu, false if you want the ActionBar to stay in place
	 */
	public void setSlidingActionBarEnabled(boolean slidingActionBarEnabled);

}
