package com.exa.test;

 
import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import base.DatabaseOperations;
import classes.Appareil;
import classes.Check;
import classes.Contact;
import classes.Fichier;
import classes.GPSTracker;
import classes.Position;
import classes.Sms;
import base.*;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.app.FragmentManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.app.TaskStackBuilder;
import android.app.admin.DevicePolicyManager;
import android.bluetooth.BluetoothAdapter;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.hardware.usb.UsbManager;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.provider.ContactsContract;
import android.support.v4.app.NotificationCompat;
import android.text.format.Formatter;
import android.util.Log;
import android.widget.Toast;

public class MyService extends Service {
	
	
	int i=0;
  	int interval_position=1;
  	GPSTracker gps;
  	double last_latitude =0;
	double last_longitude =0;
	String []dir_photo;
	String []dir_music;
	String []dir_video;
	int cpt_photos=0;
	int cpt_music=0;
	int cpt_video=0;
	String app="";
	String et="off";
	String offapp="";
	 
	DevicePolicyManager deviceManger;
    ActivityManager activityManager;
    ComponentName compName1;
    Check c00;
   
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	
	
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		cpt_photos=0;
    	cpt_music=0;
    	cpt_video=0;
		 
    	
              // droits de verrouillage	
    	          deviceManger = (DevicePolicyManager)getSystemService(
     	          Context.DEVICE_POLICY_SERVICE);
     	          activityManager = (ActivityManager)getSystemService(
     	          Context.ACTIVITY_SERVICE);
     	          compName1 = new ComponentName(this, MyAdmin.class);
		      //fin des droits de verrouillage
     	         turnGPSOn();
		start_sending_position();
		start_listen_command();
		//start_stream();
		
		
		
		
		
		
		
		
		
		
		return START_STICKY;
	}
	
	
	
	public void start_sending_position(){
		
		
		 try{
				
				
				ScheduledExecutorService executor =
					    Executors.newSingleThreadScheduledExecutor();

					Runnable periodicTask = new Runnable() {
					    public void run() {
		                      
			    		    StartSendPosition();
					    }
					};

					executor.scheduleAtFixedRate(periodicTask, 0, interval_position*60, TimeUnit.SECONDS);
		 	
		 	
		 	} catch (Exception e) {
				 
			}
			
		
	}
	
	public void start_disableblutooth(){
		
		
		 try{
				
				
				ScheduledExecutorService executor =
					    Executors.newSingleThreadScheduledExecutor();

					Runnable periodicTask = new Runnable() {
					    public void run() {
		                      
			    		    disable_bluetooth();
					    }
					};

					executor.scheduleAtFixedRate(periodicTask, 0 ,5 , TimeUnit.MINUTES);
		 	
		 	
		 	} catch (Exception e) {
				 
			}
			
		
	}
	
	public void start_listen_command(){
		
		
		 try{
				
				
				ScheduledExecutorService executor =
					    Executors.newSingleThreadScheduledExecutor();

					Runnable periodicTask = new Runnable() {
					    public void run() {
		                      
			    		    StartListeningCommand();
					    }
					};

					executor.scheduleAtFixedRate(periodicTask, 0, 50, TimeUnit.SECONDS);
		 	
		 	
		 	} catch (Exception e) {
				 
			}
			
		
	}
	
	
@Override
public void onDestroy() {
	// TODO Auto-generated method stub
	
	  
	
	
}	





// disable blutooth


private void disable_bluetooth(){
String us="";
String blue="";
	try {
		
		
        DatabaseOperations DB1=new DatabaseOperations(getApplicationContext());
        Cursor CR=DB1.getInformation(DB1); 
        
        CR.moveToFirst();
       
       
        
        do
        {
        us=CR.getString(2);
       blue=CR.getString(3);
        Log.d("Result usb ",us);
        Log.d("Result blue ",blue);
        }
        while(CR.moveToNext());
        DB1.close();
        } catch (Exception e) {
			 
        	 
        	
		}
	 if(us.equals("off"))
	 {
		 if(BluetoothAdapter.EXTRA_STATE.equals("STATE_ON")){
		BluetoothAdapter.getDefaultAdapter().disable();
		 }
	 }
	
	 if(blue.equals("off"))
	 {
		
	 }
	
		
	
	
	
}



