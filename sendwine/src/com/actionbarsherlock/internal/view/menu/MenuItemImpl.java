/**   
* @Title: MenuItemImpl.java 
* @Package com.actionbarsherlock.internal.view.menu 
* @Description: 
*			声明:
*			1)掌控校园是本人的大学的创业作品,也是本人的毕业设计
*			2)本程序尚未进行开放源代码,所以禁止组织或者是个人泄露源码,否则将会追究其刑事责任
*			3)编写本软件,我需要感谢彭老师以及其他鼓励和支持我的同学以及朋友
*			4)本程序的最终所有权属于本人	
* @author  张雷 794857063@qq.com
* @date 2013-11-14 19:33:51 
* @version V1.0   
*/

package com.actionbarsherlock.internal.view.menu;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewDebug;
import android.widget.LinearLayout;

import com.actionbarsherlock.view.ActionProvider;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.SubMenu;

// TODO: Auto-generated Javadoc
/**
 * The Class MenuItemImpl.
 * 
 * @hide
 */
public final class MenuItemImpl implements MenuItem {
	
	/** The Constant TAG. */
	private static final String TAG = "MenuItemImpl";

	/** The Constant SHOW_AS_ACTION_MASK. */
	private static final int SHOW_AS_ACTION_MASK = SHOW_AS_ACTION_NEVER
			| SHOW_AS_ACTION_IF_ROOM | SHOW_AS_ACTION_ALWAYS;

	/** The m id. */
	private final int mId;
	
	/** The m group. */
	private final int mGroup;
	
	/** The m category order. */
	private final int mCategoryOrder;
	
	/** The m ordering. */
	private final int mOrdering;
	
	/** The m title. */
	private CharSequence mTitle;
	
	/** The m title condensed. */
	private CharSequence mTitleCondensed;
	
	/** The m intent. */
	private Intent mIntent;
	
	/** The m shortcut numeric char. */
	private char mShortcutNumericChar;
	
	/** The m shortcut alphabetic char. */
	private char mShortcutAlphabeticChar;

	/** The icon's drawable which is only created as needed. */
	private Drawable mIconDrawable;
	/**
	 * The icon's resource ID which is used to get the Drawable when it is
	 * needed (if the Drawable isn't already obtained--only one of the two is
	 * needed).
	 */
	private int mIconResId = NO_ICON;

	/** The menu to which this item belongs. */
	private MenuBuilder mMenu;
	
	/** If this item should launch a sub menu, this is the sub menu to launch. */
	private SubMenuBuilder mSubMenu;

	/** The m item callback. */
	private Runnable mItemCallback;
	
	/** The m click listener. */
	private MenuItem.OnMenuItemClickListener mClickListener;

	/** The m flags. */
	private int mFlags = ENABLED;
	
	/** The Constant CHECKABLE. */
	private static final int CHECKABLE = 0x00000001;
	
	/** The Constant CHECKED. */
	private static final int CHECKED = 0x00000002;
	
	/** The Constant EXCLUSIVE. */
	private static final int EXCLUSIVE = 0x00000004;
	
	/** The Constant HIDDEN. */
	private static final int HIDDEN = 0x00000008;
	
	/** The Constant ENABLED. */
	private static final int ENABLED = 0x00000010;
	
	/** The Constant IS_ACTION. */
	private static final int IS_ACTION = 0x00000020;

	/** The m show as action. */
	private int mShowAsAction = SHOW_AS_ACTION_NEVER;

	/** The m action view. */
	private View mActionView;
	
	/** The m action provider. */
	private ActionProvider mActionProvider;
	
	/** The m on action expand listener. */
	private OnActionExpandListener mOnActionExpandListener;
	
	/** The m is action view expanded. */
	private boolean mIsActionViewExpanded = false;

	/** Used for the icon resource ID if this item does not have an icon. */
	static final int NO_ICON = 0;

