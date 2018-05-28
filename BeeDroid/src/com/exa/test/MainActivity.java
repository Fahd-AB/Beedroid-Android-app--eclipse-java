package com.exa.test;



import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.format.Formatter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.exa.test.thread_envoi;
 
import com.google.android.gms.internal.ki;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.admin.DevicePolicyManager;
import android.app.ActivityManager;
import android.app.Dialog;
import android.app.FragmentManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Typeface;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import base.DatabaseOperations;

import java.io.BufferedReader;
import java.util.Calendar;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import classes.*;
public class MainActivity extends FragmentActivity {

		GoogleMap gMap;
		double latitude;
	    double longitude;
	    BufferedReader Buffer=null;
	    GPSTracker gps;
	  	double last_latitude =0;
		double last_longitude =0;
		
	   
	
	   static final int RESULT_ENABLE = 1;
	   ComponentName compName;
	   DevicePolicyManager deviceManger;
	    ActivityManager activityManager;
	   
	   
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	
		
		
		
		
		
		
		setusername();
		connectivite();
		modifier_compte();
		parametre();
		
		
		 MapFragment f=(MapFragment)getFragmentManager().findFragmentById(R.id.map);
	        gMap=f.getMap();
		
	    	String ipadress=getIp();
	    	String macadress=getMac();
	    	String infos=getModel();
	    	String date= getDate();
	    	String constructeur=getConstructeur();
	    	Log.e("log_tag", "Mac :" +macadress);
	    	Log.e("log_tag", "Modele :" +infos);
	    	Log.e("log_tag", "Date :" +date);
	    	
	    	turnGPSOn();
	    	setupmap();
	    	dec();
	    	info();
	    	
	    	
	    	
	    	String lang=longitude+"";
		    String lat=latitude+"";
	     	
	     
	     	
	     	TextView logoo=(TextView)findViewById(R.id.TextViewUSB);
	    	Typeface tf1= Typeface.createFromAsset(getAssets(), "PineappleDemo.ttf");
	     	logoo.setTypeface(tf1);
	     	
	    
	   
	     	
	     	
	     	

	     	 
	}
	
	

 
	
	
	
	
	
	public void customDialog()
	
	 {   
		
	   FragmentManager manager=getFragmentManager();
		MyDialog myDialog= new MyDialog();
		myDialog.show(manager, "MyDialog");
		
		
		
		
		
		
	 }//end of custom dialog function
	
	
	private void connectivite(){
		
		Button btn_connectivite=(Button)findViewById(R.id.btnconnectivite);
		btn_connectivite.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				 customDialog();
			}
		});
		
		
		
	}
	
	
	

	private void parametre(){
		
		Button btn_parametre=(Button)findViewById(R.id.btnsetting);
		btn_parametre.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				FragmentManager manager=getFragmentManager();
				final MyDialog1 myDialog= new MyDialog1();
				myDialog.setcontext(MainActivity.this);
				
				myDialog.show(manager, "MyDialog1");
				  Thread t = new Thread() {
			    	    public void run() {
			    	    	 
								try {
									sleep(2000);
									
									
									if(myDialog.lang.equals("vide") || myDialog.lat.equals("vide"))
									{
										try {
											 run();	
										} catch (Exception e) {
											// TODO: handle exception
											e.printStackTrace();
										}
									
									}
									else
									{
                                          
										if(myDialog.lang.equals(" ")||(myDialog.lat.equals(" ") )  )
										{
														runOnUiThread(new Runnable(){
								    	    				public void run(){
								    	    					//Toast.makeText(getApplicationContext(), "Identifiant apppareil invalide", Toast.LENGTH_SHORT).show();	
				                                              
								    	    				}
								    	    				
								    	    		        });
												
											
										}
										else
										{
										
														runOnUiThread(new Runnable(){
								    	    				public void run(){
								    	    					//Toast.makeText(getApplicationContext(), myDialog.lang+"  "+myDialog.lat, Toast.LENGTH_LONG).show();
				                                               add_marker( Double.parseDouble(myDialog.lang),Double.parseDouble(myDialog.lat));
								    	    				
								    	    				
								    	    				}
								    	    				
								    	    		        });
										}
									
										
										
										
										
									}
								} catch (InterruptedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								 
			    	    	}};
				 t.start();
				
				
				 /****************************************************************************************/
				 Thread t1 = new Thread() {
			    	    public void run() {
			    	    	 
								try {
									sleep(2000);
									
									if (myDialog.etat.equals(" ") ||myDialog.etat.equals("off1")||myDialog.etat.equals("on1")) {
										run();
										
									}
									 
										if(myDialog.etat.equals("on"))
										{
											allouer();
											
											Log.i("droit","on");
										}
										
										if (myDialog.etat.equals("off")) {
											retirer();
											Log.i("droit","off");
										
										
									}
									
									
									
								 
								} catch (InterruptedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								 
			    	    	}};
				 t1.start();
				
				
				
			 
				 
				 
				 
				 
				 
				 
				 
				 
				 
				 
				 
				 
				 
				 
				 
				 
			 	 
				
			}
		});
		
		
		
	}
	
	
	
	
	
	
	
	
	
	
	
 
	
