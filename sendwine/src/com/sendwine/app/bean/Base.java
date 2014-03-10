

package com.sendwine.app.bean;

import java.io.Serializable;

// TODO: Auto-generated Javadoc
/**
 * The Class Base.
 */
public abstract class Base implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 6147655444773517813L;
	
	/** The Constant UTF8. */
	public final static String UTF8 = "UTF-8";
	
	/** The Constant NODE_ROOT. */
	public final static String NODE_ROOT = "cdut";

	/** The notice. */
	protected Notice notice = null;

	/**
	 * Gets the notice.
	 * 
	 * @return the notice
	 */
	public Notice getNotice() {
		return notice;
	}

	/**
	 * Sets the notice.
	 * 
	 * @param notice
	 *            the new notice
	 */
	public void setNotice(Notice notice) {
		this.notice = notice;
	}

}
