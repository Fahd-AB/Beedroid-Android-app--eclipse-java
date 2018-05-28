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

import com.exa.test.First;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.exa.test.*;
public class Client extends Thread{
  private int id_client ; 
  private String nom_prenom;
  private String tel;
  private String email;
  private String adresse;
  private String login;
  private String mot_de_passe ;
  private String question_sec1;
  private String question_sec2;
  private String question_sec3;
  
  public   String name="";
  public String mail="";
  public String adr="";
  public  String tel1="";
  public static final String strURL = "http://192.168.1.8/BeeDroid/reception.php";	
  
  private String choix;
  public String res="";
  private String mac;
  Context cnx;
  public String res_app="";

  
  
public Client(int id_client, String nom_prenom, String email, String tel,String adresse,
		String login, String mot_de_passe, String question_sec1,
		String question_sec2, String question_sec3) {
	super();
	this.id_client = id_client;
	this.nom_prenom = nom_prenom;
	this.tel = tel;
	this.email = email;
	this.adresse=adresse;
	this.login = login;
	this.mot_de_passe = mot_de_passe;
	this.question_sec1 = question_sec1;
	this.question_sec2 = question_sec2;
	this.question_sec3 = question_sec3;
	this.choix="1";
}

public Client(String choix,String mac,String login, String mot_de_passe,Context c){
	this.choix=choix;
	this.mac=mac;
	this.login = login;
	this.mot_de_passe = mot_de_passe;
	this.cnx=c;
}


public Client(int id_client, String nom_prenom, String email, String tel,String adresse,String login, String mot_de_passe) {
	super();
	this.id_client = id_client;
	this.nom_prenom = nom_prenom;
	this.tel = tel;
	this.email = email;
	this.adresse=adresse;
	this.login = login;
	this.mot_de_passe = mot_de_passe;
	this.choix="4";
}

public Client(String login)
{
	this.login=login;
	choix="5";
}
public int getId_client() {
	return id_client;
}

public void setId_client(int id_client) {
	this.id_client = id_client;
}
public String getNom_prenom() {
	return nom_prenom;
}
public void setNom_prenom(String nom_prenom) {
	this.nom_prenom = nom_prenom;
}
public String getTel() {
	return tel;
}
public void setTel(String tel) {
	this.tel = tel;
}
public String getEmail() {
	return email;
}
public void setEmail(String email) {
	this.email = email;
}
public String getLogin() {
	return login;
}
public void setLogin(String login) {
	this.login = login;
}
public String getMot_de_passe() {
	return mot_de_passe;
}
public void setMot_de_passe(String mot_de_passe) {
	this.mot_de_passe = mot_de_passe;
}
public String getQuestion_sec1() {
	return question_sec1;
}
public void setQuestion_sec1(String question_sec1) {
	this.question_sec1 = question_sec1;
}
public String getQuestion_sec2() {
	return question_sec2;
}
public void setQuestion_sec2(String question_sec2) {
	this.question_sec2 = question_sec2;
}
public String getQuestion_sec3() {
	return question_sec3;
}
public void setQuestion_sec3(String question_sec3) {
	this.question_sec3 = question_sec3;
}
public String getAdresse() {
	return adresse;
}
public void setAdresse(String adresse) {
	this.adresse = adresse;
}
public String getres()
{
return res;	
}

public void inscrit()
{
	InputStream is = null;
	String result = "";

	ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
	nameValuePairs.add(new BasicNameValuePair("nom_prenom",nom_prenom));
	nameValuePairs.add(new BasicNameValuePair("tel",tel));
	nameValuePairs.add(new BasicNameValuePair("mail",email));
	nameValuePairs.add(new BasicNameValuePair("adr",adresse));
	nameValuePairs.add(new BasicNameValuePair("log",login));
	nameValuePairs.add(new BasicNameValuePair("pass",mot_de_passe));
	nameValuePairs.add(new BasicNameValuePair("q1","Quel est le nom de votre meilleur ami ?"+question_sec1));
	nameValuePairs.add(new BasicNameValuePair("q2","Quel est votre langue prefere ?"+question_sec2));
	nameValuePairs.add(new BasicNameValuePair("q3","Quel est votre lieu de naissance ?"+question_sec3));
	nameValuePairs.add(new BasicNameValuePair("stat","10"));
	try{
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost(strURL);
		httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
		HttpResponse response = httpclient.execute(httppost);
		HttpEntity entity = response.getEntity();
		is = entity.getContent();
		 

	}catch(Exception e){
		Log.e("log_tag", "Error in http connection No data sending client" );
	}	
	
}


public void modifier()
{
	InputStream is = null;
	String result = "";

	ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
	nameValuePairs.add(new BasicNameValuePair("nom_prenom",nom_prenom));
	nameValuePairs.add(new BasicNameValuePair("tel",tel));
	nameValuePairs.add(new BasicNameValuePair("mail",email));
	nameValuePairs.add(new BasicNameValuePair("adr",adresse));
	nameValuePairs.add(new BasicNameValuePair("pass1",mot_de_passe));
	nameValuePairs.add(new BasicNameValuePair("log",login));
	nameValuePairs.add(new BasicNameValuePair("stat","17"));
	try{
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost(strURL);
		httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
		HttpResponse response = httpclient.execute(httppost);
		HttpEntity entity = response.getEntity();
		is = entity.getContent();
		 

	}catch(Exception e){
		Log.e("log_tag", "Error in http connection No data sending client (modification )" );
	}	
	
}


















public void verif_auth(){
	
	
	InputStream is = null;
	String result = "";
 
	ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
	nameValuePairs.add(new BasicNameValuePair("login",login));
	nameValuePairs.add(new BasicNameValuePair("password",mot_de_passe));
	nameValuePairs.add(new BasicNameValuePair("mac",mac));
	nameValuePairs.add(new BasicNameValuePair("stat","14"));
	Log.i("login :"+login,"password :"+mot_de_passe);
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
			
			Log.i("log_tag","Etat est : "+json_data.getString("etat")	);
			
			 String resu=json_data.getString("etat");
			 res_app=resu;
			
			 
		}
		
