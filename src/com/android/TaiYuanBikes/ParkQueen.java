package com.android.TaiYuanBikes;
/**停车场队列，主要用于对附近停车场信息获取、排序
 * 2013年3月31日21:26:28创建
 * 2013年4月   2日11:16:21修改
 * 王康瑾
 * */
import java.util.ArrayList;

import android.content.Context;

public class ParkQueen {

	private ArrayList<ParkItem> parks=new ArrayList<ParkItem>();
	public static final int NEAR_FIRST=1;
	public static final int PARKING_FIRST=2;
	private int parks_num;									//停车场总数
	private park_dao parkDB;
	
	ParkQueen(Context context){  //构造函数
		parkDB=new park_dao(context);
		parks_num=parkDB.selectAll().getCount();  //总行数
		for(int i = 0;i<parks_num;i++){
			int park_id= i+1;
			parks.add(new ParkItem(park_id,context));
		}
	}
	
	//排序，根据所给的排序原则
	public void sort(int policy){
		if (policy==NEAR_FIRST){
			quick_sort_near(parks,0,parks_num-1);
		}
		if(policy==PARKING_FIRST)
			quick_sort_postion(parks,0,parks_num-1);
		
	}
	
	
	//把停车场数组转换为String数组
	public ArrayList<String> toStringArrayList(){
		ArrayList<String> res=new ArrayList<String>();
		for(int i =0 ;i<parks_num;i++)
			res.add(parks.get(i).getName());
		return res;
	}
	
	//距离由近到远排序
	private void quick_sort_near(ArrayList<ParkItem> array,int l, int r){
		  if (l < r){
		              //Swap(s[l], s[(l + r) / 2]); //将中间的这个数和第一个数交换 参见注1
		        int i = l, j = r;
				ParkItem x = array.get(i);
		        while (i < j)
		        {
		            while(i < j && array.get(j).getDistance() >= x.getDistance()) // 从右向左找第一个小于x的数
		                            j--; 
		            if(i < j)
		            	array.set(i++, array.get(j));
		                    
		            while(i < j && array.get(i).getDistance() < x.getDistance()) // 从左向右找第一个大于等于x的数
		                            i++; 
		            if(i < j)
		            	array.set(j--, array.get(i));
		        }
		        array.set(i, x);
		        quick_sort_near(array, l, i - 1); // 递归调用
		        quick_sort_near(array, i + 1, r);
		  		}
		  
	}
	
	//车位由多到少排序
	private void quick_sort_postion(ArrayList<ParkItem> array,int l, int r){
		  if (l < r){
              //Swap(s[l], s[(l + r) / 2]); //将中间的这个数和第一个数交换 参见注1
        int i = l, j = r;
		ParkItem x = array.get(i);
        while (i < j)
        {
            while(i < j && array.get(j).getLeftPosition() <= x.getLeftPosition()) // 从右向左找第一个小于x的数
                            j--; 
            if(i < j)
            	array.set(i++, array.get(j));
                    
            while(i < j && array.get(i).getLeftPosition() > x.getLeftPosition()) // 从左向右找第一个大于等于x的数
                            i++; 
            if(i < j)
            	array.set(j--, array.get(i));
        }
        array.set(i, x);
        quick_sort_postion(array, l, i - 1); // 递归调用
        quick_sort_postion(array, i + 1, r);
  		}
  
}
	public ParkItem get(int index){
		return parks.get(index);
	}
}
