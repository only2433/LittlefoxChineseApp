package com.littlefox.chinese.edu.object;

/**
 * 사용자, 추가 사용자의 기본 정보
 * @author 정재현
 *
 */
public class UserBaseInformationObject
{
	protected String user_nickname		= "";
	protected String user_name			= "";
	protected String user_email			= "";
	protected String user_birth			= "";
	protected String user_phone			= "";
	
	public UserBaseInformationObject(String nickname)
	{
		user_nickname 	= nickname;
		user_name 		= "";
		user_email 		= "";
		user_birth  	= "";
		user_phone 		= "";
	}
	
	public UserBaseInformationObject(String nickname, String name)
	{
		user_nickname 	= nickname;
		user_name 		= name;
		user_email 		= "";
		user_birth  	= "";
		user_phone 		= "";
	}
	
	public UserBaseInformationObject(String nickname, String name, String birth)
	{
		user_nickname 	= nickname;
		user_name 		= name;
		user_birth 		= birth;
		user_phone 		= "";
	}
	
	public UserBaseInformationObject(String nickname, String name, String birth,String phone)
	{
		user_nickname 	= nickname;
		user_name 		= name;
		user_birth 		= birth;
		user_phone 		= phone;
	}
	
	public UserBaseInformationObject(String nickname, String name, String email, String birth, String phone)
	{
		user_nickname 	= nickname;
		user_name 		= name;
		user_email 		= email;
		user_birth 		= birth;
		user_phone 		= phone;
	}
	
	public String getNickname()
	{
		return user_nickname;
	}

	public String getName()
	{
		return user_name;
	}
	
	public String getEmail()
	{
		return user_email;
	}

	public String getBirthday()
	{
		return user_birth;
	}


	public String getPhoneNumber()
	{
		return user_phone;
	}

	public void setNickname(String userNickname)
	{
		this.user_nickname = userNickname;
	}

	public void setName(String userName)
	{
		this.user_name = userName;
	}

	public void setEmail(String email)
	{
		this.user_email = email;
	}

	public void setBirthday(String birthday)
	{
		this.user_birth = birthday;
	}

	public void setPhoneNumber(String phoneNumber)
	{
		this.user_phone = phoneNumber;
	}
	
	
}
