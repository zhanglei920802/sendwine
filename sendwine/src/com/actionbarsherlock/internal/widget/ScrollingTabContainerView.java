/**   
* @Title: ScrollingTabContainerView.java 
* @Package com.actionbarsherlock.internal.widget 
* @Description: 
*			澹版槑:
*			1)鎺屾帶鏍″洯鏄湰浜虹殑澶у鐨勫垱涓氫綔鍝�涔熸槸鏈汉鐨勬瘯涓氳璁�*			2)鏈▼搴忓皻鏈繘琛屽紑鏀炬簮浠ｇ爜,鎵�互绂佹缁勭粐鎴栬�鏄釜浜烘硠闇叉簮鐮�鍚﹀垯灏嗕細杩界┒鍏跺垜浜嬭矗浠�*			3)缂栧啓鏈蒋浠�鎴戦渶瑕佹劅璋㈠江鑰佸笀浠ュ強鍏朵粬榧撳姳鍜屾敮鎸佹垜鐨勫悓瀛︿互鍙婃湅鍙�*			4)鏈▼搴忕殑鏈�粓鎵�湁鏉冨睘浜庢湰浜�
* @author  寮犻浄 794857063@qq.com
* @date 2013-11-14 19:34:53 
* @version V1.0   
*/
package com.actionbarsherlock.internal.widget;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.text.TextUtils.TruncateAt;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;


import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.internal.nineoldandroids.animation.Animator;
import com.actionbarsherlock.internal.nineoldandroids.animation.ObjectAnimator;
import com.actionbarsherlock.internal.nineoldandroids.widget.NineHorizontalScrollView;
import com.sendwine.app.R;

// TODO: Auto-generated Javadoc
/**
 * This widget implements the dynamic action bar tab behavior that can change
 * across different configurations or circumstances.
 */
