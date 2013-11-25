package com.android.TaiYuanBikes;

import android.content.Context;

import com.baidu.mapapi.utils.DistanceUtil;
import com.baidu.platform.comapi.basestruct.GeoPoint;

public class ParkItem {
	/**停车场队列，主要用于对附近停车场信息获取、排序
	 * 2013年3月31日21:26:28创建
	 * 2013年4月   1日12:00:41修改
	 * 王康瑾
	 * */
	private String name;		//停车场名称
	private int left_parking;		//剩余车位
	private String position;
	private String id;
	private double distance;
	private park_dao parkDB;
	GeoPoint thispark;			//这个停车场的坐标
	ParkItem(int park_id ,Context context){
		/**初始化时：获得停车场与手机距离
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
