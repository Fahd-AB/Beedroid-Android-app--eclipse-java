package base;

import base.TableData.TableInfo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseOperations extends SQLiteOpenHelper {
 public static final int database_version=1;
 
 public String CREATE_QUERY="CREATE TABLE IF NOT EXISTS config (login TEXT, delai TEXT ,usb TEXT , blue TEXT ,status TEXT  );";
 public String CREATE_QUERY1="CREATE TABLE IF NOT EXISTS comm (type TEXT,commande TEXT ,etat TEXT );";
	public DatabaseOperations(Context context) {
		super(context, "config", null,database_version );
  this.onCreate(getWritableDatabase());
		
	
	}
	
	public DatabaseOperations(Context context,String s) {
		super(context, "comm", null,database_version );
  this.onCreate(getWritableDatabase());
		 
	 
	
	}	
	 
	
	

	@Override
	public void onCreate(SQLiteDatabase sdb) {
		// TODO Auto-generated method stub
	
		sdb.execSQL(CREATE_QUERY1);	
	sdb.execSQL(CREATE_QUERY);	

		
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub

	}
	

	
public void putInformation(DatabaseOperations dop,String login ,String delai ,String usb ,String blue ,String status){
	
	
SQLiteDatabase SQ=dop.getWritableDatabase();
ContentValues cv=new ContentValues();
cv.put("login", login);
cv.put("delai", delai);
cv.put("usb", usb);
cv.put("blue", blue);
cv.put("status", status);
long k=SQ.insert("config", null, cv);
Log.d("databse operation","one row inserted  "+k);
	
}	


public void putInformation(DatabaseOperations dop,String type ,String commande ,String etat){
	
	
SQLiteDatabase SQ=dop.getWritableDatabase();
ContentValues cv=new ContentValues();
cv.put("type", type);
cv.put("commande",commande);
cv.put("etat",etat);
 
long k=SQ.insert("comm", null, cv);
Log.d("databse operation","one row inserted comm "+k);
	
}	


public Cursor getInformation(DatabaseOperations dop)
{
	SQLiteDatabase SQ=dop.getReadableDatabase();
	String[] columns={"login","delai","usb","blue","status"};
	Cursor CR=SQ.query("config", columns, null, null,null, null,null);
	return CR;

	
}
public Cursor getInformationComm(DatabaseOperations dop)
{
	SQLiteDatabase SQ=dop.getReadableDatabase();
	String[] columns={"type","commande","etat"};
	Cursor CR=SQ.query("comm", columns, null, null,null, null,null);
	return CR;

	
}

public void delete(DatabaseOperations DOP )
{
	
	
	SQLiteDatabase SQ=DOP.getWritableDatabase();
	SQ.execSQL("delete from config");
	
	
	
}

public void deleteComm(DatabaseOperations DOP )
{
	
	
	SQLiteDatabase SQ=DOP.getWritableDatabase();
	SQ.execSQL("delete from comm");
	
	
	
}
public void update (DatabaseOperations dop,String login ,String delai ,String usb ,String blue ,String status )
{
SQLiteDatabase SQ = dop.getWritableDatabase();
ContentValues cv=new ContentValues();	
cv.put("login", login);
cv.put("delai", delai);
cv.put("usb", usb);
cv.put("blue", blue);
cv.put("status", status);
SQ.update("config", cv, null, null);



}	
public void update_usb (DatabaseOperations dop,String usb  )
{
SQLiteDatabase SQ = dop.getWritableDatabase();
ContentValues cv=new ContentValues();	

cv.put("usb", usb);

SQ.update("config", cv, null, null);




}	

public void updateStatus (DatabaseOperations dop,String status )
{
SQLiteDatabase SQ = dop.getWritableDatabase();
ContentValues cv=new ContentValues();	
 
cv.put("status",status);
SQ.update("config", cv, null, null);




}	















public void update_bluetooth (DatabaseOperations dop,String blue  )
{
SQLiteDatabase SQ = dop.getWritableDatabase();
ContentValues cv=new ContentValues();	

cv.put("blue", blue);

SQ.update("config", cv, null, null);



}	
public void update_delai (DatabaseOperations dop,String delai  )
{
SQLiteDatabase SQ = dop.getWritableDatabase();
ContentValues cv=new ContentValues();	

cv.put("delai", delai);

SQ.update("config", cv, null, null);



}	


public void updateComm (DatabaseOperations dop,String type ,String commande ,String etat ,String condition )
{
SQLiteDatabase SQ = dop.getWritableDatabase();
ContentValues cv=new ContentValues();	
cv.put("type", type);
cv.put("commande", commande);
cv.put("etat",etat);
SQ.update("comm", cv, "type"+" LIKE '"+condition+"'", null);




}	




}
