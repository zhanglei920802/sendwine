
package com.sendwine.app.bean;

// TODO: Auto-generated Javadoc

public abstract class Entity extends Base {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 4065116298036729956L;
	
	/** The cache key. */
	protected String cacheKey = null;
	
	/** The id. */
	private int id;

	/**
	 * Gets the cache key.
	 * 
	 * @return the cache key
	 */
	public String getCacheKey() {
		return cacheKey;
	}

	/**
	 * Gets the id.
	 * 
	 * @return the id
	 */
	public int getId() {
		return this.id;
	}

	/**
	 * Sets the cache key.
	 * 
	 * @param cacheKey
	 *            the new cache key
	 */
	public void setCacheKey(String cacheKey) {
		this.cacheKey = cacheKey;
	}
}
