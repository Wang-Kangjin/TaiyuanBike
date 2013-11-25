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
	
//	��ͼ���
	BMapManager mBMapMan = null;
	static MapView mMapView = null;
	static final GeoPoint initPoint=new GeoPoint((int)37.864644E6,(int)112.542705E6);
	private MapController mMapController = null;

	public MKMapViewListener mMapListener = null;
	public MKSearchListener mMKSearchListener = null;
	FrameLayout mMapViewContainer = null;
	
	// ��λ���
	LocationClient mLocClient;
	public MyLocationListenner myListener = new MyLocationListenner();
	GeoPoint mypos;
	MyLocationOverlay myLocationOverlay = null;

	LocationData locData = null;
    //�˵������
    private Button tv1;//�鿴�б�
    private Button tv2;//��ϸ·��
	

    //·�����
    MKPlanNode start;
    MKPlanNode end;
    String parkId;
    RouteOverlay routeOverlay;
    private ArrayList<String> route_res = new ArrayList<String>();	//·������

    
    //���ݿ����
    park_dao parkDB;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBMapMan=new BMapManager(getApplication());
        mBMapMan.init(DemoApplication.strKey, null);
        setContentView(R.layout.driving);
        mMapView = (MapView)findViewById(R.id.driving_bmapsView);
        mMapView.getOverlays().clear();
        
        //��ȡ��һ��ҳ�洫����id
        Intent intent= getIntent();
        parkId=Integer.toString(Integer.parseInt(intent.getStringExtra("parkId")));
        parkDB =new park_dao(DrivingActivity.this);
        
        //����ҳ��ײ��Ĳ˵��ؼ��������
        createListener();
        
        mMapController = mMapView.getController();
        
        //��ʼ����ͼ
        initMapView();		
        
        mLocClient = new LocationClient( this );
        mLocClient.registerLocationListener( myListener );	//ע�����
        
        addDrivingRoute();		//��ӵ���·��
        
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true);//��gps
        option.setCoorType("bd09ll");     //������������
        option.setScanSpan(500);			//���ö�ʱ��λ��ʱ��������λms
        mLocClient.setLocOption(option);
        mLocClient.start();
        
        
        mMapListener = new MKMapViewListener() {
			//��ͼ��ʾ�¼��������� �ýӿڼ�����ͼ��ʾ�¼����û���Ҫʵ�ָýӿ��Դ�����Ӧ�¼���
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
    	//�����ء�����
    	tv1.setOnClickListener(new OnClickListener(){
    		@Override
    		public void onClick(View v){
    			 DrivingActivity.this.finish();
    		}
    	});
    	//����ϸ·�ߡ�����
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
     * ��������������λ�õ�ʱ�򣬸���λ��
     */
    public class MyLocationListenner implements BDLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            if (location == null)
                return ;
            
            locData.latitude = location.getLatitude();//γ��
            locData.longitude = location.getLongitude();//����
            locData.direction = 2.0f;
            locData.accuracy = location.getRadius();//�뾶
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
    	end.pt = new GeoPoint((int) ((Double.parseDouble(parkDB.selectLatitude(parkId)))* 1e6), (int) ((Double.parseDouble(parkDB.selectLongitude(parkId))) *  1e6));// ���üݳ�·���������ԣ�ʱ�����ȡ��������ٻ�������
    	MKSearch mMKSearch = new MKSearch();
    	mMKSearch.init(DemoApplication.getInstance().mBMapManager, new MKSearchListener(){

            @Override
            public void onGetPoiDetailSearchResult(int type, int error) {
            }
            
            
            //��Ҫ�������ȡ�ݳ�·�ߵķ���
			public void onGetDrivingRouteResult(MKDrivingRouteResult res,
					int error) {
				// ����ſɲο�MKEvent�еĶ���
				if (error != 0 || res == null) {
					Toast.makeText(DrivingActivity.this, "��Ǹ��δ�ҵ����", Toast.LENGTH_SHORT).show();
					return;
				}
				//��ʼ��·������
				int route_step=res.getPlan(0).getRoute(0).getNumSteps();
				route_res.clear();
				for (int i =0;i<route_step;i++){
					route_res.add(res.getPlan(0).getRoute(0).getStep(i).getContent());
				}
				
				 routeOverlay = new RouteOverlay(DrivingActivity.this, mMapView);
			    // �˴���չʾһ��������Ϊʾ��
			    routeOverlay.setData(res.getPlan(0).getRoute(0));
			    //mMapView.getOverlays().clear();
			    mMapView.getOverlays().add(routeOverlay);
			    mMapView.refresh();
			    mMapView.getController().animateTo(res.getStart().pt);
			}

			public void onGetTransitRouteResult(MKTransitRouteResult res,
					int error) {
				if (error != 0 || res == null) {
					Toast.makeText(DrivingActivity.this, "��Ǹ��δ�ҵ����", Toast.LENGTH_SHORT).show();
					return;
				}
				TransitOverlay  routeOverlay = new TransitOverlay (DrivingActivity.this, mMapView);
			    // �˴���չʾһ��������Ϊʾ��
			    routeOverlay.setData(res.getPlan(0));
			    mMapView.getOverlays().clear();
			    mMapView.getOverlays().add(routeOverlay);
			    mMapView.refresh();
			    mMapView.getController().animateTo(res.getStart().pt);
			}

			public void onGetWalkingRouteResult(MKWalkingRouteResult res,
					int error) {
				if (error != 0 || res == null) {
					Toast.makeText(DrivingActivity.this, "��Ǹ��δ�ҵ����", Toast.LENGTH_SHORT).show();
					return;
				}
				RouteOverlay routeOverlay = new RouteOverlay(DrivingActivity.this, mMapView);
			    // �˴���չʾһ��������Ϊʾ��
	
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
