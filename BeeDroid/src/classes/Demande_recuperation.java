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

public class Demande_recuperation extends Thread {
	 private int id_demande ;
	 private String nom_prenom;
	  private String tel;
	  private String email;
	  private String login;
	  private String question_sec1;
	  private String question_sec2;
	  private String question_sec3;
	  private String verif;
	public Demande_recuperation(int id_demande, String nom_prenom, String tel,
			String email, String login, String question_sec1,
			String question_sec2, String question_sec3, String verif) {
		super();
		this.id_demande = id_demande;
		this.nom_prenom = nom_prenom;
		this.tel = tel;
		this.email = email;
		this.login = login;
		this.question_sec1 = question_sec1;
		this.question_sec2 = question_sec2;
		this.question_sec3 = question_sec3;
		this.verif = verif;
	}
	public int getId_demande() {
		return id_demande;
	}
	public void setId_demande(int id_demande) {
		this.id_demande = id_demande;
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
	public String getVerif() {
		return verif;
	}
	public void setVerif(String verif) {
		this.verif = verif;
	}
	public static final String strURL = "http://192.168.1.8/BeeDroid/reception.php";	
	public void send_recup()
	{
		InputStream is = null;
		String result = "";

		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		nameValuePairs.add(new BasicNameValuePair("name",nom_prenom));
	 
		nameValuePairs.add(new BasicNameValuePair("mail",email));
		 
		nameValuePairs.add(new BasicNameValuePair("log",login));
	 
		nameValuePairs.add(new BasicNameValuePair("q1", question_sec1));
		nameValuePairs.add(new BasicNameValuePair("q2", question_sec2));
		nameValuePairs.add(new BasicNameValuePair("q3", question_sec3));
		nameValuePairs.add(new BasicNameValuePair("stat","16"));
		try{
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost(strURL);
			httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			HttpResponse response = httpclient.execute(httppost);
			HttpEntity entity = response.getEntity();
			is = entity.getContent();
			 

		}catch(Exception e){
			Log.e("log_tag", "Error in http connection send_recup" + e.toString());
		}	
		
		
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		super.run();
		send_recup();
	}
	
	
}
