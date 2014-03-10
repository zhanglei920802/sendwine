/**   
* @Title: AnimatorProxy.java 
* @Package com.actionbarsherlock.internal.nineoldandroids.view.animation 
* @Description: 
*			声明:
*			1)掌控校园是本人的大学的创业作品,也是本人的毕业设计
*			2)本程序尚未进行开放源代码,所以禁止组织或者是个人泄露源码,否则将会追究其刑事责任
*			3)编写本软件,我需要感谢彭老师以及其他鼓励和支持我的同学以及朋友
*			4)本程序的最终所有权属于本人	
* @author  张雷 794857063@qq.com
* @date 2013-11-14 19:45:01 
* @version V1.0   
*/
package com.actionbarsherlock.internal.nineoldandroids.view.animation;

import java.lang.ref.WeakReference;
import java.util.WeakHashMap;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.os.Build;
import android.util.FloatMath;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;

// TODO: Auto-generated Javadoc
/**
 * The Class AnimatorProxy.
 */
public final class AnimatorProxy extends Animation {
	
	/** The Constant NEEDS_PROXY. */
	public static final boolean NEEDS_PROXY = Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB;

	/** The Constant PROXIES. */
	private static final WeakHashMap<View, AnimatorProxy> PROXIES = new WeakHashMap<View, AnimatorProxy>();

	/**
	 * Wrap.
	 * 
	 * @param view
	 *            the view
	 * @return the animator proxy
	 */
	public static AnimatorProxy wrap(View view) {
		AnimatorProxy proxy = PROXIES.get(view);
		if (proxy == null) {
			proxy = new AnimatorProxy(view);
			PROXIES.put(view, proxy);
		}
		return proxy;
	}

	/** The m view. */
	private final WeakReference<View> mView;

	/** The m alpha. */
	private float mAlpha = 1;
	
	/** The m scale x. */
	private float mScaleX = 1;
	
	/** The m scale y. */
	private float mScaleY = 1;
	
	/** The m translation x. */
	private float mTranslationX;
	
	/** The m translation y. */
	private float mTranslationY;

	/** The m before. */
	private final RectF mBefore = new RectF();
	
	/** The m after. */
	private final RectF mAfter = new RectF();
	
	/** The m temp matrix. */
	private final Matrix mTempMatrix = new Matrix();

	/**
	 * Instantiates a new animator proxy.
	 * 
	 * @param view
	 *            the view
	 */
	private AnimatorProxy(View view) {
		setDuration(0); // perform transformation immediately
		setFillAfter(true); // persist transformation beyond duration
		view.setAnimation(this);
		mView = new WeakReference<View>(view);
	}

	/**
	 * Gets the alpha.
	 * 
	 * @return the alpha
	 */
	public float getAlpha() {
		return mAlpha;
	}

	/**
	 * Sets the alpha.
	 * 
	 * @param alpha
	 *            the new alpha
	 */
	public void setAlpha(float alpha) {
		if (mAlpha != alpha) {
			mAlpha = alpha;
			View view = mView.get();
			if (view != null) {
				view.invalidate();
			}
		}
	}

	/**
	 * Gets the scale x.
	 * 
	 * @return the scale x
	 */
	public float getScaleX() {
		return mScaleX;
	}

	/**
	 * Sets the scale x.
	 * 
	 * @param scaleX
	 *            the new scale x
	 */
	public void setScaleX(float scaleX) {
		if (mScaleX != scaleX) {
			prepareForUpdate();
			mScaleX = scaleX;
			invalidateAfterUpdate();
		}
	}

	/**
	 * Gets the scale y.
	 * 
	 * @return the scale y
	 */
	public float getScaleY() {
		return mScaleY;
	}

	/**
	 * Sets the scale y.
	 * 
	 * @param scaleY
	 *            the new scale y
	 */
	public void setScaleY(float scaleY) {
		if (mScaleY != scaleY) {
			prepareForUpdate();
			mScaleY = scaleY;
			invalidateAfterUpdate();
		}
	}

	/**
	 * Gets the scroll x.
	 * 
	 * @return the scroll x
	 */
	public int getScrollX() {
		View view = mView.get();
		if (view == null) {
			return 0;
		}
		return view.getScrollX();
	}

	/**
	 * Sets the scroll x.
	 * 
	 * @param value
	 *            the new scroll x
	 */
	public void setScrollX(int value) {
		View view = mView.get();
		if (view != null) {
			view.scrollTo(value, view.getScrollY());
		}
	}

