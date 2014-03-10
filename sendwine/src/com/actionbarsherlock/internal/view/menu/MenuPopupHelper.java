/**   
* @Title: MenuPopupHelper.java 
* @Package com.actionbarsherlock.internal.view.menu 
* @Description: 
*			澹版槑:
*			1)鎺屾帶鏍″洯鏄湰浜虹殑澶у鐨勫垱涓氫綔鍝�涔熸槸鏈汉鐨勬瘯涓氳璁�*			2)鏈▼搴忓皻鏈繘琛屽紑鏀炬簮浠ｇ爜,鎵�互绂佹缁勭粐鎴栬�鏄釜浜烘硠闇叉簮鐮�鍚﹀垯灏嗕細杩界┒鍏跺垜浜嬭矗浠�*			3)缂栧啓鏈蒋浠�鎴戦渶瑕佹劅璋㈠江鑰佸笀浠ュ強鍏朵粬榧撳姳鍜屾敮鎸佹垜鐨勫悓瀛︿互鍙婃湅鍙�*			4)鏈▼搴忕殑鏈�粓鎵�湁鏉冨睘浜庢湰浜�
* @author  寮犻浄 794857063@qq.com
* @date 2013-11-14 19:34:33 
* @version V1.0   
*/

package com.actionbarsherlock.internal.view.menu;

import java.util.ArrayList;
import android.content.Context;
import android.content.res.Resources;
import android.database.DataSetObserver;
import android.os.Parcelable;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ListAdapter;
import android.widget.PopupWindow;

import com.sendwine.app.R;

import com.actionbarsherlock.internal.view.View_HasStateListenerSupport;
import com.actionbarsherlock.internal.view.View_OnAttachStateChangeListener;
import com.actionbarsherlock.internal.widget.IcsListPopupWindow;
import com.actionbarsherlock.view.MenuItem;

// TODO: Auto-generated Javadoc
/**
 * Presents a menu as a small, simple popup anchored to another view.
 * 
 * @hide
 */
