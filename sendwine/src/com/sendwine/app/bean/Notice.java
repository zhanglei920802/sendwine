
package com.sendwine.app.bean;

import java.io.Serializable;

// TODO: Auto-generated Javadoc
/**
 * The Class Notice.
 */
public class Notice implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -8954827863908501137L;

	/** The new data. */
	private int newData = 0;

	/**
	 * Gets the new data.
	 * 
	 * @return the new data
	 */
	public int getNewData() {
		return newData;
	}

	/**
	 * Sets the new data.
	 * 
	 * @param newData
	 *            the new new data
	 */
	public void setNewData(int newData) {
		this.newData = newData;
	}

	/** 
	* <p>Title: toString</p> 
	* <p>Description: </p> 
	* @return 
	* @see java.lang.Object#toString() 
	*/
	@Override
	public String toString() {
		return "Notice [newData=" + newData + "]";
	}

}
