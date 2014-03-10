
package com.slidingmenu.lib.app;

import android.os.Bundle;

import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup.LayoutParams;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.slidingmenu.lib.SlidingMenu;

// TODO: Auto-generated Javadoc
/**
 * The Class SlidingFragmentActivity.
 */
public class SlidingFragmentActivity extends SherlockFragmentActivity implements
		SlidingActivityBase {

	/** The m helper. */
	private SlidingActivityHelper mHelper;

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.support.v4.app.FragmentActivity#onCreate(android.os.Bundle)
	 */
	/** 
	* <p>Title: onCreate</p> 
	* <p>Description: </p> 
	* @param savedInstanceState 
	* @see android.support.v4.app.FragmentActivity#onCreate(android.os.Bundle) 
	*/
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mHelper = new SlidingActivityHelper(this);
		mHelper.onCreate(savedInstanceState);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onPostCreate(android.os.Bundle)
	 */
	/** 
	* <p>Title: onPostCreate</p> 
	* <p>Description: </p> 
	* @param savedInstanceState 
	* @see com.actionbarsherlock.app.SherlockFragmentActivity#onPostCreate(android.os.Bundle) 
	*/
	@Override
	public void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		mHelper.onPostCreate(savedInstanceState);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#findViewById(int)
	 */
	/** 
	* <p>Title: findViewById</p> 
	* <p>Description: </p> 
	* @param id
	* @return 
	* @see android.app.Activity#findViewById(int) 
	*/
	@Override
	public View findViewById(int id) {
		View v = super.findViewById(id);
		if (v != null)
			return v;
		return mHelper.findViewById(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.support.v4.app.FragmentActivity#onSaveInstanceState(android.os
	 * .Bundle)
	 */
	/** 
	* <p>Title: onSaveInstanceState</p> 
	* <p>Description: </p> 
	* @param outState 
	* @see com.actionbarsherlock.app.SherlockFragmentActivity#onSaveInstanceState(android.os.Bundle) 
	*/
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		mHelper.onSaveInstanceState(outState);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#setContentView(int)
	 */
	/** 
	* <p>Title: setContentView</p> 
	* <p>Description: </p> 
	* @param id 
	* @see com.actionbarsherlock.app.SherlockFragmentActivity#setContentView(int) 
	*/
	@Override
	public void setContentView(int id) {
		setContentView(getLayoutInflater().inflate(id, null));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#setContentView(android.view.View)
	 */
	/** 
	* <p>Title: setContentView</p> 
	* <p>Description: </p> 
	* @param v 
	* @see com.actionbarsherlock.app.SherlockFragmentActivity#setContentView(android.view.View) 
	*/
	@Override
	public void setContentView(View v) {
		setContentView(v, new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#setContentView(android.view.View,
	 * android.view.ViewGroup.LayoutParams)
	 */
	/** 
	* <p>Title: setContentView</p> 
	* <p>Description: </p> 
	* @param v
	* @param params 
	* @see com.actionbarsherlock.app.SherlockFragmentActivity#setContentView(android.view.View, android.view.ViewGroup.LayoutParams) 
	*/
	@Override
	public void setContentView(View v, LayoutParams params) {
		super.setContentView(v, params);
		mHelper.registerAboveContentView(v, params);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.slidingmenu.lib.app.SlidingActivityBase#setBehindContentView(int)
	 */
	/** 
	* <p>Title: setBehindContentView</p> 
	* <p>Description: </p> 
	* @param id 
	* @see com.slidingmenu.lib.app.SlidingActivityBase#setBehindContentView(int) 
	*/
	@Override
	public void setBehindContentView(int id) {
		setBehindContentView(getLayoutInflater().inflate(id, null));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.slidingmenu.lib.app.SlidingActivityBase#setBehindContentView(android
	 * .view.View)
	 */
	/** 
	* <p>Title: setBehindContentView</p> 
	* <p>Description: </p> 
	* @param v 
	* @see com.slidingmenu.lib.app.SlidingActivityBase#setBehindContentView(android.view.View) 
	*/
	@Override
	public void setBehindContentView(View v) {
		setBehindContentView(v, new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.slidingmenu.lib.app.SlidingActivityBase#setBehindContentView(android
	 * .view.View, android.view.ViewGroup.LayoutParams)
	 */
	/** 
	* <p>Title: setBehindContentView</p> 
	* <p>Description: </p> 
	* @param v
	* @param params 
	* @see com.slidingmenu.lib.app.SlidingActivityBase#setBehindContentView(android.view.View, android.view.ViewGroup.LayoutParams) 
	*/
	@Override
	public void setBehindContentView(View v, LayoutParams params) {
		mHelper.setBehindContentView(v, params);
	}

	
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.slidingmenu.lib.app.SlidingActivityBase#getSlidingMenu()
	 */
	/** 
	* <p>Title: getSlidingMenu</p> 
	* <p>Description: </p> 
	* @return 
	* @see com.slidingmenu.lib.app.SlidingActivityBase#getSlidingMenu() 
	*/
	@Override
	public SlidingMenu getSlidingMenu() {
		return mHelper.getSlidingMenu();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.slidingmenu.lib.app.SlidingActivityBase#toggle()
	 */
	/** 
	* <p>Title: toggle</p> 
	* <p>Description: </p>  
	* @see com.slidingmenu.lib.app.SlidingActivityBase#toggle() 
	*/
	@Override
	public void toggle() {
		mHelper.toggle();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.slidingmenu.lib.app.SlidingActivityBase#showAbove()
	 */
	/** 
	* <p>Title: showContent</p> 
	* <p>Description: </p>  
	* @see com.slidingmenu.lib.app.SlidingActivityBase#showContent() 
	*/
	@Override
	public void showContent() {
		mHelper.showContent();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.slidingmenu.lib.app.SlidingActivityBase#showBehind()
	 */
	/** 
	* <p>Title: showMenu</p> 
	* <p>Description: </p>  
	* @see com.slidingmenu.lib.app.SlidingActivityBase#showMenu() 
	*/
	@Override
	public void showMenu() {
		mHelper.showMenu();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.slidingmenu.lib.app.SlidingActivityBase#showSecondaryMenu()
	 */
	/** 
	* <p>Title: showSecondaryMenu</p> 
	* <p>Description: </p>  
	* @see com.slidingmenu.lib.app.SlidingActivityBase#showSecondaryMenu() 
	*/
	@Override
	public void showSecondaryMenu() {
		mHelper.showSecondaryMenu();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.slidingmenu.lib.app.SlidingActivityBase#setSlidingActionBarEnabled
	 * (boolean)
	 */
	/** 
	* <p>Title: setSlidingActionBarEnabled</p> 
	* <p>Description: </p> 
	* @param b 
	* @see com.slidingmenu.lib.app.SlidingActivityBase#setSlidingActionBarEnabled(boolean) 
	*/
	@Override
	public void setSlidingActionBarEnabled(boolean b) {
		mHelper.setSlidingActionBarEnabled(b);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onKeyUp(int, android.view.KeyEvent)
	 */
	/** 
	* <p>Title: onKeyUp</p> 
	* <p>Description: </p> 
	* @param keyCode
	* @param event
	* @return 
	* @see android.app.Activity#onKeyUp(int, android.view.KeyEvent) 
	*/
	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		boolean b = mHelper.onKeyUp(keyCode, event);
		if (b)
			return b;
		return super.onKeyUp(keyCode, event);
	}

}
