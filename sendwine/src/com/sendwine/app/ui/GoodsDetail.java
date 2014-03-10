/**   
 * @Title: GoodsDetail.java 
 * @Package com.sendwine.app.ui 
 * @Description: 
 *			
 * @author  张雷 794857063@qq.com
 * @date 2014-1-3 上午11:39:34 
 * @version V1.0   
 */

package com.sendwine.app.ui;

import java.util.ArrayList;
import java.util.List;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.webkit.WebView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.sendwine.app.AppContext;
import com.sendwine.app.R;
import com.sendwine.app.bean.Comments;
import com.sendwine.app.bean.SimpleGoods;

/**
 * @ClassName: GoodsDetail
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author 张雷 794857063@qq.com
 * @date 2014-1-3 上午11:39:34
 * 
 */
public class GoodsDetail extends BaseActivity {

	private final String LOG_TAG = "GoodsDetail";
	private boolean DEBUG = true;
	private SimpleGoods simpleGoods = null;

	private com.sendwine.app.bean.GoodsDetail mGoodsDetail = null;
	private List<Comments> mCommentsData = null;

	private ImageButton go_back;
	private TextView detail_title;

	private ViewPager pic_slider = null;
	private ViewGroup indicator = null;
	private ImageView[] imageViews, imageViewsIndicator = null;

	private ImageView imageView;

	private TextView goods_name = null;
	private TextView sale_price = null;
	private TextView goods_price = null;
	private RatingBar goods_score = null;

	private ViewPager pager_main = null;
	private PagerTabStrip pager_goods = null;
	private LayoutInflater mLayoutInflater = null;
	private View mParams = null;
	private View mDetail = null;
	private View mComments = null;
	private ListView lv_goods_params = null, lv_goods_comments = null;
	private ArrayAdapter<String> mGoodsParamsAdapter = null;
	private String[] mParamsStrings = null;
	private WebView goods_detail = null;
	private CommentsAdapter mCommentsAdapter = null;
	private List<View> mViewList;
	private String[] mTitleList;

