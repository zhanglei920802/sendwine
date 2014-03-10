/**   
* @Title: ResourcesCompat.java 
* @Package com.actionbarsherlock.internal 
* @Description: 
*			澹版槑:
*			1)鎺屾帶鏍″洯鏄湰浜虹殑澶у鐨勫垱涓氫綔鍝�涔熸槸鏈汉鐨勬瘯涓氳璁�*			2)鏈▼搴忓皻鏈繘琛屽紑鏀炬簮浠ｇ爜,鎵�互绂佹缁勭粐鎴栬�鏄釜浜烘硠闇叉簮鐮�鍚﹀垯灏嗕細杩界┒鍏跺垜浜嬭矗浠�*			3)缂栧啓鏈蒋浠�鎴戦渶瑕佹劅璋㈠江鑰佸笀浠ュ強鍏朵粬榧撳姳鍜屾敮鎸佹垜鐨勫悓瀛︿互鍙婃湅鍙�*			4)鏈▼搴忕殑鏈�粓鎵�湁鏉冨睘浜庢湰浜�
* @author  寮犻浄 794857063@qq.com
* @date 2013-11-14 19:34:24 
* @version V1.0   
*/
package com.actionbarsherlock.internal;

import com.sendwine.app.R;

import android.content.Context;
import android.os.Build;
import android.util.DisplayMetrics;

// TODO: Auto-generated Javadoc
/**
 * The Class ResourcesCompat.
 */
public final class ResourcesCompat {
	// No instances
	/**
	 * Instantiates a new resources compat.
	 */
	private ResourcesCompat() {
	}

	/**
	 * Support implementation of {@code getResources().getBoolean()} that we can
	 * use to simulate filtering based on width and smallest width qualifiers on
	 * pre-3.2.
	 * 
	 * @param context
	 *            Context to load booleans from on 3.2+ and to fetch the display
	 *            metrics.
	 * @param id
	 *            Id of boolean to load.
	 * @return Associated boolean value as reflected by the current display
	 *         metrics.
	 */
	public static boolean getResources_getBoolean(Context context, int id) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
			return context.getResources().getBoolean(id);
		}

		DisplayMetrics metrics = context.getResources().getDisplayMetrics();
		float widthDp = metrics.widthPixels / metrics.density;
		float heightDp = metrics.heightPixels / metrics.density;
		float smallestWidthDp = (widthDp < heightDp) ? widthDp : heightDp;

		if (id == R.bool.abs__action_bar_embed_tabs) {
			if (widthDp >= 480) {
				return true; // values-w480dp
			}
			return false; // values
		}
		if (id == R.bool.abs__split_action_bar_is_narrow) {
			if (widthDp >= 480) {
				return false; // values-w480dp
			}
			return true; // values
		}
		if (id == R.bool.abs__action_bar_expanded_action_views_exclusive) {
			if (smallestWidthDp >= 600) {
				return false; // values-sw600dp
			}
			return true; // values
		}
		if (id == R.bool.abs__config_allowActionMenuItemTextWithIcon) {
			if (widthDp >= 480) {
				return true; // values-w480dp
			}
			return false; // values
		}

		throw new IllegalArgumentException("Unknown boolean resource ID " + id);
	}

	/**
	 * Support implementation of {@code getResources().getInteger()} that we can
	 * use to simulate filtering based on width qualifiers on pre-3.2.
	 * 
	 * @param context
	 *            Context to load integers from on 3.2+ and to fetch the display
	 *            metrics.
	 * @param id
	 *            Id of integer to load.
	 * @return Associated integer value as reflected by the current display
	 *         metrics.
	 */
	public static int getResources_getInteger(Context context, int id) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
			return context.getResources().getInteger(id);
		}

		DisplayMetrics metrics = context.getResources().getDisplayMetrics();
		float widthDp = metrics.widthPixels / metrics.density;

		if (id == R.integer.abs__max_action_buttons) {
			if (widthDp >= 600) {
				return 5; // values-w600dp
			}
			if (widthDp >= 500) {
				return 4; // values-w500dp
			}
			if (widthDp >= 360) {
				return 3; // values-w360dp
			}
			return 2; // values
		}

		throw new IllegalArgumentException("Unknown integer resource ID " + id);
	}
}
