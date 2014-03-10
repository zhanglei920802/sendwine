/**   
* @Title: ActionProviderWrapper.java 
* @Package com.actionbarsherlock.internal.view 
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
package com.actionbarsherlock.internal.view;

import com.actionbarsherlock.internal.view.menu.SubMenuWrapper;
import com.actionbarsherlock.view.ActionProvider;
import android.view.View;

// TODO: Auto-generated Javadoc
/**
 * The Class ActionProviderWrapper.
 */
public class ActionProviderWrapper extends android.view.ActionProvider {
	
	/** The m provider. */
	private final ActionProvider mProvider;

	/**
	 * Instantiates a new action provider wrapper.
	 * 
	 * @param provider
	 *            the provider
	 */
	public ActionProviderWrapper(ActionProvider provider) {
		super(null/* TODO */); // XXX this *should* be unused
		mProvider = provider;
	}

	/**
	 * Unwrap.
	 * 
	 * @return the action provider
	 */
	public ActionProvider unwrap() {
		return mProvider;
	}

	/** 
	* <p>Title: onCreateActionView</p> 
	* <p>Description: </p> 
	* @return 
	* @see android.view.ActionProvider#onCreateActionView() 
	*/
	@Override
	public View onCreateActionView() {
		return mProvider.onCreateActionView();
	}

	/** 
	* <p>Title: hasSubMenu</p> 
	* <p>Description: </p> 
	* @return 
	* @see android.view.ActionProvider#hasSubMenu() 
	*/
	@Override
	public boolean hasSubMenu() {
		return mProvider.hasSubMenu();
	}

	/** 
	* <p>Title: onPerformDefaultAction</p> 
	* <p>Description: </p> 
	* @return 
	* @see android.view.ActionProvider#onPerformDefaultAction() 
	*/
	@Override
	public boolean onPerformDefaultAction() {
		return mProvider.onPerformDefaultAction();
	}

	/** 
	* <p>Title: onPrepareSubMenu</p> 
	* <p>Description: </p> 
	* @param subMenu 
	* @see android.view.ActionProvider#onPrepareSubMenu(android.view.SubMenu) 
	*/
	@Override
	public void onPrepareSubMenu(android.view.SubMenu subMenu) {
		mProvider.onPrepareSubMenu(new SubMenuWrapper(subMenu));
	}
}
