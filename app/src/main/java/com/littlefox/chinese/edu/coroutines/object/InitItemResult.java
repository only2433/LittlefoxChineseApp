package com.littlefox.chinese.edu.coroutines.object;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;

public class InitItemResult extends ItemResult implements Parcelable
{
	public ArrayList<InitItemInApp> inapp 		= new ArrayList<InitItemInApp>();
	public InitAppVersion version 	= new InitAppVersion();

	public String device_id 	="";
	public String facebook_msg	="";
	public String facebook_icon ="";
	public String facebook_desc	="";
	public String caption_ver 	="";

	public InitItemResult(Parcel in)
	{
		code 			= in.readString();
		message 		= in.readString();
		device_id 		= in.readString();
		facebook_msg 	= in.readString();
		facebook_icon	= in.readString();
		facebook_desc	= in.readString();
		inapp			= (ArrayList<InitItemInApp>) in.readSerializable();
		version			= (InitAppVersion) in.readSerializable();
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
		dest.writeString(code);
		dest.writeString(message);
		dest.writeString(device_id);
		dest.writeString(facebook_msg);
		dest.writeString(facebook_icon);
		dest.writeString(facebook_desc);
		dest.writeSerializable(inapp);
		dest.writeSerializable(version);
	}
	
	public static final Creator CREATOR = new Creator() {

		@Override
		public Object createFromParcel(Parcel source)
		{
			// TODO Auto-generated method stub
			return new InitItemResult(source);
		}

		@Override
		public Object[] newArray(int size)
		{
			// TODO Auto-generated method stub
			return new InitItemResult[size];
		}
		
	};
	

	
	public class InitItemInApp implements Serializable
	{
		public String inapp_code 	= "";
		public String btn1_mode 	= "";
		public String btn1_text 	= "";
		
		public String btn2_mode 	= "";
		public String btn2_text 	= "";
		public String btn2_useyn 	= "";
		
		public String iac_title 	= "";
		public String iac_content 	= "";
		public String btn_link		= "";
	}
	
	public class InitAppVersion implements Serializable
	{
		/**
		 * 최신버젼 ex) 1.0.0
		 */
		public String newest 		="";
		
		/**
		 * 	C: Critical  무조건 업데이트 진행
			N: Normal  사용자 선택 (Yes/No)
		 */
		public String update_type 	="";
		
		/**
		 * 앱 스토어 URL
		 */
		public String store_url		="";
	}
	
	
}
