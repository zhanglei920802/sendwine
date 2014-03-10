/**   
* @Title: ObjectAnimator.java 
* @Package com.actionbarsherlock.internal.nineoldandroids.animation 
* @Description: 
*			澹版槑:
*			1)鎺屾帶鏍″洯鏄湰浜虹殑澶у鐨勫垱涓氫綔鍝�涔熸槸鏈汉鐨勬瘯涓氳璁�*			2)鏈▼搴忓皻鏈繘琛屽紑鏀炬簮浠ｇ爜,鎵�互绂佹缁勭粐鎴栬�鏄釜浜烘硠闇叉簮鐮�鍚﹀垯灏嗕細杩界┒鍏跺垜浜嬭矗浠�*			3)缂栧啓鏈蒋浠�鎴戦渶瑕佹劅璋㈠江鑰佸笀浠ュ強鍏朵粬榧撳姳鍜屾敮鎸佹垜鐨勫悓瀛︿互鍙婃湅鍙�*			4)鏈▼搴忕殑鏈�粓鎵�湁鏉冨睘浜庢湰浜�
* @author  寮犻浄 794857063@qq.com
* @date 2013-11-14 19:33:47 
* @version V1.0   
*/

package com.actionbarsherlock.internal.view.menu;

import com.sendwine.app.R;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

// TODO: Auto-generated Javadoc
/**
 * The item view for each item in the ListView-based MenuViews.
 */
public class ListMenuItemView extends LinearLayout implements MenuView.ItemView {
	
	/** The m item data. */
	private MenuItemImpl mItemData;

	/** The m icon view. */
	private ImageView mIconView;
	
	/** The m radio button. */
	private RadioButton mRadioButton;
	
	/** The m title view. */
	private TextView mTitleView;
	
	/**
	 * The Object animator of object( object target, string property name, type
	 * evaluator evaluator, object[] values).
	 */
	private CheckBox mCheckBox;
	
	/** The m shortcut view. */
	private TextView mShortcutView;

	/** The m background. */
	private Drawable mBackground;
	
	/** The m text appearance. */
	private int mTextAppearance;
	
	/** The m text appearance context. */
	private Context mTextAppearanceContext;
	
	/** The m preserve icon spacing. */
	private boolean mPreserveIconSpacing;

	// UNUSED private int mMenuType;

	/** The m inflater. */
	private LayoutInflater mInflater;

	/** The m force show icon. */
	private boolean mForceShowIcon;

	/** The m context. */
	final Context mContext;

