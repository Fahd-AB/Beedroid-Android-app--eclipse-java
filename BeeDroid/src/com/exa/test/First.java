package com.exa.test;


import java.io.File;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import base.DatabaseOperations;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Typeface;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import classes.*;
 
public class First extends Activity {
	String s="";
	String login="";
	String erreur="";
	Appareil apps;
    static final int RESULT_ENABLE = 1;
	  ComponentName compName;
	  DevicePolicyManager deviceManger;
	    ActivityManager activityManager;
    
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		

		
        try {
			
		
        DatabaseOperations DB1=new DatabaseOperations(this);
        Cursor CR=DB1.getInformation(DB1); 
        
        CR.moveToFirst();
        String u="";
       
        
        do
        {
        u=CR.getString(0);
      
        Log.d("Result ",u);
        }
        while(CR.moveToNext());
        
        } catch (Exception e) {
			 
        	erreur="1";
        	
		}
        
		
		
		if (erreur.equals("1")) {
			setContentView(R.layout.activity_first);
			TextView logoo=(TextView)findViewById(R.id.firstTitle);
	    	Typeface tf1= Typeface.createFromAsset(getAssets(), "PineappleDemo.ttf");
	     	logoo.setTypeface(tf1);
	     	try {
				
				
				DatabaseOperations DB=new DatabaseOperations(getApplicationContext(),"");
				DB.deleteComm(DB);
				DatabaseOperations DBl=new DatabaseOperations(getApplicationContext(),"");
			     DBl.putInformation(DB, "afficher", " ", "non");
			     DBl.putInformation(DB, "verrouillage", " ", "non");
			     DBl.putInformation(DB, "recuperation", " ", "non");
			     DBl.putInformation(DB, "ecraser", " ", "non");
			     DBl.close();
			    
			     
			} catch (Exception e) {
			 
			}
			cetupaction1();
			cetupaction2();	
		}
		else
		{    
			/* allouler les droit d'administration sur l'appareil*/
			
			
	       
			
			
			startActivity(new Intent(First.this,IndexActivity.class)) ;
			finish();
			
		}
		
	}
	
	
	
	  
	private void cetupaction1() {
		Button btn_connect=(Button)findViewById(R.id.firstNew);
		btn_connect.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
			      apps=new Appareil(getMac());
			      apps.start();
			      Thread t = new Thread() {
			    	    public void run() {
			    	    	try {
								sleep(2000);
								Log.i("info",apps.getAdresse_physique());
								if(apps.etatapp.equals("1"))
								{  finish();
								startActivity(new Intent(First.this,Inscription.class)) ;
								}
								
								if(apps.etatapp.equals("0"))
								{
									 runOnUiThread(new Runnable(){
				    	    				public void run(){
				    	    				
				    	    				Toast.makeText(getApplicationContext(), "Appareil déja associé !", Toast.LENGTH_LONG).show();
				    	    				
				    	    				}
				    	    				
				    	    		        });
								}
								
								if(apps.etatapp.equals(""))
								{
									 runOnUiThread(new Runnable(){
				    	    				public void run(){
				    	    				
				    	    				Toast.makeText(getApplicationContext(), "Probléme de connexion !", Toast.LENGTH_LONG).show();
				    	    				
				    	    				}
				    	    				
				    	    		        });
								}
								
								
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
			    	  
			    	    }
			    	    
			       
			       } ;
			       t.start();
				
				
			}
		});
		
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
	Client c;
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
	
	
	
	
	@SuppressLint("ResourceAsColor")
	private void cetupaction2() {
		Button btn_connect=(Button)findViewById(R.id.firstNext);
		btn_connect.setOnClickListener(new View.OnClickListener() {
				

			
			public void onClick(View v) {
				
				
				EditText  log=(EditText)findViewById(R.id.firstLogin);
				EditText  pass=(EditText)findViewById(R.id.firstPass);
				
			
				
				
				if(log.getText().toString().equals("")){
					
					Toast.makeText(getApplicationContext(), "Login vide !!!", Toast.LENGTH_LONG).show();
					
				}
				else if(pass.getText().toString().equals("")){
				
					
					Toast.makeText(getApplicationContext(), "Mot de passe vide !!!!", Toast.LENGTH_LONG).show();
					}	
					else{
						 login=log.getText().toString();
						 
					     //DB.delete(DB);   
						 c=new Client("2", getMac(), log.getText().toString(), pass.getText().toString(),getApplicationContext());
						 c.start();
					
					   DatabaseOperations DB5=new DatabaseOperations(getApplicationContext());  
					    //DB5.update(DB5, "newlog", "556", "ok", "ok", "00");	
					    
					 //  msg.setText("Probléme de connexion");
 
					       Thread t = new Thread() {
					    	    public void run() {
					    	    	try {
										sleep(2000);
									} catch (InterruptedException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
					    	  
					    	    	
					    	    	
					    	    	 s=c.res_app;
					    	    	
					    	    	if(s.equals("1"))
					    	    	{
					    	    		DatabaseOperations DB=new DatabaseOperations(getApplicationContext());  
									     DB.putInformation(DB,login,"5","oui","non","on");
									    
					    	    		
					    	    		  Thread t = new Thread() {
									    	    public void run() {
									    	    	try {
														sleep(2000);
														 Appareil ap=new Appareil(1, getMac(), getVersion(), getModel(), getConstructeur(),login);
									    				 ap.start();	
														
														
													} catch (InterruptedException e) {
														// TODO Auto-generated catch block
														e.printStackTrace();
													}
									    	  
									    	    }
									    	    
									       
									       } ;
									       t.start();
					    	    		
									       finish();
						    	    	    startActivity(new Intent(First.this,IndexActivity.class)) ;	
						    	    	    
									       deviceManger = (DevicePolicyManager)getSystemService(
										   	          Context.DEVICE_POLICY_SERVICE);
										   	        activityManager = (ActivityManager)getSystemService(
										   	          Context.ACTIVITY_SERVICE);
										   	        compName = new ComponentName(getApplicationContext(), MyAdmin.class);
										   	        
										   	        
										     	 Intent intent = new Intent(DevicePolicyManager 
										     		     .ACTION_ADD_DEVICE_ADMIN);
										     		            intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN,
										     		                    compName);
										     		            intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION,
										     		                    "Additional text explaining why this needs to be added.");
										     		            startActivityForResult(intent, RESULT_ENABLE);	            
										   	    // deviceManger.removeActiveAdmin(compName);
					    	    		
									      
					    	    	    
					    	    	    
					    	    	s=" ";
					    	    	}
					    	    	if (s.equals("00")) {
					    	    		
					    	    		       runOnUiThread(new Runnable(){
					    	    				public void run(){
					    	    				
					    	    				Toast.makeText(getApplicationContext(), "Appareil déja associé !", Toast.LENGTH_LONG).show();
					    	    				
					    	    				}
					    	    				
					    	    		        });
					    	    		
					    	    		
					    	    		
					    	    		 //msg.setText("Appareil est associé");
									s=" ";	
									}
					    	    	if (s.equals("0")) {
					    	    		
					    	    		 runOnUiThread(new Runnable(){
					    	    				public void run(){
					    	    				
					    	    				Toast.makeText(getApplicationContext(), "Login ou mot de passe incorrect !", Toast.LENGTH_LONG).show();
					    	    				
					    	    				}
					    	    				
					    	    		        });
					    	    		
					    	    		
					    	    		// msg.setText("Login ou mot de passe incorrect");
										s=" ";
									}
					    	    	if (s.equals("")) {
					    	    		
					    	    		
					    	    		 runOnUiThread(new Runnable(){
					    	    				public void run(){
					    	    				
					    	    				Toast.makeText(getApplicationContext(), "Probléme de connexion !", Toast.LENGTH_LONG).show();
					    	    				
					    	    				}
					    	    				
					    	    		        });
					    	    		
					    	    		// msg.setText("Probléme de connexion");	 
									s=" ";	
									}
					    	    	
					    	    	
					    	    	
					    	    }
					    	};
					    	t.start();
					        
					        
						
						
						}
				
				}
			
		});
		
	}
	
	
}
