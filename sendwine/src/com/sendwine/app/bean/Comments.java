/**   
 * @Title: Comments.java 
 * @Package com.sendwine.app.bean 
 * @Description: 
 *			
 * @author  ���� 794857063@qq.com
 * @date 2014-1-1 ����5:44:33 
 * @version V1.0   
 */

package com.sendwine.app.bean;

/**
 * @ClassName: Comments
 * @Description: TODO(������һ�仰��������������)
 * @author ���� 794857063@qq.com
 * @date 2014-1-1 ����5:44:33
 * 
 */
public class Comments extends Base {

	/**
	 * @Fields serialVersionUID : TODO
	 */
	private static final long serialVersionUID = -8754025780748802406L;

	public String userName = "";// �û���

	public String content = "";// ����

	public float rating = 0.0f;// ����

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