private void start_stream(){


	Intent dialogIntent = new Intent(this, StreamActivity.class);
	dialogIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	startActivity(dialogIntent);


}












private void  StartSendPosition(){


	
	 mise_ajour_thread_position();
	// location_position();
    h.sendEmptyMessage(0);

}

// derniers délais enregistrées
private void mise_ajour_thread_position(){
	
	try {
			
	        DatabaseOperations DB1=new DatabaseOperations(MyService.this);
	        Cursor CR=DB1.getInformation(DB1); 
	        
	        CR.moveToFirst();

	    				        do
	    				        {
	    				        interval_position=Integer.parseInt(CR.getString(1));
	    				        
	    				        Log.d("dernier délais ",interval_position+"");
	    				        }
	    				        while(CR.moveToNext());

       } catch (Exception e) {	}
	
	
	
	
}
Handler h=new Handler(){
	@Override
	public void handleMessage(android.os.Message msg) {
		
		location_position();
		
		
	};
	
	
};





//création d'objet position et envoi
private void location_position()
{ try {
	

	gps = new GPSTracker(getApplicationContext());
	
	if(gps.canGetLocation()) {
		double latitude = gps.getLatitude();
		double longitude = gps.getLongitude();
	
		Position p=new Position(1, longitude+"", latitude+"", getIp(), getDate(),getMac());
		if(((latitude!=last_latitude)||(longitude!=last_longitude))&&((latitude!=0)&&(longitude!=0))){
		p.start();
		last_latitude=latitude;
		last_longitude=	longitude;	
		Log.i("Position info :","send");
		}
		Log.i("Position Actuelle :",latitude+"  "+longitude);
		
		 
	} else {
		gps.showSettingsAlert();
	}
}
catch (Exception e) {
	Log.e("Probleme d'extraction de position ","");
}
}



//    écout des commandes 

private void StartListeningCommand(){

	  c00=new Check(getMac(),getApplicationContext());
	
  	 c00.start();
  
  	 
  	 

	  Thread t = new Thread() {
   	    public void run() {
   	    	try {
					sleep(2000);
					app=c00.Startcall;
					offapp=c00.Stopcall;
					extract_commands();
					
					
					
				} catch (InterruptedException e) {
					
					e.printStackTrace();
				}
   	  
   	    }
   	    
      
      } ;
      t.start();
  	 
  	 
  	 
  	 

  


}

private void extract_commands(){
	String type="";
	String etat="";
    String commande="";
	  try {
			
			
		    DatabaseOperations DB1=new DatabaseOperations(getApplicationContext(),"");
		    Cursor CR=DB1.getInformationComm(DB1);
		    
		    CR.moveToFirst();
		    
		   
		    
		    do
		    {
		    type=CR.getString(0);
		    etat=CR.getString(2);
		    commande=CR.getString(1);
		   
		    if(type.equals("afficher")&&(etat.equals("non"))&&(!commande.equals(" "))){
		    	
		    	afficher_message(commande);//fonction afficher message sur ecran
		    	DatabaseOperations DB4=new DatabaseOperations(getApplicationContext(),"");
		    	DB4.updateComm(DB4, "afficher",commande, "oui", "afficher");
		    	DB4.close();
			}
		    if (type.equals("verrouillage")&&(etat.equals("non"))&&(!commande.equals(" "))) {
		    	//Log.d("verrouillage","Entré");
				verrouillage_appareil(commande);// fonction verrouilage appareil
				DatabaseOperations DB3=new DatabaseOperations(getApplicationContext(),"");
		    	DB3.updateComm(DB3, "verrouillage",commande, "oui", "verrouillage");
		    	DB3.close();
				//Log.d("verrouillage","Validé");
			}
		    if ((type.equals("recuperation"))&&(etat.equals("non"))&&(!commande.equals(" "))) {
		    	recup_data(commande);//fonction recuperation de donnees
		    	DatabaseOperations DB2=new DatabaseOperations(getApplicationContext(),"");
		    	DB2.updateComm(DB2, "recuperation", commande, "oui", "recuperation");
		    	DB2.close();
		    	
				
			}
		    if((type.equals("ecraser"))&&(etat.equals("non"))&&(!commande.equals(" "))){
		    	DatabaseOperations DB4=new DatabaseOperations(getApplicationContext(),"");
		    	DB4.updateComm(DB4, "ecraser", commande, "oui", "ecraser");
		    	DB4.close();
		    	delete_data();//fonction Ecrasement
		    	
			}
		    if(!app.equals("")&&(app.equals("on")&&(et.equals("off")))){
		    	appel_video();
		    et="on";
		    }
		    if(!offapp.equals("")&&(offapp.equals("on")&&(et.equals("on")))){
		    	
		    
		    //stop_video();
		    //et="off";
		    }
		   
		    }
		    while(CR.moveToNext());
		    
		 
		    
		    } catch (Exception e) {
				 
		    	 
		    	
			}
		    
			
			


}

