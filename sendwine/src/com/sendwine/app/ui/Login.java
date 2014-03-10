/**   
 * @Title: Login.java 
 * @Package com.sendwine.app.ui 
 * @Description: 
 *			
 * @author  张雷 794857063@qq.com
 * @date 2014-1-1 下午12:12:18 
 * @version V1.0   
 */

package com.sendwine.app.ui;

import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;

import com.sendwine.app.AppContext;
import com.sendwine.app.R;
import com.sendwine.app.ui.widgets.ClearableEditText;
import com.sendwine.app.ui.widgets.ClearableEditText.OnCliearListener;

/**
 * @ClassName: Login
 * @Description: TODO(用户登录界面)
 * @author 张雷 794857063@qq.com
 * @date 2014-1-1 下午12:12:18
 * 
 */
public class Login extends BaseActivity {

	private final String LOG_TAG = "Login";

	private final boolean DEBUG = true;

	// view
	private ClearableEditText tel = null;

	private ClearableEditText pwd = null;

	private CheckBox remember_me = null;

	private TextView register_new = null;

	private Button login = null;

	private ImageButton go_back = null;

	private TextView detail_title = null;

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
		setContentView(R.layout.login);

		{
			tel = (ClearableEditText) findViewById(R.id.tel);
			tel.setClearListener(new OnCliearListener() {

				@Override
				public void onClearText() {// /当点击清楚按钮时候，应该重置密码
					// TODO Auto-generated method stub
					if (null != pwd) {
						if (DEBUG) {
							Log.v(LOG_TAG, "user clicked clear tel");
						}
						pwd.setText("");
					}
				}
			});
			pwd = (ClearableEditText) findViewById(R.id.pwd);

			remember_me = (CheckBox) findViewById(R.id.remember_me);
			register_new = (TextView) findViewById(R.id.register_new);
			register_new.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {// 转到注册
					// TODO Auto-generated method stub
					AppContext.mUiHelper.showRegisterActivity(Login.this, null);
				}
			});

			login = (Button) findViewById(R.id.login);
			login.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					if (DEBUG)
						Log.v(LOG_TAG, "user clicked login");

					AppContext.mUiHelper.showMainActivity(Login.this, null);
				}
			});

			go_back = (ImageButton) findViewById(R.id.go_back);
			go_back.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					onDestroy();
				}
			});

			detail_title = (TextView) findViewById(R.id.detail_title);
			detail_title.setText("登录");
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

}
