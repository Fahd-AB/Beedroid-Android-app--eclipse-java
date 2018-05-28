package com.exa.test;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class Boot extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		if ("android.intent.action.BOOT_COMPLETED".equals(intent.getAction())) { 
			
			
			context.startService(new Intent(context,MyService.class));
			
			
		Log.i("info", "started at boot");
			
		}
	}

}
