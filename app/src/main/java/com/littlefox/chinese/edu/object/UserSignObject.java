package com.littlefox.chinese.edu.object;

public class UserSignObject extends UserBaseInformationObject
{
	private String mUserId = "";
	private String mPassword = "";
	
	public UserSignObject(String userId , String password, String nickName , String name , String birthday, String sexType , String phoneNumber)
	{
		super(nickName, name, birthday, sexType, phoneNumber.replace("-", ""));
		mUserId 	= userId;
		mPassword 	= password;
	}
	
	public UserSignObject(String userId , String password, String nickName , String name)
	{
		super(nickName, name);
		mUserId 	= userId;
		mPassword 	= password;
	}
	
	public UserSignObject(String userId , String password, String nickName)
	{
		super(nickName);
		mUserId 	= userId;
		mPassword 	= password;
	}

	public String getUserId()
	{
		return mUserId;
	}

	public String getPassword()
	{
		return mPassword;
	}


	
	
}
