package com.littlefox.chinese.edu.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.widget.Toast;

import com.littlefox.chinese.edu.R;
import com.littlefox.chinese.edu.common.CommonUtils;
import com.littlefox.chinese.edu.common.NetworkUtil;

public class NetworkConnectReceiver extends BroadcastReceiver
{

	public NetworkConnectReceiver()
	{
		
	}
	
	
	@Override
	public void onReceive(Context context, Intent intent)
	{
		if(NetworkUtil.isConnectNetwork(context) == false)
		{
			Toast.makeText(context, CommonUtils.getInstance(context).getLanguageTypeString(R.array.message_toast_network_error), Toast.LENGTH_LONG).show();
		}
	}
	
	public void register(Context context)
	{
		context.registerReceiver(this, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
	}
	
	public void unregister(Context context)
	{
		context.unregisterReceiver(this);
	}

}