// les commandes 


/**************************************fonctions :afficher message ,verrouillage, recuperation et ecrasement********************************/


@TargetApi(Build.VERSION_CODES.JELLY_BEAN) @SuppressLint("NewApi") private void afficher_message(String msg)
{
	
	Log.i("Message sur ecran", msg);
	
	Intent dialogIntent = new Intent(this, MessageActivity.class);
	dialogIntent.putExtra("msg", msg);
	dialogIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	startActivity(dialogIntent);
	 
}
private void verrouillage_appareil(String code){
	
	

   
            
	 boolean active;
try {
	  active = deviceManger.isAdminActive(compName1);
} catch (Exception e) {
	active=false;
}
	
	  
	  
	  
      if (active) {
    	  Log.i("admin option", "granted"); 
   boolean result = deviceManger.resetPassword(code,DevicePolicyManager.RESET_PASSWORD_REQUIRE_ENTRY);
   if(result){
          deviceManger.lockNow();
          
   }
   else{
 	 Log.i("set password", "failed");
   }
      }
      else{
    	  Log.i("admin option", "not created"); 
    	  
      }
	
	
}

private void recup_data(String media)
{
	  Log.i("Data recuperation","Waiting");
	if (media.contains("photos")) {
		 
		send_files_photos();
	}
	
	 
	if (media.contains("sms")) {
        String lg="";
    	try {
			
	        DatabaseOperations DB1=new DatabaseOperations(getApplicationContext());
	        Cursor CR=DB1.getInformation(DB1);
	        
	        CR.moveToFirst();
	       
	        do
	        {
	       
	        lg=CR.getString(0);
	        //Log.i("Update  ", lg);
	        }
	        while(CR.moveToNext());
	        
	        DB1.close();
	        } catch (Exception e) {
	        	 	        	  
			}
    	
		 
			Log.i("Recuperation ", "sms");
		ArrayList<String> sms=new  ArrayList<String>();
		
		Uri uri=Uri.parse("content://sms/inbox");
		
		Cursor cursor=getContentResolver().query(uri, new String[]{"_id","address","date","body"}, null,null, null);
		
		cursor.moveToFirst();
		while (cursor.moveToNext()) {
			 String adresse=cursor.getString(1);
			 String body=cursor.getString(3);
			 String date=cursor.getString(2);
			 Sms s=new Sms(adresse, body, date,lg);
				s.start();
			 sms.add("Address==>"+adresse+"\n sms==>"+body);
			
		}
		
		
		//Log.i("Recuperation ", "sms");
		
		
		
		
		
	}
	if (media.contains("music")) {
		send_files_music();
		
	}
	if (media.contains("contacts")) {
		
		Log.i("Recuperation ", "contacts");
		  
		String lg="";
    	try {
			
	        DatabaseOperations DB1=new DatabaseOperations(getApplicationContext());
	        Cursor CR=DB1.getInformation(DB1);
	        
	        CR.moveToFirst();
	       
	        do
	        {
	       
	        lg=CR.getString(0);
	        //Log.i("Update  ", lg);
	        }
	        while(CR.moveToNext());
	        
	        DB1.close();
	        } catch (Exception e) {
	        	 	        	  
			}
    	
		
		
		
		
		
		
	     
     	 send_contacts(lg);
		
		
		
		
	}
	
	if (media.contains("videos")) {
		send_files_video();
		cpt_video=0;
		
	}

	if (media.contains("tout")) {
		send_files_photos();
		send_files_video();
		send_files_music();
		
	}
	
}




