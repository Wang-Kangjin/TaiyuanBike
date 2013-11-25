package com.android.TaiYuanBikes;
import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
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
import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.map.LocationData;
import com.baidu.mapapi.map.MKMapViewListener;
import com.baidu.mapapi.map.MapController;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationOverlay;
import com.baidu.mapapi.map.RouteOverlay;
import com.baidu.mapapi.map.TransitOverlay;
import com.baidu.mapapi.search.MKAddrInfo;
import com.baidu.mapapi.search.MKBusLineResult;
import com.baidu.mapapi.search.MKDrivingRouteResult;
import com.baidu.mapapi.search.MKPlanNode;
import com.baidu.mapapi.search.MKPoiResult;
import com.baidu.mapapi.search.MKSearch;
import com.baidu.mapapi.search.MKSearchListener;
import com.baidu.mapapi.search.MKSuggestionResult;
import com.baidu.mapapi.search.MKTransitRouteResult;
import com.baidu.mapapi.search.MKWalkingRouteResult;
import com.baidu.mapapi.utils.CoordinateConver;
import com.baidu.platform.comapi.basestruct.GeoPoint;
public class DrivingActivity extends Activity {
	
//	地图相关
	BMapManager mBMapMan = null;
	static MapView mMapView = null;
	static final GeoPoint initPoint=new GeoPoint((int)37.864644E6,(int)112.542705E6);
	private MapController mMapController = null;

	public MKMapViewListener mMapListener = null;
	public MKSearchListener mMKSearchListener = null;
	FrameLayout mMapViewContainer = null;
	
	// 定位相关
	LocationClient mLocClient;
	public MyLocationListenner myListener = new MyLocationListenner();
	GeoPoint mypos;
	MyLocationOverlay myLocationOverlay = null;

	LocationData locData = null;
    //菜单项相关
    private Button tv1;//查看列表
    private Button tv2;//详细路线
	

    //路线相关
    MKPlanNode start;
    MKPlanNode end;
    String parkId;
    RouteOverlay routeOverlay;
    private ArrayList<String> route_res = new ArrayList<String>();	//路线数组

    
    //数据库相关
    park_dao parkDB;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBMapMan=new BMapManager(getApplication());
        mBMapMan.init(DemoApplication.strKey, null);
        setContentView(R.layout.driving);
        mMapView = (MapView)findViewById(R.id.driving_bmapsView);
        mMapView.getOverlays().clear();
        
        //获取上一个页面传来的id
        Intent intent= getIntent();
        parkId=Integer.toString(Integer.parseInt(intent.getStringExtra("parkId")));
        parkDB =new park_dao(DrivingActivity.this);
        
        //创建页面底部的菜单控件监听相关
        createListener();
        
        mMapController = mMapView.getController();
        
        //初始化地图
        initMapView();		
        
        mLocClient = new LocationClient( this );
        mLocClient.registerLocationListener( myListener );	//注册监听
        
