/**   
 * @Title: Category.java 
 * @Package com.sendwine.app.bean 
 * @Description: 
 *			
 * @author  张雷 794857063@qq.com
 * @date 2014-1-3 下午8:17:55 
 * @version V1.0   
 */

package com.sendwine.app.bean;

/**
 * @ClassName: Category
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author 张雷 794857063@qq.com
 * @date 2014-1-3 下午8:17:55
 * 
 */
public class Category extends Base {
	public String categoryName = "";
	public String categoryIntrocude = "";

	/**
	 * <p>
	 * Title:
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 */
	public Category() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * <p>
	 * Title:
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @param categoryName
	 * @param categoryIntrocude
	 */
	public Category(String categoryName, String categoryIntrocude) {
		super();
		this.categoryName = categoryName;
		this.categoryIntrocude = categoryIntrocude;
	}

}
