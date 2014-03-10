/**   
 * @Title: ActionMenuItemView.java 
 * @Package com.actionbarsherlock.internal.view.menu 
 * @Description: 
 *			澹版槑:
 *			1)鎺屾帶鏍″洯鏄湰浜虹殑澶у鐨勫垱涓氫綔鍝�涔熸槸鏈汉鐨勬瘯涓氳璁�*			2)鏈▼搴忓皻鏈繘琛屽紑鏀炬簮浠ｇ爜,鎵�互绂佹缁勭粐鎴栬�鏄釜浜烘硠闇叉簮鐮�鍚﹀垯灏嗕細杩界┒鍏跺垜浜嬭矗浠�*			3)缂栧啓鏈蒋浠�鎴戦渶瑕佹劅璋㈠江鑰佸笀浠ュ強鍏朵粬榧撳姳鍜屾敮鎸佹垜鐨勫悓瀛︿互鍙婃湅鍙�*			4)鏈▼搴忕殑鏈�粓鎵�湁鏉冨睘浜庢湰浜�
 * @author  寮犻浄 794857063@qq.com
 * @date 2013-11-14 19:34:18 
 * @version V1.0   
 */

package com.actionbarsherlock.internal.view.menu;

import java.util.HashSet;
import java.util.Set;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.accessibility.AccessibilityEvent;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;


import com.actionbarsherlock.internal.view.View_HasStateListenerSupport;
import com.actionbarsherlock.internal.view.View_OnAttachStateChangeListener;
import com.actionbarsherlock.internal.widget.CapitalizingButton;
import com.sendwine.app.R;

import static com.actionbarsherlock.internal.ResourcesCompat.getResources_getBoolean;

// TODO: Auto-generated Javadoc
/**
 * The Class ActionMenuItemView.
 * 
 * @hide
 */