        addDrivingRoute();		//添加导航路线
        
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true);//打开gps
        option.setCoorType("bd09ll");     //设置坐标类型
        option.setScanSpan(500);			//设置定时定位的时间间隔。单位ms
        mLocClient.setLocOption(option);
        mLocClient.start();
        
        
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
					Toast.makeText(DrivingActivity.this,title,Toast.LENGTH_SHORT).show();
				}
			}
		};
		mMapView.regMapViewListener(DemoApplication.getInstance().mBMapManager, mMapListener);
		myLocationOverlay = new MyLocationOverlay(mMapView);
		locData = new LocationData();
	    myLocationOverlay.setData(locData);
		mMapView.getOverlays().add(myLocationOverlay);
		myLocationOverlay.enableCompass();
		
		
		parkDB.close();
    }
    

    private void initMapView() {
    	mMapController.setCenter(initPoint);
        mMapView.setLongClickable(true);
        mMapView.getController().setZoom(14);
        mMapView.getController().enableClick(true);
        mMapView.setBuiltInZoomControls(true);
        //mMapController.setMapClickEnable(true);
        
    }
    private void createListener(){
    	tv1=(Button)findViewById(R.id.return_back);
    	tv2=(Button)findViewById(R.id.route_detail);
    	//“返回”监听
    	tv1.setOnClickListener(new OnClickListener(){
    		@Override
    		public void onClick(View v){
    			 DrivingActivity.this.finish();
    		}
    	});
    	//“详细路线”监听
    	tv2.setOnClickListener(new OnClickListener(){
    		@Override
    		public void onClick(View v){
    			Bundle route_b=new Bundle();
    			route_b.putStringArrayList("route_b", route_res);
    			Intent route_i=new Intent(DrivingActivity.this,routeDetail.class);
    			route_i.putExtras(route_b);
    			startActivity(route_i);
    		}
    	});
    }

    
    
	
	/**
     * 监听函数，有新位置的时候，更新位置
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
            mypos = new GeoPoint((int)(locData.latitude* 1e6), (int)(locData.longitude *  1e6));
            myLocationOverlay.setData(locData);
            mMapView.refresh();
            
            
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
    void addDrivingRoute(){
    	 start = new MKPlanNode();
    	 start.pt = DemoApplication.myLoaction;
    	//start.pt = new GeoPoint((int) (locData.latitude* 1e6), (int) (locData.longitude *  1e6));
    	 
    	end = new MKPlanNode();
    	end.pt = new GeoPoint(40057031, 116307852);
    	end.pt = new GeoPoint((int) ((Double.parseDouble(parkDB.selectLatitude(parkId)))* 1e6), (int) ((Double.parseDouble(parkDB.selectLongitude(parkId))) *  1e6));// 设置驾车路线搜索策略，时间优先、费用最少或距离最短
    	MKSearch mMKSearch = new MKSearch();
    	mMKSearch.init(DemoApplication.getInstance().mBMapManager, new MKSearchListener(){

            @Override
            public void onGetPoiDetailSearchResult(int type, int error) {
            }
            
            
            //主要用这个获取驾车路线的方法
			public void onGetDrivingRouteResult(MKDrivingRouteResult res,
					int error) {
				// 错误号可参考MKEvent中的定义
				if (error != 0 || res == null) {
					Toast.makeText(DrivingActivity.this, "抱歉，未找到结果", Toast.LENGTH_SHORT).show();
					return;
				}
				//初始化路线数组
				int route_step=res.getPlan(0).getRoute(0).getNumSteps();
				route_res.clear();
				for (int i =0;i<route_step;i++){
					route_res.add(res.getPlan(0).getRoute(0).getStep(i).getContent());
				}
				
				 routeOverlay = new RouteOverlay(DrivingActivity.this, mMapView);
			    // 此处仅展示一个方案作为示例
			    routeOverlay.setData(res.getPlan(0).getRoute(0));
			    //mMapView.getOverlays().clear();
			    mMapView.getOverlays().add(routeOverlay);
			    mMapView.refresh();
			    mMapView.getController().animateTo(res.getStart().pt);
			}

			public void onGetTransitRouteResult(MKTransitRouteResult res,
					int error) {
				if (error != 0 || res == null) {
					Toast.makeText(DrivingActivity.this, "抱歉，未找到结果", Toast.LENGTH_SHORT).show();
					return;
				}
				TransitOverlay  routeOverlay = new TransitOverlay (DrivingActivity.this, mMapView);
			    // 此处仅展示一个方案作为示例
			    routeOverlay.setData(res.getPlan(0));
			    mMapView.getOverlays().clear();
			    mMapView.getOverlays().add(routeOverlay);
			    mMapView.refresh();
			    mMapView.getController().animateTo(res.getStart().pt);
			}

			public void onGetWalkingRouteResult(MKWalkingRouteResult res,
					int error) {
				if (error != 0 || res == null) {
					Toast.makeText(DrivingActivity.this, "抱歉，未找到结果", Toast.LENGTH_SHORT).show();
					return;
				}
				RouteOverlay routeOverlay = new RouteOverlay(DrivingActivity.this, mMapView);
			    // 此处仅展示一个方案作为示例
	
			    routeOverlay.setData(res.getPlan(0).getRoute(0));
			    ///mMapView.getOverlays().clear();
			    mMapView.getOverlays().add(routeOverlay);
			    mMapView.refresh();
			    mMapView.getController().animateTo(res.getStart().pt);
			    
			}
			public void onGetAddrResult(MKAddrInfo res, int error) {
			}
			public void onGetPoiResult(MKPoiResult res, int arg1, int arg2) {
			}
			public void onGetBusDetailResult(MKBusLineResult result, int iError) {
			}

			@Override
			public void onGetSuggestionResult(MKSuggestionResult res, int arg1) {
			}

        });
    	
    	mMKSearch.setDrivingPolicy(MKSearch.ECAR_TIME_FIRST);

    	mMKSearch.drivingSearch(null, start, null, end);

    }
}
