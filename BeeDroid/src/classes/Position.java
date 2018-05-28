package classes;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.LangUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class Position extends Thread {
	private int id_position;
	private String longitude;
	private String latitude;
	private String adresse_logique;
	private String date_position;
	 public static final String strURL = "http://192.168.1.8/BeeDroid/reception.php";
	 public String id;
	 String choix="";
	public Position(int id_position, String longitude, String latitude,
			String adresse_logique, String date_position,String mac) {
		super();
		this.id_position = id_position;
		this.longitude = longitude;
		this.latitude = latitude;
		this.adresse_logique = adresse_logique;
		this.date_position = date_position;
		this.mac=mac;
		choix="1";
	}
	
	
	public Position(String id)
	{
	this.id=id;	
	choix="2";
	}
	

	private String mac;
	public int getId_position() {
		return id_position;
	}
	public void setId_position(int id_position) {
		this.id_position = id_position;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	public String getAdresse_logique() {
		return adresse_logique;
	}
	public void setAdresse_logique(String adresse_logique) {
		this.adresse_logique = adresse_logique;
	}
	public String getDate_position() {
		return date_position;
	}
	public void setDate_position(String date_position) {
		this.date_position = date_position;
	}
	
	
	
	private void  sendPosition() {
		
		InputStream is = null;
		String result = "";
	
		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("lang",longitude));
		nameValuePairs.add(new BasicNameValuePair("lat",latitude));
		nameValuePairs.add(new BasicNameValuePair("ip",adresse_logique));
		nameValuePairs.add(new BasicNameValuePair("date",date_position));
		nameValuePairs.add(new BasicNameValuePair("mac",mac));
		//Log.i("Mac", mac);
		nameValuePairs.add(new BasicNameValuePair("stat","11"));
		 
		try{
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost(strURL);
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			HttpResponse response = httpclient.execute(httppost);
			HttpEntity entity = response.getEntity();
			is = entity.getContent();
			 

		}catch(Exception e){
			Log.e("log_tag", "Error in http connection sendPosition" + e.toString());
		}

		
				
			
	
	}
	
	
	private void return_position_appareil()
	{
		
		InputStream is = null;
		String result = "";
	 
		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		 
		nameValuePairs.add(new BasicNameValuePair("id",id));
		nameValuePairs.add(new BasicNameValuePair("stat","22"));
		Log.i(" id "," "+id);
		// Envoie de la commande http
		try{
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost(strURL);
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			HttpResponse response = httpclient.execute(httppost);
			HttpEntity entity = response.getEntity();
			is = entity.getContent();

		}catch(Exception e){
			Log.e("log_tag", "Error in http connection return_position_appareil" + e.toString());
		}
		// Convertion de la requête en string
		try{
			BufferedReader reader = new BufferedReader(new InputStreamReader(is,"iso-8859-1"),8);
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
			is.close();
			result=sb.toString();
		}catch(Exception e){
			Log.e("log_tag", "Error converting result return_position_appareil" + e.toString());
		}
		// Parse les donnees JSON
		try{
			
			JSONArray jArray = new JSONArray(result);    
			for(int i=0;i<jArray.length();i++){
				JSONObject json_data = jArray.getJSONObject(i);
				
				Log.i("log_tag","Position est  : "+json_data.getString("lat")+" "+json_data.getString("lang")	);
				
				 String lat=json_data.getString("lat");
				 String lang=json_data.getString("lang");
				longitude=lang;
				latitude=lat;
				 
			}
			
		 
			
		}catch(JSONException e){
			Log.e("log_tag", "Error parsing data  return_position_appareil" + e.toString());
		}
	 
		
	}
	
	
	
	
	
	
	public void run() {
		
		super.run();
		  if(choix.equals("1"))
		  {
		sendPosition();
		  }
		  if(choix.equals("2"))
		  {
			  return_position_appareil();  
			  
		  }
		 
		
	}	

}
