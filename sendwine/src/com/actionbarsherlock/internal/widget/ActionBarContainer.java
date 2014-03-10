/**   
* @Title: ActionBarContainer.java 
* @Package com.actionbarsherlock.internal.widget 
* @Description: 
*			澹版槑:
*			1)鎺屾帶鏍″洯鏄湰浜虹殑澶у鐨勫垱涓氫綔鍝�涔熸槸鏈汉鐨勬瘯涓氳璁�*			2)鏈▼搴忓皻鏈繘琛屽紑鏀炬簮浠ｇ爜,鎵�互绂佹缁勭粐鎴栬�鏄釜浜烘硠闇叉簮鐮�鍚﹀垯灏嗕細杩界┒鍏跺垜浜嬭矗浠�*			3)缂栧啓鏈蒋浠�鎴戦渶瑕佹劅璋㈠江鑰佸笀浠ュ強鍏朵粬榧撳姳鍜屾敮鎸佹垜鐨勫悓瀛︿互鍙婃湅鍙�*			4)鏈▼搴忕殑鏈�粓鎵�湁鏉冨睘浜庢湰浜�
* @author  寮犻浄 794857063@qq.com
* @date 2013-11-14 19:34:18 
* @version V1.0   
*/

package com.actionbarsherlock.internal.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;


import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.internal.nineoldandroids.widget.NineFrameLayout;
import com.sendwine.app.R;

// TODO: Auto-generated Javadoc
/**
 * This class acts as a container for the action bar view and action mode
 * context views. It applies special styles as needed to help handle animated
 * transitions between them.
 * 
 * @hide
 */
public class ActionBarContainer extends NineFrameLayout {
	
	/** The m is transitioning. */
	private boolean mIsTransitioning;
	
	/** The m tab container. */
	private View mTabContainer;
	
	/** The m action bar view. */
	private ActionBarView mActionBarView;

	/** The m background. */
	private Drawable mBackground;
	
	/** The m stacked background. */
	private Drawable mStackedBackground;
	
	/** The m split background. */
	private Drawable mSplitBackground;
	
	/** The m is split. */
	private boolean mIsSplit;
	
	/** The m is stacked. */
	private boolean mIsStacked;

	/**
	 * Instantiates a new action bar container.
	 * 
	 * @param context
	 *            the context
	 */
	public ActionBarContainer(Context context) {
		this(context, null);
	}

	/**
	 * Instantiates a new action bar container.
	 * 
	 * @param context
	 *            the context
	 * @param attrs
	 *            the attrs
	 */
	public ActionBarContainer(Context context, AttributeSet attrs) {
		super(context, attrs);

		setBackgroundDrawable(null);

		TypedArray a = context.obtainStyledAttributes(attrs,
				R.styleable.SherlockActionBar);
		mBackground = a.getDrawable(R.styleable.SherlockActionBar_background);
		mStackedBackground = a
				.getDrawable(R.styleable.SherlockActionBar_backgroundStacked);

		// Fix for issue #379
		if (mStackedBackground instanceof ColorDrawable
				&& Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
			Bitmap bitmap = Bitmap.createBitmap(1, 1, Bitmap.Config.ARGB_8888);
			Canvas c = new Canvas(bitmap);
			mStackedBackground.draw(c);
			int color = bitmap.getPixel(0, 0);
			bitmap.recycle();
			mStackedBackground = new IcsColorDrawable(color);
		}

		if (getId() == R.id.abs__split_action_bar) {
			mIsSplit = true;
			mSplitBackground = a
					.getDrawable(R.styleable.SherlockActionBar_backgroundSplit);
		}
		a.recycle();

		setWillNotDraw(mIsSplit ? mSplitBackground == null
				: mBackground == null && mStackedBackground == null);
	}

	/** 
	* <p>Title: onFinishInflate</p> 
	* <p>Description: </p>  
	* @see android.view.View#onFinishInflate() 
	*/
	@Override
	public void onFinishInflate() {
		super.onFinishInflate();
		mActionBarView = (ActionBarView) findViewById(R.id.abs__action_bar);
	}

	/**
	 * Sets the primary background.
	 * 
	 * @param bg
	 *            the new primary background
	 */
	public void setPrimaryBackground(Drawable bg) {
		mBackground = bg;
		invalidate();
	}

	/**
	 * Sets the stacked background.
	 * 
	 * @param bg
	 *            the new stacked background
	 */
	public void setStackedBackground(Drawable bg) {
		mStackedBackground = bg;
		invalidate();
	}

	/**
	 * Sets the split background.
	 * 
	 * @param bg
	 *            the new split background
	 */
	public void setSplitBackground(Drawable bg) {
		mSplitBackground = bg;
		invalidate();
	}

	/**
	 * Set the action bar into a "transitioning" state. While transitioning the
	 * bar will block focus and touch from all of its descendants. This prevents
	 * the user from interacting with the bar while it is animating in or out.
	 * 
	 * @param isTransitioning
	 *            true if the bar is currently transitioning, false otherwise.
	 */
	public void setTransitioning(boolean isTransitioning) {
		mIsTransitioning = isTransitioning;
		setDescendantFocusability(isTransitioning ? FOCUS_BLOCK_DESCENDANTS
				: FOCUS_AFTER_DESCENDANTS);
	}

