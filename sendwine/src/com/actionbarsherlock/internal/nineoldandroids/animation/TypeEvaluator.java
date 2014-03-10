/**   
* @Title: TypeEvaluator.java 
* @Package com.actionbarsherlock.internal.nineoldandroids.animation 
* @Description: 
*			声明:
*			1)掌控校园是本人的大学的创业作品,也是本人的毕业设计
*			2)本程序尚未进行开放源代码,所以禁止组织或者是个人泄露源码,否则将会追究其刑事责任
*			3)编写本软件,我需要感谢彭老师以及其他鼓励和支持我的同学以及朋友
*			4)本程序的最终所有权属于本人	
* @author  张雷 794857063@qq.com
* @date 2013-11-14 19:33:40 
* @version V1.0   
*/

package com.actionbarsherlock.internal.nineoldandroids.animation;

// TODO: Auto-generated Javadoc
/**
 * Interface for use with the {@link ValueAnimator#setEvaluator(TypeEvaluator)}
 * function. Evaluators allow developers to create animations on arbitrary
 * property types, by allowing them to supply custom evaulators for types that
 * are not automatically understood and used by the animation system.
 * 
 * @param <T>
 *            the generic type
 * @see ValueAnimator#setEvaluator(TypeEvaluator)
 */
public interface TypeEvaluator<T> {

	/**
	 * This function returns the result of linearly interpolating the start and
	 * end values, with <code>fraction</code> representing the proportion
	 * between the start and end values. The calculation is a simple parametric
	 * calculation: <code>result = x0 + t * (v1 - v0)</code>, where
	 * <code>x0</code> is <code>startValue</code>, <code>x1</code> is
	 * <code>endValue</code>, and <code>t</code> is <code>fraction</code>.
	 * 
	 * @param fraction
	 *            The fraction from the starting to the ending values
	 * @param startValue
	 *            The start value.
	 * @param endValue
	 *            The end value.
	 * @return A linear interpolation between the start and end values, given
	 *         the <code>fraction</code> parameter.
	 */
	public T evaluate(float fraction, T startValue, T endValue);

}