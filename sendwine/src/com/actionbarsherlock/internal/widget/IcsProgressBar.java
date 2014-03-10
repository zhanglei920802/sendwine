/**   
* @Title: IcsProgressBar.java 
* @Package com.actionbarsherlock.internal.widget 
* @Description: 
*			声明:
*			1)掌控校园是本人的大学的创业作品,也是本人的毕业设计
*			2)本程序尚未进行开放源代码,所以禁止组织或者是个人泄露源码,否则将会追究其刑事责任
*			3)编写本软件,我需要感谢彭老师以及其他鼓励和支持我的同学以及朋友
*			4)本程序的最终所有权属于本人	
* @author  张雷 794857063@qq.com
* @date 2013-11-14 19:34:42 
* @version V1.0   
*/

package com.actionbarsherlock.internal.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.Shader;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ClipDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.graphics.drawable.shapes.Shape;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewDebug;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.Transformation;
import android.widget.RemoteViews.RemoteView;

// TODO: Auto-generated Javadoc
/**
 * <p>
 * Visual indicator of progress in some operation. Displays a bar to the user
 * representing how far the operation has progressed; the application can change
 * the amount of progress (modifying the length of the bar) as it moves forward.
 * There is also a secondary progress displayable on a progress bar which is
 * useful for displaying intermediate progress, such as the buffer level during
 * a streaming playback progress bar.
 * </p>
 * 
 * <p>
 * A progress bar can also be made indeterminate. In indeterminate mode, the
 * progress bar shows a cyclic animation without an indication of progress. This
 * mode is used by applications when the length of the task is unknown. The
 * indeterminate progress bar can be either a spinning wheel or a horizontal
 * bar.
 * </p>
 * 
 * <p>
 * The following code example shows how a progress bar can be used from a worker
 * thread to update the user interface to notify the user of progress:
 * </p>
 * 
 * <pre>
 * public class MyActivity extends Activity {
 * 	private static final int PROGRESS = 0x1;
 * 
 * 	private ProgressBar mProgress;
 * 	private int mProgressStatus = 0;
 * 
 * 	private Handler mHandler = new Handler();
 * 
 * 	protected void onCreate(Bundle icicle) {
 * 		super.onCreate(icicle);
 * 
 * 		setContentView(R.layout.progressbar_activity);
 * 
 * 		mProgress = (ProgressBar) findViewById(R.id.progress_bar);
 * 
 * 		// Start lengthy operation in a background thread
 * 		new Thread(new Runnable() {
 * 			public void run() {
 * 				while (mProgressStatus &lt; 100) {
 * 					mProgressStatus = doWork();
 * 
 * 					// Update the progress bar
 * 					mHandler.post(new Runnable() {
 * 						public void run() {
 * 							mProgress.setProgress(mProgressStatus);
 * 						}
 * 					});
 * 				}
 * 			}
 * 		}).start();
 * 	}
 * }
 * </pre>
 * 
 * <p>
 * To add a progress bar to a layout file, you can use the
 * {@code &lt;ProgressBar&gt;} element. By default, the progress bar is a
 * spinning wheel (an indeterminate indicator). To change to a horizontal
 * progress bar, apply the {@link android.R.style#Widget_ProgressBar_Horizontal
 * Widget.ProgressBar.Horizontal} style, like so:
 * </p>
 * 
 * <pre>
 * &lt;ProgressBar
 *     style="@android:style/Widget.ProgressBar.Horizontal"
 *     ... /&gt;
 * </pre>
 * 
 * <p>
 * If you will use the progress bar to show real progress, you must use the
 * horizontal bar. You can then increment the progress with
 * {@link #incrementProgressBy incrementProgressBy()} or {@link #setProgress
 * setProgress()}. By default, the progress bar is full when it reaches 100. If
 * necessary, you can adjust the maximum value (the value for a full bar) using
 * the {@link android.R.styleable#ProgressBar_max android:max} attribute. Other
 * attributes available are listed below.
 * </p>
 * 
 * <p>
 * Another common style to apply to the progress bar is
 * {@link android.R.style#Widget_ProgressBar_Small Widget.ProgressBar.Small},
 * which shows a smaller version of the spinning wheel&mdash;useful when waiting
 * for content to load. For example, you can insert this kind of progress bar
 * into your default layout for a view that will be populated by some content
 * fetched from the Internet&mdash;the spinning wheel appears immediately and
 * when your application receives the content, it replaces the progress bar with
 * the loaded content. For example:
 * </p>
 * 
 * <pre>
 * &lt;LinearLayout
 *     android:orientation="horizontal"
 *     ... &gt;
 *     &lt;ProgressBar
 *         android:layout_width="wrap_content"
 *         android:layout_height="wrap_content"
 *         style="@android:style/Widget.ProgressBar.Small"
 *         android:layout_marginRight="5dp" /&gt;
 *     &lt;TextView
 *         android:layout_width="wrap_content"
 *         android:layout_height="wrap_content"
 *         android:text="@string/loading" /&gt;
 * &lt;/LinearLayout&gt;
 * </pre>
 * 
 * <p>
 * Other progress bar styles provided by the system include:
 * </p>
 * <ul>
 * <li>{@link android.R.style#Widget_ProgressBar_Horizontal
 * Widget.ProgressBar.Horizontal}</li>
 * <li>{@link android.R.style#Widget_ProgressBar_Small Widget.ProgressBar.Small}
 * </li>
 * <li>{@link android.R.style#Widget_ProgressBar_Large Widget.ProgressBar.Large}
 * </li>
 * <li>{@link android.R.style#Widget_ProgressBar_Inverse
 * Widget.ProgressBar.Inverse}</li>
 * <li>{@link android.R.style#Widget_ProgressBar_Small_Inverse
 * Widget.ProgressBar.Small.Inverse}</li>
 * <li>{@link android.R.style#Widget_ProgressBar_Large_Inverse
 * Widget.ProgressBar.Large.Inverse}</li>
 * </ul>
 * <p>
 * The "inverse" styles provide an inverse color scheme for the spinner, which
 * may be necessary if your application uses a light colored theme (a white
 * background).
 * </p>
 * 
 * <p>
 * <strong>XML attributes</b></strong>
 * <p>
 * See {@link android.R.styleable#ProgressBar ProgressBar Attributes},
 * {@link android.R.styleable#View View Attributes}
 * </p>
 * 
 * @attr ref android.R.styleable#ProgressBar_animationResolution
 * @attr ref android.R.styleable#ProgressBar_indeterminate
 * @attr ref android.R.styleable#ProgressBar_indeterminateBehavior
 * @attr ref android.R.styleable#ProgressBar_indeterminateDrawable
 * @attr ref android.R.styleable#ProgressBar_indeterminateDuration
 * @attr ref android.R.styleable#ProgressBar_indeterminateOnly
 * @attr ref android.R.styleable#ProgressBar_interpolator
 * @attr ref android.R.styleable#ProgressBar_max
 * @attr ref android.R.styleable#ProgressBar_maxHeight
 * @attr ref android.R.styleable#ProgressBar_maxWidth
 * @attr ref android.R.styleable#ProgressBar_minHeight
 * @attr ref android.R.styleable#ProgressBar_minWidth
 * @attr ref android.R.styleable#ProgressBar_progress
 * @attr ref android.R.styleable#ProgressBar_progressDrawable
 * @attr ref android.R.styleable#ProgressBar_secondaryProgress
 */
