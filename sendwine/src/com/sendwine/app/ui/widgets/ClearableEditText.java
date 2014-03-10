/**   
 * @Title: ClearableEditText.java 
 * @Package cn.beyondme.widgets 
 * @Description: 
 *			����:
 *			1)�ƿ�У԰�Ǳ��˵Ĵ�ѧ�Ĵ�ҵ��Ʒ,Ҳ�Ǳ��˵ı�ҵ���
 *			2)��������δ���п���Դ����,���Խ�ֹ��֯�����Ǹ���й¶Դ��,���򽫻�׷������������
 *			3)��д�����,����Ҫ��л����ʦ�Լ�����������֧���ҵ�ͬѧ�Լ�����
 *			4)���������������Ȩ���ڱ���	
 * @author  ���� 794857063@qq.com
 * @date 2013-11-14 19:34:23 
 * @version V1.0   
 */

package com.sendwine.app.ui.widgets;

import com.sendwine.app.R;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.EditText;
import android.view.View.OnFocusChangeListener;

// TODO: Auto-generated Javadoc
/**
 * ����ɾ����ť���ı���
 * 
 * </p> �������Ҫ���������ݱ���յ�ʱ��������������,��ô�����ͨ��{@link #setClearListener(Listener)}
 * ����һ���������ص�{@link Listener},
 * 
 * <i><b>���������û�����������ʱ����Ϊ��Ҫ</b></i>.
 * 
 * @version 1.0
 * @author ����
 */
