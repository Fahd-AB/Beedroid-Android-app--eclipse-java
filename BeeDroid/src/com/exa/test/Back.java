package com.exa.test;


import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;


public class Back extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_back);
		 process_commande();
	       finish();
		
	}

	 void process_commande ()
		{

	     	
	     	try {
				
			
	    		ScheduledExecutorService executor =
	    			    Executors.newSingleThreadScheduledExecutor();

	    			Runnable periodicTask = new Runnable() {
	    			    public void run() {
	    			    	Log.i("background working", "perfect");
	    			    	
	    			    }
	    			};

	    			executor.scheduleAtFixedRate(periodicTask, 0, 10, TimeUnit.SECONDS);
	     	
	     	
	     	} catch (Exception e) {
				// TODO: handle exception
			}
	     	
			
			
			
			
			
			
			
			
			
			
			
		}
		
	
}
