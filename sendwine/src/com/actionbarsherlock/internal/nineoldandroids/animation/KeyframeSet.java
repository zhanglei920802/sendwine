/**   
* @Title: KeyframeSet.java 
* @Package com.actionbarsherlock.internal.nineoldandroids.animation 
* @Description: 
*			声明:
*			1)掌控校园是本人的大学的创业作品,也是本人的毕业设计
*			2)本程序尚未进行开放源代码,所以禁止组织或者是个人泄露源码,否则将会追究其刑事责任
*			3)编写本软件,我需要感谢彭老师以及其他鼓励和支持我的同学以及朋友
*			4)本程序的最终所有权属于本人	
* @author  张雷 794857063@qq.com
* @date 2013-11-14 19:34:17 
* @version V1.0   
*/

package com.actionbarsherlock.internal.nineoldandroids.animation;

import java.util.ArrayList;
import java.util.Arrays;
import android.view.animation.Interpolator;

import com.actionbarsherlock.internal.nineoldandroids.animation.Keyframe.FloatKeyframe;
import com.actionbarsherlock.internal.nineoldandroids.animation.Keyframe.IntKeyframe;
import com.actionbarsherlock.internal.nineoldandroids.animation.Keyframe.ObjectKeyframe;

// TODO: Auto-generated Javadoc
/**
 * This class holds a collection of Keyframe objects and is called by
 * ValueAnimator to calculate values between those keyframes for a given
 * animation. The class internal to the animation package because it is an
 * implementation detail of how Keyframes are stored and used.
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
class KeyframeSet {

	/** The m num keyframes. */
	int mNumKeyframes;

	/** The m first keyframe. */
	Keyframe mFirstKeyframe;
	
	/** The m last keyframe. */
	Keyframe mLastKeyframe;
	/* Time *//** The m interpolator. */
	Interpolator mInterpolator; // only used in the 2-keyframe case
	
	/** The m keyframes. */
	ArrayList<Keyframe> mKeyframes; // only used when there are not 2 keyframes
	
	/** The m evaluator. */
	TypeEvaluator mEvaluator;

	/**
	 * Instantiates a new keyframe set.
	 * 
	 * @param keyframes
	 *            the keyframes
	 */
	public KeyframeSet(Keyframe... keyframes) {
		mNumKeyframes = keyframes.length;
		mKeyframes = new ArrayList<Keyframe>();
		mKeyframes.addAll(Arrays.asList(keyframes));
		mFirstKeyframe = mKeyframes.get(0);
		mLastKeyframe = mKeyframes.get(mNumKeyframes - 1);
		mInterpolator = mLastKeyframe.getInterpolator();
	}

	/**
	 * Of int.
	 * 
	 * @param values
	 *            the values
	 * @return the keyframe set
	 */
	public static KeyframeSet ofInt(int... values) {
		int numKeyframes = values.length;
		IntKeyframe keyframes[] = new IntKeyframe[Math.max(numKeyframes, 2)];
		if (numKeyframes == 1) {
			keyframes[0] = (IntKeyframe) Keyframe.ofInt(0f);
			keyframes[1] = (IntKeyframe) Keyframe.ofInt(1f, values[0]);
		} else {
			keyframes[0] = (IntKeyframe) Keyframe.ofInt(0f, values[0]);
			for (int i = 1; i < numKeyframes; ++i) {
				keyframes[i] = (IntKeyframe) Keyframe.ofInt((float) i
						/ (numKeyframes - 1), values[i]);
			}
		}
		return new IntKeyframeSet(keyframes);
	}

	/**
	 * Of float.
	 * 
	 * @param values
	 *            the values
	 * @return the keyframe set
	 */
	public static KeyframeSet ofFloat(float... values) {
		int numKeyframes = values.length;
		FloatKeyframe keyframes[] = new FloatKeyframe[Math.max(numKeyframes, 2)];
		if (numKeyframes == 1) {
			keyframes[0] = (FloatKeyframe) Keyframe.ofFloat(0f);
			keyframes[1] = (FloatKeyframe) Keyframe.ofFloat(1f, values[0]);
		} else {
			keyframes[0] = (FloatKeyframe) Keyframe.ofFloat(0f, values[0]);
			for (int i = 1; i < numKeyframes; ++i) {
				keyframes[i] = (FloatKeyframe) Keyframe.ofFloat((float) i
						/ (numKeyframes - 1), values[i]);
			}
		}
		return new FloatKeyframeSet(keyframes);
	}

	/**
	 * Of keyframe.
	 * 
	 * @param keyframes
	 *            the keyframes
	 * @return the keyframe set
	 */
	public static KeyframeSet ofKeyframe(Keyframe... keyframes) {
		// if all keyframes of same primitive type, create the appropriate
		// KeyframeSet
		int numKeyframes = keyframes.length;
		boolean hasFloat = false;
		boolean hasInt = false;
		boolean hasOther = false;
		for (int i = 0; i < numKeyframes; ++i) {
			if (keyframes[i] instanceof FloatKeyframe) {
				hasFloat = true;
			} else if (keyframes[i] instanceof IntKeyframe) {
				hasInt = true;
			} else {
				hasOther = true;
			}
		}
		if (hasFloat && !hasInt && !hasOther) {
			FloatKeyframe floatKeyframes[] = new FloatKeyframe[numKeyframes];
			for (int i = 0; i < numKeyframes; ++i) {
				floatKeyframes[i] = (FloatKeyframe) keyframes[i];
			}
			return new FloatKeyframeSet(floatKeyframes);
		} else if (hasInt && !hasFloat && !hasOther) {
			IntKeyframe intKeyframes[] = new IntKeyframe[numKeyframes];
			for (int i = 0; i < numKeyframes; ++i) {
				intKeyframes[i] = (IntKeyframe) keyframes[i];
			}
			return new IntKeyframeSet(intKeyframes);
		} else {
			return new KeyframeSet(keyframes);
		}
	}

	/**
	 * Of object.
	 * 
	 * @param values
	 *            the values
	 * @return the keyframe set
	 */
	public static KeyframeSet ofObject(Object... values) {
		int numKeyframes = values.length;
		ObjectKeyframe keyframes[] = new ObjectKeyframe[Math.max(numKeyframes,
				2)];
		if (numKeyframes == 1) {
			keyframes[0] = (ObjectKeyframe) Keyframe.ofObject(0f);
			keyframes[1] = (ObjectKeyframe) Keyframe.ofObject(1f, values[0]);
		} else {
			keyframes[0] = (ObjectKeyframe) Keyframe.ofObject(0f, values[0]);
			for (int i = 1; i < numKeyframes; ++i) {
				keyframes[i] = (ObjectKeyframe) Keyframe.ofObject((float) i
						/ (numKeyframes - 1), values[i]);
			}
		}
		return new KeyframeSet(keyframes);
	}

	/**
	 * Sets the TypeEvaluator to be used when calculating animated values. This
	 * object is required only for KeyframeSets that are not either
	 * IntKeyframeSet or FloatKeyframeSet, both of which assume their own
	 * evaluator to speed up calculations with those primitive types.
	 * 
	 * @param evaluator
	 *            The TypeEvaluator to be used to calculate animated values.
	 */
	public void setEvaluator(TypeEvaluator evaluator) {
		mEvaluator = evaluator;
	}

	/** 
	* <p>Title: clone</p> 
	* <p>Description: </p> 
	* @return 
	* @see java.lang.Object#clone() 
	*/
	@Override
	public KeyframeSet clone() {
		ArrayList<Keyframe> keyframes = mKeyframes;
		int numKeyframes = mKeyframes.size();
		Keyframe[] newKeyframes = new Keyframe[numKeyframes];
		for (int i = 0; i < numKeyframes; ++i) {
			newKeyframes[i] = keyframes.get(i).clone();
		}
		KeyframeSet newSet = new KeyframeSet(newKeyframes);
		return newSet;
	}

	/**
	 * Gets the animated value, given the elapsed fraction of the animation
	 * (interpolated by the animation's interpolator) and the evaluator used to
	 * calculate in-between values. This function maps the input fraction to the
	 * appropriate keyframe interval and a fraction between them and returns the
	 * interpolated value. Note that the input fraction may fall outside the
	 * [0-1] bounds, if the animation's interpolator made that happen (e.g., a
	 * spring interpolation that might send the fraction past 1.0). We handle
	 * this situation by just using the two keyframes at the appropriate end
	 * when the value is outside those bounds.
	 * 
	 * @param fraction
	 *            The elapsed fraction of the animation
	 * @return The animated value.
	 */
	public Object getValue(float fraction) {

		// Special-case optimization for the common case of only two keyframes
		if (mNumKeyframes == 2) {
			if (mInterpolator != null) {
				fraction = mInterpolator.getInterpolation(fraction);
			}
			return mEvaluator.evaluate(fraction, mFirstKeyframe.getValue(),
					mLastKeyframe.getValue());
		}
		if (fraction <= 0f) {
			final Keyframe nextKeyframe = mKeyframes.get(1);
			final/* Time */Interpolator interpolator = nextKeyframe
					.getInterpolator();
			if (interpolator != null) {
				fraction = interpolator.getInterpolation(fraction);
			}
			final float prevFraction = mFirstKeyframe.getFraction();
			float intervalFraction = (fraction - prevFraction)
					/ (nextKeyframe.getFraction() - prevFraction);
			return mEvaluator.evaluate(intervalFraction,
					mFirstKeyframe.getValue(), nextKeyframe.getValue());
		} else if (fraction >= 1f) {
			final Keyframe prevKeyframe = mKeyframes.get(mNumKeyframes - 2);
			final/* Time */Interpolator interpolator = mLastKeyframe
					.getInterpolator();
			if (interpolator != null) {
				fraction = interpolator.getInterpolation(fraction);
			}
			final float prevFraction = prevKeyframe.getFraction();
			float intervalFraction = (fraction - prevFraction)
					/ (mLastKeyframe.getFraction() - prevFraction);
			return mEvaluator.evaluate(intervalFraction,
					prevKeyframe.getValue(), mLastKeyframe.getValue());
		}
		Keyframe prevKeyframe = mFirstKeyframe;
		for (int i = 1; i < mNumKeyframes; ++i) {
			Keyframe nextKeyframe = mKeyframes.get(i);
			if (fraction < nextKeyframe.getFraction()) {
				final/* Time */Interpolator interpolator = nextKeyframe
						.getInterpolator();
				if (interpolator != null) {
					fraction = interpolator.getInterpolation(fraction);
				}
				final float prevFraction = prevKeyframe.getFraction();
				float intervalFraction = (fraction - prevFraction)
						/ (nextKeyframe.getFraction() - prevFraction);
				return mEvaluator.evaluate(intervalFraction,
						prevKeyframe.getValue(), nextKeyframe.getValue());
			}
			prevKeyframe = nextKeyframe;
		}
		// shouldn't reach here
		return mLastKeyframe.getValue();
	}

	/** 
	* <p>Title: toString</p> 
	* <p>Description: </p> 
	* @return 
	* @see java.lang.Object#toString() 
	*/
	@Override
	public String toString() {
		String returnVal = " ";
		for (int i = 0; i < mNumKeyframes; ++i) {
			returnVal += mKeyframes.get(i).getValue() + "  ";
		}
		return returnVal;
	}
}