@RemoteView
public class IcsProgressBar extends View {
	
	/** The Constant IS_HONEYCOMB. */
	private static final boolean IS_HONEYCOMB = Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB;
	
	/** The Constant MAX_LEVEL. */
	private static final int MAX_LEVEL = 10000;
	
	/** The Constant ANIMATION_RESOLUTION. */
	private static final int ANIMATION_RESOLUTION = 200;
	
	/** The Constant TIMEOUT_SEND_ACCESSIBILITY_EVENT. */
	private static final int TIMEOUT_SEND_ACCESSIBILITY_EVENT = 200;

	/** The Constant ProgressBar. */
	private static final int[] ProgressBar = new int[] {
			android.R.attr.maxWidth, android.R.attr.maxHeight,
			android.R.attr.max, android.R.attr.progress,
			android.R.attr.secondaryProgress, android.R.attr.indeterminate,
			android.R.attr.indeterminateOnly,
			android.R.attr.indeterminateDrawable,
			android.R.attr.progressDrawable,
			android.R.attr.indeterminateDuration,
			android.R.attr.indeterminateBehavior, android.R.attr.minWidth,
			android.R.attr.minHeight, android.R.attr.interpolator,
			android.R.attr.animationResolution, };
	
	/** The Constant ProgressBar_maxWidth. */
	private static final int ProgressBar_maxWidth = 0;
	
	/** The Constant ProgressBar_maxHeight. */
	private static final int ProgressBar_maxHeight = 1;
	
	/** The Constant ProgressBar_max. */
	private static final int ProgressBar_max = 2;
	
	/** The Constant ProgressBar_progress. */
	private static final int ProgressBar_progress = 3;
	
	/** The Constant ProgressBar_secondaryProgress. */
	private static final int ProgressBar_secondaryProgress = 4;
	
	/** The Constant ProgressBar_indeterminate. */
	private static final int ProgressBar_indeterminate = 5;
	
	/** The Constant ProgressBar_indeterminateOnly. */
	private static final int ProgressBar_indeterminateOnly = 6;
	
	/** The Constant ProgressBar_indeterminateDrawable. */
	private static final int ProgressBar_indeterminateDrawable = 7;
	
	/** The Constant ProgressBar_progressDrawable. */
	private static final int ProgressBar_progressDrawable = 8;
	
	/** The Constant ProgressBar_indeterminateDuration. */
	private static final int ProgressBar_indeterminateDuration = 9;
	
	/** The Constant ProgressBar_indeterminateBehavior. */
	private static final int ProgressBar_indeterminateBehavior = 10;
	
	/** The Constant ProgressBar_minWidth. */
	private static final int ProgressBar_minWidth = 11;
	
	/** The Constant ProgressBar_minHeight. */
	private static final int ProgressBar_minHeight = 12;
	
	/** The Constant ProgressBar_interpolator. */
	private static final int ProgressBar_interpolator = 13;
	
	/** The Constant ProgressBar_animationResolution. */
	private static final int ProgressBar_animationResolution = 14;

	/** The m min width. */
	int mMinWidth;
	
	/** The m max width. */
	int mMaxWidth;
	
	/** The m min height. */
	int mMinHeight;
	
	/** The m max height. */
	int mMaxHeight;

	/** The m progress. */
	private int mProgress;
	
	/** The m secondary progress. */
	private int mSecondaryProgress;
	
	/** The m max. */
	private int mMax;

	/** The m behavior. */
	private int mBehavior;
	
	/** The m duration. */
	private int mDuration;
	
	/** The m indeterminate. */
	private boolean mIndeterminate;
	
	/** The m only indeterminate. */
	private boolean mOnlyIndeterminate;
	
	/** The m transformation. */
	private Transformation mTransformation;
	
	/** The m animation. */
	private AlphaAnimation mAnimation;
	
	/** The m indeterminate drawable. */
	private Drawable mIndeterminateDrawable;
	
	/** The m indeterminate real left. */
	private int mIndeterminateRealLeft;
	
	/** The m indeterminate real top. */
	private int mIndeterminateRealTop;
	
	/** The m progress drawable. */
	private Drawable mProgressDrawable;
	
	/** The m current drawable. */
	private Drawable mCurrentDrawable;
	
	/** The m sample tile. */
	Bitmap mSampleTile;
	
	/** The m no invalidate. */
	private boolean mNoInvalidate;
	
	/** The m interpolator. */
	private Interpolator mInterpolator;
	
	/** The m refresh progress runnable. */
	private RefreshProgressRunnable mRefreshProgressRunnable;
	
	/** The m ui thread id. */
	private long mUiThreadId;
	
	/** The m should start animation drawable. */
	private boolean mShouldStartAnimationDrawable;
	
