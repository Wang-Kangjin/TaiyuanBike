package com.android.TaiYuanBikes;

import android.content.Context;

import com.baidu.mapapi.utils.DistanceUtil;
import com.baidu.platform.comapi.basestruct.GeoPoint;

public class ParkItem {
	/**ͣ�������У���Ҫ���ڶԸ���ͣ������Ϣ��ȡ������
	 * 2013��3��31��21:26:28����
	 * 2013��4��   1��12:00:41�޸�
	 * �����
	 * */
	private String name;		//ͣ��������
	private int left_parking;		//ʣ�೵λ
	private String position;
	private String id;
	private double distance;
	private park_dao parkDB;
	GeoPoint thispark;			//���ͣ����������
	ParkItem(int park_id ,Context context){
		/**��ʼ��ʱ�����ͣ�������ֻ�����
		 * **/
		id=Integer.toString(park_id);
		parkDB=new park_dao(context);
		name=parkDB.selectName(id);
		left_parking=Integer.parseInt(parkDB.selectLeft_position(id));
		thispark=new GeoPoint((int)(Double.parseDouble(parkDB.selectLatitude(id))*1E6),
							  (int)(Double.parseDouble(parkDB.selectLongitude(id))*1E6));
		distance=DistanceUtil.getDistance(DemoApplication.myLoaction, thispark);
		position=parkDB.selectAddr(id);
	}
	@Override
	public
	String toString(){
		String string = null;
		return string;
	}
	public String getName(){
		return name;
	}
	public String getId(){
		return id;
	}
	public GeoPoint getGeoPoint(){
		return thispark;
	}
	public double getDistance(){
		return distance;
	}
	public int getLeftPosition(){
		return left_parking;
	}
	public String getPosition(){
		return position;
	}
}