	/**
	 * Current use case is for context menu: Extra information linked to the
	 * View that added this item to the context menu.
	 */
	private ContextMenuInfo mMenuInfo;

	/** The s prepend shortcut label. */
	private static String sPrependShortcutLabel;
	
	/** The s enter shortcut label. */
	private static String sEnterShortcutLabel;
	
	/** The s delete shortcut label. */
	private static String sDeleteShortcutLabel;
	
	/** The s space shortcut label. */
	private static String sSpaceShortcutLabel;

	/**
	 * Instantiates this menu item.
	 * 
	 * @param menu
	 *            the menu
	 * @param group
	 *            Item ordering grouping control. The item will be added after
	 *            all other items whose order is <= this number, and before any
	 *            that are larger than it. This can also be used to define
	 *            groups of items for batch state changes. Normally use 0.
	 * @param id
	 *            Unique item ID. Use 0 if you do not need a unique ID.
	 * @param categoryOrder
	 *            The ordering for this item.
	 * @param ordering
	 *            the ordering
	 * @param title
	 *            The text to display for the item.
	 * @param showAsAction
	 *            the show as action
	 */
	MenuItemImpl(MenuBuilder menu, int group, int id, int categoryOrder,
			int ordering, CharSequence title, int showAsAction) {

		/*
		 * TODO if (sPrependShortcutLabel == null) { // This is instantiated
		 * from the UI thread, so no chance of sync issues sPrependShortcutLabel
		 * = menu.getContext().getResources().getString(
		 * com.android.internal.R.string.prepend_shortcut_label);
		 * sEnterShortcutLabel = menu.getContext().getResources().getString(
		 * com.android.internal.R.string.menu_enter_shortcut_label);
		 * sDeleteShortcutLabel = menu.getContext().getResources().getString(
		 * com.android.internal.R.string.menu_delete_shortcut_label);
		 * sSpaceShortcutLabel = menu.getContext().getResources().getString(
		 * com.android.internal.R.string.menu_space_shortcut_label); }
		 */

		mMenu = menu;
		mId = id;
		mGroup = group;
		mCategoryOrder = categoryOrder;
		mOrdering = ordering;
		mTitle = title;
		mShowAsAction = showAsAction;
	}

	/**
	 * Invokes the item by calling various listeners or callbacks.
	 * 
	 * @return true if the invocation was handled, false otherwise
	 */
	public boolean invoke() {
		if (mClickListener != null && mClickListener.onMenuItemClick(this)) {
			return true;
		}

		if (mMenu.dispatchMenuItemSelected(mMenu.getRootMenu(), this)) {
			return true;
		}

		if (mItemCallback != null) {
			mItemCallback.run();
			return true;
		}

		if (mIntent != null) {
			try {
				mMenu.getContext().startActivity(mIntent);
				return true;
			} catch (ActivityNotFoundException e) {
				Log.e(TAG, "Can't find activity to handle intent; ignoring", e);
			}
		}

		if (mActionProvider != null && mActionProvider.onPerformDefaultAction()) {
			return true;
		}

		return false;
	}

	/** 
	* <p>Title: isEnabled</p> 
	* <p>Description: </p> 
	* @return 
	* @see com.actionbarsherlock.view.MenuItem#isEnabled() 
	*/
	@Override
	public boolean isEnabled() {
		return (mFlags & ENABLED) != 0;
	}

	/** 
	* <p>Title: setEnabled</p> 
	* <p>Description: </p> 
	* @param enabled
	* @return 
	* @see com.actionbarsherlock.view.MenuItem#setEnabled(boolean) 
	*/
	@Override
	public MenuItem setEnabled(boolean enabled) {
		if (enabled) {
			mFlags |= ENABLED;
		} else {
			mFlags &= ~ENABLED;
		}

		mMenu.onItemsChanged(false);

		return this;
	}