	private ImageButton goods_fav = null;
	private ImageButton goods_buy = null;
	private ImageButton goods_cart = null;
	
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
		simpleGoods = (SimpleGoods) getIntent().getSerializableExtra("goods");
		if (null == simpleGoods) {
			onDestroy();
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
	 * @see com.sendwine.app.ui.BaseActivity#initData()
	 */
	@Override
	public void initData() {
		// TODO Auto-generated method stub
		{
			imageViews = new ImageView[3];
			imageViewsIndicator = new ImageView[3];
			imageViews[0] = (ImageView) getLayoutInflater().inflate(
					R.layout.item01, null);
			imageViews[1] = (ImageView) getLayoutInflater().inflate(
					R.layout.item02, null);
			imageViews[2] = (ImageView) getLayoutInflater().inflate(
					R.layout.item03, null);
		}

		{
			mLayoutInflater = getLayoutInflater();
			mTitleList = getResources().getStringArray(R.array.good_detail_tab);
			mParamsStrings = getResources()
					.getStringArray(R.array.goods_params);
			mViewList = new ArrayList<View>();

			mGoodsDetail = new com.sendwine.app.bean.GoodsDetail();
			mGoodsDetail.capacity = "5000";
			mGoodsDetail.producing = "四川";
			mGoodsDetail.type = "浓香型";
			mGoodsDetail.state = "有货";
			{
				mParamsStrings[0] = String.format(mParamsStrings[0],
						mGoodsDetail.capacity);
				mParamsStrings[1] = String.format(mParamsStrings[1],
						mGoodsDetail.producing);
				mParamsStrings[2] = String.format(mParamsStrings[2],
						mGoodsDetail.type);
				mParamsStrings[3] = String.format(mParamsStrings[3],
						mGoodsDetail.state);
			}

			{
				mCommentsData = new ArrayList<Comments>();
				mCommentsData.add(new Comments("陈先生", "不错推荐", 1.0f));
				mCommentsData.add(new Comments("陈先生", "不错推荐", 1.5f));
				mCommentsData.add(new Comments("陈先生", "不错推荐", 2.0f));
				mCommentsData.add(new Comments("陈先生", "不错推荐", 3.0f));
				mCommentsData.add(new Comments("陈先生", "不错推荐", 3.5f));
				mCommentsData.add(new Comments("陈先生", "不错推荐", 4.0f));
				mCommentsData.add(new Comments("陈先生", "不错推荐", 5.0f));
				mCommentsAdapter = new CommentsAdapter();

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
	 * @see com.sendwine.app.ui.BaseActivity#initView()
	 */
	@Override
	public void initView() {
		// TODO Auto-generated method stub
		setContentView(R.layout.goods_detail);
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
			detail_title.setText("商品详情");

		}
		// pic slider
		{
			pic_slider = (ViewPager) findViewById(R.id.pic_slider);
			indicator = (LinearLayout) findViewById(R.id.indicator);

			pic_slider.setOnPageChangeListener(new OnPageChangeListener() {

				@Override
				public void onPageSelected(int arg0) {
					// TODO Auto-generated method stub
					for (int i = 0; i < imageViewsIndicator.length; i++) {
						imageViewsIndicator[arg0]
								.setBackgroundResource(R.drawable.hot_yellow);
						if (arg0 != i) {
							imageViewsIndicator[i]
									.setBackgroundResource(R.drawable.hot_white);
						}
					}
				}

				@Override
				public void onPageScrolled(int arg0, float arg1, int arg2) {
					// TODO Auto-generated method stub

				}

				@Override
				public void onPageScrollStateChanged(int arg0) {
					// TODO Auto-generated method stub

				}
			});
			pic_slider.setAdapter(new PicSliderAdpater());
			for (int i = 0; i < 3; i++) {
				imageView = new ImageView(this);
				imageView.setLayoutParams(new LayoutParams(15, 15));
				imageView.setPadding(20, 0, 20, 0);
				imageViewsIndicator[i] = imageView;
				if (i == 0) {
					imageViewsIndicator[i]
							.setBackgroundResource(R.drawable.hot_yellow);
				} else {
					imageViewsIndicator[i]
							.setBackgroundResource(R.drawable.hot_white);
				}
				indicator.addView(imageViewsIndicator[i]);
			}
		}
		//
		{
			goods_name = (TextView) findViewById(R.id.goods_name);
			sale_price = (TextView) findViewById(R.id.sale_price);
			goods_price = (TextView) findViewById(R.id.goods_price);
			goods_score = (RatingBar) findViewById(R.id.goods_score);
		}

		//
		{
			pager_main = (ViewPager) findViewById(R.id.pager_main);
			pager_main.setVisibility(View.VISIBLE);
			pager_goods = (PagerTabStrip) findViewById(R.id.pager_goods);
			pager_goods.setTextSpacing(50);
			pager_goods.setTextColor(getResources().getColor(R.color.black));
			// pager_goods.setDrawFullUnderline(false); //不显示整条横线

			mParams = mLayoutInflater.inflate(R.layout.goods_params, null);
			mDetail = mLayoutInflater.inflate(R.layout.goods_detail_detail,
					null);
			mComments = mLayoutInflater.inflate(R.layout.goods_comments, null);
			mViewList.add(mParams);
			mViewList.add(mDetail);
			mViewList.add(mComments);
			pager_main.setAdapter(new MyPageApdater());

			// params
			{

				lv_goods_params = (ListView) mParams
						.findViewById(R.id.lv_goods_params);
				mGoodsParamsAdapter = new ArrayAdapter<String>(mAppContext,
						R.layout.simple_list_item, mParamsStrings);
				lv_goods_params.setAdapter(mGoodsParamsAdapter);
			}

			// detail
			{

				goods_detail = (WebView) mDetail
						.findViewById(R.id.goods_detail);
				goods_detail.loadUrl("http://www.baidu.com");
			}

			// comments
			{

				lv_goods_comments = (ListView) mComments
						.findViewById(R.id.lv_goods_comments);
				lv_goods_comments.setAdapter(mCommentsAdapter);
			}

		}

		//
		{
			goods_fav = (ImageButton) findViewById(R.id.goods_fav);
			goods_buy = (ImageButton) findViewById(R.id.goods_buy);
			goods_cart = (ImageButton) findViewById(R.id.goods_cart);
			goods_cart.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					AppContext.mUiHelper.showCart(GoodsDetail.this, null);
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

	private final class PicSliderAdpater extends PagerAdapter {

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
		 * @see android.support.v4.view.PagerAdapter#getCount()
		 */
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return imageViews.length;
		}

		/**
		 * (非 Javadoc)
		 * <p>
		 * Title: isViewFromObject
		 * </p>
		 * <p>
		 * Description:
		 * </p>
		 * 
		 * @param arg0
		 * @param arg1
		 * @return
		 * @see android.support.v4.view.PagerAdapter#isViewFromObject(android.view.View,
		 *      java.lang.Object)
		 */
		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			// TODO Auto-generated method stub
			return arg0 == arg1;
		}

		/**
		 * (非 Javadoc)
		 * <p>
		 * Title: instantiateItem
		 * </p>
		 * <p>
		 * Description:
		 * </p>
		 * 
		 * @param container
		 * @param position
		 * @return
		 * @see android.support.v4.view.PagerAdapter#instantiateItem(android.view.ViewGroup,
		 *      int)
		 */
		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			// TODO Auto-generated method stub
			((ViewPager) container).addView(imageViews[position]);
			View tView = imageViews[position];
			return tView;
		}

		/**
		 * (非 Javadoc)
		 * <p>
		 * Title: destroyItem
		 * </p>
		 * <p>
		 * Description:
		 * </p>
		 * 
		 * @param container
		 * @param position
		 * @param object
		 * @see android.support.v4.view.PagerAdapter#destroyItem(android.view.ViewGroup,
		 *      int, java.lang.Object)
		 */
		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			// TODO Auto-generated method stub
			((ViewPager) container).removeView(imageViews[position]);
		}
	};

	private final class CommentsAdapter extends BaseAdapter {

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
			return mCommentsData.size();
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
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return mCommentsData.get(position);
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
			final Comments tmp = mCommentsData.get(position);
			ViewHolder viewHolder = null;

			if (null == convertView) {
				viewHolder = new ViewHolder();
				convertView = mLayoutInflater.inflate(R.layout.comments_item,
						null);
				viewHolder.cus_name = (TextView) convertView
						.findViewById(R.id.cus_name);
				viewHolder.comments_content = (TextView) convertView
						.findViewById(R.id.comments_content);
				viewHolder.goods_comment_score = (RatingBar) convertView
						.findViewById(R.id.goods_comment_score);
				convertView.setTag(viewHolder);
			} else {
				viewHolder = (ViewHolder) convertView.getTag();
			}

			viewHolder.cus_name.setText(tmp.userName);
			viewHolder.comments_content.setText(tmp.content);
			viewHolder.goods_comment_score.setRating(tmp.rating);

			return convertView;
		}

		private final class ViewHolder {
			TextView cus_name = null;
			TextView comments_content = null;
			RatingBar goods_comment_score = null;
		}
	}

