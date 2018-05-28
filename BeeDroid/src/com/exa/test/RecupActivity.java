package com.exa.test;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import classes.*;
public class RecupActivity extends Activity {
     String q1="";
     String q2="";
     String q3="";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_recup);
		
		 setspinner();
		retour();
		envoyer() ;
		
		
	}
	
	
	private void setspinner()
	{
		 
		
		Spinner spinner = (Spinner) findViewById(R.id.planets_spinner);
		// Create an ArrayAdapter using the string array and a default spinner layout
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
		        R.array.planets_array, android.R.layout.simple_spinner_item);
		// Specify the layout to use when the list of choices appears
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Apply the adapter to the spinner
		spinner.setAdapter(adapter);
		//spinner 2
		

		spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
		    @Override
		    public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
		    	
		    	
		    	String x=parentView.getItemAtPosition(position).toString();
		         q1=x;
		        // your code here
		    }

		    @Override
		    public void onNothingSelected(AdapterView<?> parentView) {
		        // your code here
		    }

		});
		
		
		
		
		
		
		
		
		Spinner spinner2 = (Spinner) findViewById(R.id.q2);
		// Create an ArrayAdapter using the string array and a default spinner layout
		ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this,
		        R.array.q2, android.R.layout.simple_spinner_item);
		// Specify the layout to use when the list of choices appears
		adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Apply the adapter to the spinner
		spinner2.setAdapter(adapter1);
		
		
		spinner2.setOnItemSelectedListener(new OnItemSelectedListener() {
		    @Override
		    public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
		    	
		    	
		    	String x=parentView.getItemAtPosition(position).toString();
		    	if(x.contains("langue"))
		    	{
		    		x="Quel est votre langue prefere ?";
		    		
		    	}
		        q2=x;
		        // your code here
		    }

		    @Override
		    public void onNothingSelected(AdapterView<?> parentView) {
		        // your code here
		    }

		});
		
		
		
		
		
		
		
		//spinner 3
		Spinner spinner3 = (Spinner) findViewById(R.id.q3);
		// Create an ArrayAdapter using the string array and a default spinner layout
		ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
		        R.array.q3, android.R.layout.simple_spinner_item);
		// Specify the layout to use when the list of choices appears
		adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Apply the adapter to the spinner
		spinner3.setAdapter(adapter2);
		
		spinner3.setOnItemSelectedListener(new OnItemSelectedListener() {
		    @Override
		    public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
		    	
		    	
		    	String x=parentView.getItemAtPosition(position).toString();
		        q3=x;
		        // your code here
		    }

		    @Override
		    public void onNothingSelected(AdapterView<?> parentView) {
		        // your code here
		    }

		});
			
		
		
	}


	private void retour(){
		Button btn_return=(Button)findViewById(R.id.btninfo);
		
		btn_return.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				EditText nom=(EditText)findViewById(R.id.fieldname);
				EditText mail=(EditText)findViewById(R.id.fieldmail);
				EditText log=(EditText)findViewById(R.id.fieldlogin);
				EditText rep1=(EditText)findViewById(R.id.fieldrep1);
				EditText rep2=(EditText)findViewById(R.id.fieldrep2);
				EditText rep3=(EditText)findViewById(R.id.fieldrep3);
				nom.setText("");
				mail.setText("");
				log.setText("");
				rep1.setText("");
				rep2.setText("");
				rep3.setText("");
				 finish();
				startActivity(new Intent(RecupActivity.this,IndexActivity.class)) ;
				
			}
		});
	}



	private void envoyer() {
		Button btn_env=(Button)findViewById(R.id.btnenvoyer);
		btn_env.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
			
				EditText nom=(EditText)findViewById(R.id.fieldname);
				EditText mail=(EditText)findViewById(R.id.fieldmail);
				EditText log=(EditText)findViewById(R.id.fieldlogin);
				EditText rep1=(EditText)findViewById(R.id.fieldrep1);
				EditText rep2=(EditText)findViewById(R.id.fieldrep2);
				EditText rep3=(EditText)findViewById(R.id.fieldrep3);
			
				
				
			
				if(nom.getText().toString().equals("")){
				
					Toast.makeText(getApplicationContext(), "nom non valide", Toast.LENGTH_LONG).show();
					
				}
				
				else if(mail.getText().toString().equals("")||(mail.getText().toString().contains("@")==false)||(mail.getText().toString().contains(".")==false)){
				
					Toast.makeText(getApplicationContext(), "mail non valide", Toast.LENGTH_LONG).show();	
				
				}
				else if(log.getText().toString().equals("")){
					
					Toast.makeText(getApplicationContext(), "login non valide", Toast.LENGTH_LONG).show();	
				
				}
               else if(rep1.getText().toString().equals("")){
					
					Toast.makeText(getApplicationContext(), "Réponse 1 non valide", Toast.LENGTH_LONG).show();	
				
				}
               else if(rep2.getText().toString().equals("")){
					
					Toast.makeText(getApplicationContext(), "Réponse 2 non valide", Toast.LENGTH_LONG).show();	
				
				}
               else if(rep3.getText().toString().equals("")){
					
					Toast.makeText(getApplicationContext(), "Réponse 3 non valide", Toast.LENGTH_LONG).show();	
				
				}
				
				else{
				Log.i("send demande", "oki")	;
				Demande_recuperation demande=new Demande_recuperation(1, nom.getText().toString(), "", mail.getText().toString(), log.getText().toString(), q1+rep1.getText().toString(), q2+rep2.getText().toString(), q3+rep3.getText().toString(), "");
					
				demande.start();
				Toast.makeText(getApplicationContext(), "votre demande effectuée avec succes !", Toast.LENGTH_LONG).show();
				startActivity(new Intent(RecupActivity.this,IndexActivity.class)) ;
				}
			}
		});
		
	}





	

}







