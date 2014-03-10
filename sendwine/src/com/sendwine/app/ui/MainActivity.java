/**   
 * @Title: MainActivity.java 
 * @Package com.sendwine.app.ui 
 * @Description: 
 *			
 * @author  张雷 794857063@qq.com
 * @date 2014-1-1 下午3:16:51 
 * @version V1.0   
 */

package com.sendwine.app.ui;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CompoundButton.OnCheckedChangeListener;

import com.sendwine.app.AppContext;
import com.sendwine.app.R;
import com.sendwine.app.bean.Base;
import com.sendwine.app.bean.CartBean;
import com.sendwine.app.bean.Category;
import com.sendwine.app.ui.Cart.CartOperateListener;
import com.sendwine.app.ui.widgets.ClearableEditText;
import com.slidingmenu.lib.SlidingMenu;

/**
 * @ClassName: MainActivity
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author 张雷 794857063@qq.com
 * @date 2014-1-1 下午3:16:51
 * 
 */
public class MainActivity extends MainBaseActivity {
	private final String LOG_TAG = "MainActivity";

	private final boolean DEBUG = true;
	private SlidingMenu sm;

	private ClearableEditText keywords = null;

	private ImageButton search = null;

	private GridView search_hot = null;

	// 测试数据
	private String[] hotData = new String[] { "五粮液", "茅台", "国泰", "五粮液", "茅台",
			"国泰"

	};

	private ArrayAdapter<String> mHotAdapter = null;

	private ImageButton go_back;
	private TextView detail_title;
	private ListView lv_category_list;
	private List<Category> mCategorys = null;
	private GoodsAdapter mCategorysAdapter = null;

	private ListView lv_articles;
	private List<com.sendwine.app.bean.Article> mArticles = null;
	private ArticleAdapter mArticleAdapter = null;

	private ListView lv_cart_list = null;
	private List<CartBean> mCartBeans = null;
	private CartAdapter mCartAdapter = null;

	private CartOperateListener mCartOperateListener = null;

	private Button check_gloab = null;
	private Button payout = null;
	private TextView total_all = null;
	private int flag = 0;