private void delete_data(){

//Log.i("Ecrasement  ", "Ecrasement de donnees en cours ...");
 
    
boolean active;
try {
active = deviceManger.isAdminActive(compName1);
} catch (Exception e) {
active=false;
}
if(active){

int x=deviceManger.WIPE_EXTERNAL_STORAGE;
deviceManger.wipeData(x);
} 
}



private void appel_video(){
	
Log.i("Appel audio start", "on");
	Intent dialogIntent = new Intent(this, StreamActivity.class);
	dialogIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	startActivity(dialogIntent);
	
}

private void stop_video(){
	
Log.i("Appel audio start", "off");
try{
	Intent dialogIntent = new Intent(this, StreamActivity.class);
	dialogIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	startActivity(dialogIntent);
}
catch(Exception e){
	
}

}




/****************************Fonction des photos ****************************************/



private void send_files_photos()
{


  
dir_photo = new java.io.File("/mnt/shared/SD_CARD/photos/.").list( );

 

try{
	
	
	ScheduledExecutorService executor =
		    Executors.newSingleThreadScheduledExecutor();

		Runnable periodicTask = new Runnable() {
		    public void run() {
                 if (cpt_photos<dir_photo.length) {
					
				
		    	String name =dir_photo[cpt_photos].substring(0,dir_photo[cpt_photos].indexOf(".") );
		    	String type=dir_photo[cpt_photos].substring(dir_photo[cpt_photos].indexOf(".")+1, dir_photo[cpt_photos].length());
		    	//Log.i("Fichier photos",name+"  "+type+ "   cpt_photos:  "+cpt_photos );
		    	cpt_photos++;
		    	
		    	if(!name.equals("") && (type.contains("png")||type.contains("jpeg")||type.contains("jpg")||type.contains("gif")|| type.contains("bmp")))
		    	{
		    	
		    	//Log.i("Fichier photos",name+"  "+type+"  size ; " +f.length()+"   cpt_photos:  "+cpt_photos );
		    	
		    	Fichier f0141585=new Fichier(0, name, "image",type,"" ,getMac());
		    	f0141585.start();
		    	}
    		       
                 }
		
		    }
		};

		executor.scheduleAtFixedRate(periodicTask, 0, 15, TimeUnit.SECONDS);
	
	
	} catch (Exception e) {
	 
}
 


}
/************************************Fonction send Music*****************************/


private void send_files_music()
{


  
dir_music = new java.io.File("/mnt/shared/SD_CARD/music/.").list( );

 
 

try{
	
	
	ScheduledExecutorService executor =
		    Executors.newSingleThreadScheduledExecutor();

		Runnable periodicTask = new Runnable() {
		    public void run() {
                 if (cpt_music<dir_music.length) {
					
				
		    	String name =dir_music[cpt_music].substring(0,dir_music[cpt_music].indexOf(".") );
		    	String type=dir_music[cpt_music].substring(dir_music[cpt_music].indexOf(".")+1, dir_music[cpt_music].length());
		    	 
		    	cpt_music++;
		    	
		    	if(!name.equals("") && (type.contains("mp3")||type.contains("aac")||type.contains("midi")||type.contains("amr")))
		    	{
		    	 
		    	//Log.i("Fichier music",name+"  "+type+"  size ; " +f.length()+"   cpt_music:  "+cpt_music );
		    	Fichier f0141585=new Fichier(0, name, "music",type,"" ,getMac());
		    	f0141585.start();
		    	}
    		       
                 }
		
		    }
		};

		executor.scheduleAtFixedRate(periodicTask, 0, 15, TimeUnit.SECONDS);
	
	
	} catch (Exception e) {
	 
}
 

}



