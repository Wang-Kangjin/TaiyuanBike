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
	* �����ݿ��״δ���ʱִ�и÷�����һ�㽫������ȳ�ʼ���������ڸ÷�����ִ��.
	* ��дonCreate����������execSQL����������
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
		String insert_sql="insert into park values(1,'����У��','112.5299','37.863444',2,'�¿�Ժ·ӭ��ֿ�')";
		db.execSQL(insert_sql);
		insert_sql="insert into park values(2,'������У��','112.529644','37.860962',23,'�¿�Ժ·�ƴ��Ͻֿ�')";
		db.execSQL(insert_sql);
		insert_sql="insert into park values(3,'����������','112.536134','37.865805',20,'ǧ�山·���������� ')";
		db.execSQL(insert_sql);
		insert_sql="insert into park values(4,'������·ӭ������ֱ���','112.547242','37.866075',32,'������·ӭ������ֱ���')";
		db.execSQL(insert_sql);
		insert_sql="insert into park values(5,'ɽ������Ժ','112.539404','37.871908',12,'������·ʡ����Ժ')";
		db.execSQL(insert_sql);
		insert_sql="insert into park values(6,'������','112.53369','37.857475',19,'�����ִ���·�� ')";
		db.execSQL(insert_sql);
		insert_sql="insert into park values(7,'���غ�ǧ���','112.525785','37.856848',32,'ǧ����·�����ֿ�')";
		db.execSQL(insert_sql);
		insert_sql="insert into park values(8,'ɽ���Ƽ���չ����','112.545929','37.871228',20,'���Ӷ�·����22��')";
		db.execSQL(insert_sql);
		insert_sql="insert into park values(9,'���Ӷ�·ˮ�����Ͻֿ�','112.545764','37.868482',12,'���Ӷ�·ˮ�����Ͻֿ�')";
		db.execSQL(insert_sql);
		insert_sql="insert into park values(10,'ʡ�ϸɲ������','112.546798','37.867193',45,'���ɱ�·5��')";
		db.execSQL(insert_sql);
		insert_sql="insert into park values(11,'����·��԰��·��һ���','112.5472','37.863379',23,'����·��԰��·��һ���')";
		db.execSQL(insert_sql);
		insert_sql="insert into park values(12,'���Ӷ�·���ֽֿ�','112.54587','37.858194',20,'���Ӷ�·���ֽֿ� ')";
		db.execSQL(insert_sql);
		insert_sql="insert into park values(13,'�п�Ժɽ��ú����','112.550149','37.858144',32,'���ֽ�19��')";
		db.execSQL(insert_sql);
		insert_sql="insert into park values(14,'��԰��·��Դ���','112.552212','37.8609',12,'��԰��·��Դ���')";
		db.execSQL(insert_sql);
		insert_sql="insert into park values(15,'�½���·ʡ�滮Ժ','112.557915','37.862957',19,'�½���·������� ')";
		db.execSQL(insert_sql);
		insert_sql="insert into park values(16,'ɽ������̨','112.554845','37.864858',32,'ӭ����366��')";
		db.execSQL(insert_sql);
		insert_sql="insert into park values(17,'ʡ��ͨ��','112.553691','37.861062',20,'��Դ��33��')";
		db.execSQL(insert_sql);
		insert_sql="insert into park values(18,'ʮ����','112.556099','37.871308',12,'ˮ���ؽ�3��')";
		db.execSQL(insert_sql);
		insert_sql="insert into park values(19,'ʡˮ����','112.557414','37.872383',45,'�½�·45��')";
		db.execSQL(insert_sql);
		insert_sql="insert into park values(20,'ʡ����','112.569035','37.879632',23,'������101��')";
		db.execSQL(insert_sql);
		insert_sql="insert into park values(21,'ʡʵ����ѧ','112.567568','37.881609',20,'���·�����ֿ� ')";
		db.execSQL(insert_sql);
		insert_sql="insert into park values(22,'��̶��԰����','112.562124','37.884571',32,'�����Ž�')";
		db.execSQL(insert_sql);
		insert_sql="insert into park values(23,'��ó����','112.564747','37.879848',12,'���������Žֿ�')";
		db.execSQL(insert_sql);
		insert_sql="insert into park values(24,'����','112.575034','37.879082',19,'����������� ')";
		db.execSQL(insert_sql);
		insert_sql="insert into park values(25,'ʡ����ҽ���ҽԺ','112.591115','37.885933',32,'���豱·190��')";
		db.execSQL(insert_sql);
		insert_sql="insert into park values(26,'ʡ�����','112.585506','37.871143',20,'������40�� ʡ���ײ����')";
		db.execSQL(insert_sql);
		insert_sql="insert into park values(27,'ʡ�˲��г�','112.578796','37.861806',12,'���ݱ�·')";
		db.execSQL(insert_sql);
		insert_sql="insert into park values(28,'���ٸߵ�ר��ѧУ','112.530981','37.8332',45,'����·����27��')";
		db.execSQL(insert_sql);
		insert_sql="insert into park values(29,'ʡ��ҵ��˾','112.567442','37.843639',23,'����·33��')";
		db.execSQL(insert_sql);
		insert_sql="insert into park values(30,'ʡ���ӹ�ҵ��ѧ�о���','112.558961','37.833121',20,'С����ƽ��· ')";
		db.execSQL(insert_sql);
		insert_sql="insert into park values(31,'�ƴ�У','112.558904','37.845532',32,'�½���·���ڻ��ֿ�')";
		db.execSQL(insert_sql);
		insert_sql="insert into park values(32,'�������Ź�˾','112.563229','37.851997',12,'˫������')";
		db.execSQL(insert_sql);

	}
	@Override
	//�������ݿ�ʱ����İ汾���뵱ǰ�İ汾�Ų�ͬʱ����ø÷���
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	}
	
	/**
	 * �����ݿ��в�ѯ���ݵĺ���
	 **/
	//��ѯ����
	public Cursor selectAll(){
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.query(Table_name, null, null, null, null, null, null);
		return cursor;
	}
	
	//��ѯͣ��������
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
	
	//��ѯ����
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
	
	//��ѯγ��
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
	
	//��ѯʣ��ͣ��λ
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
	
	//��ѯ��ַ
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