public class ClearableEditText extends EditText implements OnTouchListener,
		OnFocusChangeListener, TextWatcher {

	/** Log. @hide */
	private String TAG = "MyEditText";

	/** ���ͼƬ. */
	private Drawable mDrawableRight = null;

	/** The m touch listener. */
	private OnTouchListener mTouchListener = null;

	/** The m foucus change listener. */
	private OnFocusChangeListener mFoucusChangeListener = null;

	/** The m text watcher. */
	private TextWatcher mTextWatcher = null;

	/** The m listener. */
	private OnCliearListener mListener = null;

	/**
	 * �������ť������ʱ��,�ص��ýӿ�.
	 * 
	 * @author ����
	 */
	public interface OnCliearListener {

		/**
		 * On clear text.
		 */
		public void onClearText();
	}

	/**
	 * �������������.
	 * 
	 * @param listener
	 *            the new clear listener
	 */
	public void setClearListener(OnCliearListener listener) {
		this.mListener = listener;
	}

	/**
	 * ���ô���������.
	 * 
	 * @param touchListener
	 *            the new touch listener
	 */
	public void setTouchListener(OnTouchListener touchListener) {
		this.mTouchListener = touchListener;
	}

	/**
	 * ���ý��������.
	 * 
	 * @param fouChangeListener
	 *            the new focus change listener
	 */
	public void setFocusChangeListener(OnFocusChangeListener fouChangeListener) {
		this.setmFoucusChangeListener(fouChangeListener);
	}

	/**
	 * ���������.
	 * 
	 * @param textWatcher
	 *            the new text watcher
	 */
	public void setTextWatcher(TextWatcher textWatcher) {
		this.setmTextWatcher(textWatcher);
	}

	/**
	 * Instantiates a new clearable edit text.
	 * 
	 * @param context
	 *            the context
	 * @param attrs
	 *            the attrs
	 * @param defStyle
	 *            the def style
	 */
	public ClearableEditText(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
		// TODO Auto-generated constructor stub
	}

	/**
	 * Instantiates a new clearable edit text.
	 * 
	 * @param context
	 *            the context
	 * @param attrs
	 *            the attrs
	 */
	public ClearableEditText(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
		// TODO Auto-generated constructor stub
	}

	/**
	 * Instantiates a new clearable edit text.
	 * 
	 * @param context
	 *            the context
	 */
	public ClearableEditText(Context context) {
		super(context);
		init();
		// TODO Auto-generated constructor stub
	}

	/**
	 * Init the Drawable Right icon.
	 */
	private void init() {
		mDrawableRight = getCompoundDrawables()[2];
		if (null == mDrawableRight) {
			mDrawableRight = getResources().getDrawable(R.drawable.delete_all);

		}

		mDrawableRight.setBounds(0, 0, mDrawableRight.getIntrinsicWidth(),
				mDrawableRight.getIntrinsicHeight());
		setClearIconVisibility(false);

		super.setOnTouchListener(this);
		super.setOnFocusChangeListener(this);

	}

	/**
	 * <p>
	 * Title: onTouch
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @param v
	 * @param event
	 * @return
	 * @see android.view.View.OnTouchListener#onTouch(android.view.View,
	 *      android.view.MotionEvent)
	 */
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		// TODO Auto-generated method stub
		if (getCompoundDrawables()[2] != null) {

			// �ж��Ƿ񱻵��
			boolean taped = event.getX() > (getWidth() - getPaddingRight() - getCompoundDrawables()[2]
					.getIntrinsicWidth());

			if (taped) {
				Log.d(TAG, "Drawable Right Clicked");
				// /������벢�ص�
				setText("");
				if (null != mListener) {
					mListener.onClearText();
				}

				return true;
			}

		}

		// ��������˴�������,��ô���¼����ϲ��׳�
		if (mTouchListener != null) {
			return mTouchListener.onTouch(v, event);
		}
		return false;
	}

	/**
	 * ����ȡ���������ý����ʱ�򽫻ᱻ����.
	 * 
	 * @param v
	 *            the v
	 * @param hasFocus
	 *            the has focus
	 */
	@Override
	public void onFocusChange(View v, boolean hasFocus) {
		// TODO Auto-generated method stub
		if (hasFocus) {
			setClearIconVisibility(!TextUtils.isEmpty(getText().toString()));
		} else {
			setClearIconVisibility(false);
		}
	}

	/**
	 * set hide or show right icon.
	 * 
	 * @param visibility
	 *            the new clear icon visibility
	 */
	protected void setClearIconVisibility(boolean visibility) {
		Log.d(TAG, "visibility:" + visibility);
		setCompoundDrawables(getCompoundDrawables()[0],
				getCompoundDrawables()[1], visibility ? mDrawableRight : null,
				getCompoundDrawables()[3]);
	}

	/**
	 * <p>
	 * Title: beforeTextChanged
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @param s
	 * @param start
	 * @param count
	 * @param after
	 * @see android.text.TextWatcher#beforeTextChanged(java.lang.CharSequence,
	 *      int, int, int)
	 */
	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {
		// TODO Auto-generated method stub

	}

	/**
	 * <p>
	 * Title: afterTextChanged
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @param s
	 * @see android.text.TextWatcher#afterTextChanged(android.text.Editable)
	 */
	@Override
	public void afterTextChanged(Editable s) {
		// TODO Auto-generated method stub

	}

	/**
	 * <p>
	 * Title: onTextChanged
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @param s
	 * @param start
	 * @param before
	 * @param count
	 * @see android.widget.TextView#onTextChanged(java.lang.CharSequence, int,
	 *      int, int)
	 */
	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		if (isFocused()) {
			setClearIconVisibility(!TextUtils.isEmpty(s));
		}
	}

	/**
	 * Gets the m foucus change listener.
	 * 
	 * @return the m foucus change listener
	 */
	public OnFocusChangeListener getmFoucusChangeListener() {
		return mFoucusChangeListener;
	}

	/**
	 * Sets the m foucus change listener.
	 * 
	 * @param mFoucusChangeListener
	 *            the new m foucus change listener
	 */
	public void setmFoucusChangeListener(
			OnFocusChangeListener mFoucusChangeListener) {
		this.mFoucusChangeListener = mFoucusChangeListener;
	}

	/**
	 * Gets the m text watcher.
	 * 
	 * @return the m text watcher
	 */
	public TextWatcher getmTextWatcher() {
		return mTextWatcher;
	}

	/**
	 * Sets the m text watcher.
	 * 
	 * @param mTextWatcher
	 *            the new m text watcher
	 */
	public void setmTextWatcher(TextWatcher mTextWatcher) {
		this.mTextWatcher = mTextWatcher;
	}
}
