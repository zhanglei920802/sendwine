/**   
 * @Title: MenuListFragment.java 
 * @Package com.sendwine.app.ui 
 * @Description: 
 *			
 * @author  ���� 794857063@qq.com
 * @date 2014-1-1 ����3:13:06 
 * @version V1.0   
 */

package com.sendwine.app.ui;

import com.sendwine.app.AppContext;
import com.sendwine.app.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

/**
 * @ClassName: MenuListFragment
 * @Description: TODO(������һ�仰��������������)
 * @author ���� 794857063@qq.com
 * @date 2014-1-1 ����3:13:06
 * 
 */
public class MenuListFragment extends Fragment {

	private View view = null;
	private ImageView pic1 = null;
	private ListView lv_menu_list  = null;
	private ArrayAdapter< String> mMenuAdapter = null;
	private String [] mMenuData = null;
	
	private MainActivity mainActivity = null;
	/**
	 * (�� Javadoc)
	 * <p>
	 * Title: onCreate
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @param savedInstanceState
	 * @see android.support.v4.app.Fragment#onCreate(android.os.Bundle)
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	/**
	 * (�� Javadoc)
	 * <p>
	 * Title: onCreateView
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @param inflater
	 * @param container
	 * @param savedInstanceState
	 * @return
	 * @see android.support.v4.app.Fragment#onCreateView(android.view.LayoutInflater,
	 *      android.view.ViewGroup, android.os.Bundle)
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		// return super.onCreateView(inflater, container, savedInstanceState);
		// inflater.in
		view = inflater.inflate(R.layout.menu_list, null);
		init();
		return view;
	}

	/** 
	* @Title: init 
	* @Description: TODO
	* @param     �趨�ļ� 
	* @return void    �������� 
	* @throws 
	*/
	
	private void init() {
		// TODO Auto-generated method stub
		mMenuData = getResources().getStringArray(R.array.menu_list);
		
		mMenuAdapter = new ArrayAdapter<String>(getActivity(), R.layout.simple_list_item, mMenuData);
		lv_menu_list = (ListView) view.findViewById(R.id.lv_menu_list);
		lv_menu_list.setAdapter(mMenuAdapter);
		lv_menu_list.setOnItemClickListener(new OnItemClickListener() {
			
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				AppContext.mUiHelper.showGoodLists(getActivity(), null);
			}
		});
		pic1 = (ImageView) view.findViewById(R.id.pic1);
	}

}
