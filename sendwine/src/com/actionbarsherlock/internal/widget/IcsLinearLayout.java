/**   
* @Title: IcsLinearLayout.java 
* @Package com.actionbarsherlock.internal.widget 
* @Description: 
*			声明:
*			1)掌控校园是本人的大学的创业作品,也是本人的毕业设计
*			2)本程序尚未进行开放源代码,所以禁止组织或者是个人泄露源码,否则将会追究其刑事责任
*			3)编写本软件,我需要感谢彭老师以及其他鼓励和支持我的同学以及朋友
*			4)本程序的最终所有权属于本人	
* @author  张雷 794857063@qq.com
* @date 2013-11-14 19:34:47 
* @version V1.0   
*/
package com.actionbarsherlock.internal.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import com.actionbarsherlock.internal.nineoldandroids.widget.NineLinearLayout;

// TODO: Auto-generated Javadoc
/**
 * A simple extension of a regular linear layout that supports the divider API
 * of Android 4.0+. The dividers are added adjacent to the children by changing
 * their layout params. If you need to rely on the margins which fall in the
 * same orientation as the layout you should wrap the child in a simple
 * {@link android.widget.FrameLayout} so it can receive the margin.
 */
public class IcsLinearLayout extends NineLinearLayout {
	
	/** The Constant R_styleable_LinearLayout. */
	private static final int[] R_styleable_LinearLayout = new int[] {
	/* 0 */android.R.attr.divider,
	/* 1 */android.R.attr.measureWithLargestChild,
	/* 2 */android.R.attr.showDividers,
	/* 3 */android.R.attr.dividerPadding, };
	
	/** The Constant LinearLayout_divider. */
	private static final int LinearLayout_divider = 0;
	
	/** The Constant LinearLayout_measureWithLargestChild. */
	private static final int LinearLayout_measureWithLargestChild = 1;
	
	/** The Constant LinearLayout_showDividers. */
	private static final int LinearLayout_showDividers = 2;
	
	/** The Constant LinearLayout_dividerPadding. */
	private static final int LinearLayout_dividerPadding = 3;

	/**
	 * Don't show any dividers.
	 */
	public static final int SHOW_DIVIDER_NONE = 0;
	/**
	 * Show a divider at the beginning of the group.
	 */
	public static final int SHOW_DIVIDER_BEGINNING = 1;
	/**
	 * Show dividers between each item in the group.
	 */
	public static final int SHOW_DIVIDER_MIDDLE = 2;
	/**
	 * Show a divider at the end of the group.
	 */
	public static final int SHOW_DIVIDER_END = 4;

	/** The m divider. */
	private Drawable mDivider;
	
	/** The m divider width. */
	private int mDividerWidth;
	
	/** The m divider height. */
	private int mDividerHeight;
	
	/** The m show dividers. */
	private int mShowDividers;
	
	/** The m divider padding. */
	private int mDividerPadding;

	/** The m use largest child. */
	private boolean mUseLargestChild;

	/**
	 * Instantiates a new ics linear layout.
	 * 
	 * @param context
	 *            the context
	 * @param attrs
	 *            the attrs
	 */
	public IcsLinearLayout(Context context, AttributeSet attrs) {
		super(context, attrs);

		TypedArray a = context.obtainStyledAttributes(attrs, /*
															 * com.android.internal
															 * .R.styleable.
															 */
				R_styleable_LinearLayout);

		setDividerDrawable(a
				.getDrawable(/* com.android.internal.R.styleable. */LinearLayout_divider));
		mShowDividers = a
				.getInt(/* com.android.internal.R.styleable. */LinearLayout_showDividers,
						SHOW_DIVIDER_NONE);
		mDividerPadding = a.getDimensionPixelSize(/*
												 * com.android.internal.R.styleable
												 * .
												 */LinearLayout_dividerPadding,
				0);
		mUseLargestChild = a
				.getBoolean(
						/* com.android.internal.R.styleable. */LinearLayout_measureWithLargestChild,
						false);

		a.recycle();
	}