	/** 
	* <p>Title: getGroupId</p> 
	* <p>Description: </p> 
	* @return 
	* @see com.actionbarsherlock.view.MenuItem#getGroupId() 
	*/
	@Override
	public int getGroupId() {
		return mGroup;
	}

	/** 
	* <p>Title: getItemId</p> 
	* <p>Description: </p> 
	* @return 
	* @see com.actionbarsherlock.view.MenuItem#getItemId() 
	*/
	@Override
	@ViewDebug.CapturedViewProperty
	public int getItemId() {
		return mId;
	}

	/** 
	* <p>Title: getOrder</p> 
	* <p>Description: </p> 
	* @return 
	* @see com.actionbarsherlock.view.MenuItem#getOrder() 
	*/
	@Override
	public int getOrder() {
		return mCategoryOrder;
	}

	/**
	 * Gets the ordering.
	 * 
	 * @return the ordering
	 */
	public int getOrdering() {
		return mOrdering;
	}

	/** 
	* <p>Title: getIntent</p> 
	* <p>Description: </p> 
	* @return 
	* @see com.actionbarsherlock.view.MenuItem#getIntent() 
	*/
	@Override
	public Intent getIntent() {
		return mIntent;
	}

	/** 
	* <p>Title: setIntent</p> 
	* <p>Description: </p> 
	* @param intent
	* @return 
	* @see com.actionbarsherlock.view.MenuItem#setIntent(android.content.Intent) 
	*/
	@Override
	public MenuItem setIntent(Intent intent) {
		mIntent = intent;
		return this;
	}

	/**
	 * Gets the callback.
	 * 
	 * @return the callback
	 */
	Runnable getCallback() {
		return mItemCallback;
	}

	/**
	 * Sets the callback.
	 * 
	 * @param callback
	 *            the callback
	 * @return the menu item
	 */
	public MenuItem setCallback(Runnable callback) {
		mItemCallback = callback;
		return this;
	}

	/** 
	* <p>Title: getAlphabeticShortcut</p> 
	* <p>Description: </p> 
	* @return 
	* @see com.actionbarsherlock.view.MenuItem#getAlphabeticShortcut() 
	*/
	@Override
	public char getAlphabeticShortcut() {
		return mShortcutAlphabeticChar;
	}

	/** 
	* <p>Title: setAlphabeticShortcut</p> 
	* <p>Description: </p> 
	* @param alphaChar
	* @return 
	* @see com.actionbarsherlock.view.MenuItem#setAlphabeticShortcut(char) 
	*/
	@Override
	public MenuItem setAlphabeticShortcut(char alphaChar) {
		if (mShortcutAlphabeticChar == alphaChar)
			return this;

		mShortcutAlphabeticChar = Character.toLowerCase(alphaChar);

		mMenu.onItemsChanged(false);

		return this;
	}

	/** 
	* <p>Title: getNumericShortcut</p> 
	* <p>Description: </p> 
	* @return 
	* @see com.actionbarsherlock.view.MenuItem#getNumericShortcut() 
	*/
	@Override
	public char getNumericShortcut() {
		return mShortcutNumericChar;
	}

	/** 
	* <p>Title: setNumericShortcut</p> 
	* <p>Description: </p> 
	* @param numericChar
	* @return 
	* @see com.actionbarsherlock.view.MenuItem#setNumericShortcut(char) 
	*/
	@Override
	public MenuItem setNumericShortcut(char numericChar) {
		if (mShortcutNumericChar == numericChar)
			return this;

		mShortcutNumericChar = numericChar;

		mMenu.onItemsChanged(false);

		return this;
	}

	/** 
	* <p>Title: setShortcut</p> 
	* <p>Description: </p> 
	* @param numericChar
	* @param alphaChar
	* @return 
	* @see com.actionbarsherlock.view.MenuItem#setShortcut(char, char) 
	*/
	@Override
	public MenuItem setShortcut(char numericChar, char alphaChar) {
		mShortcutNumericChar = numericChar;
		mShortcutAlphabeticChar = Character.toLowerCase(alphaChar);

		mMenu.onItemsChanged(false);

		return this;
	}

