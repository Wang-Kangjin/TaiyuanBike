package com.android.TaiYuanBikes;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.TaiYuanBikes.R;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.BDNotifyListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.LocationData;
import com.baidu.mapapi.map.MKEvent;
import com.baidu.mapapi.map.MKMapViewListener;
import com.baidu.mapapi.map.MapController;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationOverlay;
import com.baidu.mapapi.map.PoiOverlay;
import com.baidu.mapapi.search.MKAddrInfo;
import com.baidu.mapapi.search.MKBusLineResult;
import com.baidu.mapapi.search.MKDrivingRouteResult;
import com.baidu.mapapi.search.MKPoiInfo;
import com.baidu.mapapi.search.MKPoiResult;
import com.baidu.mapapi.search.MKSearch;
import com.baidu.mapapi.search.MKSearchListener;
import com.baidu.mapapi.search.MKSuggestionResult;
import com.baidu.mapapi.search.MKTransitRouteResult;
import com.baidu.mapapi.search.MKWalkingRouteResult;
import com.baidu.mapapi.utils.CoordinateConver;
import com.baidu.platform.comapi.basestruct.GeoPoint;
public class MainActivity extends Activity {
	
	static MapView mMapView = null;
	static final GeoPoint initPoint=new GeoPoint((int)37.864644E6,(int)112.542705E6);
	private MapController mMapController = null;

	public MKMapViewListener mMapListener = null;
	FrameLayout mMapViewContainer = null;
	
	// 定位相关
	LocationClient mLocClient;
	public MyLocationListenner myListener = new MyLocationListenner();
    public NotifyLister mNotifyer=null;
    GeoPoint mypos;

	MyLocationOverlay myLocationOverlay = null;

