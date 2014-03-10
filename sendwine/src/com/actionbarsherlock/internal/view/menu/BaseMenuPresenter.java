/**   
* @Title: BaseMenuPresenter.java 
* @Package com.actionbarsherlock.internal.view.menu 
* @Description: 
*			声明:
*			1)掌控校园是本人的大学的创业作品,也是本人的毕业设计
*			2)本程序尚未进行开放源代码,所以禁止组织或者是个人泄露源码,否则将会追究其刑事责任
*			3)编写本软件,我需要感谢彭老师以及其他鼓励和支持我的同学以及朋友
*			4)本程序的最终所有权属于本人	
* @author  张雷 794857063@qq.com
* @date 2013-11-14 19:34:32 
* @version V1.0   
*/

package com.actionbarsherlock.internal.view.menu;

import java.util.ArrayList;
import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

// TODO: Auto-generated Javadoc
/**
 * Base class for MenuPresenters that have a consistent container view and item
 * views. Behaves similarly to an AdapterView in that existing item views will
 * be reused if possible when items change.
 */
public abstract class BaseMenuPresenter implements MenuPresenter {
	
	/** The Constant IS_HONEYCOMB. */
	private static final boolean IS_HONEYCOMB = Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB;

	/** The m system context. */
	protected Context mSystemContext;
	
	/** The m context. */
	protected Context mContext;
	
	/** The m menu. */
	protected MenuBuilder mMenu;
	
	/** The m system inflater. */
	protected LayoutInflater mSystemInflater;
	
	/** The m inflater. */
	protected LayoutInflater mInflater;
	
	/** The m callback. */
	private Callback mCallback;

	/** The m menu layout res. */
	private int mMenuLayoutRes;
	
	/** The m item layout res. */
	private int mItemLayoutRes;

	/** The m menu view. */
	protected MenuView mMenuView;

	/** The m id. */
	private int mId;

	/**
	 * Construct a new BaseMenuPresenter.
	 * 
	 * @param context
	 *            Context for generating system-supplied views
	 * @param menuLayoutRes
	 *            Layout resource ID for the menu container view
	 * @param itemLayoutRes
	 *            Layout resource ID for a single item view
	 */
	public BaseMenuPresenter(Context context, int menuLayoutRes,
			int itemLayoutRes) {
		mSystemContext = context;
		mSystemInflater = LayoutInflater.from(context);
		mMenuLayoutRes = menuLayoutRes;
		mItemLayoutRes = itemLayoutRes;
	}

	/** 
	* <p>Title: initForMenu</p> 
	* <p>Description: </p> 
	* @param context
	* @param menu 
	* @see com.actionbarsherlock.internal.view.menu.MenuPresenter#initForMenu(android.content.Context, com.actionbarsherlock.internal.view.menu.MenuBuilder) 
	*/
	@Override
	public void initForMenu(Context context, MenuBuilder menu) {
		mContext = context;
		mInflater = LayoutInflater.from(mContext);
		mMenu = menu;
	}

	/** 
	* <p>Title: getMenuView</p> 
	* <p>Description: </p> 
	* @param root
	* @return 
	* @see com.actionbarsherlock.internal.view.menu.MenuPresenter#getMenuView(android.view.ViewGroup) 
	*/
	@Override
	public MenuView getMenuView(ViewGroup root) {
		if (mMenuView == null) {
			mMenuView = (MenuView) mSystemInflater.inflate(mMenuLayoutRes,
					root, false);
			mMenuView.initialize(mMenu);
			updateMenuView(true);
		}

		return mMenuView;
	}

	/**
	 * Reuses item views when it can.
	 * 
	 * @param cleared
	 *            the cleared
	 */
	@Override
	public void updateMenuView(boolean cleared) {
		final ViewGroup parent = (ViewGroup) mMenuView;
		if (parent == null)
			return;

		int childIndex = 0;
		if (mMenu != null) {
			mMenu.flagActionItems();
			ArrayList<MenuItemImpl> visibleItems = mMenu.getVisibleItems();
			final int itemCount = visibleItems.size();
			for (int i = 0; i < itemCount; i++) {
				MenuItemImpl item = visibleItems.get(i);
				if (shouldIncludeItem(childIndex, item)) {
					final View convertView = parent.getChildAt(childIndex);
					final MenuItemImpl oldItem = convertView instanceof MenuView.ItemView ? ((MenuView.ItemView) convertView)
							.getItemData() : null;
					final View itemView = getItemView(item, convertView, parent);
					if (item != oldItem) {
						// Don't let old states linger with new data.
						itemView.setPressed(false);
						if (IS_HONEYCOMB)
							itemView.jumpDrawablesToCurrentState();
					}
					if (itemView != convertView) {
						addItemView(itemView, childIndex);
					}
					childIndex++;
				}
			}
		}

		// Remove leftover views.
		while (childIndex < parent.getChildCount()) {
			if (!filterLeftoverView(parent, childIndex)) {
				childIndex++;
			}
		}
	}

	/**
	 * Add an item view at the given index.
	 * 
	 * @param itemView
	 *            View to add
	 * @param childIndex
	 *            Index within the parent to insert at
	 */
	protected void addItemView(View itemView, int childIndex) {
		final ViewGroup currentParent = (ViewGroup) itemView.getParent();
		if (currentParent != null) {
			currentParent.removeView(itemView);
		}
		((ViewGroup) mMenuView).addView(itemView, childIndex);
	}