	/**
	 * Gets the shortcut.
	 * 
	 * @return The active shortcut (based on QWERTY-mode of the menu).
	 */
	char getShortcut() {
		return (mMenu.isQwertyMode() ? mShortcutAlphabeticChar
				: mShortcutNumericChar);
	}

	/**
	 * Gets the shortcut label.
	 * 
	 * @return The label to show for the shortcut. This includes the chording
	 *         key (for example 'Menu+a'). Also, any non-human readable
	 *         characters should be human readable (for example 'Menu+enter').
	 */
	String getShortcutLabel() {

		char shortcut = getShortcut();
		if (shortcut == 0) {
			return "";
		}

		StringBuilder sb = new StringBuilder(sPrependShortcutLabel);
		switch (shortcut) {

		case '\n':
			sb.append(sEnterShortcutLabel);
			break;

		case '\b':
			sb.append(sDeleteShortcutLabel);
			break;

		case ' ':
			sb.append(sSpaceShortcutLabel);
			break;

		default:
			sb.append(shortcut);
			break;
		}

		return sb.toString();
	}

	/**
	 * Should show shortcut.
	 * 
	 * @return Whether this menu item should be showing shortcuts (depends on
	 *         whether the menu should show shortcuts and whether this item has
	 *         a shortcut defined)
	 */
	boolean shouldShowShortcut() {
		// Show shortcuts if the menu is supposed to show shortcuts AND this
		// item has a shortcut
		return mMenu.isShortcutsVisible() && (getShortcut() != 0);
	}

	/** 
	* <p>Title: getSubMenu</p> 
	* <p>Description: </p> 
	* @return 
	* @see com.actionbarsherlock.view.MenuItem#getSubMenu() 
	*/
	@Override
	public SubMenu getSubMenu() {
		return mSubMenu;
	}

	/** 
	* <p>Title: hasSubMenu</p> 
	* <p>Description: </p> 
	* @return 
	* @see com.actionbarsherlock.view.MenuItem#hasSubMenu() 
	*/
	@Override
	public boolean hasSubMenu() {
		return mSubMenu != null;
	}

	/**
	 * Sets the sub menu.
	 * 
	 * @param subMenu
	 *            the new sub menu
	 */
	void setSubMenu(SubMenuBuilder subMenu) {
		mSubMenu = subMenu;

		subMenu.setHeaderTitle(getTitle());
	}

	/** 
	* <p>Title: getTitle</p> 
	* <p>Description: </p> 
	* @return 
	* @see com.actionbarsherlock.view.MenuItem#getTitle() 
	*/
	@Override
	@ViewDebug.CapturedViewProperty
	public CharSequence getTitle() {
		return mTitle;
	}

	/**
	 * Gets the title for a particular {@link ItemView}.
	 * 
	 * @param itemView
	 *            The ItemView that is receiving the title
	 * @return Either the title or condensed title based on what the ItemView
	 *         prefers
	 */
	CharSequence getTitleForItemView(MenuView.ItemView itemView) {
		return ((itemView != null) && itemView.prefersCondensedTitle()) ? getTitleCondensed()
				: getTitle();
	}

	/** 
	* <p>Title: setTitle</p> 
	* <p>Description: </p> 
	* @param title
	* @return 
	* @see com.actionbarsherlock.view.MenuItem#setTitle(java.lang.CharSequence) 
	*/
	@Override
	public MenuItem setTitle(CharSequence title) {
		mTitle = title;

		mMenu.onItemsChanged(false);

		if (mSubMenu != null) {
			mSubMenu.setHeaderTitle(title);
		}

		return this;
	}

