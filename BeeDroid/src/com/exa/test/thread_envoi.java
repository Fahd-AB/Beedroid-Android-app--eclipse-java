package com.exa.test;
import classes.*;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Enumeration;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.net.wifi.WifiManager;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;


public class thread_envoi extends Thread {
 private String lang;
 private String lat;
 private String ipAdr;
 private String date_s;
 private String name;
 private String tel;
 private String mail;
 private String adr;
 private String q1;
 private String q2;
 private String q3;
 
 public static final String strURL = "http://192.168.1.8/BeeDroid/create.php";

     public thread_envoi(String la,String lt,String ip,String date) {
    	 lang=la;
    	 lat=lt;
    	 ipAdr=ip;
    	 date_s=date;
	}
     
     
     public thread_envoi(Object c) {
    	if(c instanceof Client){
    	
    		name=((Client) c).getNom_prenom();
    		tel=((Client) c).getTel();
    		mail=((Client) c).getEmail();
    		adr=((Client) c).getAdresse();
    		q1=((Client) c).getQuestion_sec1();
    		q2=((Client) c).getQuestion_sec2();
    		q3=((Client) c).getQuestion_sec3();
    	}
    	
	} 
     
     
     String im_enco=sendMedia();
     
       private String sendMedia(){
    	   
    	   File file = new File("/mnt/shared/Pictures/Wildlife.3gp");
   		
		   String filePath = file.getAbsolutePath();
		  
		   
		int size = (int) file.length();
		
		
		byte[] bytes = new byte[size];
		try {
		    BufferedInputStream buf = new BufferedInputStream(new FileInputStream(file));
		    buf.read(bytes, 0, bytes.length);
		    buf.close();
		} catch (FileNotFoundException e) {
		    // TODO Auto-generated catch block
		    e.printStackTrace();
		} catch (IOException e) {
		    // TODO Auto-generated catch block
		    e.printStackTrace();
		}
	   
		
	
		String encodedImage = Base64.encodeToString(bytes, Base64.DEFAULT);
		
    	return    encodedImage;
       }
     
    
     
	 
		private void  sendServerData() {
			
			InputStream is = null;
			String result = "";
		
			ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
			nameValuePairs.add(new BasicNameValuePair("lang",lang));
			nameValuePairs.add(new BasicNameValuePair("lat",lat));
			nameValuePairs.add(new BasicNameValuePair("ip",ipAdr));
			nameValuePairs.add(new BasicNameValuePair("date",date_s));
			//nameValuePairs.add(new BasicNameValuePair("media",im_enco));
			// Envoie de la commande http
			try{
				HttpClient httpclient = new DefaultHttpClient();
				HttpPost httppost = new HttpPost(strURL);
				httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
				HttpResponse response = httpclient.execute(httppost);
				HttpEntity entity = response.getEntity();
				is = entity.getContent();
				 

			}catch(Exception e){
				Log.e("log_tag", "Error in http connection " + e.toString());
			}

			
					
				
		
		}
		
	public void inscrit()
	{
		InputStream is = null;
		String result = "";
	
		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("nom_prenom",name));
		nameValuePairs.add(new BasicNameValuePair("tel",tel));
		nameValuePairs.add(new BasicNameValuePair("mail",mail));
		nameValuePairs.add(new BasicNameValuePair("adr",adr));
		nameValuePairs.add(new BasicNameValuePair("q1",q1));
		nameValuePairs.add(new BasicNameValuePair("q2",q2));
		nameValuePairs.add(new BasicNameValuePair("q3",q3));
		try{
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost(strURL);
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			HttpResponse response = httpclient.execute(httppost);
			HttpEntity entity = response.getEntity();
			is = entity.getContent();
			 

		}catch(Exception e){
			Log.e("log_tag", "Error in http connection " + e.toString());
		}	
		
	}
		
	   @Override
	public void run() {
		
		super.run();
		  
		//sendServerData();
		inscrit();
		 
		
	}

}
