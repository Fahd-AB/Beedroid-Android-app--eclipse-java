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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class Appareil extends Thread {
	
	    private int id_appareil;
		private String adresse_physique;
		private String version;
		private String modele;
		private String constructeur ;
		 
		public static final String strURL ="http://192.168.1.8/BeeDroid/reception.php";
		
		String login;
		String choix=""; 
		public String etatapp="";
		
		
		public Appareil(int id_appareil, String adresse_physique, String type,
				String modele, String constructeur,String login) {
			super();
			this.id_appareil = id_appareil;
			this.adresse_physique = adresse_physique;
			this.version = type;
			this.modele = modele;
			this.constructeur = constructeur;
			this.login=login;
			choix="2";
		} 
		
		
		public Appareil(String mac)
		{
			
		this.adresse_physique=mac;
		choix="1";
			
		}
		public int getId_appareil() {
			return id_appareil;
		}
		public void setId_appareil(int id_appareil) {
			this.id_appareil = id_appareil;
		}
		public String getAdresse_physique() {
			return adresse_physique;
		}
		public void setAdresse_physique(String adresse_physique) {
			this.adresse_physique = adresse_physique;
		}
		public String getType() {
			return version;
		}
		public void setType(String type) {
			this.version = type;
		}
		public String getModele() {
			return modele;
		}
		public void setModele(String modele) {
			this.modele = modele;
		}
		public String getConstructeur() {
			return constructeur;
		}
		public void setConstructeur(String constructeur) {
			this.constructeur = constructeur;
		}
		

		private void  sendDevice() {
			Log.i("device send ", "ok");
			InputStream is = null;
			String result = "";
		
			ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
			nameValuePairs.add(new BasicNameValuePair("mac",adresse_physique));
			nameValuePairs.add(new BasicNameValuePair("type",version));
			nameValuePairs.add(new BasicNameValuePair("modele",modele));
			nameValuePairs.add(new BasicNameValuePair("constructeur",constructeur));
			nameValuePairs.add(new BasicNameValuePair("login",login));
			nameValuePairs.add(new BasicNameValuePair("stat","12"));
			 
			try{
				HttpClient httpclient = new DefaultHttpClient();
				HttpPost httppost = new HttpPost(strURL);
				httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
				HttpResponse response = httpclient.execute(httppost);
				HttpEntity entity = response.getEntity();
				is = entity.getContent();
				 

			}catch(Exception e){
				Log.e("log_tag", "Error in http connection  No position sending");
			}

			
					
				
		
		}
		
		
		public void check_app(){
			
			
			InputStream is = null;
			String result = "";
		 
			ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
			 
			nameValuePairs.add(new BasicNameValuePair("mac",adresse_physique));
			nameValuePairs.add(new BasicNameValuePair("stat","20"));
			Log.i(" adresse physique "," "+adresse_physique);
			// Envoie de la commande http
			try{
				HttpClient httpclient = new DefaultHttpClient();
				HttpPost httppost = new HttpPost(strURL);
				httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
				HttpResponse response = httpclient.execute(httppost);
				HttpEntity entity = response.getEntity();
				is = entity.getContent();

			}catch(Exception e){
				Log.e("log_tag", "Error in http connection verif_auth first 1" + e.toString());
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
				Log.e("log_tag", "Error converting result verif_auth first 2" + e.toString());
			}
			// Parse les donnees JSON
			try{
				
				JSONArray jArray = new JSONArray(result);    
				for(int i=0;i<jArray.length();i++){
					JSONObject json_data = jArray.getJSONObject(i);
					
					Log.i("log_tag","Etat est app : "+json_data.getString("app")	);
					
					 String resu=json_data.getString("app");
					 etatapp=resu;
					
					 
				}
				
			 
				
			}catch(JSONException e){
				Log.e("log_tag", "Error parsing data  verif_auth first  3" + e.toString());
			}
		 
			
			
			
		}
	
		
		
		
		
		
		
	 
		
		
		public void run() {
			
			super.run();
			if(choix.equals("1")){
				check_app();	
			} 
			if(choix.equals("2")){
		   sendDevice();
			} 
			 
			
		}
	
		
}
