/**   
 * @Title: UIHelper.java 
 * @Package com.sendwine.app 
 * @Description: 
 *			
 * @author  张雷 794857063@qq.com
 * @date 2014-1-1 下午12:31:55 
 * @version V1.0   
 */

package com.sendwine.app;

import com.sendwine.app.ui.AppStart;
import com.sendwine.app.ui.ArticleDetail;
import com.sendwine.app.ui.Cart;
import com.sendwine.app.ui.GoodsDetail;
import com.sendwine.app.ui.GoodsList;
import com.sendwine.app.ui.Login;
import com.sendwine.app.ui.MainActivity;
import com.sendwine.app.ui.Register;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 * @ClassName: UIHelper
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author 张雷 794857063@qq.com
 * @date 2014-1-1 下午12:31:55
 * 
 */
public class UIHelper {
	/**
	 * 
	 * @Title: showActivtity
	 * @Description: TODO
	 * @param @param activity
	 * 
	 * @param @param bundle 参数
	 * @param @param cls 组件名称
	 * 
	 * @return void 返回类型
	 * @throws
	 */
	public void showActivtity(Activity activity, Bundle bundle, Class<?> cls) {
		Intent intent = new Intent(activity, cls);
		if (null != bundle) {
			intent.putExtras(bundle);
		}

		activity.startActivity(intent);
		activity.overridePendingTransition(R.anim.in_from_right,
				R.anim.out_to_left);
	}

	/**
	 * 显示注册界面
	 * 
	 * @Title: showRegisterActivity
	 * @Description: TODO
	 * @param @param activity
	 * @param @param bundle 设定文件
	 * @return void 返回类型
	 * @throws
	 */
	public void showRegisterActivity(Activity activity, Bundle bundle) {

		Intent intent = new Intent(activity, Register.class);
		if (null != bundle) {
			intent.putExtras(bundle);
		}

		activity.startActivity(intent);
		activity.overridePendingTransition(R.anim.in_from_right,
				R.anim.out_to_left);

	}

	public void showLogin(Activity activity, Bundle bundle) {

		Intent intent = new Intent(activity, Login.class);
		if (null != bundle) {
			intent.putExtras(bundle);
		}

		activity.startActivity(intent);
		activity.overridePendingTransition(R.anim.in_from_right,
				R.anim.out_to_left);

		if (activity instanceof AppStart) {
			activity.finish();
		}
	}

	public void showArticleDetail(Activity activity, Bundle bundle) {

		Intent intent = new Intent(activity, ArticleDetail.class);
		if (null != bundle) {
			intent.putExtras(bundle);
		}

		activity.startActivity(intent);
		activity.overridePendingTransition(R.anim.in_from_right,
				R.anim.out_to_left);

	}

	public void showGoodsDetail(Activity activity, Bundle bundle) {

		Intent intent = new Intent(activity, GoodsDetail.class);
		if (null != bundle) {
			intent.putExtras(bundle);
		}

		activity.startActivity(intent);
		activity.overridePendingTransition(R.anim.in_from_right,
				R.anim.out_to_left);

	}

	public void showCart(Activity activity, Bundle bundle) {

		Intent intent = new Intent(activity, Cart.class);
		if (null != bundle) {
			intent.putExtras(bundle);
		}

		activity.startActivity(intent);
		activity.overridePendingTransition(R.anim.in_from_right,
				R.anim.out_to_left);

	}

	public void showGoodLists(Activity activity, Bundle bundle) {

		Intent intent = new Intent(activity, GoodsList.class);
		if (null != bundle) {
			intent.putExtras(bundle);
		}

		activity.startActivity(intent);
		activity.overridePendingTransition(R.anim.in_from_right,
				R.anim.out_to_left);

	}

	/**
	 * 显示主界面
	 * 
	 * @Title: showMainActivity
	 * @Description: TODO
	 * @param @param activity
	 * @param @param bundle 设定文件
	 * @return void 返回类型
	 * @throws
	 */
	public void showMainActivity(Activity activity, Bundle bundle) {

		Intent intent = new Intent(activity, MainActivity.class);
		if (null != bundle) {
			intent.putExtras(bundle);
		}

		activity.startActivity(intent);
		activity.overridePendingTransition(R.anim.in_from_right,
				R.anim.out_to_left);
		if (activity instanceof Login) {
			activity.finish();
		}
	}
}
