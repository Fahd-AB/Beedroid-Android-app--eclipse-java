package com.exa.test;

import java.util.Random;

import classes.*;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.text.format.Formatter;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Inscription extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_inscription);
		
		
		
		
		
		
		cetupaction();
	}
	
	
	@SuppressLint("ResourceAsColor") private void cetupaction() {
		Button btn_connect=(Button)findViewById(R.id.btncompte);
		btn_connect.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
			
				EditText  name=(EditText)findViewById(R.id.EditText05);
				EditText  tel=(EditText)findViewById(R.id.EditText01);
				EditText  mail=(EditText)findViewById(R.id.EditText02);
				EditText  adr=(EditText)findViewById(R.id.password1);
			
				
				
			
				if(name.getText().toString().equals("")){
				
					Toast.makeText(getApplicationContext(), "nom non valide", Toast.LENGTH_LONG).show();
					
				}
				else if(tel.getText().toString().equals("")){
				
					Toast.makeText(getApplicationContext(), "tel non valide", Toast.LENGTH_LONG).show();	
				
				}
				else if(mail.getText().toString().equals("")||(mail.getText().toString().contains("@")==false)||(mail.getText().toString().contains(".")==false)){
				
					Toast.makeText(getApplicationContext(), "mail non valide", Toast.LENGTH_LONG).show();	
				
				}
				else if(adr.getText().toString().equals("")){
					
					Toast.makeText(getApplicationContext(), "adr non valide", Toast.LENGTH_LONG).show();	
				
				}
				
				else{
					
					String log=generate( name.getText().toString());
					
				Intent i1=	new Intent(Inscription.this,Inscription1.class);
				i1.putExtra("name", name.getText().toString());
				i1.putExtra("mail", mail.getText().toString());
				i1.putExtra("tel", tel.getText().toString());
				i1.putExtra("adr", adr.getText().toString());
				i1.putExtra("log", log);
				
				finish();
				
				startActivity(i1) ;
				}
			}
		});
		
	}

	
	private String generate(String name){
		
		Random rand = new Random();

	    // nextInt is normally exclusive of the top value,
	    // so add 1 to make it inclusive
	    int randomNum = rand.nextInt((9999 - 0) + 1) + 0;

	    return "bee."+randomNum+"."+name;
		
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
