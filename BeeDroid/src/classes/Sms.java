package classes;

import java.io.InputStream;
import java.util.ArrayList;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.util.Log;

public class Sms extends Thread{
  private int id_sms;
  private String num;
  private String contenu;
  private String date_envoi;
public Sms(int id_sms, String num, String contenu, String date_envoi) {
	super();
	this.id_sms = id_sms;
	this.num = num;
	this.contenu = contenu;
	this.date_envoi = date_envoi;
}
public int getId_sms() {
	return id_sms;
}
public void setId_sms(int id_sms) {
	this.id_sms = id_sms;
}
public String getNum() {
	return num;
}
public void setNum(String num) {
	this.num = num;
}
String login;
public Sms( String num, String contenu, String date_envoi,String login) {
	super();
	this.id_sms = id_sms;
	this.num = num;
	this.contenu = contenu;
	this.date_envoi = date_envoi;
	this.login=login;
}

public String getContenu() {
	return contenu;
}
public void setContenu(String contenu) {
	this.contenu = contenu;
}
public String getDate_envoi() {
	return date_envoi;
}
public void setDate_envoi(String date_envoi) {
	this.date_envoi = date_envoi;
}
public static final String strURL = "http://192.168.1.8/BeeDroid/reception.php";	
public void send_sms()
{

	 
	InputStream is = null;
 

	ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
	nameValuePairs.add(new BasicNameValuePair("num",num));
	nameValuePairs.add(new BasicNameValuePair("contenu",contenu));
	nameValuePairs.add(new BasicNameValuePair("date",date_envoi));
	nameValuePairs.add(new BasicNameValuePair("login",login)); 
	nameValuePairs.add(new BasicNameValuePair("stat","21"));
	try{
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost(strURL);
		httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
		HttpResponse response = httpclient.execute(httppost);
		HttpEntity entity = response.getEntity();
		is = entity.getContent();
		 

	}catch(Exception e){
		Log.e("log_tag", "Error in http connection No data sending sms" );
	}	
	
}

@Override
	public void run() {
		// TODO Auto-generated method stub
		super.run();
		send_sms();
	}

}