	private final class MyPageApdater extends PagerAdapter {

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
		 * @see android.support.v4.view.PagerAdapter#getCount()
		 */
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return mViewList.size();
		}

		/**
		 * (非 Javadoc)
		 * <p>
		 * Title: isViewFromObject
		 * </p>
		 * <p>
		 * Description:
		 * </p>
		 * 
		 * @param arg0
		 * @param arg1
		 * @return
		 * @see android.support.v4.view.PagerAdapter#isViewFromObject(android.view.View,
		 *      java.lang.Object)
		 */
		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			// TODO Auto-generated method stub
			return arg0 == arg1;
		}

		/**
		 * (非 Javadoc)
		 * <p>
		 * Title: destroyItem
		 * </p>
		 * <p>
		 * Description:
		 * </p>
		 * 
		 * @param container
		 * @param position
		 * @param object
		 * @see android.support.v4.view.PagerAdapter#destroyItem(android.view.ViewGroup,
		 *      int, java.lang.Object)
		 */
		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			// TODO Auto-generated method stub
			// super.destroyItem(container, position, object);
			container.removeView(mViewList.get(position));
		}

		/**
		 * (非 Javadoc)
		 * <p>
		 * Title: getPageTitle
		 * </p>
		 * <p>
		 * Description:
		 * </p>
		 * 
		 * @param position
		 * @return
		 * @see android.support.v4.view.PagerAdapter#getPageTitle(int)
		 */
		@Override
		public CharSequence getPageTitle(int position) {
			// TODO Auto-generated method stub
			return mTitleList[position];
		}

		/**
		 * (非 Javadoc)
		 * <p>
		 * Title: instantiateItem
		 * </p>
		 * <p>
		 * Description:
		 * </p>
		 * 
		 * @param container
		 * @param position
		 * @return
		 * @see android.support.v4.view.PagerAdapter#instantiateItem(android.view.ViewGroup,
		 *      int)
		 */
		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			// TODO Auto-generated method stub
			container.addView(mViewList.get(position));

			return mViewList.get(position);
		}

	}

}
