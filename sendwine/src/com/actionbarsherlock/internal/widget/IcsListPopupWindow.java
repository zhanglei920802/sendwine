/**   
* @Title: BadgeView.java 
* @Package cn.cdut.app.ui.widgets 
* @Description: 
*			澹版槑:
*			1)鎺屾帶鏍″洯鏄湰浜虹殑澶у鐨勫垱涓氫綔鍝�涔熸槸鏈汉鐨勬瘯涓氳璁�*			2)鏈▼搴忓皻鏈繘琛屽紑鏀炬簮浠ｇ爜,鎵�互绂佹缁勭粐鎴栬�鏄釜浜烘硠闇叉簮鐮�鍚﹀垯灏嗕細杩界┒鍏跺垜浜嬭矗浠�*			3)缂栧啓鏈蒋浠�鎴戦渶瑕佹劅璋㈠江鑰佸笀浠ュ強鍏朵粬榧撳姳鍜屾敮鎸佹垜鐨勫悓瀛︿互鍙婃湅鍙�*			4)鏈▼搴忕殑鏈�粓鎵�湁鏉冨睘浜庢湰浜�
* @author  寮犻浄 794857063@qq.com
* @date 2013-11-14 19:34:39 
* @version V1.0   
*/
package com.actionbarsherlock.internal.widget;


import com.sendwine.app.R;

import android.content.Context;
import android.content.res.Resources;
import android.database.DataSetObserver;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.ContextThemeWrapper;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;

// TODO: Auto-generated Javadoc
/**
 * A proxy between pre- and post-Honeycomb implementations of this class.
 */
public class IcsListPopupWindow {
	/**
	 * This value controls the length of time that the user must leave a pointer
	 * down without scrolling to expand the autocomplete dropdown list to cover
	 * the IME.
	 */
	private static final int EXPAND_LIST_TIMEOUT = 250;

	/** The m context. */
	private Context mContext;
	
	/** The m popup. */
	private PopupWindow mPopup;
	
	/** The m adapter. */
	private ListAdapter mAdapter;
	
	/** The m drop down list. */
	private DropDownListView mDropDownList;

	/** The m drop down height. */
	private int mDropDownHeight = ViewGroup.LayoutParams.WRAP_CONTENT;
	
	/** The m drop down width. */
	private int mDropDownWidth = ViewGroup.LayoutParams.WRAP_CONTENT;
	
	/** The m drop down horizontal offset. */
	private int mDropDownHorizontalOffset;
	
	/** The m drop down vertical offset. */
	private int mDropDownVerticalOffset;
	
	/** The m drop down vertical offset set. */
	private boolean mDropDownVerticalOffsetSet;

	/** The m list item expand maximum. */
	private int mListItemExpandMaximum = Integer.MAX_VALUE;

	/** The m prompt view. */
	private View mPromptView;
	
	/** The m prompt position. */
	private int mPromptPosition = POSITION_PROMPT_ABOVE;

	/** The m observer. */
	private DataSetObserver mObserver;

	/** The m drop down anchor view. */
	private View mDropDownAnchorView;

	/** The m drop down list highlight. */
	private Drawable mDropDownListHighlight;

	/** The m item click listener. */
	private AdapterView.OnItemClickListener mItemClickListener;
	
	/** The m item selected listener. */
	private AdapterView.OnItemSelectedListener mItemSelectedListener;

	/** The m resize popup runnable. */
	private final ResizePopupRunnable mResizePopupRunnable = new ResizePopupRunnable();
	
	/** The m touch interceptor. */
	private final PopupTouchInterceptor mTouchInterceptor = new PopupTouchInterceptor();
	
	/** The m scroll listener. */
	private final PopupScrollListener mScrollListener = new PopupScrollListener();
	
	/** The m hide selector. */
	private final ListSelectorHider mHideSelector = new ListSelectorHider();

	/** The m handler. */
	private Handler mHandler = new Handler();