	/** 
	* <p>Title: setTitle</p> 
	* <p>Description: </p> 
	* @param title
	* @return 
	* @see com.actionbarsherlock.view.MenuItem#setTitle(int) 
	*/
	@Override
	public MenuItem setTitle(int title) {
		return setTitle(mMenu.getContext().getString(title));
	}

	/** 
	* <p>Title: getTitleCondensed</p> 
	* <p>Description: </p> 
	* @return 
	* @see com.actionbarsherlock.view.MenuItem#getTitleCondensed() 
	*/
	@Override
	public CharSequence getTitleCondensed() {
		return mTitleCondensed != null ? mTitleCondensed : mTitle;
	}

	/** 
	* <p>Title: setTitleCondensed</p> 
	* <p>Description: </p> 
	* @param title
	* @return 
	* @see com.actionbarsherlock.view.MenuItem#setTitleCondensed(java.lang.CharSequence) 
	*/
	@Override
	public MenuItem setTitleCondensed(CharSequence title) {
		mTitleCondensed = title;

		// Could use getTitle() in the loop below, but just cache what it would
		// do here
		if (title == null) {
			title = mTitle;
		}

		mMenu.onItemsChanged(false);

		return this;
	}

	/** 
	* <p>Title: getIcon</p> 
	* <p>Description: </p> 
	* @return 
	* @see com.actionbarsherlock.view.MenuItem#getIcon() 
	*/
	@Override
	public Drawable getIcon() {
		if (mIconDrawable != null) {
			return mIconDrawable;
		}

		if (mIconResId != NO_ICON) {
			return mMenu.getResources().getDrawable(mIconResId);
		}

		return null;
	}

	/** 
	* <p>Title: setIcon</p> 
	* <p>Description: </p> 
	* @param icon
	* @return 
	* @see com.actionbarsherlock.view.MenuItem#setIcon(android.graphics.drawable.Drawable) 
	*/
	@Override
	public MenuItem setIcon(Drawable icon) {
		mIconResId = NO_ICON;
		mIconDrawable = icon;
		mMenu.onItemsChanged(false);

		return this;
	}

	/** 
	* <p>Title: setIcon</p> 
	* <p>Description: </p> 
	* @param iconResId
	* @return 
	* @see com.actionbarsherlock.view.MenuItem#setIcon(int) 
	*/
	@Override
	public MenuItem setIcon(int iconResId) {
		mIconDrawable = null;
		mIconResId = iconResId;

		// If we have a view, we need to push the Drawable to them
		mMenu.onItemsChanged(false);

		return this;
	}

	/** 
	* <p>Title: isCheckable</p> 
	* <p>Description: </p> 
	* @return 
	* @see com.actionbarsherlock.view.MenuItem#isCheckable() 
	*/
	@Override
	public boolean isCheckable() {
		return (mFlags & CHECKABLE) == CHECKABLE;
	}

	/** 
	* <p>Title: setCheckable</p> 
	* <p>Description: </p> 
	* @param checkable
	* @return 
	* @see com.actionbarsherlock.view.MenuItem#setCheckable(boolean) 
	*/
	@Override
	public MenuItem setCheckable(boolean checkable) {
		final int oldFlags = mFlags;
		mFlags = (mFlags & ~CHECKABLE) | (checkable ? CHECKABLE : 0);
		if (oldFlags != mFlags) {
			mMenu.onItemsChanged(false);
		}

		return this;
	}

	/**
	 * Sets the exclusive checkable.
	 * 
	 * @param exclusive
	 *            the new exclusive checkable
	 */
	public void setExclusiveCheckable(boolean exclusive) {
		mFlags = (mFlags & ~EXCLUSIVE) | (exclusive ? EXCLUSIVE : 0);
	}

	/**
	 * Checks if is exclusive checkable.
	 * 
	 * @return true, if is exclusive checkable
	 */
	public boolean isExclusiveCheckable() {
		return (mFlags & EXCLUSIVE) != 0;
	}

