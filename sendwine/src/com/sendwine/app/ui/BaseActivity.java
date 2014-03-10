package com.sendwine.app.ui;

import com.sendwine.app.AppContext;

import android.app.Activity;
import android.os.Bundle;

/**   
 * @Title: BaseActivity.java 
 * @Package  
 * @Description: 
 *			
 * @author  张雷 794857063@qq.com
 * @date 2014-1-1 上午11:50:07 
 * @version V1.0   
 */

/**
 * @ClassName: BaseActivity
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author A18ccms a18ccms_gmail_com
 * @date 2014-1-1 上午11:50:07
 * 
 */
public abstract class BaseActivity extends Activity implements ActivityItf {

	/** 日志TAG */
	private final String LOG_TAG = "BaseActivity";

	/** 全局上下文 */
	protected AppContext mAppContext = null;

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
	protected final void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		mAppContext = (AppContext) getApplication();
		AppContext.mAppManger.addActivity(this);
		getPreActivityData();
		initData();
		initView();
		loadData();

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
		AppContext.mAppManger.finishActivity(this);
		super.onDestroy();
	}

	/**
	 * (非 Javadoc)
	 * <p>
	 * Title: onBackPressed
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @see android.app.Activity#onBackPressed()
	 */
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		// super.onBackPressed();
		onDestroy();
	}
}