public class ScrollingTabContainerView extends NineHorizontalScrollView
		implements IcsAdapterView.OnItemSelectedListener {
	// UNUSED private static final String TAG = "ScrollingTabContainerView";
	/** The m tab selector. */
	Runnable mTabSelector;
	
	/** The m tab click listener. */
	private TabClickListener mTabClickListener;

	/** The m tab layout. */
	private IcsLinearLayout mTabLayout;
	
	/** The m tab spinner. */
	private IcsSpinner mTabSpinner;
	
	/** The m allow collapse. */
	private boolean mAllowCollapse;

	/** The m inflater. */
	private LayoutInflater mInflater;

	/** The m max tab width. */
	int mMaxTabWidth;
	
	/** The m content height. */
	private int mContentHeight;
	
	/** The m selected tab index. */
	private int mSelectedTabIndex;

	/** The m visibility anim. */
	protected Animator mVisibilityAnim;
	
	/** The m vis anim listener. */
	protected final VisibilityAnimListener mVisAnimListener = new VisibilityAnimListener();

	/** The Constant sAlphaInterpolator. */
	private static final/* Time */Interpolator sAlphaInterpolator = new DecelerateInterpolator();

	/** The Constant FADE_DURATION. */
	private static final int FADE_DURATION = 200;

	/**
	 * Instantiates a new scrolling tab container view.
	 * 
	 * @param context
	 *            the context
	 */
	public ScrollingTabContainerView(Context context) {
		super(context);
		setHorizontalScrollBarEnabled(false);

		TypedArray a = getContext().obtainStyledAttributes(null,
				R.styleable.SherlockActionBar, R.attr.actionBarStyle, 0);
		setContentHeight(a.getLayoutDimension(
				R.styleable.SherlockActionBar_height, 0));
		a.recycle();

		mInflater = LayoutInflater.from(context);

		mTabLayout = createTabLayout();
		addView(mTabLayout, new ViewGroup.LayoutParams(
				ViewGroup.LayoutParams.WRAP_CONTENT,
				ViewGroup.LayoutParams.MATCH_PARENT));
	}

	/** 
	* <p>Title: onMeasure</p> 
	* <p>Description: </p> 
	* @param widthMeasureSpec
	* @param heightMeasureSpec 
	* @see android.widget.HorizontalScrollView#onMeasure(int, int) 
	*/
	@Override
	public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		final int widthMode = MeasureSpec.getMode(widthMeasureSpec);
		final boolean lockedExpanded = widthMode == MeasureSpec.EXACTLY;
		setFillViewport(lockedExpanded);

		final int childCount = mTabLayout.getChildCount();
		if (childCount > 1
				&& (widthMode == MeasureSpec.EXACTLY || widthMode == MeasureSpec.AT_MOST)) {
			if (childCount > 2) {
				mMaxTabWidth = (int) (MeasureSpec.getSize(widthMeasureSpec) * 0.4f);
			} else {
				mMaxTabWidth = MeasureSpec.getSize(widthMeasureSpec) / 2;
			}
		} else {
			mMaxTabWidth = -1;
		}

		heightMeasureSpec = MeasureSpec.makeMeasureSpec(mContentHeight,
				MeasureSpec.EXACTLY);

		final boolean canCollapse = !lockedExpanded && mAllowCollapse;

		if (canCollapse) {
			// See if we should expand
			mTabLayout.measure(MeasureSpec.UNSPECIFIED, heightMeasureSpec);
			if (mTabLayout.getMeasuredWidth() > MeasureSpec
					.getSize(widthMeasureSpec)) {
				performCollapse();
			} else {
				performExpand();
			}
		} else {
			performExpand();
		}

		final int oldWidth = getMeasuredWidth();
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		final int newWidth = getMeasuredWidth();

		if (lockedExpanded && oldWidth != newWidth) {
			// Recenter the tab display if we're at a new (scrollable) size.
			setTabSelected(mSelectedTabIndex);
		}
	}

	/**
	 * Indicates whether this view is collapsed into a dropdown menu instead of
	 * traditional tabs.
	 * 
	 * @return true if showing as a spinner
	 */
	private boolean isCollapsed() {
		return mTabSpinner != null && mTabSpinner.getParent() == this;
	}

	/**
	 * Sets the allow collapse.
	 * 
	 * @param allowCollapse
	 *            the new allow collapse
	 */
	public void setAllowCollapse(boolean allowCollapse) {
		mAllowCollapse = allowCollapse;
	}

	/**
	 * Perform collapse.
	 */
	private void performCollapse() {
		if (isCollapsed())
			return;

		if (mTabSpinner == null) {
			mTabSpinner = createSpinner();
		}
		removeView(mTabLayout);
		addView(mTabSpinner, new ViewGroup.LayoutParams(
				ViewGroup.LayoutParams.WRAP_CONTENT,
				ViewGroup.LayoutParams.MATCH_PARENT));
		if (mTabSpinner.getAdapter() == null) {
			mTabSpinner.setAdapter(new TabAdapter());
		}
		if (mTabSelector != null) {
			removeCallbacks(mTabSelector);
			mTabSelector = null;
		}
		mTabSpinner.setSelection(mSelectedTabIndex);
	}

	/**
	 * Perform expand.
	 * 
	 * @return true, if successful
	 */
	private boolean performExpand() {
		if (!isCollapsed())
			return false;

		removeView(mTabSpinner);
		addView(mTabLayout, new ViewGroup.LayoutParams(
				ViewGroup.LayoutParams.WRAP_CONTENT,
				ViewGroup.LayoutParams.MATCH_PARENT));
		setTabSelected(mTabSpinner.getSelectedItemPosition());
		return false;
	}

	/**
	 * Sets the tab selected.
	 * 
	 * @param position
	 *            the new tab selected
	 */
	public void setTabSelected(int position) {
		mSelectedTabIndex = position;
		final int tabCount = mTabLayout.getChildCount();
		for (int i = 0; i < tabCount; i++) {
			final View child = mTabLayout.getChildAt(i);
			final boolean isSelected = i == position;
			child.setSelected(isSelected);
			if (isSelected) {
				animateToTab(position);
			}
		}
	}

	/**
	 * Sets the content height.
	 * 
	 * @param contentHeight
	 *            the new content height
	 */
	public void setContentHeight(int contentHeight) {
		mContentHeight = contentHeight;
		requestLayout();
	}

	/**
	 * Creates the tab layout.
	 * 
	 * @return the ics linear layout
	 */
	private IcsLinearLayout createTabLayout() {
		final IcsLinearLayout tabLayout = (IcsLinearLayout) LayoutInflater
				.from(getContext()).inflate(
						R.layout.abs__action_bar_tab_bar_view, null);
		tabLayout.setMeasureWithLargestChildEnabled(true);
		tabLayout.setLayoutParams(new LinearLayout.LayoutParams(
				android.view.ViewGroup.LayoutParams.WRAP_CONTENT,
				android.view.ViewGroup.LayoutParams.MATCH_PARENT));
		return tabLayout;
	}

	/**
	 * Creates the spinner.
	 * 
	 * @return the ics spinner
	 */
	private IcsSpinner createSpinner() {
		final IcsSpinner spinner = new IcsSpinner(getContext(), null,
				R.attr.actionDropDownStyle);
		spinner.setLayoutParams(new LinearLayout.LayoutParams(
				android.view.ViewGroup.LayoutParams.WRAP_CONTENT,
				android.view.ViewGroup.LayoutParams.MATCH_PARENT));
		spinner.setOnItemSelectedListener(this);
		return spinner;
	}

	/** 
	* <p>Title: onConfigurationChanged</p> 
	* <p>Description: </p> 
	* @param newConfig 
	* @see android.view.View#onConfigurationChanged(android.content.res.Configuration) 
	*/
	@Override
	protected void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);

		// Action bar can change size on configuration changes.
		// Reread the desired height from the theme-specified style.
		TypedArray a = getContext().obtainStyledAttributes(null,
				R.styleable.SherlockActionBar, R.attr.actionBarStyle, 0);
		setContentHeight(a.getLayoutDimension(
				R.styleable.SherlockActionBar_height, 0));
		a.recycle();
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
			}
			ObjectAnimator anim = ObjectAnimator.ofFloat(this, "alpha", 1);
			anim.setDuration(FADE_DURATION);
			anim.setInterpolator(sAlphaInterpolator);

			anim.addListener(mVisAnimListener.withFinalVisibility(visibility));
			anim.start();
		} else {
			ObjectAnimator anim = ObjectAnimator.ofFloat(this, "alpha", 0);
			anim.setDuration(FADE_DURATION);
			anim.setInterpolator(sAlphaInterpolator);

			anim.addListener(mVisAnimListener.withFinalVisibility(visibility));
			anim.start();
		}
	}

	/**
	 * Animate to tab.
	 * 
	 * @param position
	 *            the position
	 */
	public void animateToTab(final int position) {
		final View tabView = mTabLayout.getChildAt(position);
		if (mTabSelector != null) {
			removeCallbacks(mTabSelector);
		}
		mTabSelector = new Runnable() {
			@Override
			public void run() {
				final int scrollPos = tabView.getLeft()
						- (getWidth() - tabView.getWidth()) / 2;
				smoothScrollTo(scrollPos, 0);
				mTabSelector = null;
			}
		};
		post(mTabSelector);
	}

	/** 
	* <p>Title: onAttachedToWindow</p> 
	* <p>Description: </p>  
	* @see android.view.View#onAttachedToWindow() 
	*/
	@Override
	public void onAttachedToWindow() {
		super.onAttachedToWindow();
		if (mTabSelector != null) {
			// Re-post the selector we saved
			post(mTabSelector);
		}
	}

	/** 
	* <p>Title: onDetachedFromWindow</p> 
	* <p>Description: </p>  
	* @see android.view.View#onDetachedFromWindow() 
	*/
	@Override
	public void onDetachedFromWindow() {
		super.onDetachedFromWindow();
		if (mTabSelector != null) {
			removeCallbacks(mTabSelector);
		}
	}

	/**
	 * Creates the tab view.
	 * 
	 * @param tab
	 *            the tab
	 * @param forAdapter
	 *            the for adapter
	 * @return the tab view
	 */
	private TabView createTabView(ActionBar.Tab tab, boolean forAdapter) {
		// Workaround for not being able to pass a defStyle on pre-3.0
		final TabView tabView = (TabView) mInflater.inflate(
				R.layout.abs__action_bar_tab, null);
		tabView.init(this, tab, forAdapter);

		if (forAdapter) {
			tabView.setBackgroundDrawable(null);
			tabView.setLayoutParams(new ListView.LayoutParams(
					android.view.ViewGroup.LayoutParams.MATCH_PARENT,
					mContentHeight));
		} else {
			tabView.setFocusable(true);

			if (mTabClickListener == null) {
				mTabClickListener = new TabClickListener();
			}
			tabView.setOnClickListener(mTabClickListener);
		}
		return tabView;
	}

	/**
	 * Adds the tab.
	 * 
	 * @param tab
	 *            the tab
	 * @param setSelected
	 *            the set selected
	 */
	public void addTab(ActionBar.Tab tab, boolean setSelected) {
		TabView tabView = createTabView(tab, false);
		mTabLayout.addView(tabView, new IcsLinearLayout.LayoutParams(0,
				android.view.ViewGroup.LayoutParams.MATCH_PARENT, 1));
		if (mTabSpinner != null) {
			((TabAdapter) mTabSpinner.getAdapter()).notifyDataSetChanged();
		}
		if (setSelected) {
			tabView.setSelected(true);
		}
		if (mAllowCollapse) {
			requestLayout();
		}
	}

	/**
	 * Adds the tab.
	 * 
	 * @param tab
	 *            the tab
	 * @param position
	 *            the position
	 * @param setSelected
	 *            the set selected
	 */
	public void addTab(ActionBar.Tab tab, int position, boolean setSelected) {
		final TabView tabView = createTabView(tab, false);
		mTabLayout.addView(tabView, position, new IcsLinearLayout.LayoutParams(
				0, android.view.ViewGroup.LayoutParams.MATCH_PARENT, 1));
		if (mTabSpinner != null) {
			((TabAdapter) mTabSpinner.getAdapter()).notifyDataSetChanged();
		}
		if (setSelected) {
			tabView.setSelected(true);
		}
		if (mAllowCollapse) {
			requestLayout();
		}
	}

	/**
	 * Update tab.
	 * 
	 * @param position
	 *            the position
	 */
	public void updateTab(int position) {
		((TabView) mTabLayout.getChildAt(position)).update();
		if (mTabSpinner != null) {
			((TabAdapter) mTabSpinner.getAdapter()).notifyDataSetChanged();
		}
		if (mAllowCollapse) {
			requestLayout();
		}
	}

	/**
	 * Removes the tab at.
	 * 
	 * @param position
	 *            the position
	 */
	public void removeTabAt(int position) {
		mTabLayout.removeViewAt(position);
		if (mTabSpinner != null) {
			((TabAdapter) mTabSpinner.getAdapter()).notifyDataSetChanged();
		}
		if (mAllowCollapse) {
			requestLayout();
		}
	}

	/**
	 * Removes the all tabs.
	 */
	public void removeAllTabs() {
		mTabLayout.removeAllViews();
		if (mTabSpinner != null) {
			((TabAdapter) mTabSpinner.getAdapter()).notifyDataSetChanged();
		}
		if (mAllowCollapse) {
			requestLayout();
		}
	}

	/** 
	* <p>Title: onItemSelected</p> 
	* <p>Description: </p> 
	* @param parent
	* @param view
	* @param position
	* @param id 
	* @see com.actionbarsherlock.internal.widget.IcsAdapterView.OnItemSelectedListener#onItemSelected(com.actionbarsherlock.internal.widget.IcsAdapterView, android.view.View, int, long) 
	*/
	@Override
	public void onItemSelected(IcsAdapterView<?> parent, View view,
			int position, long id) {
		TabView tabView = (TabView) view;
		tabView.getTab().select();
	}

	/** 
	* <p>Title: onNothingSelected</p> 
	* <p>Description: </p> 
	* @param parent 
	* @see com.actionbarsherlock.internal.widget.IcsAdapterView.OnItemSelectedListener#onNothingSelected(com.actionbarsherlock.internal.widget.IcsAdapterView) 
	*/
	@Override
	public void onNothingSelected(IcsAdapterView<?> parent) {
	}

	/**
	 * The Class TabView.
	 */
	public static class TabView extends LinearLayout {
		
		/** The m parent. */
		private ScrollingTabContainerView mParent;
		
		/** The m tab. */
		private ActionBar.Tab mTab;
		
		/** The m text view. */
		private CapitalizingTextView mTextView;
		
		/** The m icon view. */
		private ImageView mIconView;
		
		/** The m custom view. */
		private View mCustomView;

		/**
		 * Instantiates a new tab view.
		 * 
		 * @param context
		 *            the context
		 * @param attrs
		 *            the attrs
		 */
		public TabView(Context context, AttributeSet attrs) {
			// TODO super(context, null, R.attr.actionBarTabStyle);
			super(context, attrs);
		}

		/**
		 * Inits the.
		 * 
		 * @param parent
		 *            the parent
		 * @param tab
		 *            the tab
		 * @param forList
		 *            the for list
		 */
		public void init(ScrollingTabContainerView parent, ActionBar.Tab tab,
				boolean forList) {
			mParent = parent;
			mTab = tab;

			if (forList) {
				setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
			}

			update();
		}

		/**
		 * Bind tab.
		 * 
		 * @param tab
		 *            the tab
		 */
		public void bindTab(ActionBar.Tab tab) {
			mTab = tab;
			update();
		}

		/** 
		* <p>Title: onMeasure</p> 
		* <p>Description: </p> 
		* @param widthMeasureSpec
		* @param heightMeasureSpec 
		* @see android.widget.LinearLayout#onMeasure(int, int) 
		*/
		@Override
		public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
			super.onMeasure(widthMeasureSpec, heightMeasureSpec);

			// Re-measure if we went beyond our maximum size.
			if (mParent.mMaxTabWidth > 0
					&& getMeasuredWidth() > mParent.mMaxTabWidth) {
				super.onMeasure(MeasureSpec.makeMeasureSpec(
						mParent.mMaxTabWidth, MeasureSpec.EXACTLY),
						heightMeasureSpec);
			}
		}

		/**
		 * Update.
		 */
		public void update() {
			final ActionBar.Tab tab = mTab;
			final View custom = tab.getCustomView();
			if (custom != null) {
				final ViewParent customParent = custom.getParent();
				if (customParent != this) {
					if (customParent != null)
						((ViewGroup) customParent).removeView(custom);
					addView(custom);
				}
				mCustomView = custom;
				if (mTextView != null)
					mTextView.setVisibility(GONE);
				if (mIconView != null) {
					mIconView.setVisibility(GONE);
					mIconView.setImageDrawable(null);
				}
			} else {
				if (mCustomView != null) {
					removeView(mCustomView);
					mCustomView = null;
				}

				final Drawable icon = tab.getIcon();
				final CharSequence text = tab.getText();

				if (icon != null) {
					if (mIconView == null) {
						ImageView iconView = new ImageView(getContext());
						LayoutParams lp = new LayoutParams(
								android.view.ViewGroup.LayoutParams.WRAP_CONTENT,
								android.view.ViewGroup.LayoutParams.WRAP_CONTENT);
						lp.gravity = Gravity.CENTER_VERTICAL;
						iconView.setLayoutParams(lp);
						addView(iconView, 0);
						mIconView = iconView;
					}
					mIconView.setImageDrawable(icon);
					mIconView.setVisibility(VISIBLE);
				} else if (mIconView != null) {
					mIconView.setVisibility(GONE);
					mIconView.setImageDrawable(null);
				}

				if (text != null) {
					if (mTextView == null) {
						CapitalizingTextView textView = new CapitalizingTextView(
								getContext(), null,
								R.attr.actionBarTabTextStyle);
						textView.setEllipsize(TruncateAt.END);
						LayoutParams lp = new LayoutParams(
								android.view.ViewGroup.LayoutParams.WRAP_CONTENT,
								android.view.ViewGroup.LayoutParams.WRAP_CONTENT);
						lp.gravity = Gravity.CENTER_VERTICAL;
						textView.setLayoutParams(lp);
						addView(textView);
						mTextView = textView;
					}
					mTextView.setTextCompat(text);
					mTextView.setVisibility(VISIBLE);
				} else if (mTextView != null) {
					mTextView.setVisibility(GONE);
					mTextView.setText(null);
				}

				if (mIconView != null) {
					mIconView
							.setContentDescription(tab.getContentDescription());
				}
			}
		}

		/**
		 * Gets the tab.
		 * 
		 * @return the tab
		 */
		public ActionBar.Tab getTab() {
			return mTab;
		}
	}

	/**
	 * The Class TabAdapter.
	 */
	private class TabAdapter extends BaseAdapter {
		
		/** 
		* <p>Title: getCount</p> 
		* <p>Description: </p> 
		* @return 
		* @see android.widget.Adapter#getCount() 
		*/
		@Override
		public int getCount() {
			return mTabLayout.getChildCount();
		}

		/** 
		* <p>Title: getItem</p> 
		* <p>Description: </p> 
		* @param position
		* @return 
		* @see android.widget.Adapter#getItem(int) 
		*/
		@Override
		public Object getItem(int position) {
			return ((TabView) mTabLayout.getChildAt(position)).getTab();
		}

		/** 
		* <p>Title: getItemId</p> 
		* <p>Description: </p> 
		* @param position
		* @return 
		* @see android.widget.Adapter#getItemId(int) 
		*/
		@Override
		public long getItemId(int position) {
			return position;
		}

		/** 
		* <p>Title: getView</p> 
		* <p>Description: </p> 
		* @param position
		* @param convertView
		* @param parent
		* @return 
		* @see android.widget.Adapter#getView(int, android.view.View, android.view.ViewGroup) 
		*/
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = createTabView((ActionBar.Tab) getItem(position),
						true);
			} else {
				((TabView) convertView)
						.bindTab((ActionBar.Tab) getItem(position));
			}
			return convertView;
		}
	}

	/**
	 * The listener interface for receiving tabClick events. The class that is
	 * interested in processing a tabClick event implements this interface, and
	 * the object created with that class is registered with a component using
	 * the component's <code>addTabClickListener<code> method. When
	 * the tabClick event occurs, that object's appropriate
	 * method is invoked.
	 * 
	 * @see TabClickEvent
	 */
	private class TabClickListener implements OnClickListener {
		
		/** 
		* <p>Title: onClick</p> 
		* <p>Description: </p> 
		* @param view 
		* @see android.view.View.OnClickListener#onClick(android.view.View) 
		*/
		@Override
		public void onClick(View view) {
			TabView tabView = (TabView) view;
			tabView.getTab().select();
			final int tabCount = mTabLayout.getChildCount();
			for (int i = 0; i < tabCount; i++) {
				final View child = mTabLayout.getChildAt(i);
				child.setSelected(child == view);
			}
		}
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
		private int mFinalVisibility;

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