private void modifier_compte(){
	
	 
	
	Button btn_connect=(Button)findViewById(R.id.btncompte);
	btn_connect.setOnClickListener(new View.OnClickListener() {
		
		public void onClick(View v) {
		 
			startActivity(new Intent(MainActivity.this,Modifier.class)) ;
		}
	});
	
	
}
	
	
	

	private void setusername(){
		TextView user=(TextView)findViewById(R.id.username);
		
		
		try {
			
			
	        DatabaseOperations DB1=new DatabaseOperations(this);
	        Cursor CR=DB1.getInformation(DB1); 
	        
	        CR.moveToFirst();
	        String u="";
	       
	        
	        do
	        {
	        u=CR.getString(0);
	      
	        //Log.d("Result ",u);
	        }
	        while(CR.moveToNext());
	        
	        user.setText(u);
	        
	        } catch (Exception e) {
				 
	        	 user.setText("");
	        	
			}
	        
	}
	
private void dec()
{

	Button dec=(Button)findViewById(R.id.deconnexion);
	dec.setOnClickListener(new View.OnClickListener() {
	 
		public void onClick(View v) {
			finish();
			startActivity(new Intent(MainActivity.this,IndexActivity.class)) ;	
		
		}
		
		});	
	




}
	
	
	
	
	
	
	
	 public void setupmap(){
   	  
	        MapFragment f=(MapFragment)getFragmentManager().findFragmentById(R.id.map);
	        gMap=f.getMap();
	        //gMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
	        gMap.setMyLocationEnabled(true);
	        gMap.getUiSettings().setZoomControlsEnabled(true);
	        
	        
						mise_a_jour();
				
	    	
	    	
	   }
	    
	    public void mise_a_jour(){
	    	 // gMap.setMyLocationEnabled(true);
	    	try{
	    		 String provider;
	    		   LocationManager lm;
	    		   
	    	lm=(LocationManager)getSystemService(LOCATION_SERVICE);
	    	Criteria c=new Criteria();
	    	provider =lm.getBestProvider(c, true);
	       
	    	Location MyLocation=lm.getLastKnownLocation(provider);
	    	
	    		
	     		latitude=MyLocation.getLatitude();
	     	    longitude=MyLocation.getLongitude();
	     	 
	     	LatLng ll=new LatLng(latitude,longitude);
	       	gMap.moveCamera(CameraUpdateFactory.newLatLng(ll));
	       	gMap.animateCamera(CameraUpdateFactory.zoomTo(15));
	       	//Log.i("map", "camera moved");
	       //	
	     
	       	
	     	}catch(Exception e){
	     	
	     		latitude=36.8064948;
	     		longitude=10.1815316;
	     	
	     	}
	    	
	   
	    }

	    
	    private void add_marker(double lat1,double long1 ){
	    	gMap.addMarker(new MarkerOptions().position(new LatLng(lat1,long1)).title("Vous ami est ici"));
	    }

		@Override
		protected void onResume() {
			
			super.onResume();
			
			
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
	
	
	private void turnGPSOn(){
		Intent intent=new Intent("android.location.GPS_ENABLED_CHANGE");
		intent.putExtra("enabled", true);
		sendBroadcast(intent);
	}

	
 
	
	
	
	public void  Droit() {
		
		  deviceManger = (DevicePolicyManager)getSystemService(
	   	          Context.DEVICE_POLICY_SERVICE);
	   	        activityManager = (ActivityManager)getSystemService(
	   	          Context.ACTIVITY_SERVICE);
	   	        compName = new ComponentName(getApplicationContext(), MyAdmin.class);
		
		
	}
	
	
	
	public void allouer()
	{    Droit();
				boolean isgranted=false;
			    try {
			 	    isgranted=deviceManger.isAdminActive(compName);
				} catch (Exception e) {
					// TODO: handle exception
				}	
	    if(!isgranted){
	    	 Intent intent = new Intent(DevicePolicyManager 
	    		     .ACTION_ADD_DEVICE_ADMIN);
	    		            intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN,
	    		                    compName);
	    		            intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION,
	    		                    "Additional text explaining why this needs to be added.");
	    		
	    		            startActivityForResult(intent, RESULT_ENABLE);	
	    		         
	    		        	DatabaseOperations DB1=new DatabaseOperations(getApplicationContext());
	    		       	 DB1.updateStatus(DB1, "on");
	    		       	DB1.close();	    	        
	    }
		
		            
		  
		
		
		}
		
	public void retirer()
	
	{     Droit();
	boolean isgranted=false;
       try {
    	    isgranted=deviceManger.isAdminActive(compName);
	} catch (Exception e) {
		// TODO: handle exception
	}	
		
	if(isgranted){
		deviceManger.removeActiveAdmin(compName);
		 
		 DatabaseOperations DB1=new DatabaseOperations(getApplicationContext());
		 DB1.updateStatus(DB1, "off");
		DB1.close();
		
	}
	}

	private void info(){
		TextView del=(TextView)findViewById(R.id.btninfo);
		del.setOnClickListener(new View.OnClickListener() {
		 
			public void onClick(View v) {
			 
				Intent dialogIntent = new Intent(getApplicationContext(), InfoActivity.class);
				dialogIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(dialogIntent);
		     
			}
			
			});
		
		
	}	
	
	

	
}
