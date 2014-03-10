/**   
 * @Title: ActivityItf.java 
 * @Package com.sendwine.app.ui 
 * @Description: 
 *			
 * @author  张雷 794857063@qq.com
 * @date 2014-1-1 上午11:53:11 
 * @version V1.0   
 */

package com.sendwine.app.ui;

/**
 * 所有UI界面需事先该接口
 * 
 * 
 * @ClassName: ActivityItf
 * @Description: Activity通用接口
 * @author 张雷 794857063@qq.com
 * @date 2014-1-1 上午11:55:24
 * 
 */
public interface ActivityItf {

	/**
	 * 接收从上一个Activity传来的数据
	 * 
	 * @Title: getPreActivityData
	 * @Description: TODO
	 * @param 设定文件
	 * @return void 返回类型
	 * @throws
	 */
	public void getPreActivityData();

	/**
	 * 初始化数据
	 * 
	 * @Title: initData
	 * @Description: TODO
	 * @param 设定文件
	 * @return void 返回类型
	 * @throws
	 */
	public void initData();

	/**
	 * 初始化UI
	 * 
	 * @Title: initView
	 * @Description: TODO
	 * @param 设定文件
	 * @return void 返回类型
	 * @throws
	 */
	public void initView();

	/**
	 * 加载数据
	 * 
	 * @Title: loadData
	 * @Description: TODO
	 * @param 设定文件
	 * @return void 返回类型
	 * @throws
	 */
	public void loadData();
}
