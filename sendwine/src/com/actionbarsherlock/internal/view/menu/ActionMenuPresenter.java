/**   
* @Title: ActionMenuPresenter.java 
* @Package com.actionbarsherlock.internal.view.menu 
* @Description: 
*			澹版槑:
*			1)鎺屾帶鏍″洯鏄湰浜虹殑澶у鐨勫垱涓氫綔鍝�涔熸槸鏈汉鐨勬瘯涓氳璁�*			2)鏈▼搴忓皻鏈繘琛屽紑鏀炬簮浠ｇ爜,鎵�互绂佹缁勭粐鎴栬�鏄釜浜烘硠闇叉簮鐮�鍚﹀垯灏嗕細杩界┒鍏跺垜浜嬭矗浠�*			3)缂栧啓鏈蒋浠�鎴戦渶瑕佹劅璋㈠江鑰佸笀浠ュ強鍏朵粬榧撳姳鍜屾敮鎸佹垜鐨勫悓瀛︿互鍙婃湅鍙�*			4)鏈▼搴忕殑鏈�粓鎵�湁鏉冨睘浜庢湰浜�
* @author  寮犻浄 794857063@qq.com
* @date 2013-11-14 19:34:25 
* @version V1.0   
*/

package com.actionbarsherlock.internal.view.menu;

import static com.actionbarsherlock.internal.ResourcesCompat.getResources_getInteger;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.SparseBooleanArray;
import android.view.SoundEffectConstants;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.sendwine.app.R;

import com.actionbarsherlock.internal.view.View_HasStateListenerSupport;
import com.actionbarsherlock.internal.view.View_OnAttachStateChangeListener;
import com.actionbarsherlock.internal.view.menu.ActionMenuView.ActionMenuChildView;
import com.actionbarsherlock.view.ActionProvider;
import com.actionbarsherlock.view.MenuItem;

// TODO: Auto-generated Javadoc
/**
 * MenuPresenter for building action menus as seen in the action bar and action
 * modes.
 */
