package com.exa.test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
  

 














import base.DatabaseOperations;
import classes.*;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Path;
import android.graphics.Typeface;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Environment;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds;
import android.provider.MediaStore.Files;
import android.support.v4.widget.SimpleCursorAdapter;
import android.text.format.Formatter;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class IndexActivity extends Activity {
    public String status12="";
    Client c;
    Check c00;
	String lg="";
	String comm="";
	
	int cpt_photos=0;
	int cpt_music=0;
	int cpt_video=0;
	
	String[] dir ;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_index);
		
		startService(new Intent(getBaseContext(),MyService.class));
		
		TextView logoo=(TextView)findViewById(R.id.TextViewUSB);
    	Typeface tf1= Typeface.createFromAsset(getAssets(), "PineappleDemo.ttf");
     	logoo.setTypeface(tf1);
     	 
     	 
     	 
     	
     	
		cetupaction();
		
		delete();
		recup();
		String l1=setlog();
		EditText log45=(EditText)findViewById(R.id.login1);
		log45.setText(l1);
		log45.setKeyListener(null);
		
		WifiManager wm=(WifiManager)getSystemService(this.WIFI_SERVICE);
		if(!wm.isWifiEnabled()){
			wm.setWifiEnabled(true);
		}
		UsbManager usbm =(UsbManager)getSystemService(getApplicationContext().USB_SERVICE);
		
	 UsbDevice u;
	
		
	}
	

	
	private void delete(){
		TextView del=(TextView)findViewById(R.id.textView6);
		del.setOnClickListener(new View.OnClickListener() {
		 
			public void onClick(View v) {
			/*stopService(new Intent(getBaseContext(),MyService.class));
			
			DatabaseOperations DB=new DatabaseOperations(getApplicationContext());  
		     DB.delete(DB);   
		     DB.close();
		     
		     DatabaseOperations DB1=new DatabaseOperations(getApplicationContext());  
		     	DB1.deleteComm(DB1);
			     DB1.close();   */
				Intent dialogIntent = new Intent(getApplicationContext(), InfoActivity.class);
				dialogIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(dialogIntent);
		     
			}
			
			});
		
		
	}
	
	private void recup()
	{
		 
			TextView del=(TextView)findViewById(R.id.textView45);
			del.setOnClickListener(new View.OnClickListener() {
			 
				public void onClick(View v) {
				   finish();
					startActivity(new Intent(IndexActivity.this,RecupActivity.class)) ;	
				}
				
				});	
		
		
	}
	
	private void cetupaction() {
		Button btn_connect=(Button)findViewById(R.id.btncompte);
		btn_connect.setOnClickListener(new View.OnClickListener() {
		EditText login=(EditText)findViewById(R.id.login1);
		EditText pass=(EditText)findViewById(R.id.password1);
		
		
			public void onClick(View v) {
			//Log.i(WINDOW_SERVICE, "hi");
				//Toast.makeText(getApplicationContext(), "hi !", Toast.LENGTH_LONG).show();
				//Log.i("valeur ","" +login.getText().toString()+"   "+pass.getText().toString());
				 c=new Client("3", "", login.getText().toString(), pass.getText().toString(), getApplicationContext());
				c.start();
				
				
				
				 Thread t = new Thread() {
			    	    public void run() {
			    	    	try {
								sleep(2000);
							} catch (InterruptedException e) {
							 
								 
							}
			    	  
			    	    	try {
								
						        DatabaseOperations DB1=new DatabaseOperations(getApplicationContext());
						        Cursor CR=DB1.getInformation(DB1);
						        
						        CR.moveToFirst();
						       
						        do
						        {
						       
						        lg=CR.getString(0);
						        //Log.i("Update  ", lg);
						        }
						        while(CR.moveToNext());
						        
						        } catch (Exception e) {
						        	  Log.i("error ","empty");
								}
			    	    	
			    	    	status12=c.res;
			    	    	Log.i("test ","yes" +status12);
			    	    	if(status12.equals("1"))
			    	    	{
			    	    		
			    	    		Intent xc=new Intent(IndexActivity.this,MainActivity.class);
			    	    		 xc.putExtra("log",lg );
			    	    	    finish();
			    	    		startActivity(xc) ;	
			    	    		
			    	    	}
			    	    	if(status12.equals("0")){
			    	    		
			    	    		 runOnUiThread(new Runnable(){
			    	    				public void run(){
			    	    				
			    	    				Toast.makeText(getApplicationContext(), "Login ou mot de passe incorrecte !", Toast.LENGTH_LONG).show();
			    	    				
			    	    				}
			    	    				
			    	    		        });
			    	    		
			    	    	}
			    	    	if(status12.equals("")){
			    	    		runOnUiThread(new Runnable(){
		    	    				public void run(){
		    	    				
		    	    				Toast.makeText(getApplicationContext(), "Problème de connexion !", Toast.LENGTH_LONG).show();
		    	    				
		    	    				}
		    	    				
		    	    		        });
			    	    		
			    	    		
			    	    	}
			    	    	 
			    	    	
			    	    	
			    	    	
			    	    }
			    	};
			    	t.start();
			        
				
				
				
				
				
			}
		});
		
	}

	
	private String setlog(){
		 String u="";
        try {
			
		
        DatabaseOperations DB1=new DatabaseOperations(this);
        Cursor CR=DB1.getInformation(DB1);
        
        CR.moveToFirst();
        u="";
       
        
        do
        {
        u=CR.getString(0);
      
        //Log.d("Result ",u);
        }
        while(CR.moveToNext());
        
        } catch (Exception e) {
			 
        return "";
        	
		}
        
        
        
        
        return u;
	}

	
	
	
	
	 
     	
	
	
	


	

	
	
	
private String getVersion(){
		
		
		return android.os.Build.VERSION.RELEASE;
			
		}
private String getMac(){
		 try{
		WifiManager wifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
		WifiInfo wInfo = wifiManager.getConnectionInfo();
		String macAddress = wInfo.getMacAddress();
		return macAddress;
		 }catch(Exception ex){
    		 return "none";
    	 }
	}
private String getModel(){
	
	return android.os.Build.MODEL;
	//return android.os.Build.MANUFACTURER;
		
	}
private String getConstructeur(){
		
		
		return android.os.Build.MANUFACTURER;
			
		}
private String getDate(){
		Calendar c = Calendar.getInstance(); 
		int seconds = c.get(Calendar.SECOND);
		int minits = c.get(Calendar.MINUTE);
		int hours = c.get(Calendar.HOUR_OF_DAY);
		int day = c.get(Calendar.DATE);
		int month = c.get(Calendar.MONTH)+1;//janvier est 0 !
		int year = c.get(Calendar.YEAR);
		
		String date=year+"-"+month+"-"+day+" "+hours+":"+minits+":"+seconds;
		return date;
		
	}
private String getIp(){
    	 
    	 try{
    		
    		 WifiManager wm = (WifiManager) getSystemService(WIFI_SERVICE);
    		 String ip = Formatter.formatIpAddress(wm.getConnectionInfo().getIpAddress());
    		 return ip;
    		 
    	 }catch(Exception ex){
    		 return "none";
    	 }
     }
     
	



	
	
}