	/**
	 * (非 Javadoc)
	 * <p>
	 * Title: getPreActivityData
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @see com.sendwine.app.ui.MainBaseActivity#getPreActivityData()
	 */
	@Override
	public void getPreActivityData() {
		// TODO Auto-generated method stub

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
	 * @see com.sendwine.app.ui.MainBaseActivity#initData()
	 */
	@Override
	public void initData() {
		// TODO Auto-generated method stub
		{
			mHotAdapter = new ArrayAdapter<String>(mAppContext,
					R.layout.hotitem, hotData);
		}

		{
			mCategorys = new ArrayList<Category>();
			mCategorysAdapter = new GoodsAdapter();
			mCategorys.add(new Category("白酒", "五粮液,露酒老窖等五粮液,露酒老窖等五粮液,露酒老窖等"));
			mCategorys.addAll(mCategorys.subList(0, mCategorys.size()));
			mCategorys.addAll(mCategorys.subList(0, mCategorys.size()));
			mCategorys.addAll(mCategorys.subList(0, mCategorys.size()));
			mCategorys.addAll(mCategorys.subList(0, mCategorys.size()));
			mCategorys.addAll(mCategorys.subList(0, mCategorys.size()));
		}

		{
			mArticleAdapter = new ArticleAdapter();

			mArticles = new ArrayList<com.sendwine.app.bean.Article>();
			mArticles
					.add(new com.sendwine.app.bean.Article("中国白酒的发展历史",
							"中国白酒的发展历史中国白酒的发展历史中国白酒的发展历史中国白酒的发展历史中国白酒的发展历史中国白酒的发展历史中国白酒的发展历史中国白酒的发展历史"));
			mArticles.addAll(mArticles.subList(0, mArticles.size()));
			mArticles.addAll(mArticles.subList(0, mArticles.size()));
			mArticles.addAll(mArticles.subList(0, mArticles.size()));
			mArticles.addAll(mArticles.subList(0, mArticles.size()));
		}

		{
			{
				mCartBeans = new ArrayList<CartBean>();
				mCartAdapter = new CartAdapter();
				mCartBeans.add(new CartBean(1, true, "五粮液", "3333", "33333",
						5.0f, "baidu.com", 1));
				mCartBeans.add(new CartBean(1, true, "五粮液", "3333", "33333",
						5.0f, "baidu.com", 2));
				mCartBeans.add(new CartBean(1, true, "五粮液", "3333", "33333",
						5.0f, "baidu.com", 3));
				mCartBeans.add(new CartBean(1, true, "五粮液", "3333", "33333",
						5.0f, "baidu.com", 4));
				mCartBeans.add(new CartBean(1, true, "五粮液", "3333", "33333",
						5.0f, "baidu.com", 5));
				mCartBeans.add(new CartBean(1, true, "五粮液", "3333", "33333",
						5.0f, "baidu.com", 6));
			}

			{
				mCartOperateListener = new CartOperateListener() {

					@Override
					public void onSelectedChanged(Base data, boolean b) {
						// TODO Auto-generated method stub
						Log.d(LOG_TAG, "onSelectedChanged");

						if (mCartBeans == null || mCartBeans.isEmpty()) {
							return;
						}
						boolean flag = false;
						CartBean item = (CartBean) data;
						for (CartBean iterable_element : mCartBeans) {
							flag = item.equals(iterable_element);

							if (flag) {
								iterable_element.checked = b;
								break;
							}
						}
						mCartAdapter.notifyDataSetChanged();
						calPrice();
					}

					@Override
					public void onMinus(Base data) {
						// TODO Auto-generated method stub
						Log.d(LOG_TAG, "onSelectedChanged");

						if (mCartBeans == null || mCartBeans.isEmpty()) {
							return;
						}
						CartBean item = (CartBean) data;
						if (item.account < 1) {
							return;
						}

						item.account--;
						mCartAdapter.notifyDataSetChanged();
						calPrice();
					}

					@Override
					public void onPlus(Base data) {
						// TODO Auto-generated method stub
						Log.d(LOG_TAG, "onPlus");

						if (mCartBeans == null || mCartBeans.isEmpty()) {
							return;
						}
						CartBean item = (CartBean) data;

						item.account++;
						mCartAdapter.notifyDataSetChanged();
						calPrice();
					}

					@Override
					public void onDelete(Base data) {
						// TODO Auto-generated method stub
						Log.d(LOG_TAG, "onPlus");

						if (mCartBeans == null || mCartBeans.isEmpty()) {
							return;
						}
						CartBean item = (CartBean) data;
						mCartBeans.remove(item);
						mCartAdapter.notifyDataSetChanged();
						calPrice();
					}
				};
			}
		}
	}

	/**
	 * (非 Javadoc)
	 * <p>
	 * Title: initView
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @see com.sendwine.app.ui.MainBaseActivity#initView()
	 */
	@Override
	public void initView() {
		// TODO Auto-generated method stub
		sm = getSlidingMenu();

		// 初始化搜索
		{
			keywords = (ClearableEditText) findViewById(R.id.keywords);
			search = (ImageButton) findViewById(R.id.search);
			search_hot = (GridView) findViewById(R.id.search_hot);
			search_hot.setAdapter(mHotAdapter);
		}

		// 分类
		{
			lv_category_list = (ListView) findViewById(R.id.lv_category_list);
			lv_category_list.setAdapter(mCategorysAdapter);
			lv_category_list.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					// TODO Auto-generated method stub
					if (mCategorys == null || mCategorys.isEmpty()) {
						return;
					}

					AppContext.mUiHelper.showGoodLists(MainActivity.this, null);
				}
			});
		}

		// wine
		{
			{
				lv_articles = (ListView) findViewById(R.id.lv_articles);
				lv_articles.setAdapter(mArticleAdapter);
				lv_articles.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						// TODO Auto-generated method stub
						if (mArticles == null || mArticles.isEmpty()) {
							return;
						}

						Bundle bundle = new Bundle();
						bundle.putSerializable("article",
								mArticleAdapter.getItem(position));
						AppContext.mUiHelper.showArticleDetail(
								MainActivity.this, bundle);
						bundle = null;
					}
				});
			}
		}

		{

			{
				lv_cart_list = (ListView) findViewById(R.id.lv_cart_list);
				lv_cart_list.setAdapter(mCartAdapter);
			}

			{
				check_gloab = (Button) findViewById(R.id.check_gloab);
				check_gloab.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						if (flag == 0) {
							// unselect all
							select(false);
							flag = 1;
							check_gloab.setText("全选");
						} else {
							// selectall
							select(true);
							check_gloab.setText("全不选");
							flag = 0;
						}
					}
				});
				payout = (Button) findViewById(R.id.payout);
				payout.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						Toast.makeText(mAppContext, "支付", Toast.LENGTH_SHORT)
								.show();
					}
				});
				total_all = (TextView) findViewById(R.id.total_all);
				total_all.setText("0.0");
				calPrice();
			}
		}
	}

	/**
	 * (非 Javadoc)
	 * <p>
	 * Title: loadData
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @see com.sendwine.app.ui.MainBaseActivity#loadData()
	 */
	@Override
	public void loadData() {
		// TODO Auto-generated method stub

	}

	/**
	 * (非 Javadoc)
	 * <p>
	 * Title: onKeyDown
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @param keyCode
	 * @param event
	 * @return
	 * @see android.support.v4.app.FragmentActivity#onKeyDown(int,
	 *      android.view.KeyEvent)
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		switch (keyCode) {
		case KeyEvent.KEYCODE_MENU:

			sm.toggle(true);
			break;
		case KeyEvent.KEYCODE_BACK:
			AppContext.mAppManger.finishAllActivity();
			break;
		default:
			break;
		}
		return super.onKeyDown(keyCode, event);
	}

	private final class GoodsAdapter extends BaseAdapter {

		/**
		 * (非 Javadoc)
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
			return mCategorys.size();
		}

		/**
		 * (非 Javadoc)
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
		public Category getItem(int position) {
			// TODO Auto-generated method stub
			return mCategorys.get(position);
		}

		/**
		 * (非 Javadoc)
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
		 * (非 Javadoc)
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
			ViewHolder holder = null;
			Category article = mCategorys.get(position);
			if (null == convertView) {
				convertView = getLayoutInflater().inflate(
						R.layout.goods_item_v2, null);
				holder = new ViewHolder();
				holder.goods_icon = (ImageView) convertView
						.findViewById(R.id.goods_icon);
				holder.category_name = (TextView) convertView
						.findViewById(R.id.category_name);
				holder.category_introduce = (TextView) convertView
						.findViewById(R.id.category_introduce);

				convertView.setTag(holder);

			}
			{
				holder = (ViewHolder) convertView.getTag();
			}

			holder.category_name.setText(article.categoryName);
			holder.category_introduce.setText(article.categoryIntrocude);

			return convertView;
		}

		private final class ViewHolder {
			ImageView goods_icon = null;

			TextView category_name = null;

			TextView category_introduce = null;

		}

	}

	private final class ArticleAdapter extends BaseAdapter {

		/**
		 * (非 Javadoc)
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
			return mArticles.size();
		}

		/**
		 * (非 Javadoc)
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
		public com.sendwine.app.bean.Article getItem(int position) {
			// TODO Auto-generated method stub
			return mArticles.get(position);
		}

		/**
		 * (非 Javadoc)
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
			return 0;
		}

		/**
		 * (非 Javadoc)
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
			ViewHolder holder = null;
			com.sendwine.app.bean.Article article = mArticles.get(position);
			if (null == convertView) {
				convertView = getLayoutInflater().inflate(
						R.layout.simple_list_item_v2, null);
				holder = new ViewHolder();
				holder.simple_tv_item = (TextView) convertView
						.findViewById(R.id.simple_tv_item);
				convertView.setTag(holder);

			}
			{
				holder = (ViewHolder) convertView.getTag();
			}

			holder.simple_tv_item.setText(article.title);
			return convertView;
		}

		private final class ViewHolder {
			TextView simple_tv_item = null;
		}

	}

	private final class CartAdapter extends BaseAdapter {

		/**
		 * (非 Javadoc)
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
			return mCartBeans.size();
		}

		/**
		 * (非 Javadoc)
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
		public CartBean getItem(int position) {
			// TODO Auto-generated method stub
			return mCartBeans.get(position);
		}

		/**
		 * (非 Javadoc)
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
		 * (非 Javadoc)
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
		public View getView(final int position, View convertView,
				ViewGroup parent) {
			// TODO Auto-generated method stub
			CartBean tmp = mCartBeans.get(position);
			ViewHolder holder = null;
			if (null == convertView) {
				holder = new ViewHolder();
				convertView = getLayoutInflater().inflate(R.layout.cart_item,
						null);
				holder.check = (CheckBox) convertView.findViewById(R.id.check);
				holder.goods_icon = (ImageView) convertView
						.findViewById(R.id.goods_icon);
				holder.goods_name = (TextView) convertView
						.findViewById(R.id.goods_name);
				holder.sale_price = (TextView) convertView
						.findViewById(R.id.sale_price);
				holder.goods_price = (TextView) convertView
						.findViewById(R.id.goods_price);
				holder.goods_count = (TextView) convertView
						.findViewById(R.id.goods_count);
				holder.minus = (ImageButton) convertView
						.findViewById(R.id.minus);
				holder.plus = (ImageButton) convertView.findViewById(R.id.plus);
				holder.delete = (ImageButton) convertView
						.findViewById(R.id.delete);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			{
				holder.check.setOnCheckedChangeListener(null);
				holder.check.setChecked(true);
				holder.check.setChecked(tmp.checked);
				// icon
				holder.goods_name.setText(tmp.name);
				holder.goods_price.setText("￥" + tmp.price);
				holder.sale_price.setText("￥" + tmp.salePrice);
				holder.minus.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						if (null != mCartOperateListener) {
							mCartOperateListener.onMinus(getItem(position));
						}
					}
				});

				holder.plus.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						if (null != mCartOperateListener) {
							mCartOperateListener.onPlus(getItem(position));
						}
					}
				});

				holder.check
						.setOnCheckedChangeListener(new OnCheckedChangeListener() {

							@Override
							public void onCheckedChanged(
									CompoundButton buttonView, boolean isChecked) {
								// TODO Auto-generated method stub
								if (null != mCartOperateListener) {
									mCartOperateListener.onSelectedChanged(
											getItem(position), isChecked);
								}
							}
						});

				holder.delete.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						if (null != mCartOperateListener) {
							mCartOperateListener.onDelete(getItem(position));
						}
					}
				});
				holder.goods_count.setText("" + tmp.account);
			}
			return convertView;
		}

		private final class ViewHolder {
			CheckBox check = null;
			ImageView goods_icon = null;
			TextView goods_name = null;
			TextView sale_price = null;
			TextView goods_price = null;
			ImageButton minus = null;
			TextView goods_count = null;
			ImageButton plus = null;
			ImageButton delete = null;
		}
	}

	public interface CartOperateListener {

		public void onSelectedChanged(Base data, boolean b);

		public void onMinus(Base data);

		public void onPlus(Base data);

		public void onDelete(Base data);
	}

	public void select(boolean b) {
		if (null == mCartBeans || mCartBeans.isEmpty()) {
			return;
		}

		for (CartBean iterable_element : mCartBeans) {
			if (b) {
				iterable_element.checked = true;
			} else {
				iterable_element.checked = false;
			}

		}

		calPrice();
		mCartAdapter.notifyDataSetChanged();
	}

	public void calPrice() {
		if (null == mCartBeans || mCartBeans.isEmpty()) {

			return;
		}
		float price = 0.0f;
		for (CartBean iterable_element : mCartBeans) {
			if (iterable_element.checked) {

				price += (Float.parseFloat(iterable_element.salePrice) * iterable_element.account);
			}
		}

		total_all.setText(price + "");

	}

}