		//Log.i("Information","Res est : "+res	);
		
	}catch(JSONException e){
		Log.e("log_tag", "Error parsing data  verif_auth first  3" + e.toString());
	}
 
	
	
	
}


void authentifier()
{
	


	
	
	InputStream is = null;
	String result = "";
 
	ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
	nameValuePairs.add(new BasicNameValuePair("login",login));
	nameValuePairs.add(new BasicNameValuePair("password",mot_de_passe));
	nameValuePairs.add(new BasicNameValuePair("stat","15"));
	// Envoie de la commande http
	try{
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost(strURL);
		httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
		HttpResponse response = httpclient.execute(httppost);
		HttpEntity entity = response.getEntity();
		is = entity.getContent();

	}catch(Exception e){
		Log.e("log_tag", "Error in http connection  authentifier" + e.toString());
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
		Log.e("log_tag", "Error converting result authentifier " + e.toString());
	}
	// Parse les données JSON
	try{
		
		JSONArray jArray = new JSONArray(result);    
		for(int i=0;i<jArray.length();i++){
			JSONObject json_data = jArray.getJSONObject(i);
			// Affichage ID_ville et Nom_ville dans le LogCat
			Log.i("log_tag","Etat est : "+json_data.getString("log")	);
			
			// Résultats de la requête
			//res += "\n\t" + jArray.getJSONObject(i); 
			 String resu=json_data.getString("log");
			 
			 res=resu;
			 Log.i("log_tag","res est : "+res	); 
		}
		
		//Log.i("Information","Res est : "+res	);
		
	}catch(JSONException e){
		Log.e("log_tag", "Error parsing data authentifier " + e.toString());
	}
 

}
	



public void  recive_info_client()
{

	InputStream is = null;
	String result = "";
 
	ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
	nameValuePairs.add(new BasicNameValuePair("login",login));
	nameValuePairs.add(new BasicNameValuePair("stat","18"));
	// Envoie de la commande http
	try{
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost(strURL);
		httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
		HttpResponse response = httpclient.execute(httppost);
		HttpEntity entity = response.getEntity();
		is = entity.getContent();

	}catch(Exception e){
		Log.e("log_tag", "Error in http connection recive_info_client " + e.toString());
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
		Log.e("log_tag", "Error converting result recive information client  " + e.toString());
	}
	// Parse les données JSON
	try{
		
		JSONArray jArray = new JSONArray(result);    
		for(int i=0;i<jArray.length();i++){
			JSONObject json_data = jArray.getJSONObject(i);
			// Affichage ID_ville et Nom_ville dans le LogCat
			//Log.i("log_tag","Etat est : "+json_data.getString("etat")	);
			
			// Résultats de la requête
			//res += "\n\t" + jArray.getJSONObject(i); 
			  name=json_data.getString("name");
			  mail=json_data.getString("mail");
			  adr=json_data.getString("adr");
			  tel1=json_data.getString("tel");
			
			 
		}
		
		//Log.i("Information","Res est : "+res	);
		
	}catch(JSONException e){
		Log.e("log_tag", "Error parsing data  recive_info_client" + e.toString());
	}
 
		

}



public void run() {
	
	super.run();
	  
	 if(choix.equals("1"))
	 {
	inscrit();
	 }
    if(choix.equals("2"))
    {
    verif_auth();
    }
	if(choix.equals("3"))
	{
	authentifier();	
	}
	if(choix.equals("4"))
	{
	modifier();	
	}
	if(choix.equals("5"))
	{
		recive_info_client();	
	}
	
}


}