	/** The m last draw time. */
	private long mLastDrawTime;

	/** The m in drawing. */
	private boolean mInDrawing;

	/** The m animation resolution. */
	private int mAnimationResolution;

	/** The m accessibility manager. */
	private AccessibilityManager mAccessibilityManager;
	
	/** The m accessibility event sender. */
	private AccessibilityEventSender mAccessibilityEventSender;

	/**
	 * Create a new progress bar with range 0...100 and initial progress of 0.
	 * 
	 * @param context
	 *            the application environment
	 */
	public IcsProgressBar(Context context) {
		this(context, null);
	}

	/**
	 * Instantiates a new ics progress bar.
	 * 
	 * @param context
	 *            the context
	 * @param attrs
	 *            the attrs
	 */
	public IcsProgressBar(Context context, AttributeSet attrs) {
		this(context, attrs, android.R.attr.progressBarStyle);
	}

	/**
	 * Instantiates a new ics progress bar.
	 * 
	 * @param context
	 *            the context
	 * @param attrs
	 *            the attrs
	 * @param defStyle
	 *            the def style
	 */
	public IcsProgressBar(Context context, AttributeSet attrs, int defStyle) {
		this(context, attrs, defStyle, 0);
	}

	/**
	 * Instantiates a new ics progress bar.
	 * 
	 * @param context
	 *            the context
	 * @param attrs
	 *            the attrs
	 * @param defStyle
	 *            the def style
	 * @param styleRes
	 *            the style res
	 * @hide
	 */
	public IcsProgressBar(Context context, AttributeSet attrs, int defStyle,
			int styleRes) {
		super(context, attrs, defStyle);
		mUiThreadId = Thread.currentThread().getId();
		initProgressBar();

		TypedArray a = context.obtainStyledAttributes(attrs, /* R.styleable. */
				ProgressBar, defStyle, styleRes);

		mNoInvalidate = true;

		Drawable drawable = a
				.getDrawable(/* R.styleable. */ProgressBar_progressDrawable);
		if (drawable != null) {
			drawable = tileify(drawable, false);
			// Calling this method can set mMaxHeight, make sure the
			// corresponding
			// XML attribute for mMaxHeight is read after calling this method
			setProgressDrawable(drawable);
		}

		mDuration = a.getInt(
		/* R.styleable. */ProgressBar_indeterminateDuration, mDuration);

		mMinWidth = a.getDimensionPixelSize(
		/* R.styleable. */ProgressBar_minWidth, mMinWidth);
		mMaxWidth = a.getDimensionPixelSize(
		/* R.styleable. */ProgressBar_maxWidth, mMaxWidth);
		mMinHeight = a.getDimensionPixelSize(
		/* R.styleable. */ProgressBar_minHeight, mMinHeight);
		mMaxHeight = a.getDimensionPixelSize(
		/* R.styleable. */ProgressBar_maxHeight, mMaxHeight);

		mBehavior = a.getInt(
		/* R.styleable. */ProgressBar_indeterminateBehavior, mBehavior);

		final int resID = a.getResourceId(
		/* com.android.internal.R.styleable. */ProgressBar_interpolator,
				android.R.anim.linear_interpolator); // default to linear
														// interpolator
		if (resID > 0) {
			setInterpolator(context, resID);
		}

		setMax(a.getInt(/* R.styleable. */ProgressBar_max, mMax));

		setProgress(a.getInt(/* R.styleable. */ProgressBar_progress, mProgress));

		setSecondaryProgress(a.getInt(
		/* R.styleable. */ProgressBar_secondaryProgress, mSecondaryProgress));

		drawable = a
				.getDrawable(/* R.styleable. */ProgressBar_indeterminateDrawable);
		if (drawable != null) {
			drawable = tileifyIndeterminate(drawable);
			setIndeterminateDrawable(drawable);
		}

		mOnlyIndeterminate = a.getBoolean(
		/* R.styleable. */ProgressBar_indeterminateOnly, mOnlyIndeterminate);

		mNoInvalidate = false;

		setIndeterminate(mOnlyIndeterminate || a.getBoolean(
		/* R.styleable. */ProgressBar_indeterminate, mIndeterminate));

		mAnimationResolution = a
				.getInteger(
				/* R.styleable. */ProgressBar_animationResolution,
						ANIMATION_RESOLUTION);

		a.recycle();

		mAccessibilityManager = (AccessibilityManager) context
				.getSystemService(Context.ACCESSIBILITY_SERVICE);
	}

	/**
	 * Converts a drawable to a tiled version of itself. It will recursively
	 * traverse layer and state list drawables.
	 * 
	 * @param drawable
	 *            the drawable
	 * @param clip
	 *            the clip
	 * @return the drawable
	 */
	private Drawable tileify(Drawable drawable, boolean clip) {

		if (drawable instanceof LayerDrawable) {
			LayerDrawable background = (LayerDrawable) drawable;
			final int N = background.getNumberOfLayers();
			Drawable[] outDrawables = new Drawable[N];

			for (int i = 0; i < N; i++) {
				int id = background.getId(i);
				outDrawables[i] = tileify(
						background.getDrawable(i),
						(id == android.R.id.progress || id == android.R.id.secondaryProgress));
			}

			LayerDrawable newBg = new LayerDrawable(outDrawables);

			for (int i = 0; i < N; i++) {
				newBg.setId(i, background.getId(i));
			}

			return newBg;

		}/*
		 * else if (drawable instanceof StateListDrawable) { StateListDrawable
		 * in = (StateListDrawable) drawable; StateListDrawable out = new
		 * StateListDrawable(); int numStates = in.getStateCount(); for (int i =
		 * 0; i < numStates; i++) { out.addState(in.getStateSet(i),
		 * tileify(in.getStateDrawable(i), clip)); } return out;
		 * 
		 * }
		 */else if (drawable instanceof BitmapDrawable) {
			final Bitmap tileBitmap = ((BitmapDrawable) drawable).getBitmap();
			if (mSampleTile == null) {
				mSampleTile = tileBitmap;
			}

			final ShapeDrawable shapeDrawable = new ShapeDrawable(
					getDrawableShape());

			final BitmapShader bitmapShader = new BitmapShader(tileBitmap,
					Shader.TileMode.REPEAT, Shader.TileMode.CLAMP);
			shapeDrawable.getPaint().setShader(bitmapShader);

			return (clip) ? new ClipDrawable(shapeDrawable, Gravity.LEFT,
					ClipDrawable.HORIZONTAL) : shapeDrawable;
		}

		return drawable;
	}

