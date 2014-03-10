/**   
* @Title: CapitalizingTextView.java 
* @Package com.actionbarsherlock.internal.widget 
* @Description: 
*			声明:
*			1)掌控校园是本人的大学的创业作品,也是本人的毕业设计
*			2)本程序尚未进行开放源代码,所以禁止组织或者是个人泄露源码,否则将会追究其刑事责任
*			3)编写本软件,我需要感谢彭老师以及其他鼓励和支持我的同学以及朋友
*			4)本程序的最终所有权属于本人	
* @author  张雷 794857063@qq.com
* @date 2013-11-14 19:45:02 
* @version V1.0   
*/
package com.actionbarsherlock.internal.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.TextView;

import java.util.Locale;

// TODO: Auto-generated Javadoc
/**
 * The Class CapitalizingTextView.
 */
public class CapitalizingTextView extends TextView {
	
	/** The Constant SANS_ICE_CREAM. */
	private static final boolean SANS_ICE_CREAM = Build.VERSION.SDK_INT < Build.VERSION_CODES.ICE_CREAM_SANDWICH;
	
	/** The Constant IS_GINGERBREAD. */
	private static final boolean IS_GINGERBREAD = Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD;

	/** The Constant R_styleable_TextView. */
	private static final int[] R_styleable_TextView = new int[] { android.R.attr.textAllCaps };
	
	/** The Constant R_styleable_TextView_textAllCaps. */
	private static final int R_styleable_TextView_textAllCaps = 0;

	/** The m all caps. */
	private boolean mAllCaps;

	/**
	 * Instantiates a new capitalizing text view.
	 * 
	 * @param context
	 *            the context
	 * @param attrs
	 *            the attrs
	 */
	public CapitalizingTextView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	/**
	 * Instantiates a new capitalizing text view.
	 * 
	 * @param context
	 *            the context
	 * @param attrs
	 *            the attrs
	 * @param defStyle
	 *            the def style
	 */
	public CapitalizingTextView(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);

		TypedArray a = context.obtainStyledAttributes(attrs,
				R_styleable_TextView, defStyle, 0);
		mAllCaps = a.getBoolean(R_styleable_TextView_textAllCaps, true);
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
				try {
					setText(text.toString().toUpperCase(Locale.ROOT));
				} catch (NoSuchFieldError e) {
					// Some manufacturer broke Locale.ROOT. See #572.
					setText(text.toString().toUpperCase());
				}
			} else {
				setText(text.toString().toUpperCase());
			}
		} else {
			setText(text);
		}
	}
}