	/** The m temp rect. */
	private Rect mTempRect = new Rect();

	/** The m modal. */
	private boolean mModal;

	/** The Constant POSITION_PROMPT_ABOVE. */
	public static final int POSITION_PROMPT_ABOVE = 0;
	
	/** The Constant POSITION_PROMPT_BELOW. */
	public static final int POSITION_PROMPT_BELOW = 1;

	/**
	 * Instantiates a new ics list popup window.
	 * 
	 * @param context
	 *            the context
	 */
	public IcsListPopupWindow(Context context) {
		this(context, null, R.attr.listPopupWindowStyle);
	}

	/**
	 * Instantiates a new ics list popup window.
	 * 
	 * @param context
	 *            the context
	 * @param attrs
	 *            the attrs
	 * @param defStyleAttr
	 *            the def style attr
	 */
	public IcsListPopupWindow(Context context, AttributeSet attrs,
			int defStyleAttr) {
		mContext = context;
		mPopup = new PopupWindow(context, attrs, defStyleAttr);
		mPopup.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
	}

	/**
	 * Instantiates a new ics list popup window.
	 * 
	 * @param context
	 *            the context
	 * @param attrs
	 *            the attrs
	 * @param defStyleAttr
	 *            the def style attr
	 * @param defStyleRes
	 *            the def style res
	 */
	public IcsListPopupWindow(Context context, AttributeSet attrs,
			int defStyleAttr, int defStyleRes) {
		mContext = context;
		if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
			Context wrapped = new ContextThemeWrapper(context, defStyleRes);
			mPopup = new PopupWindow(wrapped, attrs, defStyleAttr);
		} else {
			mPopup = new PopupWindow(context, attrs, defStyleAttr, defStyleRes);
		}
		mPopup.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
	}

	/**
	 * Sets the adapter.
	 * 
	 * @param adapter
	 *            the new adapter
	 */
	public void setAdapter(ListAdapter adapter) {
		if (mObserver == null) {
			mObserver = new PopupDataSetObserver();
		} else if (mAdapter != null) {
			mAdapter.unregisterDataSetObserver(mObserver);
		}
		mAdapter = adapter;
		if (mAdapter != null) {
			adapter.registerDataSetObserver(mObserver);
		}

		if (mDropDownList != null) {
			mDropDownList.setAdapter(mAdapter);
		}
	}

	/**
	 * Sets the prompt position.
	 * 
	 * @param position
	 *            the new prompt position
	 */
	public void setPromptPosition(int position) {
		mPromptPosition = position;
	}

	/**
	 * Sets the modal.
	 * 
	 * @param modal
	 *            the new modal
	 */
	public void setModal(boolean modal) {
		mModal = true;
		mPopup.setFocusable(modal);
	}

	/**
	 * Sets the background drawable.
	 * 
	 * @param d
	 *            the new background drawable
	 */
	public void setBackgroundDrawable(Drawable d) {
		mPopup.setBackgroundDrawable(d);
	}

	/**
	 * Sets the anchor view.
	 * 
	 * @param anchor
	 *            the new anchor view
	 */
	public void setAnchorView(View anchor) {
		mDropDownAnchorView = anchor;
	}

	/**
	 * Sets the horizontal offset.
	 * 
	 * @param offset
	 *            the new horizontal offset
	 */
	public void setHorizontalOffset(int offset) {
		mDropDownHorizontalOffset = offset;
	}

	/**
	 * Sets the vertical offset.
	 * 
	 * @param offset
	 *            the new vertical offset
	 */
	public void setVerticalOffset(int offset) {
		mDropDownVerticalOffset = offset;
		mDropDownVerticalOffsetSet = true;
	}

	/**
	 * Sets the content width.
	 * 
	 * @param width
	 *            the new content width
	 */
	public void setContentWidth(int width) {
		Drawable popupBackground = mPopup.getBackground();
		if (popupBackground != null) {
			popupBackground.getPadding(mTempRect);
			mDropDownWidth = mTempRect.left + mTempRect.right + width;
		} else {
			mDropDownWidth = width;
		}
	}

	/**
	 * Sets the on item click listener.
	 * 
	 * @param clickListener
	 *            the new on item click listener
	 */
	public void setOnItemClickListener(
			AdapterView.OnItemClickListener clickListener) {
		mItemClickListener = clickListener;
	}

	/**
	 * Show.
	 */
	public void show() {
		int height = buildDropDown();

		int widthSpec = 0;
		int heightSpec = 0;

		boolean noInputMethod = isInputMethodNotNeeded();
		// XXX mPopup.setAllowScrollingAnchorParent(!noInputMethod);

		if (mPopup.isShowing()) {
			if (mDropDownWidth == ViewGroup.LayoutParams.MATCH_PARENT) {
				// The call to PopupWindow's update method below can accept -1
				// for any
				// value you do not want to update.
				widthSpec = -1;
			} else if (mDropDownWidth == ViewGroup.LayoutParams.WRAP_CONTENT) {
				widthSpec = mDropDownAnchorView.getWidth();
			} else {
				widthSpec = mDropDownWidth;
			}

			if (mDropDownHeight == ViewGroup.LayoutParams.MATCH_PARENT) {
				// The call to PopupWindow's update method below can accept -1
				// for any
				// value you do not want to update.
				heightSpec = noInputMethod ? height
						: ViewGroup.LayoutParams.MATCH_PARENT;
				if (noInputMethod) {
					mPopup.setWindowLayoutMode(
							mDropDownWidth == ViewGroup.LayoutParams.MATCH_PARENT ? ViewGroup.LayoutParams.MATCH_PARENT
									: 0, 0);
				} else {
					mPopup.setWindowLayoutMode(
							mDropDownWidth == ViewGroup.LayoutParams.MATCH_PARENT ? ViewGroup.LayoutParams.MATCH_PARENT
									: 0, ViewGroup.LayoutParams.MATCH_PARENT);
				}
			} else if (mDropDownHeight == ViewGroup.LayoutParams.WRAP_CONTENT) {
				heightSpec = height;
			} else {
				heightSpec = mDropDownHeight;
			}

			mPopup.setOutsideTouchable(true);

			mPopup.update(mDropDownAnchorView, mDropDownHorizontalOffset,
					mDropDownVerticalOffset, widthSpec, heightSpec);
		} else {
			if (mDropDownWidth == ViewGroup.LayoutParams.MATCH_PARENT) {
				widthSpec = ViewGroup.LayoutParams.MATCH_PARENT;
			} else {
				if (mDropDownWidth == ViewGroup.LayoutParams.WRAP_CONTENT) {
					mPopup.setWidth(mDropDownAnchorView.getWidth());
				} else {
					mPopup.setWidth(mDropDownWidth);
				}
			}

			if (mDropDownHeight == ViewGroup.LayoutParams.MATCH_PARENT) {
				heightSpec = ViewGroup.LayoutParams.MATCH_PARENT;
			} else {
				if (mDropDownHeight == ViewGroup.LayoutParams.WRAP_CONTENT) {
					mPopup.setHeight(height);
				} else {
					mPopup.setHeight(mDropDownHeight);
				}
			}

			mPopup.setWindowLayoutMode(widthSpec, heightSpec);
			// XXX mPopup.setClipToScreenEnabled(true);

			// use outside touchable to dismiss drop down when touching outside
			// of it, so
			// only set this if the dropdown is not always visible
			mPopup.setOutsideTouchable(true);
			mPopup.setTouchInterceptor(mTouchInterceptor);
			mPopup.showAsDropDown(mDropDownAnchorView,
					mDropDownHorizontalOffset, mDropDownVerticalOffset);
			mDropDownList.setSelection(AdapterView.INVALID_POSITION);

			if (!mModal || mDropDownList.isInTouchMode()) {
				clearListSelection();
			}
			if (!mModal) {
				mHandler.post(mHideSelector);
			}
		}
	}

	/**
	 * Dismiss.
	 */
	public void dismiss() {
		mPopup.dismiss();
		if (mPromptView != null) {
			final ViewParent parent = mPromptView.getParent();
			if (parent instanceof ViewGroup) {
				final ViewGroup group = (ViewGroup) parent;
				group.removeView(mPromptView);
			}
		}
		mPopup.setContentView(null);
		mDropDownList = null;
		mHandler.removeCallbacks(mResizePopupRunnable);
	}

	/**
	 * Sets the on dismiss listener.
	 * 
	 * @param listener
	 *            the new on dismiss listener
	 */
	public void setOnDismissListener(PopupWindow.OnDismissListener listener) {
		mPopup.setOnDismissListener(listener);
	}

	/**
	 * Sets the input method mode.
	 * 
	 * @param mode
	 *            the new input method mode
	 */
	public void setInputMethodMode(int mode) {
		mPopup.setInputMethodMode(mode);
	}

	/**
	 * Clear list selection.
	 */
	public void clearListSelection() {
		final DropDownListView list = mDropDownList;
		if (list != null) {
			// WARNING: Please read the comment where mListSelectionHidden is
			// declared
			list.mListSelectionHidden = true;
			// XXX list.hideSelector();
			list.requestLayout();
		}
	}

	/**
	 * Checks if is showing.
	 * 
	 * @return true, if is showing
	 */
	public boolean isShowing() {
		return mPopup.isShowing();
	}

	/**
	 * Checks if is input method not needed.
	 * 
	 * @return true, if is input method not needed
	 */
	private boolean isInputMethodNotNeeded() {
		return mPopup.getInputMethodMode() == PopupWindow.INPUT_METHOD_NOT_NEEDED;
	}

	/**
	 * Gets the list view.
	 * 
	 * @return the list view
	 */
	public ListView getListView() {
		return mDropDownList;
	}

	/**
	 * Builds the drop down.
	 * 
	 * @return the int
	 */
	private int buildDropDown() {
		ViewGroup dropDownView;
		int otherHeights = 0;

		if (mDropDownList == null) {
			Context context = mContext;

			mDropDownList = new DropDownListView(context, !mModal);
			if (mDropDownListHighlight != null) {
				mDropDownList.setSelector(mDropDownListHighlight);
			}
			mDropDownList.setAdapter(mAdapter);
			mDropDownList.setOnItemClickListener(mItemClickListener);
			mDropDownList.setFocusable(true);
			mDropDownList.setFocusableInTouchMode(true);
			mDropDownList
					.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
						@Override
						public void onItemSelected(AdapterView<?> parent,
								View view, int position, long id) {

							if (position != -1) {
								DropDownListView dropDownList = mDropDownList;

								if (dropDownList != null) {
									dropDownList.mListSelectionHidden = false;
								}
							}
						}

						@Override
						public void onNothingSelected(AdapterView<?> parent) {
						}
					});
			mDropDownList.setOnScrollListener(mScrollListener);

			if (mItemSelectedListener != null) {
				mDropDownList.setOnItemSelectedListener(mItemSelectedListener);
			}

			dropDownView = mDropDownList;

			View hintView = mPromptView;
			if (hintView != null) {
				// if an hint has been specified, we accomodate more space for
				// it and
				// add a text view in the drop down menu, at the bottom of the
				// list
				LinearLayout hintContainer = new LinearLayout(context);
				hintContainer.setOrientation(LinearLayout.VERTICAL);

				LinearLayout.LayoutParams hintParams = new LinearLayout.LayoutParams(
						ViewGroup.LayoutParams.MATCH_PARENT, 0, 1.0f);

				switch (mPromptPosition) {
				case POSITION_PROMPT_BELOW:
					hintContainer.addView(dropDownView, hintParams);
					hintContainer.addView(hintView);
					break;

				case POSITION_PROMPT_ABOVE:
					hintContainer.addView(hintView);
					hintContainer.addView(dropDownView, hintParams);
					break;

				default:
					break;
				}

				// measure the hint's height to find how much more vertical
				// space
				// we need to add to the drop down's height
				int widthSpec = MeasureSpec.makeMeasureSpec(mDropDownWidth,
						MeasureSpec.AT_MOST);
				int heightSpec = MeasureSpec.UNSPECIFIED;
				hintView.measure(widthSpec, heightSpec);

				hintParams = (LinearLayout.LayoutParams) hintView
						.getLayoutParams();
				otherHeights = hintView.getMeasuredHeight()
						+ hintParams.topMargin + hintParams.bottomMargin;

				dropDownView = hintContainer;
			}

			mPopup.setContentView(dropDownView);
		} else {
			dropDownView = (ViewGroup) mPopup.getContentView();
			final View view = mPromptView;
			if (view != null) {
				LinearLayout.LayoutParams hintParams = (LinearLayout.LayoutParams) view
						.getLayoutParams();
				otherHeights = view.getMeasuredHeight() + hintParams.topMargin
						+ hintParams.bottomMargin;
			}
		}

		// getMaxAvailableHeight() subtracts the padding, so we put it back
		// to get the available height for the whole window
		int padding = 0;
		Drawable background = mPopup.getBackground();
		if (background != null) {
			background.getPadding(mTempRect);
			padding = mTempRect.top + mTempRect.bottom;

			// If we don't have an explicit vertical offset, determine one from
			// the window
			// background so that content will line up.
			if (!mDropDownVerticalOffsetSet) {
				mDropDownVerticalOffset = -mTempRect.top;
			}
		}

		// Max height available on the screen for a popup.
		boolean ignoreBottomDecorations = mPopup.getInputMethodMode() == PopupWindow.INPUT_METHOD_NOT_NEEDED;
		final int maxHeight = /* mPopup. */getMaxAvailableHeight(
				mDropDownAnchorView, mDropDownVerticalOffset,
				ignoreBottomDecorations);

		if (mDropDownHeight == ViewGroup.LayoutParams.MATCH_PARENT) {
			return maxHeight + padding;
		}

		final int listContent = /* mDropDownList. */measureHeightOfChildren(
				MeasureSpec.UNSPECIFIED, 0, -1/* ListView.NO_POSITION */,
				maxHeight - otherHeights, -1);
		// add padding only if the list has items in it, that way we don't show
		// the popup if it is not needed
		if (listContent > 0)
			otherHeights += padding;

		return listContent + otherHeights;
	}

	/**
	 * Gets the max available height.
	 * 
	 * @param anchor
	 *            the anchor
	 * @param yOffset
	 *            the y offset
	 * @param ignoreBottomDecorations
	 *            the ignore bottom decorations
	 * @return the max available height
	 */
	private int getMaxAvailableHeight(View anchor, int yOffset,
			boolean ignoreBottomDecorations) {
		final Rect displayFrame = new Rect();
		anchor.getWindowVisibleDisplayFrame(displayFrame);

		final int[] anchorPos = new int[2];
		anchor.getLocationOnScreen(anchorPos);

		int bottomEdge = displayFrame.bottom;
		if (ignoreBottomDecorations) {
			Resources res = anchor.getContext().getResources();
			bottomEdge = res.getDisplayMetrics().heightPixels;
		}
		final int distanceToBottom = bottomEdge
				- (anchorPos[1] + anchor.getHeight()) - yOffset;
		final int distanceToTop = anchorPos[1] - displayFrame.top + yOffset;

		// anchorPos[1] is distance from anchor to top of screen
		int returnedHeight = Math.max(distanceToBottom, distanceToTop);
		if (mPopup.getBackground() != null) {
			mPopup.getBackground().getPadding(mTempRect);
			returnedHeight -= mTempRect.top + mTempRect.bottom;
		}

		return returnedHeight;
	}

	/**
	 * Measure height of children.
	 * 
	 * @param widthMeasureSpec
	 *            the width measure spec
	 * @param startPosition
	 *            the start position
	 * @param endPosition
	 *            the end position
	 * @param maxHeight
	 *            the max height
	 * @param disallowPartialChildPosition
	 *            the disallow partial child position
	 * @return the int
	 */
	private int measureHeightOfChildren(int widthMeasureSpec,
			int startPosition, int endPosition, final int maxHeight,
			int disallowPartialChildPosition) {

		final ListAdapter adapter = mAdapter;
		if (adapter == null) {
			return mDropDownList.getListPaddingTop()
					+ mDropDownList.getListPaddingBottom();
		}

		// Include the padding of the list
		int returnedHeight = mDropDownList.getListPaddingTop()
				+ mDropDownList.getListPaddingBottom();
		final int dividerHeight = ((mDropDownList.getDividerHeight() > 0) && mDropDownList
				.getDivider() != null) ? mDropDownList.getDividerHeight() : 0;
		// The previous height value that was less than maxHeight and contained
		// no partial children
		int prevHeightWithoutPartialChild = 0;
		int i;
		View child;

		// mItemCount - 1 since endPosition parameter is inclusive
		endPosition = (endPosition == -1/* NO_POSITION */) ? adapter.getCount() - 1
				: endPosition;

		for (i = startPosition; i <= endPosition; ++i) {
			child = mAdapter.getView(i, null, mDropDownList);
			if (mDropDownList.getCacheColorHint() != 0) {
				child.setDrawingCacheBackgroundColor(mDropDownList
						.getCacheColorHint());
			}

			measureScrapChild(child, i, widthMeasureSpec);

			if (i > 0) {
				// Count the divider for all but one child
				returnedHeight += dividerHeight;
			}

			returnedHeight += child.getMeasuredHeight();

			if (returnedHeight >= maxHeight) {
				// We went over, figure out which height to return. If
				// returnedHeight > maxHeight,
				// then the i'th position did not fit completely.
				return (disallowPartialChildPosition >= 0) // Disallowing is
															// enabled (> -1)
						&& (i > disallowPartialChildPosition) // We've past the
																// min pos
						&& (prevHeightWithoutPartialChild > 0) // We have a prev
																// height
						&& (returnedHeight != maxHeight) // i'th child did not
															// fit completely
				? prevHeightWithoutPartialChild : maxHeight;
			}

			if ((disallowPartialChildPosition >= 0)
					&& (i >= disallowPartialChildPosition)) {
				prevHeightWithoutPartialChild = returnedHeight;
			}
		}

		// At this point, we went through the range of children, and they each
		// completely fit, so return the returnedHeight
		return returnedHeight;
	}

	/**
	 * Measure scrap child.
	 * 
	 * @param child
	 *            the child
	 * @param position
	 *            the position
	 * @param widthMeasureSpec
	 *            the width measure spec
	 */
	private void measureScrapChild(View child, int position,
			int widthMeasureSpec) {
		ListView.LayoutParams p = (ListView.LayoutParams) child
				.getLayoutParams();
		if (p == null) {
			p = new ListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
					ViewGroup.LayoutParams.WRAP_CONTENT, 0);
			child.setLayoutParams(p);
		}
		// XXX p.viewType = mAdapter.getItemViewType(position);
		// XXX p.forceAdd = true;

		int childWidthSpec = ViewGroup.getChildMeasureSpec(
				widthMeasureSpec,
				mDropDownList.getPaddingLeft()
						+ mDropDownList.getPaddingRight(), p.width);
		int lpHeight = p.height;
		int childHeightSpec;
		if (lpHeight > 0) {
			childHeightSpec = MeasureSpec.makeMeasureSpec(lpHeight,
					MeasureSpec.EXACTLY);
		} else {
			childHeightSpec = MeasureSpec.makeMeasureSpec(0,
					MeasureSpec.UNSPECIFIED);
		}
		child.measure(childWidthSpec, childHeightSpec);
	}

	/**
	 * The Class DropDownListView.
	 */
	private static class DropDownListView extends ListView {
		/*
		 * WARNING: This is a workaround for a touch mode issue.
		 * 
		 * Touch mode is propagated lazily to windows. This causes problems in
		 * the following scenario: - Type something in the AutoCompleteTextView
		 * and get some results - Move down with the d-pad to select an item in
		 * the list - Move up with the d-pad until the selection disappears -
		 * Type more text in the AutoCompleteTextView *using the soft keyboard*
		 * and get new results; you are now in touch mode - The selection comes
		 * back on the first item in the list, even though the list is supposed
		 * to be in touch mode
		 * 
		 * Using the soft keyboard triggers the touch mode change but that
		 * change is propagated to our window only after the first list layout,
		 * therefore after the list attempts to resurrect the selection.
		 * 
		 * The trick to work around this issue is to pretend the list is in
		 * touch mode when we know that the selection should not appear, that is
		 * when we know the user moved the selection away from the list.
		 * 
		 * This boolean is set to true whenever we explicitly hide the list's
		 * selection and reset to false whenever we know the user moved the
		 * selection back to the list.
		 * 
		 * When this boolean is true, isInTouchMode() returns true, otherwise it
		 * returns super.isInTouchMode().
		 */
		/** The m list selection hidden. */
		private boolean mListSelectionHidden;

		/** The m hijack focus. */
		private boolean mHijackFocus;

		/**
		 * Instantiates a new drop down list view.
		 * 
		 * @param context
		 *            the context
		 * @param hijackFocus
		 *            the hijack focus
		 */
		public DropDownListView(Context context, boolean hijackFocus) {
			super(context, null, /* com.android.internal. */
			R.attr.dropDownListViewStyle);
			mHijackFocus = hijackFocus;
			// TODO: Add an API to control this
			setCacheColorHint(0); // Transparent, since the background drawable
									// could be anything.
		}

		// XXX @Override
		// View obtainView(int position, boolean[] isScrap) {
		// View view = super.obtainView(position, isScrap);

		// if (view instanceof TextView) {
		// ((TextView) view).setHorizontallyScrolling(true);
		// }

		// return view;
		// }

		/** 
		* <p>Title: isInTouchMode</p> 
		* <p>Description: </p> 
		* @return 
		* @see android.view.View#isInTouchMode() 
		*/
		@Override
		public boolean isInTouchMode() {
			// WARNING: Please read the comment where mListSelectionHidden is
			// declared
			return (mHijackFocus && mListSelectionHidden)
					|| super.isInTouchMode();
		}

		/** 
		* <p>Title: hasWindowFocus</p> 
		* <p>Description: </p> 
		* @return 
		* @see android.view.View#hasWindowFocus() 
		*/
		@Override
		public boolean hasWindowFocus() {
			return mHijackFocus || super.hasWindowFocus();
		}

		/** 
		* <p>Title: isFocused</p> 
		* <p>Description: </p> 
		* @return 
		* @see android.view.View#isFocused() 
		*/
		@Override
		public boolean isFocused() {
			return mHijackFocus || super.isFocused();
		}

		/** 
		* <p>Title: hasFocus</p> 
		* <p>Description: </p> 
		* @return 
		* @see android.view.ViewGroup#hasFocus() 
		*/
		@Override
		public boolean hasFocus() {
			return mHijackFocus || super.hasFocus();
		}
	}

	/**
	 * An asynchronous update interface for receiving notifications about
	 * PopupDataSet information as the PopupDataSet is constructed.
	 */
	private class PopupDataSetObserver extends DataSetObserver {
		
		/** 
		* <p>Title: onChanged</p> 
		* <p>Description: </p>  
		* @see android.database.DataSetObserver#onChanged() 
		*/
		@Override
		public void onChanged() {
			if (isShowing()) {
				// Resize the popup to fit new content
				show();
			}
		}

		/** 
		* <p>Title: onInvalidated</p> 
		* <p>Description: </p>  
		* @see android.database.DataSetObserver#onInvalidated() 
		*/
		@Override
		public void onInvalidated() {
			dismiss();
		}
	}

	/**
	 * The Class ListSelectorHider.
	 */
	private class ListSelectorHider implements Runnable {
		
		/** 
		* <p>Title: run</p> 
		* <p>Description: </p>  
		* @see java.lang.Runnable#run() 
		*/
		@Override
		public void run() {
			clearListSelection();
		}
	}

	/**
	 * The Class ResizePopupRunnable.
	 */
	private class ResizePopupRunnable implements Runnable {
		
		/** 
		* <p>Title: run</p> 
		* <p>Description: </p>  
		* @see java.lang.Runnable#run() 
		*/
		@Override
		public void run() {
			if (mDropDownList != null
					&& mDropDownList.getCount() > mDropDownList.getChildCount()
					&& mDropDownList.getChildCount() <= mListItemExpandMaximum) {
				mPopup.setInputMethodMode(PopupWindow.INPUT_METHOD_NOT_NEEDED);
				show();
			}
		}
	}

	/**
	 * The Class PopupTouchInterceptor.
	 */
	private class PopupTouchInterceptor implements OnTouchListener {
		
		/** 
		* <p>Title: onTouch</p> 
		* <p>Description: </p> 
		* @param v
		* @param event
		* @return 
		* @see android.view.View.OnTouchListener#onTouch(android.view.View, android.view.MotionEvent) 
		*/
		@Override
		public boolean onTouch(View v, MotionEvent event) {
			final int action = event.getAction();
			final int x = (int) event.getX();
			final int y = (int) event.getY();

			if (action == MotionEvent.ACTION_DOWN
					&& mPopup != null
					&& mPopup.isShowing()
					&& (x >= 0 && x < mPopup.getWidth() && y >= 0 && y < mPopup
							.getHeight())) {
				mHandler.postDelayed(mResizePopupRunnable, EXPAND_LIST_TIMEOUT);
			} else if (action == MotionEvent.ACTION_UP) {
				mHandler.removeCallbacks(mResizePopupRunnable);
			}
			return false;
		}
	}

	/**
	 * The listener interface for receiving popupScroll events. The class that
	 * is interested in processing a popupScroll event implements this
	 * interface, and the object created with that class is registered with a
	 * component using the component's
	 * <code>addPopupScrollListener<code> method. When
	 * the popupScroll event occurs, that object's appropriate
	 * method is invoked.
	 * 
	 * @see PopupScrollEvent
	 */
	private class PopupScrollListener implements ListView.OnScrollListener {
		
		/** 
		* <p>Title: onScroll</p> 
		* <p>Description: </p> 
		* @param view
		* @param firstVisibleItem
		* @param visibleItemCount
		* @param totalItemCount 
		* @see android.widget.AbsListView.OnScrollListener#onScroll(android.widget.AbsListView, int, int, int) 
		*/
		@Override
		public void onScroll(AbsListView view, int firstVisibleItem,
				int visibleItemCount, int totalItemCount) {

		}

		/** 
		* <p>Title: onScrollStateChanged</p> 
		* <p>Description: </p> 
		* @param view
		* @param scrollState 
		* @see android.widget.AbsListView.OnScrollListener#onScrollStateChanged(android.widget.AbsListView, int) 
		*/
		@Override
		public void onScrollStateChanged(AbsListView view, int scrollState) {
			if (scrollState == SCROLL_STATE_TOUCH_SCROLL
					&& !isInputMethodNotNeeded()
					&& mPopup.getContentView() != null) {
				mHandler.removeCallbacks(mResizePopupRunnable);
				mResizePopupRunnable.run();
			}
		}
	}
}
