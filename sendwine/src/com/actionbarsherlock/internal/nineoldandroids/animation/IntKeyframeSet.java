/**   
* @Title: IntKeyframeSet.java 
* @Package com.actionbarsherlock.internal.nineoldandroids.animation 
* @Description: 
*			声明:
*			1)掌控校园是本人的大学的创业作品,也是本人的毕业设计
*			2)本程序尚未进行开放源代码,所以禁止组织或者是个人泄露源码,否则将会追究其刑事责任
*			3)编写本软件,我需要感谢彭老师以及其他鼓励和支持我的同学以及朋友
*			4)本程序的最终所有权属于本人	
* @author  张雷 794857063@qq.com
* @date 2013-11-14 19:45:03 
* @version V1.0   
*/

package com.actionbarsherlock.internal.nineoldandroids.animation;

import java.util.ArrayList;
import android.view.animation.Interpolator;

import com.actionbarsherlock.internal.nineoldandroids.animation.Keyframe.IntKeyframe;

// TODO: Auto-generated Javadoc
/**
 * This class holds a collection of IntKeyframe objects and is called by
 * ValueAnimator to calculate values between those keyframes for a given
 * animation. The class internal to the animation package because it is an
 * implementation detail of how Keyframes are stored and used.
 * 
 * <p>
 * This type-specific subclass of KeyframeSet, along with the other
 * type-specific subclass for float, exists to speed up the getValue() method
 * when there is no custom TypeEvaluator set for the animation, so that values
 * can be calculated without autoboxing to the Object equivalents of these
 * primitive types.
 * </p>
 */
@SuppressWarnings("unchecked")
class IntKeyframeSet extends KeyframeSet {
	
	/** The first value. */
	private int firstValue;
	
	/** The last value. */
	private int lastValue;
	
	/** The delta value. */
	private int deltaValue;
	
	/** The first time. */
	private boolean firstTime = true;

	/**
	 * Instantiates a new int keyframe set.
	 * 
	 * @param keyframes
	 *            the keyframes
	 */
	public IntKeyframeSet(IntKeyframe... keyframes) {
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
		return getIntValue(fraction);
	}

	/** 
	* <p>Title: clone</p> 
	* <p>Description: </p> 
	* @return 
	* @see com.actionbarsherlock.internal.nineoldandroids.animation.KeyframeSet#clone() 
	*/
	@Override
	public IntKeyframeSet clone() {
		ArrayList<Keyframe> keyframes = mKeyframes;
		int numKeyframes = mKeyframes.size();
		IntKeyframe[] newKeyframes = new IntKeyframe[numKeyframes];
		for (int i = 0; i < numKeyframes; ++i) {
			newKeyframes[i] = (IntKeyframe) keyframes.get(i).clone();
		}
		IntKeyframeSet newSet = new IntKeyframeSet(newKeyframes);
		return newSet;
	}

	/**
	 * Gets the int value.
	 * 
	 * @param fraction
	 *            the fraction
	 * @return the int value
	 */
	public int getIntValue(float fraction) {
		if (mNumKeyframes == 2) {
			if (firstTime) {
				firstTime = false;
				firstValue = ((IntKeyframe) mKeyframes.get(0)).getIntValue();
				lastValue = ((IntKeyframe) mKeyframes.get(1)).getIntValue();
				deltaValue = lastValue - firstValue;
			}
			if (mInterpolator != null) {
				fraction = mInterpolator.getInterpolation(fraction);
			}
			if (mEvaluator == null) {
				return firstValue + (int) (fraction * deltaValue);
			} else {
				return ((Number) mEvaluator.evaluate(fraction, firstValue,
						lastValue)).intValue();
			}
		}
		if (fraction <= 0f) {
			final IntKeyframe prevKeyframe = (IntKeyframe) mKeyframes.get(0);
			final IntKeyframe nextKeyframe = (IntKeyframe) mKeyframes.get(1);
			int prevValue = prevKeyframe.getIntValue();
			int nextValue = nextKeyframe.getIntValue();
			float prevFraction = prevKeyframe.getFraction();
			float nextFraction = nextKeyframe.getFraction();
			final/* Time */Interpolator interpolator = nextKeyframe
					.getInterpolator();
			if (interpolator != null) {
				fraction = interpolator.getInterpolation(fraction);
			}
			float intervalFraction = (fraction - prevFraction)
					/ (nextFraction - prevFraction);
			return mEvaluator == null ? prevValue
					+ (int) (intervalFraction * (nextValue - prevValue))
					: ((Number) mEvaluator.evaluate(intervalFraction,
							prevValue, nextValue)).intValue();
		} else if (fraction >= 1f) {
			final IntKeyframe prevKeyframe = (IntKeyframe) mKeyframes
					.get(mNumKeyframes - 2);
			final IntKeyframe nextKeyframe = (IntKeyframe) mKeyframes
					.get(mNumKeyframes - 1);
			int prevValue = prevKeyframe.getIntValue();
			int nextValue = nextKeyframe.getIntValue();
			float prevFraction = prevKeyframe.getFraction();
			float nextFraction = nextKeyframe.getFraction();
			final/* Time */Interpolator interpolator = nextKeyframe
					.getInterpolator();
			if (interpolator != null) {
				fraction = interpolator.getInterpolation(fraction);
			}
			float intervalFraction = (fraction - prevFraction)
					/ (nextFraction - prevFraction);
			return mEvaluator == null ? prevValue
					+ (int) (intervalFraction * (nextValue - prevValue))
					: ((Number) mEvaluator.evaluate(intervalFraction,
							prevValue, nextValue)).intValue();
		}
		IntKeyframe prevKeyframe = (IntKeyframe) mKeyframes.get(0);
		for (int i = 1; i < mNumKeyframes; ++i) {
			IntKeyframe nextKeyframe = (IntKeyframe) mKeyframes.get(i);
			if (fraction < nextKeyframe.getFraction()) {
				final/* Time */Interpolator interpolator = nextKeyframe
						.getInterpolator();
				if (interpolator != null) {
					fraction = interpolator.getInterpolation(fraction);
				}
				float intervalFraction = (fraction - prevKeyframe.getFraction())
						/ (nextKeyframe.getFraction() - prevKeyframe
								.getFraction());
				int prevValue = prevKeyframe.getIntValue();
				int nextValue = nextKeyframe.getIntValue();
				return mEvaluator == null ? prevValue
						+ (int) (intervalFraction * (nextValue - prevValue))
						: ((Number) mEvaluator.evaluate(intervalFraction,
								prevValue, nextValue)).intValue();
			}
			prevKeyframe = nextKeyframe;
		}
		// shouldn't get here
		return ((Number) mKeyframes.get(mNumKeyframes - 1).getValue())
				.intValue();
	}

}
