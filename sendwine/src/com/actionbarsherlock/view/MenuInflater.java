/**   
* @Title: MenuInflater.java 
* @Package com.actionbarsherlock.view 
* @Description: 
*			澹版槑:
*			1)鎺屾帶鏍″洯鏄湰浜虹殑澶у鐨勫垱涓氫綔鍝�涔熸槸鏈汉鐨勬瘯涓氳璁�
*			2)鏈▼搴忓皻鏈繘琛屽紑鏀炬簮浠ｇ爜,鎵�互绂佹缁勭粐鎴栬�鏄釜浜烘硠闇叉簮鐮�鍚﹀垯灏嗕細杩界┒鍏跺垜浜嬭矗浠�
*			3)缂栧啓鏈蒋浠�鎴戦渶瑕佹劅璋㈠江鑰佸笀浠ュ強鍏朵粬榧撳姳鍜屾敮鎸佹垜鐨勫悓瀛︿互鍙婃湅鍙�
*			4)鏈▼搴忕殑鏈�粓鎵�湁鏉冨睘浜庢湰浜�
* @author  寮犻浄 794857063@qq.com
* @date 2013-11-14 19:33:54 
* @version V1.0   
*/

package com.actionbarsherlock.view;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import android.content.Context;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.util.Xml;
import android.view.InflateException;
import android.view.View;


import com.actionbarsherlock.internal.view.menu.MenuItemImpl;
import com.sendwine.app.R;

// TODO: Auto-generated Javadoc
/**
 * This class is used to instantiate menu XML files into Menu objects.
 * <p>
 * For performance reasons, menu inflation relies heavily on pre-processing of
 * XML files that is done at build time. Therefore, it is not currently possible
 * to use MenuInflater with an XmlPullParser over a plain XML file at runtime;
 * it only works with an XmlPullParser returned from a compiled resource (R.
 * <em>something</em> file.)
 */
public class MenuInflater {
	
	/** The Constant LOG_TAG. */
	private static final String LOG_TAG = "MenuInflater";

	/** Menu tag name in XML. */
	private static final String XML_MENU = "menu";

	/** Group tag name in XML. */
	private static final String XML_GROUP = "group";

	/** Item tag name in XML. */
	private static final String XML_ITEM = "item";

	/** The Constant NO_ID. */
	private static final int NO_ID = 0;

	/** The Constant ACTION_VIEW_CONSTRUCTOR_SIGNATURE. */
	private static final Class<?>[] ACTION_VIEW_CONSTRUCTOR_SIGNATURE = new Class[] { Context.class };

	/** The Constant ACTION_PROVIDER_CONSTRUCTOR_SIGNATURE. */
	private static final Class<?>[] ACTION_PROVIDER_CONSTRUCTOR_SIGNATURE = ACTION_VIEW_CONSTRUCTOR_SIGNATURE;

	/** The m action view constructor arguments. */
	private final Object[] mActionViewConstructorArguments;

	/** The m action provider constructor arguments. */
	private final Object[] mActionProviderConstructorArguments;

	/** The m context. */
	private Context mContext;
	
	/** The m real owner. */
	private Object mRealOwner;

	/**
	 * Constructs a menu inflater.
	 * 
	 * @param context
	 *            the context
	 * @see Activity#getMenuInflater()
	 */
	public MenuInflater(Context context) {
		mContext = context;
		mRealOwner = context;
		mActionViewConstructorArguments = new Object[] { context };
		mActionProviderConstructorArguments = mActionViewConstructorArguments;
	}

	/**
	 * Constructs a menu inflater.
	 * 
	 * @param context
	 *            the context
	 * @param realOwner
	 *            the real owner
	 * @see Activity#getMenuInflater()
	 * @hide
	 */
	public MenuInflater(Context context, Object realOwner) {
		mContext = context;
		mRealOwner = realOwner;
		mActionViewConstructorArguments = new Object[] { context };
		mActionProviderConstructorArguments = mActionViewConstructorArguments;
	}

