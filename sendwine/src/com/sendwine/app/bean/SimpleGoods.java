/**   
 * @Title: SimpleGoods.java 
 * @Package com.sendwine.app.bean 
 * @Description: 
 *			
 * @author  ���� 794857063@qq.com
 * @date 2014-1-1 ����5:14:56 
 * @version V1.0   
 */

package com.sendwine.app.bean;

/**
 * @ClassName: SimpleGoods
 * @Description: TODO(������һ�仰��������������)
 * @author ���� 794857063@qq.com
 * @date 2014-1-1 ����5:14:56
 * 
 */
public class SimpleGoods extends Base {
	
	
	
	private static final long serialVersionUID = 7064655552137998629L;
	public int id = 0;
	
	public String name = "";// ����

	public String price = "";// �۸�

	public String salePrice = "";// ���ۼ۸�

	public float grade = 0.0f;// ����

	public String thumbUrl = "";// ����ͼ��ַ

	/** 
	 * <p>Title: </p> 
	 * <p>Description: </p>  
	 */
	public SimpleGoods() {
		// TODO Auto-generated constructor stub
	}

	/** 
	* <p>Title: </p> 
	* <p>Description: </p> 
	* @param name
	* @param price
	* @param salePrice
	* @param grade
	* @param thumbUrl 
	*/
	public SimpleGoods(int id,String name, String price, String salePrice,
			float grade, String thumbUrl) {
		super();
		this.id = id;
		this.name = name;
		this.price = price;
		this.salePrice = salePrice;
		this.grade = grade;
		this.thumbUrl = thumbUrl;
	}
	
	public SimpleGoods(String name, String price, String salePrice,
			float grade, String thumbUrl) {
		super();
		this.name = name;
		this.price = price;
		this.salePrice = salePrice;
		this.grade = grade;
		this.thumbUrl = thumbUrl;
	}
	
	
	
}