	/**
	 * Gets the drawable shape.
	 * 
	 * @return the drawable shape
	 */
	Shape getDrawableShape() {
		final float[] roundedCorners = new float[] { 5, 5, 5, 5, 5, 5, 5, 5 };
		return new RoundRectShape(roundedCorners, null, null);
	}

	/**
	 * Convert a AnimationDrawable for use as a barberpole animation. Each frame
	 * of the animation is wrapped in a ClipDrawable and given a tiling
	 * BitmapShader.
	 * 
	 * @param drawable
	 *            the drawable
	 * @return the drawable
	 */
	private Drawable tileifyIndeterminate(Drawable drawable) {
		if (drawable instanceof AnimationDrawable) {
			AnimationDrawable background = (AnimationDrawable) drawable;
			final int N = background.getNumberOfFrames();
			AnimationDrawable newBg = new AnimationDrawable();
			newBg.setOneShot(background.isOneShot());

			for (int i = 0; i < N; i++) {
				Drawable frame = tileify(background.getFrame(i), true);
				frame.setLevel(10000);
				newBg.addFrame(frame, background.getDuration(i));
			}
			newBg.setLevel(10000);
			drawable = newBg;
		}
		return drawable;
	}

	/**
	 * <p>
	 * Initialize the progress bar's default values:
	 * </p>
	 * <ul>
	 * <li>progress = 0</li>
	 * <li>max = 100</li>
	 * <li>animation duration = 4000 ms</li>
	 * <li>indeterminate = false</li>
	 * <li>behavior = repeat</li>
	 * </ul>
	 * .
	 */
	private void initProgressBar() {
		mMax = 100;
		mProgress = 0;
		mSecondaryProgress = 0;
		mIndeterminate = false;
		mOnlyIndeterminate = false;
		mDuration = 4000;
		mBehavior = Animation.RESTART;
		mMinWidth = 24;
		mMaxWidth = 48;
		mMinHeight = 24;
		mMaxHeight = 48;
	}

	/**
	 * <p>
	 * Indicate whether this progress bar is in indeterminate mode.
	 * </p>
	 * 
	 * @return true if the progress bar is in indeterminate mode
	 */
	@ViewDebug.ExportedProperty(category = "progress")
	public synchronized boolean isIndeterminate() {
		return mIndeterminate;
	}

	/**
	 * <p>
	 * Change the indeterminate mode for this progress bar. In indeterminate
	 * mode, the progress is ignored and the progress bar shows an infinite
	 * animation instead.
	 * </p>
	 * 
	 * If this progress bar's style only supports indeterminate mode (such as
	 * the circular progress bars), then this will be ignored.
	 * 
	 * @param indeterminate
	 *            true to enable the indeterminate mode
	 */
	public synchronized void setIndeterminate(boolean indeterminate) {
		if ((!mOnlyIndeterminate || !mIndeterminate)
				&& indeterminate != mIndeterminate) {
			mIndeterminate = indeterminate;

			if (indeterminate) {
				// swap between indeterminate and regular backgrounds
				mCurrentDrawable = mIndeterminateDrawable;
				startAnimation();
			} else {
				mCurrentDrawable = mProgressDrawable;
				stopAnimation();
			}
		}
	}

	/**
	 * <p>
	 * Get the drawable used to draw the progress bar in indeterminate mode.
	 * </p>
	 * 
	 * @return a {@link android.graphics.drawable.Drawable} instance
	 * 
	 * @see #setIndeterminateDrawable(android.graphics.drawable.Drawable)
	 * @see #setIndeterminate(boolean)
	 */
	public Drawable getIndeterminateDrawable() {
		return mIndeterminateDrawable;
	}

	/**
	 * <p>
	 * Define the drawable used to draw the progress bar in indeterminate mode.
	 * </p>
	 * 
	 * @param d
	 *            the new drawable
	 * 
	 * @see #getIndeterminateDrawable()
	 * @see #setIndeterminate(boolean)
	 */
	public void setIndeterminateDrawable(Drawable d) {
		if (d != null) {
			d.setCallback(this);
		}
		mIndeterminateDrawable = d;
		if (mIndeterminate) {
			mCurrentDrawable = d;
			postInvalidate();
		}
	}

	/**
	 * <p>
	 * Get the drawable used to draw the progress bar in progress mode.
	 * </p>
	 * 
	 * @return a {@link android.graphics.drawable.Drawable} instance
	 * 
	 * @see #setProgressDrawable(android.graphics.drawable.Drawable)
	 * @see #setIndeterminate(boolean)
	 */
	public Drawable getProgressDrawable() {
		return mProgressDrawable;
	}

	/**
	 * <p>
	 * Define the drawable used to draw the progress bar in progress mode.
	 * </p>
	 * 
	 * @param d
	 *            the new drawable
	 * 
	 * @see #getProgressDrawable()
	 * @see #setIndeterminate(boolean)
	 */
	public void setProgressDrawable(Drawable d) {
		boolean needUpdate;
		if (mProgressDrawable != null && d != mProgressDrawable) {
			mProgressDrawable.setCallback(null);
			needUpdate = true;
		} else {
			needUpdate = false;
		}

		if (d != null) {
			d.setCallback(this);

			// Make sure the ProgressBar is always tall enough
			int drawableHeight = d.getMinimumHeight();
			if (mMaxHeight < drawableHeight) {
				mMaxHeight = drawableHeight;
				requestLayout();
			}
		}
		mProgressDrawable = d;
		if (!mIndeterminate) {
			mCurrentDrawable = d;
			postInvalidate();
		}

		if (needUpdate) {
			updateDrawableBounds(getWidth(), getHeight());
			updateDrawableState();
			doRefreshProgress(android.R.id.progress, mProgress, false, false);
			doRefreshProgress(android.R.id.secondaryProgress,
					mSecondaryProgress, false, false);
		}
	}