	/** 
	* <p>Title: isChecked</p> 
	* <p>Description: </p> 
	* @return 
	* @see com.actionbarsherlock.view.MenuItem#isChecked() 
	*/
	@Override
	public boolean isChecked() {
		return (mFlags & CHECKED) == CHECKED;
	}

	/** 
	* <p>Title: setChecked</p> 
	* <p>Description: </p> 
	* @param checked
	* @return 
	* @see com.actionbarsherlock.view.MenuItem#setChecked(boolean) 
	*/
	@Override
	public MenuItem setChecked(boolean checked) {
		if ((mFlags & EXCLUSIVE) != 0) {
			// Call the method on the Menu since it knows about the others in
			// this
			// exclusive checkable group
			mMenu.setExclusiveItemChecked(this);
		} else {
			setCheckedInt(checked);
		}

		return this;
	}

	/**
	 * Sets the checked int.
	 * 
	 * @param checked
	 *            the new checked int
	 */
	void setCheckedInt(boolean checked) {
		final int oldFlags = mFlags;
		mFlags = (mFlags & ~CHECKED) | (checked ? CHECKED : 0);
		if (oldFlags != mFlags) {
			mMenu.onItemsChanged(false);
		}
	}

	/** 
	* <p>Title: isVisible</p> 
	* <p>Description: </p> 
	* @return 
	* @see com.actionbarsherlock.view.MenuItem#isVisible() 
	*/
	@Override
	public boolean isVisible() {
		return (mFlags & HIDDEN) == 0;
	}

	/**
	 * Changes the visibility of the item. This method DOES NOT notify the
	 * parent menu of a change in this item, so this should only be called from
	 * methods that will eventually trigger this change. If unsure, use
	 * 
	 * @param shown
	 *            Whether to show (true) or hide (false).
	 * @return Whether the item's shown state was changed
	 *         {@link #setVisible(boolean)} instead.
	 */
	boolean setVisibleInt(boolean shown) {
		final int oldFlags = mFlags;
		mFlags = (mFlags & ~HIDDEN) | (shown ? 0 : HIDDEN);
		return oldFlags != mFlags;
	}

	/** 
	* <p>Title: setVisible</p> 
	* <p>Description: </p> 
	* @param shown
	* @return 
	* @see com.actionbarsherlock.view.MenuItem#setVisible(boolean) 
	*/
	@Override
	public MenuItem setVisible(boolean shown) {
		// Try to set the shown state to the given state. If the shown state was
		// changed
		// (i.e. the previous state isn't the same as given state), notify the
		// parent menu that
		// the shown state has changed for this item
		if (setVisibleInt(shown))
			mMenu.onItemVisibleChanged(this);

		return this;
	}

	/** 
	* <p>Title: setOnMenuItemClickListener</p> 
	* <p>Description: </p> 
	* @param clickListener
	* @return 
	* @see com.actionbarsherlock.view.MenuItem#setOnMenuItemClickListener(com.actionbarsherlock.view.MenuItem.OnMenuItemClickListener) 
	*/
	@Override
	public MenuItem setOnMenuItemClickListener(
			MenuItem.OnMenuItemClickListener clickListener) {
		mClickListener = clickListener;
		return this;
	}

	/** 
	* <p>Title: toString</p> 
	* <p>Description: </p> 
	* @return 
	* @see java.lang.Object#toString() 
	*/
	@Override
	public String toString() {
		return mTitle.toString();
	}

	/**
	 * Sets the menu info.
	 * 
	 * @param menuInfo
	 *            the new menu info
	 */
	void setMenuInfo(ContextMenuInfo menuInfo) {
		mMenuInfo = menuInfo;
	}

	/** 
	* <p>Title: getMenuInfo</p> 
	* <p>Description: </p> 
	* @return 
	* @see com.actionbarsherlock.view.MenuItem#getMenuInfo() 
	*/
	@Override
	public ContextMenuInfo getMenuInfo() {
		return mMenuInfo;
	}