public class MenuPopupHelper implements AdapterView.OnItemClickListener,
		View.OnKeyListener, ViewTreeObserver.OnGlobalLayoutListener,
		PopupWindow.OnDismissListener, View_OnAttachStateChangeListener,
		MenuPresenter {
	// UNUSED private static final String TAG = "MenuPopupHelper";

	/** The Constant ITEM_LAYOUT. */
	static final int ITEM_LAYOUT = R.layout.abs__popup_menu_item_layout;

	/** The m context. */
	private Context mContext;
	
	/** The m inflater. */
	private LayoutInflater mInflater;
	
	/** The m popup. */
	private IcsListPopupWindow mPopup;
	
	/** The m menu. */
	private MenuBuilder mMenu;
	
	/** The m popup max width. */
	private int mPopupMaxWidth;
	
	/** The m anchor view. */
	private View mAnchorView;
	
	/** The m overflow only. */
	private boolean mOverflowOnly;
	
	/** The m tree observer. */
	private ViewTreeObserver mTreeObserver;

	/** The m adapter. */
	private MenuAdapter mAdapter;

	/** The m presenter callback. */
	private Callback mPresenterCallback;

	/** The m force show icon. */
	boolean mForceShowIcon;

	/** The m measure parent. */
	private ViewGroup mMeasureParent;

	/**
	 * Instantiates a new menu popup helper.
	 * 
	 * @param context
	 *            the context
	 * @param menu
	 *            the menu
	 */
	public MenuPopupHelper(Context context, MenuBuilder menu) {
		this(context, menu, null, false);
	}

	/**
	 * Instantiates a new menu popup helper.
	 * 
	 * @param context
	 *            the context
	 * @param menu
	 *            the menu
	 * @param anchorView
	 *            the anchor view
	 */
	public MenuPopupHelper(Context context, MenuBuilder menu, View anchorView) {
		this(context, menu, anchorView, false);
	}

	/**
	 * Instantiates a new menu popup helper.
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
	public MenuPopupHelper(Context context, MenuBuilder menu, View anchorView,
			boolean overflowOnly) {
		mContext = context;
		mInflater = LayoutInflater.from(context);
		mMenu = menu;
		mOverflowOnly = overflowOnly;

		final Resources res = context.getResources();
		mPopupMaxWidth = Math.max(res.getDisplayMetrics().widthPixels / 2,
				res.getDimensionPixelSize(R.dimen.abs__config_prefDialogWidth));

		mAnchorView = anchorView;

		menu.addMenuPresenter(this);
	}

	/**
	 * Sets the anchor view.
	 * 
	 * @param anchor
	 *            the new anchor view
	 */
	public void setAnchorView(View anchor) {
		mAnchorView = anchor;
	}

	/**
	 * Sets the force show icon.
	 * 
	 * @param forceShow
	 *            the new force show icon
	 */
	public void setForceShowIcon(boolean forceShow) {
		mForceShowIcon = forceShow;
	}

	/**
	 * Show.
	 */
	public void show() {
		if (!tryShow()) {
			throw new IllegalStateException(
					"MenuPopupHelper cannot be used without an anchor");
		}
	}

	/**
	 * Try show.
	 * 
	 * @return true, if successful
	 */
	public boolean tryShow() {
		mPopup = new IcsListPopupWindow(mContext, null, R.attr.popupMenuStyle);
		mPopup.setOnDismissListener(this);
		mPopup.setOnItemClickListener(this);

		mAdapter = new MenuAdapter(mMenu);
		mPopup.setAdapter(mAdapter);
		mPopup.setModal(true);

		View anchor = mAnchorView;
		if (anchor != null) {
			final boolean addGlobalListener = mTreeObserver == null;
			mTreeObserver = anchor.getViewTreeObserver(); // Refresh to latest
			if (addGlobalListener)
				mTreeObserver.addOnGlobalLayoutListener(this);
			((View_HasStateListenerSupport) anchor)
					.addOnAttachStateChangeListener(this);
			mPopup.setAnchorView(anchor);
		} else {
			return false;
		}

		mPopup.setContentWidth(Math.min(measureContentWidth(mAdapter),
				mPopupMaxWidth));
		mPopup.setInputMethodMode(PopupWindow.INPUT_METHOD_NOT_NEEDED);
		mPopup.show();
		mPopup.getListView().setOnKeyListener(this);
		return true;
	}

	/**
	 * Dismiss.
	 */
	public void dismiss() {
		if (isShowing()) {
			mPopup.dismiss();
		}
	}

	/** 
	* <p>Title: onDismiss</p> 
	* <p>Description: </p>  
	* @see android.widget.PopupWindow.OnDismissListener#onDismiss() 
	*/
	@Override
	public void onDismiss() {
		mPopup = null;
		mMenu.close();
		if (mTreeObserver != null) {
			if (!mTreeObserver.isAlive())
				mTreeObserver = mAnchorView.getViewTreeObserver();
			mTreeObserver.removeGlobalOnLayoutListener(this);
			mTreeObserver = null;
		}
		((View_HasStateListenerSupport) mAnchorView)
				.removeOnAttachStateChangeListener(this);
	}

	/**
	 * Checks if is showing.
	 * 
	 * @return true, if is showing
	 */
	public boolean isShowing() {
		return mPopup != null && mPopup.isShowing();
	}

	/** 
	* <p>Title: onItemClick</p> 
	* <p>Description: </p> 
	* @param parent
	* @param view
	* @param position
	* @param id 
	* @see android.widget.AdapterView.OnItemClickListener#onItemClick(android.widget.AdapterView, android.view.View, int, long) 
	*/
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		MenuAdapter adapter = mAdapter;
		adapter.mAdapterMenu.performItemAction(adapter.getItem(position), 0);
	}

	/** 
	* <p>Title: onKey</p> 
	* <p>Description: </p> 
	* @param v
	* @param keyCode
	* @param event
	* @return 
	* @see android.view.View.OnKeyListener#onKey(android.view.View, int, android.view.KeyEvent) 
	*/
	@Override
	public boolean onKey(View v, int keyCode, KeyEvent event) {
		if (event.getAction() == KeyEvent.ACTION_UP
				&& keyCode == KeyEvent.KEYCODE_MENU) {
			dismiss();
			return true;
		}
		return false;
	}

	/**
	 * Measure content width.
	 * 
	 * @param adapter
	 *            the adapter
	 * @return the int
	 */
	private int measureContentWidth(ListAdapter adapter) {
		// Menus don't tend to be long, so this is more sane than it looks.
		int width = 0;
		View itemView = null;
		int itemType = 0;
		final int widthMeasureSpec = MeasureSpec.makeMeasureSpec(0,
				MeasureSpec.UNSPECIFIED);
		final int heightMeasureSpec = MeasureSpec.makeMeasureSpec(0,
				MeasureSpec.UNSPECIFIED);
		final int count = adapter.getCount();
		for (int i = 0; i < count; i++) {
			final int positionType = adapter.getItemViewType(i);
			if (positionType != itemType) {
				itemType = positionType;
				itemView = null;
			}
			if (mMeasureParent == null) {
				mMeasureParent = new FrameLayout(mContext);
			}
			itemView = adapter.getView(i, itemView, mMeasureParent);
			itemView.measure(widthMeasureSpec, heightMeasureSpec);
			width = Math.max(width, itemView.getMeasuredWidth());
		}
		return width;
	}

	/** 
	* <p>Title: onGlobalLayout</p> 
	* <p>Description: </p>  
	* @see android.view.ViewTreeObserver.OnGlobalLayoutListener#onGlobalLayout() 
	*/
	@Override
	public void onGlobalLayout() {
		if (isShowing()) {
			final View anchor = mAnchorView;
			if (anchor == null || !anchor.isShown()) {
				dismiss();
			} else if (isShowing()) {
				// Recompute window size and position
				mPopup.show();
			}
		}
	}

	/** 
	* <p>Title: onViewAttachedToWindow</p> 
	* <p>Description: </p> 
	* @param v 
	* @see com.actionbarsherlock.internal.view.View_OnAttachStateChangeListener#onViewAttachedToWindow(android.view.View) 
	*/
	@Override
	public void onViewAttachedToWindow(View v) {
	}

	/** 
	* <p>Title: onViewDetachedFromWindow</p> 
	* <p>Description: </p> 
	* @param v 
	* @see com.actionbarsherlock.internal.view.View_OnAttachStateChangeListener#onViewDetachedFromWindow(android.view.View) 
	*/
	@Override
	public void onViewDetachedFromWindow(View v) {
		if (mTreeObserver != null) {
			if (!mTreeObserver.isAlive())
				mTreeObserver = v.getViewTreeObserver();
			mTreeObserver.removeGlobalOnLayoutListener(this);
		}
		((View_HasStateListenerSupport) v)
				.removeOnAttachStateChangeListener(this);
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
		// Don't need to do anything; we added as a presenter in the
		// constructor.
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
		throw new UnsupportedOperationException(
				"MenuPopupHelpers manage their own views");
	}

	/** 
	* <p>Title: updateMenuView</p> 
	* <p>Description: </p> 
	* @param cleared 
	* @see com.actionbarsherlock.internal.view.menu.MenuPresenter#updateMenuView(boolean) 
	*/
	@Override
	public void updateMenuView(boolean cleared) {
		if (mAdapter != null)
			mAdapter.notifyDataSetChanged();
	}

	/** 
	* <p>Title: setCallback</p> 
	* <p>Description: </p> 
	* @param cb 
	* @see com.actionbarsherlock.internal.view.menu.MenuPresenter#setCallback(com.actionbarsherlock.internal.view.menu.MenuPresenter.Callback) 
	*/
	@Override
	public void setCallback(Callback cb) {
		mPresenterCallback = cb;
	}

	/** 
	* <p>Title: onSubMenuSelected</p> 
	* <p>Description: </p> 
	* @param subMenu
	* @return 
	* @see com.actionbarsherlock.internal.view.menu.MenuPresenter#onSubMenuSelected(com.actionbarsherlock.internal.view.menu.SubMenuBuilder) 
	*/
	@Override
	public boolean onSubMenuSelected(SubMenuBuilder subMenu) {
		if (subMenu.hasVisibleItems()) {
			MenuPopupHelper subPopup = new MenuPopupHelper(mContext, subMenu,
					mAnchorView, false);
			subPopup.setCallback(mPresenterCallback);

			boolean preserveIconSpacing = false;
			final int count = subMenu.size();
			for (int i = 0; i < count; i++) {
				MenuItem childItem = subMenu.getItem(i);
				if (childItem.isVisible() && childItem.getIcon() != null) {
					preserveIconSpacing = true;
					break;
				}
			}
			subPopup.setForceShowIcon(preserveIconSpacing);

			if (subPopup.tryShow()) {
				if (mPresenterCallback != null) {
					mPresenterCallback.onOpenSubMenu(subMenu);
				}
				return true;
			}
		}
		return false;
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
		// Only care about the (sub)menu we're presenting.
		if (menu != mMenu)
			return;

		dismiss();
		if (mPresenterCallback != null) {
			mPresenterCallback.onCloseMenu(menu, allMenusAreClosing);
		}
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
		return 0;
	}

	/** 
	* <p>Title: onSaveInstanceState</p> 
	* <p>Description: </p> 
	* @return 
	* @see com.actionbarsherlock.internal.view.menu.MenuPresenter#onSaveInstanceState() 
	*/
	@Override
	public Parcelable onSaveInstanceState() {
		return null;
	}

	/** 
	* <p>Title: onRestoreInstanceState</p> 
	* <p>Description: </p> 
	* @param state 
	* @see com.actionbarsherlock.internal.view.menu.MenuPresenter#onRestoreInstanceState(android.os.Parcelable) 
	*/
	@Override
	public void onRestoreInstanceState(Parcelable state) {
	}

	/**
	 * The Class MenuAdapter.
	 */
	private class MenuAdapter extends BaseAdapter {
		
		/** The m adapter menu. */
		private MenuBuilder mAdapterMenu;
		
		/** The m expanded index. */
		private int mExpandedIndex = -1;

		/**
		 * Instantiates a new menu adapter.
		 * 
		 * @param menu
		 *            the menu
		 */
		public MenuAdapter(MenuBuilder menu) {
			mAdapterMenu = menu;
			registerDataSetObserver(new ExpandedIndexObserver());
			findExpandedIndex();
		}

		/** 
		* <p>Title: getCount</p> 
		* <p>Description: </p> 
		* @return 
		* @see android.widget.Adapter#getCount() 
		*/
		@Override
		public int getCount() {
			ArrayList<MenuItemImpl> items = mOverflowOnly ? mAdapterMenu
					.getNonActionItems() : mAdapterMenu.getVisibleItems();
			if (mExpandedIndex < 0) {
				return items.size();
			}
			return items.size() - 1;
		}

		/** 
		* <p>Title: getItem</p> 
		* <p>Description: </p> 
		* @param position
		* @return 
		* @see android.widget.Adapter#getItem(int) 
		*/
		@Override
		public MenuItemImpl getItem(int position) {
			ArrayList<MenuItemImpl> items = mOverflowOnly ? mAdapterMenu
					.getNonActionItems() : mAdapterMenu.getVisibleItems();
			if (mExpandedIndex >= 0 && position >= mExpandedIndex) {
				position++;
			}
			return items.get(position);
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
			// Since a menu item's ID is optional, we'll use the position as an
			// ID for the item in the AdapterView
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
				convertView = mInflater.inflate(ITEM_LAYOUT, parent, false);
			}

			MenuView.ItemView itemView = (MenuView.ItemView) convertView;
			if (mForceShowIcon) {
				((ListMenuItemView) convertView).setForceShowIcon(true);
			}
			itemView.initialize(getItem(position), 0);
			return convertView;
		}

		/**
		 * Find expanded index.
		 */
		void findExpandedIndex() {
			final MenuItemImpl expandedItem = mMenu.getExpandedItem();
			if (expandedItem != null) {
				final ArrayList<MenuItemImpl> items = mMenu.getNonActionItems();
				final int count = items.size();
				for (int i = 0; i < count; i++) {
					final MenuItemImpl item = items.get(i);
					if (item == expandedItem) {
						mExpandedIndex = i;
						return;
					}
				}
			}
			mExpandedIndex = -1;
		}
	}

	/**
	 * An asynchronous update interface for receiving notifications about
	 * ExpandedIndex information as the ExpandedIndex is constructed.
	 */
	private class ExpandedIndexObserver extends DataSetObserver {
		
		/** 
		* <p>Title: onChanged</p> 
		* <p>Description: </p>  
		* @see android.database.DataSetObserver#onChanged() 
		*/
		@Override
		public void onChanged() {
			mAdapter.findExpandedIndex();
		}
	}
}