	/**
	 * Gets the current drawable.
	 * 
	 * @return The drawable currently used to draw the progress bar
	 */
	Drawable getCurrentDrawable() {
		return mCurrentDrawable;
	}

	/** 
	* <p>Title: verifyDrawable</p> 
	* <p>Description: </p> 
	* @param who
	* @return 
	* @see android.view.View#verifyDrawable(android.graphics.drawable.Drawable) 
	*/
	@Override
	protected boolean verifyDrawable(Drawable who) {
		return who == mProgressDrawable || who == mIndeterminateDrawable
				|| super.verifyDrawable(who);
	}

	/** 
	* <p>Title: jumpDrawablesToCurrentState</p> 
	* <p>Description: </p>  
	* @see android.view.View#jumpDrawablesToCurrentState() 
	*/
	@Override
	public void jumpDrawablesToCurrentState() {
		super.jumpDrawablesToCurrentState();
		if (mProgressDrawable != null)
			mProgressDrawable.jumpToCurrentState();
		if (mIndeterminateDrawable != null)
			mIndeterminateDrawable.jumpToCurrentState();
	}

	/** 
	* <p>Title: postInvalidate</p> 
	* <p>Description: </p>  
	* @see android.view.View#postInvalidate() 
	*/
	@Override
	public void postInvalidate() {
		if (!mNoInvalidate) {
			super.postInvalidate();
		}
	}

	/**
	 * The Class RefreshProgressRunnable.
	 */
	private class RefreshProgressRunnable implements Runnable {

		/** The m id. */
		private int mId;
		
		/** The m progress. */
		private int mProgress;
		
		/** The m from user. */
		private boolean mFromUser;

		/**
		 * Instantiates a new refresh progress runnable.
		 * 
		 * @param id
		 *            the id
		 * @param progress
		 *            the progress
		 * @param fromUser
		 *            the from user
		 */
		RefreshProgressRunnable(int id, int progress, boolean fromUser) {
			mId = id;
			mProgress = progress;
			mFromUser = fromUser;
		}

		/** 
		* <p>Title: run</p> 
		* <p>Description: </p>  
		* @see java.lang.Runnable#run() 
		*/
		@Override
		public void run() {
			doRefreshProgress(mId, mProgress, mFromUser, true);
			// Put ourselves back in the cache when we are done
			mRefreshProgressRunnable = this;
		}

		/**
		 * Setup.
		 * 
		 * @param id
		 *            the id
		 * @param progress
		 *            the progress
		 * @param fromUser
		 *            the from user
		 */
		public void setup(int id, int progress, boolean fromUser) {
			mId = id;
			mProgress = progress;
			mFromUser = fromUser;
		}

	}

	/**
	 * Do refresh progress.
	 * 
	 * @param id
	 *            the id
	 * @param progress
	 *            the progress
	 * @param fromUser
	 *            the from user
	 * @param callBackToApp
	 *            the call back to app
	 */
	private synchronized void doRefreshProgress(int id, int progress,
			boolean fromUser, boolean callBackToApp) {
		float scale = mMax > 0 ? (float) progress / (float) mMax : 0;
		final Drawable d = mCurrentDrawable;
		if (d != null) {
			Drawable progressDrawable = null;

			if (d instanceof LayerDrawable) {
				progressDrawable = ((LayerDrawable) d)
						.findDrawableByLayerId(id);
			}

			final int level = (int) (scale * MAX_LEVEL);
			(progressDrawable != null ? progressDrawable : d).setLevel(level);
		} else {
			invalidate();
		}

		if (callBackToApp && id == android.R.id.progress) {
			onProgressRefresh(scale, fromUser);
		}
	}

	/**
	 * On progress refresh.
	 * 
	 * @param scale
	 *            the scale
	 * @param fromUser
	 *            the from user
	 */
	void onProgressRefresh(float scale, boolean fromUser) {
		if (mAccessibilityManager.isEnabled()) {
			scheduleAccessibilityEventSender();
		}
	}

	/**
	 * Refresh progress.
	 * 
	 * @param id
	 *            the id
	 * @param progress
	 *            the progress
	 * @param fromUser
	 *            the from user
	 */
	private synchronized void refreshProgress(int id, int progress,
			boolean fromUser) {
		if (mUiThreadId == Thread.currentThread().getId()) {
			doRefreshProgress(id, progress, fromUser, true);
		} else {
			RefreshProgressRunnable r;
			if (mRefreshProgressRunnable != null) {
				// Use cached RefreshProgressRunnable if available
				r = mRefreshProgressRunnable;
				// Uncache it
				mRefreshProgressRunnable = null;
				r.setup(id, progress, fromUser);
			} else {
				// Make a new one
				r = new RefreshProgressRunnable(id, progress, fromUser);
			}
			post(r);
		}
	}

	/**
	 * <p>
	 * Set the current progress to the specified value. Does not do anything if
	 * the progress bar is in indeterminate mode.
	 * </p>
	 * 
	 * @param progress
	 *            the new progress, between 0 and {@link #getMax()}
	 * 
	 * @see #setIndeterminate(boolean)
	 * @see #isIndeterminate()
	 * @see #getProgress()
	 * @see #incrementProgressBy(int)
	 */
	public synchronized void setProgress(int progress) {
		setProgress(progress, false);
	}

	/**
	 * Sets the progress.
	 * 
	 * @param progress
	 *            the progress
	 * @param fromUser
	 *            the from user
	 */
	synchronized void setProgress(int progress, boolean fromUser) {
		if (mIndeterminate) {
			return;
		}

		if (progress < 0) {
			progress = 0;
		}

		if (progress > mMax) {
			progress = mMax;
		}

		if (progress != mProgress) {
			mProgress = progress;
			refreshProgress(android.R.id.progress, mProgress, fromUser);
		}
	}

