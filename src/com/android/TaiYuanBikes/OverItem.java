package com.android.TaiYuanBikes;

import java.util.ArrayList;
import java.util.List;

import com.baidu.mapapi.map.ItemizedOverlay;
import com.baidu.mapapi.map.OverlayItem;
import com.baidu.platform.comapi.basestruct.GeoPoint;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.Toast;

public class OverItem extends ItemizedOverlay<OverlayItem> {
	private List<OverlayItem> GeoList = new ArrayList<OverlayItem>();
	private Context mContext;
	private park_dao park;
	private double[] park_lat;
	private double[] park_lon;
	private String[] park_name;
	private GeoPoint[] park_geo;

	private int count=0;					//记录总行数


	public OverItem(Drawable marker, Context context) {
		super(marker);

		this.mContext = context;
		
		//获得经纬度，初始化经纬度数组
		park =new park_dao(this.mContext);
		count=park.selectAll().getCount();  //总行数
		park_lat=new double[count];
		park_lon=new double[count];
		park_name= new String[count];
		for(int i=0;i<count;i++){
			park_lat[i] = Double.parseDouble(park.selectLatitude(String.valueOf(i+1)));
		}
		for(int i=0;i<count;i++){
			park_lon[i] = Double.parseDouble(park.selectLongitude(String.valueOf(i+1)));
		}
		for(int i=0;i<count;i++){
			park_name[i] = park.selectName(String.valueOf(i+1));
		}
		park.close();
		
		// 用给定的经纬度构造GeoPoint，单位是微度 (度 * 1E6)
		park_geo = new GeoPoint[count];
		for(int i=0;i<count;i++ ){
			park_geo[i]=new GeoPoint((int) (park_lat[i] * 1E6), (int) (park_lon[i] * 1E6));
			GeoList.add(new OverlayItem(park_geo[i], park_name[i], park_name[i]));
		}
		
		populate(); // createItem(int)方法构造item。一旦有了数据，在调用其它方法前，首先调用这个方法
	}

	@Override
	protected OverlayItem createItem(int i) {
		return GeoList.get(i);
	}

	@Override
	public int size() {
		return GeoList.size();
	}

	@Override
	// 处理当点击事件
	protected boolean onTap(int i) {
		Toast.makeText(this.mContext, GeoList.get(i).getSnippet(),
				Toast.LENGTH_SHORT).show();
		return true;
	}
}