	/**
	 * Instantiates a new list menu item view.
	 * 
	 * @param context
	 *            the context
	 * @param attrs
	 *            the attrs
	 * @param defStyle
	 *            the def style
	 */
	public ListMenuItemView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs);
		mContext = context;

		TypedArray a = context.obtainStyledAttributes(attrs,
				R.styleable.SherlockMenuView, defStyle, 0);

		mBackground = a
				.getDrawable(R.styleable.SherlockMenuView_itemBackground);
		mTextAppearance = a.getResourceId(
				R.styleable.SherlockMenuView_itemTextAppearance, -1);
		mPreserveIconSpacing = a.getBoolean(
				R.styleable.SherlockMenuView_preserveIconSpacing, false);
		mTextAppearanceContext = context;

		a.recycle();
	}

	/**
	 * Instantiates a new list menu item view.
	 * 
	 * @param context
	 *            the context
	 * @param attrs
	 *            the attrs
	 */
	public ListMenuItemView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	/** 
	* <p>Title: onFinishInflate</p> 
	* <p>Description: </p>  
	* @see android.view.View#onFinishInflate() 
	*/
	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();

		setBackgroundDrawable(mBackground);

		mTitleView = (TextView) findViewById(R.id.abs__title);
		if (mTextAppearance != -1) {
			mTitleView.setTextAppearance(mTextAppearanceContext,
					mTextAppearance);
		}

		mShortcutView = (TextView) findViewById(R.id.abs__shortcut);
	}

	/** 
	* <p>Title: initialize</p> 
	* <p>Description: </p> 
	* @param itemData
	* @param menuType 
	* @see com.actionbarsherlock.internal.view.menu.MenuView.ItemView#initialize(com.actionbarsherlock.internal.view.menu.MenuItemImpl, int) 
	*/
	@Override
	public void initialize(MenuItemImpl itemData, int menuType) {
		mItemData = itemData;
		// UNUSED mMenuType = menuType;

		setVisibility(itemData.isVisible() ? View.VISIBLE : View.GONE);

		setTitle(itemData.getTitleForItemView(this));
		setCheckable(itemData.isCheckable());
		setShortcut(itemData.shouldShowShortcut(), itemData.getShortcut());
		setIcon(itemData.getIcon());
		setEnabled(itemData.isEnabled());
	}

	/**
	 * Sets the force show icon.
	 * 
	 * @param forceShow
	 *            the new force show icon
	 */
	public void setForceShowIcon(boolean forceShow) {
		mPreserveIconSpacing = mForceShowIcon = forceShow;
	}

	/** 
	* <p>Title: setTitle</p> 
	* <p>Description: </p> 
	* @param title 
	* @see com.actionbarsherlock.internal.view.menu.MenuView.ItemView#setTitle(java.lang.CharSequence) 
	*/
	@Override
	public void setTitle(CharSequence title) {
		if (title != null) {
			mTitleView.setText(title);

			if (mTitleView.getVisibility() != VISIBLE)
				mTitleView.setVisibility(VISIBLE);
		} else {
			if (mTitleView.getVisibility() != GONE)
				mTitleView.setVisibility(GONE);
		}
	}

	/** 
	* <p>Title: getItemData</p> 
	* <p>Description: </p> 
	* @return 
	* @see com.actionbarsherlock.internal.view.menu.MenuView.ItemView#getItemData() 
	*/
	@Override
	public MenuItemImpl getItemData() {
		return mItemData;
	}

	/** 
	* <p>Title: setCheckable</p> 
	* <p>Description: </p> 
	* @param checkable 
	* @see com.actionbarsherlock.internal.view.menu.MenuView.ItemView#setCheckable(boolean) 
	*/
	@Override
	public void setCheckable(boolean checkable) {

		if (!checkable && mRadioButton == null && mCheckBox == null) {
			return;
		}

		if (mRadioButton == null) {
			insertRadioButton();
		}
		if (mCheckBox == null) {
			insertCheckBox();
		}

		// Depending on whether its exclusive check or not, the checkbox or
		// radio button will be the one in use (and the other will be
		// otherCompoundButton)
		final CompoundButton compoundButton;
		final CompoundButton otherCompoundButton;

		if (mItemData.isExclusiveCheckable()) {
			compoundButton = mRadioButton;
			otherCompoundButton = mCheckBox;
		} else {
			compoundButton = mCheckBox;
			otherCompoundButton = mRadioButton;
		}

		if (checkable) {
			compoundButton.setChecked(mItemData.isChecked());

			final int newVisibility = checkable ? VISIBLE : GONE;
			if (compoundButton.getVisibility() != newVisibility) {
				compoundButton.setVisibility(newVisibility);
			}

			// Make sure the other compound button isn't visible
			if (otherCompoundButton.getVisibility() != GONE) {
				otherCompoundButton.setVisibility(GONE);
			}
		} else {
			mCheckBox.setVisibility(GONE);
			mRadioButton.setVisibility(GONE);
		}
	}

	/** 
	* <p>Title: setChecked</p> 
	* <p>Description: </p> 
	* @param checked 
	* @see com.actionbarsherlock.internal.view.menu.MenuView.ItemView#setChecked(boolean) 
	*/
	@Override
	public void setChecked(boolean checked) {
		CompoundButton compoundButton;

		if (mItemData.isExclusiveCheckable()) {
			if (mRadioButton == null) {
				insertRadioButton();
			}
			compoundButton = mRadioButton;
		} else {
			if (mCheckBox == null) {
				insertCheckBox();
			}
			compoundButton = mCheckBox;
		}

		compoundButton.setChecked(checked);
	}

	/** 
	* <p>Title: setShortcut</p> 
	* <p>Description: </p> 
	* @param showShortcut
	* @param shortcutKey 
	* @see com.actionbarsherlock.internal.view.menu.MenuView.ItemView#setShortcut(boolean, char) 
	*/
	@Override
	public void setShortcut(boolean showShortcut, char shortcutKey) {
		final int newVisibility = (showShortcut && mItemData
				.shouldShowShortcut()) ? VISIBLE : GONE;

		if (newVisibility == VISIBLE) {
			mShortcutView.setText(mItemData.getShortcutLabel());
		}

		if (mShortcutView.getVisibility() != newVisibility) {
			mShortcutView.setVisibility(newVisibility);
		}
	}

	/** 
	* <p>Title: setIcon</p> 
	* <p>Description: </p> 
	* @param icon 
	* @see com.actionbarsherlock.internal.view.menu.MenuView.ItemView#setIcon(android.graphics.drawable.Drawable) 
	*/
	@Override
	public void setIcon(Drawable icon) {
		final boolean showIcon = mItemData.shouldShowIcon() || mForceShowIcon;
		if (!showIcon && !mPreserveIconSpacing) {
			return;
		}

		if (mIconView == null && icon == null && !mPreserveIconSpacing) {
			return;
		}

		if (mIconView == null) {
			insertIconView();
		}

		if (icon != null || mPreserveIconSpacing) {
			mIconView.setImageDrawable(showIcon ? icon : null);

			if (mIconView.getVisibility() != VISIBLE) {
				mIconView.setVisibility(VISIBLE);
			}
		} else {
			mIconView.setVisibility(GONE);
		}
	}

	/** 
	* <p>Title: onMeasure</p> 
	* <p>Description: </p> 
	* @param widthMeasureSpec
	* @param heightMeasureSpec 
	* @see android.widget.LinearLayout#onMeasure(int, int) 
	*/
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		if (mIconView != null && mPreserveIconSpacing) {
			// Enforce minimum icon spacing
			ViewGroup.LayoutParams lp = getLayoutParams();
			LayoutParams iconLp = (LayoutParams) mIconView.getLayoutParams();
			if (lp.height > 0 && iconLp.width <= 0) {
				iconLp.width = lp.height;
			}
		}
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}

	/**
	 * Insert icon view.
	 */
	private void insertIconView() {
		LayoutInflater inflater = getInflater();
		mIconView = (ImageView) inflater.inflate(
				R.layout.abs__list_menu_item_icon, this, false);
		addView(mIconView, 0);
	}

	/**
	 * Insert radio button.
	 */
	private void insertRadioButton() {
		LayoutInflater inflater = getInflater();
		mRadioButton = (RadioButton) inflater.inflate(
				R.layout.abs__list_menu_item_radio, this, false);
		addView(mRadioButton);
	}

	/**
	 * Insert check box.
	 */
	private void insertCheckBox() {
		LayoutInflater inflater = getInflater();
		mCheckBox = (CheckBox) inflater.inflate(
				R.layout.abs__list_menu_item_checkbox, this, false);
		addView(mCheckBox);
	}

	/** 
	* <p>Title: prefersCondensedTitle</p> 
	* <p>Description: </p> 
	* @return 
	* @see com.actionbarsherlock.internal.view.menu.MenuView.ItemView#prefersCondensedTitle() 
	*/
	@Override
	public boolean prefersCondensedTitle() {
		return false;
	}

	/** 
	* <p>Title: showsIcon</p> 
	* <p>Description: </p> 
	* @return 
	* @see com.actionbarsherlock.internal.view.menu.MenuView.ItemView#showsIcon() 
	*/
	@Override
	public boolean showsIcon() {
		return mForceShowIcon;
	}

	/**
	 * Gets the inflater.
	 * 
	 * @return the inflater
	 */
	private LayoutInflater getInflater() {
		if (mInflater == null) {
			mInflater = LayoutInflater.from(mContext);
		}
		return mInflater;
	}
}
