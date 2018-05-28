package classes;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
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

import android.util.Base64;
import android.util.Log;

public class Fichier extends Thread {
	private int id_fichier ;
	private String non_fichier;
    private String contenu ;
    private String type ;
    private String taille;
    private String data;
	private String mac;
    public static final String strURL ="http://192.168.1.8/BeeDroid/reception.php";
	public Fichier(int id_fichier, String non_fichier, String contenu,String type, String taille ,String Mac) {
		super();
		this.id_fichier = id_fichier;
		this.non_fichier = non_fichier;
		this.contenu = contenu;
		this.type = type;
		this.taille = taille;
		this.data=codeMedia();
		this.mac=Mac;
	}
	public int getId_fichier() {
		return id_fichier;
	}
	public void setId_fichier(int id_fichier) {
		this.id_fichier = id_fichier;
	}
	public String getNon_fichier() {
		return non_fichier;
	}
	public void setNon_fichier(String non_fichier) {
		this.non_fichier = non_fichier;
	}
	public String getContenu() {
		return contenu;
	}
	public void setContenu(String contenu) {
		this.contenu = contenu;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getTaille() {
		return taille;
	}
	public void setTaille(String taille) {
		this.taille = taille;
	} 
   
	   public String getMac() {
			return mac;
		}
		public void setMac(String mac) {
			this.mac = mac;
		}





	
	
	
	 private String codeMedia(){
  	   
		
		 
		 String folder="";
		 
  	      //File file = new File("/storage/extSdCard/test/"+non_fichier+"."+type);
		 //File file = new File("/mnt/shared/SD_CARD/"+non_fichier+"."+type);
		  // String filePath = file.getAbsolutePath();
		  
		   
  	 if( (type.contains("png")||type.contains("jpeg")||type.contains("jpg")||type.contains("gif")|| type.contains("bmp")))
     {
  		   folder = "photos";
     	
     }
     
     if(type.contains("mp3")||type.contains("aac")||type.contains("midi")||type.contains("amr"))
     {
    	  
    	   folder = "music";
     }
     
     if(type.contains("3gp")||type.contains("mp4")||type.contains("avi")||type.contains("mpeg"))
     {
    	   	
    	   folder = "video";
     }  	   
  	   
     
     Log.i("folder",folder);
     File file = new File("/mnt/shared/SD_CARD/"+folder+"/"+non_fichier+"."+type);   
     taille=(int)file.length()+"";
  	   
  	   Log.i("path",""+file.getAbsolutePath());
  	   
  	   
  	   
  	   
  	   
  	   
  	   
  	   
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
	
	 
	 
	 public void sendData()
	 {
	 	InputStream is = null;
	 	String result = "";
        String conteint="";
        if( (type.contains("png")||type.contains("jpeg")||type.contains("jpg")||type.contains("gif")|| type.contains("bmp")))
        {
        conteint="image";	
        	
        }
        
        if(type.contains("mp3")||type.contains("aac")||type.contains("midi")||type.contains("amr"))
        {
        	 conteint="music";		
        	
        }
        
        if(type.contains("3gp")||type.contains("mp4")||type.contains("avi")||type.contains("mpeg"))
        {
        	 conteint="video";		
        	
        }
        
	 	ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
	 	nameValuePairs.add(new BasicNameValuePair("media",data));
	 	nameValuePairs.add(new BasicNameValuePair("mac",getMac()));
	 	nameValuePairs.add(new BasicNameValuePair("filename",non_fichier));
	 	nameValuePairs.add(new BasicNameValuePair("type",type));
	 	
	 	//double tail=(Double.parseDouble(taille));
	 	//long taille=(long)Math.floor(tail + 0.5d);
	 	int totsize=(Integer.parseInt(taille)/1024);
		nameValuePairs.add(new BasicNameValuePair("size",totsize+""));		
		nameValuePairs.add(new BasicNameValuePair("content",conteint));
	 	nameValuePairs.add(new BasicNameValuePair("stat","13"));
	 	//Log.i("Message :", "Fichier envoi "+non_fichier+"   taille:"+taille+" type :"+type+"    mac :"+mac+"   Send ");
	 	try{
	 		HttpClient httpclient = new DefaultHttpClient();
	 		HttpPost httppost = new HttpPost(strURL);
	 		httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
	 		HttpResponse response = httpclient.execute(httppost);
	 		HttpEntity entity = response.getEntity();
	 		is = entity.getContent();
	 		 

	 	}catch(Exception e){
	 		Log.e("log_tag", "Error in http connection  No file "+conteint+" Sending ");
	 	}	
	 	
	 }
	 
	 
	 
	 
	 
	
	 public void run() {
			
			super.run();
			  
		 
			sendData();
			 
			
		}
	
	
	
}
