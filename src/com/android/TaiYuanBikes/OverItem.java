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

	private int count=0;					//��¼������


	public OverItem(Drawable marker, Context context) {
		super(marker);

		this.mContext = context;
		
		//��þ�γ�ȣ���ʼ����γ������
		park =new park_dao(this.mContext);
		count=park.selectAll().getCount();  //������
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
		
		// �ø����ľ�γ�ȹ���GeoPoint����λ��΢�� (�� * 1E6)
		park_geo = new GeoPoint[count];
		for(int i=0;i<count;i++ ){
			park_geo[i]=new GeoPoint((int) (park_lat[i] * 1E6), (int) (park_lon[i] * 1E6));
			GeoList.add(new OverlayItem(park_geo[i], park_name[i], park_name[i]));
		}
		
		populate(); // createItem(int)��������item��һ���������ݣ��ڵ�����������ǰ�����ȵ����������
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
	// ��������¼�
	protected boolean onTap(int i) {
		Toast.makeText(this.mContext, GeoList.get(i).getSnippet(),
				Toast.LENGTH_SHORT).show();
		return true;
	}
}