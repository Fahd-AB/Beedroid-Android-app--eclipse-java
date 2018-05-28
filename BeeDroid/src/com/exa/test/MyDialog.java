package com.exa.test;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import base.DatabaseOperations;
import android.app.DialogFragment;
import android.bluetooth.BluetoothAdapter;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class MyDialog extends DialogFragment implements View.OnClickListener {
      Button cancel;
      String us="";
      String blue="";
      ToggleButton toggle_usb;
      ToggleButton toggle_blue;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		 getDialog().setTitle("Gestion de connectivité");
		View view=inflater.inflate(R.layout.my_dialog, null);
		
		
		cancel=(Button)view.findViewById(R.id.cancel);
		cancel.setOnClickListener(this);
		toggle_usb=(ToggleButton)view.findViewById(R.id.usb);
		toggle_blue=(ToggleButton)view.findViewById(R.id.etatapp12);
		
		
		
		
		
		
		try {
			
			
	        DatabaseOperations DB1=new DatabaseOperations(getActivity().getApplicationContext());
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
	        
	        } catch (Exception e) {
				 
	        	 
	        	
			}
		 if(us.equals("on"))
		 {
			 toggle_usb.setChecked(true); 
			 
		 }
		 else
		 {
			 toggle_usb.setChecked(false);  
			 
		 }
		 if(blue.equals("on"))
		 {
			 toggle_blue.setChecked(true); 
			 
		 }
		 else
		 {
			 toggle_blue.setChecked(false);  
			 
		 }
		
		
		 /**********************************************************************************/
		toggle_usb.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			   @Override
			   public void onCheckedChanged(CompoundButton buttonView,
			     boolean isChecked) {
			    
			    if(isChecked){
			    	DatabaseOperations DB1=new DatabaseOperations(getActivity().getApplicationContext());
			    	DB1.update_usb(DB1, "on");
			    
			    	//Toast.makeText(getActivity(), "USB on", Toast.LENGTH_SHORT).show();	
			    }
			    else
			    {    DatabaseOperations DB1=new DatabaseOperations(getActivity().getApplicationContext());
		    	DB1.update_usb(DB1, "off");
			    	//Toast.makeText(getActivity(), "USB off", Toast.LENGTH_SHORT).show();	
		    	
		    	
		    	
			    	
			    }

			   }
			  }); 

		/**********************************************************************************/
		
	
		toggle_blue.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			   @Override
			   public void onCheckedChanged(CompoundButton buttonView,
			     boolean isChecked) {
				   ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
			    
			    if(isChecked){
			    
			    	//Toast.makeText(getActivity(), "bluetooth on", Toast.LENGTH_SHORT).show();	
			    	DatabaseOperations DB1=new DatabaseOperations(getActivity().getApplicationContext());
			    	DB1.update_bluetooth(DB1, "on");
			    	BluetoothAdapter.getDefaultAdapter().enable();
			    	try {
						executor.shutdown();
					} catch (Exception e) {
						// TODO: handle exception
					}
			    }
			    else
			    {
			    	DatabaseOperations DB1=new DatabaseOperations(getActivity().getApplicationContext());
			    	DB1.update_bluetooth(DB1, "off");
			    	//Toast.makeText(getActivity(), "bluetooth off", Toast.LENGTH_SHORT).show();	
			    	 try{
							
						  executor = Executors.newSingleThreadScheduledExecutor();

								Runnable periodicTask = new Runnable() {
								    public void run() {
					                      if(BluetoothAdapter.getDefaultAdapter().isEnabled())
					                      {
								    	BluetoothAdapter.getDefaultAdapter().disable();
					                      }
								    }
								};

								executor.scheduleAtFixedRate(periodicTask, 0, 2, TimeUnit.SECONDS);
					 	
					 	
					 	} catch (Exception e) {
							 
						}
			    	
			    	
			    	
			    }

			   }
			  }); 

		
		
		
		
		
		
		
		
		
		
		
		
		
		
		

		 
		
		
		
		
		
		
		
		setCancelable(false);
		return view;
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v.getId()==R.id.cancel) {
		//Toast.makeText(getActivity(), "Annuler", Toast.LENGTH_LONG).show();	
			dismiss();
		}
		 
		 
		 
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
