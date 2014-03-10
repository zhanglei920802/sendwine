/**   
* @Title: FloatKeyframeSet.java 
* @Package com.actionbarsherlock.internal.nineoldandroids.animation 
* @Description: 
*			声明:
*			1)掌控校园是本人的大学的创业作品,也是本人的毕业设计
*			2)本程序尚未进行开放源代码,所以禁止组织或者是个人泄露源码,否则将会追究其刑事责任
*			3)编写本软件,我需要感谢彭老师以及其他鼓励和支持我的同学以及朋友
*			4)本程序的最终所有权属于本人	
* @author  张雷 794857063@qq.com
* @date 2013-11-14 19:33:53 
* @version V1.0   
*/

package com.actionbarsherlock.internal.nineoldandroids.animation;

import java.util.ArrayList;
import android.view.animation.Interpolator;

import com.actionbarsherlock.internal.nineoldandroids.animation.Keyframe.FloatKeyframe;

// TODO: Auto-generated Javadoc
/**
 * This class holds a collection of FloatKeyframe objects and is called by
 * ValueAnimator to calculate values between those keyframes for a given
 * animation. The class internal to the animation package because it is an
 * implementation detail of how Keyframes are stored and used.
 * 
 * <p>
 * This type-specific subclass of KeyframeSet, along with the other
 * type-specific subclass for int, exists to speed up the getValue() method when
 * there is no custom TypeEvaluator set for the animation, so that values can be
 * calculated without autoboxing to the Object equivalents of these primitive
 * types.
 * </p>
 */
@SuppressWarnings("unchecked")
class FloatKeyframeSet extends KeyframeSet {
	
	/** The first value. */
	private float firstValue;
	
	/** The last value. */
	private float lastValue;
	
	/** The delta value. */
	private float deltaValue;
	
	/** The first time. */
	private boolean firstTime = true;

	/**
	 * Instantiates a new float keyframe set.
	 * 
	 * @param keyframes
	 *            the keyframes
	 */
	public FloatKeyframeSet(FloatKeyframe... keyframes) {
		super(keyframes);
	}

	/** 
	* <p>Title: getValue</p> 
	* <p>Description: </p> 
	* @param fraction
	* @return 
	* @see com.actionbarsherlock.internal.nineoldandroids.animation.KeyframeSet#getValue(float) 
	*/
	@Override
	public Object getValue(float fraction) {
		return getFloatValue(fraction);
	}

	/** 
	* <p>Title: clone</p> 
	* <p>Description: </p> 
	* @return 
	* @see com.actionbarsherlock.internal.nineoldandroids.animation.KeyframeSet#clone() 
	*/
	@Override
	public FloatKeyframeSet clone() {
		ArrayList<Keyframe> keyframes = mKeyframes;
		int numKeyframes = mKeyframes.size();
		FloatKeyframe[] newKeyframes = new FloatKeyframe[numKeyframes];
		for (int i = 0; i < numKeyframes; ++i) {
			newKeyframes[i] = (FloatKeyframe) keyframes.get(i).clone();
		}
		FloatKeyframeSet newSet = new FloatKeyframeSet(newKeyframes);
		return newSet;
	}

	/**
	 * Gets the float value.
	 * 
	 * @param fraction
	 *            the fraction
	 * @return the float value
	 */
	public float getFloatValue(float fraction) {
		if (mNumKeyframes == 2) {
			if (firstTime) {
				firstTime = false;
				firstValue = ((FloatKeyframe) mKeyframes.get(0))
						.getFloatValue();
				lastValue = ((FloatKeyframe) mKeyframes.get(1)).getFloatValue();
				deltaValue = lastValue - firstValue;
			}
			if (mInterpolator != null) {
				fraction = mInterpolator.getInterpolation(fraction);
			}
			if (mEvaluator == null) {
				return firstValue + fraction * deltaValue;
			} else {
				return ((Number) mEvaluator.evaluate(fraction, firstValue,
						lastValue)).floatValue();
			}
		}
		if (fraction <= 0f) {
			final FloatKeyframe prevKeyframe = (FloatKeyframe) mKeyframes
					.get(0);
			final FloatKeyframe nextKeyframe = (FloatKeyframe) mKeyframes
					.get(1);
			float prevValue = prevKeyframe.getFloatValue();
			float nextValue = nextKeyframe.getFloatValue();
			float prevFraction = prevKeyframe.getFraction();
			float nextFraction = nextKeyframe.getFraction();
			final/* Time */Interpolator interpolator = nextKeyframe
					.getInterpolator();
			if (interpolator != null) {
				fraction = interpolator.getInterpolation(fraction);
			}
			float intervalFraction = (fraction - prevFraction)
					/ (nextFraction - prevFraction);
			return mEvaluator == null ? prevValue + intervalFraction
					* (nextValue - prevValue) : ((Number) mEvaluator.evaluate(
					intervalFraction, prevValue, nextValue)).floatValue();
		} else if (fraction >= 1f) {
			final FloatKeyframe prevKeyframe = (FloatKeyframe) mKeyframes
					.get(mNumKeyframes - 2);
			final FloatKeyframe nextKeyframe = (FloatKeyframe) mKeyframes
					.get(mNumKeyframes - 1);
			float prevValue = prevKeyframe.getFloatValue();
			float nextValue = nextKeyframe.getFloatValue();
			float prevFraction = prevKeyframe.getFraction();
			float nextFraction = nextKeyframe.getFraction();
			final/* Time */Interpolator interpolator = nextKeyframe
					.getInterpolator();
			if (interpolator != null) {
				fraction = interpolator.getInterpolation(fraction);
			}
			float intervalFraction = (fraction - prevFraction)
					/ (nextFraction - prevFraction);
			return mEvaluator == null ? prevValue + intervalFraction
					* (nextValue - prevValue) : ((Number) mEvaluator.evaluate(
					intervalFraction, prevValue, nextValue)).floatValue();
		}
		FloatKeyframe prevKeyframe = (FloatKeyframe) mKeyframes.get(0);
		for (int i = 1; i < mNumKeyframes; ++i) {
			FloatKeyframe nextKeyframe = (FloatKeyframe) mKeyframes.get(i);
			if (fraction < nextKeyframe.getFraction()) {
				final/* Time */Interpolator interpolator = nextKeyframe
						.getInterpolator();
				if (interpolator != null) {
					fraction = interpolator.getInterpolation(fraction);
				}
				float intervalFraction = (fraction - prevKeyframe.getFraction())
						/ (nextKeyframe.getFraction() - prevKeyframe
								.getFraction());
				float prevValue = prevKeyframe.getFloatValue();
				float nextValue = nextKeyframe.getFloatValue();
				return mEvaluator == null ? prevValue + intervalFraction
						* (nextValue - prevValue) : ((Number) mEvaluator
						.evaluate(intervalFraction, prevValue, nextValue))
						.floatValue();
			}
			prevKeyframe = nextKeyframe;
		}
		// shouldn't get here
		return ((Number) mKeyframes.get(mNumKeyframes - 1).getValue())
				.floatValue();
	}

}
