/**   
* @Title: ActionBarContextView.java 
* @Package com.actionbarsherlock.internal.widget 
* @Description: 
*			澹版槑:
*			1)鎺屾帶鏍″洯鏄湰浜虹殑澶у鐨勫垱涓氫綔鍝�涔熸槸鏈汉鐨勬瘯涓氳璁�*			2)鏈▼搴忓皻鏈繘琛屽紑鏀炬簮浠ｇ爜,鎵�互绂佹缁勭粐鎴栬�鏄釜浜烘硠闇叉簮鐮�鍚﹀垯灏嗕細杩界┒鍏跺垜浜嬭矗浠�*			3)缂栧啓鏈蒋浠�鎴戦渶瑕佹劅璋㈠江鑰佸笀浠ュ強鍏朵粬榧撳姳鍜屾敮鎸佹垜鐨勫悓瀛︿互鍙婃湅鍙�*			4)鏈▼搴忕殑鏈�粓鎵�湁鏉冨睘浜庢湰浜�
* @author  寮犻浄 794857063@qq.com
* @date 2013-11-14 19:34:39 
* @version V1.0   
*/
package com.actionbarsherlock.internal.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;
import android.view.animation.DecelerateInterpolator;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.actionbarsherlock.internal.nineoldandroids.animation.Animator;
import com.actionbarsherlock.internal.nineoldandroids.animation.Animator.AnimatorListener;
import com.actionbarsherlock.internal.nineoldandroids.animation.AnimatorSet;
import com.actionbarsherlock.internal.nineoldandroids.animation.ObjectAnimator;
import com.actionbarsherlock.internal.nineoldandroids.view.animation.AnimatorProxy;
import com.actionbarsherlock.internal.nineoldandroids.widget.NineLinearLayout;
import com.actionbarsherlock.internal.view.menu.ActionMenuPresenter;
import com.actionbarsherlock.internal.view.menu.ActionMenuView;
import com.actionbarsherlock.internal.view.menu.MenuBuilder;
import com.actionbarsherlock.view.ActionMode;
import com.sendwine.app.R;

// TODO: Auto-generated Javadoc
/**
 * The Class ActionBarContextView.
 * 
 * @hide
 */
