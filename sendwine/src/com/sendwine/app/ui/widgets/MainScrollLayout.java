package com.sendwine.app.ui.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.Scroller;

// TODO: Auto-generated Javadoc
/**
 * The Class MainScrollLayout.
 */
public class MainScrollLayout extends ViewGroup {

	/**
	 * 屏幕切换监听器.
	 * 
	 * @author liux
	 */
	public interface OnViewChangeListener {

		/**
		 * On view change.
		 * 
		 * @param view
		 *            the view
		 */
		public void OnViewChange(int view);
	}

	/** The Constant TAG. */
	private static final String TAG = "ScrollLayout";

	/** The m scroller. */
	private Scroller mScroller;

	/** The m cur screen. */
	private int mCurScreen;

	/** The m default screen. */
	private int mDefaultScreen = 0;

	/** The Constant TOUCH_STATE_REST. */
	private static final int TOUCH_STATE_REST = 0;

	/** The m touch slop. */
	private int mTouchSlop;

	/** The m on view change listener. */
	private OnViewChangeListener mOnViewChangeListener;

	/**
	 * 设置是否可左右滑动.
	 * 
	 * @param context
	 *            the context
	 * @param attrs
	 *            the attrs
	 * @author liux
	 */
	// private boolean isScroll = true;

	// public void setIsScroll(boolean b) {
	// this.isScroll = b;
	// }

	public MainScrollLayout(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	/**
	 * Instantiates a new main scroll layout.
	 * 
	 * @param context
	 *            the context
	 * @param attrs
	 *            the attrs
	 * @param defStyle
	 *            the def style
	 */
	public MainScrollLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		mScroller = new Scroller(context);
		mCurScreen = mDefaultScreen;
		mTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
	}

	/**
	 * <p>
	 * Title: computeScroll
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @see android.view.View#computeScroll()
	 */
	@Override
	public void computeScroll() {
		if (mScroller.computeScrollOffset()) {
			scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
			postInvalidate();
		}
	}

	/**
	 * Gets the cur screen.
	 * 
	 * @return the cur screen
	 */
	public int getCurScreen() {
		return mCurScreen;
	}

	/**
	 * <p>
	 * Title: onLayout
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @param changed
	 * @param l
	 * @param t
	 * @param r
	 * @param b
	 * @see android.view.ViewGroup#onLayout(boolean, int, int, int, int)
	 */
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		int childLeft = 0;
		final int childCount = getChildCount();
		for (int i = 0; i < childCount; i++) {
			final View childView = getChildAt(i);
			if (childView.getVisibility() != View.GONE) {
				final int childWidth = childView.getMeasuredWidth();
				childView.layout(childLeft, 0, childLeft + childWidth,
						childView.getMeasuredHeight());
				childLeft += childWidth;
			}
		}
	}

	/**
	 * <p>
	 * Title: onMeasure
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @param widthMeasureSpec
	 * @param heightMeasureSpec
	 * @see android.view.View#onMeasure(int, int)
	 */
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// Log.e(TAG, "onMeasure");
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		final int width = MeasureSpec.getSize(widthMeasureSpec);
		final int widthMode = MeasureSpec.getMode(widthMeasureSpec);
		if (widthMode != MeasureSpec.EXACTLY) {
			throw new IllegalStateException(
					"ScrollLayout only canmCurScreen run at EXACTLY mode!");
		}
		final int heightMode = MeasureSpec.getMode(heightMeasureSpec);

		if (heightMode != MeasureSpec.EXACTLY) {
			throw new IllegalStateException(
					"ScrollLayout only can run at EXACTLY mode!");
		}

		// The children are given the same width and height as the scrollLayout
		final int count = getChildCount();
		for (int i = 0; i < count; i++) {
			getChildAt(i).measure(widthMeasureSpec, heightMeasureSpec);
		}
		// Log.e(TAG, "moving to screen "+mCurScreen);
		scrollTo(mCurScreen * width, 0);
	}

	/**
	 * Scroll to screen.
	 * 
	 * @param whichScreen
	 *            the which screen
	 */
	public void scrollToScreen(int whichScreen) {
		// get the valid layout page
		whichScreen = Math.max(0, Math.min(whichScreen, getChildCount() - 1));
		if (getScrollX() != (whichScreen * getWidth())) {
			final int delta = whichScreen * getWidth() - getScrollX();
			mScroller.startScroll(getScrollX(), 0, delta, 0,
					Math.abs(delta) * 1);// 持续滚动时间 以毫秒为单位
			mCurScreen = whichScreen;
			invalidate(); // Redraw the layout

			if (mOnViewChangeListener != null) {
				mOnViewChangeListener.OnViewChange(mCurScreen);
			}
		}
	}

	/**
	 * 设置屏幕切换监听器.
	 * 
	 * @param listener
	 *            the listener
	 */
	public void SetOnViewChangeListener(OnViewChangeListener listener) {
		mOnViewChangeListener = listener;
	}

	/**
	 * Sets the to screen.
	 * 
	 * @param whichScreen
	 *            the new to screen
	 */
	public void setToScreen(int whichScreen) {
		whichScreen = Math.max(0, Math.min(whichScreen, getChildCount() - 1));
		mCurScreen = whichScreen;
		scrollTo(whichScreen * getWidth(), 0);

		if (mOnViewChangeListener != null) {
			mOnViewChangeListener.OnViewChange(mCurScreen);
		}
	}

	/**
	 * According to the position of current layout scroll to the destination
	 * page.
	 */
	public void snapToDestination() {
		final int screenWidth = getWidth();
		final int destScreen = (getScrollX() + screenWidth / 2) / screenWidth;
		snapToScreen(destScreen);
	}

	/**
	 * Snap to screen.
	 * 
	 * @param whichScreen
	 *            the which screen
	 */
	public void snapToScreen(int whichScreen) {
		// // 是否可滑动
		// if (!isScroll) {
		// this.setToScreen(whichScreen);
		// return;
		// }

		scrollToScreen(whichScreen);
	}
}