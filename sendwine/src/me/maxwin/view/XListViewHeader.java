/**   
 * @Title: XListViewHeader.java 
 * @Package me.maxwin.view 
 * @Description: 
 *			����:
 *			1)�ƿ�У԰�Ǳ��˵Ĵ�ѧ�Ĵ�ҵ��Ʒ,Ҳ�Ǳ��˵ı�ҵ���
 *			2)��������δ���п���Դ����,���Խ�ֹ��֯�����Ǹ���й¶Դ��,���򽫻�׷������������
 *			3)��д�����,����Ҫ��л����ʦ�Լ�����������֧���ҵ�ͬѧ�Լ�����
 *			4)���������������Ȩ���ڱ���	
 * @author  ���� 794857063@qq.com
 * @date 2013-11-14 19:34:19 
 * @version V1.0   
 */
package me.maxwin.view;

import com.sendwine.app.R;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

// TODO: Auto-generated Javadoc
/**
 * The Class XListViewHeader.
 */
public class XListViewHeader extends LinearLayout {

	/** The m container. */
	private LinearLayout mContainer;

	/** The m arrow image view. */
	private ImageView mArrowImageView;

	/** The m progress bar. */
	private ProgressBar mProgressBar;

	/** The m hint text view. */
	private TextView mHintTextView;

	/** The m state. */
	private int mState = STATE_NORMAL;

	/** The m rotate up anim. */
	private Animation mRotateUpAnim;

	/** The m rotate down anim. */
	private Animation mRotateDownAnim;

	/** The rotate anim duration. */
	private final int ROTATE_ANIM_DURATION = 180;

	/** The Constant STATE_NORMAL. */
	public final static int STATE_NORMAL = 0;

	/** The Constant STATE_READY. */
	public final static int STATE_READY = 1;

	/** The Constant STATE_REFRESHING. */
	public final static int STATE_REFRESHING = 2;

	/**
	 * Instantiates a new x list view header.
	 * 
	 * @param context
	 *            the context
	 */
	public XListViewHeader(Context context) {
		super(context);
		initView(context);
	}

	/**
	 * Instantiates a new x list view header.
	 * 
	 * @param context
	 *            the context
	 * @param attrs
	 *            the attrs
	 */
	public XListViewHeader(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView(context);
	}

	/**
	 * Gets the visiable height.
	 * 
	 * @return the visiable height
	 */
	public int getVisiableHeight() {
		return mContainer.getHeight();
	}

	/**
	 * Inits the view.
	 * 
	 * @param context
	 *            the context
	 */
	private void initView(Context context) {
		// ��ʼ�������������ˢ��view�߶�Ϊ0
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
				android.view.ViewGroup.LayoutParams.FILL_PARENT, 0);
		mContainer = (LinearLayout) LayoutInflater.from(context).inflate(
				R.layout.xlistview_header, null);
		addView(mContainer, lp);
		setGravity(Gravity.BOTTOM);

		mArrowImageView = (ImageView) findViewById(R.id.xlistview_header_arrow);
		mHintTextView = (TextView) findViewById(R.id.xlistview_header_hint_textview);
		mProgressBar = (ProgressBar) findViewById(R.id.xlistview_header_progressbar);

		mRotateUpAnim = new RotateAnimation(0.0f, -180.0f,
				Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
				0.5f);
		mRotateUpAnim.setDuration(ROTATE_ANIM_DURATION);
		mRotateUpAnim.setFillAfter(true);
		mRotateDownAnim = new RotateAnimation(-180.0f, 0.0f,
				Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
				0.5f);
		mRotateDownAnim.setDuration(ROTATE_ANIM_DURATION);
		mRotateDownAnim.setFillAfter(true);
	}

	/**
	 * Sets the state.
	 * 
	 * @param state
	 *            the new state
	 */
	public void setState(int state) {
		if (state == mState)
			return;

		if (state == STATE_REFRESHING) { // ��ʾ����
			mArrowImageView.clearAnimation();
			mArrowImageView.setVisibility(View.GONE);
			mProgressBar.setVisibility(View.VISIBLE);
		} else { // ��ʾ��ͷͼƬ
			mArrowImageView.setVisibility(View.VISIBLE);
			mProgressBar.setVisibility(View.GONE);
		}

		switch (state) {
		case STATE_NORMAL:
			if (mState == STATE_READY) {
				mArrowImageView.startAnimation(mRotateDownAnim);
			}
			if (mState == STATE_REFRESHING) {
				mArrowImageView.clearAnimation();
			}
			mHintTextView.setText(R.string.xlistview_header_hint_normal);
			break;
		case STATE_READY:
			if (mState != STATE_READY) {
				mArrowImageView.clearAnimation();
				mArrowImageView.startAnimation(mRotateUpAnim);
				mHintTextView.setText(R.string.xlistview_header_hint_ready);
			}
			break;
		case STATE_REFRESHING:
			mHintTextView.setText(R.string.xlistview_header_hint_loading);
			break;
		default:
		}

		mState = state;
	}

	/**
	 * Sets the visiable height.
	 * 
	 * @param height
	 *            the new visiable height
	 */
	public void setVisiableHeight(int height) {
		if (height < 0)
			height = 0;
		LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) mContainer
				.getLayoutParams();
		lp.height = height;
		mContainer.setLayoutParams(lp);
	}

}
