/**   
 * @Title: Cart.java 
 * @Package com.sendwine.app.ui 
 * @Description: 
 *			
 * @author  张雷 794857063@qq.com
 * @date 2014-1-3 下午3:18:01 
 * @version V1.0   
 */

package com.sendwine.app.ui;

import java.util.ArrayList;
import java.util.List;

import android.provider.ContactsContract.CommonDataKinds.Im;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.sendwine.app.AppContext;
import com.sendwine.app.R;
import com.sendwine.app.bean.Base;
import com.sendwine.app.bean.CartBean;

/**
 * @ClassName: Cart
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author 张雷 794857063@qq.com
 * @date 2014-1-3 下午3:18:01
 * 
 */
public class Cart extends BaseActivity {
	private final String LOG_TAG = "Cart";

	private final boolean DEBUG = true;

	private View go_back;

	private TextView detail_title;

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
	 * @see com.sendwine.app.ui.BaseActivity#getPreActivityData()
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
	 * @see com.sendwine.app.ui.BaseActivity#initData()
	 */
	@Override
	public void initData() {
		// TODO Auto-generated method stub
		{
			mCartBeans = new ArrayList<CartBean>();
			mCartAdapter = new CartAdapter();
			mCartBeans.add(new CartBean(1, true, "五粮液", "3333", "33333", 5.0f,
					"baidu.com", 1));
			mCartBeans.add(new CartBean(1, true, "五粮液", "3333", "33333", 5.0f,
					"baidu.com", 2));
			mCartBeans.add(new CartBean(1, true, "五粮液", "3333", "33333", 5.0f,
					"baidu.com", 3));
			mCartBeans.add(new CartBean(1, true, "五粮液", "3333", "33333", 5.0f,
					"baidu.com", 4));
			mCartBeans.add(new CartBean(1, true, "五粮液", "3333", "33333", 5.0f,
					"baidu.com", 5));
			mCartBeans.add(new CartBean(1, true, "五粮液", "3333", "33333", 5.0f,
					"baidu.com", 6));
		}

		{
			mCartOperateListener = new CartOperateListener() {

				@Override
				public void onSelectedChanged(Base data, boolean b) {
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

	/**
	 * (非 Javadoc)
	 * <p>
	 * Title: initView
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @see com.sendwine.app.ui.BaseActivity#initView()
	 */
	@Override
	public void initView() {
		// TODO Auto-generated method stub
		setContentView(R.layout.cart_list);

		{

			go_back = (ImageButton) findViewById(R.id.go_back);
			go_back.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					onDestroy();
				}
			});

			detail_title = (TextView) findViewById(R.id.detail_title);
			detail_title.setText("购物车");
		}

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

	/**
	 * (非 Javadoc)
	 * <p>
	 * Title: loadData
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @see com.sendwine.app.ui.BaseActivity#loadData()
	 */
	@Override
	public void loadData() {
		// TODO Auto-generated method stub

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
