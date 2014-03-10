/**   
* @Title: CapitalizingButton.java 
* @Package com.actionbarsherlock.internal.widget 
* @Description: 
*			声明:
*			1)掌控校园是本人的大学的创业作品,也是本人的毕业设计
*			2)本程序尚未进行开放源代码,所以禁止组织或者是个人泄露源码,否则将会追究其刑事责任
*			3)编写本软件,我需要感谢彭老师以及其他鼓励和支持我的同学以及朋友
*			4)本程序的最终所有权属于本人	
* @author  张雷 794857063@qq.com
* @date 2013-11-14 19:34:24 
* @version V1.0   
*/
package com.actionbarsherlock.internal.widget;

import java.util.Locale;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.Button;

// TODO: Auto-generated Javadoc
/**
 * The Class CapitalizingButton.
 */
public class CapitalizingButton extends Button {
	
	/** The Constant SANS_ICE_CREAM. */
	private static final boolean SANS_ICE_CREAM = Build.VERSION.SDK_INT < Build.VERSION_CODES.ICE_CREAM_SANDWICH;
	
	/** The Constant IS_GINGERBREAD. */
	private static final boolean IS_GINGERBREAD = Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD;

	/** The Constant R_styleable_Button. */
	private static final int[] R_styleable_Button = new int[] { android.R.attr.textAllCaps };
	
	/** The Constant R_styleable_Button_textAllCaps. */
	private static final int R_styleable_Button_textAllCaps = 0;

	/** The m all caps. */
	private boolean mAllCaps;

	/**
	 * Instantiates a new capitalizing button.
	 * 
	 * @param context
	 *            the context
	 * @param attrs
	 *            the attrs
	 */
	public CapitalizingButton(Context context, AttributeSet attrs) {
		super(context, attrs);

		TypedArray a = context
				.obtainStyledAttributes(attrs, R_styleable_Button);
		mAllCaps = a.getBoolean(R_styleable_Button_textAllCaps, true);
		a.recycle();
	}

	/**
	 * Sets the text compat.
	 * 
	 * @param text
	 *            the new text compat
	 */
	public void setTextCompat(CharSequence text) {
		if (SANS_ICE_CREAM && mAllCaps && text != null) {
			if (IS_GINGERBREAD) {
				setText(text.toString().toUpperCase(Locale.ROOT));
			} else {
				setText(text.toString().toUpperCase());
			}
		} else {
			setText(text);
		}
	}
}
