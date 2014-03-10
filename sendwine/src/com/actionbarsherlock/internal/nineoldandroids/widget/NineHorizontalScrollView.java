/**   
* @Title: NineHorizontalScrollView.java 
* @Package com.actionbarsherlock.internal.nineoldandroids.widget 
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
package com.actionbarsherlock.internal.nineoldandroids.widget;

import android.content.Context;
import android.widget.HorizontalScrollView;
import com.actionbarsherlock.internal.nineoldandroids.view.animation.AnimatorProxy;

// TODO: Auto-generated Javadoc
/**
 * The Class NineHorizontalScrollView.
 */
public class NineHorizontalScrollView extends HorizontalScrollView {
	
	/** The m proxy. */
	private final AnimatorProxy mProxy;

	/**
	 * Instantiates a new nine horizontal scroll view.
	 * 
	 * @param context
	 *            the context
	 */
	public NineHorizontalScrollView(Context context) {
		super(context);
		mProxy = AnimatorProxy.NEEDS_PROXY ? AnimatorProxy.wrap(this) : null;
	}

	/** 
	* <p>Title: setVisibility</p> 
	* <p>Description: </p> 
	* @param visibility 
	* @see android.view.View#setVisibility(int) 
	*/
	@Override
	public void setVisibility(int visibility) {
		if (mProxy != null) {
			if (visibility == GONE) {
				clearAnimation();
			} else if (visibility == VISIBLE) {
				setAnimation(mProxy);
			}
		}
		super.setVisibility(visibility);
	}

	/** 
	* <p>Title: getAlpha</p> 
	* <p>Description: </p> 
	* @return 
	* @see android.view.View#getAlpha() 
	*/
	@Override
	public float getAlpha() {
		if (AnimatorProxy.NEEDS_PROXY) {
			return mProxy.getAlpha();
		} else {
			return super.getAlpha();
		}
	}

	/** 
	* <p>Title: setAlpha</p> 
	* <p>Description: </p> 
	* @param alpha 
	* @see android.view.View#setAlpha(float) 
	*/
	@Override
	public void setAlpha(float alpha) {
		if (AnimatorProxy.NEEDS_PROXY) {
			mProxy.setAlpha(alpha);
		} else {
			super.setAlpha(alpha);
		}
	}
}
