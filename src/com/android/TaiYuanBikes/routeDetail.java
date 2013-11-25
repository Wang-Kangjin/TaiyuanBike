package com.android.TaiYuanBikes;

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
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

public class routeDetail extends Activity{
	private ListView lv;

	private Button tv3;
	private ArrayList<String> route_res = new ArrayList<String>();	//路线数组
	@Override
	public void onCreate(Bundle saveInstanceState){
		super.onCreate(saveInstanceState);
		setContentView(R.layout.rout_detail);
		//添加查看地图的监听
		addListener();
		
		//获得驾车路线数组
		Bundle route_b=this.getIntent().getExtras();
		route_res=route_b.getStringArrayList("route_b");
		
		
		//加载list view项
		lv=(ListView) findViewById(R.id.routes);
		lv.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,route_res));
		
		
	}

	void addListener(){
		tv3=(Button)findViewById(R.id.return_route);
		tv3.setOnClickListener(new OnClickListener(){
			@Override
    		public void onClick(View v){
				 routeDetail.this.finish();
    			 //Intent intent = new Intent(near_pio_Activity.this, MainActivity.class);
    			 //startActivity(intent);
			}
		});
	}
}
