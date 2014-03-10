/**   
 * @Title: Register.java 
 * @Package com.sendwine.app.ui 
 * @Description: 
 *			
 * @author  ���� 794857063@qq.com
 * @date 2014-1-1 ����2:07:42 
 * @version V1.0   
 */

package com.sendwine.app.ui;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.sendwine.app.R;
import com.sendwine.app.ui.widgets.ClearableEditText;
import com.sendwine.app.ui.widgets.ClearableEditText.OnCliearListener;

/**
 * @ClassName: Register
 * @Description: TODO(������һ�仰��������������)
 * @author ���� 794857063@qq.com
 * @date 2014-1-1 ����2:07:42
 * 
 */
public class Register extends BaseActivity {
	private final String LOG_TAG = "Register";

	private final boolean DEBUG = true;

	// view
	private ClearableEditText tel = null;

	private EditText very_code = null;

	private Button get_code = null;

	private ClearableEditText pwd = null;

	private ClearableEditText confirm_pwd = null;

	private Button register = null;

	private View go_back;

	private TextView detail_title;

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
		setContentView(R.layout.register);
		{
			tel = (ClearableEditText) findViewById(R.id.tel);
			tel.setClearListener(new OnCliearListener() {

				@Override
				public void onClearText() {
					// TODO Auto-generated method stub
					if (null != pwd) {
						pwd.setText("");
					}

					if (null != confirm_pwd) {
						confirm_pwd.setText("");
					}
				}
			});

			very_code = (EditText) findViewById(R.id.very_code);
			get_code = (Button) findViewById(R.id.get_code);
			get_code.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub

				}
			});

			pwd = (ClearableEditText) findViewById(R.id.pwd);
			confirm_pwd = (ClearableEditText) findViewById(R.id.confirm_pwd);
			register = (Button) findViewById(R.id.register);
			register.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub

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
			detail_title.setText("ע��");
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