	/**
	 * Filter the child view at index and remove it if appropriate.
	 * 
	 * @param parent
	 *            Parent to filter from
	 * @param childIndex
	 *            Index to filter
	 * @return true if the child view at index was removed
	 */
	protected boolean filterLeftoverView(ViewGroup parent, int childIndex) {
		parent.removeViewAt(childIndex);
		return true;
	}

	/** 
	* <p>Title: setCallback</p> 
	* <p>Description: </p> 
	* @param cb 
	* @see com.actionbarsherlock.internal.view.menu.MenuPresenter#setCallback(com.actionbarsherlock.internal.view.menu.MenuPresenter.Callback) 
	*/
	@Override
	public void setCallback(Callback cb) {
		mCallback = cb;
	}

	/**
	 * Create a new item view that can be re-bound to other item data later.
	 * 
	 * @param parent
	 *            the parent
	 * @return The new item view
	 */
	public MenuView.ItemView createItemView(ViewGroup parent) {
		return (MenuView.ItemView) mSystemInflater.inflate(mItemLayoutRes,
				parent, false);
	}

	/**
	 * Prepare an item view for use. See AdapterView for the basic idea at work
	 * here. This may require creating a new item view, but well-behaved
	 * implementations will re-use the view passed as convertView if present.
	 * The returned view will be populated with data from the item parameter.
	 * 
	 * @param item
	 *            Item to present
	 * @param convertView
	 *            Existing view to reuse
	 * @param parent
	 *            Intended parent view - use for inflation.
	 * @return View that presents the requested menu item
	 */
	public View getItemView(MenuItemImpl item, View convertView,
			ViewGroup parent) {
		MenuView.ItemView itemView;
		if (convertView instanceof MenuView.ItemView) {
			itemView = (MenuView.ItemView) convertView;
		} else {
			itemView = createItemView(parent);
		}
		bindItemView(item, itemView);
		return (View) itemView;
	}

	/**
	 * Bind item data to an existing item view.
	 * 
	 * @param item
	 *            Item to bind
	 * @param itemView
	 *            View to populate with item data
	 */
	public abstract void bindItemView(MenuItemImpl item,
			MenuView.ItemView itemView);

	/**
	 * Filter item by child index and item data.
	 * 
	 * @param childIndex
	 *            Indended presentation index of this item
	 * @param item
	 *            Item to present
	 * @return true if this item should be included in this menu presentation;
	 *         false otherwise
	 */
	public boolean shouldIncludeItem(int childIndex, MenuItemImpl item) {
		return true;
	}

	/** 
	* <p>Title: onCloseMenu</p> 
	* <p>Description: </p> 
	* @param menu
	* @param allMenusAreClosing 
	* @see com.actionbarsherlock.internal.view.menu.MenuPresenter#onCloseMenu(com.actionbarsherlock.internal.view.menu.MenuBuilder, boolean) 
	*/
	@Override
	public void onCloseMenu(MenuBuilder menu, boolean allMenusAreClosing) {
		if (mCallback != null) {
			mCallback.onCloseMenu(menu, allMenusAreClosing);
		}
	}

	/** 
	* <p>Title: onSubMenuSelected</p> 
	* <p>Description: </p> 
	* @param menu
	* @return 
	* @see com.actionbarsherlock.internal.view.menu.MenuPresenter#onSubMenuSelected(com.actionbarsherlock.internal.view.menu.SubMenuBuilder) 
	*/
	@Override
	public boolean onSubMenuSelected(SubMenuBuilder menu) {
		if (mCallback != null) {
			return mCallback.onOpenSubMenu(menu);
		}
		return false;
	}

	/** 
	* <p>Title: flagActionItems</p> 
	* <p>Description: </p> 
	* @return 
	* @see com.actionbarsherlock.internal.view.menu.MenuPresenter#flagActionItems() 
	*/
	@Override
	public boolean flagActionItems() {
		return false;
	}

	/** 
	* <p>Title: expandItemActionView</p> 
	* <p>Description: </p> 
	* @param menu
	* @param item
	* @return 
	* @see com.actionbarsherlock.internal.view.menu.MenuPresenter#expandItemActionView(com.actionbarsherlock.internal.view.menu.MenuBuilder, com.actionbarsherlock.internal.view.menu.MenuItemImpl) 
	*/
	@Override
	public boolean expandItemActionView(MenuBuilder menu, MenuItemImpl item) {
		return false;
	}

	/** 
	* <p>Title: collapseItemActionView</p> 
	* <p>Description: </p> 
	* @param menu
	* @param item
	* @return 
	* @see com.actionbarsherlock.internal.view.menu.MenuPresenter#collapseItemActionView(com.actionbarsherlock.internal.view.menu.MenuBuilder, com.actionbarsherlock.internal.view.menu.MenuItemImpl) 
	*/
	@Override
	public boolean collapseItemActionView(MenuBuilder menu, MenuItemImpl item) {
		return false;
	}

	/** 
	* <p>Title: getId</p> 
	* <p>Description: </p> 
	* @return 
	* @see com.actionbarsherlock.internal.view.menu.MenuPresenter#getId() 
	*/
	@Override
	public int getId() {
		return mId;
	}

	/**
	 * Sets the id.
	 * 
	 * @param id
	 *            the new id
	 */
	public void setId(int id) {
		mId = id;
	}
}