	/**
	 * <p>
	 * Set the current secondary progress to the specified value. Does not do
	 * anything if the progress bar is in indeterminate mode.
	 * </p>
	 * 
	 * @param secondaryProgress
	 *            the new secondary progress, between 0 and {@link #getMax()}
	 * @see #setIndeterminate(boolean)
	 * @see #isIndeterminate()
	 * @see #getSecondaryProgress()
	 * @see #incrementSecondaryProgressBy(int)
	 */
	public synchronized void setSecondaryProgress(int secondaryProgress) {
		if (mIndeterminate) {
			return;
		}

		if (secondaryProgress < 0) {
			secondaryProgress = 0;
		}

		if (secondaryProgress > mMax) {
			secondaryProgress = mMax;
		}

		if (secondaryProgress != mSecondaryProgress) {
			mSecondaryProgress = secondaryProgress;
			refreshProgress(android.R.id.secondaryProgress, mSecondaryProgress,
					false);
		}
	}

	/**
	 * <p>
	 * Get the progress bar's current level of progress. Return 0 when the
	 * progress bar is in indeterminate mode.
	 * </p>
	 * 
	 * @return the current progress, between 0 and {@link #getMax()}
	 * 
	 * @see #setIndeterminate(boolean)
	 * @see #isIndeterminate()
	 * @see #setProgress(int)
	 * @see #setMax(int)
	 * @see #getMax()
	 */
	@ViewDebug.ExportedProperty(category = "progress")
	public synchronized int getProgress() {
		return mIndeterminate ? 0 : mProgress;
	}

	/**
	 * <p>
	 * Get the progress bar's current level of secondary progress. Return 0 when
	 * the progress bar is in indeterminate mode.
	 * </p>
	 * 
	 * @return the current secondary progress, between 0 and {@link #getMax()}
	 * 
	 * @see #setIndeterminate(boolean)
	 * @see #isIndeterminate()
	 * @see #setSecondaryProgress(int)
	 * @see #setMax(int)
	 * @see #getMax()
	 */
	@ViewDebug.ExportedProperty(category = "progress")
	public synchronized int getSecondaryProgress() {
		return mIndeterminate ? 0 : mSecondaryProgress;
	}

	/**
	 * <p>
	 * Return the upper limit of this progress bar's range.
	 * </p>
	 * 
	 * @return a positive integer
	 * 
	 * @see #setMax(int)
	 * @see #getProgress()
	 * @see #getSecondaryProgress()
	 */
	@ViewDebug.ExportedProperty(category = "progress")
	public synchronized int getMax() {
		return mMax;
	}

	/**
	 * <p>
	 * Set the range of the progress bar to 0...<tt>max</tt>.
	 * </p>
	 * 
	 * @param max
	 *            the upper range of this progress bar
	 * 
	 * @see #getMax()
	 * @see #setProgress(int)
	 * @see #setSecondaryProgress(int)
	 */
	public synchronized void setMax(int max) {
		if (max < 0) {
			max = 0;
		}
		if (max != mMax) {
			mMax = max;
			postInvalidate();

			if (mProgress > max) {
				mProgress = max;
			}
			refreshProgress(android.R.id.progress, mProgress, false);
		}
	}

	/**
	 * <p>
	 * Increase the progress bar's progress by the specified amount.
	 * </p>
	 * 
	 * @param diff
	 *            the amount by which the progress must be increased
	 * 
	 * @see #setProgress(int)
	 */
	public synchronized final void incrementProgressBy(int diff) {
		setProgress(mProgress + diff);
	}

	/**
	 * <p>
	 * Increase the progress bar's secondary progress by the specified amount.
	 * </p>
	 * 
	 * @param diff
	 *            the amount by which the secondary progress must be increased
	 * 
	 * @see #setSecondaryProgress(int)
	 */
	public synchronized final void incrementSecondaryProgressBy(int diff) {
		setSecondaryProgress(mSecondaryProgress + diff);
	}

	/**
	 * <p>
	 * Start the indeterminate progress animation.
	 * </p>
	 */
	void startAnimation() {
		if (getVisibility() != VISIBLE) {
			return;
		}

		if (mIndeterminateDrawable instanceof Animatable) {
			mShouldStartAnimationDrawable = true;
			mAnimation = null;
		} else {
			if (mInterpolator == null) {
				mInterpolator = new LinearInterpolator();
			}

			mTransformation = new Transformation();
			mAnimation = new AlphaAnimation(0.0f, 1.0f);
			mAnimation.setRepeatMode(mBehavior);
			mAnimation.setRepeatCount(Animation.INFINITE);
			mAnimation.setDuration(mDuration);
			mAnimation.setInterpolator(mInterpolator);
			mAnimation.setStartTime(Animation.START_ON_FIRST_FRAME);
		}
		postInvalidate();
	}

	/**
	 * <p>
	 * Stop the indeterminate progress animation.
	 * </p>
	 */
	void stopAnimation() {
		mAnimation = null;
		mTransformation = null;
		if (mIndeterminateDrawable instanceof Animatable) {
			((Animatable) mIndeterminateDrawable).stop();
			mShouldStartAnimationDrawable = false;
		}
		postInvalidate();
	}

	/**
	 * Sets the acceleration curve for the indeterminate animation. The
	 * interpolator is loaded as a resource from the specified context.
	 * 
	 * @param context
	 *            The application environment
	 * @param resID
	 *            The resource identifier of the interpolator to load
	 */
	public void setInterpolator(Context context, int resID) {
		setInterpolator(AnimationUtils.loadInterpolator(context, resID));
	}

	/**
	 * Sets the acceleration curve for the indeterminate animation. Defaults to
	 * a linear interpolation.
	 * 
	 * @param interpolator
	 *            The interpolator which defines the acceleration curve
	 */
	public void setInterpolator(Interpolator interpolator) {
		mInterpolator = interpolator;
	}

	/**
	 * Gets the acceleration curve type for the indeterminate animation.
	 * 
	 * @return the {@link Interpolator} associated to this animation
	 */
	public Interpolator getInterpolator() {
		return mInterpolator;
	}