	/**
	 * Gets the scroll y.
	 * 
	 * @return the scroll y
	 */
	public int getScrollY() {
		View view = mView.get();
		if (view == null) {
			return 0;
		}
		return view.getScrollY();
	}

	/**
	 * Sets the scroll y.
	 * 
	 * @param value
	 *            the new scroll y
	 */
	public void setScrollY(int value) {
		View view = mView.get();
		if (view != null) {
			view.scrollTo(view.getScrollY(), value);
		}
	}

	/**
	 * Gets the translation x.
	 * 
	 * @return the translation x
	 */
	public float getTranslationX() {
		return mTranslationX;
	}

	/**
	 * Sets the translation x.
	 * 
	 * @param translationX
	 *            the new translation x
	 */
	public void setTranslationX(float translationX) {
		if (mTranslationX != translationX) {
			prepareForUpdate();
			mTranslationX = translationX;
			invalidateAfterUpdate();
		}
	}

	/**
	 * Gets the translation y.
	 * 
	 * @return the translation y
	 */
	public float getTranslationY() {
		return mTranslationY;
	}

	/**
	 * Sets the translation y.
	 * 
	 * @param translationY
	 *            the new translation y
	 */
	public void setTranslationY(float translationY) {
		if (mTranslationY != translationY) {
			prepareForUpdate();
			mTranslationY = translationY;
			invalidateAfterUpdate();
		}
	}

	/**
	 * Prepare for update.
	 */
	private void prepareForUpdate() {
		View view = mView.get();
		if (view != null) {
			computeRect(mBefore, view);
		}
	}

	/**
	 * Invalidate after update.
	 */
	private void invalidateAfterUpdate() {
		View view = mView.get();
		if (view == null) {
			return;
		}
		View parent = (View) view.getParent();
		if (parent == null) {
			return;
		}

		view.setAnimation(this);

		final RectF after = mAfter;
		computeRect(after, view);
		after.union(mBefore);

		parent.invalidate((int) FloatMath.floor(after.left),
				(int) FloatMath.floor(after.top),
				(int) FloatMath.ceil(after.right),
				(int) FloatMath.ceil(after.bottom));
	}

	/**
	 * Compute rect.
	 * 
	 * @param r
	 *            the r
	 * @param view
	 *            the view
	 */
	private void computeRect(final RectF r, View view) {
		// compute current rectangle according to matrix transformation
		final float w = view.getWidth();
		final float h = view.getHeight();

		// use a rectangle at 0,0 to make sure we don't run into issues with
		// scaling
		r.set(0, 0, w, h);

		final Matrix m = mTempMatrix;
		m.reset();
		transformMatrix(m, view);
		mTempMatrix.mapRect(r);

		r.offset(view.getLeft(), view.getTop());

		// Straighten coords if rotations flipped them
		if (r.right < r.left) {
			final float f = r.right;
			r.right = r.left;
			r.left = f;
		}
		if (r.bottom < r.top) {
			final float f = r.top;
			r.top = r.bottom;
			r.bottom = f;
		}
	}

	/**
	 * Transform matrix.
	 * 
	 * @param m
	 *            the m
	 * @param view
	 *            the view
	 */
	private void transformMatrix(Matrix m, View view) {
		final float w = view.getWidth();
		final float h = view.getHeight();

		final float sX = mScaleX;
		final float sY = mScaleY;
		if ((sX != 1.0f) || (sY != 1.0f)) {
			final float deltaSX = ((sX * w) - w) / 2f;
			final float deltaSY = ((sY * h) - h) / 2f;
			m.postScale(sX, sY);
			m.postTranslate(-deltaSX, -deltaSY);
		}
		m.postTranslate(mTranslationX, mTranslationY);
	}

	/** 
	* <p>Title: applyTransformation</p> 
	* <p>Description: </p> 
	* @param interpolatedTime
	* @param t 
	* @see android.view.animation.Animation#applyTransformation(float, android.view.animation.Transformation) 
	*/
	@Override
	protected void applyTransformation(float interpolatedTime, Transformation t) {
		View view = mView.get();
		if (view != null) {
			t.setAlpha(mAlpha);
			transformMatrix(t.getMatrix(), view);
		}
	}

	/** 
	* <p>Title: reset</p> 
	* <p>Description: </p>  
	* @see android.view.animation.Animation#reset() 
	*/
	@Override
	public void reset() {
		/* Do nothing. */
	}
}
