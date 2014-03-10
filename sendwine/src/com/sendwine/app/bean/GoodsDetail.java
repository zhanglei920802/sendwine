/**   
 * @Title: GoodsDetail.java 
 * @Package com.sendwine.app.bean 
 * @Description: 
 *			
 * @author  张雷 794857063@qq.com
 * @date 2014-1-1 下午5:20:05 
 * @version V1.0   
 */

package com.sendwine.app.bean;

/**
 * @ClassName: GoodsDetail
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @author 张雷 794857063@qq.com
 * @date 2014-1-1 下午5:20:05
 * 
 */
public class GoodsDetail extends SimpleGoods {

	/**
	 * @Fields serialVersionUID : TODO
	 */
	private static final long serialVersionUID = -1145875810907072745L;

	public String[] urlArrays = null;// 图片url数组

	// 参数
	public String capacity = "";// 容量

	public String producing = "";// 产地

	public String type = "";// 类型

	public String state = "";// 有货

	// 详情
	public String foodDetailURL = "";// 商品详情url

	
}
