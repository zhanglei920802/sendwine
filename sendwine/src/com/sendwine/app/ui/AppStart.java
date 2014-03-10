/**   
 * @Title: AppStart.java 
 * @Package com.sendwine.app.ui 
 * @Description: 
 *			
 * @author  张雷 794857063@qq.com
 * @date 2014-1-1 下午12:13:52 
 * @version V1.0   
 */

package com.sendwine.app.ui;

import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;

import com.sendwine.app.AppContext;
import com.sendwine.app.R;

/**
 * @ClassName: AppStart
 * @Description: TODO 程序启动界面
 * @author 张雷 794857063@qq.com
 * @date 2014-1-1 下午12:13:52
 * 
 */
public class AppStart extends BaseActivity {

	/** 日志TAG */
	private final String LOG_TAG = "AppStart";

	/** DEBUG标记 */
	private final boolean DEBUG = true;

	/**
	 * 动画监听器
	 */
	private AnimationListener mAnimationListener = new AnimationListener() {

		@Override
		public void onAnimationEnd(Animation animation) {
			if (DEBUG)
				Log.v(LOG_TAG, "anim end");
			redirect();
		}

		@Override
		public void onAnimationRepeat(Animation animation) {

		}

		@Override
		public void onAnimationStart(Animation animation) {
			if (DEBUG)
				Log.v(LOG_TAG, "anim start");
		}
	};

	/**
	 * 跳转界面
	 * 
	 * @Title: redirect
	 * @Description: TODO
	 * @param 设定文件
	 * @return void 返回类型
	 * @throws
	 */
	private void redirect() {
		AppContext.mUiHelper.showLogin(this, null);
	}

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
		View view = getLayoutInflater().inflate(R.layout.appstart, null);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(view);
		this.initAnimation(view);
	}

	public void initAnimation(View view) {
		Animation anim = new AlphaAnimation(0.7f, 1.0f);
		anim.setDuration(2000);
		anim.setAnimationListener(mAnimationListener);
		view.startAnimation(anim);
		anim = null;
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

}
