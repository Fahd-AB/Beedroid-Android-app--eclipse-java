package com.exa.test;


import base.DatabaseOperations;
import classes.Appareil;
import classes.Client;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.text.format.Formatter;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class Inscription1 extends Activity {
	String log="";
	 static final int RESULT_ENABLE = 1;
	  ComponentName compName;
	  DevicePolicyManager deviceManger;
	    ActivityManager activityManager;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_inscription1);
		String log1=getIntent().getExtras().getString("log");
		TextView  logintext=(TextView)findViewById(R.id.username);
		logintext.setText(log1);
		 cetupaction();
	}

	
	@SuppressLint("ResourceAsColor") private void cetupaction() {
		Button btn_connect=(Button)findViewById(R.id.btncompte);
		btn_connect.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
			
				
				EditText  mdp=(EditText)findViewById(R.id.login1);
				EditText  rep1=(EditText)findViewById(R.id.EditText06);
				EditText  rep3=(EditText)findViewById(R.id.EditText03);
				EditText  rep2=(EditText)findViewById(R.id.EditText04);
				
				
			
				if(mdp.getText().toString().equals("")){
					
					Toast.makeText(getApplicationContext(), "nom non valide", Toast.LENGTH_LONG).show();
					
				}
				
				else if(rep1.getText().toString().equals("")){
				
					Toast.makeText(getApplicationContext(), "rep1 non valide", Toast.LENGTH_LONG).show();	
				
				}
				else if(rep2.getText().toString().equals("")){
				
					Toast.makeText(getApplicationContext(), "rep2 non valide", Toast.LENGTH_LONG).show();	
				
				}
				else if(rep3.getText().toString().equals("")){
				
					Toast.makeText(getApplicationContext(), "rep3 non valide", Toast.LENGTH_LONG).show();	
				
				}
				else{
					String name=getIntent().getExtras().getString("name");
					String mail=getIntent().getExtras().getString("mail");
					String tel=getIntent().getExtras().getString("tel");
					String adr=getIntent().getExtras().getString("adr");
					 log=getIntent().getExtras().getString("log");
					 
					 DatabaseOperations DB=new DatabaseOperations(getApplicationContext());  
				     DB.putInformation(DB,log,"10","oui","non","on"); 
				     
				     Client c=new Client(1, name, mail,tel,adr,log,mdp.getText().toString(), rep1.getText().toString(), rep2.getText().toString(), rep3.getText().toString());
					 c.start();	
						

					
				    
    	    		
    	    		  Thread t1 = new Thread() {
				    	    public void run() {
				    	    	try {
									sleep(2000);
									 Appareil ap=new Appareil(1, getMac(), getVersion(), getModel(), getConstructeur(),log);
				    				 ap.start();	
									
									
								} catch (InterruptedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
				    	  
				    	    }
				    	    
				       
				       } ;
				       t1.start();
    	    		
				       
				finish();
				startActivity(new Intent(Inscription1.this,IndexActivity.class)) ;
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
				
				}
				
				
				
				
				
			}
		});
		
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
 
	
}
private String getConstructeur(){
	
	
	return android.os.Build.MANUFACTURER;
		
	}
	
	
private String getVersion(){
	
	
	return android.os.Build.VERSION.RELEASE;
		
	}
	
		
	
	
	
	
	
}