	/** 
	* <p>Title: onInterceptTouchEvent</p> 
	* <p>Description: </p> 
	* @param ev
	* @return 
	* @see android.view.ViewGroup#onInterceptTouchEvent(android.view.MotionEvent) 
	*/
	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		return mIsTransitioning || super.onInterceptTouchEvent(ev);
	}

	/** 
	* <p>Title: onTouchEvent</p> 
	* <p>Description: </p> 
	* @param ev
	* @return 
	* @see android.view.View#onTouchEvent(android.view.MotionEvent) 
	*/
	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		super.onTouchEvent(ev);

		// An action bar always eats touch events.
		return true;
	}

	/** 
	* <p>Title: onHoverEvent</p> 
	* <p>Description: </p> 
	* @param ev
	* @return 
	* @see android.view.View#onHoverEvent(android.view.MotionEvent) 
	*/
	@Override
	public boolean onHoverEvent(MotionEvent ev) {
		super.onHoverEvent(ev);

		// An action bar always eats hover events.
		return true;
	}

	/**
	 * Sets the tab container.
	 * 
	 * @param tabView
	 *            the new tab container
	 */
	public void setTabContainer(ScrollingTabContainerView tabView) {
		if (mTabContainer != null) {
			removeView(mTabContainer);
		}
		mTabContainer = tabView;
		if (tabView != null) {
			addView(tabView);
			final ViewGroup.LayoutParams lp = tabView.getLayoutParams();
			lp.width = android.view.ViewGroup.LayoutParams.MATCH_PARENT;
			lp.height = android.view.ViewGroup.LayoutParams.WRAP_CONTENT;
			tabView.setAllowCollapse(false);
		}
	}

	/**
	 * Gets the tab container.
	 * 
	 * @return the tab container
	 */
	public View getTabContainer() {
		return mTabContainer;
	}

	/** 
	* <p>Title: onDraw</p> 
	* <p>Description: </p> 
	* @param canvas 
	* @see android.view.View#onDraw(android.graphics.Canvas) 
	*/
	@Override
	public void onDraw(Canvas canvas) {
		if (getWidth() == 0 || getHeight() == 0) {
			return;
		}

		if (mIsSplit) {
			if (mSplitBackground != null)
				mSplitBackground.draw(canvas);
		} else {
			if (mBackground != null) {
				mBackground.draw(canvas);
			}
			if (mStackedBackground != null && mIsStacked) {
				mStackedBackground.draw(canvas);
			}
		}
	}

	// This causes the animation reflection to fail on pre-HC platforms
	// @Override
	// public android.view.ActionMode startActionModeForChild(View child,
	// android.view.ActionMode.Callback callback) {
	// // No starting an action mode for an action bar child! (Where would it
	// go?)
	// return null;
	// }

	/** 
	* <p>Title: onMeasure</p> 
	* <p>Description: </p> 
	* @param widthMeasureSpec
	* @param heightMeasureSpec 
	* @see android.widget.FrameLayout#onMeasure(int, int) 
	*/
	@Override
	public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);

		if (mActionBarView == null)
			return;

		final LayoutParams lp = (LayoutParams) mActionBarView.getLayoutParams();
		final int actionBarViewHeight = mActionBarView.isCollapsed() ? 0
				: mActionBarView.getMeasuredHeight() + lp.topMargin
						+ lp.bottomMargin;

		if (mTabContainer != null && mTabContainer.getVisibility() != GONE) {
			final int mode = MeasureSpec.getMode(heightMeasureSpec);
			if (mode == MeasureSpec.AT_MOST) {
				final int maxHeight = MeasureSpec.getSize(heightMeasureSpec);
				setMeasuredDimension(getMeasuredWidth(),
						Math.min(
								actionBarViewHeight
										+ mTabContainer.getMeasuredHeight(),
								maxHeight));
			}
		}
	}

	/** 
	* <p>Title: onLayout</p> 
	* <p>Description: </p> 
	* @param changed
	* @param l
	* @param t
	* @param r
	* @param b 
	* @see android.widget.FrameLayout#onLayout(boolean, int, int, int, int) 
	*/
	@Override
	public void onLayout(boolean changed, int l, int t, int r, int b) {
		super.onLayout(changed, l, t, r, b);

		final boolean hasTabs = mTabContainer != null
				&& mTabContainer.getVisibility() != GONE;

		if (mTabContainer != null && mTabContainer.getVisibility() != GONE) {
			final int containerHeight = getMeasuredHeight();
			final int tabHeight = mTabContainer.getMeasuredHeight();

			if ((mActionBarView.getDisplayOptions() & ActionBar.DISPLAY_SHOW_HOME) == 0) {
				// Not showing home, put tabs on top.
				final int count = getChildCount();
				for (int i = 0; i < count; i++) {
					final View child = getChildAt(i);

					if (child == mTabContainer)
						continue;

					if (!mActionBarView.isCollapsed()) {
						child.offsetTopAndBottom(tabHeight);
					}
				}
				mTabContainer.layout(l, 0, r, tabHeight);
			} else {
				mTabContainer.layout(l, containerHeight - tabHeight, r,
						containerHeight);
			}
		}

		boolean needsInvalidate = false;
		if (mIsSplit) {
			if (mSplitBackground != null) {
				mSplitBackground.setBounds(0, 0, getMeasuredWidth(),
						getMeasuredHeight());
				needsInvalidate = true;
			}
		} else {
			if (mBackground != null) {
				mBackground.setBounds(mActionBarView.getLeft(),
						mActionBarView.getTop(), mActionBarView.getRight(),
						mActionBarView.getBottom());
				needsInvalidate = true;
			}
			if ((mIsStacked = hasTabs && mStackedBackground != null)) {
				mStackedBackground.setBounds(mTabContainer.getLeft(),
						mTabContainer.getTop(), mTabContainer.getRight(),
						mTabContainer.getBottom());
				needsInvalidate = true;
			}
		}

		if (needsInvalidate) {
			invalidate();
		}
	}
}
