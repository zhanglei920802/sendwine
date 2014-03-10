/**   
* @Title: View_OnAttachStateChangeListener.java 
* @Package com.actionbarsherlock.internal.view 
* @Description: 
*			声明:
*			1)掌控校园是本人的大学的创业作品,也是本人的毕业设计
*			2)本程序尚未进行开放源代码,所以禁止组织或者是个人泄露源码,否则将会追究其刑事责任
*			3)编写本软件,我需要感谢彭老师以及其他鼓励和支持我的同学以及朋友
*			4)本程序的最终所有权属于本人	
* @author  张雷 794857063@qq.com
* @date 2013-11-14 19:34:43 
* @version V1.0   
*/
package com.actionbarsherlock.internal.view;

import android.view.View;

// TODO: Auto-generated Javadoc
/**
 * The listener interface for receiving view_OnAttachStateChange events. The
 * class that is interested in processing a view_OnAttachStateChange event
 * implements this interface, and the object created with that class is
 * registered with a component using the component's
 * <code>addView_OnAttachStateChangeListener<code> method. When
 * the view_OnAttachStateChange event occurs, that object's appropriate
 * method is invoked.
 * 
 * @see View_OnAttachStateChangeEvent
 */
public interface View_OnAttachStateChangeListener {
	
	/**
	 * On view attached to window.
	 * 
	 * @param v
	 *            the v
	 */
	void onViewAttachedToWindow(View v);

	/**
	 * On view detached from window.
	 * 
	 * @param v
	 *            the v
	 */
	void onViewDetachedFromWindow(View v);
}
