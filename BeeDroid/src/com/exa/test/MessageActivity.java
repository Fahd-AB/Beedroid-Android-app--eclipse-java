package com.exa.test;


import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class MessageActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		 this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_message);
		TextView logoo=(TextView)findViewById(R.id.log_msg);
    	Typeface tf1= Typeface.createFromAsset(getAssets(), "PineappleDemo.ttf");
     	logoo.setTypeface(tf1);
		EditText msg=(EditText)findViewById(R.id.messageplace);
		String ms=getIntent().getExtras().get("msg").toString();
		msg.setText(ms);
		msg.setKeyListener(null);
		setupaction();
	}
 
	private void setupaction() {
		Button btn_finish=(Button)findViewById(R.id.ok_btn);
		btn_finish.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
	
				
				finish();
				
				
			}	
			});
		
	}
	
}
