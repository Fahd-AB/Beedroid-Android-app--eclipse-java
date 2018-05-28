package com.exa.test;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import classes.Position;

import com.exa.test.*;

import base.DatabaseOperations;
import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.DialogFragment;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.wifi.p2p.WifiP2pManager.ActionListener;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ToggleButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class MyDialog1 extends DialogFragment implements View.OnClickListener  {
	String min="";
	EditText minute;
	EditText identifiant;
	Button valider;
	Context cnx;
	ToggleButton toggle_etat;
	 ComponentName compName;
	  DevicePolicyManager deviceManger;
	    ActivityManager activityManager;
	    Position p;
	    String lat="vide";
	    String lang="vide";
	    String etat=" ";
	    
 
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
         setCancelable(false);
		 getDialog().setTitle("Paramètre");
		View view=inflater.inflate(R.layout.mydialog1, null);
		minute=(EditText)view.findViewById(R.id.minute);
		identifiant=(EditText)view.findViewById(R.id.identifiant);
		valider=(Button)view.findViewById(R.id.valider);
		valider.setOnClickListener(this);
		toggle_etat=(ToggleButton)view.findViewById(R.id.etatapp12);
		
		
		
		
		
		
	 
		
		 /*deviceManger = (DevicePolicyManager)getSystemService(cnx.DEVICE_POLICY_SERVICE);
	   	        activityManager = (ActivityManager)getSystemService(
	   	          Context.ACTIVITY_SERVICE);
	   	        compName = new ComponentName(cnx, MyAdmin.class);*/
		   
		String stat="";
		
		try {
			
			
	        DatabaseOperations DB1=new DatabaseOperations(getActivity().getApplicationContext());
	        Cursor CR=DB1.getInformation(DB1); 
	        
	        CR.moveToFirst();
	       
	       
	        
	        do
	        {
	        min=CR.getString(1);
	        stat=CR.getString(4);
	        
	        
	        }
	        while(CR.moveToNext());
	        
	        } catch (Exception e) {
				 
	        	 
	        	
			}
		
		minute.setText(min);
		if(stat.equals("on")){
		toggle_etat.setChecked(true);
		}
		else
		{
			toggle_etat.setChecked(false);	
		}
		 /**********************************************************************************/
		toggle_etat.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			   @Override
			   public void onCheckedChanged(CompoundButton buttonView,
			     boolean isChecked) {
				   
			    if(isChecked){
			    	
			      etat="on1";
			    
			 	
			    }
			    else
			    {
			    	etat="off1";
			    	
                  }
			    
			    
			    
			    
			    
			    

			   }
			  }); 

		/**********************************************************************************/
		
		

		return view;
		
	}
	
	


	@SuppressLint("HandlerLeak") Handler h=new Handler(){
			@Override
			public void handleMessage(android.os.Message msg) {
				
				Intent i=new Intent(cnx,MainActivity.class);
				i.putExtra("lat", p.getLatitude());
				i.putExtra("lang",p.getLongitude());
				startActivity(i) ;		
				
			}	
			};
	
	

	public void setcontext(Context context)
	{
	cnx=context;	
		
	}
	
	@SuppressLint("HandlerLeak") @Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

		if (v.getId()==R.id.valider) {
		
			if (etat.equals("on1")) {
				etat="on";
				Log.i("Dialo", "on");
			}
			if (etat.equals("off1")) {
				etat="off";
				Log.i("Dialo", "off");
			}
			int test_minute=0;
			try {
				test_minute=Integer.parseInt(minute.getText().toString());
			} catch (Exception e) {
				test_minute=0;
			}
			
			
			
			if(test_minute>2880 || test_minute<2)
			{
			Toast.makeText(getActivity(), "Délai entre 5 minutes et 2 jours ", Toast.LENGTH_LONG).show();	
			}
			else if(minute.getText().toString().equals(""))
			{
			Toast.makeText(getActivity(), "Délai invalide ", Toast.LENGTH_LONG).show();	
			}
			
			else
		    {
				
				DatabaseOperations DB1=new DatabaseOperations(getActivity().getApplicationContext());
		    	DB1.update_delai(DB1,minute.getText().toString() );
		    	DB1.close();
		    	 if(!identifiant.getText().toString().equals("")){
		    		 
		    		//Toast.makeText(getActivity(), "identifiant  ", Toast.LENGTH_LONG).show();	
		    		  p=new Position(identifiant.getText().toString());
		    		p.start();
		    		
		  		  Thread t = new Thread() {
		  	    	    public void run() {
		  	    	    	try {
		  						sleep(2000);
		  						 
		  						Log.i("position ami ",p.getLatitude()+"   "+p.getLongitude());
		  						
		  						
		  						try {
		  							lat=p.getLatitude();
		  							lang=p.getLongitude();
								} catch (Exception e) {
									
								}
		  						
		  						
		  						
		  						
		  						
		  							
		  						
		  							
		  							
		  						
		  							
		  							 
		  					       
		  					
		  						
		  						
		  						
		  					} catch (InterruptedException e) {
		  						// TODO Auto-generated catch block
		  						e.printStackTrace();
		  					}
		  	    	  
		  	    	    }
		  	    	    
		  	       
		  	       } ;
		  	       t.start();
		  		
		  		
		    	
		    		 
		    	 }
		    	  //startActivity(new Intent(cnx,MainActivity.class)) ;	
		    	
				dismiss();	
			}
			
			
			
		}
		 
		 
		 
		
	}
	
	
	
	
	
	 
	

	
	
	
}