	LocationData locData = new LocationData();
    //菜单项相关
	EditText enterText;
    private Button tv1;//查看列表
    private TextView tv2;//菜单
	Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            Toast.makeText(MainActivity.this, "msg:" +msg.what, Toast.LENGTH_SHORT).show();
        };
    };
    
    //搜索相关
    MKSearch mMKSearch = null;
    String search_key;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
      
        mMapView = (MapView)findViewById(R.id.bmapsView);
        
        //菜单控件监听相关
        createListener();
        
        mMapController = mMapView.getController();
        
        initMapView();
        
        
        
        //搜索初始化
        mMKSearch = new MKSearch();
        mMKSearch.init(DemoApplication.getInstance().mBMapManager, new MKSearchListener(){

        	@Override
        	public void onGetAddrResult(MKAddrInfo arg0, int arg1) {
        		// TODO Auto-generated method stub
        		
        	}

        	@Override
        	public void onGetBusDetailResult(MKBusLineResult arg0, int arg1) {
        		// TODO Auto-generated method stub
        		
        	}

        	@Override
        	public void onGetDrivingRouteResult(MKDrivingRouteResult arg0, int arg1) {
        		// TODO Auto-generated method stub
        		
        	}

        	@Override
        	public void onGetPoiDetailSearchResult(int arg0, int arg1) {
        		// TODO Auto-generated method stub
        		
        	}

        	@Override
        	public void onGetPoiResult(MKPoiResult res, int type, int error) {
        		// TODO Auto-generated method stub
        		// 错误号可参考MKEvent中的定义

        		if ( error == MKEvent.ERROR_RESULT_NOT_FOUND){

        			Toast.makeText(MainActivity.this, "抱歉，未找到结果",Toast.LENGTH_LONG).show();

        		    return ;

        		        }

        		        else if (error != 0 || res == null) {

        		        Toast.makeText(MainActivity.this, "搜索出错啦..", Toast.LENGTH_LONG).show();

        		        return;

        		        }

        			// 将poi结果显示到地图上

        			PoiOverlay poiOverlay = new PoiOverlay(MainActivity.this, mMapView);

        			poiOverlay.setData(res.getAllPoi());

        			mMapView.getOverlays().add(poiOverlay);

        			mMapView.refresh();

        			//当ePoiType为2（公交线路）或4（地铁线路）时， poi坐标为空

        			for(MKPoiInfo info : res.getAllPoi() ){

        				if ( info.pt != null ){

        				mMapView.getController().animateTo(info.pt);

        				break;

        				}

        		    }

        	}

        	@Override
        	public void onGetSuggestionResult(MKSuggestionResult arg0, int arg1) {
        		// TODO Auto-generated method stub
        		
        	}

        	@Override
        	public void onGetTransitRouteResult(MKTransitRouteResult arg0, int arg1) {
        		// TODO Auto-generated method stub
        		
        	}

        	@Override
        	public void onGetWalkingRouteResult(MKWalkingRouteResult arg0, int arg1) {
        		// TODO Auto-generated method stub
        		
        	}

        });//注意，MKSearchListener只支持一个，以最后一次设置为准
        
        mMapView.getController().setZoom(14);
        mMapView.getController().enableClick(true);
        
        mMapListener = new MKMapViewListener() {
			//地图显示事件监听器。 该接口监听地图显示事件，用户需要实现该接口以处理相应事件。
			@Override
			public void onMapMoveFinish() {
				// TODO Auto-generated method stub
			}
			
			@Override
			public void onClickMapPoi(MapPoi mapPoiInfo) {
				// TODO Auto-generated method stub
				String title = "";
				if (mapPoiInfo != null){
					title = mapPoiInfo.strText;
					Toast.makeText(MainActivity.this,title,Toast.LENGTH_SHORT).show();
				}
			}
		};
		mMapView.regMapViewListener(DemoApplication.getInstance().mBMapManager, mMapListener);
		myLocationOverlay = new MyLocationOverlay(mMapView);
		 
		locData.latitude=(double)37.906793E6;
		locData.longitude=(double)112.518415E6;
	    myLocationOverlay.setData(locData);
		mMapView.getOverlays().add(myLocationOverlay);
		myLocationOverlay.enableCompass();
		
		
		mLocClient = new LocationClient( this );
        mLocClient.registerLocationListener( myListener );	//注册监听
        
        //位置提醒相关代码
        mNotifyer = new NotifyLister();
        mNotifyer.SetNotifyLocation(42.03249652949337,113.3129895882556,3000,"bd09ll");
        //4个参数代表要位置提醒的点的坐标，具体含义依次为：纬度，经度，距离范围，坐标系类型(gcj02,gps,bd09,bd09ll)
        
        mLocClient.registerNotify(mNotifyer);
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true);//打开gps
        option.setCoorType("bd09ll");     //设置坐标类型
        option.setScanSpan(5000);			//设置定时定位的时间间隔。单位ms
        mLocClient.setLocOption(option);
        mLocClient.start();
		mMapView.refresh();
		
		Drawable marker = getResources().getDrawable(R.drawable.bz); //得到需要标在地图上的资源
		mMapView.getOverlays().add(new OverItem(marker, MainActivity.this)); //添加ItemizedOverlay实例到mMapView
		mMapView.refresh();//刷新地图
    }
    

    private void initMapView() {
    	mMapController.setCenter(initPoint);
        mMapView.setLongClickable(true);
        mMapView.getController().setZoom(14);
        //mMapController.setMapClickEnable(true);
        
    }
    private void createListener(){
    	tv1=(Button)findViewById(R.id.near_pio);
    	
    	//“附近停车场”监听
    	tv1.setOnClickListener(new OnClickListener(){
    		@Override
    		public void onClick(View v){
    			 Intent intent = new Intent(MainActivity.this, near_pio_Activity.class);
    			 startActivity(intent);
    		}
    	});
    	
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
    	switch (item.getItemId()){
    	case R.id.menu_mypos:mMapController.animateTo(mypos);return true;
    	case R.id.menu_search: 
    		LayoutInflater inflater = getLayoutInflater();
    		final View layout = inflater.inflate(R.layout.edit_dialog,
                (ViewGroup) findViewById(R.id.dialog));   new AlertDialog.Builder(MainActivity.this).setTitle("搜索").setView(layout)
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
          
          @Override
          public void onClick(DialogInterface dialog, int which) {
           // TODO Auto-generated method stub
           enterText=(EditText) layout.findViewById(R.id.etname);
           search_key=enterText.getText().toString();
           GeoPoint ptLB = new GeoPoint( (int)(37.898593 * 1E6),(int)(112.502317 * 1E6)); 

        // 太原西北部的一个点

        GeoPoint ptRT = new GeoPoint( (int)(37.800576 * 1E6),(int)(112.592004 * 1E6));

        mMKSearch.poiSearchInbounds(search_key, ptLB, ptRT);
          }
         })
                .setNegativeButton("取消", null).show();
        return true;
    	case R.id.menu_quite: this.finish();return true;
    	case R.id.menu_clear: mMapView.getOverlays().clear();
    						  Drawable marker = getResources().getDrawable(R.drawable.bz); //得到需要标在地图上的资源
							  mMapView.getOverlays().add(new OverItem(marker, MainActivity.this)); //添加ItemizedOverlay实例到mMapView
							  myLocationOverlay.setData(locData);
							  mMapView.getOverlays().add(myLocationOverlay);
							  mMapView.refresh(); 
							  return true;
    	default :return super.onOptionsItemSelected(item);}
    }
	
	/**
     * 监听函数，又新位置的时候，格式化成字符串，输出到屏幕中
     */
    public class MyLocationListenner implements BDLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            if (location == null)
                return ;
            
            locData.latitude = location.getLatitude();//纬度
            locData.longitude = location.getLongitude();//经度
            locData.direction = 2.0f;
            locData.accuracy = location.getRadius();//半径
            locData.direction = location.getDerect();
            //Log.d("loctest",String.format("before: lat: %f lon: %f", location.getLatitude(),location.getLongitude()));
           // GeoPoint p = CoordinateConver.fromGcjToBaidu(new GeoPoint((int)(locData.latitude* 1e6), (int)(locData.longitude *  1e6)));
          //  Log.d("loctest",String.format("before: lat: %d lon: %d", p.getLatitudeE6(),p.getLongitudeE6()));
            myLocationOverlay.setData(locData);
            mMapView.refresh();
            mypos=new GeoPoint((int)(locData.latitude* 1e6), (int)(locData.longitude *  1e6));
            DemoApplication.myLoaction=mypos;
            //Toast.makeText(MainActivity.this, locData.latitude+" ,"+locData.longitude, Toast.LENGTH_LONG).show();
            //mMapController.animateTo(new GeoPoint((int)(locData.latitude* 1e6), (int)(locData.longitude *  1e6)));
            
        }
        
        public void onReceivePoi(BDLocation poiLocation) {
            if (poiLocation == null){
                return ;
            }
        }
    }
    
    public class NotifyLister extends BDNotifyListener{
        public void onNotify(BDLocation mlocation, float distance) {
        }
    }
    
    @Override
    protected void onPause() {
        mMapView.onPause();
        super.onPause();
    }
    
    @Override
    protected void onResume() {
        mMapView.onResume();
        super.onResume();
    }
    
    @Override
    protected void onSaveInstanceState(Bundle outState) {
    	super.onSaveInstanceState(outState);
    	mMapView.onSaveInstanceState(outState);
    	
    }
    
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
    	super.onRestoreInstanceState(savedInstanceState);
    	mMapView.onRestoreInstanceState(savedInstanceState);
    }
    
    public void testUpdateClick(){
        mLocClient.requestLocation();
    }
}


