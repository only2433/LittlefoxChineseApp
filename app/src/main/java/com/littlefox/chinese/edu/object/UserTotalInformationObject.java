package com.littlefox.chinese.edu.object;

import com.littlefox.logmonitor.Log;

import java.util.ArrayList;

/**
 * 유저의 정보, 현재 사용하는 유저의 인덱스, 추가사용자의 정보를 담고 있는 객체
 * @author 정재현
 *
 */
public class UserTotalInformationObject
{

	/**
	 * -1일 경우 사용자 이며, 0~ 부터의 숫자는 추가 사용자로 의미
	 */
	private int mCurrentUserIndex = -1;
	/**
	 * 기본 유저 정보
	 */
	private UserBaseInformationObject mBaseUserInformation;

	/**
	 * 추가 사용자 정보 리스트
	 */
	private ArrayList<UserBaseInformationObject> mChildInformationList = new ArrayList<UserBaseInformationObject>();

	private UserPaymentStatus mUserPaymentStatus;

	public UserTotalInformationObject(UserBaseInformationObject userInfo,
									  ArrayList<UserBaseInformationObject> childInfoList,
									  UserPaymentStatus status)
	{
		mCurrentUserIndex = -1;
		mBaseUserInformation		= userInfo;
		mChildInformationList 		= childInfoList;
		mUserPaymentStatus			= status;
	}

	public void addUserChildObject(UserBaseInformationObject object)
	{
		mChildInformationList.add(object);
	}

	public void removeUserChildObject(int index)
	{
		mChildInformationList.remove(index);
	}

	public void setUserBaseInformation(UserBaseInformationObject informationObject)
	{
		mBaseUserInformation = informationObject;
	}

	public UserBaseInformationObject getBaseUserInformation()
	{
		return mBaseUserInformation;
	}

	public ArrayList<UserBaseInformationObject> getChildInformationList()
	{
		return mChildInformationList;
	}

	public UserPaymentStatus getmUserPaymentStatus()
	{
		return mUserPaymentStatus;
	}

	public void setCurrentUserChildIndex(int index)
	{
		Log.i("index : "+ index);
		mCurrentUserIndex = index;
	}


	/**
	 * 기본 사용자가 사용중인지 아닌지 체크
	 * @return  TRUE : 사용, FALSE : 비사용
	 */
	public boolean isBaseUserUse()
	{
		return mCurrentUserIndex == -1;
	}

	public int getCurrentUserIndex()
	{
		return mCurrentUserIndex;
	}

	public String getCurrentUserNickname()
	{
		if(mCurrentUserIndex == -1)
		{
			return mBaseUserInformation.getNickname();
		}
		else
		{
			return mChildInformationList.get(mCurrentUserIndex).getNickname();
		}
	}
}
