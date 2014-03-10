/**   
 * @Title: AppContext.java 
 * @Package com.sendwine.app 
 * @Description: 
 *			
 * @author  张雷 794857063@qq.com
 * @date 2014-1-1 下午12:01:52 
 * @version V1.0   
 */

package com.sendwine.app;

import android.app.Application;

/**
 * @ClassName: AppContext
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author 张雷 794857063@qq.com
 * @date 2014-1-1 下午12:01:52
 * 
 */
public class AppContext extends Application {

	private final String LOG_TAG = "AppContext";

	/** 应用程序管理类 */
	public static AppManager mAppManger;

	public static UIHelper mUiHelper;

	/**
	 * (非 Javadoc)
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
