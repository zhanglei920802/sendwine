package com.sendwine.app.ui;

import com.sendwine.app.AppContext;

import android.app.Activity;
import android.os.Bundle;

/**   
 * @Title: BaseActivity.java 
 * @Package  
 * @Description: 
 *			
 * @author  ���� 794857063@qq.com
 * @date 2014-1-1 ����11:50:07 
 * @version V1.0   
 */

/**
 * @ClassName: BaseActivity
 * @Description: TODO(������һ�仰��������������)
 * @author A18ccms a18ccms_gmail_com
 * @date 2014-1-1 ����11:50:07
 * 
 */
public abstract class BaseActivity extends Activity implements ActivityItf {

	/** ��־TAG */
	private final String LOG_TAG = "BaseActivity";

	/** ȫ�������� */
	protected AppContext mAppContext = null;

	/**
	 * (�� Javadoc)
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
	 * (�� Javadoc)
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
	 * (�� Javadoc)
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
	 * (�� Javadoc)
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
	 * (�� Javadoc)
	 * <p>
	 * Title: onCreate
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * ����ʹ��ģ�����ģʽ,���ٴ��������� ����,ʹ��<code>final</code>�ؼ���,�������಻�淶�Ĳ��� </p>
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
	 * (�� Javadoc)
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
	 * (�� Javadoc)
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