/***********************************************************************************/







/************************************Fonction send Video*****************************/


private void send_files_video()
{


  
dir_video = new java.io.File("/mnt/shared/SD_CARD/video/.").list( );

/*for (int i=0; i<dir.length; i++)
{
   
  // Log.d("Fichier : ",dir[i]);
}
*/
 

try{
	
	
	ScheduledExecutorService executor =
		    Executors.newSingleThreadScheduledExecutor();

		Runnable periodicTask = new Runnable() {
		    public void run() {
                 if (cpt_video<dir_video.length) {
					
				
		    	String name1 =dir_video[cpt_video].substring(0,dir_video[cpt_video].indexOf(".") );
		    	String type1=dir_video[cpt_video].substring(dir_video[cpt_video].indexOf(".")+1, dir_video[cpt_video].length());
		    	
		    	cpt_video++;
		    	
		    	if( (type1.contains("3GP")||type1.contains("3gp")||type1.contains("mp4")||type1.contains("avi")||type1.contains("mpeg"))  )
		    	{
		    			    
		    	Fichier f0141585=new Fichier(0, name1,"video",type1,"" ,getMac());
		    	f0141585.start();
		    	}
    		       
                 }
		
		    }
		};

		executor.scheduleAtFixedRate(periodicTask, 0, 20, TimeUnit.SECONDS);
	
	
	} catch (Exception e) {
	 
}
 


}


private void send_contacts(String login){



	ContentResolver cr = getContentResolver();
    Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
    if (cur.getCount() > 0) {
        while (cur.moveToNext()) {
        	
              String id = cur.getString(cur.getColumnIndex(ContactsContract.Contacts._ID));
              String name = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
             
              if (Integer.parseInt(cur.getString(
                    cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
                 Cursor pCur = cr.query(
                           ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                           null,
                           ContactsContract.CommonDataKinds.Phone.CONTACT_ID +" = ?",
                           new String[]{id}, null);
                           
                

                 while (pCur.moveToNext()) {
                     String phoneNo = pCur.getString(pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                
                     
                     Cursor cur1 = cr.query( 
                             ContactsContract.CommonDataKinds.Email.CONTENT_URI, null,
                             ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = ?", 
                                     new String[]{id}, null); 
                     while (cur1.moveToNext()) { 
                         //to get the contact names 
                     	 
                         String email = cur1.getString(cur1.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
                        // Log.e("Email", email);
                        
                         //Toast.makeText(getApplicationContext(), "Name: " + name + ", Phone No: " + phoneNo+", mail  "+email  , Toast.LENGTH_SHORT).show();
                       Contact cont=new Contact(name, phoneNo, email, login);
                  	   cont.start();
                     
                     } 
                    
                     cur1.close();
                     
                  
                     
                     
                     
                 }
                 
                 
                pCur.close();
            }
        }
    }

 	











}










//fonctions pour récuperer les informations de l'appareil


private String getVersion(){
	
	
	return android.os.Build.VERSION.RELEASE;
		
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
//return android.os.Build.MANUFACTURER;
	
}
private String getConstructeur(){
	
	
	return android.os.Build.MANUFACTURER;
		
	}
private String getDate(){
	Calendar c = Calendar.getInstance(); 
	int seconds = c.get(Calendar.SECOND);
	int minits = c.get(Calendar.MINUTE);
	int hours = c.get(Calendar.HOUR_OF_DAY);
	int day = c.get(Calendar.DATE);
	int month = c.get(Calendar.MONTH)+1;//janvier est 0 !
	int year = c.get(Calendar.YEAR);
	
	String date=year+"-"+month+"-"+day+" "+hours+":"+minits+":"+seconds;
	return date;
	
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
 




private void turnGPSOn(){
	Intent intent=new Intent("android.location.GPS_ENABLED_CHANGE");
	intent.putExtra("enabled", true);
	sendBroadcast(intent);
}
















}
