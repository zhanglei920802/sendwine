/**   
* @Title: CommonBiz.java 
* @Package cn.cdut.app.business 
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

package com.actionbarsherlock.internal.view.menu;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View;

import com.actionbarsherlock.view.ActionProvider;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.SubMenu;

// TODO: Auto-generated Javadoc
/**
 * The Class ActionMenuItem.
 * 
 * @hide
 */
public class ActionMenuItem implements MenuItem {
	
	/** The m id. */
	private final int mId;
	
	/** The m group. */
	private final int mGroup;
	// UNUSED private final int mCategoryOrder;
	/** The m ordering. */
	private final int mOrdering;

	/** The m title. */
	private CharSequence mTitle;
	
	/**
	 * The void load image(int type, image view img_content, string url, app
	 * context app context).
	 */
	private CharSequence mTitleCondensed;
	
	/** The m intent. */
	private Intent mIntent;
	
	/** The m shortcut numeric char. */
	private char mShortcutNumericChar;
	
	/** The m shortcut alphabetic char. */
	private char mShortcutAlphabeticChar;

	/** The m icon drawable. */
	private Drawable mIconDrawable;
	// UNUSED private int mIconResId = NO_ICON;

	/** The m context. */
	private Context mContext;

	/** The m click listener. */
	private MenuItem.OnMenuItemClickListener mClickListener;

	// UNUSED private static final int NO_ICON = 0;

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

