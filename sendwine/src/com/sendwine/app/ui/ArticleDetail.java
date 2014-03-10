/**   
 * @Title: ArticleDetail.java 
 * @Package com.sendwine.app.ui 
 * @Description: 
 *			
 * @author  ���� 794857063@qq.com
 * @date 2014-1-3 ����5:52:02 
 * @version V1.0   
 */

package com.sendwine.app.ui;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;

import com.sendwine.app.R;
import com.sendwine.app.bean.Article;

/**
 * @ClassName: ArticleDetail
 * @Description: TODO(������һ�仰��������������)
 * @author ���� 794857063@qq.com
 * @date 2014-1-3 ����5:52:02
 * 
 */
public class ArticleDetail extends BaseActivity {

	private TextView detail_title;
	private View go_back;

	private TextView title = null;
	private TextView content = null;

	private com.sendwine.app.bean.Article article = null;

	/**
	 * (�� Javadoc)
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
		article = (Article) getIntent().getSerializableExtra("article");
		if (article == null) {
			onDestroy();
		}
	}

	/**
	 * (�� Javadoc)
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

	}

	/**
	 * (�� Javadoc)
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
		setContentView(R.layout.artical_detail);

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
			detail_title.setText("��������");

		}

		{
			title = (TextView) findViewById(R.id.title);
			content = (TextView) findViewById(R.id.content);

			title.setText(article.title);
			content.setText(article.content);
		}
	}

	/**
	 * (�� Javadoc)
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

}
