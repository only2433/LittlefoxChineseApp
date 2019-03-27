package com.littlefox.chinese.edu.object;

import android.content.Context;

import com.littlefox.chinese.edu.R;
import com.littlefox.chinese.edu.common.CommonUtils;

public class UserSelectInformation
{
	private String mUserName = "";

	
	public UserSelectInformation(String userName)
	{
		mUserName = userName;
	}

	
	public String getUserName()
	{
		return mUserName;
	}
	
	public String getBaseUserName(Context context)
	{
		return CommonUtils.getInstance(context).getLanguageTypeString(R.array.title_base_user);
	}
	
	public String getAddUserName(Context context)
	{
		return CommonUtils.getInstance(context).getLanguageTypeString(R.array.title_add_user);
	}
}