	/**
	 * Instantiates a new action menu item.
	 * 
	 * @param context
	 *            the context
	 * @param group
	 *            the group
	 * @param id
	 *            the id
	 * @param categoryOrder
	 *            the category order
	 * @param ordering
	 *            the ordering
	 * @param title
	 *            the title
	 */
	public ActionMenuItem(Context context, int group, int id,
			int categoryOrder, int ordering, CharSequence title) {
		mContext = context;
		mId = id;
		mGroup = group;
		// UNUSED mCategoryOrder = categoryOrder;
		mOrdering = ordering;
		mTitle = title;
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
	* <p>Title: getIcon</p> 
	* <p>Description: </p> 
	* @return 
	* @see com.actionbarsherlock.view.MenuItem#getIcon() 
	*/
	@Override
	public Drawable getIcon() {
		return mIconDrawable;
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
	* <p>Title: getItemId</p> 
	* <p>Description: </p> 
	* @return 
	* @see com.actionbarsherlock.view.MenuItem#getItemId() 
	*/
	@Override
	public int getItemId() {
		return mId;
	}

	/** 
	* <p>Title: getMenuInfo</p> 
	* <p>Description: </p> 
	* @return 
	* @see com.actionbarsherlock.view.MenuItem#getMenuInfo() 
	*/
	@Override
	public ContextMenuInfo getMenuInfo() {
		return null;
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
	* <p>Title: getOrder</p> 
	* <p>Description: </p> 
	* @return 
	* @see com.actionbarsherlock.view.MenuItem#getOrder() 
	*/
	@Override
	public int getOrder() {
		return mOrdering;
	}

	/** 
	* <p>Title: getSubMenu</p> 
	* <p>Description: </p> 
	* @return 
	* @see com.actionbarsherlock.view.MenuItem#getSubMenu() 
	*/
	@Override
	public SubMenu getSubMenu() {
		return null;
	}

	/** 
	* <p>Title: getTitle</p> 
	* <p>Description: </p> 
	* @return 
	* @see com.actionbarsherlock.view.MenuItem#getTitle() 
	*/
	@Override
	public CharSequence getTitle() {
		return mTitle;
	}

	/** 
	* <p>Title: getTitleCondensed</p> 
	* <p>Description: </p> 
	* @return 
	* @see com.actionbarsherlock.view.MenuItem#getTitleCondensed() 
	*/
	@Override
	public CharSequence getTitleCondensed() {
		return mTitleCondensed;
	}

	/** 
	* <p>Title: hasSubMenu</p> 
	* <p>Description: </p> 
	* @return 
	* @see com.actionbarsherlock.view.MenuItem#hasSubMenu() 
	*/
	@Override
	public boolean hasSubMenu() {
		return false;
	}

	/** 
	* <p>Title: isCheckable</p> 
	* <p>Description: </p> 
	* @return 
	* @see com.actionbarsherlock.view.MenuItem#isCheckable() 
	*/
	@Override
	public boolean isCheckable() {
		return (mFlags & CHECKABLE) != 0;
	}

	/** 
	* <p>Title: isChecked</p> 
	* <p>Description: </p> 
	* @return 
	* @see com.actionbarsherlock.view.MenuItem#isChecked() 
	*/
	@Override
	public boolean isChecked() {
		return (mFlags & CHECKED) != 0;
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
	* <p>Title: setAlphabeticShortcut</p> 
	* <p>Description: </p> 
	* @param alphaChar
	* @return 
	* @see com.actionbarsherlock.view.MenuItem#setAlphabeticShortcut(char) 
	*/
	@Override
	public MenuItem setAlphabeticShortcut(char alphaChar) {
		mShortcutAlphabeticChar = alphaChar;
		return this;
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
		mFlags = (mFlags & ~CHECKABLE) | (checkable ? CHECKABLE : 0);
		return this;
	}

	/**
	 * Sets the exclusive checkable.
	 * 
	 * @param exclusive
	 *            the exclusive
	 * @return the action menu item
	 */
	public ActionMenuItem setExclusiveCheckable(boolean exclusive) {
		mFlags = (mFlags & ~EXCLUSIVE) | (exclusive ? EXCLUSIVE : 0);
		return this;
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
		mFlags = (mFlags & ~CHECKED) | (checked ? CHECKED : 0);
		return this;
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
		mFlags = (mFlags & ~ENABLED) | (enabled ? ENABLED : 0);
		return this;
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
		mIconDrawable = icon;
		// UNUSED mIconResId = NO_ICON;
		return this;
	}

	/** 
	* <p>Title: setIcon</p> 
	* <p>Description: </p> 
	* @param iconRes
	* @return 
	* @see com.actionbarsherlock.view.MenuItem#setIcon(int) 
	*/
	@Override
	public MenuItem setIcon(int iconRes) {
		// UNUSED mIconResId = iconRes;
		mIconDrawable = mContext.getResources().getDrawable(iconRes);
		return this;
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
	* <p>Title: setNumericShortcut</p> 
	* <p>Description: </p> 
	* @param numericChar
	* @return 
	* @see com.actionbarsherlock.view.MenuItem#setNumericShortcut(char) 
	*/
	@Override
	public MenuItem setNumericShortcut(char numericChar) {
		mShortcutNumericChar = numericChar;
		return this;
	}

	/** 
	* <p>Title: setOnMenuItemClickListener</p> 
	* <p>Description: </p> 
	* @param menuItemClickListener
	* @return 
	* @see com.actionbarsherlock.view.MenuItem#setOnMenuItemClickListener(com.actionbarsherlock.view.MenuItem.OnMenuItemClickListener) 
	*/
	@Override
	public MenuItem setOnMenuItemClickListener(
			OnMenuItemClickListener menuItemClickListener) {
		mClickListener = menuItemClickListener;
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
		mShortcutAlphabeticChar = alphaChar;
		return this;
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
		mTitle = mContext.getResources().getString(title);
		return this;
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
		return this;
	}

	/** 
	* <p>Title: setVisible</p> 
	* <p>Description: </p> 
	* @param visible
	* @return 
	* @see com.actionbarsherlock.view.MenuItem#setVisible(boolean) 
	*/
	@Override
	public MenuItem setVisible(boolean visible) {
		mFlags = (mFlags & HIDDEN) | (visible ? 0 : HIDDEN);
		return this;
	}

	/**
	 * Invoke.
	 * 
	 * @return true, if successful
	 */
	public boolean invoke() {
		if (mClickListener != null && mClickListener.onMenuItemClick(this)) {
			return true;
		}

		if (mIntent != null) {
			mContext.startActivity(mIntent);
			return true;
		}

		return false;
	}

	/** 
	* <p>Title: setShowAsAction</p> 
	* <p>Description: </p> 
	* @param show 
	* @see com.actionbarsherlock.view.MenuItem#setShowAsAction(int) 
	*/
	@Override
	public void setShowAsAction(int show) {
		// Do nothing. ActionMenuItems always show as action buttons.
	}

	/** 
	* <p>Title: setActionView</p> 
	* <p>Description: </p> 
	* @param actionView
	* @return 
	* @see com.actionbarsherlock.view.MenuItem#setActionView(android.view.View) 
	*/
	@Override
	public MenuItem setActionView(View actionView) {
		throw new UnsupportedOperationException();
	}

	/** 
	* <p>Title: getActionView</p> 
	* <p>Description: </p> 
	* @return 
	* @see com.actionbarsherlock.view.MenuItem#getActionView() 
	*/
	@Override
	public View getActionView() {
		return null;
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
		throw new UnsupportedOperationException();
	}

	/** 
	* <p>Title: getActionProvider</p> 
	* <p>Description: </p> 
	* @return 
	* @see com.actionbarsherlock.view.MenuItem#getActionProvider() 
	*/
	@Override
	public ActionProvider getActionProvider() {
		return null;
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
		throw new UnsupportedOperationException();
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
		return false;
	}

	/** 
	* <p>Title: isActionViewExpanded</p> 
	* <p>Description: </p> 
	* @return 
	* @see com.actionbarsherlock.view.MenuItem#isActionViewExpanded() 
	*/
	@Override
	public boolean isActionViewExpanded() {
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
		// No need to save the listener; ActionMenuItem does not support
		// collapsing items.
		return this;
	}
}