	/**
	 * Set how dividers should be shown between items in this layout.
	 * 
	 * @param showDividers
	 *            One or more of {@link #SHOW_DIVIDER_BEGINNING},
	 *            {@link #SHOW_DIVIDER_MIDDLE}, or {@link #SHOW_DIVIDER_END}, or
	 *            {@link #SHOW_DIVIDER_NONE} to show no dividers.
	 */
	@Override
	public void setShowDividers(int showDividers) {
		if (showDividers != mShowDividers) {
			requestLayout();
			invalidate(); // XXX This is required if you are toggling a divider
							// off
		}
		mShowDividers = showDividers;
	}

	/**
	 * Gets the show dividers.
	 * 
	 * @return A flag set indicating how dividers should be shown around items.
	 * @see #setShowDividers(int)
	 */
	@Override
	public int getShowDividers() {
		return mShowDividers;
	}

	/**
	 * Set a drawable to be used as a divider between items.
	 * 
	 * @param divider
	 *            Drawable that will divide each item.
	 * @see #setShowDividers(int)
	 */
	@Override
	public void setDividerDrawable(Drawable divider) {
		if (divider == mDivider) {
			return;
		}
		mDivider = divider;
		if (divider != null) {
			mDividerWidth = divider.getIntrinsicWidth();
			mDividerHeight = divider.getIntrinsicHeight();
		} else {
			mDividerWidth = 0;
			mDividerHeight = 0;
		}
		setWillNotDraw(divider == null);
		requestLayout();
	}

	/**
	 * Set padding displayed on both ends of dividers.
	 * 
	 * @param padding
	 *            Padding value in pixels that will be applied to each end
	 * 
	 * @see #setShowDividers(int)
	 * @see #setDividerDrawable(Drawable)
	 * @see #getDividerPadding()
	 */
	@Override
	public void setDividerPadding(int padding) {
		mDividerPadding = padding;
	}

	/**
	 * Get the padding size used to inset dividers in pixels.
	 * 
	 * @return the ${e.g(1).rsfl()}
	 * @see #setShowDividers(int)
	 * @see #setDividerDrawable(Drawable)
	 * @see #setDividerPadding(int)
	 */
	@Override
	public int getDividerPadding() {
		return mDividerPadding;
	}

	/**
	 * Get the width of the current divider drawable.
	 * 
	 * @return the ${e.g(1).rsfl()}
	 * @hide Used internally by framework.
	 */
	public int getDividerWidth() {
		return mDividerWidth;
	}

	/** 
	* <p>Title: measureChildWithMargins</p> 
	* <p>Description: </p> 
	* @param child
	* @param parentWidthMeasureSpec
	* @param widthUsed
	* @param parentHeightMeasureSpec
	* @param heightUsed 
	* @see android.view.ViewGroup#measureChildWithMargins(android.view.View, int, int, int, int) 
	*/
	@Override
	protected void measureChildWithMargins(View child,
			int parentWidthMeasureSpec, int widthUsed,
			int parentHeightMeasureSpec, int heightUsed) {
		final int index = indexOfChild(child);
		final int orientation = getOrientation();
		final LayoutParams params = (LayoutParams) child.getLayoutParams();
		if (hasDividerBeforeChildAt(index)) {
			if (orientation == VERTICAL) {
				// Account for the divider by pushing everything up
				params.topMargin = mDividerHeight;
			} else {
				// Account for the divider by pushing everything left
				params.leftMargin = mDividerWidth;
			}
		}

		final int count = getChildCount();
		if (index == count - 1) {
			if (hasDividerBeforeChildAt(count)) {
				if (orientation == VERTICAL) {
					params.bottomMargin = mDividerHeight;
				} else {
					params.rightMargin = mDividerWidth;
				}
			}
		}
		super.measureChildWithMargins(child, parentWidthMeasureSpec, widthUsed,
				parentHeightMeasureSpec, heightUsed);
	}

	/** 
	* <p>Title: onDraw</p> 
	* <p>Description: </p> 
	* @param canvas 
	* @see android.widget.LinearLayout#onDraw(android.graphics.Canvas) 
	*/
	@Override
	protected void onDraw(Canvas canvas) {
		if (mDivider != null) {
			if (getOrientation() == VERTICAL) {
				drawDividersVertical(canvas);
			} else {
				drawDividersHorizontal(canvas);
			}
		}
		super.onDraw(canvas);
	}

