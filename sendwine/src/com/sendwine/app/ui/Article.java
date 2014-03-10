/**   
 * @Title: Article.java 
 * @Package com.sendwine.app.ui 
 * @Description: 
 *			
 * @author  张雷 794857063@qq.com
 * @date 2014-1-3 下午5:29:43 
 * @version V1.0   
 */

package com.sendwine.app.ui;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.sendwine.app.AppContext;
import com.sendwine.app.R;

/**
 * @ClassName: Article
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author 张雷 794857063@qq.com
 * @date 2014-1-3 下午5:29:43
 * 
 */
public class Article extends BaseActivity {

	private ImageButton go_back;
	private TextView detail_title;
	private ListView lv_articles;
	private List<com.sendwine.app.bean.Article> mArticles = null;
	private ArticleAdapter mArticleAdapter = null;

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
			mArticleAdapter = new ArticleAdapter();
			
			mArticles = new ArrayList<com.sendwine.app.bean.Article>();
			mArticles.add(new com.sendwine.app.bean.Article("中国白酒的发展历史","中国白酒的发展历史中国白酒的发展历史中国白酒的发展历史中国白酒的发展历史中国白酒的发展历史中国白酒的发展历史中国白酒的发展历史中国白酒的发展历史"));
			mArticles.addAll(mArticles.subList(0, mArticles.size()));
			mArticles.addAll(mArticles.subList(0, mArticles.size()));
			mArticles.addAll(mArticles.subList(0, mArticles.size()));
			mArticles.addAll(mArticles.subList(0, mArticles.size()));
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
		setContentView(R.layout.article_list);
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
			detail_title.setText("酒文化");

		}

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
					bundle.putSerializable("article", mArticleAdapter.getItem(position));
					AppContext.mUiHelper.showArticleDetail(Article.this, bundle);
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
						R.layout.simple_list_item, null);
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
}
