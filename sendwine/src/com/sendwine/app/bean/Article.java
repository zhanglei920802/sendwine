/**   
 * @Title: Article.java 
 * @Package com.sendwine.app.bean 
 * @Description: 
 *			
 * @author  ���� 794857063@qq.com
 * @date 2014-1-3 ����5:43:11 
 * @version V1.0   
 */

package com.sendwine.app.bean;

/**
 * @ClassName: Article
 * @Description: TODO(������һ�仰��������������)
 * @author ���� 794857063@qq.com
 * @date 2014-1-3 ����5:43:11
 * 
 */
public class Article extends Base {
	/** 
	* @Fields serialVersionUID : TODO 
	*/ 
	private static final long serialVersionUID = -1610122674742829065L;
	public String title = "";
	public String content = "";
	/** 
	* <p>Title: </p> 
	* <p>Description: </p> 
	* @param title
	* @param content 
	*/
	public Article(String title, String content) {
		super();
		this.title = title;
		this.content = content;
	}
	
	
}
