package com.littlefox.chinese.edu.object.result;

import java.util.ArrayList;

import com.littlefox.chinese.edu.object.UserBaseInformationObject;
import com.littlefox.chinese.edu.object.result.base.BaseResult;

public class UserLoginResult extends BaseResult
{
	private ArrayList<UserBaseInformationObject> list = new ArrayList<UserBaseInformationObject>();
	private String access_token = "";
	private String client_key	= "";
	private String server_key 	= "";
	public ArrayList<UserBaseInformationObject> getChildInfoList()
	{
		return list;
	}
	
	public String getAccessToken()
	{
		return access_token;
	}
	
	public String getClientKey()
	{
		return client_key;
	}
	
	public String getServerKey()
	{
		return server_key;
	}
	
}
