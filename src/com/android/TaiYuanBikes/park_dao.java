package com.android.TaiYuanBikes;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class park_dao extends SQLiteOpenHelper{
	private final static String DB_NAME="park";
	private final static int DB_VERDSION=1;
	private final static String Table_name="park";
	
	
	//Constructor
	public park_dao(Context context){
		super(context,DB_NAME,null,DB_VERDSION);
	}
	
	@Override
	/**
	* 当数据库首次创建时执行该方法，一般将创建表等初始化操作放在该方法中执行.
	* 重写onCreate方法，调用execSQL方法创建表
	* */
	public void onCreate(SQLiteDatabase db){
		String sql=" CREATE TABLE [park] (" +
				"[id] INT NOT NULL ON CONFLICT FAIL," +
				"[name] VARCHAR2(30)," +
				"[longitude] VARCHAR(15)," +
				"[latitude] VARCHAR(15)," +
				"[left_postion] INT," +
				"[addr] VARCHAR2(25)) ";
		Log.d("tag", "db no exits");
		db.execSQL(sql);
		String insert_sql="insert into park values(1,'理工大北校区','112.5299','37.863444',2,'新矿院路迎泽街口')";
		db.execSQL(insert_sql);
		insert_sql="insert into park values(2,'理工大虎峪校区','112.529644','37.860962',23,'新矿院路移村南街口')";
		db.execSQL(insert_sql);
		insert_sql="insert into park values(3,'理工大西北门','112.536134','37.865805',20,'千峰北路理工大西北门 ')";
		db.execSQL(insert_sql);
		insert_sql="insert into park values(4,'滨河西路迎泽西大街北口','112.547242','37.866075',32,'滨河西路迎泽西大街北口')";
		db.execSQL(insert_sql);
		insert_sql="insert into park values(5,'山西博物院','112.539404','37.871908',12,'滨河西路省博物院')";
		db.execSQL(insert_sql);
		insert_sql="insert into park values(6,'后王村','112.53369','37.857475',19,'后王街大王路口 ')";
		db.execSQL(insert_sql);
		insert_sql="insert into park values(7,'美特好千峰店','112.525785','37.856848',32,'千峰南路后王街口')";
		db.execSQL(insert_sql);
		insert_sql="insert into park values(8,'山西科技会展中心','112.545929','37.871228',20,'滨河东路北段22号')";
		db.execSQL(insert_sql);
		insert_sql="insert into park values(9,'滨河东路水西关南街口','112.545764','37.868482',12,'滨河东路水西关南街口')";
		db.execSQL(insert_sql);
		insert_sql="insert into park values(10,'省老干部活动中心','112.546798','37.867193',45,'劲松北路5号')";
		db.execSQL(insert_sql);
		insert_sql="insert into park values(11,'劲松路桃园南路西一巷口','112.5472','37.863379',23,'劲松路桃园南路西一巷口')";
		db.execSQL(insert_sql);
		insert_sql="insert into park values(12,'滨河东路康乐街口','112.54587','37.858194',20,'滨河东路康乐街口 ')";
		db.execSQL(insert_sql);
		insert_sql="insert into park values(13,'中科院山西煤化所','112.550149','37.858144',32,'康乐街19号')";
		db.execSQL(insert_sql);
		insert_sql="insert into park values(14,'桃园南路文源巷口','112.552212','37.8609',12,'桃园南路文源巷口')";
		db.execSQL(insert_sql);
		insert_sql="insert into park values(15,'新建南路省规划院','112.557915','37.862957',19,'新建南路新泽巷口 ')";
		db.execSQL(insert_sql);
		insert_sql="insert into park values(16,'山西电视台','112.554845','37.864858',32,'迎泽大街366号')";
		db.execSQL(insert_sql);
		insert_sql="insert into park values(17,'省交通厅','112.553691','37.861062',20,'文源巷33号')";
		db.execSQL(insert_sql);
		insert_sql="insert into park values(18,'十五中','112.556099','37.871308',12,'水西关街3号')";
		db.execSQL(insert_sql);
		insert_sql="insert into park values(19,'省水利厅','112.557414','37.872383',45,'新建路45号')";
		db.execSQL(insert_sql);
		insert_sql="insert into park values(20,'省政府','112.569035','37.879632',23,'府东街101号')";
		db.execSQL(insert_sql);
		insert_sql="insert into park values(21,'省实验中学','112.567568','37.881609',20,'解放路府西街口 ')";
		db.execSQL(insert_sql);
		insert_sql="insert into park values(22,'龙潭公园南门','112.562124','37.884571',32,'旱西门街')";
		db.execSQL(insert_sql);
		insert_sql="insert into park values(23,'国贸大厦','112.564747','37.879848',12,'府西街三桥街口')";
		db.execSQL(insert_sql);
		insert_sql="insert into park values(24,'柳北','112.575034','37.879082',19,'府东街柳巷口 ')";
		db.execSQL(insert_sql);
		insert_sql="insert into park values(25,'省中西医结合医院','112.591115','37.885933',32,'建设北路190号')";
		db.execSQL(insert_sql);
		insert_sql="insert into park values(26,'省博物馆','112.585506','37.871143',20,'文庙巷40西 省民俗博物馆')";
		db.execSQL(insert_sql);
		insert_sql="insert into park values(27,'省人才市场','112.578796','37.861806',12,'并州北路')";
		db.execSQL(insert_sql);
		insert_sql="insert into park values(28,'警官高等专科学校','112.530981','37.8332',45,'晋祠路二段27号')";
		db.execSQL(insert_sql);
		insert_sql="insert into park values(29,'省盐业公司','112.567442','37.843639',23,'长治路33号')";
		db.execSQL(insert_sql);
		insert_sql="insert into park values(30,'省电子工业科学研究所','112.558961','37.833121',20,'小店区平阳路 ')";
		db.execSQL(insert_sql);
		insert_sql="insert into park values(31,'财大北校','112.558904','37.845532',32,'新建南路南内环街口')";
		db.execSQL(insert_sql);
		insert_sql="insert into park values(32,'公交集团公司','112.563229','37.851997',12,'双塔西街')";
		db.execSQL(insert_sql);

	}
	@Override
	//当打开数据库时传入的版本号与当前的版本号不同时会调用该方法
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	}
	
	/**
	 * 从数据库中查询数据的函数
	 **/
	//查询所有
	public Cursor selectAll(){
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.query(Table_name, null, null, null, null, null, null);
		return cursor;
	}
	
	//查询停车场名称
	public String selectName(String id){
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.query(Table_name, new String[]{"name"}, "id="+id, null, null, null, null);
		if(cursor.moveToFirst()){
			return cursor.getString(cursor.getColumnIndex("name"));
		}
		else{
			return "bucunzai";
		}
	}
	
	//查询经度
	public String selectLongitude(String id){
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.query(Table_name, new String[]{"longitude"}, "id="+id, null, null, null, null);
		if(cursor.moveToFirst()){
			return cursor.getString(cursor.getColumnIndex("longitude"));
		}
		else{
			return "bucunzai";
		}
	}
	
	//查询纬度
	public String selectLatitude(String id){
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.query(Table_name, new String[]{"latitude"}, "id="+id, null, null, null, null);
		if(cursor.moveToFirst()){
			return cursor.getString(cursor.getColumnIndex("latitude"));
		}
		else{
			return "bucunzai";
		}
	}
	
	//查询剩余停车位
	public String selectLeft_position(String id){
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.query(Table_name, null, "id="+id, null, null, null, null);
		if(cursor.moveToFirst()){
			return cursor.getString(cursor.getColumnIndex("left_postion"));
		}
		else{
			return "bucunzai";
		}
	}
	
	//查询地址
	public String selectAddr(String id){
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.query(Table_name, null, "id="+id, null, null, null, null);
		//Cursor cursor = db.query(Table_name, null, null, null, null, null, null);
		if(cursor.moveToFirst()){
			return cursor.getString(cursor.getColumnIndex("addr"));
		}
		else{
			return "bucunzai";
		}
	}
}