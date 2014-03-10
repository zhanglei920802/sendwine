/**   
 * @Title: Article.java 
 * @Package com.sendwine.app.bean 
 * @Description: 
 *			
 * @author  张雷 794857063@qq.com
 * @date 2014-1-3 下午5:43:11 
 * @version V1.0   
 */

package com.sendwine.app.bean;

/**
 * @ClassName: Article
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author 张雷 794857063@qq.com
 * @date 2014-1-3 下午5:43:11
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
