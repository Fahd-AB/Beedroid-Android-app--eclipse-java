package com.exa.test;

 
import base.DatabaseOperations;
import classes.Appareil;
import classes.Client;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
 
public class Modifier extends Activity {
	EditText name1;
	EditText email1;
	EditText adresse1;
	EditText tel1;
	 Client cccc;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_modifier);
		
	
	     name1=(EditText)findViewById(R.id.nomprenom);
		 email1=(EditText)findViewById(R.id.email);
		 adresse1=(EditText)findViewById(R.id.adresse);
		 tel1=(EditText)findViewById(R.id.phone);
		 load_info();
		modifier_compte();
		
	}
	
	
	
	
	
	private void load_info(){
		
		 String logins="";
			try {
				
				
		        DatabaseOperations DB1=new DatabaseOperations(Modifier.this);
		        Cursor CR=DB1.getInformation(DB1); 
		        
		        CR.moveToFirst();
		       
		       
		        
		        do
		        {
		        	logins=CR.getString(0);
		      
		        //Log.d("Result ",logins);
		        }
		        while(CR.moveToNext());
		        
		         
		        
		        } catch (Exception e) {
					 
		        	 
		        	
				}
		
		
	 
		//String logins="f";
		 cccc=new Client(logins);
		  cccc.start();

		  Thread t = new Thread() {
	    	    public void run() {
	    	    	try {
						sleep(1000);
						 runOnUiThread(new Runnable(){
	    	    				public void run(){
	    	    					
	    	    					  // Log.d("update  ",cccc.getNom_prenom());
	    	    					name1.setText(cccc.name);
	    							email1.setText(cccc.mail);
	    							adresse1.setText(cccc.adr);
	    							tel1.setText(cccc.tel1);
	    							   Log.d("update  ",cccc.name+"");
	    							
	    	    				}
	    	    				
	    	    		        });
						 
						
						
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	    	  
	    	    }
	    	    
	       
	       } ;
	       t.start();
		
		
	}
	
	
	
	
	
	
	
	
	
	
	
	/************************************Modification de compte client***********************************************/
	private void  modifier_compte(){
		 
		
		Button btn_Annuler=(Button)findViewById(R.id.btnAnnuler);
		btn_Annuler.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
			
				startActivity(new Intent(Modifier.this,MainActivity.class)) ;
				finish();
			}
		});
		
		
		
		
		
		Button btn_connect=(Button)findViewById(R.id.btnValider);
		btn_connect.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				EditText name=(EditText)findViewById(R.id.nomprenom);
				EditText password=(EditText)findViewById(R.id.mdp);
				EditText passwordconf=(EditText)findViewById(R.id.cmdp);
				EditText email=(EditText)findViewById(R.id.email);
				EditText adresse=(EditText)findViewById(R.id.adresse);
				EditText tel=(EditText)findViewById(R.id.phone);
				
				
				if (name.getText().toString().equals("")) {
					Toast.makeText(Modifier.this,"Le champ Nom et prénom est vide  ", Toast.LENGTH_LONG).show();
				}
				
				else if (password.getText().toString().length()<4){
					Toast.makeText(Modifier.this,"Minimum 8 caractére pour le mot de passe  ", Toast.LENGTH_LONG).show();
					
				}else if (!password.getText().toString().equals(passwordconf.getText().toString())) {
					Toast.makeText(Modifier.this,"Comfirmation mot de passe incorrect  ", Toast.LENGTH_LONG).show();
				}
				else if(email.getText().toString().equals("")||(email.getText().toString().contains("@")==false)||(email.getText().toString().contains(".")==false)){
				
					Toast.makeText(getApplicationContext(), "mail non valide", Toast.LENGTH_LONG).show();	
				}
				else if(adresse.getText().toString().equals("")){
					
					Toast.makeText(getApplicationContext(), "adresse non valide (exemple@domaine)", Toast.LENGTH_LONG).show();	
				}
				else if (!isNumeric(tel.getText().toString())) {
					Toast.makeText(getApplicationContext(), "Téléphone incorrect (doit être numerique)", Toast.LENGTH_LONG).show();
				}
				else if (tel.getText().toString().length()<8) {
					Toast.makeText(getApplicationContext(), "Télephone au minimun 8 chiffre ", Toast.LENGTH_LONG).show();
					
				}
				else{
					 String u="";
					try {
						
						
				        DatabaseOperations DB1=new DatabaseOperations(Modifier.this);
				        Cursor CR=DB1.getInformation(DB1); 
				        
				        CR.moveToFirst();
				       
				       
				        
				        do
				        {
				        u=CR.getString(0);
				      
				        Log.d("Result ",u);
				        }
				        while(CR.moveToNext());
				        
				         
				        
				        } catch (Exception e) {
							 
				        	 
				        	
						}
					Toast.makeText(getApplicationContext(), "Modification de compte effectué !", Toast.LENGTH_LONG).show();
				Client c=new Client(1, name.getText().toString(), email.getText().toString(), tel.getText().toString(), adresse.getText().toString(),u,password.getText().toString());
				c.start();
				startActivity(new Intent(Modifier.this,MainActivity.class)) ;
				finish();
				}
			}
		});
		
		
		
	 
		
		
	}
	
	
	
	/**************************************************************************************************************/
	public boolean isNumeric(String input) {
		  try {
		    Integer.parseInt(input);
		    return true;
		  }
		  catch (NumberFormatException e) {
		    // s is not numeric
		    return false;
		  }
		}
	
	
	
	
	
	
	

}
