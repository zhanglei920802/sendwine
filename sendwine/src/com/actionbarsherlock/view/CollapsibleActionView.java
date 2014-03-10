/**   
* @Title: CollapsibleActionView.java 
* @Package com.actionbarsherlock.view 
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

package com.actionbarsherlock.view;

// TODO: Auto-generated Javadoc
/**
 * When a {@link View} implements this interface it will receive callbacks when
 * expanded or collapsed as an action view alongside the optional, app-specified
 * callbacks to {@link OnActionExpandListener}.
 * 
 * <p>
 * See {@link MenuItem} for more information about action views. See
 * {@link android.app.ActionBar} for more information about the action bar.
 */
public interface CollapsibleActionView {
	/**
	 * Called when this view is expanded as an action view. See
	 * {@link MenuItem#expandActionView()}.
	 */
	public void onActionViewExpanded();

	/**
	 * Called when this view is collapsed as an action view. See
	 * {@link MenuItem#collapseActionView()}.
	 */
	public void onActionViewCollapsed();
}
