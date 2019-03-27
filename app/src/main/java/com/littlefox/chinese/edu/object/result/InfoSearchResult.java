package com.littlefox.chinese.edu.object.result;

import android.os.Parcel;
import android.os.Parcelable;

import com.littlefox.chinese.edu.object.ContentPlayObject;
import com.littlefox.chinese.edu.object.result.base.BaseResult;

/**
 * 아이디 비밀번호 찾기 정보를 담고 있는 클래스
 * @author 정재현
 *
 */
public class InfoSearchResult extends BaseResult implements Parcelable
{
	private String login_id 		= "";
	private String reg_date 		= "";
	private String email			= "";
	
	public InfoSearchResult(Parcel in)
	{
		result 			= in.readString();
		message 		= in.readString();
		login_id 			= in.readString();
		reg_date 		= in.readString();
		email 			= in.readString();
	}
	
	public String getLoginID()
	{
		return login_id;
	}
	
	public String getRegisterDate()
	{
		return reg_date;
	}
	
	public String getEmail()
	{
		return email;
	}
	
	public String getBlindEmail()
	{
		String[] text = email.split("@");
		text[0] = text[0].substring(0, text[0].length() - 2);
		text[0] = text[0] + "**";
		return text[0] +"@"+text[1];
		
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
		dest.writeString(result);
		dest.writeString(message);
		dest.writeString(login_id);
		dest.writeString(reg_date);
		dest.writeString(email);
	}
	
	public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {

		@Override
		public Object createFromParcel(Parcel source)
		{
			return new InfoSearchResult(source);
		}

		@Override
		public Object[] newArray(int size)
		{
			return new InfoSearchResult[size];
		}
		
	};
}
