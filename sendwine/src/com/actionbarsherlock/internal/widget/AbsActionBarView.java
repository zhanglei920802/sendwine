/**   
* @Title: AbsActionBarView.java 
* @Package com.actionbarsherlock.internal.widget 
* @Description: 
*			澹版槑:
*			1)鎺屾帶鏍″洯鏄湰浜虹殑澶у鐨勫垱涓氫綔鍝�涔熸槸鏈汉鐨勬瘯涓氳璁�*			2)鏈▼搴忓皻鏈繘琛屽紑鏀炬簮浠ｇ爜,鎵�互绂佹缁勭粐鎴栬�鏄釜浜烘硠闇叉簮鐮�鍚﹀垯灏嗕細杩界┒鍏跺垜浜嬭矗浠�*			3)缂栧啓鏈蒋浠�鎴戦渶瑕佹劅璋㈠江鑰佸笀浠ュ強鍏朵粬榧撳姳鍜屾敮鎸佹垜鐨勫悓瀛︿互鍙婃湅鍙�*			4)鏈▼搴忕殑鏈�粓鎵�湁鏉冨睘浜庢湰浜�
* @author  寮犻浄 794857063@qq.com
* @date 2013-11-14 19:45:00 
* @version V1.0   
*/
package com.actionbarsherlock.internal.widget;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;


import com.actionbarsherlock.internal.nineoldandroids.animation.Animator;
import com.actionbarsherlock.internal.nineoldandroids.animation.AnimatorSet;
import com.actionbarsherlock.internal.nineoldandroids.animation.ObjectAnimator;
import com.actionbarsherlock.internal.nineoldandroids.view.NineViewGroup;
import com.actionbarsherlock.internal.view.menu.ActionMenuPresenter;
import com.actionbarsherlock.internal.view.menu.ActionMenuView;
import com.sendwine.app.R;

import static com.actionbarsherlock.internal.ResourcesCompat.getResources_getBoolean;

// TODO: Auto-generated Javadoc
/**
 * The Class AbsActionBarView.
 */
public abstract class AbsActionBarView extends NineViewGroup {
	
	/** The m menu view. */
	protected ActionMenuView mMenuView;
	
	/** The m action menu presenter. */
	protected ActionMenuPresenter mActionMenuPresenter;
	
	/** The m split view. */
	protected ActionBarContainer mSplitView;
	
	/** The m split action bar. */
	protected boolean mSplitActionBar;
	
	/** The m split when narrow. */
	protected boolean mSplitWhenNarrow;
	
	/** The m content height. */
	protected int mContentHeight;

	/** The m context. */
	final Context mContext;

	/** The m visibility anim. */
	protected Animator mVisibilityAnim;
	
	/** The m vis anim listener. */
	protected final VisibilityAnimListener mVisAnimListener = new VisibilityAnimListener();

	/** The Constant sAlphaInterpolator. */
	private static final/* Time */Interpolator sAlphaInterpolator = new DecelerateInterpolator();

	/** The Constant FADE_DURATION. */
	private static final int FADE_DURATION = 200;

	/**
	 * Instantiates a new abs action bar view.
	 * 
	 * @param context
	 *            the context
	 */
	public AbsActionBarView(Context context) {
		super(context);
		mContext = context;
	}

