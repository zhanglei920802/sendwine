/**   
* @Title: CollapsibleActionViewWrapper.java 
* @Package com.actionbarsherlock.internal.widget 
* @Description: 
*			声明:
*			1)掌控校园是本人的大学的创业作品,也是本人的毕业设计
*			2)本程序尚未进行开放源代码,所以禁止组织或者是个人泄露源码,否则将会追究其刑事责任
*			3)编写本软件,我需要感谢彭老师以及其他鼓励和支持我的同学以及朋友
*			4)本程序的最终所有权属于本人	
* @author  张雷 794857063@qq.com
* @date 2013-11-14 19:34:07 
* @version V1.0   
*/
package com.actionbarsherlock.internal.widget;

import android.view.View;
import android.widget.FrameLayout;
import com.actionbarsherlock.view.CollapsibleActionView;

// TODO: Auto-generated Javadoc
/**
 * Wraps an ABS collapsible action view in a native container that delegates the
 * calls.
 */
public class CollapsibleActionViewWrapper extends FrameLayout implements
		android.view.CollapsibleActionView {
	
	/** The child. */
	private final CollapsibleActionView child;

	/**
	 * Instantiates a new collapsible action view wrapper.
	 * 
	 * @param child
	 *            the child
	 */
	public CollapsibleActionViewWrapper(View child) {
		super(child.getContext());
		this.child = (CollapsibleActionView) child;
		addView(child);
	}

	/** 
	* <p>Title: onActionViewExpanded</p> 
	* <p>Description: </p>  
	* @see android.view.CollapsibleActionView#onActionViewExpanded() 
	*/
	@Override
	public void onActionViewExpanded() {
		child.onActionViewExpanded();
	}

	/** 
	* <p>Title: onActionViewCollapsed</p> 
	* <p>Description: </p>  
	* @see android.view.CollapsibleActionView#onActionViewCollapsed() 
	*/
	@Override
	public void onActionViewCollapsed() {
		child.onActionViewCollapsed();
	}

	/**
	 * Unwrap.
	 * 
	 * @return the view
	 */
	public View unwrap() {
		return getChildAt(0);
	}
}
