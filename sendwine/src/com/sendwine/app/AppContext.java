/**   
 * @Title: AppContext.java 
 * @Package com.sendwine.app 
 * @Description: 
 *			
 * @author  ���� 794857063@qq.com
 * @date 2014-1-1 ����12:01:52 
 * @version V1.0   
 */

package com.sendwine.app;

import android.app.Application;

/**
 * @ClassName: AppContext
 * @Description: TODO(������һ�仰��������������)
 * @author ���� 794857063@qq.com
 * @date 2014-1-1 ����12:01:52
 * 
 */
public class AppContext extends Application {

	private final String LOG_TAG = "AppContext";

	/** Ӧ�ó�������� */
	public static AppManager mAppManger;

	public static UIHelper mUiHelper;

	/**
	 * (�� Javadoc)
	 * <p>
	 * Title: onCreate
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @see android.app.Application#onCreate()
	 */
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		mAppManger = AppManager.getInstance();
		mUiHelper = new UIHelper();
	}
}
