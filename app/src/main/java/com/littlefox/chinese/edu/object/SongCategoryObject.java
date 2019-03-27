package com.littlefox.chinese.edu.object;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * 송 메인 화면에서 각각의 탭 카테고리 정보 내용의 오브젝트
 * @author 정재현
 *
 */
public class SongCategoryObject implements Parcelable
{
	private String title 		= "";
	private String description 	= "";
	private String sm_id 		= "";
	
	public SongCategoryObject()
	{
		
	}
	
	public SongCategoryObject(Parcel in)
	{
		title 		= in.readString();
		description = in.readString();
		sm_id 		= in.readString();
	}
	
	public String getTitle()
	{
		return title;
	}
	
	public String getDescription()
	{
		return description;
	}
	
	public String getSMID()
	{
		return sm_id;
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
		dest.writeString(title);
		dest.writeString(description);
		dest.writeString(sm_id);
	}
	
	public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {

		@Override
		public Object createFromParcel(Parcel source)
		{
			// TODO Auto-generated method stub
			return new SongCategoryObject(source);
		}

		@Override
		public Object[] newArray(int size)
		{
			// TODO Auto-generated method stub
			return new SongCategoryObject[size];
		}
		
	};
}
