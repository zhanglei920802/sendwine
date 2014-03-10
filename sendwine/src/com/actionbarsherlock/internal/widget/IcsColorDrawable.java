/**   
* @Title: IcsColorDrawable.java 
* @Package com.actionbarsherlock.internal.widget 
* @Description: 
*			声明:
*			1)掌控校园是本人的大学的创业作品,也是本人的毕业设计
*			2)本程序尚未进行开放源代码,所以禁止组织或者是个人泄露源码,否则将会追究其刑事责任
*			3)编写本软件,我需要感谢彭老师以及其他鼓励和支持我的同学以及朋友
*			4)本程序的最终所有权属于本人	
* @author  张雷 794857063@qq.com
* @date 2013-11-14 19:33:57 
* @version V1.0   
*/
package com.actionbarsherlock.internal.widget;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;

// TODO: Auto-generated Javadoc
/**
 * A version of {@link android.graphics.drawable.ColorDrawable} that respects
 * bounds.
 */
public class IcsColorDrawable extends Drawable {
	
	/** The color. */
	private int color;
	
	/** The paint. */
	private final Paint paint = new Paint();

	/**
	 * Instantiates a new ics color drawable.
	 * 
	 * @param color
	 *            the color
	 */
	public IcsColorDrawable(int color) {
		this.color = color;
	}

	/** 
	* <p>Title: draw</p> 
	* <p>Description: </p> 
	* @param canvas 
	* @see android.graphics.drawable.Drawable#draw(android.graphics.Canvas) 
	*/
	@Override
	public void draw(Canvas canvas) {
		if ((color >>> 24) != 0) {
			paint.setColor(color);
			canvas.drawRect(getBounds(), paint);
		}
	}

	/** 
	* <p>Title: setAlpha</p> 
	* <p>Description: </p> 
	* @param alpha 
	* @see android.graphics.drawable.Drawable#setAlpha(int) 
	*/
	@Override
	public void setAlpha(int alpha) {
		if (alpha != (color >>> 24)) {
			color = (color & 0x00FFFFFF) & (alpha << 24);
			invalidateSelf();
		}
	}

	/** 
	* <p>Title: setColorFilter</p> 
	* <p>Description: </p> 
	* @param colorFilter 
	* @see android.graphics.drawable.Drawable#setColorFilter(android.graphics.ColorFilter) 
	*/
	@Override
	public void setColorFilter(ColorFilter colorFilter) {
		// Ignored
	}

	/** 
	* <p>Title: getOpacity</p> 
	* <p>Description: </p> 
	* @return 
	* @see android.graphics.drawable.Drawable#getOpacity() 
	*/
	@Override
	public int getOpacity() {
		return color >>> 24;
	}
}
