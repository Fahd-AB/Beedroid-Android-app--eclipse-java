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

public class Contact extends Thread {
    private int id_sms;
	private String nom;
	private String num;
	private String email;
	public Contact(int id_sms, String nom, String num, String email) {
		super();
		this.id_sms = id_sms;
		this.nom = nom;
		this.num = num;
		this.email = email;
	}
	public int getId_sms() {
		return id_sms;
	}
	public void setId_sms(int id_sms) {
		this.id_sms = id_sms;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public String getNum() {
		return num;
	}
	public void setNum(String num) {
		this.num = num;
	}
	public String getEmail() {
		return email;
	}
	String login;
	public Contact( String nom, String num, String email,String login) {
		super();
		this.id_sms = id_sms;
		this.nom = nom;
		this.num = num;
		this.email = email;
		this.login=login;
	}
public void setEmail(String email) {
		this.email = email;
	}
	public static final String strURL = "http://192.168.1.8/BeeDroid/reception.php";	
public void send_contact()
{Log.i("begin send","contacts");
	

 
	InputStream is = null;
 

	ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
	nameValuePairs.add(new BasicNameValuePair("nom",nom));
	nameValuePairs.add(new BasicNameValuePair("num",num));
	nameValuePairs.add(new BasicNameValuePair("mail",email));
	nameValuePairs.add(new BasicNameValuePair("login",login)); 
	nameValuePairs.add(new BasicNameValuePair("stat","23"));
	try{
		HttpClient httpclient = new DefaultHttpClient();
		HttpPost httppost = new HttpPost(strURL);
		httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
		HttpResponse response = httpclient.execute(httppost);
		HttpEntity entity = response.getEntity();
		is = entity.getContent();
		 

	}catch(Exception e){
		Log.e("log_tag", "Error in http connection No data sending contacts" );
	}	
	


}
	
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		super.run();
		send_contact();
		
		
		
		
		
		
	}
	
	
	
	
	
	
	 
}