	/** 
	* <p>Title: setVisibility</p> 
	* <p>Description: </p> 
	* @param v 
	* @see android.view.View#setVisibility(int) 
	*/
	@Override
	public void setVisibility(int v) {
		if (getVisibility() != v) {
			super.setVisibility(v);

			if (mIndeterminate) {
				// let's be nice with the UI thread
				if (v == GONE || v == INVISIBLE) {
					stopAnimation();
				} else {
					startAnimation();
				}
			}
		}
	}

	/** 
	* <p>Title: onVisibilityChanged</p> 
	* <p>Description: </p> 
	* @param changedView
	* @param visibility 
	* @see android.view.View#onVisibilityChanged(android.view.View, int) 
	*/
	@Override
	protected void onVisibilityChanged(View changedView, int visibility) {
		super.onVisibilityChanged(changedView, visibility);

		if (mIndeterminate) {
			// let's be nice with the UI thread
			if (visibility == GONE || visibility == INVISIBLE) {
				stopAnimation();
			} else {
				startAnimation();
			}
		}
	}

	/** 
	* <p>Title: invalidateDrawable</p> 
	* <p>Description: </p> 
	* @param dr 
	* @see android.view.View#invalidateDrawable(android.graphics.drawable.Drawable) 
	*/
	@Override
	public void invalidateDrawable(Drawable dr) {
		if (!mInDrawing) {
			if (verifyDrawable(dr)) {
				final Rect dirty = dr.getBounds();
				final int scrollX = getScrollX() + getPaddingLeft();
				final int scrollY = getScrollY() + getPaddingTop();

				invalidate(dirty.left + scrollX, dirty.top + scrollY,
						dirty.right + scrollX, dirty.bottom + scrollY);
			} else {
				super.invalidateDrawable(dr);
			}
		}
	}

