/**   
 * @Title: Category.java 
 * @Package com.sendwine.app.bean 
 * @Description: 
 *			
 * @author  ���� 794857063@qq.com
 * @date 2014-1-3 ����8:17:55 
 * @version V1.0   
 */

package com.sendwine.app.bean;

/**
 * @ClassName: Category
 * @Description: TODO(������һ�仰��������������)
 * @author ���� 794857063@qq.com
 * @date 2014-1-3 ����8:17:55
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
