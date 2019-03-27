package com.littlefox.chinese.edu.object;

import java.util.ArrayList;

/**
 * 오토 로그인 일때 정보를 지속적으로 저장하기 위해 사용
 * @author 정재현
 *
 */
public class UserLoginObject
{
	private String userId = "";
	private String userPassword = "";
	
	public UserLoginObject(String userId , String userPassword)
	{
		this.userId = userId;
		this.userPassword = userPassword;
	}
	
	public void setUserID(String userId)
	{
		this.userId = userId;
	}
	
	public String getUserID()
	{
		return userId;
	}
	
	public String getUserPassword()
	{
		return userPassword;
	}
	
	public void setUserPassword(String userPassword)
	{
		this.userPassword = userPassword;
	}
	
	
	

}
