package com.android.TaiYuanBikes;

import java.math.BigDecimal;
import java.util.ArrayList;

import com.android.TaiYuanBikes.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

public class near_pio_Activity extends Activity{
	private ArrayList<String> pios = new ArrayList<String>();
	private ParkQueen parkqueen;
	private ListView lv;
	private Button tv3;
	private Button l_button;
	private ArrayAdapter<String> adapter;    //list view 适配器
	@Override
	public void onCreate(Bundle saveInstanceState){
		super.onCreate(saveInstanceState);
		setContentView(R.layout.near_pio);
		//添加查看地图的监听
		addListener();
		
		//初始化array list数组
		/*park_info= new park_dao(near_pio_Activity.this);
		count=park_info.selectAll().getCount();  //总行数
		for(int i=0;i<count;i++){
			String id= Integer.toString(i+1);
			pios.add(park_info.selectName(id));
		}*/
		parkqueen=new ParkQueen(near_pio_Activity.this);
		pios=parkqueen.toStringArrayList();
		
		//数据库查询
		
		//加载list view项
		lv=(ListView) findViewById(R.id.pio_id);
		adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,pios);
		lv.setAdapter(adapter);
		//添加list view监听
		lv.setOnItemClickListener(new OnItemClickListener(){
			@Override 
			public void onItemClick (AdapterView<?> parent,View view, final int position,long id){
				String dig_title=pios.get(position);
				//设置list view中的数据
				//position+=1;	//id与position值相差1，需要加1
				String dig_add=getAdd(position);
				String dig_dis=getDistance(position);
				String[] dig_array={dig_add,dig_dis};
				//lv_dig.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,dig_array));
				 LayoutInflater inflater = getLayoutInflater();
				 View layout = inflater.inflate(R.layout.dialog_layout,
						 (ViewGroup) findViewById(R.id.info_dialog));
				 new AlertDialog.Builder(near_pio_Activity.this).setTitle(dig_title).setView(layout).setItems(dig_array, new DialogInterface.OnClickListener(){

					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						// TODO Auto-generated method stub
						
					}})
			    	.setPositiveButton("开始导航", new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO 点击事件处理
							String parkId=parkqueen.get(position).getId();
							Intent intent = new Intent(near_pio_Activity.this, DrivingActivity.class);
							intent.putExtra("parkId", parkId);
			    			startActivity(intent);
						}
					}).setNegativeButton("取消", null).show();
				 
			}
		});
		//park_info.close();
	}
	String getAdd(int id){
		//获得所选对象的地址
		String addr = parkqueen.get(id).getPosition();
		String result="地址："+addr;
		return result;
	}
	
	String getDistance(int id){
		//获得所选对象的距离
		double dis=parkqueen.get(id).getDistance();
		double dis_km=dis/1000;
		dis_km = round(dis_km,2,BigDecimal.ROUND_HALF_UP);
		String km=String.valueOf(dis_km);
		return "距离："+km+"Km";
	}
	void addListener(){
		tv3=(Button)findViewById(R.id.look_map);
		l_button=(Button)findViewById(R.id.near_first);
		
		tv3.setOnClickListener(new OnClickListener(){
			@Override
    		public void onClick(View v){
				 near_pio_Activity.this.finish();
    			 //Intent intent = new Intent(near_pio_Activity.this, MainActivity.class);
    			 //startActivity(intent);
			}
		});
		l_button.setOnClickListener(new OnClickListener(){
			//点击左边按钮，调用按距离从近到远排序
			@Override
    		public void onClick(View v){
				 parkqueen.sort(ParkQueen.NEAR_FIRST);
				 pios.clear();
				 ArrayList<String> array = new ArrayList<String>( parkqueen.toStringArrayList());
				 for(int i=0;i<array.size();i++){
					 pios.add(array.get(i));
				 }
				 adapter.notifyDataSetChanged();
			}
		});
		
	}
	  public static double round(double value, int scale, int roundingMode) {  
	        BigDecimal bd = new BigDecimal(value);  
	        bd = bd.setScale(scale, roundingMode);  
	        double d = bd.doubleValue();  
	        bd = null;  
	        return d;  
	    }
}
