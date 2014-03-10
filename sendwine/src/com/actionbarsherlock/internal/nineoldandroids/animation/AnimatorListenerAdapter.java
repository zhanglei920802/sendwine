/**   
* @Title: AnimatorListenerAdapter.java 
* @Package com.actionbarsherlock.internal.nineoldandroids.animation 
* @Description: 
*			声明:
*			1)掌控校园是本人的大学的创业作品,也是本人的毕业设计
*			2)本程序尚未进行开放源代码,所以禁止组织或者是个人泄露源码,否则将会追究其刑事责任
*			3)编写本软件,我需要感谢彭老师以及其他鼓励和支持我的同学以及朋友
*			4)本程序的最终所有权属于本人	
* @author  张雷 794857063@qq.com
* @date 2013-11-14 19:44:59 
* @version V1.0   
*/

package com.actionbarsherlock.internal.nineoldandroids.animation;

// TODO: Auto-generated Javadoc
/**
 * This adapter class provides empty implementations of the methods from.
 * 
 * {@link android.animation.Animator.AnimatorListener}. Any custom listener that
 * cares only about a subset of the methods of this listener can simply subclass
 * this adapter class instead of implementing the interface directly.
 */
public abstract class AnimatorListenerAdapter implements
		Animator.AnimatorListener {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onAnimationCancel(Animator animation) {
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onAnimationEnd(Animator animation) {
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onAnimationRepeat(Animator animation) {
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void onAnimationStart(Animator animation) {
	}

}
