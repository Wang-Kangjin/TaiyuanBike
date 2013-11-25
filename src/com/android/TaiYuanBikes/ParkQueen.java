package com.android.TaiYuanBikes;
/**ͣ�������У���Ҫ���ڶԸ���ͣ������Ϣ��ȡ������
 * 2013��3��31��21:26:28����
 * 2013��4��   2��11:16:21�޸�
 * �����
 * */
import java.util.ArrayList;

import android.content.Context;

public class ParkQueen {

	private ArrayList<ParkItem> parks=new ArrayList<ParkItem>();
	public static final int NEAR_FIRST=1;
	public static final int PARKING_FIRST=2;
	private int parks_num;									//ͣ��������
	private park_dao parkDB;
	
	ParkQueen(Context context){  //���캯��
		parkDB=new park_dao(context);
		parks_num=parkDB.selectAll().getCount();  //������
		for(int i = 0;i<parks_num;i++){
			int park_id= i+1;
			parks.add(new ParkItem(park_id,context));
		}
	}
	
	//���򣬸�������������ԭ��
	public void sort(int policy){
		if (policy==NEAR_FIRST){
			quick_sort_near(parks,0,parks_num-1);
		}
		if(policy==PARKING_FIRST)
			quick_sort_postion(parks,0,parks_num-1);
		
	}
	
	
	//��ͣ��������ת��ΪString����
	public ArrayList<String> toStringArrayList(){
		ArrayList<String> res=new ArrayList<String>();
		for(int i =0 ;i<parks_num;i++)
			res.add(parks.get(i).getName());
		return res;
	}
	
	//�����ɽ���Զ����
	private void quick_sort_near(ArrayList<ParkItem> array,int l, int r){
		  if (l < r){
		              //Swap(s[l], s[(l + r) / 2]); //���м��������͵�һ�������� �μ�ע1
		        int i = l, j = r;
				ParkItem x = array.get(i);
		        while (i < j)
		        {
		            while(i < j && array.get(j).getDistance() >= x.getDistance()) // ���������ҵ�һ��С��x����
		                            j--; 
		            if(i < j)
		            	array.set(i++, array.get(j));
		                    
		            while(i < j && array.get(i).getDistance() < x.getDistance()) // ���������ҵ�һ�����ڵ���x����
		                            i++; 
		            if(i < j)
		            	array.set(j--, array.get(i));
		        }
		        array.set(i, x);
		        quick_sort_near(array, l, i - 1); // �ݹ����
		        quick_sort_near(array, i + 1, r);
		  		}
		  
	}
	
	//��λ�ɶൽ������
	private void quick_sort_postion(ArrayList<ParkItem> array,int l, int r){
		  if (l < r){
              //Swap(s[l], s[(l + r) / 2]); //���м��������͵�һ�������� �μ�ע1
        int i = l, j = r;
		ParkItem x = array.get(i);
        while (i < j)
        {
            while(i < j && array.get(j).getLeftPosition() <= x.getLeftPosition()) // ���������ҵ�һ��С��x����
                            j--; 
            if(i < j)
            	array.set(i++, array.get(j));
                    
            while(i < j && array.get(i).getLeftPosition() > x.getLeftPosition()) // ���������ҵ�һ�����ڵ���x����
                            i++; 
            if(i < j)
            	array.set(j--, array.get(i));
        }
        array.set(i, x);
        quick_sort_postion(array, l, i - 1); // �ݹ����
        quick_sort_postion(array, i + 1, r);
  		}
  
}
	public ParkItem get(int index){
		return parks.get(index);
	}
}