	/**
	 * On size changed.
	 * 
	 * @param w
	 *            the w
	 * @param h
	 *            the h
	 * @param oldw
	 *            the oldw
	 * @param oldh
	 *            the oldh
	 * @hide
	 * @Override public int getResolvedLayoutDirection(Drawable who) { return
	 *           (who == mProgressDrawable || who == mIndeterminateDrawable) ?
	 *           getResolvedLayoutDirection() :
	 *           super.getResolvedLayoutDirection(who); }
	 */

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		updateDrawableBounds(w, h);
	}

	/**
	 * Update drawable bounds.
	 * 
	 * @param w
	 *            the w
	 * @param h
	 *            the h
	 */
	private void updateDrawableBounds(int w, int h) {
		// onDraw will translate the canvas so we draw starting at 0,0
		int right = w - getPaddingRight() - getPaddingLeft();
		int bottom = h - getPaddingBottom() - getPaddingTop();
		int top = 0;
		int left = 0;

		if (mIndeterminateDrawable != null) {
			// Aspect ratio logic does not apply to AnimationDrawables
			if (mOnlyIndeterminate
					&& !(mIndeterminateDrawable instanceof AnimationDrawable)) {
				// Maintain aspect ratio. Certain kinds of animated drawables
				// get very confused otherwise.
				final int intrinsicWidth = mIndeterminateDrawable
						.getIntrinsicWidth();
				final int intrinsicHeight = mIndeterminateDrawable
						.getIntrinsicHeight();
				final float intrinsicAspect = (float) intrinsicWidth
						/ intrinsicHeight;
				final float boundAspect = (float) w / h;
				if (intrinsicAspect != boundAspect) {
					if (boundAspect > intrinsicAspect) {
						// New width is larger. Make it smaller to match height.
						final int width = (int) (h * intrinsicAspect);
						left = (w - width) / 2;
						right = left + width;
					} else {
						// New height is larger. Make it smaller to match width.
						final int height = (int) (w * (1 / intrinsicAspect));
						top = (h - height) / 2;
						bottom = top + height;
					}
				}
			}
			mIndeterminateDrawable.setBounds(0, 0, right - left, bottom - top);
			mIndeterminateRealLeft = left;
			mIndeterminateRealTop = top;
		}

		if (mProgressDrawable != null) {
			mProgressDrawable.setBounds(0, 0, right, bottom);
		}
	}

	/** 
	* <p>Title: onDraw</p> 
	* <p>Description: </p> 
	* @param canvas 
	* @see android.view.View#onDraw(android.graphics.Canvas) 
	*/
	@Override
	protected synchronized void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		Drawable d = mCurrentDrawable;
		if (d != null) {
			// Translate canvas so a indeterminate circular progress bar with
			// padding
			// rotates properly in its animation
			canvas.save();
			canvas.translate(getPaddingLeft() + mIndeterminateRealLeft,
					getPaddingTop() + mIndeterminateRealTop);
			long time = getDrawingTime();
			if (mAnimation != null) {
				mAnimation.getTransformation(time, mTransformation);
				float scale = mTransformation.getAlpha();
				try {
					mInDrawing = true;
					d.setLevel((int) (scale * MAX_LEVEL));
				} finally {
					mInDrawing = false;
				}
				if (SystemClock.uptimeMillis() - mLastDrawTime >= mAnimationResolution) {
					mLastDrawTime = SystemClock.uptimeMillis();
					postInvalidateDelayed(mAnimationResolution);
				}
			}
			d.draw(canvas);
			canvas.restore();
			if (mShouldStartAnimationDrawable && d instanceof Animatable) {
				((Animatable) d).start();
				mShouldStartAnimationDrawable = false;
			}
		}
	}

	/** 
	* <p>Title: onMeasure</p> 
	* <p>Description: </p> 
	* @param widthMeasureSpec
	* @param heightMeasureSpec 
	* @see android.view.View#onMeasure(int, int) 
	*/
	@Override
	protected synchronized void onMeasure(int widthMeasureSpec,
			int heightMeasureSpec) {
		Drawable d = mCurrentDrawable;

		int dw = 0;
		int dh = 0;
		if (d != null) {
			dw = Math
					.max(mMinWidth, Math.min(mMaxWidth, d.getIntrinsicWidth()));
			dh = Math.max(mMinHeight,
					Math.min(mMaxHeight, d.getIntrinsicHeight()));
		}
		updateDrawableState();
		dw += getPaddingLeft() + getPaddingRight();
		dh += getPaddingTop() + getPaddingBottom();

		if (IS_HONEYCOMB) {
			setMeasuredDimension(
					View.resolveSizeAndState(dw, widthMeasureSpec, 0),
					View.resolveSizeAndState(dh, heightMeasureSpec, 0));
		} else {
			setMeasuredDimension(View.resolveSize(dw, widthMeasureSpec),
					View.resolveSize(dh, heightMeasureSpec));
		}
	}

	/** 
	* <p>Title: drawableStateChanged</p> 
	* <p>Description: </p>  
	* @see android.view.View#drawableStateChanged() 
	*/
	@Override
	protected void drawableStateChanged() {
		super.drawableStateChanged();
		updateDrawableState();
	}

	/**
	 * Update drawable state.
	 */
	private void updateDrawableState() {
		int[] state = getDrawableState();

		if (mProgressDrawable != null && mProgressDrawable.isStateful()) {
			mProgressDrawable.setState(state);
		}

		if (mIndeterminateDrawable != null
				&& mIndeterminateDrawable.isStateful()) {
			mIndeterminateDrawable.setState(state);
		}
	}

	/**
	 * The Class SavedState.
	 */
	static class SavedState extends BaseSavedState {
		
		/** The progress. */
		int progress;
		
		/** The secondary progress. */
		int secondaryProgress;

		/**
		 * Constructor called from {@link IcsProgressBar#onSaveInstanceState()}.
		 * 
		 * @param superState
		 *            the super state
		 */
		SavedState(Parcelable superState) {
			super(superState);
		}

		/**
		 * Constructor called from {@link #CREATOR}.
		 * 
		 * @param in
		 *            the in
		 */
		private SavedState(Parcel in) {
			super(in);
			progress = in.readInt();
			secondaryProgress = in.readInt();
		}

		/** 
		* <p>Title: writeToParcel</p> 
		* <p>Description: </p> 
		* @param out
		* @param flags 
		* @see android.view.AbsSavedState#writeToParcel(android.os.Parcel, int) 
		*/
		@Override
		public void writeToParcel(Parcel out, int flags) {
			super.writeToParcel(out, flags);
			out.writeInt(progress);
			out.writeInt(secondaryProgress);
		}

		/** The Constant CREATOR. */
		public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator<SavedState>() {
			@Override
			public SavedState createFromParcel(Parcel in) {
				return new SavedState(in);
			}

			@Override
			public SavedState[] newArray(int size) {
				return new SavedState[size];
			}
		};
	}

	/** 
	* <p>Title: onSaveInstanceState</p> 
	* <p>Description: </p> 
	* @return 
	* @see android.view.View#onSaveInstanceState() 
	*/
	@Override
	public Parcelable onSaveInstanceState() {
		// Force our ancestor class to save its state
		Parcelable superState = super.onSaveInstanceState();
		SavedState ss = new SavedState(superState);

		ss.progress = mProgress;
		ss.secondaryProgress = mSecondaryProgress;

		return ss;
	}

	/** 
	* <p>Title: onRestoreInstanceState</p> 
	* <p>Description: </p> 
	* @param state 
	* @see android.view.View#onRestoreInstanceState(android.os.Parcelable) 
	*/
	@Override
	public void onRestoreInstanceState(Parcelable state) {
		SavedState ss = (SavedState) state;
		super.onRestoreInstanceState(ss.getSuperState());

		setProgress(ss.progress);
		setSecondaryProgress(ss.secondaryProgress);
	}

	/** 
	* <p>Title: onAttachedToWindow</p> 
	* <p>Description: </p>  
	* @see android.view.View#onAttachedToWindow() 
	*/
	@Override
	protected void onAttachedToWindow() {
		super.onAttachedToWindow();
		if (mIndeterminate) {
			startAnimation();
		}
	}

	/** 
	* <p>Title: onDetachedFromWindow</p> 
	* <p>Description: </p>  
	* @see android.view.View#onDetachedFromWindow() 
	*/
	@Override
	protected void onDetachedFromWindow() {
		if (mIndeterminate) {
			stopAnimation();
		}
		if (mRefreshProgressRunnable != null) {
			removeCallbacks(mRefreshProgressRunnable);
		}
		if (mAccessibilityEventSender != null) {
			removeCallbacks(mAccessibilityEventSender);
		}
		// This should come after stopAnimation(), otherwise an invalidate
		// message remains in the
		// queue, which can prevent the entire view hierarchy from being GC'ed
		// during a rotation
		super.onDetachedFromWindow();
	}

	/** 
	* <p>Title: onInitializeAccessibilityEvent</p> 
	* <p>Description: </p> 
	* @param event 
	* @see android.view.View#onInitializeAccessibilityEvent(android.view.accessibility.AccessibilityEvent) 
	*/
	@Override
	public void onInitializeAccessibilityEvent(AccessibilityEvent event) {
		super.onInitializeAccessibilityEvent(event);
		event.setItemCount(mMax);
		event.setCurrentItemIndex(mProgress);
	}

	/**
	 * Schedule a command for sending an accessibility event. </br> Note: A
	 * command is used to ensure that accessibility events are sent at most one
	 * in a given time frame to save system resources while the progress changes
	 * quickly.
	 */
	private void scheduleAccessibilityEventSender() {
		if (mAccessibilityEventSender == null) {
			mAccessibilityEventSender = new AccessibilityEventSender();
		} else {
			removeCallbacks(mAccessibilityEventSender);
		}
		postDelayed(mAccessibilityEventSender, TIMEOUT_SEND_ACCESSIBILITY_EVENT);
	}

	/**
	 * Command for sending an accessibility event.
	 */
	private class AccessibilityEventSender implements Runnable {
		
		/** 
		* <p>Title: run</p> 
		* <p>Description: </p>  
		* @see java.lang.Runnable#run() 
		*/
		@Override
		public void run() {
			sendAccessibilityEvent(AccessibilityEvent.TYPE_VIEW_SELECTED);
		}
	}
}