	/**
	 * Action format changed.
	 */
	public void actionFormatChanged() {
		mMenu.onItemActionRequestChanged(this);
	}

	/**
	 * Should show icon.
	 * 
	 * @return Whether the menu should show icons for menu items.
	 */
	public boolean shouldShowIcon() {
		return mMenu.getOptionalIconsVisible();
	}

	/**
	 * Checks if is action button.
	 * 
	 * @return true, if is action button
	 */
	public boolean isActionButton() {
		return (mFlags & IS_ACTION) == IS_ACTION;
	}

	/**
	 * Requests action button.
	 * 
	 * @return true, if successful
	 */
	public boolean requestsActionButton() {
		return (mShowAsAction & SHOW_AS_ACTION_IF_ROOM) == SHOW_AS_ACTION_IF_ROOM;
	}

	/**
	 * Requires action button.
	 * 
	 * @return true, if successful
	 */
	public boolean requiresActionButton() {
		return (mShowAsAction & SHOW_AS_ACTION_ALWAYS) == SHOW_AS_ACTION_ALWAYS;
	}

	/**
	 * Sets the checks if is action button.
	 * 
	 * @param isActionButton
	 *            the new checks if is action button
	 */
	public void setIsActionButton(boolean isActionButton) {
		if (isActionButton) {
			mFlags |= IS_ACTION;
		} else {
			mFlags &= ~IS_ACTION;
		}
	}

	/**
	 * Shows text as action.
	 * 
	 * @return true, if successful
	 */
	public boolean showsTextAsAction() {
		return (mShowAsAction & SHOW_AS_ACTION_WITH_TEXT) == SHOW_AS_ACTION_WITH_TEXT;
	}

	/** 
	* <p>Title: setShowAsAction</p> 
	* <p>Description: </p> 
	* @param actionEnum 
	* @see com.actionbarsherlock.view.MenuItem#setShowAsAction(int) 
	*/
	@Override
	public void setShowAsAction(int actionEnum) {
		switch (actionEnum & SHOW_AS_ACTION_MASK) {
		case SHOW_AS_ACTION_ALWAYS:
		case SHOW_AS_ACTION_IF_ROOM:
		case SHOW_AS_ACTION_NEVER:
			// Looks good!
			break;

		default:
			// Mutually exclusive options selected!
			throw new IllegalArgumentException(
					"SHOW_AS_ACTION_ALWAYS, SHOW_AS_ACTION_IF_ROOM,"
							+ " and SHOW_AS_ACTION_NEVER are mutually exclusive.");
		}
		mShowAsAction = actionEnum;
		mMenu.onItemActionRequestChanged(this);
	}

	/** 
	* <p>Title: setActionView</p> 
	* <p>Description: </p> 
	* @param view
	* @return 
	* @see com.actionbarsherlock.view.MenuItem#setActionView(android.view.View) 
	*/
	@Override
	public MenuItem setActionView(View view) {
		mActionView = view;
		mActionProvider = null;
		if (view != null && view.getId() == View.NO_ID && mId > 0) {
			view.setId(mId);
		}
		mMenu.onItemActionRequestChanged(this);
		return this;
	}

	/** 
	* <p>Title: setActionView</p> 
	* <p>Description: </p> 
	* @param resId
	* @return 
	* @see com.actionbarsherlock.view.MenuItem#setActionView(int) 
	*/
	@Override
	public MenuItem setActionView(int resId) {
		final Context context = mMenu.getContext();
		final LayoutInflater inflater = LayoutInflater.from(context);
		setActionView(inflater.inflate(resId, new LinearLayout(context), false));
		return this;
	}

