package com.littlefox.chinese.edu.object.result;

import java.io.Serializable;
import java.util.ArrayList;

import android.os.Parcel;
import android.os.Parcelable;

import com.littlefox.chinese.edu.object.result.base.BaseResult;

public class InitAppResult extends BaseResult implements Parcelable
{
	private String server_time 		= "";
	private String lf_device_id 	= "";
	private String iac_id 			= "";
	private String newest_ver  		= "";
	private String update_type		= "";
	
	private ArrayList<InitItemInApp> iac_list = new ArrayList<InitItemInApp>();
	
	public InitAppResult(Parcel in)
	{
		server_time 	= in.readString();
		lf_device_id 	= in.readString();
		iac_id			= in.readString();
		iac_list		= (ArrayList<InitItemInApp>)in.readSerializable();
		newest_ver		= in.readString();
		update_type		= in.readString();
	}
	
	@Override
	public int describeContents()
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags)
	{
		dest.writeString(server_time);
		dest.writeString(lf_device_id);
		dest.writeString(iac_id);
		dest.writeSerializable(iac_list);
		dest.writeString(newest_ver);
		dest.writeString(update_type);
	}
	
	public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {

		@Override
		public Object createFromParcel(Parcel source)
		{
			// TODO Auto-generated method stub
			return new InitAppResult(source);
		}

		@Override
		public Object[] newArray(int size)
		{
			// TODO Auto-generated method stub
			return new InitAppResult[size];
		}
		
	};
	
	
	public String getServerTime()
	{
		return server_time;
	}

	public void setServerTime(String server_time)
	{
		this.server_time = server_time;
	}

	public String getDeviceId()
	{
		return lf_device_id;
	}

	public String getIACId()
	{
		return iac_id;
	}

	public ArrayList<InitItemInApp> getIACInformationList()
	{
		return iac_list;
	}
	
	public String getAppVersion()
	{
		return newest_ver;
	}
	
	public String getUpdateType()
	{
		return update_type;
	}

	public class InitItemInApp implements Serializable
	{
		public String btn1_mode 	= "";
		public String btn1_text 	= "";
		
		public String btn2_mode 	= "";
		public String btn2_text 	= "";
		public String btn2_useyn 	= "";
		
		public String iac_title 		= "";
		public String iac_content 	= "";
		public String lating_date 	= "";
		public String btn_link		= "";
	}

	
	
}