public class ActionBarContextView extends AbsActionBarView implements
		AnimatorListener {
	// UNUSED private static final String TAG = "ActionBarContextView";

	/** The m title. */
	private CharSequence mTitle;
	
	/** The m subtitle. */
	private CharSequence mSubtitle;

	/** The m close. */
	private NineLinearLayout mClose;
	
	/** The m custom view. */
	private View mCustomView;
	
	/** The m title layout. */
	private LinearLayout mTitleLayout;
	
	/** The m title view. */
	private TextView mTitleView;
	
	/** The m subtitle view. */
	private TextView mSubtitleView;
	
	/** The m title style res. */
	private int mTitleStyleRes;
	
	/** The m subtitle style res. */
	private int mSubtitleStyleRes;
	
	/** The m split background. */
	private Drawable mSplitBackground;

	/** The m current animation. */
	private Animator mCurrentAnimation;
	
	/** The m animate in on layout. */
	private boolean mAnimateInOnLayout;
	
	/** The m animation mode. */
	private int mAnimationMode;

	/** The Constant ANIMATE_IDLE. */
	private static final int ANIMATE_IDLE = 0;
	
	/** The Constant ANIMATE_IN. */
	private static final int ANIMATE_IN = 1;
	
	/** The Constant ANIMATE_OUT. */
	private static final int ANIMATE_OUT = 2;

	/**
	 * Instantiates a new action bar context view.
	 * 
	 * @param context
	 *            the context
	 */
	public ActionBarContextView(Context context) {
		this(context, null);
	}

	/**
	 * Instantiates a new action bar context view.
	 * 
	 * @param context
	 *            the context
	 * @param attrs
	 *            the attrs
	 */
	public ActionBarContextView(Context context, AttributeSet attrs) {
		this(context, attrs, R.attr.actionModeStyle);
	}

	/**
	 * Instantiates a new action bar context view.
	 * 
	 * @param context
	 *            the context
	 * @param attrs
	 *            the attrs
	 * @param defStyle
	 *            the def style
	 */
	public ActionBarContextView(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);

		TypedArray a = context.obtainStyledAttributes(attrs,
				R.styleable.SherlockActionMode, defStyle, 0);
		setBackgroundDrawable(a
				.getDrawable(R.styleable.SherlockActionMode_background));
		mTitleStyleRes = a.getResourceId(
				R.styleable.SherlockActionMode_titleTextStyle, 0);
		mSubtitleStyleRes = a.getResourceId(
				R.styleable.SherlockActionMode_subtitleTextStyle, 0);

		mContentHeight = a.getLayoutDimension(
				R.styleable.SherlockActionMode_height, 0);

		mSplitBackground = a
				.getDrawable(R.styleable.SherlockActionMode_backgroundSplit);

		a.recycle();
	}

	/** 
	* <p>Title: onDetachedFromWindow</p> 
	* <p>Description: </p>  
	* @see android.view.View#onDetachedFromWindow() 
	*/
	@Override
	public void onDetachedFromWindow() {
		super.onDetachedFromWindow();
		if (mActionMenuPresenter != null) {
			mActionMenuPresenter.hideOverflowMenu();
			mActionMenuPresenter.hideSubMenus();
		}
	}

	/** 
	* <p>Title: setSplitActionBar</p> 
	* <p>Description: </p> 
	* @param split 
	* @see com.actionbarsherlock.internal.widget.AbsActionBarView#setSplitActionBar(boolean) 
	*/
	@Override
	public void setSplitActionBar(boolean split) {
		if (mSplitActionBar != split) {
			if (mActionMenuPresenter != null) {
				// Mode is already active; move everything over and adjust the
				// menu itself.
				final LayoutParams layoutParams = new LayoutParams(
						LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
				if (!split) {
					mMenuView = (ActionMenuView) mActionMenuPresenter
							.getMenuView(this);
					mMenuView.setBackgroundDrawable(null);
					final ViewGroup oldParent = (ViewGroup) mMenuView
							.getParent();
					if (oldParent != null)
						oldParent.removeView(mMenuView);
					addView(mMenuView, layoutParams);
				} else {
					// Allow full screen width in split mode.
					mActionMenuPresenter.setWidthLimit(getContext()
							.getResources().getDisplayMetrics().widthPixels,
							true);
					// No limit to the item count; use whatever will fit.
					mActionMenuPresenter.setItemLimit(Integer.MAX_VALUE);
					// Span the whole width
					layoutParams.width = LayoutParams.MATCH_PARENT;
					layoutParams.height = mContentHeight;
					mMenuView = (ActionMenuView) mActionMenuPresenter
							.getMenuView(this);
					mMenuView.setBackgroundDrawable(mSplitBackground);
					final ViewGroup oldParent = (ViewGroup) mMenuView
							.getParent();
					if (oldParent != null)
						oldParent.removeView(mMenuView);
					mSplitView.addView(mMenuView, layoutParams);
				}
			}
			super.setSplitActionBar(split);
		}
	}

	/** 
	* <p>Title: setContentHeight</p> 
	* <p>Description: </p> 
	* @param height 
	* @see com.actionbarsherlock.internal.widget.AbsActionBarView#setContentHeight(int) 
	*/
	@Override
	public void setContentHeight(int height) {
		mContentHeight = height;
	}

	/**
	 * Sets the custom view.
	 * 
	 * @param view
	 *            the new custom view
	 */
	public void setCustomView(View view) {
		if (mCustomView != null) {
			removeView(mCustomView);
		}
		mCustomView = view;
		if (mTitleLayout != null) {
			removeView(mTitleLayout);
			mTitleLayout = null;
		}
		if (view != null) {
			addView(view);
		}
		requestLayout();
	}

	/**
	 * Sets the title.
	 * 
	 * @param title
	 *            the new title
	 */
	public void setTitle(CharSequence title) {
		mTitle = title;
		initTitle();
	}

	/**
	 * Sets the subtitle.
	 * 
	 * @param subtitle
	 *            the new subtitle
	 */
	public void setSubtitle(CharSequence subtitle) {
		mSubtitle = subtitle;
		initTitle();
	}

	/**
	 * Gets the title.
	 * 
	 * @return the title
	 */
	public CharSequence getTitle() {
		return mTitle;
	}

	/**
	 * Gets the subtitle.
	 * 
	 * @return the subtitle
	 */
	public CharSequence getSubtitle() {
		return mSubtitle;
	}

	/**
	 * Inits the title.
	 */
	private void initTitle() {
		if (mTitleLayout == null) {
			LayoutInflater inflater = LayoutInflater.from(getContext());
			inflater.inflate(R.layout.abs__action_bar_title_item, this);
			mTitleLayout = (LinearLayout) getChildAt(getChildCount() - 1);
			mTitleView = (TextView) mTitleLayout
					.findViewById(R.id.abs__action_bar_title);
			mSubtitleView = (TextView) mTitleLayout
					.findViewById(R.id.abs__action_bar_subtitle);
			if (mTitleStyleRes != 0) {
				mTitleView.setTextAppearance(mContext, mTitleStyleRes);
			}
			if (mSubtitleStyleRes != 0) {
				mSubtitleView.setTextAppearance(mContext, mSubtitleStyleRes);
			}
		}

		mTitleView.setText(mTitle);
		mSubtitleView.setText(mSubtitle);

		final boolean hasTitle = !TextUtils.isEmpty(mTitle);
		final boolean hasSubtitle = !TextUtils.isEmpty(mSubtitle);
		mSubtitleView.setVisibility(hasSubtitle ? VISIBLE : GONE);
		mTitleLayout.setVisibility(hasTitle || hasSubtitle ? VISIBLE : GONE);
		if (mTitleLayout.getParent() == null) {
			addView(mTitleLayout);
		}
	}

	/**
	 * Inits the for mode.
	 * 
	 * @param mode
	 *            the mode
	 */
	public void initForMode(final ActionMode mode) {
		if (mClose == null) {
			LayoutInflater inflater = LayoutInflater.from(mContext);
			mClose = (NineLinearLayout) inflater.inflate(
					R.layout.abs__action_mode_close_item, this, false);
			addView(mClose);
		} else if (mClose.getParent() == null) {
			addView(mClose);
		}

		View closeButton = mClose
				.findViewById(R.id.abs__action_mode_close_button);
		closeButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mode.finish();
			}
		});

		final MenuBuilder menu = (MenuBuilder) mode.getMenu();
		if (mActionMenuPresenter != null) {
			mActionMenuPresenter.dismissPopupMenus();
		}
		mActionMenuPresenter = new ActionMenuPresenter(mContext);
		mActionMenuPresenter.setReserveOverflow(true);

		final LayoutParams layoutParams = new LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
		if (!mSplitActionBar) {
			menu.addMenuPresenter(mActionMenuPresenter);
			mMenuView = (ActionMenuView) mActionMenuPresenter.getMenuView(this);
			mMenuView.setBackgroundDrawable(null);
			addView(mMenuView, layoutParams);
		} else {
			// Allow full screen width in split mode.
			mActionMenuPresenter.setWidthLimit(getContext().getResources()
					.getDisplayMetrics().widthPixels, true);
			// No limit to the item count; use whatever will fit.
			mActionMenuPresenter.setItemLimit(Integer.MAX_VALUE);
			// Span the whole width
			layoutParams.width = LayoutParams.MATCH_PARENT;
			layoutParams.height = mContentHeight;
			menu.addMenuPresenter(mActionMenuPresenter);
			mMenuView = (ActionMenuView) mActionMenuPresenter.getMenuView(this);
			mMenuView.setBackgroundDrawable(mSplitBackground);
			mSplitView.addView(mMenuView, layoutParams);
		}

		mAnimateInOnLayout = true;
	}

	/**
	 * Close mode.
	 */
	public void closeMode() {
		if (mAnimationMode == ANIMATE_OUT) {
			// Called again during close; just finish what we were doing.
			return;
		}
		if (mClose == null) {
			killMode();
			return;
		}

		finishAnimation();
		mAnimationMode = ANIMATE_OUT;
		mCurrentAnimation = makeOutAnimation();
		mCurrentAnimation.start();
	}

	/**
	 * Finish animation.
	 */
	private void finishAnimation() {
		final Animator a = mCurrentAnimation;
		if (a != null) {
			mCurrentAnimation = null;
			a.end();
		}
	}

	/**
	 * Kill mode.
	 */
	public void killMode() {
		finishAnimation();
		removeAllViews();
		if (mSplitView != null) {
			mSplitView.removeView(mMenuView);
		}
		mCustomView = null;
		mMenuView = null;
		mAnimateInOnLayout = false;
	}

	/** 
	* <p>Title: showOverflowMenu</p> 
	* <p>Description: </p> 
	* @return 
	* @see com.actionbarsherlock.internal.widget.AbsActionBarView#showOverflowMenu() 
	*/
	@Override
	public boolean showOverflowMenu() {
		if (mActionMenuPresenter != null) {
			return mActionMenuPresenter.showOverflowMenu();
		}
		return false;
	}

	/** 
	* <p>Title: hideOverflowMenu</p> 
	* <p>Description: </p> 
	* @return 
	* @see com.actionbarsherlock.internal.widget.AbsActionBarView#hideOverflowMenu() 
	*/
	@Override
	public boolean hideOverflowMenu() {
		if (mActionMenuPresenter != null) {
			return mActionMenuPresenter.hideOverflowMenu();
		}
		return false;
	}

	/** 
	* <p>Title: isOverflowMenuShowing</p> 
	* <p>Description: </p> 
	* @return 
	* @see com.actionbarsherlock.internal.widget.AbsActionBarView#isOverflowMenuShowing() 
	*/
	@Override
	public boolean isOverflowMenuShowing() {
		if (mActionMenuPresenter != null) {
			return mActionMenuPresenter.isOverflowMenuShowing();
		}
		return false;
	}

	/** 
	* <p>Title: generateDefaultLayoutParams</p> 
	* <p>Description: </p> 
	* @return 
	* @see android.view.ViewGroup#generateDefaultLayoutParams() 
	*/
	@Override
	protected ViewGroup.LayoutParams generateDefaultLayoutParams() {
		// Used by custom views if they don't supply layout params. Everything
		// else
		// added to an ActionBarContextView should have them already.
		return new MarginLayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.WRAP_CONTENT);
	}

	/** 
	* <p>Title: generateLayoutParams</p> 
	* <p>Description: </p> 
	* @param attrs
	* @return 
	* @see android.view.ViewGroup#generateLayoutParams(android.util.AttributeSet) 
	*/
	@Override
	public ViewGroup.LayoutParams generateLayoutParams(AttributeSet attrs) {
		return new MarginLayoutParams(getContext(), attrs);
	}

	/** 
	* <p>Title: onMeasure</p> 
	* <p>Description: </p> 
	* @param widthMeasureSpec
	* @param heightMeasureSpec 
	* @see android.view.View#onMeasure(int, int) 
	*/
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		final int widthMode = MeasureSpec.getMode(widthMeasureSpec);
		if (widthMode != MeasureSpec.EXACTLY) {
			throw new IllegalStateException(
					getClass().getSimpleName()
							+ " can only be used "
							+ "with android:layout_width=\"match_parent\" (or fill_parent)");
		}

		final int heightMode = MeasureSpec.getMode(heightMeasureSpec);
		if (heightMode == MeasureSpec.UNSPECIFIED) {
			throw new IllegalStateException(getClass().getSimpleName()
					+ " can only be used "
					+ "with android:layout_height=\"wrap_content\"");
		}

		final int contentWidth = MeasureSpec.getSize(widthMeasureSpec);

		int maxHeight = mContentHeight > 0 ? mContentHeight : MeasureSpec
				.getSize(heightMeasureSpec);

		final int verticalPadding = getPaddingTop() + getPaddingBottom();
		int availableWidth = contentWidth - getPaddingLeft()
				- getPaddingRight();
		final int height = maxHeight - verticalPadding;
		final int childSpecHeight = MeasureSpec.makeMeasureSpec(height,
				MeasureSpec.AT_MOST);

		if (mClose != null) {
			availableWidth = measureChildView(mClose, availableWidth,
					childSpecHeight, 0);
			MarginLayoutParams lp = (MarginLayoutParams) mClose
					.getLayoutParams();
			availableWidth -= lp.leftMargin + lp.rightMargin;
		}

		if (mMenuView != null && mMenuView.getParent() == this) {
			availableWidth = measureChildView(mMenuView, availableWidth,
					childSpecHeight, 0);
		}

		if (mTitleLayout != null && mCustomView == null) {
			availableWidth = measureChildView(mTitleLayout, availableWidth,
					childSpecHeight, 0);
		}

		if (mCustomView != null) {
			ViewGroup.LayoutParams lp = mCustomView.getLayoutParams();
			final int customWidthMode = lp.width != LayoutParams.WRAP_CONTENT ? MeasureSpec.EXACTLY
					: MeasureSpec.AT_MOST;
			final int customWidth = lp.width >= 0 ? Math.min(lp.width,
					availableWidth) : availableWidth;
			final int customHeightMode = lp.height != LayoutParams.WRAP_CONTENT ? MeasureSpec.EXACTLY
					: MeasureSpec.AT_MOST;
			final int customHeight = lp.height >= 0 ? Math.min(lp.height,
					height) : height;
			mCustomView
					.measure(MeasureSpec.makeMeasureSpec(customWidth,
							customWidthMode), MeasureSpec.makeMeasureSpec(
							customHeight, customHeightMode));
		}

		if (mContentHeight <= 0) {
			int measuredHeight = 0;
			final int count = getChildCount();
			for (int i = 0; i < count; i++) {
				View v = getChildAt(i);
				int paddedViewHeight = v.getMeasuredHeight() + verticalPadding;
				if (paddedViewHeight > measuredHeight) {
					measuredHeight = paddedViewHeight;
				}
			}
			setMeasuredDimension(contentWidth, measuredHeight);
		} else {
			setMeasuredDimension(contentWidth, maxHeight);
		}
	}

	/**
	 * Make in animation.
	 * 
	 * @return the animator
	 */
	private Animator makeInAnimation() {
		mClose.setTranslationX(-mClose.getWidth()
				- ((MarginLayoutParams) mClose.getLayoutParams()).leftMargin);
		ObjectAnimator buttonAnimator = ObjectAnimator.ofFloat(mClose,
				"translationX", 0);
		buttonAnimator.setDuration(200);
		buttonAnimator.addListener(this);
		buttonAnimator.setInterpolator(new DecelerateInterpolator());

		AnimatorSet set = new AnimatorSet();
		AnimatorSet.Builder b = set.play(buttonAnimator);

		if (mMenuView != null) {
			final int count = mMenuView.getChildCount();
			if (count > 0) {
				for (int i = count - 1, j = 0; i >= 0; i--, j++) {
					AnimatorProxy child = AnimatorProxy.wrap(mMenuView
							.getChildAt(i));
					child.setScaleY(0);
					ObjectAnimator a = ObjectAnimator.ofFloat(child, "scaleY",
							0, 1);
					a.setDuration(100);
					a.setStartDelay(j * 70);
					b.with(a);
				}
			}
		}

		return set;
	}

	/**
	 * Make out animation.
	 * 
	 * @return the animator
	 */
	private Animator makeOutAnimation() {
		ObjectAnimator buttonAnimator = ObjectAnimator
				.ofFloat(
						mClose,
						"translationX",
						-mClose.getWidth()
								- ((MarginLayoutParams) mClose
										.getLayoutParams()).leftMargin);
		buttonAnimator.setDuration(200);
		buttonAnimator.addListener(this);
		buttonAnimator.setInterpolator(new DecelerateInterpolator());

		AnimatorSet set = new AnimatorSet();
		AnimatorSet.Builder b = set.play(buttonAnimator);

		if (mMenuView != null) {
			final int count = mMenuView.getChildCount();
			if (count > 0) {
				for (int i = 0; i < 0; i++) {
					AnimatorProxy child = AnimatorProxy.wrap(mMenuView
							.getChildAt(i));
					child.setScaleY(0);
					ObjectAnimator a = ObjectAnimator.ofFloat(child, "scaleY",
							0);
					a.setDuration(100);
					a.setStartDelay(i * 70);
					b.with(a);
				}
			}
		}

		return set;
	}

	/** 
	* <p>Title: onLayout</p> 
	* <p>Description: </p> 
	* @param changed
	* @param l
	* @param t
	* @param r
	* @param b 
	* @see android.view.ViewGroup#onLayout(boolean, int, int, int, int) 
	*/
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		int x = getPaddingLeft();
		final int y = getPaddingTop();
		final int contentHeight = b - t - getPaddingTop() - getPaddingBottom();

		if (mClose != null && mClose.getVisibility() != GONE) {
			MarginLayoutParams lp = (MarginLayoutParams) mClose
					.getLayoutParams();
			x += lp.leftMargin;
			x += positionChild(mClose, x, y, contentHeight);
			x += lp.rightMargin;

			if (mAnimateInOnLayout) {
				mAnimationMode = ANIMATE_IN;
				mCurrentAnimation = makeInAnimation();
				mCurrentAnimation.start();
				mAnimateInOnLayout = false;
			}
		}

		if (mTitleLayout != null && mCustomView == null) {
			x += positionChild(mTitleLayout, x, y, contentHeight);
		}

		if (mCustomView != null) {
			x += positionChild(mCustomView, x, y, contentHeight);
		}

		x = r - l - getPaddingRight();

		if (mMenuView != null) {
			x -= positionChildInverse(mMenuView, x, y, contentHeight);
		}
	}

	/** 
	* <p>Title: onAnimationStart</p> 
	* <p>Description: </p> 
	* @param animation 
	* @see com.actionbarsherlock.internal.nineoldandroids.animation.Animator.AnimatorListener#onAnimationStart(com.actionbarsherlock.internal.nineoldandroids.animation.Animator) 
	*/
	@Override
	public void onAnimationStart(Animator animation) {
	}

	/** 
	* <p>Title: onAnimationEnd</p> 
	* <p>Description: </p> 
	* @param animation 
	* @see com.actionbarsherlock.internal.nineoldandroids.animation.Animator.AnimatorListener#onAnimationEnd(com.actionbarsherlock.internal.nineoldandroids.animation.Animator) 
	*/
	@Override
	public void onAnimationEnd(Animator animation) {
		if (mAnimationMode == ANIMATE_OUT) {
			killMode();
		}
		mAnimationMode = ANIMATE_IDLE;
	}

	/** 
	* <p>Title: onAnimationCancel</p> 
	* <p>Description: </p> 
	* @param animation 
	* @see com.actionbarsherlock.internal.nineoldandroids.animation.Animator.AnimatorListener#onAnimationCancel(com.actionbarsherlock.internal.nineoldandroids.animation.Animator) 
	*/
	@Override
	public void onAnimationCancel(Animator animation) {
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

	/** 
	* <p>Title: shouldDelayChildPressedState</p> 
	* <p>Description: </p> 
	* @return 
	* @see android.view.ViewGroup#shouldDelayChildPressedState() 
	*/
	@Override
	public boolean shouldDelayChildPressedState() {
		return false;
	}

	/** 
	* <p>Title: onInitializeAccessibilityEvent</p> 
	* <p>Description: </p> 
	* @param event 
	* @see android.view.View#onInitializeAccessibilityEvent(android.view.accessibility.AccessibilityEvent) 
	*/
	@Override
	public void onInitializeAccessibilityEvent(AccessibilityEvent event) {
		if (event.getEventType() == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED) {
			// Action mode started
			// TODO event.setSource(this);
			event.setClassName(getClass().getName());
			event.setPackageName(getContext().getPackageName());
			event.setContentDescription(mTitle);
		} else {
			// TODO super.onInitializeAccessibilityEvent(event);
		}
	}
}