public class ActionMenuItemView extends LinearLayout implements
		MenuView.ItemView, View.OnClickListener, View.OnLongClickListener,
		ActionMenuView.ActionMenuChildView, View_HasStateListenerSupport {
	// UNUSED private static final String TAG = "ActionMenuItemView";

	/** The m item data. */
	private MenuItemImpl mItemData;

	/** The m title. */
	private CharSequence mTitle;

	/** The m item invoker. */
	private MenuBuilder.ItemInvoker mItemInvoker;

	/** The m image button. */
	private ImageButton mImageButton;

	/** The m text button. */
	private CapitalizingButton mTextButton;

	/** The m allow text with icon. */
	private boolean mAllowTextWithIcon;

	/** The m expanded format. */
	private boolean mExpandedFormat;

	/** The m min width. */
	private int mMinWidth;

	/** The m listeners. */
	private final Set<View_OnAttachStateChangeListener> mListeners = new HashSet<View_OnAttachStateChangeListener>();

	/**
	 * Instantiates a new action menu item view.
	 * 
	 * @param context
	 *            the context
	 */
	public ActionMenuItemView(Context context) {
		this(context, null);
	}

	/**
	 * Instantiates a new action menu item view.
	 * 
	 * @param context
	 *            the context
	 * @param attrs
	 *            the attrs
	 */
	public ActionMenuItemView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	/**
	 * Instantiates a new action menu item view.
	 * 
	 * @param context
	 *            the context
	 * @param attrs
	 *            the attrs
	 * @param defStyle
	 *            the def style
	 */
	public ActionMenuItemView(Context context, AttributeSet attrs, int defStyle) {
		// TODO super(context, attrs, defStyle);
		super(context, attrs);
		mAllowTextWithIcon = getResources_getBoolean(context,
				R.bool.abs__config_allowActionMenuItemTextWithIcon);
		TypedArray a = context.obtainStyledAttributes(attrs,
				R.styleable.SherlockActionMenuItemView, 0, 0);
		mMinWidth = a.getDimensionPixelSize(
				R.styleable.SherlockActionMenuItemView_android_minWidth, 0);
		a.recycle();
	}

	/**
	 * <p>
	 * Title: addOnAttachStateChangeListener
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @param listener
	 * @see com.actionbarsherlock.internal.view.View_HasStateListenerSupport#addOnAttachStateChangeListener(com.actionbarsherlock.internal.view.View_OnAttachStateChangeListener)
	 */
	@Override
	public void addOnAttachStateChangeListener(
			View_OnAttachStateChangeListener listener) {
		mListeners.add(listener);
	}

	/**
	 * <p>
	 * Title: removeOnAttachStateChangeListener
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @param listener
	 * @see com.actionbarsherlock.internal.view.View_HasStateListenerSupport#removeOnAttachStateChangeListener(com.actionbarsherlock.internal.view.View_OnAttachStateChangeListener)
	 */
	@Override
	public void removeOnAttachStateChangeListener(
			View_OnAttachStateChangeListener listener) {
		mListeners.remove(listener);
	}

	/**
	 * <p>
	 * Title: onAttachedToWindow
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
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
	 * <p>
	 * Title: onDetachedFromWindow
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @see android.view.View#onDetachedFromWindow()
	 */
	@Override
	protected void onDetachedFromWindow() {
		super.onDetachedFromWindow();
		for (View_OnAttachStateChangeListener listener : mListeners) {
			listener.onViewDetachedFromWindow(this);
		}
	}

	/**
	 * <p>
	 * Title: onFinishInflate
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @see android.view.View#onFinishInflate()
	 */
	@Override
	public void onFinishInflate() {

		mImageButton = (ImageButton) findViewById(R.id.abs__imageButton);
		mTextButton = (CapitalizingButton) findViewById(R.id.abs__textButton);
		mImageButton.setOnClickListener(this);
		mTextButton.setOnClickListener(this);
		mImageButton.setOnLongClickListener(this);
		setOnClickListener(this);
		setOnLongClickListener(this);
	}

	/**
	 * <p>
	 * Title: getItemData
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @return
	 * @see com.actionbarsherlock.internal.view.menu.MenuView.ItemView#getItemData()
	 */
	@Override
	public MenuItemImpl getItemData() {
		return mItemData;
	}

	/**
	 * <p>
	 * Title: initialize
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @param itemData
	 * @param menuType
	 * @see com.actionbarsherlock.internal.view.menu.MenuView.ItemView#initialize(com.actionbarsherlock.internal.view.menu.MenuItemImpl,
	 *      int)
	 */
	@Override
	public void initialize(MenuItemImpl itemData, int menuType) {
		mItemData = itemData;

		setIcon(itemData.getIcon());
		setTitle(itemData.getTitleForItemView(this)); // Title only takes effect
														// if there is no icon
		setId(itemData.getItemId());

		setVisibility(itemData.isVisible() ? View.VISIBLE : View.GONE);
		setEnabled(itemData.isEnabled());
	}

	/**
	 * <p>
	 * Title: setEnabled
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @param enabled
	 * @see android.view.View#setEnabled(boolean)
	 */
	@Override
	public void setEnabled(boolean enabled) {
		super.setEnabled(enabled);
		mImageButton.setEnabled(enabled);
		mTextButton.setEnabled(enabled);
	}

	/**
	 * <p>
	 * Title: onClick
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @param v
	 * @see android.view.View.OnClickListener#onClick(android.view.View)
	 */
	@Override
	public void onClick(View v) {
		if (mItemInvoker != null) {
			mItemInvoker.invokeItem(mItemData);
		}
	}

	/**
	 * Sets the item invoker.
	 * 
	 * @param invoker
	 *            the new item invoker
	 */
	public void setItemInvoker(MenuBuilder.ItemInvoker invoker) {
		mItemInvoker = invoker;
	}

	/**
	 * <p>
	 * Title: prefersCondensedTitle
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @return
	 * @see com.actionbarsherlock.internal.view.menu.MenuView.ItemView#prefersCondensedTitle()
	 */
	@Override
	public boolean prefersCondensedTitle() {
		return true;
	}

	/**
	 * <p>
	 * Title: setCheckable
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @param checkable
	 * @see com.actionbarsherlock.internal.view.menu.MenuView.ItemView#setCheckable(boolean)
	 */
	@Override
	public void setCheckable(boolean checkable) {
		// TODO Support checkable action items
	}

	/**
	 * <p>
	 * Title: setChecked
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @param checked
	 * @see com.actionbarsherlock.internal.view.menu.MenuView.ItemView#setChecked(boolean)
	 */
	@Override
	public void setChecked(boolean checked) {
		// TODO Support checkable action items
	}

	/**
	 * Sets the expanded format.
	 * 
	 * @param expandedFormat
	 *            the new expanded format
	 */
	public void setExpandedFormat(boolean expandedFormat) {
		if (mExpandedFormat != expandedFormat) {
			mExpandedFormat = expandedFormat;
			if (mItemData != null) {
				mItemData.actionFormatChanged();
			}
		}
	}

	/**
	 * Update text button visibility.
	 */
	private void updateTextButtonVisibility() {
		boolean visible = !TextUtils.isEmpty(mTextButton.getText());
		visible &= mImageButton.getDrawable() == null
				|| (mItemData.showsTextAsAction() && (mAllowTextWithIcon || mExpandedFormat));

		mTextButton.setVisibility(visible ? VISIBLE : GONE);
	}

	/**
	 * <p>
	 * Title: setIcon
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @param icon
	 * @see com.actionbarsherlock.internal.view.menu.MenuView.ItemView#setIcon(android.graphics.drawable.Drawable)
	 */
	@Override
	public void setIcon(Drawable icon) {
		mImageButton.setImageDrawable(icon);
		if (icon != null) {
			mImageButton.setVisibility(VISIBLE);
		} else {
			mImageButton.setVisibility(GONE);
		}

		updateTextButtonVisibility();
	}

	/**
	 * Checks for text.
	 * 
	 * @return true, if successful
	 */
	public boolean hasText() {
		return mTextButton.getVisibility() != GONE;
	}

	/**
	 * <p>
	 * Title: setShortcut
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @param showShortcut
	 * @param shortcutKey
	 * @see com.actionbarsherlock.internal.view.menu.MenuView.ItemView#setShortcut(boolean,
	 *      char)
	 */
	@Override
	public void setShortcut(boolean showShortcut, char shortcutKey) {
		// Action buttons don't show text for shortcut keys.
	}

	/**
	 * <p>
	 * Title: setTitle
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @param title
	 * @see com.actionbarsherlock.internal.view.menu.MenuView.ItemView#setTitle(java.lang.CharSequence)
	 */
	@Override
	public void setTitle(CharSequence title) {
		mTitle = title;

		mTextButton.setTextCompat(mTitle);

		setContentDescription(mTitle);
		updateTextButtonVisibility();
	}

	/**
	 * <p>
	 * Title: dispatchPopulateAccessibilityEvent
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @param event
	 * @return
	 * @see android.view.View#dispatchPopulateAccessibilityEvent(android.view.accessibility.AccessibilityEvent)
	 */
	@Override
	public boolean dispatchPopulateAccessibilityEvent(AccessibilityEvent event) {
		onPopulateAccessibilityEvent(event);
		return true;
	}

	/**
	 * <p>
	 * Title: onPopulateAccessibilityEvent
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @param event
	 * @see android.view.View#onPopulateAccessibilityEvent(android.view.accessibility.AccessibilityEvent)
	 */
	@Override
	public void onPopulateAccessibilityEvent(AccessibilityEvent event) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
			super.onPopulateAccessibilityEvent(event);
		}
		final CharSequence cdesc = getContentDescription();
		if (!TextUtils.isEmpty(cdesc)) {
			event.getText().add(cdesc);
		}
	}

	/**
	 * <p>
	 * Title: dispatchHoverEvent
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @param event
	 * @return
	 * @see android.view.ViewGroup#dispatchHoverEvent(android.view.MotionEvent)
	 */
	@Override
	public boolean dispatchHoverEvent(MotionEvent event) {
		// Don't allow children to hover; we want this to be treated as a single
		// component.
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
			return onHoverEvent(event);
		}
		return false;
	}

	/**
	 * <p>
	 * Title: showsIcon
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @return
	 * @see com.actionbarsherlock.internal.view.menu.MenuView.ItemView#showsIcon()
	 */
	@Override
	public boolean showsIcon() {
		return true;
	}

	/**
	 * <p>
	 * Title: needsDividerBefore
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @return
	 * @see com.actionbarsherlock.internal.view.menu.ActionMenuView.ActionMenuChildView#needsDividerBefore()
	 */
	@Override
	public boolean needsDividerBefore() {
		return hasText() && mItemData.getIcon() == null;
	}

	/**
	 * <p>
	 * Title: needsDividerAfter
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @return
	 * @see com.actionbarsherlock.internal.view.menu.ActionMenuView.ActionMenuChildView#needsDividerAfter()
	 */
	@Override
	public boolean needsDividerAfter() {
		return hasText();
	}

	/**
	 * <p>
	 * Title: onLongClick
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @param v
	 * @return
	 * @see android.view.View.OnLongClickListener#onLongClick(android.view.View)
	 */
	@Override
	public boolean onLongClick(View v) {
		if (hasText()) {
			// Don't show the cheat sheet for items that already show text.
			return false;
		}

		final int[] screenPos = new int[2];
		final Rect displayFrame = new Rect();
		getLocationOnScreen(screenPos);
		getWindowVisibleDisplayFrame(displayFrame);

		final Context context = getContext();
		final int width = getWidth();
		final int height = getHeight();
		final int midy = screenPos[1] + height / 2;
		final int screenWidth = context.getResources().getDisplayMetrics().widthPixels;

		Toast cheatSheet = Toast.makeText(context, mItemData.getTitle(),
				Toast.LENGTH_SHORT);
		if (midy < displayFrame.height()) {
			// Show along the top; follow action buttons
			cheatSheet.setGravity(Gravity.TOP | Gravity.RIGHT, screenWidth
					- screenPos[0] - width / 2, height);
		} else {
			// Show along the bottom center
			cheatSheet.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL,
					0, height);
		}
		cheatSheet.show();
		return true;
	}

	/**
	 * <p>
	 * Title: onMeasure
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @param widthMeasureSpec
	 * @param heightMeasureSpec
	 * @see android.widget.LinearLayout#onMeasure(int, int)
	 */
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);

		final int widthMode = MeasureSpec.getMode(widthMeasureSpec);
		final int specSize = MeasureSpec.getSize(widthMeasureSpec);
		final int oldMeasuredWidth = getMeasuredWidth();
		final int targetWidth = widthMode == MeasureSpec.AT_MOST ? Math.min(
				specSize, mMinWidth) : mMinWidth;

		if (widthMode != MeasureSpec.EXACTLY && mMinWidth > 0
				&& oldMeasuredWidth < targetWidth) {
			// Remeasure at exactly the minimum width.
			super.onMeasure(MeasureSpec.makeMeasureSpec(targetWidth,
					MeasureSpec.EXACTLY), heightMeasureSpec);
		}
	}
}