	/**
	 * Inflate a menu hierarchy from the specified XML resource. Throws
	 * 
	 * @param menuRes
	 *            Resource ID for an XML layout resource to load (e.g.,
	 *            <code>R.menu.main_activity</code>)
	 * @param menu
	 *            The Menu to inflate into. The items and submenus will be added
	 *            to this Menu. {@link InflateException} if there is an error.
	 */
	public void inflate(int menuRes, Menu menu) {
		XmlResourceParser parser = null;
		try {
			parser = mContext.getResources().getLayout(menuRes);
			AttributeSet attrs = Xml.asAttributeSet(parser);

			parseMenu(parser, attrs, menu);
		} catch (XmlPullParserException e) {
			throw new InflateException("Error inflating menu XML", e);
		} catch (IOException e) {
			throw new InflateException("Error inflating menu XML", e);
		} finally {
			if (parser != null)
				parser.close();
		}
	}

	/**
	 * Called internally to fill the given menu. If a sub menu is seen, it will
	 * call this recursively.
	 * 
	 * @param parser
	 *            the parser
	 * @param attrs
	 *            the attrs
	 * @param menu
	 *            the menu
	 * @throws XmlPullParserException
	 *             the xml pull parser exception
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	private void parseMenu(XmlPullParser parser, AttributeSet attrs, Menu menu)
			throws XmlPullParserException, IOException {
		MenuState menuState = new MenuState(menu);

		int eventType = parser.getEventType();
		String tagName;
		boolean lookingForEndOfUnknownTag = false;
		String unknownTagName = null;

		// This loop will skip to the menu start tag
		do {
			if (eventType == XmlPullParser.START_TAG) {
				tagName = parser.getName();
				if (tagName.equals(XML_MENU)) {
					// Go to next tag
					eventType = parser.next();
					break;
				}

				throw new RuntimeException("Expecting menu, got " + tagName);
			}
			eventType = parser.next();
		} while (eventType != XmlPullParser.END_DOCUMENT);

		boolean reachedEndOfMenu = false;
		while (!reachedEndOfMenu) {
			switch (eventType) {
			case XmlPullParser.START_TAG:
				if (lookingForEndOfUnknownTag) {
					break;
				}

				tagName = parser.getName();
				if (tagName.equals(XML_GROUP)) {
					menuState.readGroup(attrs);
				} else if (tagName.equals(XML_ITEM)) {
					menuState.readItem(attrs);
				} else if (tagName.equals(XML_MENU)) {
					// A menu start tag denotes a submenu for an item
					SubMenu subMenu = menuState.addSubMenuItem();

					// Parse the submenu into returned SubMenu
					parseMenu(parser, attrs, subMenu);
				} else {
					lookingForEndOfUnknownTag = true;
					unknownTagName = tagName;
				}
				break;

			case XmlPullParser.END_TAG:
				tagName = parser.getName();
				if (lookingForEndOfUnknownTag && tagName.equals(unknownTagName)) {
					lookingForEndOfUnknownTag = false;
					unknownTagName = null;
				} else if (tagName.equals(XML_GROUP)) {
					menuState.resetGroup();
				} else if (tagName.equals(XML_ITEM)) {
					// Add the item if it hasn't been added (if the item was
					// a submenu, it would have been added already)
					if (!menuState.hasAddedItem()) {
						if (menuState.itemActionProvider != null
								&& menuState.itemActionProvider.hasSubMenu()) {
							menuState.addSubMenuItem();
						} else {
							menuState.addItem();
						}
					}
				} else if (tagName.equals(XML_MENU)) {
					reachedEndOfMenu = true;
				}
				break;

			case XmlPullParser.END_DOCUMENT:
				throw new RuntimeException("Unexpected end of document");
			}

			eventType = parser.next();
		}
	}

	/**
	 * The listener interface for receiving inflatedOnMenuItemClick events. The
	 * class that is interested in processing a inflatedOnMenuItemClick event
	 * implements this interface, and the object created with that class is
	 * registered with a component using the component's
	 * <code>addInflatedOnMenuItemClickListener<code> method. When
	 * the inflatedOnMenuItemClick event occurs, that object's appropriate
	 * method is invoked.
	 * 
	 * @see InflatedOnMenuItemClickEvent
	 */
	private static class InflatedOnMenuItemClickListener implements
			MenuItem.OnMenuItemClickListener {
		
		/** The Constant PARAM_TYPES. */
		private static final Class<?>[] PARAM_TYPES = new Class[] { MenuItem.class };

		/** The m real owner. */
		private Object mRealOwner;
		
		/** The m method. */
		private Method mMethod;

		/**
		 * Instantiates a new inflated on menu item click listener.
		 * 
		 * @param realOwner
		 *            the real owner
		 * @param methodName
		 *            the method name
		 */
		public InflatedOnMenuItemClickListener(Object realOwner,
				String methodName) {
			mRealOwner = realOwner;
			Class<?> c = realOwner.getClass();
			try {
				mMethod = c.getMethod(methodName, PARAM_TYPES);
			} catch (Exception e) {
				InflateException ex = new InflateException(
						"Couldn't resolve menu item onClick handler "
								+ methodName + " in class " + c.getName());
				ex.initCause(e);
				throw ex;
			}
		}

		/** 
		* <p>Title: onMenuItemClick</p> 
		* <p>Description: </p> 
		* @param item
		* @return 
		* @see com.actionbarsherlock.view.MenuItem.OnMenuItemClickListener#onMenuItemClick(com.actionbarsherlock.view.MenuItem) 
		*/
		@Override
		public boolean onMenuItemClick(MenuItem item) {
			try {
				if (mMethod.getReturnType() == Boolean.TYPE) {
					return (Boolean) mMethod.invoke(mRealOwner, item);
				} else {
					mMethod.invoke(mRealOwner, item);
					return true;
				}
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}
	}

	/**
	 * State for the current menu.
	 * <p>
	 * Groups can not be nested unless there is another menu (which will have
	 * its state class).
	 */
	private class MenuState {
		
		/** The menu. */
		private Menu menu;

		/*
		 * Group state is set on items as they are added, allowing an item to
		 * override its group state. (As opposed to set on items at the group
		 * end tag.)
		 */
		/** The group id. */
		private int groupId;
		
		/** The group category. */
		private int groupCategory;
		
		/** The group order. */
		private int groupOrder;
		
		/** The group checkable. */
		private int groupCheckable;
		
		/** The group visible. */
		private boolean groupVisible;
		
		/** The group enabled. */
		private boolean groupEnabled;

		/** The item added. */
		private boolean itemAdded;
		
		/** The item id. */
		private int itemId;
		
		/** The item category order. */
		private int itemCategoryOrder;
		
		/** The item title. */
		private CharSequence itemTitle;
		
		/** The item title condensed. */
		private CharSequence itemTitleCondensed;
		
		/** The item icon res id. */
		private int itemIconResId;
		
		/** The item alphabetic shortcut. */
		private char itemAlphabeticShortcut;
		
		/** The item numeric shortcut. */
		private char itemNumericShortcut;
		/**
		 * Sync to attrs.xml enum: - 0: none - 1: all - 2: exclusive
		 */
		private int itemCheckable;
		
		/** The item checked. */
		private boolean itemChecked;
		
		/** The item visible. */
		private boolean itemVisible;
		
		/** The item enabled. */
		private boolean itemEnabled;

		/**
		 * Sync to attrs.xml enum, values in MenuItem: - 0: never - 1: ifRoom -
		 * 2: always - -1: Safe sentinel for "no value".
		 */
		private int itemShowAsAction;

		/** The item action view layout. */
		private int itemActionViewLayout;
		
		/** The item action view class name. */
		private String itemActionViewClassName;
		
		/** The item action provider class name. */
		private String itemActionProviderClassName;

		/** The item listener method name. */
		private String itemListenerMethodName;

		/** The item action provider. */
		private ActionProvider itemActionProvider;

		/** The Constant defaultGroupId. */
		private static final int defaultGroupId = NO_ID;
		
		/** The Constant defaultItemId. */
		private static final int defaultItemId = NO_ID;
		
		/** The Constant defaultItemCategory. */
		private static final int defaultItemCategory = 0;
		
		/** The Constant defaultItemOrder. */
		private static final int defaultItemOrder = 0;
		
		/** The Constant defaultItemCheckable. */
		private static final int defaultItemCheckable = 0;
		
		/** The Constant defaultItemChecked. */
		private static final boolean defaultItemChecked = false;
		
		/** The Constant defaultItemVisible. */
		private static final boolean defaultItemVisible = true;
		
		/** The Constant defaultItemEnabled. */
		private static final boolean defaultItemEnabled = true;

		/**
		 * Instantiates a new menu state.
		 * 
		 * @param menu
		 *            the menu
		 */
		public MenuState(final Menu menu) {
			this.menu = menu;

			resetGroup();
		}

		/**
		 * Reset group.
		 */
		public void resetGroup() {
			groupId = defaultGroupId;
			groupCategory = defaultItemCategory;
			groupOrder = defaultItemOrder;
			groupCheckable = defaultItemCheckable;
			groupVisible = defaultItemVisible;
			groupEnabled = defaultItemEnabled;
		}

		/**
		 * Called when the parser is pointing to a group tag.
		 * 
		 * @param attrs
		 *            the attrs
		 */
		public void readGroup(AttributeSet attrs) {
			TypedArray a = mContext.obtainStyledAttributes(attrs,
					R.styleable.SherlockMenuGroup);

			groupId = a.getResourceId(R.styleable.SherlockMenuGroup_android_id,
					defaultGroupId);
			groupCategory = a.getInt(
					R.styleable.SherlockMenuGroup_android_menuCategory,
					defaultItemCategory);
			groupOrder = a.getInt(
					R.styleable.SherlockMenuGroup_android_orderInCategory,
					defaultItemOrder);
			groupCheckable = a.getInt(
					R.styleable.SherlockMenuGroup_android_checkableBehavior,
					defaultItemCheckable);
			groupVisible = a.getBoolean(
					R.styleable.SherlockMenuGroup_android_visible,
					defaultItemVisible);
			groupEnabled = a.getBoolean(
					R.styleable.SherlockMenuGroup_android_enabled,
					defaultItemEnabled);

			a.recycle();
		}

		/**
		 * Called when the parser is pointing to an item tag.
		 * 
		 * @param attrs
		 *            the attrs
		 */
		public void readItem(AttributeSet attrs) {
			TypedArray a = mContext.obtainStyledAttributes(attrs,
					R.styleable.SherlockMenuItem);

			// Inherit attributes from the group as default value
			itemId = a.getResourceId(R.styleable.SherlockMenuItem_android_id,
					defaultItemId);
			final int category = a.getInt(
					R.styleable.SherlockMenuItem_android_menuCategory,
					groupCategory);
			final int order = a.getInt(
					R.styleable.SherlockMenuItem_android_orderInCategory,
					groupOrder);
			itemCategoryOrder = (category & Menu.CATEGORY_MASK)
					| (order & Menu.USER_MASK);
			itemTitle = a.getText(R.styleable.SherlockMenuItem_android_title);
			itemTitleCondensed = a
					.getText(R.styleable.SherlockMenuItem_android_titleCondensed);
			itemIconResId = a.getResourceId(
					R.styleable.SherlockMenuItem_android_icon, 0);
			itemAlphabeticShortcut = getShortcut(a
					.getString(R.styleable.SherlockMenuItem_android_alphabeticShortcut));
			itemNumericShortcut = getShortcut(a
					.getString(R.styleable.SherlockMenuItem_android_numericShortcut));
			if (a.hasValue(R.styleable.SherlockMenuItem_android_checkable)) {
				// Item has attribute checkable, use it
				itemCheckable = a.getBoolean(
						R.styleable.SherlockMenuItem_android_checkable, false) ? 1
						: 0;
			} else {
				// Item does not have attribute, use the group's (group can have
				// one more state
				// for checkable that represents the exclusive checkable)
				itemCheckable = groupCheckable;
			}

			itemChecked = a.getBoolean(
					R.styleable.SherlockMenuItem_android_checked,
					defaultItemChecked);
			itemVisible = a.getBoolean(
					R.styleable.SherlockMenuItem_android_visible, groupVisible);
			itemEnabled = a.getBoolean(
					R.styleable.SherlockMenuItem_android_enabled, groupEnabled);

			TypedValue value = new TypedValue();
			a.getValue(R.styleable.SherlockMenuItem_android_showAsAction, value);
			itemShowAsAction = value.type == TypedValue.TYPE_INT_HEX ? value.data
					: -1;

			itemListenerMethodName = a
					.getString(R.styleable.SherlockMenuItem_android_onClick);
			itemActionViewLayout = a.getResourceId(
					R.styleable.SherlockMenuItem_android_actionLayout, 0);

			// itemActionViewClassName =
			// a.getString(R.styleable.SherlockMenuItem_android_actionViewClass);
			value = new TypedValue();
			a.getValue(R.styleable.SherlockMenuItem_android_actionViewClass,
					value);
			itemActionViewClassName = value.type == TypedValue.TYPE_STRING ? value.string
					.toString() : null;

			// itemActionProviderClassName =
			// a.getString(R.styleable.SherlockMenuItem_android_actionProviderClass);
			value = new TypedValue();
			a.getValue(
					R.styleable.SherlockMenuItem_android_actionProviderClass,
					value);
			itemActionProviderClassName = value.type == TypedValue.TYPE_STRING ? value.string
					.toString() : null;

			final boolean hasActionProvider = itemActionProviderClassName != null;
			if (hasActionProvider && itemActionViewLayout == 0
					&& itemActionViewClassName == null) {
				itemActionProvider = newInstance(itemActionProviderClassName,
						ACTION_PROVIDER_CONSTRUCTOR_SIGNATURE,
						mActionProviderConstructorArguments);
			} else {
				if (hasActionProvider) {
					Log.w(LOG_TAG, "Ignoring attribute 'actionProviderClass'."
							+ " Action view already specified.");
				}
				itemActionProvider = null;
			}

			a.recycle();

			itemAdded = false;
		}

		/**
		 * Gets the shortcut.
		 * 
		 * @param shortcutString
		 *            the shortcut string
		 * @return the shortcut
		 */
		private char getShortcut(String shortcutString) {
			if (shortcutString == null) {
				return 0;
			} else {
				return shortcutString.charAt(0);
			}
		}

		/**
		 * Sets the item.
		 * 
		 * @param item
		 *            the new item
		 */
		private void setItem(MenuItem item) {
			item.setChecked(itemChecked).setVisible(itemVisible)
					.setEnabled(itemEnabled).setCheckable(itemCheckable >= 1)
					.setTitleCondensed(itemTitleCondensed)
					.setIcon(itemIconResId)
					.setAlphabeticShortcut(itemAlphabeticShortcut)
					.setNumericShortcut(itemNumericShortcut);

			if (itemShowAsAction >= 0) {
				item.setShowAsAction(itemShowAsAction);
			}

			if (itemListenerMethodName != null) {
				if (mContext.isRestricted()) {
					throw new IllegalStateException(
							"The android:onClick attribute cannot "
									+ "be used within a restricted context");
				}
				item.setOnMenuItemClickListener(new InflatedOnMenuItemClickListener(
						mRealOwner, itemListenerMethodName));
			}

			if (itemCheckable >= 2) {
				if (item instanceof MenuItemImpl) {
					MenuItemImpl impl = (MenuItemImpl) item;
					impl.setExclusiveCheckable(true);
				} else {
					menu.setGroupCheckable(groupId, true, true);
				}
			}

			boolean actionViewSpecified = false;
			if (itemActionViewClassName != null) {
				View actionView = (View) newInstance(itemActionViewClassName,
						ACTION_VIEW_CONSTRUCTOR_SIGNATURE,
						mActionViewConstructorArguments);
				item.setActionView(actionView);
				actionViewSpecified = true;
			}
			if (itemActionViewLayout > 0) {
				if (!actionViewSpecified) {
					item.setActionView(itemActionViewLayout);
					actionViewSpecified = true;
				} else {
					Log.w(LOG_TAG, "Ignoring attribute 'itemActionViewLayout'."
							+ " Action view already specified.");
				}
			}
			if (itemActionProvider != null) {
				item.setActionProvider(itemActionProvider);
			}
		}

		/**
		 * Adds the item.
		 */
		public void addItem() {
			itemAdded = true;
			setItem(menu.add(groupId, itemId, itemCategoryOrder, itemTitle));
		}

		/**
		 * Adds the sub menu item.
		 * 
		 * @return the sub menu
		 */
		public SubMenu addSubMenuItem() {
			itemAdded = true;
			SubMenu subMenu = menu.addSubMenu(groupId, itemId,
					itemCategoryOrder, itemTitle);
			setItem(subMenu.getItem());
			return subMenu;
		}

		/**
		 * Checks for added item.
		 * 
		 * @return true, if successful
		 */
		public boolean hasAddedItem() {
			return itemAdded;
		}

		/**
		 * New instance.
		 * 
		 * @param <T>
		 *            the generic type
		 * @param className
		 *            the class name
		 * @param constructorSignature
		 *            the constructor signature
		 * @param arguments
		 *            the arguments
		 * @return the t
		 */
		@SuppressWarnings("unchecked")
		private <T> T newInstance(String className,
				Class<?>[] constructorSignature, Object[] arguments) {
			try {
				Class<?> clazz = mContext.getClassLoader().loadClass(className);
				Constructor<?> constructor = clazz
						.getConstructor(constructorSignature);
				return (T) constructor.newInstance(arguments);
			} catch (Exception e) {
				Log.w(LOG_TAG, "Cannot instantiate class: " + className, e);
			}
			return null;
		}
	}
}