	/**
	 * Draw dividers vertical.
	 * 
	 * @param canvas
	 *            the canvas
	 */
	void drawDividersVertical(Canvas canvas) {
		final int count = getChildCount();
		for (int i = 0; i < count; i++) {
			final View child = getChildAt(i);

			if (child != null && child.getVisibility() != GONE) {
				if (hasDividerBeforeChildAt(i)) {
					final LayoutParams lp = (LayoutParams) child
							.getLayoutParams();
					final int top = child.getTop() - lp.topMargin/*
																 * -
																 * mDividerHeight
																 */;
					drawHorizontalDivider(canvas, top);
				}
			}
		}

		if (hasDividerBeforeChildAt(count)) {
			final View child = getChildAt(count - 1);
			int bottom = 0;
			if (child == null) {
				bottom = getHeight() - getPaddingBottom() - mDividerHeight;
			} else {
				// final LayoutParams lp = (LayoutParams)
				// child.getLayoutParams();
				bottom = child.getBottom()/* + lp.bottomMargin */;
			}
			drawHorizontalDivider(canvas, bottom);
		}
	}

	/**
	 * Draw dividers horizontal.
	 * 
	 * @param canvas
	 *            the canvas
	 */
	void drawDividersHorizontal(Canvas canvas) {
		final int count = getChildCount();
		for (int i = 0; i < count; i++) {
			final View child = getChildAt(i);

			if (child != null && child.getVisibility() != GONE) {
				if (hasDividerBeforeChildAt(i)) {
					final LayoutParams lp = (LayoutParams) child
							.getLayoutParams();
					final int left = child.getLeft() - lp.leftMargin/*
																	 * -
																	 * mDividerWidth
																	 */;
					drawVerticalDivider(canvas, left);
				}
			}
		}

		if (hasDividerBeforeChildAt(count)) {
			final View child = getChildAt(count - 1);
			int right = 0;
			if (child == null) {
				right = getWidth() - getPaddingRight() - mDividerWidth;
			} else {
				// final LayoutParams lp = (LayoutParams)
				// child.getLayoutParams();
				right = child.getRight()/* + lp.rightMargin */;
			}
			drawVerticalDivider(canvas, right);
		}
	}

	/**
	 * Draw horizontal divider.
	 * 
	 * @param canvas
	 *            the canvas
	 * @param top
	 *            the top
	 */
	void drawHorizontalDivider(Canvas canvas, int top) {
		mDivider.setBounds(getPaddingLeft() + mDividerPadding, top, getWidth()
				- getPaddingRight() - mDividerPadding, top + mDividerHeight);
		mDivider.draw(canvas);
	}

	/**
	 * Draw vertical divider.
	 * 
	 * @param canvas
	 *            the canvas
	 * @param left
	 *            the left
	 */
	void drawVerticalDivider(Canvas canvas, int left) {
		mDivider.setBounds(left, getPaddingTop() + mDividerPadding, left
				+ mDividerWidth, getHeight() - getPaddingBottom()
				- mDividerPadding);
		mDivider.draw(canvas);
	}

	/**
	 * Determines where to position dividers between children.
	 * 
	 * @param childIndex
	 *            Index of child to check for preceding divider
	 * @return true if there should be a divider before the child at childIndex
	 * @hide Pending API consideration. Currently only used internally by the
	 *       system.
	 */
	protected boolean hasDividerBeforeChildAt(int childIndex) {
		if (childIndex == 0) {
			return (mShowDividers & SHOW_DIVIDER_BEGINNING) != 0;
		} else if (childIndex == getChildCount()) {
			return (mShowDividers & SHOW_DIVIDER_END) != 0;
		} else if ((mShowDividers & SHOW_DIVIDER_MIDDLE) != 0) {
			boolean hasVisibleViewBefore = false;
			for (int i = childIndex - 1; i >= 0; i--) {
				if (getChildAt(i).getVisibility() != GONE) {
					hasVisibleViewBefore = true;
					break;
				}
			}
			return hasVisibleViewBefore;
		}
		return false;
	}

