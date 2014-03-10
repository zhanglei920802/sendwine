/**   
* @Title: FloatEvaluator.java 
* @Package com.actionbarsherlock.internal.nineoldandroids.animation 
* @Description: 
*			声明:
*			1)掌控校园是本人的大学的创业作品,也是本人的毕业设计
*			2)本程序尚未进行开放源代码,所以禁止组织或者是个人泄露源码,否则将会追究其刑事责任
*			3)编写本软件,我需要感谢彭老师以及其他鼓励和支持我的同学以及朋友
*			4)本程序的最终所有权属于本人	
* @author  张雷 794857063@qq.com
* @date 2013-11-14 19:33:45 
* @version V1.0   
*/

package com.actionbarsherlock.internal.nineoldandroids.animation;

// TODO: Auto-generated Javadoc
/**
 * This evaluator can be used to perform type interpolation between
 * <code>float</code> values.
 */
public class FloatEvaluator implements TypeEvaluator<Number> {

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
	 *            The start value; should be of type <code>float</code> or
	 *            <code>Float</code>
	 * @param endValue
	 *            The end value; should be of type <code>float</code> or
	 *            <code>Float</code>
	 * @return A linear interpolation between the start and end values, given
	 *         the <code>fraction</code> parameter.
	 */
	@Override
	public Float evaluate(float fraction, Number startValue, Number endValue) {
		float startFloat = startValue.floatValue();
		return startFloat + fraction * (endValue.floatValue() - startFloat);
	}
}