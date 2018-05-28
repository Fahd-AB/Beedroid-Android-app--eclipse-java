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

import base.DatabaseOperations;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;


public class Check extends Thread {
public String res ;
String mac;
Context cnx;
String comm="";
String etat="";
public String Startcall="off";
public String Stopcall="off";
  public static final String strURL ="http://192.168.1.8/BeeDroid/commande.php";


public Check(String mac ,Context cnx)
{
	this.mac=mac;
	this.cnx=cnx;
}






public String getServerData() {
	Log.d("recuperation de donnees", "entred ");
	InputStream is = null;
	DatabaseOperations DB=new DatabaseOperations(cnx,"");  
 
	String result = "";
	ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
	nameValuePairs.add(new BasicNameValuePair("mac",mac));
	// Envoie de la commande http
	try{
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost(strURL);
		httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
		HttpResponse response = httpclient.execute(httppost);
		HttpEntity entity = response.getEntity();
		is = entity.getContent();

	}catch(Exception e){
		Log.i("log_tag", "Error in http connection getServerData commande");
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
		Log.e("log_tag", "Error in http connection recuperation de donnees ");
	}
	// Parse les données JSON
	try{
		String verr="";
		String aff="";
		String recup="";
		String del="";
		String staapp="";
		String stoapp="";
		JSONArray jArray = new JSONArray(result);    
		for(int i=0;i<jArray.length();i++){
			JSONObject json_data = jArray.getJSONObject(i);
            
			 verr=json_data.getString("cmdverr");
			 aff=json_data.getString("cmdaff");
			 recup=json_data.getString("cmdrecup");
			 del=json_data.getString("cmddel");
			 staapp=json_data.getString("cmdapp");
			 stoapp=json_data.getString("cmdappoff");
			//Log.d("list  commandes"," verr:"+verr+"  aff:"+aff+"  recup:"+recup+"   del:"+del );
			
			 
			 
			 
			if(aff.contains("afficher")){
				String str=aff.substring(10, aff.length());
				Log.d("Commande afficher",""+str);
				DatabaseOperations DB3=new DatabaseOperations(cnx,"");
				try {
					
					
			        
			        Cursor CR=DB3.getInformationComm(DB3); 
			        
			        CR.moveToFirst();
			        
			       
			        
			        do
			        {
			        	if(CR.getString(0).equals("afficher"))
			        	{
			           comm=CR.getString(1);
			           etat=CR.getString(2);
			        	}
			        	
			        }
			        while(CR.moveToNext());
			        
			        } catch (Exception e) {
						 
			        	 Log.d("erreur commande",""+e.getMessage());   
			        	
					}
				if ((!comm.equals(str))&& ((etat.equals("oui"))||(comm.equals(" ")))) {
				Log.d("Commande Afficher",""+str);
				DB3.updateComm(DB3, "afficher", str,"non", "afficher");
				}
			}
			
			
			
			
			
			
			
			
			if(verr.contains("verrouillage")){
				
				String str=verr.substring(14, verr.length());
				Log.d("Commande verrouillage",""+str);
			DatabaseOperations DB1=new DatabaseOperations(cnx,"");
				try {
						
						
				        
				        Cursor CR=DB1.getInformationComm(DB1); 
				        
				        CR.moveToFirst();
				        
				       
				        
				        do
				        {
				        	if(CR.getString(0).equals("verrouillage"))
				        	{
				        comm=CR.getString(1);
				        etat=CR.getString(2);
				        	}
				        	
				        }
				        while(CR.moveToNext());
				        
				        } catch (Exception e) {
							 
				        	 Log.d("erreur commande",""+e.getMessage());   
				        	
						}
				
				if ((!comm.equals(str))&& ((etat.equals("oui"))||(comm.equals(" ")))) {
					DB1.updateComm(DB1, "verrouillage", str,"non", "verrouillage");
					Log.d("Commande Verrouiilage",""+str);
					DB1.close();
				}
				
						
			}
			
			if(recup.contains("recuperation")){
				
				String str=recup.substring(14, recup.length());
				 Log.d("str",""+str); 
				DatabaseOperations DB4=new DatabaseOperations(cnx,"");
				try {
					
					
			        
			        Cursor CR=DB4.getInformationComm(DB4); 
			        
			        CR.moveToFirst();
			        
			       
			        
			        do
			        {
			        	if(CR.getString(0).equals("recuperation"))
			        	{
			           comm=CR.getString(1);
			           etat=CR.getString(2);
			        	}
			        	
			        }
			        while(CR.moveToNext());
			        
			        } catch (Exception e) {
						 
			        	 Log.d("erreur commande",""+e.getMessage());   
			        	
					}
				
				
				if ((!comm.equals(str))&& ((etat.equals("oui"))||(comm.equals(" ")))) {
				DB4.updateComm(DB4, "recuperation", str,"non", "recuperation");
				Log.d("Commande recuperation",""+str);
				DB4.close();
				}
				
						
			}
			if(del.contains("ecraser")){
				 
				DatabaseOperations DB5=new DatabaseOperations(cnx,"");
					try {
							
							
					        
					        Cursor CR=DB5.getInformationComm(DB5); 
					        
					        CR.moveToFirst();
					        
					       
					        
					        do
					        {
					        	if(CR.getString(0).equals("ecraser"))
					        	{
					        comm=CR.getString(1);
					        etat=CR.getString(2);
					        	}
					        	
					        }
					        while(CR.moveToNext());
					        
					        } catch (Exception e) {
								 
					        	 Log.d("erreur commande",""+e.getMessage());   
					        	
							}
				
				
				
				
					if ((!comm.equals(del))&& ((etat.equals("oui"))||(comm.equals(" ")))) {
				DB5.updateComm(DB5, "ecraser", "ecraser","non", "ecraser");
				DB5.close();
				Log.d("Commande Ecraser","Ecrasement "+del);
					}		
			}
			
			
           if(staapp.contains("startcall")){
        	   if(Startcall.equals("off")){
				Startcall="on";
        	   }	
			}
			
           if(stoapp.contains("stopcall")){
        	   if(Stopcall.equals("off")){
				Stopcall="on";
        	   }	
			}
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			
			//Log.i("done cheking","no commands found");
		
			
		}
		DB.close();
	}catch(JSONException e){
		Log.e("log_tag", "Error parsing data getServerData" );
	}
 
	 
   
	return res ; 
}

 




 
	
String resres="";	
@Override
public void run() {
	// TODO Auto-generated method stub
	//super.run();
	 resres=getServerData();
	
}
	
}