	/**
	 * When true, all children with a weight will be considered having the
	 * minimum size of the largest child. If false, all children are measured
	 * normally.
	 * 
	 * @return True to measure children with a weight using the minimum size of
	 *         the largest child, false otherwise.
	 * 
	 * @attr ref android.R.styleable#LinearLayout_measureWithLargestChild
	 */
	@Override
	public boolean isMeasureWithLargestChildEnabled() {
		return mUseLargestChild;
	}

	/**
	 * When set to true, all children with a weight will be considered having
	 * the minimum size of the largest child. If false, all children are
	 * measured normally.
	 * 
	 * Disabled by default.
	 * 
	 * @param enabled
	 *            True to measure children with a weight using the minimum size
	 *            of the largest child, false otherwise.
	 * 
	 * @attr ref android.R.styleable#LinearLayout_measureWithLargestChild
	 */
	@Override
	public void setMeasureWithLargestChildEnabled(boolean enabled) {
		mUseLargestChild = enabled;
	}

	/** 
	* <p>Title: onMeasure</p> 
	* <p>Description: </p> 
	* @param widthMeasureSpec
	* @param heightMeasureSpec 
	* @see android.widget.LinearLayout#onMeasure(int, int) 
	*/
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);

		if (mUseLargestChild) {
			final int orientation = getOrientation();
			switch (orientation) {
			case HORIZONTAL:
				useLargestChildHorizontal();
				break;

			case VERTICAL:
				useLargestChildVertical();
				break;
			}
		}
	}

	/**
	 * Use largest child horizontal.
	 */
	private void useLargestChildHorizontal() {
		final int childCount = getChildCount();

		// Find largest child width
		int largestChildWidth = 0;
		for (int i = 0; i < childCount; i++) {
			final View child = getChildAt(i);
			largestChildWidth = Math.max(child.getMeasuredWidth(),
					largestChildWidth);
		}

		int totalWidth = 0;
		// Re-measure childs
		for (int i = 0; i < childCount; i++) {
			final View child = getChildAt(i);

			if (child == null || child.getVisibility() == View.GONE) {
				continue;
			}

			final LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) child
					.getLayoutParams();

			float childExtra = lp.weight;
			if (childExtra > 0) {
				child.measure(MeasureSpec.makeMeasureSpec(largestChildWidth,
						MeasureSpec.EXACTLY), MeasureSpec.makeMeasureSpec(
						child.getMeasuredHeight(), MeasureSpec.EXACTLY));
				totalWidth += largestChildWidth;

			} else {
				totalWidth += child.getMeasuredWidth();
			}

			totalWidth += lp.leftMargin + lp.rightMargin;
		}

		totalWidth += getPaddingLeft() + getPaddingRight();
		setMeasuredDimension(totalWidth, getMeasuredHeight());
	}

	/**
	 * Use largest child vertical.
	 */
	private void useLargestChildVertical() {
		final int childCount = getChildCount();

		// Find largest child width
		int largestChildHeight = 0;
		for (int i = 0; i < childCount; i++) {
			final View child = getChildAt(i);
			largestChildHeight = Math.max(child.getMeasuredHeight(),
					largestChildHeight);
		}

		int totalHeight = 0;
		// Re-measure childs
		for (int i = 0; i < childCount; i++) {
			final View child = getChildAt(i);

			if (child == null || child.getVisibility() == View.GONE) {
				continue;
			}

			final LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) child
					.getLayoutParams();

			float childExtra = lp.weight;
			if (childExtra > 0) {
				child.measure(MeasureSpec.makeMeasureSpec(
						child.getMeasuredWidth(), MeasureSpec.EXACTLY),
						MeasureSpec.makeMeasureSpec(largestChildHeight,
								MeasureSpec.EXACTLY));
				totalHeight += largestChildHeight;

			} else {
				totalHeight += child.getMeasuredHeight();
			}

			totalHeight += lp.leftMargin + lp.rightMargin;
		}

		totalHeight += getPaddingLeft() + getPaddingRight();
		setMeasuredDimension(getMeasuredWidth(), totalHeight);
	}
}
