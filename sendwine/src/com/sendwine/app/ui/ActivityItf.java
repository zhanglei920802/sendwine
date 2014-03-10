/**   
 * @Title: ActivityItf.java 
 * @Package com.sendwine.app.ui 
 * @Description: 
 *			
 * @author  ���� 794857063@qq.com
 * @date 2014-1-1 ����11:53:11 
 * @version V1.0   
 */

package com.sendwine.app.ui;

/**
 * ����UI���������ȸýӿ�
 * 
 * 
 * @ClassName: ActivityItf
 * @Description: Activityͨ�ýӿ�
 * @author ���� 794857063@qq.com
 * @date 2014-1-1 ����11:55:24
 * 
 */
public interface ActivityItf {

	/**
	 * ���մ���һ��Activity����������
	 * 
	 * @Title: getPreActivityData
	 * @Description: TODO
	 * @param �趨�ļ�
	 * @return void ��������
	 * @throws
	 */
	public void getPreActivityData();

	/**
	 * ��ʼ������
	 * 
	 * @Title: initData
	 * @Description: TODO
	 * @param �趨�ļ�
	 * @return void ��������
	 * @throws
	 */
	public void initData();

	/**
	 * ��ʼ��UI
	 * 
	 * @Title: initView
	 * @Description: TODO
	 * @param �趨�ļ�
	 * @return void ��������
	 * @throws
	 */
	public void initView();

	/**
	 * ��������
	 * 
	 * @Title: loadData
	 * @Description: TODO
	 * @param �趨�ļ�
	 * @return void ��������
	 * @throws
	 */
	public void loadData();
}