public class ActionMenuPresenter extends BaseMenuPresenter implements
		ActionProvider.SubUiVisibilityListener {
	// UNUSED private static final String TAG = "ActionMenuPresenter";

	/** The m overflow button. */
	private View mOverflowButton;
	
	/** The m reserve overflow. */
	private boolean mReserveOverflow;
	
	/** The m reserve overflow set. */
	private boolean mReserveOverflowSet;
	
	/** The m width limit. */
	private int mWidthLimit;
	
	/** The m action item width limit. */
	private int mActionItemWidthLimit;
	
	/** The m max items. */
	private int mMaxItems;
	
	/** The m max items set. */
	private boolean mMaxItemsSet;
	
	/** The m strict width limit. */
	private boolean mStrictWidthLimit;
	
	/** The m width limit set. */
	private boolean mWidthLimitSet;
	
	/** The m expanded action views exclusive. */
	private boolean mExpandedActionViewsExclusive;

	/** The m min cell size. */
	private int mMinCellSize;

	// Group IDs that have been added as actions - used temporarily, allocated
	// here for reuse.
	/** The m action button groups. */
	private final SparseBooleanArray mActionButtonGroups = new SparseBooleanArray();

	/** The m scrap action button view. */
	private View mScrapActionButtonView;

	/** The m overflow popup. */
	private OverflowPopup mOverflowPopup;
	
	/** The m action button popup. */
	private ActionButtonSubmenu mActionButtonPopup;

	/** The m posted open runnable. */
	private OpenOverflowRunnable mPostedOpenRunnable;

	/** The m popup presenter callback. */
	final PopupPresenterCallback mPopupPresenterCallback = new PopupPresenterCallback();
	
	/** The m open sub menu id. */
	int mOpenSubMenuId;

	/**
	 * Instantiates a new action menu presenter.
	 * 
	 * @param context
	 *            the context
	 */
	public ActionMenuPresenter(Context context) {
		super(context, R.layout.abs__action_menu_layout,
				R.layout.abs__action_menu_item_layout);
	}

	/** 
	* <p>Title: initForMenu</p> 
	* <p>Description: </p> 
	* @param context
	* @param menu 
	* @see com.actionbarsherlock.internal.view.menu.BaseMenuPresenter#initForMenu(android.content.Context, com.actionbarsherlock.internal.view.menu.MenuBuilder) 
	*/
	@Override
	public void initForMenu(Context context, MenuBuilder menu) {
		super.initForMenu(context, menu);

		final Resources res = context.getResources();

		if (!mReserveOverflowSet) {
			mReserveOverflow = reserveOverflow(mContext);
		}

		if (!mWidthLimitSet) {
			mWidthLimit = res.getDisplayMetrics().widthPixels / 2;
		}

		// Measure for initial configuration
		if (!mMaxItemsSet) {
			mMaxItems = getResources_getInteger(context,
					R.integer.abs__max_action_buttons);
		}

		int width = mWidthLimit;
		if (mReserveOverflow) {
			if (mOverflowButton == null) {
				mOverflowButton = new OverflowMenuButton(mSystemContext);
				final int spec = MeasureSpec.makeMeasureSpec(0,
						MeasureSpec.UNSPECIFIED);
				mOverflowButton.measure(spec, spec);
			}
			width -= mOverflowButton.getMeasuredWidth();
		} else {
			mOverflowButton = null;
		}

		mActionItemWidthLimit = width;

		mMinCellSize = (int) (ActionMenuView.MIN_CELL_SIZE * res
				.getDisplayMetrics().density);

		// Drop a scrap view as it may no longer reflect the proper
		// context/config.
		mScrapActionButtonView = null;
	}

	/**
	 * Reserve overflow.
	 * 
	 * @param context
	 *            the context
	 * @return true, if successful
	 */
	public static boolean reserveOverflow(Context context) {
		if (Build.VERSION.SDK_INT < Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
			return (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB);
		} else {
			return !HasPermanentMenuKey.get(context);
		}
	}

	/**
	 * The Class HasPermanentMenuKey.
	 */
	private static class HasPermanentMenuKey {
		
		/**
		 * Gets the.
		 * 
		 * @param context
		 *            the context
		 * @return true, if successful
		 */
		public static boolean get(Context context) {
			return ViewConfiguration.get(context).hasPermanentMenuKey();
		}
	}

	/**
	 * On configuration changed.
	 * 
	 * @param newConfig
	 *            the new config
	 */
	public void onConfigurationChanged(Configuration newConfig) {
		if (!mMaxItemsSet) {
			mMaxItems = getResources_getInteger(mContext,
					R.integer.abs__max_action_buttons);
			if (mMenu != null) {
				mMenu.onItemsChanged(true);
			}
		}
	}

	/**
	 * Sets the width limit.
	 * 
	 * @param width
	 *            the width
	 * @param strict
	 *            the strict
	 */
	public void setWidthLimit(int width, boolean strict) {
		mWidthLimit = width;
		mStrictWidthLimit = strict;
		mWidthLimitSet = true;
	}

	/**
	 * Sets the reserve overflow.
	 * 
	 * @param reserveOverflow
	 *            the new reserve overflow
	 */
	public void setReserveOverflow(boolean reserveOverflow) {
		mReserveOverflow = reserveOverflow;
		mReserveOverflowSet = true;
	}

	/**
	 * Sets the item limit.
	 * 
	 * @param itemCount
	 *            the new item limit
	 */
	public void setItemLimit(int itemCount) {
		mMaxItems = itemCount;
		mMaxItemsSet = true;
	}

	/**
	 * Sets the expanded action views exclusive.
	 * 
	 * @param isExclusive
	 *            the new expanded action views exclusive
	 */
	public void setExpandedActionViewsExclusive(boolean isExclusive) {
		mExpandedActionViewsExclusive = isExclusive;
	}

	/** 
	* <p>Title: getMenuView</p> 
	* <p>Description: </p> 
	* @param root
	* @return 
	* @see com.actionbarsherlock.internal.view.menu.BaseMenuPresenter#getMenuView(android.view.ViewGroup) 
	*/
	@Override
	public MenuView getMenuView(ViewGroup root) {
		MenuView result = super.getMenuView(root);
		((ActionMenuView) result).setPresenter(this);
		return result;
	}

	/** 
	* <p>Title: getItemView</p> 
	* <p>Description: </p> 
	* @param item
	* @param convertView
	* @param parent
	* @return 
	* @see com.actionbarsherlock.internal.view.menu.BaseMenuPresenter#getItemView(com.actionbarsherlock.internal.view.menu.MenuItemImpl, android.view.View, android.view.ViewGroup) 
	*/
	@Override
	public View getItemView(MenuItemImpl item, View convertView,
			ViewGroup parent) {
		View actionView = item.getActionView();
		if (actionView == null || item.hasCollapsibleActionView()) {
			if (!(convertView instanceof ActionMenuItemView)) {
				convertView = null;
			}
			actionView = super.getItemView(item, convertView, parent);
		}
		actionView.setVisibility(item.isActionViewExpanded() ? View.GONE
				: View.VISIBLE);

		final ActionMenuView menuParent = (ActionMenuView) parent;
		final ViewGroup.LayoutParams lp = actionView.getLayoutParams();
		if (!menuParent.checkLayoutParams(lp)) {
			actionView.setLayoutParams(menuParent.generateLayoutParams(lp));
		}
		return actionView;
	}

	/** 
	* <p>Title: bindItemView</p> 
	* <p>Description: </p> 
	* @param item
	* @param itemView 
	* @see com.actionbarsherlock.internal.view.menu.BaseMenuPresenter#bindItemView(com.actionbarsherlock.internal.view.menu.MenuItemImpl, com.actionbarsherlock.internal.view.menu.MenuView.ItemView) 
	*/
	@Override
	public void bindItemView(MenuItemImpl item, MenuView.ItemView itemView) {
		itemView.initialize(item, 0);

		final ActionMenuView menuView = (ActionMenuView) mMenuView;
		ActionMenuItemView actionItemView = (ActionMenuItemView) itemView;
		actionItemView.setItemInvoker(menuView);
	}

	/** 
	* <p>Title: shouldIncludeItem</p> 
	* <p>Description: </p> 
	* @param childIndex
	* @param item
	* @return 
	* @see com.actionbarsherlock.internal.view.menu.BaseMenuPresenter#shouldIncludeItem(int, com.actionbarsherlock.internal.view.menu.MenuItemImpl) 
	*/
	@Override
	public boolean shouldIncludeItem(int childIndex, MenuItemImpl item) {
		return item.isActionButton();
	}

	/** 
	* <p>Title: updateMenuView</p> 
	* <p>Description: </p> 
	* @param cleared 
	* @see com.actionbarsherlock.internal.view.menu.BaseMenuPresenter#updateMenuView(boolean) 
	*/
	@Override
	public void updateMenuView(boolean cleared) {
		super.updateMenuView(cleared);

		if (mMenu != null) {
			final ArrayList<MenuItemImpl> actionItems = mMenu.getActionItems();
			final int count = actionItems.size();
			for (int i = 0; i < count; i++) {
				final ActionProvider provider = actionItems.get(i)
						.getActionProvider();
				if (provider != null) {
					provider.setSubUiVisibilityListener(this);
				}
			}
		}

		final ArrayList<MenuItemImpl> nonActionItems = mMenu != null ? mMenu
				.getNonActionItems() : null;

		boolean hasOverflow = false;
		if (mReserveOverflow && nonActionItems != null) {
			final int count = nonActionItems.size();
			if (count == 1) {
				hasOverflow = !nonActionItems.get(0).isActionViewExpanded();
			} else {
				hasOverflow = count > 0;
			}
		}

		if (hasOverflow) {
			if (mOverflowButton == null) {
				mOverflowButton = new OverflowMenuButton(mSystemContext);
			}
			ViewGroup parent = (ViewGroup) mOverflowButton.getParent();
			if (parent != mMenuView) {
				if (parent != null) {
					parent.removeView(mOverflowButton);
				}
				ActionMenuView menuView = (ActionMenuView) mMenuView;
				menuView.addView(mOverflowButton,
						menuView.generateOverflowButtonLayoutParams());
			}
		} else if (mOverflowButton != null
				&& mOverflowButton.getParent() == mMenuView) {
			((ViewGroup) mMenuView).removeView(mOverflowButton);
		}

		((ActionMenuView) mMenuView).setOverflowReserved(mReserveOverflow);
	}

	/** 
	* <p>Title: filterLeftoverView</p> 
	* <p>Description: </p> 
	* @param parent
	* @param childIndex
	* @return 
	* @see com.actionbarsherlock.internal.view.menu.BaseMenuPresenter#filterLeftoverView(android.view.ViewGroup, int) 
	*/
	@Override
	public boolean filterLeftoverView(ViewGroup parent, int childIndex) {
		if (parent.getChildAt(childIndex) == mOverflowButton)
			return false;
		return super.filterLeftoverView(parent, childIndex);
	}

	/** 
	* <p>Title: onSubMenuSelected</p> 
	* <p>Description: </p> 
	* @param subMenu
	* @return 
	* @see com.actionbarsherlock.internal.view.menu.BaseMenuPresenter#onSubMenuSelected(com.actionbarsherlock.internal.view.menu.SubMenuBuilder) 
	*/
	@Override
	public boolean onSubMenuSelected(SubMenuBuilder subMenu) {
		if (!subMenu.hasVisibleItems())
			return false;

		SubMenuBuilder topSubMenu = subMenu;
		while (topSubMenu.getParentMenu() != mMenu) {
			topSubMenu = (SubMenuBuilder) topSubMenu.getParentMenu();
		}
		View anchor = findViewForItem(topSubMenu.getItem());
		if (anchor == null) {
			if (mOverflowButton == null)
				return false;
			anchor = mOverflowButton;
		}

		mOpenSubMenuId = subMenu.getItem().getItemId();
		mActionButtonPopup = new ActionButtonSubmenu(mContext, subMenu);
		mActionButtonPopup.setAnchorView(anchor);
		mActionButtonPopup.show();
		super.onSubMenuSelected(subMenu);
		return true;
	}

	/**
	 * Find view for item.
	 * 
	 * @param item
	 *            the item
	 * @return the view
	 */
	private View findViewForItem(MenuItem item) {
		final ViewGroup parent = (ViewGroup) mMenuView;
		if (parent == null)
			return null;

		final int count = parent.getChildCount();
		for (int i = 0; i < count; i++) {
			final View child = parent.getChildAt(i);
			if (child instanceof MenuView.ItemView
					&& ((MenuView.ItemView) child).getItemData() == item) {
				return child;
			}
		}
		return null;
	}

	/**
	 * Display the overflow menu if one is present.
	 * 
	 * @return true if the overflow menu was shown, false otherwise.
	 */
	public boolean showOverflowMenu() {
		if (mReserveOverflow && !isOverflowMenuShowing() && mMenu != null
				&& mMenuView != null && mPostedOpenRunnable == null
				&& !mMenu.getNonActionItems().isEmpty()) {
			OverflowPopup popup = new OverflowPopup(mContext, mMenu,
					mOverflowButton, true);
			mPostedOpenRunnable = new OpenOverflowRunnable(popup);
			// Post this for later; we might still need a layout for the anchor
			// to be right.
			((View) mMenuView).post(mPostedOpenRunnable);

			// ActionMenuPresenter uses null as a callback argument here
			// to indicate overflow is opening.
			super.onSubMenuSelected(null);

			return true;
		}
		return false;
	}

	/**
	 * Hide the overflow menu if it is currently showing.
	 * 
	 * @return true if the overflow menu was hidden, false otherwise.
	 */
	public boolean hideOverflowMenu() {
		if (mPostedOpenRunnable != null && mMenuView != null) {
			((View) mMenuView).removeCallbacks(mPostedOpenRunnable);
			mPostedOpenRunnable = null;
			return true;
		}

		MenuPopupHelper popup = mOverflowPopup;
		if (popup != null) {
			popup.dismiss();
			return true;
		}
		return false;
	}

	/**
	 * Dismiss all popup menus - overflow and submenus.
	 * 
	 * @return true if popups were dismissed, false otherwise. (This can be
	 *         because none were open.)
	 */
	public boolean dismissPopupMenus() {
		boolean result = hideOverflowMenu();
		result |= hideSubMenus();
		return result;
	}

	/**
	 * Dismiss all submenu popups.
	 * 
	 * @return true if popups were dismissed, false otherwise. (This can be
	 *         because none were open.)
	 */
	public boolean hideSubMenus() {
		if (mActionButtonPopup != null) {
			mActionButtonPopup.dismiss();
			return true;
		}
		return false;
	}

	/**
	 * Checks if is overflow menu showing.
	 * 
	 * @return true if the overflow menu is currently showing
	 */
	public boolean isOverflowMenuShowing() {
		return mOverflowPopup != null && mOverflowPopup.isShowing();
	}

	/**
	 * Checks if is overflow reserved.
	 * 
	 * @return true if space has been reserved in the action menu for an
	 *         overflow item.
	 */
	public boolean isOverflowReserved() {
		return mReserveOverflow;
	}

	/** 
	* <p>Title: flagActionItems</p> 
	* <p>Description: </p> 
	* @return 
	* @see com.actionbarsherlock.internal.view.menu.BaseMenuPresenter#flagActionItems() 
	*/
	@Override
	public boolean flagActionItems() {
		final ArrayList<MenuItemImpl> visibleItems = mMenu.getVisibleItems();
		final int itemsSize = visibleItems.size();
		int maxActions = mMaxItems;
		int widthLimit = mActionItemWidthLimit;
		final int querySpec = MeasureSpec.makeMeasureSpec(0,
				MeasureSpec.UNSPECIFIED);
		final ViewGroup parent = (ViewGroup) mMenuView;

		int requiredItems = 0;
		int requestedItems = 0;
		int firstActionWidth = 0;
		boolean hasOverflow = false;
		for (int i = 0; i < itemsSize; i++) {
			MenuItemImpl item = visibleItems.get(i);
			if (item.requiresActionButton()) {
				requiredItems++;
			} else if (item.requestsActionButton()) {
				requestedItems++;
			} else {
				hasOverflow = true;
			}
			if (mExpandedActionViewsExclusive && item.isActionViewExpanded()) {
				// Overflow everything if we have an expanded action view and
				// we're
				// space constrained.
				maxActions = 0;
			}
		}

		// Reserve a spot for the overflow item if needed.
		if (mReserveOverflow
				&& (hasOverflow || requiredItems + requestedItems > maxActions)) {
			maxActions--;
		}
		maxActions -= requiredItems;

		final SparseBooleanArray seenGroups = mActionButtonGroups;
		seenGroups.clear();

		int cellSize = 0;
		int cellsRemaining = 0;
		if (mStrictWidthLimit) {
			cellsRemaining = widthLimit / mMinCellSize;
			final int cellSizeRemaining = widthLimit % mMinCellSize;
			cellSize = mMinCellSize + cellSizeRemaining / cellsRemaining;
		}

		// Flag as many more requested items as will fit.
		for (int i = 0; i < itemsSize; i++) {
			MenuItemImpl item = visibleItems.get(i);

			if (item.requiresActionButton()) {
				View v = getItemView(item, mScrapActionButtonView, parent);
				if (mScrapActionButtonView == null) {
					mScrapActionButtonView = v;
				}
				if (mStrictWidthLimit) {
					cellsRemaining -= ActionMenuView.measureChildForCells(v,
							cellSize, cellsRemaining, querySpec, 0);
				} else {
					v.measure(querySpec, querySpec);
				}
				final int measuredWidth = v.getMeasuredWidth();
				widthLimit -= measuredWidth;
				if (firstActionWidth == 0) {
					firstActionWidth = measuredWidth;
				}
				final int groupId = item.getGroupId();
				if (groupId != 0) {
					seenGroups.put(groupId, true);
				}
				item.setIsActionButton(true);
			} else if (item.requestsActionButton()) {
				// Items in a group with other items that already have an action
				// slot
				// can break the max actions rule, but not the width limit.
				final int groupId = item.getGroupId();
				final boolean inGroup = seenGroups.get(groupId);
				boolean isAction = (maxActions > 0 || inGroup)
						&& widthLimit > 0
						&& (!mStrictWidthLimit || cellsRemaining > 0);

				if (isAction) {
					View v = getItemView(item, mScrapActionButtonView, parent);
					if (mScrapActionButtonView == null) {
						mScrapActionButtonView = v;
					}
					if (mStrictWidthLimit) {
						final int cells = ActionMenuView.measureChildForCells(
								v, cellSize, cellsRemaining, querySpec, 0);
						cellsRemaining -= cells;
						if (cells == 0) {
							isAction = false;
						}
					} else {
						v.measure(querySpec, querySpec);
					}
					final int measuredWidth = v.getMeasuredWidth();
					widthLimit -= measuredWidth;
					if (firstActionWidth == 0) {
						firstActionWidth = measuredWidth;
					}

					if (mStrictWidthLimit) {
						isAction &= widthLimit >= 0;
					} else {
						// Did this push the entire first item past the limit?
						isAction &= widthLimit + firstActionWidth > 0;
					}
				}

				if (isAction && groupId != 0) {
					seenGroups.put(groupId, true);
				} else if (inGroup) {
					// We broke the width limit. Demote the whole group, they
					// all overflow now.
					seenGroups.put(groupId, false);
					for (int j = 0; j < i; j++) {
						MenuItemImpl areYouMyGroupie = visibleItems.get(j);
						if (areYouMyGroupie.getGroupId() == groupId) {
							// Give back the action slot
							if (areYouMyGroupie.isActionButton())
								maxActions++;
							areYouMyGroupie.setIsActionButton(false);
						}
					}
				}

				if (isAction)
					maxActions--;

				item.setIsActionButton(isAction);
			}
		}
		return true;
	}

	/** 
	* <p>Title: onCloseMenu</p> 
	* <p>Description: </p> 
	* @param menu
	* @param allMenusAreClosing 
	* @see com.actionbarsherlock.internal.view.menu.BaseMenuPresenter#onCloseMenu(com.actionbarsherlock.internal.view.menu.MenuBuilder, boolean) 
	*/
	@Override
	public void onCloseMenu(MenuBuilder menu, boolean allMenusAreClosing) {
		dismissPopupMenus();
		super.onCloseMenu(menu, allMenusAreClosing);
	}

	/** 
	* <p>Title: onSaveInstanceState</p> 
	* <p>Description: </p> 
	* @return 
	* @see com.actionbarsherlock.internal.view.menu.MenuPresenter#onSaveInstanceState() 
	*/
	@Override
	public Parcelable onSaveInstanceState() {
		SavedState state = new SavedState();
		state.openSubMenuId = mOpenSubMenuId;
		return state;
	}

	/** 
	* <p>Title: onRestoreInstanceState</p> 
	* <p>Description: </p> 
	* @param state 
	* @see com.actionbarsherlock.internal.view.menu.MenuPresenter#onRestoreInstanceState(android.os.Parcelable) 
	*/
	@Override
	public void onRestoreInstanceState(Parcelable state) {
		SavedState saved = (SavedState) state;
		if (saved.openSubMenuId > 0) {
			MenuItem item = mMenu.findItem(saved.openSubMenuId);
			if (item != null) {
				SubMenuBuilder subMenu = (SubMenuBuilder) item.getSubMenu();
				onSubMenuSelected(subMenu);
			}
		}
	}

	/** 
	* <p>Title: onSubUiVisibilityChanged</p> 
	* <p>Description: </p> 
	* @param isVisible 
	* @see com.actionbarsherlock.view.ActionProvider.SubUiVisibilityListener#onSubUiVisibilityChanged(boolean) 
	*/
	@Override
	public void onSubUiVisibilityChanged(boolean isVisible) {
		if (isVisible) {
			// Not a submenu, but treat it like one.
			super.onSubMenuSelected(null);
		} else {
			mMenu.close(false);
		}
	}

	/**
	 * The Class SavedState.
	 */
	private static class SavedState implements Parcelable {
		
		/** The open sub menu id. */
		public int openSubMenuId;

		/**
		 * Instantiates a new saved state.
		 */
		SavedState() {
		}

		/**
		 * Instantiates a new saved state.
		 * 
		 * @param in
		 *            the in
		 */
		SavedState(Parcel in) {
			openSubMenuId = in.readInt();
		}

		/** 
		* <p>Title: describeContents</p> 
		* <p>Description: </p> 
		* @return 
		* @see android.os.Parcelable#describeContents() 
		*/
		@Override
		public int describeContents() {
			return 0;
		}

		/** 
		* <p>Title: writeToParcel</p> 
		* <p>Description: </p> 
		* @param dest
		* @param flags 
		* @see android.os.Parcelable#writeToParcel(android.os.Parcel, int) 
		*/
		@Override
		public void writeToParcel(Parcel dest, int flags) {
			dest.writeInt(openSubMenuId);
		}

		/** The Constant CREATOR. */
		@SuppressWarnings("unused")
		public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator<SavedState>() {
			@Override
			public SavedState createFromParcel(Parcel in) {
				return new SavedState(in);
			}

			@Override
			public SavedState[] newArray(int size) {
				return new SavedState[size];
			}
		};
	}

	/**
	 * The Class OverflowMenuButton.
	 */
	private class OverflowMenuButton extends ImageButton implements
			ActionMenuChildView, View_HasStateListenerSupport {
		
		/** The m listeners. */
		private final Set<View_OnAttachStateChangeListener> mListeners = new HashSet<View_OnAttachStateChangeListener>();

		/**
		 * Instantiates a new overflow menu button.
		 * 
		 * @param context
		 *            the context
		 */
		public OverflowMenuButton(Context context) {
			super(context, null, R.attr.actionOverflowButtonStyle);

			setClickable(true);
			setFocusable(true);
			setVisibility(VISIBLE);
			setEnabled(true);
		}

		/** 
		* <p>Title: performClick</p> 
		* <p>Description: </p> 
		* @return 
		* @see android.view.View#performClick() 
		*/
		@Override
		public boolean performClick() {
			if (super.performClick()) {
				return true;
			}

			playSoundEffect(SoundEffectConstants.CLICK);
			showOverflowMenu();
			return true;
		}

		/** 
		* <p>Title: needsDividerBefore</p> 
		* <p>Description: </p> 
		* @return 
		* @see com.actionbarsherlock.internal.view.menu.ActionMenuView.ActionMenuChildView#needsDividerBefore() 
		*/
		@Override
		public boolean needsDividerBefore() {
			return false;
		}

		/** 
		* <p>Title: needsDividerAfter</p> 
		* <p>Description: </p> 
		* @return 
		* @see com.actionbarsherlock.internal.view.menu.ActionMenuView.ActionMenuChildView#needsDividerAfter() 
		*/
		@Override
		public boolean needsDividerAfter() {
			return false;
		}

		/** 
		* <p>Title: onAttachedToWindow</p> 
		* <p>Description: </p>  
		* @see android.view.View#onAttachedToWindow() 
		*/
		@Override
		protected void onAttachedToWindow() {
			super.onAttachedToWindow();
			for (View_OnAttachStateChangeListener listener : mListeners) {
				listener.onViewAttachedToWindow(this);
			}
		}

		/** 
		* <p>Title: onDetachedFromWindow</p> 
		* <p>Description: </p>  
		* @see android.view.View#onDetachedFromWindow() 
		*/
		@Override
		protected void onDetachedFromWindow() {
			super.onDetachedFromWindow();
			for (View_OnAttachStateChangeListener listener : mListeners) {
				listener.onViewDetachedFromWindow(this);
			}

			if (mOverflowPopup != null)
				mOverflowPopup.dismiss();
		}

		/** 
		* <p>Title: addOnAttachStateChangeListener</p> 
		* <p>Description: </p> 
		* @param listener 
		* @see com.actionbarsherlock.internal.view.View_HasStateListenerSupport#addOnAttachStateChangeListener(com.actionbarsherlock.internal.view.View_OnAttachStateChangeListener) 
		*/
		@Override
		public void addOnAttachStateChangeListener(
				View_OnAttachStateChangeListener listener) {
			mListeners.add(listener);
		}

		/** 
		* <p>Title: removeOnAttachStateChangeListener</p> 
		* <p>Description: </p> 
		* @param listener 
		* @see com.actionbarsherlock.internal.view.View_HasStateListenerSupport#removeOnAttachStateChangeListener(com.actionbarsherlock.internal.view.View_OnAttachStateChangeListener) 
		*/
		@Override
		public void removeOnAttachStateChangeListener(
				View_OnAttachStateChangeListener listener) {
			mListeners.remove(listener);
		}
	}

	/**
	 * The Class OverflowPopup.
	 */
	private class OverflowPopup extends MenuPopupHelper {
		
		/**
		 * Instantiates a new overflow popup.
		 * 
		 * @param context
		 *            the context
		 * @param menu
		 *            the menu
		 * @param anchorView
		 *            the anchor view
		 * @param overflowOnly
		 *            the overflow only
		 */
		public OverflowPopup(Context context, MenuBuilder menu,
				View anchorView, boolean overflowOnly) {
			super(context, menu, anchorView, overflowOnly);
			setCallback(mPopupPresenterCallback);
		}

		/** 
		* <p>Title: onDismiss</p> 
		* <p>Description: </p>  
		* @see com.actionbarsherlock.internal.view.menu.MenuPopupHelper#onDismiss() 
		*/
		@Override
		public void onDismiss() {
			super.onDismiss();
			mMenu.close();
			mOverflowPopup = null;
		}
	}

	/**
	 * The Class ActionButtonSubmenu.
	 */
	private class ActionButtonSubmenu extends MenuPopupHelper {
		// UNUSED private SubMenuBuilder mSubMenu;

		/**
		 * Instantiates a new action button submenu.
		 * 
		 * @param context
		 *            the context
		 * @param subMenu
		 *            the sub menu
		 */
		public ActionButtonSubmenu(Context context, SubMenuBuilder subMenu) {
			super(context, subMenu);
			// UNUSED mSubMenu = subMenu;

			MenuItemImpl item = (MenuItemImpl) subMenu.getItem();
			if (!item.isActionButton()) {
				// Give a reasonable anchor to nested submenus.
				setAnchorView(mOverflowButton == null ? (View) mMenuView
						: mOverflowButton);
			}

			setCallback(mPopupPresenterCallback);

			boolean preserveIconSpacing = false;
			final int count = subMenu.size();
			for (int i = 0; i < count; i++) {
				MenuItem childItem = subMenu.getItem(i);
				if (childItem.isVisible() && childItem.getIcon() != null) {
					preserveIconSpacing = true;
					break;
				}
			}
			setForceShowIcon(preserveIconSpacing);
		}

		/** 
		* <p>Title: onDismiss</p> 
		* <p>Description: </p>  
		* @see com.actionbarsherlock.internal.view.menu.MenuPopupHelper#onDismiss() 
		*/
		@Override
		public void onDismiss() {
			super.onDismiss();
			mActionButtonPopup = null;
			mOpenSubMenuId = 0;
		}
	}

	/**
	 * The Class PopupPresenterCallback.
	 */
	private class PopupPresenterCallback implements MenuPresenter.Callback {

		/** 
		* <p>Title: onOpenSubMenu</p> 
		* <p>Description: </p> 
		* @param subMenu
		* @return 
		* @see com.actionbarsherlock.internal.view.menu.MenuPresenter.Callback#onOpenSubMenu(com.actionbarsherlock.internal.view.menu.MenuBuilder) 
		*/
		@Override
		public boolean onOpenSubMenu(MenuBuilder subMenu) {
			if (subMenu == null)
				return false;

			mOpenSubMenuId = ((SubMenuBuilder) subMenu).getItem().getItemId();
			return false;
		}

		/** 
		* <p>Title: onCloseMenu</p> 
		* <p>Description: </p> 
		* @param menu
		* @param allMenusAreClosing 
		* @see com.actionbarsherlock.internal.view.menu.MenuPresenter.Callback#onCloseMenu(com.actionbarsherlock.internal.view.menu.MenuBuilder, boolean) 
		*/
		@Override
		public void onCloseMenu(MenuBuilder menu, boolean allMenusAreClosing) {
			if (menu instanceof SubMenuBuilder) {
				((SubMenuBuilder) menu).getRootMenu().close(false);
			}
		}
	}

	/**
	 * The Class OpenOverflowRunnable.
	 */
	private class OpenOverflowRunnable implements Runnable {
		
		/** The m popup. */
		private OverflowPopup mPopup;

		/**
		 * Instantiates a new open overflow runnable.
		 * 
		 * @param popup
		 *            the popup
		 */
		public OpenOverflowRunnable(OverflowPopup popup) {
			mPopup = popup;
		}

		/** 
		* <p>Title: run</p> 
		* <p>Description: </p>  
		* @see java.lang.Runnable#run() 
		*/
		@Override
		public void run() {
			mMenu.changeMenuMode();
			final View menuView = (View) mMenuView;
			if (menuView != null && menuView.getWindowToken() != null
					&& mPopup.tryShow()) {
				mOverflowPopup = mPopup;
			}
			mPostedOpenRunnable = null;
		}
	}
}
