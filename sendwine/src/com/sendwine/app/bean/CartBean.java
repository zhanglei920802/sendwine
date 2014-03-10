/**   
 * @Title: CartBean.java 
 * @Package com.sendwine.app.bean 
 * @Description: 
 *			
 * @author  ���� 794857063@qq.com
 * @date 2014-1-3 ����3:56:57 
 * @version V1.0   
 */

package com.sendwine.app.bean;

/**
 * @ClassName: CartBean
 * @Description: TODO(������һ�仰��������������)
 * @author ���� 794857063@qq.com
 * @date 2014-1-3 ����3:56:57
 * 
 */
public class CartBean extends SimpleGoods {
	/**
	 * @Fields serialVersionUID : TODO
	 */
	private static final long serialVersionUID = -2049314640343295534L;
	public int account = 0;// ����
	public boolean checked = true;

	/**
	 * <p>
	 * Title:
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @param account
	 * @param checked
	 */
	public CartBean(int account, boolean checked, String name, String price,
			String salePrice, float grade, String thumbUrl, int id) {
		super();
		this.id = id;
		this.account = account;
		this.checked = checked;
		this.name = name;
		this.price = price;
		this.salePrice = salePrice;
		this.grade = grade;
		this.thumbUrl = thumbUrl;
	}

	/**
	 * (�� Javadoc)
	 * <p>
	 * Title: equals
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @param o
	 * @return
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object o) {
		// TODO Auto-generated method stub
		return ((CartBean) o).id == (id);
	}

}
