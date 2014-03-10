/**   
 * @Title: MainBaseActivity.java 
 * @Package com.sendwine.app.ui 
 * @Description: 
 *			
 * @author  张雷 794857063@qq.com
 * @date 2014-1-1 下午3:08:29 
 * @version V1.0   
 */

package com.sendwine.app.ui;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.sendwine.app.AppContext;
import com.sendwine.app.R;
import com.sendwine.app.ui.widgets.MainScrollLayout;
import com.slidingmenu.lib.SlidingMenu;
import com.slidingmenu.lib.app.SlidingFragmentActivity;

/**
 * @ClassName: MainBaseActivity
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author 张雷 794857063@qq.com
 * @date 2014-1-1 下午3:08:29
 * 
 */
public abstract class MainBaseActivity extends SlidingFragmentActivity
		implements ActivityItf, OnClickListener {

	/** 日志TAG */
	private final String LOG_TAG = "MainBaseActivity";
	/** The m frag. */
	protected Fragment mFrag;

	/** 全局上下文 */
	protected AppContext mAppContext = null;

	// /view
	private MainScrollLayout main_main_scrolllayout;
	private RadioButton frame_main;
	private RadioButton frame_search;
	private RadioButton frame_wine;
	private RadioButton frame_cart;
	private int mCurSel;
	private ImageButton go_back;
	private TextView detail_title;
	private ImageButton confirm = null;
	private RelativeLayout nav_title = null;
	private View mPopupWindowView;
	private PopupWindow mPopupWindow;

	private String[] pop_menu = null;

	/**
	 * (非 Javadoc)
	 * <p>
	 * Title: getPreActivityData
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @see com.sendwine.app.ui.ActivityItf#getPreActivityData()
	 */
	@Override
	public abstract void getPreActivityData();

	/**
	 * (非 Javadoc)
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
		// TODO Auto-generated method stub
		int id = v.getId();
		switch (id) {
		case R.id.frame_main:
			mCurSel = 0;
			// showDialog();

			/* showDialog(); */
			break;
		case R.id.frame_search:

			mCurSel = 1;

			break;
		case R.id.frame_wine:
			mCurSel = 2;

			break;
		case R.id.frame_cart:
			mCurSel = 3;

			break;

		}
		setSelected(mCurSel);
	}

	/**
	 * @Title: setSelected
	 * @Description: TODO
	 * @param @param mCurSel2 设定文件
	 * @return void 返回类型
	 * @throws
	 */

	private void setSelected(int id) {
		// TODO Auto-generated method stub
		if (0 == id) {// main 0
			if (!frame_main.isChecked()) {
				frame_main.setChecked(true);
			}
			frame_search.setChecked(false);
			frame_wine.setChecked(false);
			frame_cart.setChecked(false);

			main_main_scrolllayout.snapToScreen(0);
			mCurSel = 0;

			detail_title.setText("我送酒");
		} else if (1 == id) {// chat

			mCurSel = 1;

			if (!frame_search.isChecked()) {
				frame_search.setChecked(true);
			}
			frame_main.setChecked(false);
			frame_wine.setChecked(false);
			frame_cart.setChecked(false);

			main_main_scrolllayout.snapToScreen(1);
			mCurSel = 1;
			detail_title.setText("搜索");
			// onChatModelClicked(true);// fire
		} else if (2 == id) {// query 1
			if (!frame_wine.isChecked()) {
				frame_wine.setChecked(true);
			}
			frame_main.setChecked(false);
			frame_search.setChecked(false);
			frame_cart.setChecked(false);
			main_main_scrolllayout.snapToScreen(2);
			mCurSel = 2;
			detail_title.setText("酒文化");
		} else if (3 == id) {// app 4

			if (!frame_cart.isChecked()) {
				frame_cart.setChecked(true);
			}
			frame_main.setChecked(false);
			frame_search.setChecked(false);
			frame_wine.setChecked(false);
			main_main_scrolllayout.snapToScreen(3);
			mCurSel = 3;
			detail_title.setText("购物车");

		}
	}

	/**
	 * (非 Javadoc)
	 * <p>
	 * Title: initData
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @see com.sendwine.app.ui.ActivityItf#initData()
	 */
	@Override
	public abstract void initData();

	/**
	 * (非 Javadoc)
	 * <p>
	 * Title: initView
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @see com.sendwine.app.ui.ActivityItf#initView()
	 */
	@Override
	public abstract void initView();

	/**
	 * (非 Javadoc)
	 * <p>
	 * Title: loadData
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @see com.sendwine.app.ui.ActivityItf#loadData()
	 */
	@Override
	public abstract void loadData();

	/**
	 * (非 Javadoc)
	 * <p>
	 * Title: onCreate
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 这里使用模板设计模式,减少代码冗余量 此外,使用<code>final</code>关键字,避免子类不规范的操作 </p>
	 * 
	 * @param savedInstanceState
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@Override
	public final void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		setTheme(R.style.Theme_Sherlock_Light_NoActionBar);
		super.onCreate(savedInstanceState);

		mAppContext = (AppContext) getApplication();
		AppContext.mAppManger.addActivity(this);
		setBehindContentView(R.layout.menu_frame);

		if (savedInstanceState == null) {
			FragmentTransaction t = this.getSupportFragmentManager()
					.beginTransaction();
			mFrag = new MenuListFragment();
			t.replace(R.id.menu_frame, mFrag);
			t.commit();
		} else {
			mFrag = this.getSupportFragmentManager().findFragmentById(
					R.id.menu_frame);
		}

		SlidingMenu sm = getSlidingMenu();
		sm.setShadowWidthRes(R.dimen.shadow_width);
		sm.setShadowDrawable(R.drawable.shadow);
		sm.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		sm.setFadeDegree(0.35f);
		sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
		sm.setMode(SlidingMenu.LEFT);
		setSlidingActionBarEnabled(true);
		sm = null;
		mFrag = null;

		this.init();
		this.initFrameButton();

		getPreActivityData();
		initData();
		initView();
		loadData();

	}

	/**
	 * @Title: init
	 * @Description: TODO
	 * @param 设定文件
	 * @return void 返回类型
	 * @throws
	 */

	private void init() {
		// TODO Auto-generated method stub
		try {

			setContentView(R.layout.content_frame);
			nav_title = (RelativeLayout) findViewById(R.id.nav_title);
			go_back = (ImageButton) findViewById(R.id.go_back);
			go_back.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					SlidingMenu menu = getSlidingMenu();
					if (null != menu) {
						menu.toggle();
					}
				}
			});

			detail_title = (TextView) findViewById(R.id.detail_title);
			detail_title.setText("我送酒");

			{
				pop_menu = getResources().getStringArray(R.array.pop_menu);
				mPopupWindowView = getLayoutInflater().inflate(
						R.layout.pop_menu_list, null);
				ListView lv_pop_menu_list = (ListView) mPopupWindowView
						.findViewById(R.id.lv_pop_menu_list);
				lv_pop_menu_list.setAdapter(new PopMenuAdapter());
				lv_pop_menu_list
						.setOnItemClickListener(new OnItemClickListener() {

							@Override
							public void onItemClick(AdapterView<?> parent,
									View view, int position, long id) {
								// TODO Auto-generated method stub
								if (null != mPopupWindow) {
									hidePopupWindow();
								}

								switch (position) {
								case 0:
									AppContext.mUiHelper.showLogin(
											MainBaseActivity.this, null);
									break;

								case 1:
									AppContext.mUiHelper.showRegisterActivity(
											MainBaseActivity.this, null);
									break;
								case 2:
									AppContext.mUiHelper.showCart(
											MainBaseActivity.this, null);
									break;
								}
							}

						});
			}
			confirm = (ImageButton) findViewById(R.id.confirm);
			confirm.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					showPopWindow();
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @Title: initFrameButton
	 * @Description: TODO
	 * @param 设定文件
	 * @return void 返回类型
	 * @throws
	 */

	private void initFrameButton() {
		// TODO Auto-generated method stub
		main_main_scrolllayout = (MainScrollLayout) findViewById(R.id.main_main_scrolllayout);
		main_main_scrolllayout.snapToScreen(0);

		frame_main = (RadioButton) findViewById(R.id.frame_main);
		frame_main.setOnClickListener(this);
		frame_search = (RadioButton) findViewById(R.id.frame_search);
		frame_search.setOnClickListener(this);
		frame_wine = (RadioButton) findViewById(R.id.frame_wine);
		frame_wine.setOnClickListener(this);

		frame_cart = (RadioButton) findViewById(R.id.frame_cart);
		frame_cart.setOnClickListener(this);

	}

	/**
	 * (非 Javadoc)
	 * <p>
	 * Title: onDestroy
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @see android.app.Activity#onDestroy()
	 */
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		// AppContext.mAppManger.finishAllActivity();
		super.onDestroy();
	}

	@SuppressWarnings("deprecation")
	private void showPopWindow() {
		mPopupWindow = new PopupWindow(mPopupWindowView, getResources()
				.getDimensionPixelSize(R.dimen.popwindow_width),
				ViewGroup.LayoutParams.WRAP_CONTENT);
		mPopupWindow.setBackgroundDrawable(new BitmapDrawable());
		mPopupWindow.setFocusable(true);
		// mPopupWindow.showAtLocation(nav_title, Gravity.TOP, 0, 0);
		mPopupWindow.showAsDropDown(confirm, 0, 20);
		// getResources().getd
	}

	private void hidePopupWindow() {
		if (null != mPopupWindow && mPopupWindow.isShowing()) {
			mPopupWindow.dismiss();
			mPopupWindow = null;
		}
	}

	private final class PopMenuAdapter extends BaseAdapter {

		/**
		 * <p>
		 * Title: getCount
		 * </p>
		 * <p>
		 * Description:
		 * </p>
		 * 
		 * @return
		 * @see android.widget.Adapter#getCount()
		 */
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return pop_menu.length;
		}

		/**
		 * <p>
		 * Title: getItem
		 * </p>
		 * <p>
		 * Description:
		 * </p>
		 * 
		 * @param position
		 * @return
		 * @see android.widget.Adapter#getItem(int)
		 */
		@Override
		public String getItem(int position) {
			// TODO Auto-generated method stub
			return pop_menu[position];
		}

		/**
		 * <p>
		 * Title: getItemId
		 * </p>
		 * <p>
		 * Description:
		 * </p>
		 * 
		 * @param position
		 * @return
		 * @see android.widget.Adapter#getItemId(int)
		 */
		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		/**
		 * <p>
		 * Title: getView
		 * </p>
		 * <p>
		 * Description:
		 * </p>
		 * 
		 * @param position
		 * @param convertView
		 * @param parent
		 * @return
		 * @see android.widget.Adapter#getView(int, android.view.View,
		 *      android.view.ViewGroup)
		 */
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			convertView = getLayoutInflater().inflate(
					R.layout.simple_list_item_v1, null);

			if (pop_menu.length == 1) {
				convertView
						.setBackgroundResource(R.drawable.selector_center_bg);

			} else if (pop_menu.length == 2) {
				switch (position) {
				case 0:
					convertView
							.setBackgroundResource(R.drawable.selector_top_bg);

					break;

				case 1:
					convertView
							.setBackgroundResource(R.drawable.selector_bottom_bg);

					break;
				}
			} else if (pop_menu.length > 2) {

				if (0 == position) {
					convertView
							.setBackgroundResource(R.drawable.selector_top_bg);
				} else if ((pop_menu.length - 1) == position) {
					convertView
							.setBackgroundResource(R.drawable.selector_bottom_bg);

				} else {
					convertView
							.setBackgroundResource(R.drawable.selector_center_bg);
				}

			}

			((TextView) convertView.findViewById(R.id.simple_tv_item))
					.setText(pop_menu[position]);
			return convertView;
		}

	}
}