	/** 
	* <p>Title: getActionView</p> 
	* <p>Description: </p> 
	* @return 
	* @see com.actionbarsherlock.view.MenuItem#getActionView() 
	*/
	@Override
	public View getActionView() {
		if (mActionView != null) {
			return mActionView;
		} else if (mActionProvider != null) {
			mActionView = mActionProvider.onCreateActionView();
			return mActionView;
		} else {
			return null;
		}
	}

	/** 
	* <p>Title: getActionProvider</p> 
	* <p>Description: </p> 
	* @return 
	* @see com.actionbarsherlock.view.MenuItem#getActionProvider() 
	*/
	@Override
	public ActionProvider getActionProvider() {
		return mActionProvider;
	}

	/** 
	* <p>Title: setActionProvider</p> 
	* <p>Description: </p> 
	* @param actionProvider
	* @return 
	* @see com.actionbarsherlock.view.MenuItem#setActionProvider(com.actionbarsherlock.view.ActionProvider) 
	*/
	@Override
	public MenuItem setActionProvider(ActionProvider actionProvider) {
		mActionView = null;
		mActionProvider = actionProvider;
		mMenu.onItemsChanged(true); // Measurement can be changed
		return this;
	}

	/** 
	* <p>Title: setShowAsActionFlags</p> 
	* <p>Description: </p> 
	* @param actionEnum
	* @return 
	* @see com.actionbarsherlock.view.MenuItem#setShowAsActionFlags(int) 
	*/
	@Override
	public MenuItem setShowAsActionFlags(int actionEnum) {
		setShowAsAction(actionEnum);
		return this;
	}

	/** 
	* <p>Title: expandActionView</p> 
	* <p>Description: </p> 
	* @return 
	* @see com.actionbarsherlock.view.MenuItem#expandActionView() 
	*/
	@Override
	public boolean expandActionView() {
		if ((mShowAsAction & SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW) == 0
				|| mActionView == null) {
			return false;
		}

		if (mOnActionExpandListener == null
				|| mOnActionExpandListener.onMenuItemActionExpand(this)) {
			return mMenu.expandItemActionView(this);
		}

		return false;
	}

	/** 
	* <p>Title: collapseActionView</p> 
	* <p>Description: </p> 
	* @return 
	* @see com.actionbarsherlock.view.MenuItem#collapseActionView() 
	*/
	@Override
	public boolean collapseActionView() {
		if ((mShowAsAction & SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW) == 0) {
			return false;
		}
		if (mActionView == null) {
			// We're already collapsed if we have no action view.
			return true;
		}

		if (mOnActionExpandListener == null
				|| mOnActionExpandListener.onMenuItemActionCollapse(this)) {
			return mMenu.collapseItemActionView(this);
		}

		return false;
	}

	/** 
	* <p>Title: setOnActionExpandListener</p> 
	* <p>Description: </p> 
	* @param listener
	* @return 
	* @see com.actionbarsherlock.view.MenuItem#setOnActionExpandListener(com.actionbarsherlock.view.MenuItem.OnActionExpandListener) 
	*/
	@Override
	public MenuItem setOnActionExpandListener(OnActionExpandListener listener) {
		mOnActionExpandListener = listener;
		return this;
	}

	/**
	 * Checks for collapsible action view.
	 * 
	 * @return true, if successful
	 */
	public boolean hasCollapsibleActionView() {
		return (mShowAsAction & SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW) != 0
				&& mActionView != null;
	}

	/**
	 * Sets the action view expanded.
	 * 
	 * @param isExpanded
	 *            the new action view expanded
	 */
	public void setActionViewExpanded(boolean isExpanded) {
		mIsActionViewExpanded = isExpanded;
		mMenu.onItemsChanged(false);
	}

	/** 
	* <p>Title: isActionViewExpanded</p> 
	* <p>Description: </p> 
	* @return 
	* @see com.actionbarsherlock.view.MenuItem#isActionViewExpanded() 
	*/
	@Override
	public boolean isActionViewExpanded() {
		return mIsActionViewExpanded;
	}
}
