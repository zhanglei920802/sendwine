/**   
 * @Title: GoodsList.java 
 * @Package com.sendwine.app.ui 
 * @Description: 
 *			
 * @author  张雷 794857063@qq.com
 * @date 2014-1-1 下午5:47:41 
 * @version V1.0   
 */

package com.sendwine.app.ui;

import java.util.ArrayList;
import java.util.List;

import com.sendwine.app.AppContext;
import com.sendwine.app.R;
import com.sendwine.app.bean.SimpleGoods;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

/**
 * @ClassName: GoodsList
 * @Description:
 * 
 *               商品列表
 * @author 张雷 794857063@qq.com
 * @date 2014-1-1 下午5:47:41
 * 
 */
public class GoodsList extends BaseActivity {
	private ImageButton go_back;
	private TextView detail_title;
	private ListView lv_goods_list;
	private List<SimpleGoods> mGoods = null;
	private GoodsAdapter mGoodsAdapter = null;

	private Button goods_drink = null;
	private Button price = null;
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
			mGoods = new ArrayList<SimpleGoods>();
			mGoodsAdapter = new GoodsAdapter();
			mGoods.add(new SimpleGoods("贵州特醇-华彩人生", "1988", "20888", 2.5f, "baidu.com"));
			mGoods.addAll(mGoods.subList(0, mGoods.size()));
			mGoods.addAll(mGoods.subList(0, mGoods.size()));
			mGoods.addAll(mGoods.subList(0, mGoods.size()));
			mGoods.addAll(mGoods.subList(0, mGoods.size()));
			mGoods.addAll(mGoods.subList(0, mGoods.size()));
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
		setContentView(R.layout.goods_list);
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
			detail_title.setText("商品列表");

		}
		
		{
			goods_drink = (Button) findViewById(R.id.good);
			price = (Button) findViewById(R.id.price);
		}
		{
			lv_goods_list = (ListView) findViewById(R.id.lv_goods_list);
			lv_goods_list.setAdapter(mGoodsAdapter);
			lv_goods_list.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					// TODO Auto-generated method stub
					if (mGoods == null || mGoods.isEmpty()) {
						return;
					}

					Bundle bundle = new Bundle();
					bundle.putSerializable("goods",
							mGoodsAdapter.getItem(position));
					AppContext.mUiHelper
							.showGoodsDetail(GoodsList.this, bundle);
					bundle = null;
				}
			});
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
			return mGoods.size();
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
		public SimpleGoods getItem(int position) {
			// TODO Auto-generated method stub
			return mGoods.get(position);
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
			SimpleGoods article = mGoods.get(position);
			if (null == convertView) {
				convertView = getLayoutInflater().inflate(R.layout.goods_item,
						null);
				holder = new ViewHolder();
				holder.goods_icon = (ImageView) convertView
						.findViewById(R.id.goods_icon);
				holder.goods_name = (TextView) convertView
						.findViewById(R.id.goods_name);
				holder.sale_price = (TextView) convertView
						.findViewById(R.id.sale_price);
				holder.goods_price = (TextView) convertView
						.findViewById(R.id.goods_price);
				holder.goods_score = (RatingBar) convertView
						.findViewById(R.id.goods_score);
				convertView.setTag(holder);

			}
			{
				holder = (ViewHolder) convertView.getTag();
			}

			holder.goods_name.setText(article.name);
			holder.sale_price.setText("￥"+article.salePrice);
			holder.goods_price.setText("￥"+article.price);
			holder.goods_score.setRating(article.grade);

			return convertView;
		}

		private final class ViewHolder {
			ImageView goods_icon = null;

			TextView goods_name = null;

			TextView sale_price = null;

			TextView goods_price = null;

			RatingBar goods_score = null;
		}

	}
}
