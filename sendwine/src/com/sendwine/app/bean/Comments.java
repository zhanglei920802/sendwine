/**   
 * @Title: Comments.java 
 * @Package com.sendwine.app.bean 
 * @Description: 
 *			
 * @author  张雷 794857063@qq.com
 * @date 2014-1-1 下午5:44:33 
 * @version V1.0   
 */

package com.sendwine.app.bean;

/**
 * @ClassName: Comments
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author 张雷 794857063@qq.com
 * @date 2014-1-1 下午5:44:33
 * 
 */
public class Comments extends Base {

	/**
	 * @Fields serialVersionUID : TODO
	 */
	private static final long serialVersionUID = -8754025780748802406L;

	public String userName = "";// 用户名

	public String content = "";// 内容

	public float rating = 0.0f;// 评分

	/** 
	* <p>Title: </p> 
	* <p>Description: </p> 
	* @param userName
	* @param content
	* @param rating 
	*/
	public Comments(String userName, String content, float rating) {
		super();
		this.userName = userName;
		this.content = content;
		this.rating = rating;
	}
	
	/** 
	 * <p>Title: </p> 
	 * <p>Description: </p>  
	 */
	public Comments() {
		// TODO Auto-generated constructor stub
	}
}
