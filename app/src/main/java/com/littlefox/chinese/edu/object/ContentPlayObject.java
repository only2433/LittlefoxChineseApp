package com.littlefox.chinese.edu.object;

import android.os.Parcel;
import android.os.Parcelable;

import com.littlefox.chinese.edu.object.result.base.PlayObject;

import java.util.ArrayList;

/**
 * 플레이어에서 사용할 객체 클래스
 * @author 정재현
 *
 */
public class ContentPlayObject implements Parcelable
{
	/**
	 * 플레이 타입 종류 : 동요, 단편, 시리즈
	 */
	private int mPlayItemType = -1;
	
	private int mSelectPosition = -1;
	
	/**
	 * Feature 시리즈 인지의 여부
	 */
	private boolean isRecommandItemEmpty = false;
	
	/**
	 * 캡션정보가 없는지의 여부
	 */
	private boolean isCaptionEmpty = false;
	
	/**
	 * 퀴즈가 없는 지 의 여부
	 */
	private boolean isQuizEmpty = false;
	
	private ArrayList<PlayObject> mPlayObjectList = new ArrayList<PlayObject>();
	
	public ContentPlayObject(int type)
	{
		mPlayItemType = type;
		mPlayObjectList.clear();
		isRecommandItemEmpty = false;
		isCaptionEmpty = false;
		isQuizEmpty = false;
	}
	
	public ContentPlayObject(int type, ArrayList<PlayObject> playObjectList)
	{
		mPlayItemType 	= type;
		mPlayObjectList = playObjectList;
		isRecommandItemEmpty = false;
		isCaptionEmpty = false;
		isQuizEmpty = false;
	}
	
	public ContentPlayObject(Parcel in)
	{
		mPlayItemType 	= in.readInt();
		mSelectPosition = in.readInt();
		isRecommandItemEmpty = in.readInt() == 1;
		isCaptionEmpty 	= in.readInt() == 1;
		isQuizEmpty		= in.readInt() == 1;
		mPlayObjectList = (ArrayList<PlayObject>)in.readSerializable();
	}
	
	public void setSelectPosition(int position)
	{
		mSelectPosition = position;
	}
	
	public void addPlayObject(PlayObject playObject)
	{
		mPlayObjectList.add(playObject);
	}
	
	public void setRecommandItemEmpty()
	{
		isRecommandItemEmpty = true;
	}
	
	public boolean isRecommandItemEmpty()
	{
		return isRecommandItemEmpty;
	}
	
	public void setCaptionEmpty()
	{
		isCaptionEmpty = true;
	}
	
	public boolean isCaptionEmpty()
	{
		return isCaptionEmpty;
	}
	
	public void setQuizEmpty()
	{
		isQuizEmpty = true;
	}
	
	public boolean isQuizEmpty()
	{
		return isQuizEmpty;
	}
	
	public void setPlayObjectList(ArrayList<PlayObject> playObjectList)
	{
		mPlayObjectList = playObjectList;
	}
	
	public int getPlayItemType()
	{
		return mPlayItemType;
	}
	
	public int getSelectedPosition()
	{
		return mSelectPosition;
	}
	
	public ArrayList<PlayObject> getPlayObjectList()
	{
		return mPlayObjectList;
	}
	
	public PlayObject getPlayObject(int position)
	{
		return mPlayObjectList.get(position);
	}

	@Override
	public int describeContents()
	{
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags)
	{
		dest.writeInt(mPlayItemType);
		dest.writeInt(mSelectPosition);
		dest.writeInt(isRecommandItemEmpty == true ? 1 : 0);
		dest.writeInt(isCaptionEmpty == true ? 1 : 0);
		dest.writeInt(isQuizEmpty == true ? 1 : 0);
		dest.writeSerializable(mPlayObjectList);
	}
	
	public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {

		@Override
		public Object createFromParcel(Parcel source)
		{
			// TODO Auto-generated method stub
			return new ContentPlayObject(source);
		}

		@Override
		public Object[] newArray(int size)
		{
			// TODO Auto-generated method stub
			return new ContentPlayObject[size];
		}
		
	};
}