	/**
	 * Instantiates a new abs action bar view.
	 * 
	 * @param context
	 *            the context
	 * @param attrs
	 *            the attrs
	 */
	public AbsActionBarView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
	}

	/**
	 * Instantiates a new abs action bar view.
	 * 
	 * @param context
	 *            the context
	 * @param attrs
	 *            the attrs
	 * @param defStyle
	 *            the def style
	 */
	public AbsActionBarView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		mContext = context;
	}

	/*
	 * Must be public so we can dispatch pre-2.2 via ActionBarImpl.
	 */
	/** 
	* <p>Title: onConfigurationChanged</p> 
	* <p>Description: </p> 
	* @param newConfig 
	* @see android.view.View#onConfigurationChanged(android.content.res.Configuration) 
	*/
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO) {
			super.onConfigurationChanged(newConfig);
		} else if (mMenuView != null) {
			mMenuView.onConfigurationChanged(newConfig);
		}

		// Action bar can change size on configuration changes.
		// Reread the desired height from the theme-specified style.
		TypedArray a = getContext().obtainStyledAttributes(null,
				R.styleable.SherlockActionBar, R.attr.actionBarStyle, 0);
		setContentHeight(a.getLayoutDimension(
				R.styleable.SherlockActionBar_height, 0));
		a.recycle();
		if (mSplitWhenNarrow) {
			setSplitActionBar(getResources_getBoolean(getContext(),
					R.bool.abs__split_action_bar_is_narrow));
		}
		if (mActionMenuPresenter != null) {
			mActionMenuPresenter.onConfigurationChanged(newConfig);
		}
	}

	/**
	 * Sets whether the bar should be split right now, no questions asked.
	 * 
	 * @param split
	 *            true if the bar should split
	 */
	public void setSplitActionBar(boolean split) {
		mSplitActionBar = split;
	}

	/**
	 * Sets whether the bar should split if we enter a narrow screen
	 * configuration.
	 * 
	 * @param splitWhenNarrow
	 *            true if the bar should check to split after a config change
	 */
	public void setSplitWhenNarrow(boolean splitWhenNarrow) {
		mSplitWhenNarrow = splitWhenNarrow;
	}

	/**
	 * Sets the content height.
	 * 
	 * @param height
	 *            the new content height
	 */
	public void setContentHeight(int height) {
		mContentHeight = height;
		requestLayout();
	}

	/**
	 * Gets the content height.
	 * 
	 * @return the content height
	 */
	public int getContentHeight() {
		return mContentHeight;
	}

	/**
	 * Sets the split view.
	 * 
	 * @param splitView
	 *            the new split view
	 */
	public void setSplitView(ActionBarContainer splitView) {
		mSplitView = splitView;
	}

	/**
	 * Gets the animated visibility.
	 * 
	 * @return Current visibility or if animating, the visibility being animated
	 *         to.
	 */
	public int getAnimatedVisibility() {
		if (mVisibilityAnim != null) {
			return mVisAnimListener.mFinalVisibility;
		}
		return getVisibility();
	}

	/**
	 * Animate to visibility.
	 * 
	 * @param visibility
	 *            the visibility
	 */
	public void animateToVisibility(int visibility) {
		if (mVisibilityAnim != null) {
			mVisibilityAnim.cancel();
		}
		if (visibility == VISIBLE) {
			if (getVisibility() != VISIBLE) {
				setAlpha(0);
				if (mSplitView != null && mMenuView != null) {
					mMenuView.setAlpha(0);
				}
			}
			ObjectAnimator anim = ObjectAnimator.ofFloat(this, "alpha", 1);
			anim.setDuration(FADE_DURATION);
			anim.setInterpolator(sAlphaInterpolator);
			if (mSplitView != null && mMenuView != null) {
				AnimatorSet set = new AnimatorSet();
				ObjectAnimator splitAnim = ObjectAnimator.ofFloat(mMenuView,
						"alpha", 1);
				splitAnim.setDuration(FADE_DURATION);
				set.addListener(mVisAnimListener
						.withFinalVisibility(visibility));
				set.play(anim).with(splitAnim);
				set.start();
			} else {
				anim.addListener(mVisAnimListener
						.withFinalVisibility(visibility));
				anim.start();
			}
		} else {
			ObjectAnimator anim = ObjectAnimator.ofFloat(this, "alpha", 0);
			anim.setDuration(FADE_DURATION);
			anim.setInterpolator(sAlphaInterpolator);
			if (mSplitView != null && mMenuView != null) {
				AnimatorSet set = new AnimatorSet();
				ObjectAnimator splitAnim = ObjectAnimator.ofFloat(mMenuView,
						"alpha", 0);
				splitAnim.setDuration(FADE_DURATION);
				set.addListener(mVisAnimListener
						.withFinalVisibility(visibility));
				set.play(anim).with(splitAnim);
				set.start();
			} else {
				anim.addListener(mVisAnimListener
						.withFinalVisibility(visibility));
				anim.start();
			}
		}
	}

	/** 
	* <p>Title: setVisibility</p> 
	* <p>Description: </p> 
	* @param visibility 
	* @see com.actionbarsherlock.internal.nineoldandroids.view.NineViewGroup#setVisibility(int) 
	*/
	@Override
	public void setVisibility(int visibility) {
		if (mVisibilityAnim != null) {
			mVisibilityAnim.end();
		}
		super.setVisibility(visibility);
	}

	/**
	 * Show overflow menu.
	 * 
	 * @return true, if successful
	 */
	public boolean showOverflowMenu() {
		if (mActionMenuPresenter != null) {
			return mActionMenuPresenter.showOverflowMenu();
		}
		return false;
	}

	/**
	 * Post show overflow menu.
	 */
	public void postShowOverflowMenu() {
		post(new Runnable() {
			@Override
			public void run() {
				showOverflowMenu();
			}
		});
	}

	/**
	 * Hide overflow menu.
	 * 
	 * @return true, if successful
	 */
	public boolean hideOverflowMenu() {
		if (mActionMenuPresenter != null) {
			return mActionMenuPresenter.hideOverflowMenu();
		}
		return false;
	}

	/**
	 * Checks if is overflow menu showing.
	 * 
	 * @return true, if is overflow menu showing
	 */
	public boolean isOverflowMenuShowing() {
		if (mActionMenuPresenter != null) {
			return mActionMenuPresenter.isOverflowMenuShowing();
		}
		return false;
	}

	/**
	 * Checks if is overflow reserved.
	 * 
	 * @return true, if is overflow reserved
	 */
	public boolean isOverflowReserved() {
		return mActionMenuPresenter != null
				&& mActionMenuPresenter.isOverflowReserved();
	}

	/**
	 * Dismiss popup menus.
	 */
	public void dismissPopupMenus() {
		if (mActionMenuPresenter != null) {
			mActionMenuPresenter.dismissPopupMenus();
		}
	}

	/**
	 * Measure child view.
	 * 
	 * @param child
	 *            the child
	 * @param availableWidth
	 *            the available width
	 * @param childSpecHeight
	 *            the child spec height
	 * @param spacing
	 *            the spacing
	 * @return the int
	 */
	protected int measureChildView(View child, int availableWidth,
			int childSpecHeight, int spacing) {
		child.measure(MeasureSpec.makeMeasureSpec(availableWidth,
				MeasureSpec.AT_MOST), childSpecHeight);

		availableWidth -= child.getMeasuredWidth();
		availableWidth -= spacing;

		return Math.max(0, availableWidth);
	}

	/**
	 * Position child.
	 * 
	 * @param child
	 *            the child
	 * @param x
	 *            the x
	 * @param y
	 *            the y
	 * @param contentHeight
	 *            the content height
	 * @return the int
	 */
	protected int positionChild(View child, int x, int y, int contentHeight) {
		int childWidth = child.getMeasuredWidth();
		int childHeight = child.getMeasuredHeight();
		int childTop = y + (contentHeight - childHeight) / 2;

		child.layout(x, childTop, x + childWidth, childTop + childHeight);

		return childWidth;
	}

	/**
	 * Position child inverse.
	 * 
	 * @param child
	 *            the child
	 * @param x
	 *            the x
	 * @param y
	 *            the y
	 * @param contentHeight
	 *            the content height
	 * @return the int
	 */
	protected int positionChildInverse(View child, int x, int y,
			int contentHeight) {
		int childWidth = child.getMeasuredWidth();
		int childHeight = child.getMeasuredHeight();
		int childTop = y + (contentHeight - childHeight) / 2;

		child.layout(x - childWidth, childTop, x, childTop + childHeight);

		return childWidth;
	}

	/**
	 * The listener interface for receiving visibilityAnim events. The class
	 * that is interested in processing a visibilityAnim event implements this
	 * interface, and the object created with that class is registered with a
	 * component using the component's
	 * <code>addVisibilityAnimListener<code> method. When
	 * the visibilityAnim event occurs, that object's appropriate
	 * method is invoked.
	 * 
	 * @see VisibilityAnimEvent
	 */
	protected class VisibilityAnimListener implements Animator.AnimatorListener {
		
		/** The m canceled. */
		private boolean mCanceled = false;
		
		/** The m final visibility. */
		int mFinalVisibility;

		/**
		 * With final visibility.
		 * 
		 * @param visibility
		 *            the visibility
		 * @return the visibility anim listener
		 */
		public VisibilityAnimListener withFinalVisibility(int visibility) {
			mFinalVisibility = visibility;
			return this;
		}

		/** 
		* <p>Title: onAnimationStart</p> 
		* <p>Description: </p> 
		* @param animation 
		* @see com.actionbarsherlock.internal.nineoldandroids.animation.Animator.AnimatorListener#onAnimationStart(com.actionbarsherlock.internal.nineoldandroids.animation.Animator) 
		*/
		@Override
		public void onAnimationStart(Animator animation) {
			setVisibility(VISIBLE);
			mVisibilityAnim = animation;
			mCanceled = false;
		}

		/** 
		* <p>Title: onAnimationEnd</p> 
		* <p>Description: </p> 
		* @param animation 
		* @see com.actionbarsherlock.internal.nineoldandroids.animation.Animator.AnimatorListener#onAnimationEnd(com.actionbarsherlock.internal.nineoldandroids.animation.Animator) 
		*/
		@Override
		public void onAnimationEnd(Animator animation) {
			if (mCanceled)
				return;

			mVisibilityAnim = null;
			setVisibility(mFinalVisibility);
			if (mSplitView != null && mMenuView != null) {
				mMenuView.setVisibility(mFinalVisibility);
			}
		}

		/** 
		* <p>Title: onAnimationCancel</p> 
		* <p>Description: </p> 
		* @param animation 
		* @see com.actionbarsherlock.internal.nineoldandroids.animation.Animator.AnimatorListener#onAnimationCancel(com.actionbarsherlock.internal.nineoldandroids.animation.Animator) 
		*/
		@Override
		public void onAnimationCancel(Animator animation) {
			mCanceled = true;
		}

		/** 
		* <p>Title: onAnimationRepeat</p> 
		* <p>Description: </p> 
		* @param animation 
		* @see com.actionbarsherlock.internal.nineoldandroids.animation.Animator.AnimatorListener#onAnimationRepeat(com.actionbarsherlock.internal.nineoldandroids.animation.Animator) 
		*/
		@Override
		public void onAnimationRepeat(Animator animation) {
		}
	}
}
